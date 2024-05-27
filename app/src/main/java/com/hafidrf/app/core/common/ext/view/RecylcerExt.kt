package com.hafidrf.app.core.common.ext.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.onEndScroll(isGrid: Boolean, listener: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var mLayoutManager: LinearLayoutManager

            if (isGrid)
                mLayoutManager = recyclerView.layoutManager as GridLayoutManager
            else
                mLayoutManager = recyclerView.layoutManager as LinearLayoutManager


            val visibleItemCount = recyclerView.childCount
            val totalItemCount = mLayoutManager.itemCount
            val firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()


            if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                listener()
            }
        }
    })
}
