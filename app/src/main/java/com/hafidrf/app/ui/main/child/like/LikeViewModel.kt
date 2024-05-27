package com.hafidrf.app.ui.main.child.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.ui.base.BaseViewModel

class LikeViewModel(
    private val useCase: LikeUseCase
) : BaseViewModel() {

    private val _post = MutableLiveData<List<PostEntity>>()
    val post: LiveData<List<PostEntity>> = _post

    fun getLikePosts(){
        viewModelScope.launch {
            try {
                _post.postValue(useCase.getLikePosts())
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

    fun isExist(idPost: String) = useCase.isLikeExist(idPost)
}