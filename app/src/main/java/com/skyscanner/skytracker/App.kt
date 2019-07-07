package com.skyscanner.skytracker

import androidx.multidex.MultiDexApplication
import com.skyscanner.skytracker.di.dataModule
import com.skyscanner.skytracker.di.domainModule
import com.skyscanner.skytracker.di.presentationModule
import org.koin.android.ext.android.startKoin


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                dataModule,
                domainModule,
                presentationModule
            )
        )
    }
}