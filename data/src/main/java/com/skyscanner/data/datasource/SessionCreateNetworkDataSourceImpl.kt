package com.skyscanner.data.datasource

import com.skyscanner.data.BuildConfig
import com.skyscanner.data.api.SkyScannerService
import com.skyscanner.data.model.SessionInput
import com.skyscanner.data.util.Mockable
import com.skyscanner.data.util.getApiErrorMeaning

/**
 * A class for requesting the API for a new session
 * @return an Session Id
 */
@Mockable
class SessionCreateNetworkDataSourceImpl(
    private val skyScannerService: SkyScannerService
) : SessionCreateNetworkDataSource {

    override suspend fun getSessionId(input: SessionInput): String = with(input) {
        skyScannerService.createSession(
            BuildConfig.SKY_SCANNER_API_KEY,
            locationSchema,
            country,
            currency,
            locale,
            placeOfOrigin,
            placeOfDestination,
            outboundDate,
            inboundDate,
            adults,
            cabinClassType
        )
    }.run {
        return this.raw().header(HEADER_KEY_LOCATION)?.let { header ->
            header.substring(header.lastIndexOf('/') + 1)
        } ?: throw SessionNotCreatedException(code(), getApiErrorMeaning())
    }

    companion object {
        private const val HEADER_KEY_LOCATION = "Location"
    }
}

class SessionNotCreatedException(val errorCode: Int, val meaning: String) : RuntimeException(meaning)