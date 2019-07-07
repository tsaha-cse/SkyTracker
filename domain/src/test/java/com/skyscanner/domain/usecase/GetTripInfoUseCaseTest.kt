package com.skyscanner.domain.usecase

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.whenever
import com.skyscanner.data.model.FlightInfo
import com.skyscanner.data.repository.FlightInfoRepositoryImpl
import com.skyscanner.data.repository.FlightListingException
import com.skyscanner.data.util.ERROR_CODE_400
import com.skyscanner.domain.base.BaseTest
import com.skyscanner.domain.base.Result
import com.skyscanner.domain.model.Trip
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.io.File

class GetTripInfoUseCaseTest : BaseTest() {

    @Mock
    lateinit var flightInfoRepository: FlightInfoRepositoryImpl

    lateinit var getTripInfoUseCase: GetTripInfoUseCase

    @Before
    fun setUp() {
        getTripInfoUseCase = GetTripInfoUseCase(flightInfoRepository)
    }

    @Test
    fun `Should return trip list`() =
        runBlocking {
            val flightInfo: FlightInfo = Gson().fromJson(response)
            whenever(
                flightInfoRepository.getFlightInfo(aSessionId)
            ).thenReturn(flightInfo)
            val result: Result<List<Trip>> = getTripInfoUseCase(aSessionId)

            assertTrue(result is Result.Success)
            assertEquals(flightInfo.itineraries.size, (result as Result.Success).value.size)
        }

    @Test
    @Throws(FlightListingException::class)
    fun `Should return error`() =
        runBlocking {
            whenever(
                flightInfoRepository.getFlightInfo(aSessionId)
            ).thenThrow(FlightListingException(ERROR_CODE_400, meaning))
            try {
                getTripInfoUseCase(aSessionId)
            } catch (e: Exception) {
                assertTrue(e is FlightListingException)
                assertEquals(ERROR_CODE_400, (e as FlightListingException).errorCode)
            }
            return@runBlocking
        }

    companion object {
        private val response: String =
            File("src/test/resources/response_get_flight.json").readText(Charsets.UTF_8)
        private val aSessionId = "1533acfc-ccd4-4727-827c-c3b766984df0"
        private const val meaning = "Input validation failed"
    }
}