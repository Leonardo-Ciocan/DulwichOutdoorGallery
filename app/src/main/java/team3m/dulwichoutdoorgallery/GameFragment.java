package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.Timer;
import java.util.TimerTask;

public class GameFragment extends Fragment {

    private Game game;
    private ImageButton[] imageButtonsArray = new ImageButton[4];
    private ImageView artworkToMatch;
    private Timer timer = new Timer();
    private ViewAnimator theVa;
    private int currentButton;
    private TextView changeImageChoiceNumberText;
    private TextView changeImageNumberText;
    private TextView changeArtistText;
    private Activity theActivity;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        changeImageChoiceNumberText = (TextView) v.findViewById(R.id.currentRotatedPicture);
        changeImageNumberText = (TextView) v.findViewById(R.id.currentGameArtwork);
        changeArtistText = (TextView) v.findViewById(R.id.currentGameArtworkArtist);
        artworkToMatch = (ImageView) v.findViewById(R.id.imageView);
        imageButtonsArray[0] = (ImageButton) v.findViewById(R.id.imageButton1);
        imageButtonsArray[1] = (ImageButton) v.findViewById(R.id.imageButton2);
        imageButtonsArray[2] = (ImageButton) v.findViewById(R.id.imageButton3);
        imageButtonsArray[3] = (ImageButton) v.findViewById(R.id.imageButton4);
        theVa = (ViewAnimator) v.findViewById(R.id.viewAnimator);

        for (int t = 0; t < 4; t++) {
            final int u = t;
            imageButtonsArray[t].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.makeAGuess(u)) {
                        imageClick();
                    } else {
                        incorrectAnswerAlert();
                    }
                }
            });
        }

        game = Game.getNextGame(theActivity);
        loadData();

        theVa.setInAnimation(AnimationUtils.loadAnimation(theActivity, android.R.anim.slide_in_left));
        theVa.setOutAnimation(theActivity, android.R.anim.slide_out_right);
        theVa.setDisplayedChild(0);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onTimerTick();
            }
        }, 3000, 3000);

        if (Game.allSetsComplete()) {
            gameComplete();
        }

        Button lpb = (Button) v.findViewById(R.id.letsplaybutton);
        lpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences gameUiData = theActivity.getSharedPreferences("gameUi", 0);

                        gameUiData.edit()
                                .putBoolean("showHelp", false)
                                .commit();

                        theActivity.findViewById(R.id.gameHelpFrame).setVisibility(View.GONE);
                    }
                });
            }
        });

        SharedPreferences gameUiData = theActivity.getSharedPreferences("gameUi", 0);

        if (!gameUiData.getBoolean("showHelp", true)) {
            v.findViewById(R.id.gameHelpFrame).setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        theActivity = activity; //save this for later
    }

    public void loadData() {
        game = Game.getNextGame(theActivity);
        theActivity.runOnUiThread(new Thread(new Runnable() {
            public void run() {
                if (Game.allSetsComplete()) {
                    changeImageNumberText.setText("Game completed!");
                } else {
                    currentButton = 0;
                    artworkToMatch.setImageResource(game.getIdOfArtworkToMatch());
                    for (int i = 0; i < 4; i++) {
                        imageButtonsArray[i].setImageResource(game.getPossibleChoices()[i]);
                    }
                    changeImageNumberText.setText("Picture: " + ((Game.progress()) + 1) + "/20");
                    changeArtistText.setText("Artist: " + game.getArtistName());
                }
            }
        }));
    }

    public void onTimerTick() {
        currentButton = ++currentButton % 4;
        theActivity.runOnUiThread(new Thread(new Runnable() {
            public void run() {
                int currentText = currentButton + 1;
                changeImageChoiceNumberText.setText(Integer.toString(currentText));
                theVa.setDisplayedChild(currentButton);
            }
        }));
    }

    public void imageClick() {

        if (!Game.allSetsComplete()) {
            loadData();
        } else {
            // if all 20 choices guessed correctly, run the GameComplete activity
            gameComplete();
        }
    }

    public void gameComplete() {
        // start GameCompleteFragment
        timer.purge();

        theActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences GameCompleteUiData = theActivity.getSharedPreferences("gameCompleteUi", 0);

                GameCompleteUiData.edit()
                        .putBoolean("gameUi", false)
                        .commit();
            }
        });
    }

    public void incorrectAnswerAlert() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(theActivity);
        dlgAlert.setMessage("Try again.");
        dlgAlert.setTitle("Incorrect Answer");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timer.cancel();
    }
}
