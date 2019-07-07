package com.skyscanner.domain

/**
 * File consists helper functions to
 * convert the API data model
 * to business specific data model
 */

import android.annotation.SuppressLint
import com.skyscanner.data.model.Carrier
import com.skyscanner.data.model.Leg
import com.skyscanner.data.model.Place
import com.skyscanner.domain.model.Inbound
import com.skyscanner.domain.model.Outbound
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

fun Leg.toOutbound(placesMap: Map<Int, Place>, carriersMap: Map<Int, Carrier>): Outbound =
    Outbound(
        id,
        formatToHHmm(departure),
        formatToHHmm(arrival),
        placesMap.getValue(originStation).code,
        placesMap.getValue(destinationStation).code,
        carriersMap.getValue(carriers[0]).name,
        carriersMap.getValue(carriers[0]).imageUrl,
        fromMinutesToHHmm(duration)
    )

fun Leg.toInbound(placesMap: Map<Int, Place>, carriersMap: Map<Int, Carrier>): Inbound =
    Inbound(
        id,
        formatToHHmm(departure),
        formatToHHmm(arrival),
        placesMap.getValue(originStation).code,
        placesMap.getValue(destinationStation).code,
        carriersMap.getValue(carriers[0]).name,
        carriersMap.getValue(carriers[0]).imageUrl,
        fromMinutesToHHmm(duration)
    )

fun fromMinutesToHHmm(minutes: Int): String {
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    if (minutes % 60 == 0) {
        return String.format("%2dh", hours)
    }
    val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%2dh %2dm", hours, remainMinutes)
}

@SuppressLint("SimpleDateFormat")
fun formatToHHmm(dateTime: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val output = SimpleDateFormat("HH:mm")
    return output.format(sdf.parse(dateTime))
}