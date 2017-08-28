package com.meli.pdesire.yandereservice.fragments

/**
 * Created by PDesire on 20.05.2017.
 */

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

class AudioSettingsFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_general)
        setHasOptionsMenu(true)

        val heavybass = findPreference("heavybass_switch")

        heavybass.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callHeavybass(true)
            } else {
                YandereCommandHandler.callHeavybass(false)
            }
            true
        }


        val reboot = findPreference("reboot_click")

        reboot.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.ensure_restart))
                    .setMessage(getString(R.string.ensure_restart_description))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        YandereCommandHandler.callReboot()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, which ->
                        // do nothing
                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
            false
        }
    }
}