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

package com.yandereaudio.pdesire.yanderecore.services.listeners;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.yandereaudio.pdesire.yanderecore.framework.app.YandereWearToMobileTransaction;

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
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.i(YandereWearableApplyListener.class.getSimpleName(), "Message Received" + messageEvent.getPath());
        final YandereWearToMobileTransaction executeLocal = new YandereWearToMobileTransaction(messageEvent.getPath());
        executeLocal.wearToCommand();
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