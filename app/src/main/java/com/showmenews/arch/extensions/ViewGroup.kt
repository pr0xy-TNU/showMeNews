package com.showmenews.arch.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resourceId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resourceId, this, attachToRoot)
}
