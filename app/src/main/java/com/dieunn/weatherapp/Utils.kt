package com.dieunn.weatherapp

import android.text.format.DateFormat
import java.util.*

const val API_KEY = "cf58d530508a2e92363fbf293d230cf2"

fun parseLongTimestampToDate(
    time: Long,
    isTimeOnly: Boolean = false,
    isDayOnly: Boolean = false
): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = time * 1000
    if (isTimeOnly) return DateFormat.format("HH:mm", cal).toString()
    if (isDayOnly) return DateFormat.format("dd/MM",cal).toString()
    return DateFormat.format("dd-MM-yyyy HH:mm", cal).toString()
}

