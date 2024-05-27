package com.hafidrf.app.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hafidrf.app.core.common.ext.view.loadImage
import com.hafidrf.app.data.model.User
import com.hafidrf.app.databinding.ItemUserBinding


class UserVH(private val viewBinding: ItemUserBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: User) {
        item.picture?.let { viewBinding.ivThumbnail.loadImage(it) }
        viewBinding.tvName.text = "${item.firstName} ${item.lastName}"
    }
}