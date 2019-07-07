package com.skyscanner.data.datasource

import com.skyscanner.data.model.SessionInput

interface SessionInDeviceStorage {
    fun getSessionId(input: SessionInput): String

    fun storeSession(sessionId: String, input: SessionInput)
}