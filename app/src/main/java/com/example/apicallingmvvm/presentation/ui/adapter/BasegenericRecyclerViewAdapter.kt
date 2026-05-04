package com.example.apicallingmvvm.presentation.ui.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

abstract class BaseGenericRecyclerViewAdapter<T>(
    private var items: ArrayList<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun getViewType(position: Int): Int

    abstract fun onBindData(holder: RecyclerView.ViewHolder?, `val`: T)

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return setViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder, position: Int) {
        onBindData(viewHolder, items[viewHolder.adapterPosition])
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(`object`: T) {
        items.add(`object`)
        notifyItemInserted(items.size - 1)
    }

    fun addItemAt(`object`: T, position: Int) {
        items.add(position, `object`)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun addAllItems(modelItems: java.util.ArrayList<T>) {
        items = modelItems
        this.notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun removeItemAt(position: Int): T {
        val item = items[position]
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
        return item
    }

    fun replaceItem(newItem: T, position: Int) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun getItems(): ArrayList<T> {
        return items
    }

    fun updateData(newList: List<T>) {
        (items as MutableList<*>).clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}