package com.example.digikala.views;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.example.digikala.R;

public class DigikalaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String id = getString(R.string.channel_id);
            String name = getString(R.string.channel_name);
            NotificationChannel channel = new NotificationChannel(
                    id,
                    name,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.channel_description));

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
