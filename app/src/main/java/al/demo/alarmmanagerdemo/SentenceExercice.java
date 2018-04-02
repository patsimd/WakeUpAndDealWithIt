package al.demo.alarmmanagerdemo;

import java.util.Random;

/**
 * Created by MathGuilmette on 2018-04-01.
 */

public class SentenceExercice {
    private String[] sentences = {  "Aujourd'hui sera une très belle journée.",
                                    "Je ne suis pas fatigué ce matin.",
                                    "Cet exercice est très agréable.",
                                    "Cette application est la meilleure de tous les temps."};
    private String randomSentence;
    private boolean isCorrect;

    public SentenceExercice(){
        Random rand = new Random();
        int random = rand.nextInt(sentences.length);
        randomSentence = sentences[random];
    }

    public String getSentence(){
        return randomSentence;
    }

    public boolean isCorrect(String userSentence){
        if(userSentence == randomSentence)
            return true;
        else
            return false;
    }
}
