package projectmeli.yandereaudio.pdesire.projectcluster.receivers

import android.content.Context
import android.util.Log
import com.pdesire.projectcluster.receivers.ClusterBootReceiver

/**
 * Created by pdesire on 10.12.17.
 */
class YandereClusterBootReceiver : ClusterBootReceiver() {

    private val LOG_TAG = javaClass.simpleName
    private val PREFS_NAME = "cluster_prefs"
    private val PREFS_DONT_SHOW_AGAIN = "dont_show_again"

    override fun onClusterSucceeded (context: Context) {
        Log.d(LOG_TAG, "Project Cluster appliance succeeded")
    }

    override fun onClusterFailure(context: Context) {
        Log.e(LOG_TAG, "Project Cluster appliance has been failed.")
    }
}