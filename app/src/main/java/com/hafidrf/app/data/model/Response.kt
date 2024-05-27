package com.hafidrf.app.data.model

import com.squareup.moshi.Json


data class BaseResponseData<T>(
    @Json(name = "limit") val limit: Int,
    @Json(name = "page") val page: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "data") val data: List<T>
)