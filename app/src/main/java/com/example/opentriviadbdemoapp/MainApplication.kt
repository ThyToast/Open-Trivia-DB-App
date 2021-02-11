package com.example.opentriviadbdemoapp

import android.app.Application
import android.content.res.Resources
import com.example.opentriviadbdemoapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

var resources: Resources? = null

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule))
        }

        var resources = resources

    }
}