package al.demo.alarmmanagerdemo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.ALARM_SERVICE;

public class NotificationPublisher extends BroadcastReceiver {
    private String TAG = "NotificationPublisher";
    SQLiteDatabase db;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent service_intent = new Intent(context, AlarmPlayer.class);
        service_intent.putExtra("alarmMusic",intent.getStringExtra("musicUri"));
        context.startService(service_intent);

        int alarmID = intent.getIntExtra("alarmID",-1);
        if(alarmID != -1){

            Cursor alarmCurs;
            boolean validCursor = false;

            if(MainActivity.active){
                alarmCurs = MainActivity.dbHelper.getAlarmByID(alarmID);
            }
            else{
                db = context.openOrCreateDatabase("ALARM",Context.MODE_PRIVATE, null);
                String[] args = {String.valueOf(alarmID)};
                alarmCurs = db.query("alarm",null,"id = ?", args ,null,null,"id ASC");
            }


            try {
                if (alarmCurs.moveToFirst()) {
                    validCursor = true;
                    if (alarmCurs.getInt(6) == 1) {
                        //Repeat
                        Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");
                        notificationIntent.putExtra("AlarmName",alarmCurs.getString(1));
                        notificationIntent.putExtra("musicUri",alarmCurs.getString(7));
                        notificationIntent.putExtra("difficultyString", alarmCurs.getString(5));
                        notificationIntent.putExtra("alarmID",alarmCurs.getInt(0));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.MINUTE, alarmCurs.getInt(3));
                        selectedTime.set(Calendar.HOUR_OF_DAY, alarmCurs.getInt(2));
                        selectedTime.set(Calendar.SECOND, 0);
                        selectedTime.add(Calendar.DAY_OF_MONTH, 1);

                        AlarmHelper alarmHelper = new AlarmHelper(context);
                        alarmHelper.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);

                    } else {
                        //Not Repeat
                        if(MainActivity.active){
                            MainActivity.dbHelper.updateAlarmEnable(alarmID,false);
                            MainActivity.updateAlarmList();
                        }
                        else{
                            db.execSQL("UPDATE alarm SET enabled =0 WHERE id =" + String.valueOf(alarmID));
                        }
                    }
                }
            } finally {
                alarmCurs.close();
            }

            if(!validCursor){
                if(MainActivity.active){
                    MainActivity.dbHelper.updateAlarmEnable(alarmID,false);
                    MainActivity.updateAlarmList();
                }
                else{
                    db.execSQL("UPDATE alarm SET enabled =0 WHERE id =" + String.valueOf(alarmID));
                }
            }

        }


        int randomActivity = (int)(Math.random() * Game.GamesArray.length);

        Intent contentIntent = new Intent(context, Game.GamesArray[randomActivity]);

        contentIntent.putExtra("Difficulty",intent.getStringExtra("difficultyString"));

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
