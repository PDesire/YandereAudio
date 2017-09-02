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

class SSRSonyCallibrationFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_PDESIRE_SSR = "pdesire_ssr"
    private val PREF_HTC_SSR = "htc_ssr"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_sony_ssr)
        setHasOptionsMenu(true)

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usePDesire = preferences.getBoolean(PREF_PDESIRE_SSR, false)
        val useHTC = preferences.getBoolean(PREF_HTC_SSR, false)

        val htc = findPreference("htc_ssr_switch")

        val pdesire = findPreference("pdesire_ssr_switch")

        if (usePDesire)
            htc.setEnabled(false)
        else if (useHTC)
            pdesire.setEnabled(false)

        htc.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.secure_replace("surround_sound_rec_AZ.cfg", "/system/Yuno/Sony/SSR/HTC", "/system/etc/surround_sound_3mic")
                pdesire.setEnabled(false)
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_HTC_SSR, true)
                editor.apply()
            } else {
                setStock()
                pdesire.setEnabled(true)
            }
            true
        }

        pdesire.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.secure_replace("surround_sound_rec_AZ.cfg", "/system/Yuno/Sony/SSR/PDesire", "/system/etc/surround_sound_3mic")
                htc.setEnabled(false)
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_PDESIRE_SSR, true)
                editor.apply()
                YandereOutputWrapper.addNotification(activity, getString(R.string.pdesire_ssr_enabled), getString(R.string.pdesire_ssr_enabled_description))
            } else {
                setStock()
                htc.setEnabled(true)
            }
            true
        }
    }

    fun setStock() {
        YandereCommandHandler.secure_replace("surround_sound_rec_AZ.cfg", "/system/Yuno/Sony/SSR/stock", "/system/etc/surround_sound_3mic")
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(PREF_HTC_SSR, false)
        editor.apply()
        editor.putBoolean(PREF_PDESIRE_SSR, false)
        editor.apply()
    }
}