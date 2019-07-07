package com.skyscanner.data.datasource

import com.skyscanner.data.model.SessionInput

interface SessionCreateNetworkDataSource {

    suspend fun getSessionId(input: SessionInput): String
}