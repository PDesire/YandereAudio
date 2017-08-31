package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YanderePackageManager
import projectmeli.yandereaudio.pdesire.R

class HTCCallibrationFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_htc_callibration)
        setHasOptionsMenu(true)

        val vendor = findPreference("vendor_htc_switch")

        vendor.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        val etc = findPreference("etc_htc_switch")

        etc.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        val own = findPreference("own_htc_switch")

        own.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        own.setEnabled(false)
        etc.setEnabled(false)
        vendor.setEnabled(false)
    }
}