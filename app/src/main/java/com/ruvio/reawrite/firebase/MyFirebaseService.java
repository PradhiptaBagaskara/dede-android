package com.ruvio.reawrite.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String dataPayload = data.get("data");


        /*
         * Cek jika notif berisi data payload
         * pengiriman data payload dapat dieksekusi secara background atau foreground
         */

        if (remoteMessage.getData().size() > 0) {
            Log.e("TAG", "Message data payload: " + remoteMessage.getData());

            try {
                JSONObject jsonParse = new JSONObject(dataPayload);
                showNotif(jsonParse.getString("title"), jsonParse.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
         * Cek jika notif berisi data notification payload
         * hanya dieksekusi ketika aplikasi bejalan secara foreground
         * dan dapat push notif melalui UI Firebase console
         */
        if (remoteMessage.getNotification() != null) {
            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showNotif(String title, String message){

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "NotifApps")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_logo) // icon
                .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
                .setLights(200,200,200) // light button
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
                .setOnlyAlertOnce(true) // set alert sound notif
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent); // action notif ketika di tap
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notifBuilder.build());
    }
}