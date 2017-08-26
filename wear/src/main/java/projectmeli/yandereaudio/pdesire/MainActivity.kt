package projectmeli.yandereaudio.pdesire

/**
 * Created by PDesire on 20.05.2017.
 */

import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.SwitchPreference
import android.util.Log
import preference.WearPreferenceActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.Wearable
import java.util.concurrent.TimeUnit


class MainActivity : WearPreferenceActivity() {

    private val LOG_TAG = MainActivity::class.java.simpleName

    private val CONNECTION_TIME_OUT_MS: Long = 2000

    private var googleApiClient: GoogleApiClient? = null
    private var nodeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_main)

        initGoogleApiClient()
        sendToast("test")
    }

    private fun initGoogleApiClient() {
        googleApiClient = getGoogleApiClient(this)
        retrieveDeviceNode()
    }

    private fun getGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build()
    }

    private fun retrieveDeviceNode() {
        Thread(Runnable {
            if (googleApiClient != null && !(googleApiClient!!.isConnected() || googleApiClient!!.isConnecting()))
                googleApiClient!!.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS)

            val result = Wearable.NodeApi.getConnectedNodes(googleApiClient).await()

            val nodes = result.nodes

            if (nodes.size > 0)
                nodeId = nodes[0].id

            Log.v(LOG_TAG, "Node ID of phone: " + nodeId)

            googleApiClient!!.disconnect()
        }).start()
    }

    private fun sendToast(path : String) {
        if (nodeId != null) {
            Thread(Runnable {
                if (googleApiClient != null && !(googleApiClient!!.isConnected() || googleApiClient!!.isConnecting()))
                    googleApiClient!!.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS)

                Wearable.MessageApi.sendMessage(googleApiClient, nodeId, path, null).await()
                googleApiClient!!.disconnect()
            }).start()
        }
    }
}
