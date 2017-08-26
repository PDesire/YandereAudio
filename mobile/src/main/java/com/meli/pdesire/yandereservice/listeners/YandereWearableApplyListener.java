package com.meli.pdesire.yandereservice.listeners;

/**
 * Created by PDesire on 8/26/17.
 */

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.meli.pdesire.yandereservice.resolver.YandereWearToMobileTransaction;

public class YandereWearableApplyListener extends WearableListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Destroy");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Data changed" );
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Message Received" + messageEvent.getPath());
        YandereWearToMobileTransaction.wearable.wearToCommand(messageEvent.getPath());
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Connected");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Disconnected");
    }
}