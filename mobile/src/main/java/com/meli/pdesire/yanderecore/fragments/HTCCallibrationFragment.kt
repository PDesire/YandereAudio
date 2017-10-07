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

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.meli.pdesire.yanderecore.framework.YandereCommandHandler
import projectmeli.yandereaudio.pdesire.R

class HTCCallibrationFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_HTC_FIRST_RUN = "htcfirstrun"
    private val PREF_HTC_ETC = "htcetc"
    private val PREF_HTC_VENDOR = "htcvendor"
    private val PREF_HTC_OWN = "htcown"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_htc_callibration)
        setHasOptionsMenu(true)

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val useVendor = preferences.getBoolean(PREF_HTC_VENDOR, false)
        val useEtc = preferences.getBoolean(PREF_HTC_ETC, false)
        val useOwn = preferences.getBoolean(PREF_HTC_OWN, false)
        val isFirstRun = preferences.getBoolean(PREF_HTC_FIRST_RUN, false)
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        if (!isFirstRun) {
            YandereCommandHandler.copy("/system/etc", "htc_audio_effects.conf", "/system/Yuno/HTCstock")
            editor.putBoolean(PREF_HTC_FIRST_RUN, true)
            editor.apply()
        }

        val vendor = findPreference("vendor_htc_switch")
        val etc = findPreference("etc_htc_switch")
        val own = findPreference("own_htc_switch")

        if (useVendor) {
            etc.isEnabled = false
            own.isEnabled = false
        } else if (useEtc) {
            vendor.isEnabled = false
            own.isEnabled = false
        } else if (useOwn) {
            vendor.isEnabled = false
            etc.isEnabled = false
        }

        vendor.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.vendorizeHTC()
                editor.putBoolean(PREF_HTC_VENDOR, true)
                editor.apply()
                etc.isEnabled = false
                own.isEnabled = false
            } else {
                YandereCommandHandler.stockingHTC()
                editor.putBoolean(PREF_HTC_VENDOR, false)
                editor.apply()
                etc.isEnabled = true
                own.isEnabled = true
            }
            true
        }

        etc.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.etcizeHTC()
                editor.putBoolean(PREF_HTC_ETC, true)
                editor.apply()
                vendor.isEnabled = false
                own.isEnabled = false
            } else {
                YandereCommandHandler.stockingHTC()
                editor.putBoolean(PREF_HTC_ETC, false)
                editor.apply()
                vendor.isEnabled = true
                own.isEnabled = true
            }
            true
        }

        own.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.owningHTC()
                editor.putBoolean(PREF_HTC_OWN, true)
                editor.apply()
                vendor.isEnabled = false
                etc.isEnabled = false
            } else {
                YandereCommandHandler.stockingHTC()
                editor.putBoolean(PREF_HTC_OWN, false)
                editor.apply()
                vendor.isEnabled = true
                etc.isEnabled = true
            }
            true
        }
    }
}