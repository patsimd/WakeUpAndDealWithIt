package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class MathExercice extends AppCompatActivity{
    private String answer ;
    private int answerInt = 0;
    private String equation = "";
    private int minEqu = 3;
    private int maxEqu = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_exercice);
        createActivity();
    }

    private void createActivity(){

        int randomNumber = randomizeNumber(minEqu,maxEqu);
        char ope;
        int num;
        for(int i =0 ; i<randomNumber; i++) {
            num = randomizeNumber(0,50);
            equation += num;

            
            if (i != randomNumber - 1){
                ope = randomizeOperator();
                equation += " " + ope + " ";
                if(ope== '+')
                    answerInt+= num;
                else if(ope== '-')
                    answerInt-= num;
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
        int random = rand.nextInt(max) + min;

        return random;
    }

    public void validate(View view){
            EditText answerEquation = (EditText)findViewById(R.id.equationEdit);
            String rep = answerEquation.getText().toString();
            if(rep == answer)
                answer ="37";
        }
}
