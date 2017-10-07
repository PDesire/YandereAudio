package com.meli.pdesire.yanderecore.fragments

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import projectmeli.yandereaudio.pdesire.R

class AnalyticsFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_ANALYTICS = "analytics"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        addPreferencesFromResource(R.xml.pref_analytics)
        setHasOptionsMenu(true)

        val analytics = findPreference("analytics_switch")

        analytics.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS, false)
                editor.apply()
            } else {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS, true)
                editor.apply()
            }
            true
        }

        val analytics_info = findPreference("analytics_help")

        analytics_info.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.about_firebase_analytics))
                    .setMessage(getString(R.string.about_firebase_analytics_description))
                    .setPositiveButton("Okay") { _, _ ->

                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
            false
        }
    }
}