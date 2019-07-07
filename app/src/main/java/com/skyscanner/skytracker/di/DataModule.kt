package com.skyscanner.skytracker.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skyscanner.data.api.SkyScannerService
import com.skyscanner.data.datasource.SessionCreateNetworkDataSource
import com.skyscanner.data.datasource.SessionCreateNetworkDataSourceImpl
import com.skyscanner.data.datasource.SessionInDeviceStorage
import com.skyscanner.data.datasource.SessionSharedPreferenceStorage
import com.skyscanner.data.repository.FlightInfoRepository
import com.skyscanner.data.repository.FlightInfoRepositoryImpl
import com.skyscanner.data.repository.SessionRepository
import com.skyscanner.data.repository.SessionRepositoryImpl
import com.skyscanner.data.util.createOkHttp
import com.skyscanner.data.util.createWebService
import com.skyscanner.skytracker.BuildConfig
import com.skyscanner.skytracker.R
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import org.koin.experimental.builder.singleBy
import retrofit2.converter.gson.GsonConverterFactory

/**
 * init all network config here
 */
val dataModule = module {
    single {
        GsonBuilder().create()
    }
    single {
        createWebService<SkyScannerService>(
            BuildConfig.SKY_SCANNER_BASE_URL,
            createOkHttp(
                level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            ),
            setOf(GsonConverterFactory.create(get() as Gson))
        )
    }

    single {
        with(androidContext()) {
            getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        }
    }

    singleBy<SessionCreateNetworkDataSource, SessionCreateNetworkDataSourceImpl>()
    singleBy<SessionInDeviceStorage, SessionSharedPreferenceStorage>()
    singleBy<SessionRepository, SessionRepositoryImpl>()
    singleBy<FlightInfoRepository, FlightInfoRepositoryImpl>()
}