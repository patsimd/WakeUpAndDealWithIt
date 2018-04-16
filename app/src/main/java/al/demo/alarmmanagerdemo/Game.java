package al.demo.alarmmanagerdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    public static String[] Difficulties = {"Easy","Normal","Hard"};

    protected String difficulte;
    private boolean intentionalDestroy = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficulte = getIntent().getStringExtra("Difficulty");
    }

    public void gameCompleted() {
        setContentView(R.layout.game_completed);
        stopService(new Intent(this, AlarmPlayer.class));
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
                }
        });

    }

    public void startNewGame(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Wrong Answer :(");
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
        int randomActivity = (int)(Math.random() * 4);

        Intent contentIntent;

        switch (randomActivity){
            case 0: contentIntent = new Intent(this, ShakingGame.class);
                break;
            case 1: contentIntent = new Intent(this, MathExercice.class);
                break;
            case 2: contentIntent = new Intent(this, SentenceExercice.class);
                break;
            case 3: contentIntent = new Intent(this, FollowPathExercice.class);
                break;
            default: contentIntent = new Intent(this, MainActivity.class);
                break;
        }

        contentIntent.putExtra("Difficulty",difficulte);

        contentIntent.setAction(Intent.ACTION_MAIN);
        contentIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        contentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(contentIntent);

        intentionalDestroy = true;
        close();
    }

    public void close()
    {
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(!intentionalDestroy)
            stopService(new Intent(this, AlarmPlayer.class));
    }


}
