package com.skyscanner.presentation.viewmodel

data class LoadingStatus(val isLoading: Boolean, val message: String? = null)

const val LOOKING_FOR_FLIGHTS = "Looking for flights"
const val COLLECTING_INFO = "Collecting info"
const val TOTAL_FLIGHTS_FOUND = "%d flights found"