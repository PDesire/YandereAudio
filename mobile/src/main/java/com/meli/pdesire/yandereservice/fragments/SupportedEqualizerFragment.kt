package com.meli.pdesire.yandereservice.fragments

import android.annotation.TargetApi
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.preference.SwitchPreference
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import com.meli.pdesire.yandereservice.framework.YanderePackageManager
import projectmeli.yandereaudio.pdesire.R

class SupportedEqualizerFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_supported_eq)
        setHasOptionsMenu(true)

        val isV4AInstalled = YanderePackageManager.isAppInstalled("com.audlabs.viperfx", activity)

        val screen = preferenceScreen
        val v4a = findPreference("v4a") as PreferenceScreen

        if (!isV4AInstalled) {
            screen.removePreference(v4a)
        }
    }
}