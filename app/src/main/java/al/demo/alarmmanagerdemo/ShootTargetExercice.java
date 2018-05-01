package al.demo.alarmmanagerdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Target;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class ShootTargetExercice extends Game{
    private boolean gameStarted = false;
    private int lives = 3;
    private int targetRemaining = 5;
    private float ShootingTargetspawnDelay = 3;
    private float targetLifeTime = 3;
    private int targetSize = 150;
    private int targetSpawned =0;
    private boolean succes = true;
    Bitmap targetBmp;

    private myThread thread;
    private ShootingTargetSurfaceView shootSurfaceView;

    private boolean alreadyStartedNewGame = false;

    private Vector<ShootingTarget> shootingTarget = new Vector<ShootingTarget>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shootSurfaceView = new ShootingTargetSurfaceView(this);
        targetBmp = BitmapFactory.decodeResource(getResources(), R.drawable.target);start();
    }



    @Override
    public void gameCompleted() {
        stopService(new Intent(this, AlarmPlayer.class));
        stopService(new Intent(this, BadService.class));
        finish();
    }

    public void start(){
        alreadyStartedNewGame = true;
        setContentView(shootSurfaceView);


        if(difficulte.equals(Game.Difficulties[0])){
            lives = 3;
            ShootingTargetspawnDelay = 3;
            targetRemaining = 8;
            targetLifeTime = 3;
            targetSize = 200;
        }
        else if(difficulte.equals(Game.Difficulties[1])){
            lives = 3;
            ShootingTargetspawnDelay = 2.25f;
            targetRemaining = 12;
            targetLifeTime = 2.25f;
            targetSize = 200;
        }
        else if (difficulte.equals(Game.Difficulties[2])){

            lives = 3;
            ShootingTargetspawnDelay = 1.5f;
            targetRemaining = 20;
            targetLifeTime = 1.5f;
            targetSize = 150;
        }
    }


    private Vector2d getFinishPoint(){
        int x = targetSize + (int)(Math.random() * ((int)shootSurfaceView.right - 2 * targetSize));
        int y = (int)(shootSurfaceView.bottom / 6 ) + (int)(Math.random() * (int)(shootSurfaceView.bottom / 2 ));

        return new Vector2d(x,y);
    }

    public void update(double deltaTime, double gameClock){

        if(targetRemaining <= 0 ){
            if(shootingTarget.isEmpty())
                thread.setRunning(false);
        }
        else if(Math.floor(( gameClock / ShootingTargetspawnDelay) ) > targetSpawned){

            Vector2d finish = getFinishPoint();
            Vector2d start = new Vector2d(finish.x, shootSurfaceView.bottom + targetSize);

            ShootingTarget newTarget = new ShootingTarget(start, finish, targetSize, targetBmp);
            shootingTarget.add(newTarget);

            targetSpawned++;
            targetRemaining--;
        }

        if (!shootingTarget.isEmpty()) {
            for (int i = 0; i < shootingTarget.size(); i++) {
                shootingTarget.elementAt(i).update(deltaTime);
            }
        }
    }


    public class Vector2d
    {
        public float x = 0;
        public float y = 0;
        public Vector2d(Vector2d v)
        {
            x = v.x;
            y = v.y;
        }
        public Vector2d(float x, float y){
            this.x = x;
            this.y = y;
        }
        public float length(){
            return (float)Math.sqrt(x*x + y*y);
        }
        public Vector2d normalize(){
            float length = length();
            return div(length);
        }
        public Vector2d mul(float a){
            return new Vector2d((x * a), (y * a));
        }
        public Vector2d div(float a){
            return new Vector2d((x / a), (y / a));
        }
        public Vector2d add(Vector2d v){
            return new Vector2d((x + v.x), (y + v.y));
        }
        public Vector2d sub(Vector2d v){
            return new Vector2d((x - v.x), (y - v.y));
        }
        public Vector2d vectorTo(Vector2d v){
            return new Vector2d(v.x - x  , v.y - y );
        }
        public double distanceTo(Vector2d v){
            return vectorTo(v).length();
        }
    }


    private class ShootingTarget
    {
        private Bitmap targetBmp;
        private Vector2d location;
        private Vector2d startCoord;
        private Vector2d finishCoord;

        private Vector2d direction;
        private Vector2d differenceVector;

        private float completionRate = 0;

        private final float speed = 100;
        private int radius = 100;
        private boolean dead = false;

        private final double clickForgivness = 10;
        ShootingTarget(Vector2d start, Vector2d finish, int radius, Bitmap targetBmp) {
            this.targetBmp = targetBmp;

            startCoord = start;
            finishCoord = finish;
            //startCoord = new Vector2d(200, 200);
            //finishCoord = new Vector2d(1000, 1000);
            location = startCoord;
            direction = (startCoord.vectorTo(finishCoord)).normalize();

            differenceVector = startCoord.vectorTo(finishCoord);
            this.radius = radius;
        }

        void update(double deltaTime)
        {
            if(dead) return;

            completionRate += (deltaTime / targetLifeTime);
            if(completionRate >= 1 ){
                lives--;
                dead = true;
                if(lives == 0){
                    succes = false;
                    thread.setRunning(false);
                }
                shootingTarget.remove(this);
            }
            else
            {
                Vector2d offset = differenceVector.mul((float)Math.sin(completionRate * Math.PI));
                location = startCoord.add(offset);
            }

            /*
            Vector2d translation = direction.mul((float)deltaTime * speed);

            location = location.add(translation);
            if(location.distanceTo(finishCoord) < translation.length() * 5 ){
                lives--;
                if(!alreadyStartedNewGame && lives == 0){
                    startNewGame();
                    alreadyStartedNewGame = true;
                }
            }
            */
        }
        public boolean isOn(float x, float y){
            Vector2d start = new Vector2d(location);
            Vector2d end = new Vector2d(location.x + radius, location.y + radius);

            return ( ( x <  end.x && x > start.x ) &&( y <  end.y && y > start.y )  );
            //clickForgivness
        }
        void draw(Canvas c) {
            c.drawBitmap(targetBmp, null, new RectF(location.x, location.y, location.x+ radius, location.y+ radius), null);
        }

        void scale(int dRadius) {
            radius += dRadius;
        }
    }


    private class myThread extends Thread {

        private final int targetFPS = 30;
        private long startTime;
        private long finishTime = -1;
        private long targetTime = 1000/targetFPS;
        public double deltaTime;
        public double gameClock = 0;
        private boolean started = false;

        private final SurfaceHolder _surfaceHolder;
        private boolean running = false;

        private myThread(SurfaceHolder surfaceHolder, ShootingTargetSurfaceView SurfaceView) {
            _surfaceHolder = surfaceHolder;
            shootSurfaceView = SurfaceView;
        }

        void setRunning(boolean run) {
            running = run;
            gameClock = 0;
        }



        @Override
        public void run() {
            Canvas c;

            while (running) {

                if (!started) {
                    gameClock = 0;
                    finishTime = System.currentTimeMillis();
                    started = true;
                }

                startTime = System.currentTimeMillis();

                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    if (!running || c == null) return;
                    synchronized (_surfaceHolder) {
                        update(deltaTime, gameClock);
                        shootSurfaceView.draw(c);
                    }
                } finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }

                long previousFinishTime = finishTime;
                finishTime = System.currentTimeMillis();
                deltaTime = (double) (finishTime - previousFinishTime) / 1000;
                gameClock += deltaTime;

                long sleepTime = targetTime - (finishTime - startTime);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!succes)
            {
                finish();
                changeGame();
            }
            else
                gameCompleted();

        }
    }


    public class ShootingTargetSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        public Paint color, black;
        Bitmap bg;

        public float left, right, top, bottom;
        float leftbtn, firebtn, rightbtn;
        float scale;
        float fontHeight;
        float gameOverX, gameOverY;


        Random myRandom;

        public ShootingTargetSurfaceView(Context context) {
            super(context);
            scale = getResources().getDisplayMetrics().density;

            color = new Paint();
            color.setColor(Color.BLUE);
            color.setStyle(Paint.Style.FILL);

            black = new Paint();
            black.setStyle(Paint.Style.FILL);
            black.setTextSize(20 * scale);
            black.setFakeBoldText(true);
            myRandom = new Random();

            getHolder().addCallback(this);
        }


        void setupBG(int w, int h) {
            bg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bg);

            left = 0;
            right = w;

            float standoff = 80 * scale;
            float third = w / 3;

            leftbtn = third;
            firebtn = third + third;
            rightbtn = w;
            bottom = h - standoff;
            top = standoff;

            Paint.FontMetrics fm = black.getFontMetrics();
            fontHeight = fm.descent - fm.ascent;

            float heightcenter = bottom + (standoff / 2 + fontHeight / 2);
            Rect bounds = new Rect();


            black.getTextBounds("Game Over", 0, "Game Over".length(), bounds);
            gameOverX = right / 2.0f - bounds.width() / 2.0f;
            gameOverY = (h / 2.0f) - (fontHeight / 2.0f);
        }


        @Override
        public void draw(Canvas c) {
            super.draw(c);
            c.drawColor(Color.WHITE);  //entire screen black, then draw on the background.

            if (bg == null) return;
            c.drawBitmap(bg, 0, 0, null);
            c.drawText("Lives: " + lives, 25, fontHeight * 1.25f, black);
            c.drawText("Target Remainging: " + targetRemaining, 25, fontHeight * 2.5f, black);

            if (!shootingTarget.isEmpty()) {
                for (int i = 0; i < shootingTarget.size(); i++) {
                    (shootingTarget.elementAt(i)).draw(c);
                }
            }


        }


        void checkGameState() {


        }

        /*
       * touch event to deal with the left, right, and fire "button"
       *
       */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            // Retrieve the new x and y touch positions
            float x = event.getX();
            float y = event.getY();


            if (!shootingTarget.isEmpty()) {
                for (int i = 0; i < shootingTarget.size(); i++) {
                    ShootingTarget target = shootingTarget.elementAt(i);
                    if(target.isOn(x,y)){
                        shootingTarget.removeElementAt(i);
                    }
                }
            }


            return super.onTouchEvent(event);
        }


        /*
         *  Three surface methods to override.  created, changed, and destroyed.
         *  setup on create, do nothing in changed, and shut it all down in destroyed.
         */
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

            setupBG(getWidth(), getHeight());

            thread = new myThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            boolean retry = true;
            thread.setRunning(false);
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
