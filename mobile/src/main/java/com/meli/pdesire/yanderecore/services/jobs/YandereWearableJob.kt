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

package com.meli.pdesire.yanderecore.services.jobs

import android.content.Intent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.meli.pdesire.yanderecore.framework.YandereActivityManager
import com.meli.pdesire.yanderecore.services.listeners.YandereWearableApplyListener

/**
 * Created by pdesire on 24.11.17.
 */
class YandereWearableJob : JobService() {

    override fun onStartJob(job: JobParameters): Boolean {
        val service = Intent(this, YandereWearableApplyListener::class.java)

        if (YandereActivityManager.checkServiceRunning(YandereWearableApplyListener::class.java.name, this))
            return true

        startService(service)
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        val service = Intent(this, YandereWearableApplyListener::class.java)

        if (!YandereActivityManager.checkServiceRunning(YandereWearableApplyListener::class.java.name, this))
            return true

        stopService(service)
        return false
    }
}