package com.example.opentriviadbdemoapp.di

import android.app.Application
import com.example.opentriviadbdemoapp.MainApplication
import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<MainApplication> {
    fun injectRetrofit(app: Application)
}