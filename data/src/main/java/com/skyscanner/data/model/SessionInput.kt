package com.skyscanner.data.model

data class SessionInput(
    val locationSchema: String,
    val country: String,
    val currency: String,
    val locale: String,
    val placeOfOriginCode: String,
    val placeOfOrigin: String,
    val placeOfDestinationCode: String,
    val placeOfDestination: String,
    val outboundDate: String,
    val inboundDate: String,
    val adults: Int,
    val cabinClassType: String
)