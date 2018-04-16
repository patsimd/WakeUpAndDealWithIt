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

/**
 * Created by Benoit on 4/15/2018.
 */

public class FollowPathExercice extends Game{

    private boolean followingPath = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (difficulte){
            case "Easy":
                setContentView(R.layout.followpath_supereasy);
                break;
            case "Normal":
            setContentView(R.layout.followpath_easy);
                break;
            case "Hard":
                setContentView(R.layout.followpath_normal);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);

        int x = Math.round(ev.getX());
        int y = Math.round(ev.getY());

        RelativeLayout b = (View)findViewById(R.id.Background);
        if(b == null)
            return true;



        if(!followingPath){
            for (int i=0; i<b.getChildCount(); i++) {
                View child = (View)b.getChildAt(i);
                if (x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()) {
                    if (child.getId() == R.id.StartingPoint) {
                        followingPath = true;
                    }
                }

            }
        }
        else {
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

            followingPath = inSections ;

            if (followingPath == false) {
                ((TextView) findViewById(R.id.debugText)).setText("Ok");
            }
            else
                ((TextView)findViewById(R.id.debugText)).setText("Failed, try again");
        }
        return true;
    }

}
