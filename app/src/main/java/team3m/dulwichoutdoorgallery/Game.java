package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Bookkeeping class for the main GameFragment class.
 */
public class Game {
    /**
     * Placeholder activity to interface between the fragment and API methods that require an
     * activity.
     */
    private static Activity theActivity;
    /**
     * Represents the 20 questions for the game.
     */
    private static ArrayList<Game> theSets = null;
    /**
     * Store's the new artist's name fora particular question.
     */
    private String artist;
    /**
     * Stores the resource name for the street art drawable for a particular question.
     */
    private String artworkResourceName;
    /**
     *
     */
    private int artworkToMatch;
    /**
     *
     */
    private int[] possibleChoices;
    /**
     * Stores the index of the int array which is the correct choice for a particular question.
     */
    private int correctChoice;
    /**
     * Stores whether the user has attempted to answer a particular question.
     */
    private boolean completed = false;
    /**
     * Stores whether the user has got a particular attempt at a question correct.
     */
    private boolean correct = false;
    /**
     * Stores the names of the four Dulwich Picture Gallery pieces for a particular question.
     */
    private String[] artworkName = new String[4];
    /**
     * Stores the names of the artists of the four Dulwich Picture Gallery pieces for a particular
     * question.
     */
    private String[] artworkArtist = new String[4];

    /**
     * Sets up a new object of the Game class (an instance of a question.)
     */
    private Game() { //intentionally private - only our static functions should create Games
        possibleChoices = new int[4];
    }

    /**
     * Initialises a new set of question sets.
     */
    private static void loadSets() {
        int event;
        Game cur = null;

        SharedPreferences completionData = theActivity.getSharedPreferences("Completion", 0);

        // Assigns contents of games xml file to xml parser
        theSets = new ArrayList<>();
        XmlResourceParser data = theActivity.getResources().getXml(R.xml.games);

        /**
         * Reads xml data and creates a new game object for each pictureSet listed in xml.
         * Each pictureSet corresponds to one question in the game. The resource names listed in
         * each attribute are saved to each corresponding game object.
         */
        try {
            event = data.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                event = data.getEventType();

                if (event == XmlPullParser.START_TAG) {
                    if (data.getName().contentEquals("pictureSet")) {
                        cur = new Game();

                        cur.artist = data.getAttributeValue(null, "artist");
                        //use the resource name to save completion data across runs
                        cur.artworkResourceName = data.getAttributeValue(null, "target");
                        cur.artworkToMatch = data.getAttributeResourceValue(null, "target", 0);
                        cur.possibleChoices[0] = data.getAttributeResourceValue(null, "choice0", 0);
                        cur.possibleChoices[1] = data.getAttributeResourceValue(null, "choice1", 0);
                        cur.possibleChoices[2] = data.getAttributeResourceValue(null, "choice2", 0);
                        cur.possibleChoices[3] = data.getAttributeResourceValue(null, "choice3", 0);
                        cur.correctChoice = data.getAttributeIntValue(null, "correct", 0);
                        cur.completed = completionData.getBoolean(cur.artworkResourceName, false);
                        cur.correct = completionData.getBoolean(cur.artworkResourceName + "_correct", false);
                        cur.artworkName[0] = data.getAttributeValue(null, "choice0name");
                        cur.artworkName[1] = data.getAttributeValue(null, "choice1name");
                        cur.artworkName[2] = data.getAttributeValue(null, "choice2name");
                        cur.artworkName[3] = data.getAttributeValue(null, "choice3name");
                        cur.artworkArtist[0] = data.getAttributeValue(null, "choice0artist");
                        cur.artworkArtist[1] = data.getAttributeValue(null, "choice1artist");
                        cur.artworkArtist[2] = data.getAttributeValue(null, "choice2artist");
                        cur.artworkArtist[3] = data.getAttributeValue(null, "choice3artist");
                    }
                } else if (event == XmlPullParser.END_TAG) {
                    if (data.getName().contentEquals("pictureSet")) {
                        theSets.add(cur);
                        cur = null;
                    }
                }
                data.next();
            }
        } catch (IOException | XmlPullParserException x) {
            x.printStackTrace();
        }
    }

    /**
     * Calculates the number of correct answers the user has got so far. If no question sets exist,
     * these are generated.
     *
     * @return number of correctly answered questions
     */
    public static int score() {
        if (theSets == null)
            loadSets();

        int res = 0;

        for (int t = 0; t < theSets.size(); t++) {
            if (theSets.get(t).correct)
                res++;
        }
        return res;
    }

    /**
     * Calculates the number of questions the user has attempted to answer. If no question sets
     * exist, these are generated.
     *
     * @return number of questions attempted by user
     */
    public static int progress() {
        if (theSets == null)
            loadSets();

        int res = 0;

        for (int t = 0; t < theSets.size(); t++)
            if (theSets.get(t).completed)
                res++;

        return res;
    }

    /**
     * Determines if the user has attempted to answer all the questions. If no question sets
     * exist, these are generated.
     *
     * @return true if all questions attempted, false if not
     */
    public static boolean allSetsComplete() {
        if (theSets == null)
            loadSets();

        for (int t = 0; t < theSets.size(); t++) {
            if (!theSets.get(t).isCompleted())
                return false;
        }

        return true;
    }

    /**
     * Randomly selects a question from the question set. If the question has already been
     * attempted, questions will continue to be selected until a new one is found. If no question
     * sets exist, these are generated.
     *
     * @param caller the activity
     * @return next question, or null if no questions left
     */
    public static Game getNextGame(Activity caller) {
        theActivity = caller;
        if (theSets == null)
            loadSets();

        Random r = new Random();
        int n = r.nextInt(theSets.size());
        if (allSetsComplete())
            return null; // game over!

        while (theSets.get(n).isCompleted()) // otherwise make sure it's a new game
            n = r.nextInt(theSets.size());
        return theSets.get(n);
    }

    /**
     * Resets the user's progress in the game.
     */
    public static void reset() {
        SharedPreferences.Editor updateCompletionData = theActivity.getSharedPreferences("Completion", 0).edit();

        if (theSets == null)
            loadSets();

        for (int t = 0; t < theSets.size(); t++) {
            Game g = theSets.get(t);
            g.correct = false;
            g.completed = false;
            updateCompletionData.putBoolean(g.artworkResourceName, false).
                    putBoolean(g.artworkResourceName + "_correct", g.correct);
        }
        updateCompletionData.apply();
    }

    public int getIdOfArtworkToMatch() {
        return artworkToMatch;
    }

    public String getArtistName() {
        return artist;
    }

    public int[] getPossibleChoices() {
        return possibleChoices;
    }

    public String getArtworkName(int i) {
        return artworkName[i];
    }

    public String getArtworkArtist(int i) {
        return artworkArtist[i];
    }

    /**
     * Sets the question as having been attempted. If the selected picture was the correct choice,
     * this question's boolean completed is set to true.
     *
     * @param pictureNumber the number of the image choices selected
     * @return true if image choice was correct, false if choice was incorrect
     */
    public boolean makeAGuess(int pictureNumber) {
        SharedPreferences.Editor updateCompletionData = theActivity.getSharedPreferences("Completion", 0).edit();

        completed = true;
        if (pictureNumber == correctChoice)
            correct = true;

        updateCompletionData.putBoolean(this.artworkResourceName, true).
                putBoolean(this.artworkResourceName + "_correct", correct)
                .apply();
        return correct;
    }

    /**
     * Checks if question has already been attempted.
     *
     * @return true if question has been attempted, or false if it has not
     */
    boolean isCompleted() {
        return completed;
    }
}