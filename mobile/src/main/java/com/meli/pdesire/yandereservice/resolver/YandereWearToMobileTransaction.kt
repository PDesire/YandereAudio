package com.meli.pdesire.yandereservice.resolver

import android.util.Log
import com.meli.pdesire.yandereservice.framework.YandereCommandHandler


/**
 * Created by PDesire on 8/26/17.
 */
class YandereWearToMobileTransaction {
    companion object wearable {
        fun wearToCommand(command: String) {
            if (command.equals("/start_pdesireaudio_enable")) {
                YandereCommandHandler.callPDesireAudio(1)
            } else if (command.equals("/start_pdesireaudio_disable")) {
                YandereCommandHandler.callPDesireAudio(0)
            } else if (command.equals("/start_heavybass_enable")) {
                YandereCommandHandler.callHeavybass(true)
            } else if (command.equals("/start_heavybass_disable")) {
                YandereCommandHandler.callHeavybass(false)
            } else if (command.equals("/start_reboot")) {
                YandereCommandHandler.callReboot()
            } else {
                Log.i("YandereAudio:", "Transaction Failed, Command not valid")
            }
        }
    }
}