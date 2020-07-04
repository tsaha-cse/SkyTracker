package com.skyscanner.presentation.viewmodel

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.skyscanner.domain.base.Reason
import com.skyscanner.domain.base.Result
import com.skyscanner.domain.model.Trip
import com.skyscanner.domain.usecase.GetSessionIdUseCase
import com.skyscanner.domain.usecase.GetTripInfoUseCase
import com.skyscanner.presentation.base.BaseTest
import com.skyscanner.presentation.base.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FlightListViewModelTest : BaseTest() {

    lateinit var getSessionIdUseCase: GetSessionIdUseCase

    lateinit var getTripInfoUseCase: GetTripInfoUseCase

    lateinit var flightListViewModel: FlightListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Check Load Data`() {
        getSessionIdUseCase = mock {
            onBlocking { invoke(any()) } doReturn Result.Success(aSessionId)
        }
        getTripInfoUseCase = mock {
            onBlocking { invoke(aSessionId) } doReturn Result.Success(flightList)
        }
        flightListViewModel = FlightListViewModel(getSessionIdUseCase, getTripInfoUseCase)
        runBlocking {
            flightListViewModel.loadData()
        }
        flightListViewModel.getTripListDispatcher().observeOnce {
            assertEquals(flightList, it)
        }
        flightListViewModel.getLoadingStatus().observeOnce {
            assertEquals(false, it.isLoading)
            assertEquals(String.format(TOTAL_FLIGHTS_FOUND, flightList.size), it.message)
        }
    }

    @Test
    fun `Should not return flight list`() {
        getSessionIdUseCase = mock {
            onBlocking { invoke(any()) } doReturn Result.Failure(Reason(exception = RuntimeException()))
        }
        getTripInfoUseCase = mock {
            onBlocking { invoke(aSessionId) } doReturn Result.Success(flightList)
        }
        flightListViewModel = FlightListViewModel(getSessionIdUseCase, getTripInfoUseCase)

        runBlocking {
            flightListViewModel.loadData()
        }
        flightListViewModel.getTripListDispatcher().observeOnce {
            assertEquals(emptyList<Trip>(), it)
        }
        flightListViewModel.getLoadingStatus().observeOnce {
            assertEquals(false, it.isLoading)
        }
    }

    companion object {
        private val aSessionId = "1533acfc-ccd4-4727-827c-c3b766984df0"
        private val dummyTrip = Trip(
            null,
            null,
            "$",
            250.00,
            null,
            10.0
        )

        private val flightList = listOf(dummyTrip, dummyTrip)
    }
}