package com.skyscanner.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.skyscanner.data.base.BaseTest
import com.skyscanner.data.datasource.InvalidSessionException
import com.skyscanner.data.datasource.SessionCreateNetworkDataSourceImpl
import com.skyscanner.data.datasource.SessionSharedPreferenceStorage
import com.skyscanner.data.model.SessionInput
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SessionRepositoryImplTest : BaseTest() {

    @Mock
    lateinit var sessionInDeviceStorage: SessionSharedPreferenceStorage

    @Mock
    lateinit var sessionCreateNetworkDataSource: SessionCreateNetworkDataSourceImpl

    lateinit var sessionRepositoryImpl: SessionRepositoryImpl

    @Before
    fun setup() {
        sessionRepositoryImpl =
            SessionRepositoryImpl(sessionInDeviceStorage, sessionCreateNetworkDataSource)
    }

    @Test
    fun `Session Id Should Return From Network Source`() =
        runBlocking {
            whenever(sessionInDeviceStorage.getSessionId(sessionInput)).thenThrow(
                InvalidSessionException()
            )
            whenever(sessionCreateNetworkDataSource.getSessionId(sessionInput)).thenReturn(
                aSessionId
            )
            assertEquals(aSessionId, sessionRepositoryImpl.getSessionId(false, sessionInput))
            verify(sessionInDeviceStorage).storeSession(aSessionId, sessionInput)
        }

    @Test
    fun `Session Id Should Return From Device Storage`() =
        runBlocking {
            whenever(sessionInDeviceStorage.getSessionId(sessionInput)).thenReturn(
                aSessionId
            )
            assertEquals(aSessionId, sessionRepositoryImpl.getSessionId(false, sessionInput))
            verifyNoMoreInteractions(sessionCreateNetworkDataSource)
        }

    @Test
    fun `Session Id Should Return From Network Source When By Force`() =
        runBlocking {
            whenever(sessionCreateNetworkDataSource.getSessionId(sessionInput)).thenReturn(
                aSessionId
            )
            assertEquals(aSessionId, sessionRepositoryImpl.getSessionId(true, sessionInput))
            verify(sessionInDeviceStorage).storeSession(aSessionId, sessionInput)
        }

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
            "2019-07-08",
            "2019-07-00",
            1,
            "economy"
        )

        private val aSessionId = "1533acfc-ccd4-4727-827c-c3b766984df0"
    }
}