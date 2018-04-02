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
import android.support.v7.app.AppCompatActivity;
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

public class Game extends AppCompatActivity {
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
}
