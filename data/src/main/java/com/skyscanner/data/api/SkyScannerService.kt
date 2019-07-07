package com.skyscanner.data.api

import com.google.gson.JsonObject
import com.skyscanner.data.model.FlightInfo
import retrofit2.Response
import retrofit2.http.*

interface SkyScannerService {

    @POST("pricing/v1.0")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    suspend fun createSession(
        @Field("apiKey") apiKey: String,
        @Field("locationschema") locationSchema: String,
        @Field("country") country: String,
        @Field("currency") currency: String,
        @Field("locale") locale: String,
        @Field("originplace") originPlace: String,
        @Field("destinationplace") destinationPlace: String,
        @Field("outbounddate") outboundDate: String,
        @Field("inbounddate") inboundDate: String,
        @Field("adults") adults: Int,
        @Field("cabinclass") cabinClass: String
    ): Response<JsonObject>

    @GET("pricing/v1.0/{sessionKey}")
    suspend fun getFlightInfo(
        @Path("sessionKey") sessionKey: String,
        @Query("apiKey") apiKey: String
    ): Response<FlightInfo>
}