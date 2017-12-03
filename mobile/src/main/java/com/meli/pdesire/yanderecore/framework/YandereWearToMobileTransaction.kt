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

package com.meli.pdesire.yanderecore.framework

import android.util.Log
import com.meli.pdesire.yanderecore.framework.YandereCommandHandler
import com.meli.pdesire.yanderecore.framework.YanderePDesireAudioAPI


/**
 * Created by PDesire on 8/26/17.
 */
class YandereWearToMobileTransaction(val command: String) {
    fun wearToCommand() {
        if (command.equals("/start_pdesireaudio_enable")) {
            YanderePDesireAudioAPI.callPDesireAudio(1)
        } else if (command.equals("/start_pdesireaudio_disable")) {
            YanderePDesireAudioAPI.callPDesireAudio(0)
        } else if (command.equals("/start_heavybass_enable")) {
            YandereCommandHandler.callHeavybass(true)
        } else if (command.equals("/start_heavybass_disable")) {
            YandereCommandHandler.callHeavybass(false)
        } else if (command.equals("/start_reboot")) {
            YandereCommandHandler.callReboot()
        } else {
            Log.i("YandereAudio:", "Transaction Failed, Command not valid")
        }
    }
}