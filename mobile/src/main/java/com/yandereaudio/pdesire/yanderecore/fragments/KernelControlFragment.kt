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

package com.yandereaudio.pdesire.yanderecore.fragments

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereOutputWrapper
import com.yandereaudio.pdesire.yanderecore.framework.app.YanderePDesireAudioAPI
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereYunoGasaIQAPI
import projectmeli.yandereaudio.pdesire.R

class KernelControlFragment : PreferenceFragment() {

    private val PREFS_NAME = "prefs"
    private val PREF_GASAIQ = "gasaiq"
    private val PREF_GASAIQ_COMPANDER = "gasaiq_compander"
    private val PREF_GASAIQ_BIQUADS = "gasaiq_biquads"
    private val PREF_PDESIREAUDIO = "pdesireaudio"
    private val PREF_ADS = "ads"

    private lateinit var mInterstitialAd: InterstitialAd

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mOutputWrapper : YandereOutputWrapper? = YandereOutputWrapper(activity)

        addPreferencesFromResource(R.xml.pref_gasaiq_control)
        setHasOptionsMenu(true)

        val pdesireaudio_uhqa = findPreference("pdesireaudio_uhqa_switch")
        val pdesireaudio_static = findPreference("pdesireaudio_static_switch")
        val gasaiq_audio = findPreference("gasaiq_audio_switch")
        val allow_compander = findPreference("allow_compander_switch")
        val allow_biquads = findPreference("allow_biquads_switch")

        val preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usePDesireAudio = preferences.getBoolean(PREF_PDESIREAUDIO, false)
        val useAds = preferences.getBoolean(PREF_ADS, true)
        val editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()

        if (useAds) {
            mInterstitialAd = InterstitialAd(activity)
            mInterstitialAd.adUnitId = "ca-app-pub-6207390033733991/6525356424"
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }

        if (!usePDesireAudio) {
            pdesireaudio_static.isEnabled = false
        }

        if (YanderePDesireAudioAPI.getPDesireAudio() != "") {
            mOutputWrapper!!.outputToast(R.string.pdesireaudio_found)
        } else {
            mOutputWrapper!!.outputToast(R.string.pdesireaudio_not_found)
        }

        gasaiq_audio.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                editor.putBoolean(PREF_GASAIQ, true)
                editor.apply()
                YandereYunoGasaIQAPI.callGasaIQAudio(1)
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
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
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
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
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
            } else {
                editor.putBoolean(PREF_GASAIQ_BIQUADS, false)
                editor.apply()
                YandereYunoGasaIQAPI.callAllowBiQuads(0)
            }

            true
        }

        pdesireaudio_uhqa.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YanderePDesireAudioAPI.callPDesireAudio(1)
                mOutputWrapper.addNotification(getString(R.string.pdesireaudio_enabled), getString(R.string.pdesireaudio_enabled_description))
                pdesireaudio_static.isEnabled = true
                editor.putBoolean(PREF_PDESIREAUDIO, true)
                editor.apply()
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
            } else {
                YanderePDesireAudioAPI.callPDesireAudio(0)
                mOutputWrapper.addNotification(getString(R.string.pdesireaudio_disabled), getString(R.string.pdesireaudio_disabled_description))
                pdesireaudio_static.isEnabled = false
                editor.putBoolean(PREF_PDESIREAUDIO, false)
                editor.apply()
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
            }

            true
        }

        pdesireaudio_static.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreference)
                    .isChecked
            if (!switched) {
                YanderePDesireAudioAPI.callPDesireAudioStatic(1)
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
            } else {
                YanderePDesireAudioAPI.callPDesireAudioStatic(0)
                if (mInterstitialAd.isLoaded && useAds) {
                    mInterstitialAd.show()
                }
            }

            true
        }
    }
}