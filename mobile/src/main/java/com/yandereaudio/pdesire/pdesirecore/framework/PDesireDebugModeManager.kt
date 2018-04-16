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

package com.yandereaudio.pdesire.pdesirecore.framework

import android.app.Activity
import android.provider.Settings
import android.util.Log
import com.yandereaudio.pdesire.yanderecore.framework.os.YanderePropertyControl

object PDesireDebugModeManager {

    fun pdesireDebuggerState () :  Boolean {
        return YanderePropertyControl.getprop("ro.yandereaudio.debug").equals("true")
    }

    fun pdesire_debug (TAG : String, message : String) {
        if (pdesireDebuggerState())
            Log.d(TAG, message)
    }

    fun pdesire_err (TAG : String, message : String) {
        if (pdesireDebuggerState())
            Log.e(TAG, message)
    }

    fun pdesire_bsod (TAG : String, message : String, activity: Activity) {
        if (pdesireDebuggerState())
            Log.e(TAG, message)

        activity.finish()
    }

    fun pdesire_verbose (TAG : String, message : String) {
        if (pdesireDebuggerState())
            Log.v(TAG, message)
    }

    fun pdesire_warn (TAG : String, message : String) {
        if (pdesireDebuggerState())
            Log.w(TAG, message)
    }

    fun pdesire_info (TAG : String, message : String) {
        if (pdesireDebuggerState())
            Log.i(TAG, message)
    }
}