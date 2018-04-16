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
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereOutputWrapper
import projectmeli.yandereaudio.pdesire.R

class CreditsFragment : PreferenceFragment() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        val mOutputWrapper : YandereOutputWrapper? = YandereOutputWrapper(activity)
        super.onCreate(savedInstanceState)


        addPreferencesFromResource(R.xml.pref_other)
        setHasOptionsMenu(true)

        val profile_view = findPreference("profile_view_click")
        profile_view.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/member.php?u=6126659"))
            startActivity(intent)
            false
        }

        val credits = findPreference("credits_click")
        credits.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            mOutputWrapper!!.outputMessage(R.string.credits_title, R.string.credits)
            false
        }
    }
}