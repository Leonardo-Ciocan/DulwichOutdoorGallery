package team3m.dulwichoutdoorgallery;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends ActionBarActivity {
    private ImageButton[] imageButtonsArray = new ImageButton[4];
    private Timer timer = new Timer();
    private HorizontalScrollView hsv;
    private int currentButton;

    private Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        imageButtonsArray[0] = (ImageButton) findViewById(R.id.imageButton);
        imageButtonsArray[1] = (ImageButton) findViewById(R.id.imageButton2);
        imageButtonsArray[2] = (ImageButton) findViewById(R.id.imageButton3);
        imageButtonsArray[3] = (ImageButton) findViewById(R.id.imageButton4);
        hsv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        hsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onTimerTick();
            }
        }, 3000, 3000);
    }

    public void onTimerTick() {
        currentButton = ++currentButton % 4;
        Log.i("debug", "tick");
        hsv.smoothScrollTo(imageButtonsArray[currentButton].getLeft(), 0);

        Log.i(Integer.toString(currentButton), Integer.toString(imageButtonsArray[currentButton].getLeft()));
    }
    public void imageClick(int currentImageId, int clickedID)
    {
        //code when the user makes a choice
        if(game.checkMatch(currentImageId,clickedID))
        {
            game.progress++;
            game.no_right++;
            game.checkAchievment(game.no_right);
        }
    }

    public void clickButton1(View view) {
        // return to backend that 1st picture was clicked

        //first number is the current image that you must match
        //second number is the choice
        imageClick(1,1);


    }

    public void clickButton2(View view) {
        // return to backend that 2nd picture was clicked

        imageClick(1,2);
    }

    public void clickButton3(View view) {
        // return to backend that 3rd picture was clicked
        imageClick(1,3);
    }

    public void clickButton4(View view) {
        // return to backend that 4th picture was clicked
        imageClick(1,4);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            return rootView;
        }
    }
}