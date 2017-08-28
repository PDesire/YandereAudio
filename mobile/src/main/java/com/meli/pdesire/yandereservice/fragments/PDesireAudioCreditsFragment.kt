package com.meli.pdesire.yandereservice.fragments

/**
 * Created by PDesire on 20.05.2017.
 */


import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.meli.pdesire.yandereservice.framework.YandereOutputWrapper
import projectmeli.yandereaudio.pdesire.R

class PDesireAudioCreditsFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        addPreferencesFromResource(R.xml.pref_other_pdesireaudio)
        setHasOptionsMenu(true)
        val profile_view = findPreference("profile_view_click")
        profile_view.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/member.php?u=6126659"))
            startActivity(intent)
            false
        }

        val credits = findPreference("credits_click")
        credits.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            YandereOutputWrapper.outputMessage(R.string.credits_title, R.string.credits_pdesireaudio, activity)
            false
        }
    }
}
