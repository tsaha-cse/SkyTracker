package com.skyscanner.presentation.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getNextMondayInString(): String = getNextMonday().toServerFormat()

fun Calendar.getFollowingDayOfMondayInString(): String =
    this.apply {
        time = getNextMonday()
        add(Calendar.DATE, 1)
    }.time.toServerFormat()


fun Calendar.getNextMonday(): Date {
    val now = this
    val weekday = now.get(Calendar.DAY_OF_WEEK)
    if (weekday != Calendar.MONDAY) {
        val days = (Calendar.SATURDAY - weekday + 2) % 7
        now.add(Calendar.DAY_OF_YEAR, days)
    }
    return now.time
}

@SuppressLint("SimpleDateFormat")
fun Date.toServerFormat(): String = SimpleDateFormat("yyyy-MM-dd").format(this)

@SuppressLint("SimpleDateFormat")
fun formatDateMonth(date: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val output = SimpleDateFormat("dd MMM")
    return output.format(sdf.parse(date))
}