package al.demo.alarmmanagerdemo;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

public class PowerConnectedBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "PowerConnectedBroRe";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Reset all alarms when the phone restart
        if (intent != null) {
            if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){

                AlarmHelper alarmManager = new AlarmHelper(context);
                SQLiteDatabase db = context.openOrCreateDatabase("ALARM",Context.MODE_PRIVATE, null);
                Cursor allAlarmsCursor = db.query("alarm",null,null,null,null,null,"id ASC");

                while (allAlarmsCursor.moveToNext()) {

                    Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");
                    notificationIntent.putExtra("AlarmName", allAlarmsCursor.getString(1));
                    notificationIntent.putExtra("musicUri", allAlarmsCursor.getString(7));
                    notificationIntent.putExtra("difficultyString", allAlarmsCursor.getString(5));
                    notificationIntent.putExtra("alarmID", allAlarmsCursor.getInt(0));

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,  allAlarmsCursor.getInt(0), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.set(Calendar.MINUTE, allAlarmsCursor.getInt(3));
                    selectedTime.set(Calendar.HOUR_OF_DAY, allAlarmsCursor.getInt(2));
                    selectedTime.set(Calendar.SECOND, 0);
                    if (selectedTime.before(Calendar.getInstance())) {
                        if(!(selectedTime.get(Calendar.HOUR_OF_DAY) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && selectedTime.get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)))
                            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    alarmManager.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);
                }

                allAlarmsCursor.close();

            }
        }


    }
}
