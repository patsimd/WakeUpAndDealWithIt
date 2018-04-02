package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class SentenceExercice extends Game {
    private String[] sentencesEasy = {  "Aujourd'hui sera une très belle journée.",
                                        "Je ne suis pas fatigué ce matin.",
                                        "Cet exercice est très agréable.",
                                        "Cette application est la meilleure de tous les temps."};

    private String[] sentencesMedium = {  "Aujourd'hui sera une très belle journée.",
                                        "Je ne suis pas fatigué ce matin.",
                                        "Cet exercice est très agréable.",
                                        "Cette application est la meilleure de tous les temps."};

    private String[] sentencesHard = {  "Aujourd'hui sera une très belle journée.",
                                        "Je ne suis pas fatigué ce matin.",
                                        "Cet exercice est très agréable.",
                                        "Cette application est la meilleure de tous les temps."};


    private String randomSentence;
    private int difficulte =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentence_exercice);
        createActivity();

    }

    private void createActivity(){
        Random rand = new Random();
        int random;
        if(difficulte == 1){
            random = rand.nextInt(sentencesEasy.length);
            randomSentence = sentencesEasy[random];
        }
        else if(difficulte == 2){
            random = rand.nextInt(sentencesMedium.length);
            randomSentence = sentencesMedium[random];
        }
        else if (difficulte == 3){
            random = rand.nextInt(sentencesHard.length);
            randomSentence = sentencesHard[random];
        }

        TextView txtSentence = (TextView)findViewById(R.id.phraseText);
        txtSentence.setText(randomSentence);
    }

    public void validate(View view){
        EditText answerSentence = (EditText)findViewById(R.id.phraseEdit);
        String rep = answerSentence.getText().toString();
        if(rep.equals(randomSentence))
            gameCompleted();
        else{

        }
    }

    public void another(View view) {
        createActivity();
    }
}
