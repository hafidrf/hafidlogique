package com.hafidrf.app.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.hafidrf.app.data.local.converter.PostConverter
import com.hafidrf.app.data.model.User

@Entity(tableName = "post")
@TypeConverters(PostConverter::class)
data class PostEntity(
    @PrimaryKey
    @SerializedName("id")
    @NonNull
    val id: String,
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