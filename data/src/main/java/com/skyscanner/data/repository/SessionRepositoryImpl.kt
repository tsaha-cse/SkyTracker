package com.skyscanner.data.repository

import com.skyscanner.data.datasource.InvalidSessionException
import com.skyscanner.data.datasource.SessionCreateNetworkDataSource
import com.skyscanner.data.datasource.SessionInDeviceStorage
import com.skyscanner.data.model.SessionInput

/**
 * Repository for retrieving sessionId from data sources
 */
class SessionRepositoryImpl(
    private val sessionInDeviceStorage: SessionInDeviceStorage,
    private val sessionCreateNetworkDataSource: SessionCreateNetworkDataSource
) : SessionRepository {

    /**
     * get sessionId from data sources
     * @param byForce true to generate the sessionId from
     * network and save it to in device. false to return from in device storage
     * @param SessionInput input params to pass to the API service
     */
    override suspend fun getSessionId(byForce: Boolean, input: SessionInput): String =
        if (byForce) {
            sessionCreateNetworkDataSource.getSessionId(input).also {
                sessionInDeviceStorage.storeSession(it, input)
            }
        } else {
            try {
                sessionInDeviceStorage.getSessionId(input)
            } catch (e: InvalidSessionException) {
                sessionCreateNetworkDataSource.getSessionId(input).also {
                    sessionInDeviceStorage.storeSession(it, input)
                }
            }
        }
}