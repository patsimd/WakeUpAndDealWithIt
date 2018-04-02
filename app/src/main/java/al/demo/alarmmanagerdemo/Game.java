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


public class Game extends AppCompatActivity {
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void gameCompleted()
    {
        setContentView(R.layout.game_completed);
        stopService(new Intent(this, AlarmPlayer.class));
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, AlarmPlayer.class));
    }


}
