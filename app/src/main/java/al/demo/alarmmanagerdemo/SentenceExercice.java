package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class SentenceExercice extends Game {
    private String[] sentencesEasy = {  "I am a horse.",
                                        "I eat an apple.",
                                        "An orange unicorn flies.",
                                        "The sun is beautiful."};

    private String[] sentencesMedium = {  "It is really hot and sunny outside.",
                                        "I'm not tired this morning.",
                                        "This exercise is really enjoyable.",
                                        "Today is gonna be a good day."};

    private String[] sentencesHard = {  "This app is, without a doubt, the best of all time.",
                                        "Pneumonoultramicroscopicsilicovolcanoconiosis.",
                                        "Symbols '@' '/' '<' are unpleasant to write.",
                                        "Incomprehensibilities is a very long word."};


    private String randomSentence;
    private TextView txtSentence;
    private EditText answerSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentence_exercice);
        txtSentence = (TextView)findViewById(R.id.phraseText);
        answerSentence = (EditText)findViewById(R.id.phraseEdit);
        createActivity();
    }

    private void createActivity(){
        Random rand = new Random();
        int random;
        if(difficulte.equals(Game.Difficulties[0])){
            random = rand.nextInt(sentencesEasy.length);
            randomSentence = sentencesEasy[random];
        }
        else if(difficulte.equals(Game.Difficulties[1])){
            random = rand.nextInt(sentencesMedium.length);
            randomSentence = sentencesMedium[random];
        }
        else if (difficulte.equals(Game.Difficulties[2])){
            random = rand.nextInt(sentencesHard.length);
            randomSentence = sentencesHard[random];
        }

        txtSentence.setText(randomSentence);
    }

    public void validate(View view){
        String rep = answerSentence.getText().toString();
        if(rep.equals(randomSentence))
            gameCompleted();
        else{
            //TODO, Pas reussi
        }
    }

    public void another(View view) {
        createActivity();
    }
}
