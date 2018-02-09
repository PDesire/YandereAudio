/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the PPL (PDesire Public License) License
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

package com.pdesire.projectcluster.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


/**
 * Created by PDesire on 8/26/17.
 */
abstract class ClusterBootReceiver : BroadcastReceiver() {

    private val PREFS_NAME = "cluster_prefs"
    private val PREF_CLUSTER_SUCCESS = "cluster_success"

    override fun onReceive(context: Context, arg1: Intent) {
        if (context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(PREF_CLUSTER_SUCCESS, false)) {
            onClusterSucceeded(context)

        } else {
            onClusterFailure(context)
        }
    }

    abstract fun onClusterSucceeded (context: Context)
    abstract fun onClusterFailure (context: Context)
}