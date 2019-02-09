package com.example.malut.clother.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.malut.clother.R;
import com.example.malut.clother.SplashActivity;

public class AlarmReceiver extends BroadcastReceiver {



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        long when = System.currentTimeMillis();
        boolean isSticky = isInitialStickyBroadcast();

        if(!isSticky) {

            Intent notificationIntent = new Intent(context, SplashActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            String CHANNEL_ID = "channel";// The id of the channel.
            CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.
            NotificationCompat.Builder mBuilder;
            Bundle bundle = new Bundle();
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(mChannel);
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentText("Open the app to know what to wear today")
                    .setSmallIcon(R.drawable.icon)
                    .setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("Time to open the app!");
            mBuilder.setAutoCancel(true);
            mNotificationManager.notify(1, mBuilder.build());
        }



    }

}