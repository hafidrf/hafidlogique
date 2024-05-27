package com.hafidrf.app.ui.main.child.user

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hafidrf.app.R
import com.hafidrf.app.core.common.ext.view.viewBinding
import com.hafidrf.app.databinding.FragmentUserBinding
import com.hafidrf.app.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.core.common.ext.view.onEndScroll
import com.hafidrf.app.core.common.ext.view.toBinding
import com.hafidrf.app.data.model.BaseResponseData
import com.hafidrf.app.data.model.LoaderState
import com.hafidrf.app.data.model.ResultState
import com.hafidrf.app.data.model.User
import com.hafidrf.app.ui.adapter.UserVH
import com.hafidrf.app.ui.base.GenericAdapter
import com.hafidrf.app.ui.user.DetailUserActivity


class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val viewBinding: FragmentUserBinding by viewBinding(FragmentUserBinding::bind)
    private val viewModel: UserViewModel by viewModel()

    private val userAdapter = object : GenericAdapter<User>(
        itemClickListener = {
            it.id?.let { id ->
                startActivity(DetailUserActivity.newIntent(requireActivity(), id))
            }

        }
    ) {
        override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return UserVH(parent.toBinding())
        }

        override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
            val data = getItem(position)
            (holder as UserVH).bind(data)
        }
    }

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        observeData()

        loadUser()
    }


    private fun initView() {
        viewBinding.rvUser.adapter = userAdapter
        viewBinding.rvUser.onEndScroll(true) {
            if((userAdapter.itemCount % RequestCons.QUERY.LIMIT == 0) && !isLoading)
                loadUser(page)
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) { handleUser(it) }
        viewModel.loader.observe(viewLifecycleOwner) { handleLoading(it) }
    }

    private fun initListener() {
        viewBinding.swipeRefresh.setOnRefreshListener {
            page = 0
            loadUser(page)
        }
    }

    private fun loadUser(page: Int = 0) {
        viewModel.getUser(page = page)
    }

    private fun handleUser(state: ResultState<BaseResponseData<User>>) {
        when (state) {
            is ResultState.Success -> {
                state.data?.let {
                    page = it.page + 1
                    if (it.page == 0) {
                        it.data.let { items ->
                            userAdapter.setItems(items.toMutableList())
                        }
                    } else {
                        it.data.let { items ->
                            userAdapter.paginateItems(items.toMutableList())
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
            UserFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}