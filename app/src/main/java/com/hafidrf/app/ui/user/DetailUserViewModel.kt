package com.hafidrf.app.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.model.*
import com.hafidrf.app.ui.base.BaseViewModel

class DetailUserViewModel(
    private val useCase : DetailUserUseCase,
    private val postDao: PostDao
) : BaseViewModel() {

    private val _user = MutableLiveData<ResultState<User>>()
    val user: LiveData<ResultState<User>> = _user

    fun getUser(userId : String) {
        loaderState.value = LoaderState.OnLoading(true)
        viewModelScope.launch {
            try {
                val response = useCase.getUserDetail(userId)
                if (response.isSuccessful) {
                    _user.postValue(ResultState.Success(response.body()))
                } else {
                    _user.postValue(
                        ResultState.Error(
                            errorCode = response.code(),
                            data = response.errorBody()
                        )
                    )
                }
                loaderState.value = LoaderState.OnLoading(false)
            } catch (throwable: Throwable) {
                _user.postValue(ResultState.Error(exception = throwable))
                loaderState.value = LoaderState.OnLoading(false)
            }
        }
    }

    private val _post = MutableLiveData<ResultState<BaseResponseData<Post>>>()
    val post: LiveData<ResultState<BaseResponseData<Post>>> = _post

    fun getPost(userId : String, limit: Int = RequestCons.QUERY.LIMIT, page: Int) {
        loaderState.value = LoaderState.OnLoading(true)
        viewModelScope.launch {
            try {
                val response = useCase.getPost(userId, limit, page)
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
                _post.postValue(ResultState.Error(exception = throwable))
                loaderState.value = LoaderState.OnLoading(false)
            }
        }
    }


    fun getPostDao() = postDao
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