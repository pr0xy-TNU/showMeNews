package com.showmenews.usecase.home.categories

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showmenews.R
import com.showmenews.arch.extensions.inflate
import com.showmenews.usecase.base.BaseRvAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter : BaseRvAdapter<CategoriesAdapter.CategoryViewHolder, String>() {

    var selectedItemSet = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = parent.inflate(R.layout.item_category, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CategoryViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: String) = with(containerView) {
            tvCategoryName.text = item
            if (selectedItemSet.contains(item)){
                chbCategory.isChecked = true
            }
            chbCategory.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItemSet.add(item)
                } else {
                    selectedItemSet.remove(item)
                }
            }
        }
    }

    fun clearSelectedList() {
        selectedItemSet.clear()
    }
}
