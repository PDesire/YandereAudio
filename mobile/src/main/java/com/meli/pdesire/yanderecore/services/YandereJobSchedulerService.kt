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

package com.meli.pdesire.yanderecore.services

/**
 * Created by PDesire on 9/2/17.
 */
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import com.meli.pdesire.yanderecore.framework.YandereJobUtility
import com.meli.pdesire.yanderecore.listeners.YandereWearableApplyListener

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