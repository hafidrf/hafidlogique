package com.hafidrf.app.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import com.hafidrf.app.core.common.cons.IntentCons
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.core.common.ext.data.dashIfEmpty
import com.hafidrf.app.core.common.ext.view.*
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.model.*
import com.hafidrf.app.databinding.ActivityDetailUserBinding
import com.hafidrf.app.ui.adapter.PostVH
import com.hafidrf.app.ui.base.BaseActivity
import com.hafidrf.app.ui.base.GenericAdapter

class DetailUserActivity : BaseActivity(), KoinComponent {

    private val viewBinding: ActivityDetailUserBinding by lazy {
        ActivityDetailUserBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailUserViewModel by viewModel()

    private var userId: String? = null
    private var isLoading = false

    private var posts = ArrayList<Post>()
    private var filterPosts = ArrayList<Post>()

    private var postDao : PostDao? = null

    private val postAdapter = object : GenericAdapter<Post>(
        itemClickListener = {

        }
    ) {
        override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return PostVH(parent.toBinding())
        }

        override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
            val data = getItem(position)
            (holder as PostVH).bind(data, postDao, {

            }, { item, isExist ->
                if (isExist) {
                    Toast.makeText(this@DetailUserActivity, "Unlike ${item.owner?.firstName} Post", Toast.LENGTH_SHORT).show()
                    data.id?.let { viewModel.deleteLike(it) }
                } else {
                    Toast.makeText(this@DetailUserActivity, "Liked ${item.owner?.firstName} Post", Toast.LENGTH_SHORT).show()
                    viewModel.addLike(item)
                }
                notifyItemChanged(position)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        intent.getStringExtra(IntentCons.KEY.USER_ID)?.let {
            userId = it
        }

        initView()
        initListener()
        observeData()

        userId?.let { viewModel.getUser(it) }
    }

    private fun observeData() {
        viewModel.user.observe(this) { handleUser(it) }
        viewModel.post.observe(this) { handlePost(it) }
        viewModel.loader.observe(this) { handleLoading(it) }
    }


    private fun initView() {
        postDao = viewModel.getPostDao()
        viewBinding.rvPost.adapter = postAdapter
        viewBinding.rvPost.onEndScroll(false) {
            if (viewBinding.etSearch.text.isEmpty()) {
                if ((postAdapter.itemCount % RequestCons.QUERY.LIMIT == 0) && !isLoading)
                    loadPost(page)
            }
        }
    }

    private fun initListener() {
        viewBinding.swipeRefresh.setOnRefreshListener {
            page = 0
            loadPost(page)
        }

        viewBinding.ivBack.setOnClickListener {
            finish()
        }
        viewBinding.etSearch.onChangeText { value ->
            viewBinding.tvMessageError.isVisible = false
            if (value.isEmpty()) {
                postAdapter.setItems(posts)
            } else {
                filterPosts.clear()
                posts.forEach { item ->
                    if (item.text.toString().contains(value)) {
                        filterPosts.add(item)
                    }
                }
                postAdapter.setItems(filterPosts)
            }

            viewBinding.tvMessageError.isVisible = postAdapter.itemCount == 0
        }
    }

    private fun loadPost(page: Int = 0) {
        viewBinding.etSearch.setText("")
        userId?.let { viewModel.getPost(page = page, userId = it) }
    }

    private fun handleUser(state: ResultState<User>) {
        when (state) {
            is ResultState.Success -> {
                state.data?.let {
                    loadPost(0)
                    setUserData(it)
                }

            }
            is ResultState.Error -> {
                Toast.makeText(
                    this,
                    "Erorr ${state.errorCode}  : Terjadi kesalahan saat mengambil data User",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handlePost(state: ResultState<BaseResponseData<Post>>) {
        when (state) {
            is ResultState.Success -> {
                state.data?.let {
                    page = it.page + 1
                    if (it.page == 0) {
                        it.data.let { items ->
                            posts.clear()
                            posts.addAll(items)
                            postAdapter.setItems(items.toMutableList())
                        }
                    } else {
                        it.data.let { items ->
                            posts.addAll(items)
                            postAdapter.paginateItems(items.toMutableList())
                        }
                    }

                    viewBinding.tvMessageError.isVisible = postAdapter.itemCount == 0
                }

            }
            is ResultState.Error -> {
                Toast.makeText(
                    this,
                    "Erorr ${state.errorCode}  : Terjadi kesalahan saat mengambil data post",
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

    private fun setUserData(user: User) {
        with(viewBinding) {
            user.picture?.let { ivThumbnail.loadImageRounded(it) }
            tvName.text = "${user.firstName} ${user.lastName}"
            tvAddresss.text = user.location.toString()
            tvGender.text = user.gender.dashIfEmpty()
            tvEmail.text = user.email.dashIfEmpty()
            tvPhone.text = user.phone.dashIfEmpty()
        }
    }

    companion object {
        fun newIntent(context: Context, userId: String): Intent {
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra(IntentCons.KEY.USER_ID, userId)
            return intent
        }
    }
}