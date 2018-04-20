package al.demo.alarmmanagerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BadService extends Service {

    private String diff;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIf) {
        diff = intent.getStringExtra("diff");
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
        //do something you want
        int randomActivity = (int)(Math.random() * Game.GamesArray.length);

        Intent contentIntent = new Intent(this, Game.GamesArray[randomActivity]);

        contentIntent.putExtra("Difficulty",diff);

        this.stopSelf();

        startActivity(contentIntent);
        //stop service

    }
}