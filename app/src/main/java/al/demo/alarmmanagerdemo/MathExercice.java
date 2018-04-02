package al.demo.alarmmanagerdemo;

import java.util.Random;

/**
 * Created by MathGuilmette on 2018-04-01.
 */

public class MathExercice {
    private String answer;
    private int intAnswer;
    private boolean isCorrect;
    private String equation = "";
    private int minEqu = 3;
    private int maxEqu = 6;

    public MathExercice(){

        int randomNumber = randomizeNumber(minEqu,maxEqu);

        for(int i =0 ; i<randomNumber; i++){
            equation += randomizeNumber(0,10);
            if(i != randomNumber -1)
                equation += " " + randomizeOperator() + " ";
        }
        intAnswer = 0;
        answer = String.valueOf(intAnswer);
    }

    public String getEquation(){
        return equation;
    }

    public boolean isCorrect(String userAnswer){
        if(answer == userAnswer)
            return true;
        else
            return false;
    }

    private char randomizeOperator(){
        int random = (int)(Math.random() * 3 + 1);

        switch (random){
            case 1: return '+';
            case 2: return '-';
            case 3: return '*';
            //case 4: return '/';
            default: return ' ';
        }
    }

    private int randomizeNumber(int min,int max){
        Random rand = new Random();
        int random = rand.nextInt(max) + min;

        return random;
    }
}
