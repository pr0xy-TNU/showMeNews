package com.showmenews.arch.extensions

import android.util.Log
import androidx.lifecycle.ViewModel

fun ViewModel.logDebug(message: String) {
    Log.d(this::class.java.simpleName, message)
}