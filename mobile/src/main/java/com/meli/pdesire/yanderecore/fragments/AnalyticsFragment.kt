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
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import projectmeli.yandereaudio.pdesire.R

class AnalyticsFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_ANALYTICS = "analytics"
    private val PREF_ANALYTICS_FABRIC = "analytics_fabric"

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        addPreferencesFromResource(R.xml.pref_analytics)
        setHasOptionsMenu(true)

        val analytics = findPreference("analytics_switch")

        analytics.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS, false)
                editor.apply()
                activity.finish()
                activity.startActivity(activity.intent)
            } else {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS, true)
                editor.apply()
                activity.finish()
                activity.startActivity(activity.intent)
            }
            true
        }

        val analytics_info = findPreference("analytics_help")

        analytics_info.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.about_firebase_analytics))
                    .setMessage(getString(R.string.about_firebase_analytics_description))
                    .setPositiveButton("Okay") { _, _ ->

                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
            false
        }

        val analytics_fabric = findPreference("analytics_switch_fabric")

        analytics_fabric.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS_FABRIC, false)
                editor.apply()
                activity.finish()
                activity.startActivity(activity.intent)
            } else {
                val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                editor.putBoolean(PREF_ANALYTICS_FABRIC, true)
                editor.apply()
                activity.finish()
                activity.startActivity(activity.intent)
            }
            true
        }

        val analytics_info_fabric = findPreference("analytics_help_fabric")

        analytics_info_fabric.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val messageOutput = AlertDialog.Builder(activity)
            messageOutput.setTitle(getString(R.string.about_fabric_analytics))
                    .setMessage(getString(R.string.about_fabric_analytics_description))
                    .setPositiveButton("Okay") { _, _ ->

                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
            false
        }
    }
}