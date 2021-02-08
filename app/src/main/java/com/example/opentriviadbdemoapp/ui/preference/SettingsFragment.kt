package com.example.opentriviadbdemoapp.ui.preference

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.opentriviadbdemoapp.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}