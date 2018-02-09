package projectmeli.yandereaudio.pdesire.projectyandere.framework

import android.os.AsyncTask
import android.util.Log
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
                Log.d(this.javaClass.name, "Command \"" + command + "\" executed successfully")
            } catch (io : IOException) {
                io.printStackTrace()
                Log.e(this.javaClass.name, "Command \"" + command + "\" failed")
            }
            return "Finished"
        }

        override fun onPreExecute() {
            Log.v(this.javaClass.name, "Start")
        }
    }

}