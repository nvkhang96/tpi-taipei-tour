package com.nvkhang96.tpitaipeitravel

import android.app.Application
import com.nvkhang96.tpitaipeitravel.core.di.coreModules
import com.nvkhang96.tpitaipeitravel.attraction.di.attractionModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TaipeiTravelApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TaipeiTravelApp)
            modules(applicationModules)
        }
    }
}

val applicationModules = module {
    includes(
        coreModules,
        attractionModules,
    )
}