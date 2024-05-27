package com.hafidrf.app.ui.user

import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.repository.PostRepository
import com.hafidrf.app.data.repository.UserRepository

class DetailUserUseCase(
    private val userRepo: UserRepository,
    private val postRepo: PostRepository
) {
    suspend fun getUserDetail(userId : String) = userRepo.getUserDetail(userId)
    suspend fun getPost(userId : String, limit : Int, page : Int) = postRepo.getUserPost(userId, limit, page)

    suspend fun getLikePosts() = postRepo.getLikePosts()
    suspend fun addLike(postEntity: PostEntity) = postRepo.addLike(postEntity)
    suspend fun deleteLike(idPost: String) = postRepo.deleteLike(idPost)
}