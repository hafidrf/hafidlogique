package com.hafidrf.app.data.remote.domain

import retrofit2.Response
import retrofit2.http.*
import com.hafidrf.app.data.model.BaseResponseData
import com.hafidrf.app.data.model.Post

interface PostServices {

    @GET("post")
    suspend fun getPost(
        @Query("limit") limit : Int,
        @Query("page") page : Int
    ): Response<BaseResponseData<Post>>

    @GET("user/{id}/post")
    suspend fun getUserPost(
        @Path("id") id : String,
        @Query("limit") limit : Int,
        @Query("page") page : Int
    ): Response<BaseResponseData<Post>>


    @GET("tag/{tag}/post")
    suspend fun getPostByTag(
        @Path("tag") tag : String,
        @Query("limit") limit : Int,
        @Query("page") page : Int
    ): Response<BaseResponseData<Post>>
}