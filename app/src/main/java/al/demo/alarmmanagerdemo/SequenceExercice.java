package al.demo.alarmmanagerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class SequenceExercice extends Game{
    private int nbSeq;
    private String sequence = "";
    private int nbIterate = 0;
    int number;

    private EditText answer;
    private TextView seq1;
    private TextView seq2;
    private TextView seq3;
    private TextView seq4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_exercice);
        answer = (EditText) findViewById(R.id.sequenceEdit);
        answer.setEnabled(false);

        seq1 = (TextView) findViewById(R.id.seq1);
        seq2 = (TextView) findViewById(R.id.seq2);
        seq3 = (TextView) findViewById(R.id.seq3);
        seq4 = (TextView) findViewById(R.id.seq4);
        createActivity();
    }

    private void createActivity() {
        if (difficulte.equals(Game.Difficulties[0])) {
            nbSeq = 4;
        } else if (difficulte.equals(Game.Difficulties[1])) {
            nbSeq = 6;
        } else if (difficulte.equals(Game.Difficulties[2])) {
            nbSeq = 8;
        }

        new CountDownTimer(1500, 1000) {
            public void onTick(long millisUntilFinished){

            }
            public void onFinish(){
                for(int i =0; i < nbSeq +1; i++) {
                    new CountDownTimer((i + 1) * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            if (number != -99) {
                                number = randomizeNumber();
                                ResetTextView();
                                sequence += number;
                            }

                            switch (number) {
                                case 1:
                                    seq1.setTextSize(50);
                                    seq1.setTextColor(Color.RED);
                                    break;
                                case 2:
                                    seq2.setTextSize(50);
                                    seq2.setTextColor(Color.RED);
                                    break;
                                case 3:
                                    seq3.setTextSize(50);
                                    seq3.setTextColor(Color.RED);
                                    break;
                                case 4:
                                    seq4.setTextSize(50);
                                    seq4.setTextColor(Color.RED);
                                    break;
                                case -99:
                                    answer.setEnabled(true);
                                default:
                                    break;
                            }
                        }
                    }.start();

                    new CountDownTimer((i + 1) * 1000 + 500, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            ResetTextView();
                            nbIterate++;
                            if (nbIterate == nbSeq)
                                number = -99;
                        }
                    }.start();
                }
            }
        }.start();
    }

    private int randomizeNumber(){
        Random rand = new Random();
        int random = rand.nextInt((4 - 1) + 1) + 1;

        return random;
    }

    public void validate(View view){
        String rep = answer.getText().toString();
        if(rep.equals(sequence) && !rep.equals("")) {
            gameCompleted();
        }
        else{
            startNewGame();
        }
    }

    /*public void another(View view){
        sequence = "";
        nbIterate = 0;
		answer.setEnabled(false);
		createActivity();
    }*/

    private void ResetTextView(){
        seq1.setTextSize(30);
        seq1.setTextColor(Color.BLACK);
        seq2.setTextSize(30);
        seq2.setTextColor(Color.BLACK);
        seq3.setTextSize(30);
        seq3.setTextColor(Color.BLACK);
        seq4.setTextSize(30);
        seq4.setTextColor(Color.BLACK);
    }
}
