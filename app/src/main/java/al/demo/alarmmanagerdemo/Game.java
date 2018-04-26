package al.demo.alarmmanagerdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;


public class Game extends AppCompatActivity {
    public static String[] Difficulties = {"Easy","Normal","Hard"};
    public static Class<?>[] GamesArray = {IntrusExercice.class,ShakingGame.class,SentenceExercice.class,MathExercice.class,FollowPathExercice.class,SequenceExercice.class,rightcolorExercice.class};
    protected String difficulte;
    //private boolean intentionalDestroy = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficulte = getIntent().getStringExtra("Difficulty");

        Intent service_intent = new Intent(this, BadService.class);
        service_intent.putExtra("diff",difficulte);
        startService(service_intent);
    }

    public void gameCompleted() {
        setContentView(R.layout.game_completed);
        stopService(new Intent(this, AlarmPlayer.class));
        stopService(new Intent(this, BadService.class));
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
                }
        });

    }

    public void startNewGame(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),0);
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("You failed :(");
        alertDialog.setMessage("Another game will start!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //do something when the dialog dismiss.
                changeGame();
            }
        });
    }

    private void changeGame(){
        stopService(new Intent(this, BadService.class));
        int randomActivity = (int)(Math.random() * GamesArray.length);

        Intent contentIntent = new Intent(this, GamesArray[randomActivity]);

        contentIntent.putExtra("Difficulty",difficulte);

        startActivity(contentIntent);

        //intentionalDestroy = true;
        close();
    }

    public void close()
    {
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*if(!intentionalDestroy)
            stopService(new Intent(this, AlarmPlayer.class));*/
    }


}
