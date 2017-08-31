package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import projectmeli.yandereaudio.pdesire.R

class SecurityFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_SECURE_REPLACE = "secure_replace"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        addPreferencesFromResource(R.xml.pref_security)
        setHasOptionsMenu(true)

        val secure_replace = findPreference("secure_replace_switch")

        secure_replace.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_SECURE_REPLACE, true)
                editor.apply()
                YandereCommandHandler.setSecureReplace(true)
            } else {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_SECURE_REPLACE, false)
                editor.apply()
                YandereCommandHandler.setSecureReplace(false)
            }
            true
        }
    }
}