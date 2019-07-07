package com.skyscanner.data.repository

import com.skyscanner.data.model.SessionInput

interface SessionRepository {

    suspend fun getSessionId(byForce: Boolean, input: SessionInput): String
}