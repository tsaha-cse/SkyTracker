package com.skyscanner.data.repository

import com.skyscanner.data.BuildConfig
import com.skyscanner.data.api.SkyScannerService
import com.skyscanner.data.model.FlightInfo
import com.skyscanner.data.util.Mockable
import com.skyscanner.data.util.getApiErrorMeaning

/**
 * Repository for getting the flight list
 */
@Mockable
class FlightInfoRepositoryImpl(
    private val skyScannerService: SkyScannerService
) : FlightInfoRepository {

    override suspend fun getFlightInfo(sessionId: String): FlightInfo =
        with(skyScannerService.getFlightInfo(sessionId, BuildConfig.SKY_SCANNER_API_KEY)) {
            return body() ?: throw FlightListingException(code(), getApiErrorMeaning())
        }
}

class FlightListingException(val errorCode: Int, meaning: String) : RuntimeException(meaning)