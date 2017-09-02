package com.meli.pdesire.yandereservice.framework

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
import android.graphics.BitmapFactory




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