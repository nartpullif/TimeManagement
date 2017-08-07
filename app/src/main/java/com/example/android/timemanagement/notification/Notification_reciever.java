package com.example.android.timemanagement.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.android.timemanagement.activities.HomeTabActivity;
import com.example.android.timemanagement.activities.MainActivity;

/**
 * Created by Mark on 2017/8/6.
 */

public class Notification_reciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent back_intent  =new Intent(context, HomeTabActivity.class);
        back_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,back_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle("Warning")
                .setContentText("You have a task to start on")
                .setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        notificationManager.notify(100,builder.build());
    }
}
