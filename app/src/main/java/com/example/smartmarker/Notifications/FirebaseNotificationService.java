package com.example.smartmarker.Notifications;

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
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.example.smartmarker.MainActivity;
import com.example.smartmarker.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseNotificationService extends FirebaseMessagingService {


    String user_id;
    String send_id;
    String flag;
    NotificationCompat.Builder notificationBuilder;
    private String userID;
    public FirebaseNotificationService() {
    }
//받는사람
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Service", "zzz");
        Map<String, String> data_notify = remoteMessage.getData();

        if (data_notify != null) {
            Log.e("FCMService", "received");
            user_id = data_notify.get("user_id");
            send_id=data_notify.get("send_id");
            flag=data_notify.get("flag");



                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle=new Bundle(2);
                intent.putExtra("user_id",user_id);
                intent.putExtra("flag",flag);

                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                String channelId = "mychannel";


        }

    }

}




