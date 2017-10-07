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
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yanderecore.framework.YandereCommandHandler
import com.meli.pdesire.yanderecore.framework.YandereOutputWrapper
import projectmeli.yandereaudio.pdesire.R

class AudioSettingsFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_general)
        setHasOptionsMenu(true)

        val heavybass = findPreference("heavybass_switch")
        val reboot = findPreference("reboot_click")
        val pdaec_core = findPreference("pdaec_core_switch")
        val pdaec_audio_effects = findPreference("pdaec_effects_switch")

        heavybass.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callHeavybass(true)
                YandereOutputWrapper.addNotification(activity, getString(R.string.heavybass_enabled), getString(R.string.heavybass_enabled_description))
            } else {
                YandereCommandHandler.callHeavybass(false)
                YandereOutputWrapper.addNotification(activity, getString(R.string.heavybass_disabled), getString(R.string.heavybass_disabled_description))
            }
            true
        }

        pdaec_core.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {

            } else {

            }
            true
        }

        pdaec_core.isEnabled = false

        pdaec_audio_effects.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.callPDAECMagic()
            } else {
                YandereCommandHandler.removePDAECMagic()
            }
            true
        }


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