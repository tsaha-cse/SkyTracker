package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class Leg(

	@field:SerializedName("SegmentIds")
	val segmentIds: List<Int?>? = null,

	@field:SerializedName("Duration")
	val duration: Int,

	@field:SerializedName("Arrival")
	val arrival: String,

	@field:SerializedName("Carriers")
	val carriers: List<Int> = emptyList(),

	@field:SerializedName("Directionality")
	val directionality: String,

	@field:SerializedName("OriginStation")
	val originStation: Int,

	@field:SerializedName("Departure")
	val departure: String,

	@field:SerializedName("FlightNumbers")
	val flightNumbers: List<FlightNumbersItem?> = emptyList(),

	@field:SerializedName("JourneyMode")
	val journeyMode: String,

	@field:SerializedName("DestinationStation")
	val destinationStation: Int,

	@field:SerializedName("Stops")
	val stops: List<Any?>? = null,

//	@field:SerializedName("OperatingCarriers")
//	val operatingCarriers: List<Int?>? = null,

	@field:SerializedName("Id")
	val id: String
)