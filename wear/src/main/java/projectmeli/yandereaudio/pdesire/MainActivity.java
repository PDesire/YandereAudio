package projectmeli.yandereaudio.pdesire;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

/**
 * Created by PDesire on 8/26/17.
 */

public class MainActivity extends PreferenceActivity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_main);

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.i(MainActivity.class.getSimpleName(), "Connection failed");
                    }
                })
                .addApi(Wearable.API)
                .build();

        client.connect();

        Preference heavybass = findPreference("heavybass_switch");

        heavybass.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean switched = ((SwitchPreference) preference)
                        .isChecked();
                if (switched == false) {
                    sendMessage("/start_heavybass_enable", null);
                } else {
                    sendMessage("/start_heavybass_disable", null);
                }
                return true;
            }
        });

        Preference pdesireaudio = findPreference("pdesireaudio_switch");

        pdesireaudio.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean switched = ((SwitchPreference) preference)
                        .isChecked();
                if (switched == false) {
                    sendMessage("/start_pdesireaudio_enable", null);
                } else {
                    sendMessage("/start_pdesireaudio_disable", null);
                }
                return true;
            }
        });

        Preference reboot = findPreference("reboot_phone_click");

        reboot.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                sendMessage("/start_reboot", null);
                return false;
            }
        });

    }

    private void sendMessage(final String message, final byte[] payload) {
        Log.i(MainActivity.class.getSimpleName(), "Sending message " + message);
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                List<Node> nodes = getConnectedNodesResult.getNodes();
                for (Node node : nodes) {
                    Log.i(MainActivity.class.getSimpleName(), "sending " + message + " to " + node);
                    Wearable.MessageApi.sendMessage(client, node.getId(), message, payload).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.i(MainActivity.class.getSimpleName(), "Result " + sendMessageResult.getStatus());
                        }
                    });
                }

            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(client, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(MainActivity.class.getSimpleName(), "Connection failed");
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Wearable.MessageApi.removeListener(client, this);
        client.disconnect();

    }
}