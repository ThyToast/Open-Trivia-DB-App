package com.example.opentriviadbdemoapp

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.opentriviadbdemoapp.di.DaggerAppComponent
import com.example.opentriviadbdemoapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule))
        }

        toggleDarkMode()
        DaggerAppComponent.builder().build().injectRetrofit(this)
    }

    private fun toggleDarkMode() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = preferences.getBoolean("dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences, s: String ->
            val prefDarkMode = sharedPreferences.getBoolean("dark_mode", false)

            if (prefDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("dark_mode", true).apply()

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("dark_mode", false).apply()
            }
        }
    }
}