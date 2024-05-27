package com.hafidrf.app.ui.adapter

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hafidrf.app.R
import com.hafidrf.app.core.common.ext.data.dashIfEmpty
import com.hafidrf.app.core.common.ext.view.loadImageCircle
import com.hafidrf.app.core.common.ext.view.loadImageRounded
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.databinding.ChipTagBinding
import com.hafidrf.app.databinding.ItemPostBinding


class LikeVH(private val viewBinding: ItemPostBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: PostEntity, isExist: Boolean, onLike: (PostEntity, Boolean) -> Unit) {
        item.image?.let { viewBinding.ivPhoto.loadImageRounded(it) }
        item.owner?.picture?.let { viewBinding.ivProfile.loadImageCircle(it) }

        viewBinding.llTag.removeAllViews()
        viewBinding.tvName.text = "${item.owner?.firstName} ${item.owner?.lastName}"
        viewBinding.tvDate.text = item.publishDate.dashIfEmpty()
        viewBinding.tvDesc.text = item.text.dashIfEmpty()
        viewBinding.tvLink.text = item.link.dashIfEmpty()
        viewBinding.tvLike.text = "${item.likes} Likes"

        viewBinding.ivLove.setImageResource(R.drawable.ic_like_fill)


        viewBinding.tvLink.setOnClickListener {
            Toast.makeText(
                viewBinding.root.context,
                "Underconstruction ${item.link}",
                Toast.LENGTH_SHORT
            ).show()
        }

        viewBinding.ivLove.setOnClickListener {
            item.id?.let { id ->
                onLike(item, isExist)
            }
        }

    }

    private fun showTag(): ChipTagBinding {
        return ChipTagBinding.inflate(
            LayoutInflater.from(viewBinding.root.context),
            viewBinding.llTag,
            true
        )
    }
}