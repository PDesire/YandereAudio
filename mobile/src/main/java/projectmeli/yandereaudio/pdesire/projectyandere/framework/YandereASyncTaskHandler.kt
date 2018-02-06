package projectmeli.yandereaudio.pdesire.projectyandere.framework

import android.os.AsyncTask
import java.io.IOException

/**
 * Created by pdesire on 13.12.17.
 */
class YandereASyncTaskHandler {
    class CommandASyncExecution(val command : String) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String? {
            try {
                val execute = Runtime.getRuntime().exec("su -c " + command)
                execute.waitFor()
            } catch (io : IOException) {
                io.printStackTrace()
            }
            return "Finished"
        }

        override fun onPreExecute() {
        }
    }

}