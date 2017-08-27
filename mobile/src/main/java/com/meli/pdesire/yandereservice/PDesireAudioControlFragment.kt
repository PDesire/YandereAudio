package com.meli.pdesire.yandereservice

/**
 * Created by PDesire on 20.05.2017.
 */

import android.annotation.TargetApi
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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_pdesireaudio_control)
        setHasOptionsMenu(true)

        if (!YanderePDesireAudioAPI.getPDesireAudio().equals(""))
            YandereOutputWrapper.outputToast(R.string.pdesireaudio_found, activity)
        else
            YandereOutputWrapper.outputToast(R.string.pdesireaudio_not_found, activity)

        val pdesireaudio_uhqa = findPreference("pdesireaudio_uhqa_switch")

        pdesireaudio_uhqa.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callPDesireAudio(1)
            } else {
                YandereCommandHandler.callPDesireAudio(0)
            }

            true
        }


        val pdesireaudio_static = findPreference("pdesireaudio_static_switch")

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