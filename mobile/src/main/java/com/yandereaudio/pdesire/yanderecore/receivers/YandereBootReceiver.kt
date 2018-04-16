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

package com.yandereaudio.pdesire.yanderecore.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereOutputWrapper
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereBootApplyManager
import projectmeli.yandereaudio.pdesire.R
import com.yandereaudio.pdesire.yanderecore.services.YandereFirebaseMessagingService
import com.yandereaudio.pdesire.yanderecore.services.listeners.YandereWearableApplyListener


/**
 * Created by PDesire on 8/26/17.
 */
class YandereBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {

        val mOutputWrapper : YandereOutputWrapper? = YandereOutputWrapper(context)

        val actionOfIntent = arg1.action
        if (actionOfIntent == CONNECTIVITY_ACTION) {
            Log.d("YandereAudio:", "BroadcastReceiver Connected")
        }

        val firebase = Intent(context, YandereFirebaseMessagingService::class.java)
        val wearable = Intent(context, YandereWearableApplyListener::class.java)

        context.startService(firebase)
        context.startService(wearable)

        val apply_manager = YandereBootApplyManager(context).release()

        if (apply_manager)
            mOutputWrapper!!.addNotification(context.getString(R.string.boot_apply_success_title), context.getString(R.string.boot_apply_success_desc))
        else
            mOutputWrapper!!.addNotification(context.getString(R.string.boot_apply_failed_title), context.getString(R.string.boot_apply_failed_desc))

        mOutputWrapper.addNotification(context.getString(R.string.welcome_back), context.getString(R.string.welcome_back_description))

        Log.d("YandereAudio:", "YandereServices started")
    }
}