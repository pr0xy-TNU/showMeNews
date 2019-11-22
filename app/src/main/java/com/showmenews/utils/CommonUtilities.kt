package com.showmenews.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.util.TypedValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.showmenews.App
import com.showmenews.arch.extensions.logDebug
import com.showmenews.data.ApiResponse
import com.showmenews.data.Error
import com.showmenews.data.ErrorType
import com.showmenews.data.ErrorWrapper
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


fun parseThrowable(throwable: Throwable): Error {
    when (throwable) {
        is HttpException -> {
            val errorBody = throwable.response()?.errorBody()?.string()
            errorBody?.let {
                if (errorBody.isEmpty()) return@let
                val gson = Gson()
                try {
                    val result = gson.fromJson(it, ErrorWrapper::class.java)
                    result?.let { errorWrapper ->
                        return Error(
                            ErrorType.HTTP,
                            hashMapOf("failure" to errorWrapper.fault?.errorDescription)
                        )
                    }
                } catch (excp: Exception) {
                    return Error(ErrorType.HTTP, hashMapOf("failure" to excp.localizedMessage))
                }
            }
            return Error(ErrorType.HTTP, hashMapOf("failure" to throwable.localizedMessage))
        }
        is IOException -> {
            return Error(ErrorType.NETWORK, hashMapOf("failure" to "Connection error"))
        }
    }
    return Error(ErrorType.UNHANDLED, hashMapOf("failure" to "Unknown error"))
}

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val netInfo = cm?.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}