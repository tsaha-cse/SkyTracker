package com.skyscanner.domain.usecase

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.skyscanner.data.model.SessionInput
import com.skyscanner.data.repository.SessionRepository
import com.skyscanner.domain.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetSessionIdUseCaseTest : BaseTest() {

    @Mock
    lateinit var sessionRepository: SessionRepository

    lateinit var getSessionIdUseCase: GetSessionIdUseCase

    private val byForceParamCaptor = argumentCaptor<Boolean>()

    private val sessionInputParamCaptor = argumentCaptor<SessionInput>()

    @Before
    fun setUp() {
        getSessionIdUseCase = GetSessionIdUseCase(sessionRepository)
    }

    @Test
    fun `build method is called with current parameter`() {
        runBlocking {
            getSessionIdUseCase(sessionIdUseCaseParam)
            verify(sessionRepository).getSessionId(
                byForceParamCaptor.capture(),
                sessionInputParamCaptor.capture()
            )
            assertFalse(byForceParamCaptor.firstValue)
            assertEquals(
                sessionIdUseCaseParam.sessionInput,
                sessionInputParamCaptor.firstValue
            )
        }
    }

    companion object {
        private val aSessionId = "1533acfc-ccd4-4727-827c-c3b766984df0"

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
            "2019-07-08",
            1,
            "economy"
        )

        private val sessionIdUseCaseParam = SessionIdUseCaseParam(false, sessionInput)
    }
}