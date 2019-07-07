package com.skyscanner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skyscanner.data.model.SessionInput
import com.skyscanner.data.repository.FlightListingException
import com.skyscanner.data.util.ERROR_CODE_410
import com.skyscanner.domain.base.Result
import com.skyscanner.domain.model.Trip
import com.skyscanner.domain.usecase.GetSessionIdUseCase
import com.skyscanner.domain.usecase.GetTripInfoUseCase
import com.skyscanner.domain.usecase.SessionIdUseCaseParam
import com.skyscanner.presentation.base.BaseViewModel
import com.skyscanner.presentation.ext.getFollowingDayOfMondayInString
import com.skyscanner.presentation.ext.getNextMondayInString
import kotlinx.coroutines.launch
import java.util.*

class FlightListViewModel(
    private val getSessionIdUseCase: GetSessionIdUseCase,
    private val getTripInfoUseCase: GetTripInfoUseCase
) : BaseViewModel() {

    fun getInput(): SessionInput = sessionInput

    private val loadingStatus: LiveData<LoadingStatus> =
        MutableLiveData<LoadingStatus>().apply {
            value = LoadingStatus(false)
        }

    /**
     * @return the LiveData to observe the current
     * loading status by the observer (UI)
     */
    fun getLoadingStatus(): LiveData<LoadingStatus> = loadingStatus

    private val tripListDispatcher: LiveData<List<Trip>> =
        MutableLiveData<List<Trip>>().apply {
            value = emptyList()
        }

    /**
     * @return the LiveData to observe the List<Trip> update
     * by the observer (UI).
     */
    fun getTripListDispatcher(): LiveData<List<Trip>> = tripListDispatcher

    /**
     * function to be called from UI to propagate the loading process.
     * @param byForceSessionUpdate true when you need to refresh the session
     * helpful when there is an error and want to reload with a fresh session key
     */
    fun loadData(byForceSessionUpdate: Boolean = false) =
        launch {
            loadingStatus.postVal(LoadingStatus(true, LOOKING_FOR_FLIGHTS))
            with(getSessionId(byForceSessionUpdate)) {
                when (this) {
                    is Result.Success -> {
                        loadFlightInfo(value)
                    }

                    is Result.Failure -> {
                        loadingStatus.postVal(LoadingStatus(false))
                    }
                }
            }
        }

    private suspend fun getSessionId(byForce: Boolean) =
        getSessionIdUseCase(SessionIdUseCaseParam(byForce, sessionInput))

    private fun loadFlightInfo(sessionId: String) =
        launch {
            loadingStatus.postVal(LoadingStatus(true, COLLECTING_INFO))
            with(getFlightInfo(sessionId)) {
                when (this) {
                    is Result.Success -> {
                        loadingStatus.postVal(
                            LoadingStatus(
                                false,
                                String.format(TOTAL_FLIGHTS_FOUND, value.size)
                            )
                        )
                        tripListDispatcher.postVal(value)
                    }

                    is Result.Failure -> {
                        loadingStatus.postVal(LoadingStatus(false))
                        if ((reason.exception as? FlightListingException)
                                ?.errorCode == ERROR_CODE_410
                        ) {
                            loadingStatus.postVal(LoadingStatus(false))
                        }
                    }
                }
            }
        }

    private suspend fun getFlightInfo(sessionId: String) = getTripInfoUseCase(sessionId)

    companion object {
        private val sessionInput = SessionInput(
            "sky",
            "UK",
            "GBP",
            "en-GB",
            "EDI",
            "EDI-sky",
            "LOND",
            "LOND-sky",
            Calendar.getInstance().getNextMondayInString(),
            Calendar.getInstance().getFollowingDayOfMondayInString(),
            1,
            "economy"
        )
    }
}