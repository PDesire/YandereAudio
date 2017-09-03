package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import com.meli.pdesire.yandereservice.framework.YandereOutputWrapper
import projectmeli.yandereaudio.pdesire.R

class SSECallibrationFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_PDESIRE_CLEARAUDIO = "pdesire_clearaudio"
    private val PREF_TREBLE_CLEARAUDIO= "treble_clearaudio"
    private val PREF_BASS_CLEARAUDIO= "bass_clearaudio"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_sony_sse)
        setHasOptionsMenu(true)

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usePDesire = preferences.getBoolean(PREF_PDESIRE_CLEARAUDIO, false)
        val useBass = preferences.getBoolean(PREF_BASS_CLEARAUDIO, false)
        val useTreble = preferences.getBoolean(PREF_TREBLE_CLEARAUDIO, false)
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        val treble = findPreference("treble_clearaudio_switch")
        val bass = findPreference("bass_clearaudio_switch")
        val pdesire = findPreference("pdesire_clearaudio_switch")

        if (usePDesire) {
            treble.isEnabled = false
            bass.isEnabled = false
        } else if (useBass) {
            treble.isEnabled = false
            pdesire.isEnabled = false
        } else if (useTreble) {
            pdesire.isEnabled = false
            bass.isEnabled = false
        }

        treble.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.secure_replace("effect_params.data", "/system/Yuno/Sony/ClearAudio/Treble", "/system/etc/sony_effect")
                pdesire.isEnabled = false
                bass.isEnabled = false
                editor.putBoolean(PREF_TREBLE_CLEARAUDIO, true)
                editor.apply()
                YandereOutputWrapper.addNotification(activity, getString(R.string.treble_clearaudio_enabled), getString(R.string.treble_clearaudio_enabled_description))
            } else {
                setStock()
                pdesire.isEnabled = true
                bass.isEnabled = true
            }
            true
        }

        bass.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.secure_replace("effect_params.data", "/system/Yuno/Sony/ClearAudio/Bass", "/system/etc/sony_effect")
                treble.isEnabled = false
                pdesire.isEnabled = false
                editor.putBoolean(PREF_BASS_CLEARAUDIO, true)
                editor.apply()
                YandereOutputWrapper.addNotification(activity, getString(R.string.bass_clearaudio_enabled), getString(R.string.bass_clearaudio_enabled_description))
            } else {
                setStock()
                treble.isEnabled = true
                pdesire.isEnabled = true
            }
            true
        }

        pdesire.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.secure_replace("effect_params.data", "/system/Yuno/Sony/ClearAudio/PDesire", "/system/etc/sony_effect")
                treble.isEnabled = false
                bass.isEnabled = false
                editor.putBoolean(PREF_PDESIRE_CLEARAUDIO, true)
                editor.apply()
                YandereOutputWrapper.addNotification(activity, getString(R.string.pdesire_clearaudio_enabled), getString(R.string.pdesire_clearaudio_enabled_description))
            } else {
                setStock()
                treble.isEnabled = true
                bass.isEnabled = true
            }
            true
        }
    }

    fun setStock() {
        YandereCommandHandler.secure_replace("effect_params.data", "/system/Yuno/Sony/ClearAudio/stock", "/system/etc/sony_effect")
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(PREF_PDESIRE_CLEARAUDIO, false)
        editor.apply()
        editor.putBoolean(PREF_BASS_CLEARAUDIO, false)
        editor.apply()
        editor.putBoolean(PREF_TREBLE_CLEARAUDIO, false)
        editor.apply()
    }
}