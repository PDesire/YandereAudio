package com.meli.pdesire.yandereservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.meli.pdesire.yandereservice.listeners.YandereWearableApplyListener

/**
 * Created by PDesire on 8/26/17.
 */
class YandereBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent) {
        val intent = Intent(context, YandereWearableApplyListener::class.java)
        context.startService(intent)
        Log.i("YandereAudio:", "YandereWearableApplyListener started")
    }
}