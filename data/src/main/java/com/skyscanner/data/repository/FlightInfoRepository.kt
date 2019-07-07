package com.skyscanner.data.repository

import com.skyscanner.data.model.FlightInfo

interface FlightInfoRepository {

    suspend fun getFlightInfo(sessionId: String): FlightInfo
}