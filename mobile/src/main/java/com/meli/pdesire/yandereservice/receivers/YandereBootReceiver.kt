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

package com.meli.pdesire.yandereservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.meli.pdesire.yandereservice.framework.YandereJobUtility
import com.meli.pdesire.yandereservice.framework.YandereOutputWrapper
import com.meli.pdesire.yandereservice.listeners.YandereWearableApplyListener
import projectmeli.yandereaudio.pdesire.R

/**
 * Created by PDesire on 8/26/17.
 */
class YandereBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent) {
        val wearable = Intent(context, YandereWearableApplyListener::class.java)
        val firebase = Intent(context, YandereWearableApplyListener::class.java)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            YandereJobUtility.scheduleJobWearable(context)
            YandereJobUtility.scheduleJobFirebase(context)
        } else {
            context.startService(wearable)
            context.startService(firebase)
        }
        
        YandereOutputWrapper.addNotification(context, context.getString(R.string.welcome_back), context.getString(R.string.welcome_back_description))

        Log.i("YandereAudio:", "YandereServices started")
    }
}