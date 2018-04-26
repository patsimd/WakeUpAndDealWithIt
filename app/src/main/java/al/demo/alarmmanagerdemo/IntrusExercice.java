package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class IntrusExercice extends Game {

    private ImageView img11;
    private ImageView img12;
    private ImageView img13;
    private ImageView img14;

    private ImageView img21;
    private ImageView img22;
    private ImageView img23;
    private ImageView img24;

    private ImageView img31;
    private ImageView img32;
    private ImageView img33;
    private ImageView img34;

    private ImageView img41;
    private ImageView img42;
    private ImageView img43;
    private ImageView img44;

    private ArrayList<ImageView> listImageButton = new ArrayList<>();
    private ImageButton tabImgButt[];// = {img11,img12,img13,img14,img21,img22,img23,img24,img31,img32,img33,img34,img41,img42,img43,img44};

    private int goodImg;
    private int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intrus_exercice);
        setImageButton();
        createActivity();
    }

    private void createActivity(){
        listImageButton.clear();
        goodImg = 0;
        img = 0;
        Random rand = new Random();
        int randomImg = rand.nextInt(3);

        switch (randomImg){
            case 0:
                goodImg = R.drawable.pikagood;
                img = R.drawable.pika;
                break;
            case 1:
                goodImg = R.drawable.mariogood;
                img = R.drawable.mario;
                break;
            case 2:
                goodImg = R.drawable.angrybirdgood;
                img = R.drawable.angrybird;
                break;
        }

        if(difficulte.equals(Game.Difficulties[0])){
            listImageButton.add(img22);
            listImageButton.add(img23);
            listImageButton.add(img32);
            listImageButton.add(img33);
        }
        else if(difficulte.equals(Game.Difficulties[1])){
            listImageButton.add(img12);
            listImageButton.add(img13);
            listImageButton.add(img22);
            listImageButton.add(img23);
            listImageButton.add(img32);
            listImageButton.add(img33);
            listImageButton.add(img42);
            listImageButton.add(img43);
        }
        else if (difficulte.equals(Game.Difficulties[2])){
            listImageButton.add(img11);
            listImageButton.add(img12);
            listImageButton.add(img13);
            listImageButton.add(img14);
            listImageButton.add(img21);
            listImageButton.add(img22);
            listImageButton.add(img23);
            listImageButton.add(img24);
            listImageButton.add(img31);
            listImageButton.add(img32);
            listImageButton.add(img33);
            listImageButton.add(img34);
            listImageButton.add(img41);
            listImageButton.add(img42);
            listImageButton.add(img43);
            listImageButton.add(img44);
        }

        int randomCase = rand.nextInt(listImageButton.size());

        for(int i = 0; i < listImageButton.size();i++) {
            listImageButton.get(i).setMaxWidth(300);
            if (i == randomCase) {
                listImageButton.get(i).setImageResource(goodImg);
                listImageButton.get(i).setTag(goodImg);
            } else
                listImageButton.get(i).setImageResource(img);
        }
    }

    public void validate(View view){
        if(view.getTag() == null)
            startNewGame();
        else if((int)view.getTag() == goodImg)
            gameCompleted();
        else{
            startNewGame();
        }
    }

    private void setImageButton(){
        img11 = (ImageView) findViewById(R.id.img11);
        img12 = (ImageView) findViewById(R.id.img12);
        img13 = (ImageView) findViewById(R.id.img13);
        img14 = (ImageView) findViewById(R.id.img14);

        img21 = (ImageView) findViewById(R.id.img21);
        img22 = (ImageView) findViewById(R.id.img22);
        img23 = (ImageView) findViewById(R.id.img23);
        img24 = (ImageView) findViewById(R.id.img24);

        img31 = (ImageView) findViewById(R.id.img31);
        img32 = (ImageView) findViewById(R.id.img32);
        img33 = (ImageView) findViewById(R.id.img33);
        img34 = (ImageView) findViewById(R.id.img34);

        img41 = (ImageView) findViewById(R.id.img41);
        img42 = (ImageView) findViewById(R.id.img42);
        img43 = (ImageView) findViewById(R.id.img43);
        img44 = (ImageView) findViewById(R.id.img44);

    }
}
