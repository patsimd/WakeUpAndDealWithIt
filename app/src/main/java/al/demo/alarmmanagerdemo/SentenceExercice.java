package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class SentenceExercice extends Game {
    private String[] sentencesEasy = {  "Je suis un cheval.",
                                        "Je mange une pomme.",
                                        "Une licorne orange.",
                                        "Le soleil est beau."};

    private String[] sentencesMedium = {  "Il fait très chaud et beau à l'extérieur",
                                        "Je ne suis pas fatigué ce matin.",
                                        "Cet exercice est très agréable.",
                                        "Cette application est la meilleure de tous les temps."};

    private String[] sentencesHard = {  "Aujourd'hui sera une très belle journée.",
                                        "Le mot 'c'est-à-dire' est difficile à écrire.",
                                        "Les symboles '@' '/' '<' sont désagréables.",
                                        "Anticonstitutionnellement est le mot le plus long du dictionnaire."};


    private String randomSentence;
    private int difficulte =1;
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

        txtSentence.setText(randomSentence);
    }

    public void validate(View view){
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
