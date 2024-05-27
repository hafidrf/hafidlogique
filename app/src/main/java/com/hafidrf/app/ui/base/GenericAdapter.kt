package com.hafidrf.app.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T>(
    private val itemClickListener: (T) -> Unit?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataObjects: MutableList<T> = mutableListOf()

    abstract fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { itemClickListener.invoke(getItem(position)) }
        onBindData(holder, position)
    }

    override fun getItemCount(): Int {
        return dataObjects.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: MutableList<T>) {
        dataObjects.clear()
        dataObjects.addAll(items)
        notifyDataSetChanged()
    }

    fun notifyDataChanged(){
        notifyDataSetChanged()
    }

    fun paginateItems(items: MutableList<T>) {
        dataObjects.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        dataObjects.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return dataObjects[position]
    }
}