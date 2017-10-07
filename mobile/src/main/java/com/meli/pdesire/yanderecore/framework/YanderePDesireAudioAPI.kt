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

package com.meli.pdesire.yanderecore.framework

/**
 * Created by PDesire on 8/17/17.
 */
object YanderePDesireAudioAPI {

    fun getPDesireAudio() : String {
        var pdesireaudio_path : String = ""

        val apiLists : Int = 4
        val pdesireaudio_arrayed_paths = arrayOf("/sys/module/snd_soc_wcd9330/uhqa_mode_pdesireaudio",
                                                "/sys/module/snd_soc_wcd9330/PDesireAudio",
                                                "/sys/module/snd_soc_wcd9320/uhqa_mode_pdesireaudio",
                                                "/sys/module/snd_soc_wcd9320/PDesireAudio")

        var count : Int = 0
        while (count != apiLists) {
            if(YandereFileManager.fileCheck(pdesireaudio_arrayed_paths[count])) {
                pdesireaudio_path = pdesireaudio_arrayed_paths[count]
                break
            }
            count++
        }

        return pdesireaudio_path
    }

    fun getPDesireAudioStatic() : String{
        var pdesireaudio_path : String = ""

        val apiLists : Int = 2
        val pdesireaudio_arrayed_paths = arrayOf("/sys/module/snd_soc_wcd9320/pdesireaudio_static_mode",
                                                        "/sys/module/snd_soc_wcd9330/pdesireaudio_static_mode")

        var count : Int = 0
        while (count != apiLists) {
            if(YandereFileManager.fileCheck(pdesireaudio_arrayed_paths[count])) {
                pdesireaudio_path = pdesireaudio_arrayed_paths[count]
                break
            }
            count++
        }

        return pdesireaudio_path
    }
}