package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class SentenceExercice extends AppCompatActivity {
    private String[] sentences = {  "Aujourd'hui sera une très belle journée.",
                                    "Je ne suis pas fatigué ce matin.",
                                    "Cet exercice est très agréable.",
                                    "Cette application est la meilleure de tous les temps."};
    private String randomSentence;
    private boolean isCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentence_exercice);
        createActivity();

    }

    private void createActivity(){
        Random rand = new Random();
        int random = rand.nextInt(sentences.length);
        randomSentence = sentences[random];

        TextView txtSentence = (TextView)findViewById(R.id.phraseText);
        txtSentence.setText(randomSentence);
    }

    public void validate(View view){
        EditText answerSentence = (EditText)findViewById(R.id.phraseEdit);
        String rep = answerSentence.getText().toString();
        if(rep == randomSentence)
            setContentView(R.layout.activity_main);
    }
}
