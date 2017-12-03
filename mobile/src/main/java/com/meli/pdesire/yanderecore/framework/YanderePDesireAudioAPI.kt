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

/**
 * Created by PDesire on 8/17/17.
 */
object YanderePDesireAudioAPI {

    fun getPDesireAudio() : String {
        val pdesireaudio_path = "/sys/module/snd_soc_wcd9330/PDesireAudio"
        return if (YandereFileManager.fileCheck(pdesireaudio_path))
            pdesireaudio_path
        else
            ""
    }

    private fun getPDesireAudioStatic() : String {
        val pdesireaudio_path = "/sys/module/snd_soc_wcd9330/pdesireaudio_static_mode"
        return if(YandereFileManager.fileCheck(pdesireaudio_path))
            pdesireaudio_path
        else
            ""

    }

    fun callPDesireAudio (activation : Int) {
        YandereRootUtility().sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudio())
    }

    fun callPDesireAudioStatic (activation : Int) {
        YandereRootUtility().sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudioStatic())
    }
}