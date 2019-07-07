package com.skyscanner.skytracker.di

import com.skyscanner.domain.usecase.GetSessionIdUseCase
import com.skyscanner.domain.usecase.GetTripInfoUseCase
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val domainModule = module {
    single<GetSessionIdUseCase>()
    single<GetTripInfoUseCase>()
}