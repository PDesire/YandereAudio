package com.meli.pdesire.yandereservice.framework;

/**
 * Created by PDesire on 9/2/17.
 */

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.meli.pdesire.yandereservice.services.YandereJobSchedulerService;

public class YandereJobUtility {

    // schedule the start of the service every 5 - 30 seconds
    @TargetApi(Build.VERSION_CODES.M)
    public static void scheduleJobWearable(Context context) {
        ComponentName serviceComponent = new ComponentName(context, YandereJobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(500); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        assert jobScheduler != null;
        jobScheduler.schedule(builder.build());
    }
}
