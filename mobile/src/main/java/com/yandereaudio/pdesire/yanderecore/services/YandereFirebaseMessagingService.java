/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
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

package com.yandereaudio.pdesire.yanderecore.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import projectmeli.yandereaudio.pdesire.MainActivity;
import projectmeli.yandereaudio.pdesire.R;

public class YandereFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("YandereFirebaseMessage", "Message data: " + remoteMessage.getData());
        }
        try {
            sendNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(), remoteMessage.getNotification().getBody());
        } catch (NullPointerException e) {
            sendNotification("Firebase Mesaging failed", "Sending notification via Firebase Cloud Messaging has been failed, contact PDesire for that");
            e.printStackTrace();
        }
    }

    private void sendNotification(String messageTitle, String messageBody) {
        Intent openAppMainIntent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openAppIntent = PendingIntent.getActivity(this, 0 /* Request code */, openAppMainIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Intent openXDAThreadMainIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openXDAThreadIntent = PendingIntent.getActivity(this, 0 /* Request code */, openXDAThreadMainIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Action openApp = new NotificationCompat.Action.Builder(R.drawable.ic_yandere_black_24dp, "Open YandereAudio", openAppIntent).build();

        NotificationCompat.Action openXDA = new NotificationCompat.Action.Builder(R.drawable.ic_yandere_black_24dp, "Open XDA Thread", openXDAThreadIntent).build();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Firebase_Notify_ID")
                .setSmallIcon(R.drawable.ic_yandere_black_24dp)
                .setContentTitle(messageTitle + " ^-^")
                .setContentText("Master, " + messageBody + " ^~^")
                .addAction(openApp)
                .addAction(openXDA)
                .setTicker(messageBody)
                .setLights(Color.argb(255, 88, 42, 114), 1000, 200);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        try {
            notificationManager.notify(0, notificationBuilder.build());
        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }
    }
}
