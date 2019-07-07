package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class FlightNumbersItem(

	@field:SerializedName("CarrierId")
	val carrierId: Int? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null
)