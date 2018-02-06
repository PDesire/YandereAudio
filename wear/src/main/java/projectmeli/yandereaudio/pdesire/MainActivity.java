/*
 * Copyright (C) 2017 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the BSD-3-Clause License
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package projectmeli.yandereaudio.pdesire;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
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
                .addOnConnectionFailedListener(result -> Log.i(MainActivity.class.getSimpleName(), "Connection failed"))
                .addApi(Wearable.API)
                .build();

        client.connect();

        Preference heavybass = findPreference("heavybass_switch");

        heavybass.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean switched = ((SwitchPreference) preference)
                    .isChecked();
            if (!switched) {
                sendMessage("/start_heavybass_enable", null);
            } else {
                sendMessage("/start_heavybass_disable", null);
            }
            return true;
        });

        Preference pdesireaudio = findPreference("pdesireaudio_switch");

        pdesireaudio.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean switched = ((SwitchPreference) preference)
                    .isChecked();
            if (!switched) {
                sendMessage("/start_pdesireaudio_enable", null);
            } else {
                sendMessage("/start_pdesireaudio_disable", null);
            }
            return true;
        });

        Preference reboot = findPreference("reboot_phone_click");

        reboot.setOnPreferenceClickListener(preference -> {
            sendMessage("/start_reboot", null);
            return false;
        });

    }

    private void sendMessage(final String message, final byte[] payload) {
        Log.i(MainActivity.class.getSimpleName(), "Sending message " + message);
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback(getConnectedNodesResult -> {
            List<Node> nodes = getConnectedNodesResult.getNodes();
            for (Node node : nodes) {
                Log.i(MainActivity.class.getSimpleName(), "sending " + message + " to " + node);
                Wearable.MessageApi.sendMessage(client, node.getId(), message, payload).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
                        Log.i(MainActivity.class.getSimpleName(), "Result " + sendMessageResult.getStatus());
                    }
                });
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