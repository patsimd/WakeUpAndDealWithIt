package al.demo.alarmmanagerdemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AlarmPlayer extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIf){
        mediaPlayer = MediaPlayer.create(this, R.raw.beepbeep);
        mediaPlayer.start();

        return START_NOT_STICKY;
    }
}
