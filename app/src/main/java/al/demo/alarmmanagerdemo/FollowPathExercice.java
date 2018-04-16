package al.demo.alarmmanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View.OnTouchListener;

/**
 * Created by Benoit on 4/15/2018.
 */

public class FollowPathExercice extends Game{

    private boolean followingPath = false;
    private boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(difficulte.equals(Game.Difficulties[0]))
            setContentView(R.layout.followpath_supereasy);
        else if(difficulte.equals(Game.Difficulties[1]))
            setContentView(R.layout.followpath_easy);
        else if (difficulte.equals(Game.Difficulties[2]))
            setContentView(R.layout.followpath_normal);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);

        if (ev.getAction() == android.view.MotionEvent.ACTION_UP) {
            followingPath = false;
            SetPrompt();
            return true;
        } else {


            int x = Math.round(ev.getX());
            int y = Math.round(ev.getY());

            RelativeLayout b = (RelativeLayout) findViewById(R.id.Background);
            if (b == null)
                return true;


            if (!followingPath) {
                for (int i = 0; i < b.getChildCount(); i++) {
                    View child = (View) b.getChildAt(i);
                    if (x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()) {
                        if (child.getId() == R.id.StartingPoint) {
                            followingPath = true;
                            gameStarted = true;
                            SetPrompt();
                        }
                    }

                }
            } else {
                boolean inSections = false;

                for (int i = 0; i < b.getChildCount(); i++) {
                    View child = (View) b.getChildAt(i);
                    if (x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()) {
                        if (child.getId() == R.id.StartingPoint ||
                                child.getId() == R.id.section0 ||
                                child.getId() == R.id.section1 ||
                                child.getId() == R.id.section2 ||
                                child.getId() == R.id.section3 ||
                                child.getId() == R.id.section4 ||
                                child.getId() == R.id.section5 ||
                                child.getId() == R.id.section6) {
                            inSections = true;
                        }
                    }
                    if (x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()) {
                        if (child.getId() == R.id.FinishingPoint) {
                            gameCompleted();
                        }
                    }

                }
                followingPath = inSections;
                SetPrompt();
            }
            return true;
        }
    }

    private void SetPrompt()
    {
        View prompt = findViewById(R.id.InstructionWindow);
        if (prompt != null) {
            if (followingPath == false && gameStarted == true) {
                prompt.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.instruction)).setLines(1);
                ((TextView) findViewById(R.id.instruction)).setText("Failed, try again");
            } else {
                prompt.setVisibility(View.GONE);
            }
        }
    }

}
