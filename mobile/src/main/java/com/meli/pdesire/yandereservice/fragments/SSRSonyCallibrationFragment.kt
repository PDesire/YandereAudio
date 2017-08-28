package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import projectmeli.yandereaudio.pdesire.R

class SSRSonyCallibrationFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_sony_ssr)
        setHasOptionsMenu(true)

        val htc = findPreference("htc_ssr_switch")

        htc.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        val pdesire = findPreference("pdesire_clearaudio_switch")

        pdesire.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }
    }
}