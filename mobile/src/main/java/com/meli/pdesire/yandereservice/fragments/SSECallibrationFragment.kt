package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import projectmeli.yandereaudio.pdesire.R

class SSECallibrationFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_sony_sse)
        setHasOptionsMenu(true)

        val treble = findPreference("treble_clearaudio_switch")

        treble.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        val bass = findPreference("bass_clearaudio_switch")

        bass.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
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