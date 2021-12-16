package com.example.smartmarker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseNotificationService extends FirebaseMessagingService {


    String title;
    String text;

    NotificationCompat.Builder notificationBuilder;

    public FirebaseNotificationService() {
    }
//받는사람
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Service", "zzz");
        Map<String, String> data_notify = remoteMessage.getData();

        Log.e("FCMService", "received");
        title = data_notify.get("title");
        text = data_notify.get("text");


        Intent intent = new Intent(getBaseContext(), intro.class);
        Bundle bundle = new Bundle(2);
        intent.putExtra("title", title);
        intent.putExtra("text", text);

        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        String channelId = "mychannel";

        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSound(defaultUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelName = "channelName";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManger.createNotificationChannel(channel);
        notificationManger.notify(0, notificationBuilder.build());

    }

}




