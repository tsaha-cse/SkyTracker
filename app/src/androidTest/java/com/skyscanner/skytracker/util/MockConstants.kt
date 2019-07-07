package com.skyscanner.skytracker.util

const val URL = "http://127.0.0.1"
const val PORT = 8080

object APIPaths {
    const val CREATE_SESSION = "/pricing/v1.0"
    const val GET_FLIGHT_INFO = "/pricing/v1.0/1533acfc-ccd4-4727-827c-c3b766984df0"
}

object MockFiles {
    const val RESPONSE_CREATE_SESSION = "response_create_session.json"
    const val RESPONSE_GET_FLIGHT_INFO = "response_get_flight.json"
}

object Header {
    const val HEADER_KEY = "Location"
    const val HEADER_VALUE =
        "http://partners.api.skyscanner.net/apiservices/pricing/uk2/v1.0/1533acfc-ccd4-4727-827c-c3b766984df0"
}