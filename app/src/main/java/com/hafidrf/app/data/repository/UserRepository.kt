package com.hafidrf.app.data.repository

import com.hafidrf.app.data.remote.domain.UserServices

class UserRepository(
    private val userServices: UserServices
) {
    suspend fun getUser(limit : Int, page : Int) = userServices.getUser(limit, page)
    suspend fun getUserDetail(userId : String) = userServices.getDetailUser(userId)
}