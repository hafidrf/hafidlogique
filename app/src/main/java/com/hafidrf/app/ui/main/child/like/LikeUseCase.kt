package com.hafidrf.app.ui.main.child.like

import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.repository.PostRepository

class LikeUseCase(
    private val postRepository: PostRepository
) {
    suspend fun getLikePosts() = postRepository.getLikePosts()
    suspend fun addLike(postEntity: PostEntity) = postRepository.addLike(postEntity)
    suspend fun deleteLike(idPost: String) = postRepository.deleteLike(idPost)
    fun isLikeExist(idPost: String) = postRepository.isLikeExist(idPost)
}