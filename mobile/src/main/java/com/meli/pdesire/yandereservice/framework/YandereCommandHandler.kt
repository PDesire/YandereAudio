package com.meli.pdesire.yandereservice.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context



@SuppressLint("Registered")

/**
 * Created by PDesire on 8/27/17.
 */
object YandereCommandHandler : Activity() {

    private val PREFS_NAME = "prefs_secure_replace"
    private val PREF_SECURE_REPLACE = "secure_replace"

    fun secure_replace(source_directory : String, source_file : String, destination : String) : Int {
        //val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        //val useSecureReplace = preferences.getBoolean(PREF_SECURE_REPLACE, false)
        var md5hashsum : String = ""
        var secondmd5hashsum : String = " "

        //if (useSecureReplace) {
        md5hashsum = YandereCryptography.fileToMD5(destination + "/" + source_file);

        YandereRootUtility.sudo("cp " + source_directory + "/" + source_file + " " + destination)

        secondmd5hashsum = YandereCryptography.fileToMD5(destination + "/" + source_file);

        if (md5hashsum.equals(secondmd5hashsum))
            return 1
        else
            return 0

        //} else {
        //    YandereRootUtility.sudo("cp " + source_directory + "/" + source_file + " " + destination)
        //    return 1
        //}
    }

    @Override
    fun copy(source_directory : String,
             source_file : String,
             destination : String) : Int {
        var success : Int = 0

        mount_rw()
        success = secure_replace(source_directory, source_file, destination)
        mount_ro()

        if (success == 1)
            return 1
        else
            return 0
    }

    @Override
    fun copy(source_directory : String,
             source_file : String,
             destination : String,
             source_directoryTwo : String,
             source_fileTwo : String,
             destinationTwo : String) : Int {
        var successOne : Int = 0
        var successTwo : Int = 0
        mount_rw()
        successOne = secure_replace(source_directory, source_file, destination)
        successTwo = secure_replace(source_directoryTwo, source_fileTwo, destinationTwo)
        mount_ro()

        if (successOne == successTwo) {
            if (successOne == 1)
                return 1
            else
                return 0
        } else
            return 0
    }

    fun mount_rw () {
        YandereRootUtility.mount_rw_rootfs()
        YandereRootUtility.mount_rw_system()
    }

    fun mount_ro () {
        YandereRootUtility.mount_ro_rootfs()
        YandereRootUtility.mount_ro_system()
    }

    fun callHeavybass (apply : Boolean) : Int {
        if (apply) {
            return copy("/system/Yuno/heavybass",
                    "srs_processing.cfg",
                    "/system/etc/srs")
        } else {
            return copy("/system/Yuno/stock",
                    "srs_processing.cfg",
                    "/system/etc/srs")
        }
    }

    fun callYumeEngine() : Int {
        return copy("/system/Yuno/Engines/Yume/Final/etc",
                "audio_effects.conf",
                "/system/etc",
                "/system/Yuno/Engines/Yume/Final/vendor",
                "audio_effects.conf",
                "/system/vendor/etc")
    }

    fun callMeliEngine() : Int {
        return copy("/system/Yuno/Engines/Meli/etc",
                "audio_effects.conf",
                "/system/etc",
                "/system/Yuno/Engines/Meli/vendor",
                "audio_effects.conf",
                "/system/vendor/etc")
    }

    fun callReboot() {
        YandereRootUtility.sudo("reboot")
    }

    fun callPDesireAudio (activation : Int) {
        YandereRootUtility.sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudio())
    }

    fun callPDesireAudioStatic (activation : Int) {
        YandereRootUtility.sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudioStatic())
    }
}