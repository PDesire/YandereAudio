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

package com.meli.pdesire.yanderecore.framework

import android.content.Context
import android.content.pm.PackageManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


/**
 * Created by PDesire on 8/16/17.
 */


object YanderePackageManager {

    // Check if app is installed, this just count for apps which are installed and not their subclasses
    fun isAppInstalled(packageName : String, contexts : Context): Boolean {
        val pm = contexts.packageManager
        val installed: Boolean
        installed = try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        return installed
    }

    // Check if classes and subclasses exist
    private fun packageExists(packageName: String, context: Context): Boolean {
        try {
            val info = context.packageManager.getApplicationInfo(packageName, 0) ?: // No need really to test for null, if the package does not
                    // exist it will really rise an exception. but in case Google
                    // changes the API in the future lets be safe and test it
                    return false

            return true
        } catch (ex: Exception) {
            // If we get here only means the Package does not exist
        }

        return false
    }

    fun closedReleaseTest(context: Context) : Boolean {
        val mFirebaseRemoteConfig: FirebaseRemoteConfig? = FirebaseRemoteConfig.getInstance()

        val closedRelease = mFirebaseRemoteConfig!!.getBoolean("piracy_switch")

        if (!closedRelease)
            return false

        val blockedClassesValue = 4
        val blockedClasses = arrayOf("com.dimonvideo.luckypatcher",
                                            "com.chelpus.lackypatch",
                                            "com.android.vending.billing.InAppBillingService.LACK",
                                            "com.android.vending.billing.InAppBillingService.LOCK")

        var count = 0

        while (count != blockedClassesValue) {
            if (packageExists(blockedClasses[count], context))
                return true

            count++
        }

        return false
    }
}