package com.showmenews.arch.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.closeKeyboard() {
    val view: View? = activity?.currentFocus
    view?.let {
        val manager: InputMethodManager? =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager?.let {
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

fun Fragment.logDebug(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun Fragment.makeSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    val snackBar = Snackbar.make(activity!!.findViewById(android.R.id.content), message, duration)

    val snackbarView = snackBar.getView()

    val textView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(activity!!, android.R.color.black))

    snackbarView.setBackgroundColor(ContextCompat.getColor(activity!!, android.R.color.white))
    snackBar.show()
}