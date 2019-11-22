package com.showmenews.arch.extensions

import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.logging(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun AppCompatActivity.makeSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), message, duration)

    val snackbarView = snackBar.getView()

    val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))

    snackbarView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
    snackBar.show()
}