package com.mkokic.ingou

import android.app.Application
import com.mkokic.ingou.di.applicationModule
import com.mkokic.ingou.di.ingouModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IngouApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IngouApplication)
            modules(listOf(applicationModule, ingouModule))
        }
    }
}