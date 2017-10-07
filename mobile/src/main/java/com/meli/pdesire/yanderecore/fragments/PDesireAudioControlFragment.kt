/*
 * Copyright (C) 2017 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the BSD-3-Clause License
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.meli.pdesire.yanderecore.fragments

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
import com.meli.pdesire.yanderecore.framework.YandereCommandHandler
import com.meli.pdesire.yanderecore.framework.YandereOutputWrapper
import com.meli.pdesire.yanderecore.framework.YanderePDesireAudioAPI
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
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        if (!usePDesireAudio) {
            pdesireaudio_static.isEnabled = false
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
                pdesireaudio_static.isEnabled = true
                editor.putBoolean(PREF_PDESIREAUDIO, true)
                editor.apply()
            } else {
                YandereCommandHandler.callPDesireAudio(0)
                YandereOutputWrapper.addNotification(activity, getString(R.string.pdesireaudio_disabled), getString(R.string.pdesireaudio_disabled_description))
                pdesireaudio_static.isEnabled = false
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