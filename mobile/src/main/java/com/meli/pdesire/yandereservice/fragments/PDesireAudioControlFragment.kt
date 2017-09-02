package com.meli.pdesire.yandereservice.fragments

/**
 * Created by PDesire on 20.05.2017.
 */

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import com.meli.pdesire.yandereservice.framework.YandereOutputWrapper
import com.meli.pdesire.yandereservice.framework.YanderePDesireAudioAPI
import projectmeli.yandereaudio.pdesire.R

class PDesireAudioControlFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_PDESIREAUDIO = "pdesireaudio"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_pdesireaudio_control)
        setHasOptionsMenu(true)

        val pdesireaudio_uhqa = findPreference("pdesireaudio_uhqa_switch")
        val pdesireaudio_static = findPreference("pdesireaudio_static_switch")

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usePDesireAudio = preferences.getBoolean(PREF_PDESIREAUDIO, false)

        if (!usePDesireAudio) {
            pdesireaudio_static.setEnabled(false)
        }

        if (!YanderePDesireAudioAPI.getPDesireAudio().equals("")) {
            YandereOutputWrapper.outputToast(R.string.pdesireaudio_found, activity)
        } else {
            YandereOutputWrapper.outputToast(R.string.pdesireaudio_not_found, activity)
        }

        pdesireaudio_uhqa.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callPDesireAudio(1)
                YandereOutputWrapper.addNotification(activity, getString(R.string.pdesireaudio_enabled), getString(R.string.pdesireaudio_enabled_description))
                pdesireaudio_static.setEnabled(true)
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_PDESIREAUDIO, true)
                editor.apply()
            } else {
                YandereCommandHandler.callPDesireAudio(0)
                YandereOutputWrapper.addNotification(activity, getString(R.string.pdesireaudio_disabled), getString(R.string.pdesireaudio_disabled_description))
                pdesireaudio_static.setEnabled(false)
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_PDESIREAUDIO, false)
                editor.apply()
            }

            true
        }

        pdesireaudio_static.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callPDesireAudioStatic(1)
            } else {
                YandereCommandHandler.callPDesireAudioStatic(0)
            }

            true
        }
    }
}