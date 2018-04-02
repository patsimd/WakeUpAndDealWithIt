package al.demo.alarmmanagerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.app.Service;
import android.view.View;

import android.os.Binder;
import android.os.IBinder;
import android.content.ServiceConnection;
import android.content.ComponentName;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import al.demo.alarmmanagerdemo.AlarmPlayer.LocalBinder;

public class Game extends Activity {
    private Context context;
    private AlarmPlayer alarmPlayer;
    private boolean mBound = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


     private ServiceConnection mConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            alarmPlayer = binder.GetAlarmPlayer();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void gameCompleted()
    {
        setContentView(R.layout.game_completed);
        stopService(new Intent(this, AlarmPlayer.class));


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
                }
        });

    }

    public void close()
    {
        this.finish();
    }

    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

}
