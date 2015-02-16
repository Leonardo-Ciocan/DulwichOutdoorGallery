package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private boolean achieved = false;//achievement for logging
    private static Activity theActivity;
    private static ArrayList<Game> theSets = null;
    private String artist;
    private int artworkToMatch;
    private int[] possibleChoices;
    private int correctChoice;
    private boolean completed = false;

    //todo
    public void saving() {

        //not finished - saving the progress
        try {
            Boolean isNew = false;


            File file = new File("game.txt");

            // if file doesn't exist, then create it
            if (!file.exists()) {
                file.createNewFile();
                isNew = true;//first time ran
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            if (isNew) {
                bw.write("0\n3\n0");
            }

            //check if empty
            CharSequence fileContents = "";
            bw.append(fileContents);
            if (fileContents.length() < 3)
                bw.write("0\n3\n0");


            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //todo
    public void updateFile() {

    }

    private static void loadSets() {
        int event;
        Game cur = null;

        theSets = new ArrayList<>();
        XmlResourceParser data = theActivity.getResources().getXml(R.xml.games);

        try {
            event = data.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                event = data.getEventType();

                if (event == XmlPullParser.START_TAG) {
                    if (data.getName().contentEquals("pictureSet")) {
                        cur = new Game();

                        cur.artist = data.getAttributeValue(null, "artist");
                        cur.artworkToMatch = data.getAttributeResourceValue(null, "target", 0);
                        cur.possibleChoices[0] = data.getAttributeResourceValue(null, "choice0", 0);
                        cur.possibleChoices[1] = data.getAttributeResourceValue(null, "choice1", 0);
                        cur.possibleChoices[2] = data.getAttributeResourceValue(null, "choice2", 0);
                        cur.possibleChoices[3] = data.getAttributeResourceValue(null, "choice3", 0);
                        cur.correctChoice = data.getAttributeIntValue(null, "correct", 0);
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

    public static int progress() {
        if (theSets == null)
            loadSets();

        int res = 1;

        for (int t = 0; t < theSets.size(); t++)
            if (theSets.get(t).isCompleted())
                res++;

        return res;
    }

    public static boolean allSetsComplete() {
        if (theSets == null)
            loadSets();

        for (int t = 0; t < theSets.size(); t++) {
            if (!theSets.get(t).isCompleted())
                return false;
        }

        return true;
    }

    public static Game getNextGame(Activity caller) {
        theActivity = caller;
        if (theSets == null)
            loadSets();

        Random r = new Random();
        int n = r.nextInt(theSets.size());

        if (allSetsComplete())
            return theSets.get(n); //they're just playing for fun

        while (theSets.get(n).isCompleted()) // otherwise make sure it's a new game
            n = r.nextInt(theSets.size());
        return theSets.get(n);
    }

    private Game() { //intentionally private - only our static functions should create Games
        possibleChoices = new int[4];
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

    public boolean makeAGuess(int pictureNumber) {
        if (pictureNumber == correctChoice) {
            completed = true;
            return true;
        }
        return false;
    }

    public boolean isCompleted() {
        return completed;
    }
}