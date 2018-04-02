package al.demo.alarmmanagerdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NotificationPublisher extends BroadcastReceiver {
    private String TAG = "NotificationPublisher";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent service_intent = new Intent(context, AlarmPlayer.class);
        service_intent.putExtra("alarmMusic",intent.getStringExtra("musicUri"));
        context.startService(service_intent);


        int randomActivity = (int)(Math.random() * 3);

        Intent contentIntent;

        switch (randomActivity){
            case 0: contentIntent = new Intent(context, MainActivity.class);
                break;
            case 1: contentIntent = new Intent(context, MainActivity.class);
                break;
            case 2: contentIntent = new Intent(context, MainActivity.class);
                break;
            default: contentIntent = new Intent(context, MainActivity.class);
                break;
        }

        contentIntent.setAction(Intent.ACTION_MAIN);
        contentIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        contentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentIntent(PendingIntent.getActivity(context, 0, contentIntent, 0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(intent.getStringExtra("AlarmName"))
                .build();

        notificationManager.notify(123, notification);

        context.startActivity(contentIntent);

    }


}
