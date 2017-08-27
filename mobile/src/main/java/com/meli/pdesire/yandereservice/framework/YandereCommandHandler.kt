package com.meli.pdesire.yandereservice.framework

/**
 * Created by PDesire on 8/27/17.
 */
object YandereCommandHandler {
    fun mount_rw () {
        YandereRootUtility.mount_rw_rootfs()
        YandereRootUtility.mount_rw_system()
    }

    fun mount_ro () {
        YandereRootUtility.mount_ro_rootfs()
        YandereRootUtility.mount_ro_system()
    }

    fun callHeavybass (apply : Boolean) {
        mount_rw()
        if (apply) {
            YandereRootUtility.sudo("cp /system/Yuno/heavybass/srs_processing.cfg /system/etc/srs")
        } else {
            YandereRootUtility.sudo("cp /system/Yuno/stock/srs_processing.cfg /system/etc/srs")
        }
        mount_ro()
    }

    fun callYumeEngine() {
        mount_rw()
        YandereRootUtility.sudo("cp /system/Yuno/Engines/Yume/Final/etc/audio_effects.conf /system/etc")
        YandereRootUtility.sudo("cp /system/Yuno/Engines/Yume/Final/vendor/audio_effects.conf /system/etc")
        mount_ro()
    }

    fun callMeliEngine() {
        mount_rw()
        YandereRootUtility.sudo("cp /system/Yuno/Engines/Meli/etc/audio_effects.conf /system/etc")
        YandereRootUtility.sudo("cp /system/Yuno/Engines/Meli/vendor/audio_effects.conf /system/etc")
        mount_ro()
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