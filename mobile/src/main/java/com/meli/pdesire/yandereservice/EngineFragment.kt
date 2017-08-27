package com.meli.pdesire.yandereservice

/**
 * Created by PDesire on 20.05.2017.
 */

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler
import com.meli.pdesire.yandereservice.framework.YandereRootUtility
import projectmeli.yandereaudio.pdesire.R

class EngineFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_engines)
        setHasOptionsMenu(true)

        val yume = findPreference("Yume")
        val meli = findPreference("Meli")


        yume.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.yume_audio))
                    .setMessage(getString(R.string.yume_audio_description))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        YandereCommandHandler.callYumeEngine()
                        YandereCommandHandler.callReboot()
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        // do nothing
                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()

            false
        }

        meli.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.meli_audio))
                    .setMessage(getString(R.string.meli_audio_description))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        YandereCommandHandler.callMeliEngine()
                        YandereCommandHandler.callReboot()
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        // do nothing
                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()

            false
        }
    }
}
