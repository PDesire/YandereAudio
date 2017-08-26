package com.meli.pdesire.yandereservice.resolver

import android.util.Log
import com.meli.pdesire.yandereservice.framework.YanderePDesireAudioAPI
import com.meli.pdesire.yandereservice.framework.YandereRootUtility


/**
 * Created by PDesire on 8/26/17.
 */
class YandereWearToMobileTransaction {
    companion object wearable {
        fun wearToCommand(command: String) {
            if (command.equals("/start_pdesireaudio_enable")) {
                YandereRootUtility.sudo("echo 1 " + YanderePDesireAudioAPI.getPDesireAudio())
            } else if (command.equals("/start_pdesireaudio_disable")) {
                YandereRootUtility.sudo("echo 0 " + YanderePDesireAudioAPI.getPDesireAudio())
            } else if (command.equals("/start_heavybass_enable")) {
                YandereRootUtility.mount_rw_rootfs()
                YandereRootUtility.mount_rw_system()
                YandereRootUtility.sudo("cp /system/Yuno/stock/srs_processing.cfg /system/etc/srs")
                YandereRootUtility.mount_ro_rootfs()
                YandereRootUtility.mount_ro_system()
            } else if (command.equals("/start_heavybass_disable")) {
                YandereRootUtility.mount_rw_rootfs()
                YandereRootUtility.mount_rw_system()
                YandereRootUtility.sudo("cp /system/Yuno/heavybass/srs_processing.cfg /system/etc/srs")
                YandereRootUtility.mount_ro_rootfs()
                YandereRootUtility.mount_ro_system()
            } else if (command.equals("/start_reboot")) {
                YandereRootUtility.sudo("reboot")
            } else {
                Log.i("YandereAudio:", "Transaction Failed, Command not valid")
            }
        }
    }
}