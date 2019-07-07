package com.skyscanner.domain.usecase

import com.skyscanner.data.model.SessionInput
import com.skyscanner.data.repository.SessionRepository
import com.skyscanner.data.util.Mockable
import com.skyscanner.domain.base.BaseUseCase

@Mockable
class GetSessionIdUseCase(
    private val sessionRepository: SessionRepository
) : BaseUseCase<SessionIdUseCaseParam, String>() {

    override suspend fun build(param: SessionIdUseCaseParam): String =
        sessionRepository.getSessionId(param.byForce, param.sessionInput)
}

data class SessionIdUseCaseParam(val byForce: Boolean, val sessionInput: SessionInput)