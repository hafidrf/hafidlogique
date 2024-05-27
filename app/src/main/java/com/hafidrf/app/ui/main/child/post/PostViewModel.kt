package com.hafidrf.app.ui.main.child.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.model.*
import com.hafidrf.app.ui.base.BaseViewModel

class PostViewModel(
    private val useCase: PostUseCase
) : BaseViewModel() {
    private val _post = MutableLiveData<ResultState<BaseResponseData<Post>>>()
    val post: LiveData<ResultState<BaseResponseData<Post>>> = _post

    private val _postDB = MutableLiveData<List<PostEntity>>()
    val postDB: LiveData<List<PostEntity>> = _postDB

    fun getPost(limit: Int = RequestCons.QUERY.LIMIT, page: Int, tag : String?) {
        loaderState.value = LoaderState.OnLoading(true)
        viewModelScope.launch {
            try {
                val response = if(tag == null) useCase.getPost(limit, page) else useCase.getPostByTag(tag, limit, page)
                if (response.isSuccessful) {
                    _post.postValue(ResultState.Success(response.body()))
                } else {
                    _post.postValue(
                        ResultState.Error(
                            errorCode = response.code(),
                            data = response.errorBody()
                        )
                    )
                }
                loaderState.value = LoaderState.OnLoading(false)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                _post.postValue(ResultState.Error(exception = throwable))
                loaderState.value = LoaderState.OnLoading(false)
            }
        }
    }


    fun getLikePosts(){
        viewModelScope.launch {
            try {
                _postDB.postValue(useCase.getLikePosts())
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun addLike(postEntity: PostEntity){
        viewModelScope.launch {
            try {
                useCase.addLike(postEntity)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteLike(idPost : String){
        viewModelScope.launch {
            try {
                useCase.deleteLike(idPost)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}