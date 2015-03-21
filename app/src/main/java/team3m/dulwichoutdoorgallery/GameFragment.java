package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

/**
 * Class that interfaces between user and Game class.
 */
public class GameFragment extends Fragment {
    /**
     * An object of the Game class used for game bookingkeeping.
     */
    private Game game;
    /**
     * An array of ImageButtons. Used to store the four image choices for the current question.
     */
    private ImageButton[] imageButtonsArray = new ImageButton[4];
    /**
     * ImageView that displays the "new art" for the current question.
     */
    private ImageView artworkToMatch;
    /**
     * ViewAnimator that stores the four ImageButtons and generates the animation when user moves
     * between image choices.
     */
    private ViewAnimator theVa;
    /**
     * Stores the number of the ImageButton currently selected by the user.
     */
    private int currentButton;
    /**
     * A TextView that gives a visual representation of the currentButton variable.
     */
    private TextView changeImageChoiceNumberText;
    /**
     * A TextView that shows the user's progress through the game. Shown in the form of
     * "Picture: #/20".
     */
    private TextView changeImageNumberText;
    /**
     * A TextView that displays the name of the artist that produced the currently selected new art.
     */
    private TextView changeArtistText;
    /**
     * A TextView that displays the name of the artist that produced the currently selected
     * Dulwich Picture Gallery piece.
     */
    private TextView changeOldArtistText;
    /**
     * A TextView that displays the name of the currently selected Dulwich Picture Gallery piece.
     */
    private TextView changeOldArtNameText;
    /**
     * Placeholder activity to interface between the fragment and API methods that require an
     * activity.
     */
    private Activity theActivity;
    /**
     * Detects motion input from the user.
     */
    private GestureDetector theGestureDetector;

    public GameFragment() {
        // Required empty public constructor
    }


    /**
     * Sets up GameFragment.
     *
     * @return the GameFragment
     */
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Constructor for fragment.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the savedInstanceState
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        /**
         * Sets up TextViews and assigns resources to them.
         */
        changeImageChoiceNumberText = (TextView) v.findViewById(R.id.currentRotatedPicture);
        changeImageNumberText = (TextView) v.findViewById(R.id.currentGameArtwork);
        changeArtistText = (TextView) v.findViewById(R.id.currentNewArtist);
        changeArtistText.setTypeface(null, Typeface.BOLD);
        changeOldArtistText = (TextView) v.findViewById(R.id.currentSelectedOldArtworkName);
        changeOldArtistText.setTypeface(null, Typeface.BOLD);
        changeOldArtNameText = (TextView) v.findViewById(R.id.currentSelectedOldArtworkArtist);
        changeOldArtNameText.setTypeface(null, Typeface.BOLD);

        /**
         * Sets up images / animations, and assigns resources to them.
         */
        artworkToMatch = (ImageView) v.findViewById(R.id.imageView);
        imageButtonsArray[0] = (ImageButton) v.findViewById(R.id.imageButton1);
        imageButtonsArray[1] = (ImageButton) v.findViewById(R.id.imageButton2);
        imageButtonsArray[2] = (ImageButton) v.findViewById(R.id.imageButton3);
        imageButtonsArray[3] = (ImageButton) v.findViewById(R.id.imageButton4);
        theVa = (ViewAnimator) v.findViewById(R.id.viewAnimator);
        theVa.setInAnimation(AnimationUtils.loadAnimation(theActivity, android.R.anim.fade_in));
        theVa.setOutAnimation(theActivity, android.R.anim.fade_out);

        /**
         * Sets up motion detection so user can interface with this class.
         */
        theGestureDetector = new GestureDetector(getActivity().getBaseContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    /**
                     * Detects when user makes a single tap motion on the ViewAnimator. If the
                     * ImageButton that has been selected is the correct choice, a "correct"
                     * CustomDialogClass is displayed to the user. If an incorrect choice is
                     * selected, an "incorrect" CustomDialogClass is displayed to the user.
                     *
                     * In both cases, this class will check if all 20 questions have been attempted.
                     * If not, the game class will select a random question that has not been
                     * attempted.
                     *
                     * If all 20 questions have been attempted, the game class will check the user's
                     * score. If this score is below 20, a "try again" complete screen will be
                     * displayed. If this score is 20, a congratulatory screen will be displayed
                     * instead.
                     *
                     * @param e the MotionEvent
                     * @return always returns true
                     */
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
                            }, 1200);
                        } else {
                            final CustomDialogClass cdd = new CustomDialogClass(theActivity);
                            cdd.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    cdd.dismiss();
                                }
                            }, 1200);
                        }
                        if (!Game.allSetsComplete()) {
                            loadData();
                        } else {
                            // if all 20 choices guessed correctly, run the GameComplete activity
                            if (Game.score() == 20) {
                                showCompleteScreen20(getView());
                            } else {
                                showCompleteScreen(getView());
                            }
                        }
                        return true;
                    }

                    /**
                     * Detects when the user makes a swipe left or swipe right motion on the
                     * ViewAnimator, and changes the currently displayed choice of image
                     * accordingly. If the image selected is number 1 and the user swipes left, the
                     * image number will be rotated around to 4. If the image selected is 4 and the
                     * user swipes right, the image number will be rotated around to 1.
                     *
                     * This also change the text displayed in the TextViews indicating the image
                     * choice number, the Dulwich Picture Gallery piece name and artist.
                     *
                     * @param e1 first MotionEvent
                     * @param e2 second MotionEvent
                     * @param velocityX direction of movement (X-axis)
                     * @param velocityY direction of movement (Y-axis)
                     * @return always true
                     */
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
/**
 * Sets up the gesture detection listener for selecting an image choice in onSingleTapConfirmed.
 * Ordinarily the code in onSingleTapConfirmed would be in the OnTouchListener, but because the
 * OnTouchListener is a specialised version of GestureDetector, it is ignored when a more general
 * GestureDetector is being used.
 *
 * Also resets the currently displayed image to 1 when a new question is launched.
 */
        for (int t = 0; t < 4; t++) {
            imageButtonsArray[t].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return theGestureDetector.onTouchEvent(event);
                }
            });
        }
        theVa.setDisplayedChild(0);
