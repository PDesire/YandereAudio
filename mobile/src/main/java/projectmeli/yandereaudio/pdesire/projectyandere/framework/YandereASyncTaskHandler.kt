package projectmeli.yandereaudio.pdesire.projectyandere.framework

import android.os.AsyncTask

/**
 * Created by pdesire on 13.12.17.
 */
class YandereASyncTaskHandler()  {
    class CommandASyncExecution(val command : String) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String? {
            val execute = Runtime.getRuntime().exec("su -c " + command)
            execute.waitFor()
            return "Finished"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
        }

        override fun onPreExecute() {
        }
    }

}