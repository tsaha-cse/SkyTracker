package com.skyscanner.data.model

import com.google.gson.annotations.SerializedName

data class Agent(

    @field:SerializedName("ImageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Name")
    val name: String
)