package com.skyscanner.data.util

import retrofit2.Response

const val ERROR_CODE_400 = 400
const val ERROR_CODE_410 = 410
const val ERROR_CODE_429 = 429
const val ERROR_CODE_403 = 403

/**
 * Function to map the error messages with error code
 * Messages are collected from API documentation
 */
fun <T> Response<T>.getApiErrorMeaning(): String =
    when (this.code()) {
        ERROR_CODE_400 -> "Input validation failed"
        ERROR_CODE_410 -> "The session has expired. A new session must be created."
        ERROR_CODE_429 -> "There have been too many requests in the last minute."
        ERROR_CODE_403 -> "The API Key was not supplied, or it was invalid, " +
                "or it is not authorized to access the service"
        else -> "Undefined error"
    }