package com.hafidrf.app.ui.adapter

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hafidrf.app.R
import com.hafidrf.app.core.common.ext.data.dashIfEmpty
import com.hafidrf.app.core.common.ext.view.loadImageCircle
import com.hafidrf.app.core.common.ext.view.loadImageRounded
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.local.entity.PostEntity
import com.hafidrf.app.data.mapper.toEntity
import com.hafidrf.app.data.model.Post
import com.hafidrf.app.databinding.ChipTagBinding
import com.hafidrf.app.databinding.ItemPostBinding


class PostVH(private val viewBinding: ItemPostBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Post, dao: PostDao? = null, onTag : (String) -> Unit, onLike : (PostEntity, Boolean) -> Unit) {
        item.image?.let { viewBinding.ivPhoto.loadImageRounded(it) }
        item.owner?.picture?.let { viewBinding.ivProfile.loadImageCircle(it) }
        val isExist = dao?.isExist(item.id) ?: false

        viewBinding.llTag.removeAllViews()
        viewBinding.tvName.text = "${item.owner?.firstName} ${item.owner?.lastName}"
        viewBinding.tvDate.text = item.publishDate.dashIfEmpty()
        viewBinding.tvDesc.text = item.text.dashIfEmpty()
        viewBinding.tvLink.text = item.link.dashIfEmpty()
        viewBinding.tvLike.text = "${item.likes} Likes"
        viewBinding.ivLove.setOnClickListener {
            onLike(item.toEntity(), isExist)
        }

        if(isExist){
            viewBinding.ivLove.setImageResource(R.drawable.ic_like_fill)
        }else{
            viewBinding.ivLove.setImageResource(R.drawable.ic_like)
        }

        viewBinding.tvLink.setOnClickListener {
            Toast.makeText(
                viewBinding.root.context,
                "Underconstruction ${item.link}",
                Toast.LENGTH_SHORT
            ).show()
        }

        item.tags?.forEach {
            val tagBinding = showTag()
            tagBinding.root.setOnClickListener{ view ->
                onTag(it)
            }
            tagBinding.tvTag.text = it.dashIfEmpty()
            tagBinding.tvTag.tag = it
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