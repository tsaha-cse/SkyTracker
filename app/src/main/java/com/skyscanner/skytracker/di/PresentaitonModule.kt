package com.skyscanner.skytracker.di

import com.skyscanner.presentation.viewmodel.FlightListViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val presentationModule = module {
    viewModel<FlightListViewModel>()
}