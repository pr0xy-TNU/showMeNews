package com.showmenews.usecase.base

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<VH : RecyclerView.ViewHolder, T> : RecyclerView.Adapter<VH>() {

    val sizeChengedListener: ((Int) -> Unit)? = null

    private val _items = ArrayList<T>()
    var items: List<T>
        get() = _items
        set(value) {
            _items.clear()
            _items.addAll(value)
            notifyDataSetChanged()
            sizeChengedListener?.let { it(items.size) }
        }

    override fun getItemCount(): Int = _items.size

    open fun addItems(data: Collection<T>) {
        _items.addAll(data)
        notifyItemRangeInserted(itemCount - data.size, data.size)
        sizeChengedListener?.let { it(items.size) }
    }

    fun insertItem(item: T, index: Int) {
        _items.add(index, item)
        notifyItemInserted(index)
        sizeChengedListener?.let { it(_items.size) }
    }

    fun remoteItem(index:Int) {
        _items.removeAt(index)
        notifyItemRemoved(index)
        sizeChengedListener?.let { it(_items.size) }
    }

    fun swapItems(data: Collection<T>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
        sizeChengedListener?.let { it(_items.size) }
    }

    fun clear() {
        _items.clear()
        notifyDataSetChanged()
        sizeChengedListener?.let { it(_items.size) }
    }

    fun logDebug(message:String){
        Log.d(this::class.java.simpleName, message)
    }
}