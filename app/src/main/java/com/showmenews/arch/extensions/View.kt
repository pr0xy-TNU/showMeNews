package com.showmenews.arch.extensions

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.showmenews.R
import com.squareup.picasso.Picasso


fun ImageView.loadFit(
    imageUrl: String?,
    placeHolderImg: Int = R.drawable.bg_placeholder,
    errorHolderImg: Int = R.drawable.bg_placeholder
) {
    Picasso.get()
        .load(imageUrl)
        .error(placeHolderImg)
        .fit()
        .centerInside()
        .placeholder(errorHolderImg)
        .into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun RecyclerView.onScrollToEnd(linearLayoutManager: LinearLayoutManager, onScrollNearEnd: (Unit) -> Unit)
        = addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (linearLayoutManager.childCount + linearLayoutManager.findFirstVisibleItemPosition() >= linearLayoutManager.itemCount - 5) {
            onScrollNearEnd(Unit)
        }
    }
})