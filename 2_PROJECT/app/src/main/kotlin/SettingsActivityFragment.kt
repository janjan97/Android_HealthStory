package com.example.kotlin

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.HealthStory.R


class SettingsActivityFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}