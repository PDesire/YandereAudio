package com.meli.pdesire.yandereservice.services

/**
 * Created by PDesire on 9/2/17.
 */
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import com.meli.pdesire.yandereservice.framework.YandereJobUtility
import com.meli.pdesire.yandereservice.listeners.YandereWearableApplyListener

/**
 * JobService to be scheduled by the JobScheduler.
 * start another service
 */
class YandereJobSchedulerService : JobService() {

    private val TAG = "SyncService"

    override fun onStartJob(params: JobParameters): Boolean {
        val service = Intent(applicationContext, YandereWearableApplyListener::class.java)
        applicationContext.startService(service)
        YandereJobUtility.scheduleJobWearable(applicationContext)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        return true
    }
}