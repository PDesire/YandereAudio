package com.meli.pdesire.yandereservice.framework

import android.app.AlertDialog
import android.content.Context
import projectmeli.yandereaudio.pdesire.R

/**
 * Created by PDesire on 8/16/17.
 */
object YandereOutputWrapper {

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
}