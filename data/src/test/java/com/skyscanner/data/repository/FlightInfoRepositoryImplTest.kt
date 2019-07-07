package com.skyscanner.data.repository

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.skyscanner.data.BuildConfig
import com.skyscanner.data.api.SkyScannerService
import com.skyscanner.data.base.BaseTest
import com.skyscanner.data.model.FlightInfo
import com.skyscanner.data.util.ERROR_CODE_410
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.File

class FlightInfoRepositoryImplTest : BaseTest() {

    val skyScannerService: SkyScannerService = mock()

    lateinit var flightInfoRepository: FlightInfoRepositoryImpl

    @Before
    fun setUp() {
        flightInfoRepository = FlightInfoRepositoryImpl(skyScannerService)
    }

    @Test
    fun `Should return flight info`() =
        runBlocking {
            val flightInfo: FlightInfo = Gson().fromJson(response)
            whenever(
                skyScannerService.getFlightInfo(
                    aSessionId,
                    BuildConfig.SKY_SCANNER_API_KEY
                )
            ).thenReturn(Response.success(flightInfo))

            assertEquals(flightInfo, flightInfoRepository.getFlightInfo(aSessionId))
        }

    @Test
    @Throws(FlightListingException::class)
    fun `Should return FlightListingException`() =
        runBlocking {
            whenever(
                skyScannerService.getFlightInfo(
                    aSessionId,
                    BuildConfig.SKY_SCANNER_API_KEY
                )
            ).thenReturn(
                Response.error(
                    ERROR_CODE_410,
                    ResponseBody.create(MediaType.parse("application/json"), "{}")
                )
            )

            try {
                flightInfoRepository.getFlightInfo(aSessionId)
            } catch (e: Exception) {
                assertTrue(e is FlightListingException)
                assertEquals(ERROR_CODE_410, (e as FlightListingException).errorCode)
            }
            return@runBlocking
        }

    companion object {
        private val response: String =
            File("src/test/resources/response_get_flight.json").readText(Charsets.UTF_8)
        private val aSessionId = "1533acfc-ccd4-4727-827c-c3b766984df0"
    }
}