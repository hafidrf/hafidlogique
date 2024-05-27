package com.hafidrf.app.data.repository

import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.remote.domain.PostServices

class PostRepository(
    private val postServices: PostServices,
    private val postDao: PostDao,
) {
    suspend fun getPost(limit : Int, page : Int) = postServices.getPost(limit, page)
    suspend fun getPostByTag(tag : String, limit : Int, page : Int) = postServices.getPostByTag(tag, limit, page)
    suspend fun getUserPost(userId : String, limit : Int, page : Int) = postServices.getUserPost(userId, limit, page)

    suspend fun getLikePosts() = postDao.getPosts()
    suspend fun addLike(post : PostEntity) = postDao.insert(post)
    suspend fun deleteLike(idPost : String) = postDao.deletePost(idPost)
    fun isLikeExist(idPost : String) = postDao.isExist(idPost)
}