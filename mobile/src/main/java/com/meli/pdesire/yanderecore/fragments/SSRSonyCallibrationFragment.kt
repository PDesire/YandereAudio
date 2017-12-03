/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
 * Copyright (C) 2017-2018 Daniel Vásquez, All rights reserved.
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
import com.meli.pdesire.yanderecore.framework.YandereOutputWrapper
import projectmeli.yandereaudio.pdesire.R

class SSRSonyCallibrationFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_PDESIRE_SSR = "pdesire_ssr"
    private val PREF_HTC_SSR = "htc_ssr"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        val mOutputWrapper : YandereOutputWrapper? = YandereOutputWrapper(activity)
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.pref_sony_ssr)
        setHasOptionsMenu(true)

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usePDesire = preferences.getBoolean(PREF_PDESIRE_SSR, false)
        val useHTC = preferences.getBoolean(PREF_HTC_SSR, false)
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        val htc = findPreference("htc_ssr_switch")

        val pdesire = findPreference("pdesire_ssr_switch")

        if (usePDesire)
            htc.isEnabled = false
        else if (useHTC)
            pdesire.isEnabled = false

        htc.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.copy("/system/Yuno/Sony/SSR/HTC", "surround_sound_rec_AZ.cfg", "/system/etc/surround_sound_3mic")
                pdesire.isEnabled = false
                editor.putBoolean(PREF_HTC_SSR, true)
                editor.apply()
                mOutputWrapper!!.addNotification(getString(R.string.htc_ssr_enabled), getString(R.string.htc_ssr_enabled_description))
            } else {
                setStock()
                pdesire.isEnabled = true
            }
            true
        }

        pdesire.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YandereCommandHandler.copy("/system/Yuno/Sony/SSR/PDesire", "surround_sound_rec_AZ.cfg", "/system/etc/surround_sound_3mic")
                htc.isEnabled = false
                editor.putBoolean(PREF_PDESIRE_SSR, true)
                editor.apply()
                mOutputWrapper!!.addNotification(getString(R.string.pdesire_ssr_enabled), getString(R.string.pdesire_ssr_enabled_description))
            } else {
                setStock()
                htc.isEnabled = true
            }
            true
        }
    }

    private fun setStock() {
        YandereCommandHandler.copy("/system/Yuno/Sony/SSR/stock", "surround_sound_rec_AZ.cfg", "/system/etc/surround_sound_3mic")
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(PREF_HTC_SSR, false)
        editor.apply()
        editor.putBoolean(PREF_PDESIRE_SSR, false)
        editor.apply()
    }
}