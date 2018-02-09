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

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import projectmeli.yandereaudio.pdesire.MainActivity
import projectmeli.yandereaudio.pdesire.R



/**
 * Created by PDesire on 8/16/17.
 */
class YandereOutputWrapper(private val mContext : Context) {

    // If you use R strings, use that
    @Override
    fun outputMessage (title : Int, message : Int) {
        val messageOutput = AlertDialog.Builder(mContext)
        messageOutput.setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .create()
                .show()
    }

    @Override
    fun outputMessage (title : String, message : String) {
        val messageOutput = AlertDialog.Builder(mContext)
        messageOutput.setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .create()
                .show()
    }

    @Override
    fun outputToast (message : Int) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    @Override
    fun outputToast (message : String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun addNotification(title: String, message: String) {

        val i = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, i, 0)
        val picture = BitmapFactory.decodeResource(mContext.resources,
                R.mipmap.ic_launcher)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("YandereMessage", "YandereAudioMessage", NotificationManager.IMPORTANCE_DEFAULT)
            val nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(notificationChannel)
            val b = NotificationCompat.Builder(mContext, "YandereMessage")
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_yandere_black_24dp)
                    .setTicker(message)
                    .setContentTitle(title + " ^-^")
                    .setContentText("Master, " + message + " ^~^")
                    .setChannelId("YandereMessage")
                    .setLargeIcon(picture)
                    .setContentIntent(pendingIntent)
            nm.notify(1, b.build())
        } else{
            val b = NotificationCompat.Builder(mContext, "OutputWrapperNotification")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_yandere_black_24dp)
                    .setTicker(message)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentTitle(title + " ^-^")
                    .setContentText("Master, " + message + " ^~^")
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(picture)
                    .setContentInfo("INFO")
            val nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, b.build())
        }
    }
}