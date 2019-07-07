package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class FlightInfo(

    @field:SerializedName("Status")
    val status: String? = null,

    @field:SerializedName("Carriers")
    val carriers: List<Carrier> = emptyList(),

    @field:SerializedName("Legs")
    val legs: List<Leg> = emptyList(),

    @field:SerializedName("Itineraries")
    val itineraries: List<ItinerariesItem> = emptyList(),

    @field:SerializedName("SessionKey")
    val sessionKey: String? = null,

    @field:SerializedName("Agents")
    val agents: List<Agent> = emptyList(),

    @field:SerializedName("Segments")
    val segments: List<SegmentsItem> = emptyList(),

    @field:SerializedName("Currencies")
    val currencies: List<Currencies> = emptyList(),

    @field:SerializedName("Places")
    val places: List<Place> = emptyList(),

    @field:SerializedName("Query")
    val query: Query? = null
)