package projectmeli.yandereaudio.pdesire.projectyandere.framework

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.io.IOException
import java.lang.Exception

/**
 * Created by pdesire on 13.12.17.
 */
class YandereASyncTaskHandler {
    class CommandASyncExecution(val command : String) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String? {
            try {
                try {
                    val su = Runtime.getRuntime().exec("su -c " + command)
                    val outputStream = DataOutputStream(su.outputStream)

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
                Log.d(this.javaClass.name, "Command \"" + command + "\" executed successfully")
            } catch (io : Exception) {
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