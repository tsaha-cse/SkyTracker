package com.skyscanner.skytracker.base

import androidx.multidex.MultiDexApplication
import com.skyscanner.skytracker.di.domainModule
import com.skyscanner.skytracker.di.presentationModule
import org.koin.android.ext.android.startKoin

class TestApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                testDataModule,
                domainModule,
                presentationModule
            )
        )
    }
}