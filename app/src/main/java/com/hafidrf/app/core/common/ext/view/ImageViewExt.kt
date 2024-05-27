package com.hafidrf.app.core.common.ext.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hafidrf.app.R

fun ImageView.loadImage(url: String, placeholder: Int = R.drawable.ic_profile_grey) {
    Glide.with(this)
        .load(url)
        .error(placeholder)
        .into(this)
}

fun ImageView.loadImageCircle(url: String, placeholder: Int = R.drawable.ic_profile_grey) {
    Glide.with(this)
        .load(url)
        .error(placeholder)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}


fun ImageView.loadImageRounded(url: String, placeholder: Int = R.drawable.ic_profile_grey) {
    Glide.with(this)
        .load(url)
        .error(placeholder)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
        .into(this)
}