package com.showmenews.utils

import java.text.SimpleDateFormat
import java.util.*


object DateFormats {
    const val EEEMMMdyyyy = "EEE, MMM d yyyy"
    const val yyyyMMddThhmmssZ = "yyyy-MM-dd'T'hh:mm:ssZ"
    const val yyyyMMdd = "yyyyMMdd"
}

fun formattedDateFromStringToString(
    source: String,
    pattern: String = DateFormats.EEEMMMdyyyy
): String {
    val res = SimpleDateFormat(DateFormats.yyyyMMddThhmmssZ, Locale.ENGLISH).parse(source)
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(res)
}

fun formattedDateFromDateToString(source: Date, pattern: String = DateFormats.yyyyMMdd): String {
    return SimpleDateFormat(pattern,  Locale.ENGLISH).format(source)
}