package com.jbrunoo.codinghealth.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getPublishedTime(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(currentTimeMillis))
}