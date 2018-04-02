package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class MathExercice extends Game{
    private String answer ;
    private int answerInt = 0;
    private String equation = "";
    private int minEqu;
    private int maxEqu;
    private int maxNum;
    private int difficulte =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_exercice);
        createActivity();
    }

    private void createActivity(){
        equation = "";
        answerInt =0;
        int randomNumber;
        if(difficulte == 1){
            minEqu = 3;
            maxEqu = 5;
            maxNum = 30;
        }
        else if(difficulte == 2){
            minEqu = 4;
            maxEqu = 6;
            maxNum = 50;
        }
        else if (difficulte == 3){
            minEqu = 5;
            maxEqu = 7;
            maxNum = 75;
        }
        randomNumber = randomizeNumber(minEqu,maxEqu);
        char ope =' ';
        int num;
        for(int i =0 ; i<randomNumber; i++) {
            num = randomizeNumber(0,maxNum);
            equation += num;
            if(ope ==' ')
                answerInt = num;
            else if(ope== '+')
                answerInt+= num;
            else if(ope== '-')
                answerInt-= num;

            if (i != randomNumber - 1){
                ope = randomizeOperator();
                equation += " " + ope + " ";
            }

        }
        answer = String.valueOf(answerInt);
        TextView txtEquation = (TextView)findViewById(R.id.equationText);
        txtEquation.setText(equation);
    }

    private char randomizeOperator(){
        Random rand = new Random();
        int random = rand.nextInt(2) + 1;
        switch (random){
            case 1: return '+';
            case 2: return '-';
            //case 3: return '*';
            //case 4: return '/';
            default: return ' ';
        }
    }

    private int randomizeNumber(int min,int max){
        Random rand = new Random();
        int random = rand.nextInt((max - min) + 1) + min;

        return random;
    }

    public void validate(View view){
            EditText answerEquation = (EditText)findViewById(R.id.equationEdit);
            String rep = answerEquation.getText().toString();
            if(rep.equals(answer))
            {
                gameCompleted();
            }
            else{

            }
    }

    public void another(View view){
        createActivity();
    }
}
