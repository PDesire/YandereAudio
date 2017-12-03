/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
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

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yanderecore.framework.YandereYunoGasaIQAPI
import projectmeli.yandereaudio.pdesire.R

class YunoGasaIQControlFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_GASAIQ = "gasaiq"
    private val PREF_GASAIQ_COMPANDER = "gasaiq_compander"
    private val PREF_GASAIQ_BIQUADS = "gasaiq_biquads"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_gasaiq_control)
        setHasOptionsMenu(true)

        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        val gasaiq_audio = findPreference("gasaiq_audio_switch")
        val allow_compander = findPreference("allow_compander_switch")
        val allow_biquads = findPreference("allow_biquads_switch")

        gasaiq_audio.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                editor.putBoolean(PREF_GASAIQ, true)
                editor.apply()
                YandereYunoGasaIQAPI.callGasaIQAudio(1)
            } else {
                editor.putBoolean(PREF_GASAIQ, false)
                editor.apply()
                YandereYunoGasaIQAPI.callGasaIQAudio(0)
            }

            true
        }

        allow_compander.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                editor.putBoolean(PREF_GASAIQ_COMPANDER, true)
                editor.apply()
                YandereYunoGasaIQAPI.callAllowCompander(1)
            } else {
                editor.putBoolean(PREF_GASAIQ_COMPANDER, false)
                editor.apply()
                YandereYunoGasaIQAPI.callAllowCompander(0)
            }

            true
        }

        allow_biquads.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                editor.putBoolean(PREF_GASAIQ_BIQUADS, true)
                editor.apply()
                YandereYunoGasaIQAPI.callAllowBiQuads(1)
            } else {
                editor.putBoolean(PREF_GASAIQ_BIQUADS, false)
                editor.apply()
                YandereYunoGasaIQAPI.callAllowBiQuads(0)
            }

            true
        }
    }
}