/**
 * Sets up the button used in the help screen displayed when the game is first run, or is reset.
 * When pressed, this removes the help screen and displays the main game screen. This is saved in
 * SharedPreferences.
 */
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
/**
 * Detects if the game has been previously started. If showHelp has been set to false in
 * SharedPreferences, this is persistently saved and the help screen will no longer be displayed
 * when the game is started, until the user presses the reset button.
 */
        SharedPreferences gameUiData = theActivity.getSharedPreferences("gameUi", 0);
        if (!gameUiData.getBoolean("showHelp", true)) {
            v.findViewById(R.id.gameHelpFrame).setVisibility(View.GONE);
        }
/**
 * Retrieves the next question from the game class, and fetches the relevant string and drawable
 * resources to be used for the game.
 */
        game = Game.getNextGame(theActivity);
        loadData();

        /**
         * Detects if all 20 questions have been attempted by the user. If this is the case, if the
         * user has a score of 20, the congratulatory screen is displayed that awards them the
         * Fake Spotter badge. If their score is less than 20, another screen is displayed asking
         * the user to try the game again.
         */
        if (Game.allSetsComplete()) {
            if (Game.score() == 20) {
                showCompleteScreen20(v);
            } else {
                showCompleteScreen(v);
            }
        }
/**
 * Sets up reset button. When clicked, the progress of the user will be erased and they will restart
 * at question one. This will also make the help screen visible again.
 */
        ImageButton resetGameButton = (ImageButton) v.findViewById(R.id.gameResetButton);
        resetGameButton.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                theActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Game.reset();
                        game = Game.getNextGame(theActivity);
                        loadData();
                        theActivity.findViewById(R.id.gameHelpFrame).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        return v;
    }

    /**
     * When the user returns to the game, the game class will select a new, random question that
     * user has not yet attempted.
     *
     * @param activity the activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        theActivity = activity; //save this for later
        game = Game.getNextGame(theActivity);

    }

    /**
     * Fetches a new, random question that the user has not yet answered. Also fetches the resource
     * names for the strings and drawables needed for this question from the game class, and assigns
     * these resources to their relevant TextViews and ImageButtons.
     */
    void loadData() {
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

    /**
     * Completion screen that is displayed when the user gets less than 20 questions correct.
     * Displays the user's score, a reset button that allows the game to be restarted, and a share
     * button which opens a native Android share dialog that will send a string containing the
     * user's score in the game
     *
     * Removes the main game screen from view and displays the game complete screen.
     *
     * @param v the view
     */
    void showCompleteScreen(View v) {
        TextView displayCorrectAnswers = (TextView) v.findViewById(R.id.textViewGameComplete1);
        displayCorrectAnswers.setText("Well done, you completed the Fake Spotter game! You got " + Game.score() + "/20 questions correct.");
        Button shareButton = (Button) v.findViewById(R.id.gameShareButton);
        shareButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                share();
                return true;
            }
        });
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
        v.findViewById(R.id.gameCompleteFrame).setVisibility(View.VISIBLE);
        v.findViewById(R.id.gameFrame).setVisibility(View.GONE);
    }

    /**
     * Completion screen that is displayed when the user gets all 20 questions correct.
     * Displays a message congratulating the user, a reset button that allows the game to be
     * restarted, and a share button which opens a native Android share dialog that will send a
     * string containing the user's score in the game. The "Fake Spotter" badge is also activated
     * and a notification popup for this displayed.
     * <p/>
     * Removes the main game screen from view and displays the game complete screen.
     *
     * @param v the view
     */
    void showCompleteScreen20(View v) {
        if (!Core.getBadgeStatus(7)) {
            Core.notifyBadgeEarned(BadgesFragment.badges.get(7));
            Core.setBadgeCompleted(7);
        }
        Button shareButton2 = (Button) v.findViewById(R.id.gameShareButton2);
        shareButton2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                share();
                return true;
            }
        });
        Button restartGameButton = (Button) v.findViewById(R.id.gameRestartButton2);
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
                        theActivity.findViewById(R.id.gameComplete20Frame).setVisibility(View.GONE);
                    }
                });
            }
        });
        v.findViewById(R.id.gameComplete20Frame).setVisibility(View.VISIBLE);
        v.findViewById(R.id.gameFrame).setVisibility(View.GONE);

    }

    /**
     * Sets up native Android share dialog. Will send a string message containing the user's score
     * in the game.
     */
    void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //Uri uri = Uri.parse("android.resource:team3m.dulwichoutdoorgallery" + R.drawable.fakespotter);
        //shareIntent.setType("*/*");
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I guessed " + Game.score() + "/20 pictures right in the Dulwich Outdoor Gallery 'Fake Spotter' game!");
        //shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}