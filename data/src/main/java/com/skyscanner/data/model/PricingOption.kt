package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class PricingOption(

	@field:SerializedName("DeeplinkUrl")
	val deeplinkUrl: String? = null,

	@field:SerializedName("Price")
	val price: Double,

	@field:SerializedName("Agents")
	val agents: List<Int> = emptyList(),

	@field:SerializedName("QuoteAgeInMinutes")
	val quoteAgeInMinutes: Int? = null
)