package al.demo.alarmmanagerdemo;

/**
 * Created by Jabbawabba Wakotokata on 2018-04-16.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import java.util.Random;
public class rightcolorExercice extends Game {

    private boolean gameReady = false;
    private int toComplete = 5;
    private boolean rightcolor = false;
    private int chances = 3;

    String colorsName[] = {"Black", "Red", "Green", "Blue", "Yellow", "Orange", "Purple"};
    int colors[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.rightcolor);
        findViewById(R.id.InstructionWindow).setVisibility(View.VISIBLE);


        colors = new int[]{ContextCompat.getColor(rightcolorExercice.this, R.color.black),
                ContextCompat.getColor(rightcolorExercice.this, R.color.red),
                ContextCompat.getColor(rightcolorExercice.this, R.color.green),
                ContextCompat.getColor(rightcolorExercice.this, R.color.blue),
                ContextCompat.getColor(rightcolorExercice.this, R.color.yellow),
                ContextCompat.getColor(rightcolorExercice.this, R.color.orange),
                ContextCompat.getColor(rightcolorExercice.this, R.color.purple)};


        if (difficulte.equals(Game.Difficulties[0]))
            toComplete = 6;
        else if (difficulte.equals(Game.Difficulties[1]))
            toComplete = 10;
        else if (difficulte.equals(Game.Difficulties[2]))
            toComplete = 15;


        NewTestColorCombination();
        Scores();

        Button button = (Button) findViewById(R.id.readyButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameReady = true;
                findViewById(R.id.InstructionWindow).setVisibility(View.GONE);
            }
        });


        button = (Button) findViewById(R.id.YesButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (gameReady && rightcolor) {
                    Scores();
                }
                else if (gameReady && !rightcolor) {
                    Fail();
                }
                NewTestColorCombination();
            }
        });

        button = (Button) findViewById(R.id.NoButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (gameReady && !rightcolor) {
                    Scores();
                }
                else if (gameReady && rightcolor) {
                    Fail();
                }
                NewTestColorCombination();
            }
        });
    }

    protected void Scores()
    {
        toComplete -= 1;
        if(toComplete <= 0)
            gameCompleted();

        TextView textView = (TextView) findViewById(R.id.ColorRemaining);
                if(textView == null)
                    return;
        textView.setText("Remaining: "  + String.valueOf(toComplete) );
        NewTestColorCombination();
    }


    protected void NewTestColorCombination(){

        int color1 = (int)(Math.random() * colorsName.length);

        rightcolor =  Math.random() < 0.5;

        if (rightcolor) {
            SetTextColor(color1, color1);
        }
        else{
            int color2;
            do{
                color2 =(int)(Math.random() * colorsName.length);
            }while(color2 == color1);
            SetTextColor(color1, color2);
        }
    }

    protected void SetTextColor(int name, int color)
    {
        TextView textView = (TextView)findViewById(R.id.colorText);
        if(textView == null)
            return;

        textView.setText(colorsName[name]);
        textView.setTextColor(colors[color]);
    }

    protected void Fail(){
        chances--;
        TextView textView = (TextView) findViewById(R.id.TriesRemaining);
        if (textView == null)
            return;
        textView.setText("  Tries left : " + String.valueOf(chances));
        if(chances <= 0)
            startNewGame();
    }


}
