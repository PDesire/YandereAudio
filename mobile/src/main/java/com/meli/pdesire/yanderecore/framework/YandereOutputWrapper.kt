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

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import projectmeli.yandereaudio.pdesire.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import projectmeli.yandereaudio.pdesire.MainActivity


@SuppressLint("Registered")
/**
 * Created by PDesire on 8/16/17.
 */
object YandereOutputWrapper : Activity() {

    // If you use R strings, use that
    @Override
    fun outputMessage (title : Int, message : Int, context : Context) {
        val messageOutput = AlertDialog.Builder(context)
        messageOutput.setTitle(title)
        .setMessage(message).setIcon(R.mipmap.ic_launcher)
        .create()
        .show()
    }

    @Override
    fun outputMessage (title : String, message : String, context : Context) {
        val messageOutput = AlertDialog.Builder(context)
        messageOutput.setTitle(title)
                .setMessage(message).setIcon(R.mipmap.ic_launcher)
                .create()
                .show()
    }

    @Override
    fun outputToast (message : Int, context : Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @Override
    fun outputToast (message : String, context : Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun addNotification(context: Context, title: String, message: String) {

        val i = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val b = NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_yandere_black_24dp)
                .setTicker("test")
                .setContentTitle(title + " ^-^")
                .setContentText("Master, " + message + " ^~^")
                .setContentIntent(pendingIntent)
                .setContentInfo("INFO")

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(1, b.build())
    }
}