package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class Carrier(

    @field:SerializedName("ImageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Code")
    val code: String,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("DisplayCode")
    val displayCode: String
)