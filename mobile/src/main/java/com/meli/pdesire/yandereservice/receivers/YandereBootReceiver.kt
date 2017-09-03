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
        val intent = Intent(context, YandereWearableApplyListener::class.java)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            YandereJobUtility.scheduleJobWearable(context)
        } else {
            context.startService(intent)
        }
        
        YandereOutputWrapper.addNotification(context, context.getString(R.string.welcome_back), context.getString(R.string.welcome_back_description))

        Log.i("YandereAudio:", "YandereWearableApplyListener started")
    }
}