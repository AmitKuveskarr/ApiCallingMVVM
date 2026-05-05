package com.example.apicallingmvvm.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseGenericRecyclerViewAdapter<T>(
    private var items: ArrayList<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun getViewType(position: Int): Int

    abstract fun onBindData(holder: RecyclerView.ViewHolder?, `val`: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setViewHolder(parent, viewType)
    }//

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        onBindData(viewHolder, items[position])
    }//

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }//

    override fun getItemCount(): Int {
        return items.size
    }
}
