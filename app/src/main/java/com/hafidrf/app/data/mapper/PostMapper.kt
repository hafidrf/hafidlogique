package com.hafidrf.app.data.mapper

import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.model.Post

fun Post.toEntity() : PostEntity{
    return PostEntity(
        id?:"0", text, image, likes, tags, link, publishDate, owner
    )
}

fun PostEntity.toModel() : Post{
    return Post(
        id, text, image, likes, tags, link, publishDate, owner
    )
}