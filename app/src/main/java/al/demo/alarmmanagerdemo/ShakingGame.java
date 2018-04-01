package al.demo.alarmmanagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ShakingGame extends Activity implements SensorEventListener{

    private Sensor mySensor;
    private SensorManager SM;

    private Calendar lastTime;
    private double lastAcceleration;

    private ProgressBar progressBar;
    private int fillBar = 0;

    private TextView yText;
    private static float growthMult = 20;

    private static float depletePerSec = 250;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.shaking_game);

        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);


        lastTime = Calendar.getInstance();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setMax(1000);
        progressBar.setProgress(fillBar = 0);
        lastAcceleration = 0;
      //  xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.value);
        //zText = (TextView)findViewById(R.id.zText);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       // double sumForce = Math.sqrt(Math.pow(sensorEvent.values[0], 2)   + Math.pow(sensorEvent.values[1], 2) + Math.pow(sensorEvent.values[2], 2));
        double currentAcceleration = sensorEvent.values[1];

        if(lastAcceleration == 0)
        {
            lastAcceleration = currentAcceleration;
            return;
        }
        double deltaAcc = Math.abs(currentAcceleration - lastAcceleration);




        Calendar currentTime = Calendar.getInstance();
        double timeLapse = currentTime.getTimeInMillis() - lastTime.getTimeInMillis();


        fillBar += (deltaAcc * growthMult - depletePerSec) * (timeLapse/1000);

        progressBar.setProgress(fillBar);
        yText.setText("dY: " + deltaAcc);

        lastTime = currentTime;
        lastAcceleration = currentAcceleration;

        // xText.setText("x: " + sensorEvent.values[0]);
        // yText.setText("y: " + sensorEvent.values[1]);
        // zText.setText("z: " + sensorEvent.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
