package com.hafidrf.app.data.remote.domain

import retrofit2.Response
import retrofit2.http.*
import com.hafidrf.app.data.model.BaseResponseData
import com.hafidrf.app.data.model.User

interface UserServices {


    @GET("user")
    suspend fun getUser(
        @Query("limit") limit : Int,
        @Query("page") page : Int
    ): Response<BaseResponseData<User>>

    @GET("user/{id}")
    suspend fun getDetailUser(
        @Path("id") id : String
    ): Response<User>

}