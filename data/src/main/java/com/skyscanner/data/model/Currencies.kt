package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class Currencies(

    @field:SerializedName("Symbol")
    val symbol: String? = null,

    @field:SerializedName("Code")
    val code: String? = null
)