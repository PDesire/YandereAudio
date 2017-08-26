package com.meli.pdesire.yandereservice.framework

import android.support.annotation.NonNull
import java.io.DataOutputStream
import java.io.IOException

/**
 * Created by PDesire on 26.05.2017.
 */

object YandereRootUtility {

    fun mount_rw_rootfs() {
        sudo("mount -o remount,rw /")
        sudo("mount -o remount,rw rootfs")
    }

    fun mount_ro_rootfs() {
        sudo("mount -o remount,ro /")
        sudo("mount -o remount,ro rootfs")
    }

    fun mount_rw_system() {
        sudo("mount -o remount,rw /system")
    }

    fun mount_ro_system() {
        sudo("mount -o remount,ro /system")
    }

    @NonNull
    fun sudo(strings: String) {
        try {
            val su = Runtime.getRuntime().exec("su")
            val outputStream = DataOutputStream(su.outputStream)

            for (s in strings) {
                outputStream.writeBytes(s + "\n")
                outputStream.flush()
            }

            outputStream.writeBytes("exit\n")
            outputStream.flush()
            try {
                su.waitFor()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        execute_sudo(strings)
    }

    @NonNull
    private fun execute_sudo(command : String) {
        val execute = Runtime.getRuntime().exec("su -c " + command)
        execute.waitFor()
    }
}