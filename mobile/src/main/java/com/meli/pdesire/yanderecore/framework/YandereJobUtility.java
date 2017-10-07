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

package com.meli.pdesire.yanderecore.framework;

/**
 * Created by PDesire on 9/2/17.
 */

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.meli.pdesire.yanderecore.services.YandereFirebaseJobSchedulerService;
import com.meli.pdesire.yanderecore.services.YandereJobSchedulerService;

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

    @TargetApi(Build.VERSION_CODES.M)
    public static void scheduleJobFirebase(Context context) {
        ComponentName serviceComponent = new ComponentName(context, YandereFirebaseJobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(10000); // wait at least
        builder.setOverrideDeadline(50000); // maximum delay
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        assert jobScheduler != null;
        jobScheduler.schedule(builder.build());
    }
}
