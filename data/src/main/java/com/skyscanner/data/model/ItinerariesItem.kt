package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class ItinerariesItem(

    @field:SerializedName("InboundLegId")
    val inboundLegId: String,

    @field:SerializedName("OutboundLegId")
    val outboundLegId: String,

    @field:SerializedName("PricingOptions")
    val pricingOptions: List<PricingOption> = emptyList()
)