package com.example;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.R;
import com.example.afterNotification;


public class MainActivity extends AppCompatActivity {
    // declaring variables
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private Notification.Builder builder;
    private String channelId = "i.apps.notifications";
    private String description = "Test notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // accessing button
        Button btn = findViewById(R.id.btn);

        // it is a class to notify the user of events that happen.
        // This is how you tell the user that something has happened in the
        // background.
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // onClick listener for the button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pendingIntent is an intent for future use i.e after
                // the notification is clicked, this intent will come into action
                Intent intent = new Intent(MainActivity.this, afterNotification.class);

                // FLAG_UPDATE_CURRENT specifies that if a previous
                // PendingIntent already exists, then the current one
                // will update it with the latest intent
                // 0 is the request code, using it later with the
                // same method again will get back the same pending
                // intent for future reference
                // intent passed here is to our afterNotification class
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // RemoteViews are used to use the content of
                // some different layout apart from the current activity layout
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_after_notification);

                // checking if android version is greater than oreo(API 26) or not
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(false);
                    notificationManager.createNotificationChannel(notificationChannel);

                    builder = new Notification.Builder(MainActivity.this, channelId)
                            .setContent(contentView)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                            .setContentIntent(pendingIntent);
                } else {

                    builder = new Notification.Builder(MainActivity.this)
                            .setContent(contentView)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                            .setContentIntent(pendingIntent);
                }
                notificationManager.notify(1234, builder.build());
            }
        });
    }
}
