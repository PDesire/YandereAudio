package com.meli.pdesire.yanderecore.framework

import android.content.Context

/**
 * Created by pdesire on 26.11.17.
 */

class YandereBootApplyManager(private val mContext: Context) {

    private var PDESIREAUDIO_STATE = false
    private var PDESIREAUDIO_STATIC_STATE = false

    private var GASAIQ_STATE = false
    private var GASAIQ_COMPANDER_STATE = false
    private var GASAIQ_BIQUADS_STATE = false

    private fun obtainPreferences() {
        val PREFS_NAME = "prefs"
        val preference = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val PREF_PDESIREAUDIO = "pdesireaudio"
        PDESIREAUDIO_STATE = preference.getBoolean(PREF_PDESIREAUDIO, false)
        val PREF_PDESIREAUDIO_STATIC = "pdesireaudio_static"
        PDESIREAUDIO_STATIC_STATE = preference.getBoolean(PREF_PDESIREAUDIO_STATIC, false)

        val PREF_GASAIQ = "gasaiq"
        GASAIQ_STATE = preference.getBoolean(PREF_GASAIQ, false)
        val PREF_GASAIQ_COMPANDER = "gasaiq_compander"
        GASAIQ_COMPANDER_STATE = preference.getBoolean(PREF_GASAIQ_COMPANDER, false)
        val PREF_GASAIQ_BIQUADS = "gasaiq_biquads"
        GASAIQ_BIQUADS_STATE = preference.getBoolean(PREF_GASAIQ_BIQUADS, false)
    }

    fun release(): Boolean {
        obtainPreferences()
        try {
            if (PDESIREAUDIO_STATE)
                YanderePDesireAudioAPI.callPDesireAudio(1)

            if (PDESIREAUDIO_STATIC_STATE)
                YanderePDesireAudioAPI.callPDesireAudioStatic(1)

            if (GASAIQ_STATE)
                YandereYunoGasaIQAPI.callGasaIQAudio(1)

            if (GASAIQ_COMPANDER_STATE)
                YandereYunoGasaIQAPI.callAllowCompander(1)

            if (GASAIQ_BIQUADS_STATE)
                YandereYunoGasaIQAPI.callAllowBiQuads(1)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }
}