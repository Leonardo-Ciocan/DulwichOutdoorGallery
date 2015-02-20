package team3m.dulwichoutdoorgallery;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends ActionBarActivity implements GameHelpFragment.OnFragmentInteractionListener {
    private Game game;
    private ImageButton[] imageButtonsArray = new ImageButton[4];
    private ImageView artworkToMatch;
    private Timer timer = new Timer();
    private HorizontalScrollView hsv;
    private int currentButton;
    private TextView changeImageChoiceNumberText;
    private TextView changeImageNumberText;
    private TextView changeArtistText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences gameUiData = getSharedPreferences("gameUi", 0);

        if (savedInstanceState == null) {
            if (gameUiData.getBoolean("showHelp", true))
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.game_container, GameHelpFragment.newInstance())
                        .commit();
        }

        game = Game.getNextGame(this);
        changeImageChoiceNumberText = (TextView) findViewById(R.id.currentRotatedPicture);
        changeImageNumberText = (TextView) findViewById(R.id.currentGameArtwork);
        changeArtistText = (TextView) findViewById(R.id.currentGameArtworkArtist);
        artworkToMatch = (ImageView) findViewById(R.id.imageView);
        imageButtonsArray[0] = (ImageButton) findViewById(R.id.imageButton1);
        imageButtonsArray[1] = (ImageButton) findViewById(R.id.imageButton2);
        imageButtonsArray[2] = (ImageButton) findViewById(R.id.imageButton3);
        imageButtonsArray[3] = (ImageButton) findViewById(R.id.imageButton4);
        loadData();
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

    public void loadData() {
        game = Game.getNextGame(this);
        runOnUiThread(new Thread(new Runnable() {
            public void run() {
                artworkToMatch.setImageResource(game.getIdOfArtworkToMatch());
                for (int i = 0; i < 4; i++) {
                    imageButtonsArray[i].setImageResource(game.getPossibleChoices()[i]);
                }

                changeImageNumberText.setText("Picture: " + (Game.progress() + 1) + "/20");
                changeArtistText.setText(game.getArtistName());
            }
        }));
    }

    public void onTimerTick() {
        currentButton = ++currentButton % 4;
        //Log.i(Integer.toString(currentButton), Integer.toString(imageButtonsArray[currentButton].getLeft()));
        hsv.smoothScrollTo(imageButtonsArray[currentButton].getLeft(), 0);
        runOnUiThread(new Thread(new Runnable() {
            public void run() {
                int currentText = currentButton + 1;
                changeImageChoiceNumberText.setText(Integer.toString(currentText));
            }
        }));
    }

    public void imageClick() {

        if (!Game.allSetsComplete()) {
            loadData();
        } else {
            // if all 20 choices guessed correctly, run the GameComplete activity
            timer.purge();
            Intent intent = new Intent(this, GameCompleteActivity.class);
            gameComplete(intent);
            loadData();
        }
    }

    public void clickButton1(View view) {
        // return to backend that 1st picture was clicked
        if (game.makeAGuess(0)) {
            imageClick();
        } else {
            incorrectAnswerAlert();
        }
    }

    public void clickButton2(View view) {
        // return to backend that 2nd picture was clicked
        if (game.makeAGuess(1)) {
            imageClick();
        } else {
            incorrectAnswerAlert();
        }
    }

    public void clickButton3(View view) {
        // return to backend that 3rd picture was clicked
        if (game.makeAGuess(2)) {
            imageClick();
        } else {
            incorrectAnswerAlert();
        }
    }

    public void clickButton4(View view) {
        // return to backend that 4th picture was clicked
        if (game.makeAGuess(3)) {
            imageClick();
        } else {
            incorrectAnswerAlert();
        }
    }

    public void gameComplete(Intent intent) {

        // start GameCompleteActivity
        startActivity(intent);
    }

    public void incorrectAnswerAlert() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Try again.");
        dlgAlert.setTitle("Incorrect Answer");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
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

    @Override
    public void onFragmentInteraction() {
        SharedPreferences gameUiData = getSharedPreferences("gameUi", 0);

        gameUiData.edit()
                .putBoolean("showHelp", false)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .remove(getSupportFragmentManager().findFragmentById(R.id.game_container))
                .commit();
    }
}