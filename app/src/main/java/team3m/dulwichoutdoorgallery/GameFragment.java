package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.Timer;

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

    private TextView changeOldArtistText;
    private TextView changeOldArtNameText;

    private Activity theActivity;
    private GestureDetector theGestureDetector;

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
        changeArtistText = (TextView) v.findViewById(R.id.currentNewArtist);

        changeOldArtistText = (TextView) v.findViewById(R.id.currentSelectedOldArtworkName);
        changeOldArtNameText = (TextView) v.findViewById(R.id.currentSelectedOldArtworkArtist);

        artworkToMatch = (ImageView) v.findViewById(R.id.imageView);
        imageButtonsArray[0] = (ImageButton) v.findViewById(R.id.imageButton1);
        imageButtonsArray[1] = (ImageButton) v.findViewById(R.id.imageButton2);
        imageButtonsArray[2] = (ImageButton) v.findViewById(R.id.imageButton3);
        imageButtonsArray[3] = (ImageButton) v.findViewById(R.id.imageButton4);
        theVa = (ViewAnimator) v.findViewById(R.id.viewAnimator);
        theVa.setInAnimation(AnimationUtils.loadAnimation(theActivity, android.R.anim.fade_in));
        theVa.setOutAnimation(theActivity, android.R.anim.fade_out);

        theGestureDetector = new GestureDetector(getActivity().getBaseContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (game.makeAGuess(currentButton)) {
                            final CustomDialogClass2 cdd = new CustomDialogClass2(theActivity);
                            cdd.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    cdd.dismiss();
                                }
                            }, 1500);
                        } else {
                            final CustomDialogClass cdd = new CustomDialogClass(theActivity);
                            cdd.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    cdd.dismiss();
                                }
                            }, 1500);
                        }
                        if (!Game.allSetsComplete()) {
                            loadData();
                        } else {
                            // if all 20 choices guessed correctly, run the GameComplete activity
                            showCompleteScreen(getView());
                        }
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        if (velocityX > 0) { //right
                            if ((--currentButton) < 0) currentButton = 3;
                        } else {
                            if ((++currentButton) > 3) currentButton = 0;
                        }
                        changeImageChoiceNumberText.setText(Integer.toString(currentButton + 1));
                        theVa.setDisplayedChild(currentButton);
                        changeOldArtistText.setText(game.getArtworkName(currentButton));
                        changeOldArtNameText.setText(game.getArtworkArtist(currentButton));
                        return true;
                    }
                }
        );

        for (int t = 0; t < 4; t++) {
            imageButtonsArray[t].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return theGestureDetector.onTouchEvent(event);
                }
            });
        }

        theVa.setDisplayedChild(0);

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
                                .apply();
                        theActivity.findViewById(R.id.gameHelpFrame).setVisibility(View.GONE);
                    }
                });
            }
        });

        SharedPreferences gameUiData = theActivity.getSharedPreferences("gameUi", 0);

        if (!gameUiData.getBoolean("showHelp", true)) {
            v.findViewById(R.id.gameHelpFrame).setVisibility(View.GONE);
        }

        game = Game.getNextGame(theActivity);
        loadData();

        if (Game.allSetsComplete()) {
            showCompleteScreen(v);
        }

        Button restartGameButton = (Button) v.findViewById(R.id.gameRestartButton);
        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Game.reset();
                        theActivity.findViewById(R.id.gameFrame).setVisibility(View.VISIBLE);
                        game = Game.getNextGame(theActivity);
                        loadData();
                        theActivity.findViewById(R.id.gameCompleteFrame).setVisibility(View.GONE);
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        theActivity = activity; //save this for later
        game = Game.getNextGame(theActivity);

    }

    public void loadData() {
        game = Game.getNextGame(theActivity);
        theActivity.runOnUiThread(new Thread(new Runnable() {
            public void run() {
                if (Game.allSetsComplete()) {
                    changeImageNumberText.setText("Game completed!");
                } else {
                    artworkToMatch.setImageResource(game.getIdOfArtworkToMatch());
                    for (int i = 0; i < 4; i++) {
                        imageButtonsArray[i].setImageResource(game.getPossibleChoices()[i]);
                    }
                    changeImageNumberText.setText("Picture: " + ((Game.progress()) + 1) + "/20");
                    changeArtistText.setText("Street Artist: " + game.getArtistName());
                    currentButton = 0;
                    changeOldArtistText.setText(game.getArtworkName(currentButton));
                    changeOldArtNameText.setText(game.getArtworkArtist(currentButton));
                    theVa.setDisplayedChild(0);
                    changeImageChoiceNumberText.setText(Integer.toString(currentButton + 1));
                }
            }
        }));
    }

    public void showCompleteScreen(View v) {
        TextView displayCorrectAnswers = (TextView) v.findViewById(R.id.textViewGameComplete2);
        displayCorrectAnswers.setText("You got " + Game.score() + "/20 questions correct!");
        v.findViewById(R.id.gameCompleteFrame).setVisibility(View.VISIBLE);
        v.findViewById(R.id.gameFrame).setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timer.cancel();
    }
}