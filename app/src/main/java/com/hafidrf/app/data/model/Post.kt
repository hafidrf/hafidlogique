package com.hafidrf.app.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("likes")
    val likes: Int = 0,
    @SerializedName("tags")
    val tags: List<String>?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("publishDate")
    val publishDate: String?,
    @SerializedName("owner")
    val owner: User?,
)