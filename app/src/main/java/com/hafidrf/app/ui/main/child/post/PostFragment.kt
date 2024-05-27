package com.hafidrf.app.ui.main.child.post

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.hafidrf.app.R
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.core.common.ext.view.onEndScroll
import com.hafidrf.app.core.common.ext.view.toBinding
import com.hafidrf.app.core.common.ext.view.viewBinding
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.model.*
import com.hafidrf.app.databinding.FragmentPostBinding
import com.hafidrf.app.ui.adapter.PostVH
import com.hafidrf.app.ui.base.BaseFragment
import com.hafidrf.app.ui.base.GenericAdapter


class PostFragment : BaseFragment(R.layout.fragment_post), KoinComponent {

    private val viewBinding: FragmentPostBinding by viewBinding(FragmentPostBinding::bind)
    private val viewModel: PostViewModel by viewModel()

    private val postDao : PostDao by inject()

    private val postAdapter = object : GenericAdapter<Post>(
        itemClickListener = {
            //todo onClick
        }
    ) {
        override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return PostVH(parent.toBinding())
        }

        override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
            val data = getItem(position)
            (holder as PostVH).bind(data, postDao, {
                loadPost(0, it)
                viewBinding.flTag.isVisible = true
                viewBinding.tvTag.text = it
            }, { item, isExist ->
                if(isExist){
                    Toast.makeText(requireContext(), "Unlike ${item.owner?.firstName} Post", Toast.LENGTH_SHORT).show()
                    data.id?.let { viewModel.deleteLike(it) }
                }else{
                    Toast.makeText(requireContext(), "Liked ${item.owner?.firstName} Post", Toast.LENGTH_SHORT).show()
                    viewModel.addLike(item)
                }
                notifyItemChanged(position)
            })
        }
    }

    private var isLoading = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
        initListener()
        observeData()

        loadPost()
    }


    private fun initView() {
        viewBinding.rvPost.adapter = postAdapter
        viewBinding.rvPost.onEndScroll(false) {
            if((postAdapter.itemCount % RequestCons.QUERY.LIMIT == 0) && !isLoading)
                loadPost(page)
        }
    }

    private fun observeData() {
        viewModel.post.observe(viewLifecycleOwner) { handlePost(it) }
        viewModel.loader.observe(viewLifecycleOwner) { handleLoading(it) }
    }

    private fun initListener() {
        viewBinding.swipeRefresh.setOnRefreshListener {
            page = 0
            loadPost(page)
        }
        viewBinding.flTag.setOnClickListener {
            loadPost(0)
            viewBinding.flTag.isVisible = false
        }
    }

    private fun loadPost(page: Int = 0, tag : String? = null) {
        viewModel.getPost(page = page, tag = tag)
    }

    private fun handlePost(state: ResultState<BaseResponseData<Post>>) {
        when (state) {
            is ResultState.Success -> {
                state.data?.let {
                    page = it.page + 1
                    if (it.page == 0) {
                        it.data.let { items ->
                            postAdapter.setItems(items.toMutableList())
                        }
                    } else {
                        it.data.let { items ->
                            postAdapter.paginateItems(items.toMutableList())
                        }
                    }
                }

            }
            is ResultState.Error -> {
                Toast.makeText(
                    requireContext(),
                    "Erorr ${state.errorCode}  : Terjadi kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleLoading(state: LoaderState?) {
        when (state) {
            is LoaderState.OnLoading -> {
                isLoading = state.isLoading
                if (state.isLoading) {
                    viewBinding.progressBar.isVisible = !viewBinding.swipeRefresh.isRefreshing
                } else {
                    viewBinding.progressBar.isVisible = false
                    viewBinding.swipeRefresh.isRefreshing = false
                }
            }
            else -> {
                viewBinding.progressBar.isVisible = false
                viewBinding.swipeRefresh.isRefreshing = false
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}