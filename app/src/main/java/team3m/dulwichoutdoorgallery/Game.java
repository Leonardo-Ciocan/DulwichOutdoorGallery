package team3m.dulwichoutdoorgallery;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;

public class Game {

    int progress = 0;// how far has the user gone through with the game 0-19
    int no_right = 0;//number of right guesses
    int no_guesses = 3; // guesses taken
    String picture = "";
    String pictures = "pic1,pic2,pic3.."; // CSV
    Boolean achieved = false;//achievement for logging

    static ArrayList<Integer> matches = new ArrayList<>();


    public static void main(String args[]) {


        //first number is the image
        //second number is it's matching photoshopped image

        matches.add(0,19);
        matches.add(1,18);
        matches.add(2,17);
        matches.add(3,16);
        matches.add(4,15);
        matches.add(5,14);
        matches.add(6,13);
        matches.add(7,12);
        matches.add(8,11);
        matches.add(9,10);
        matches.add(10,9);
        matches.add(11,8);
        matches.add(12,7);
        matches.add(13,6);
        matches.add(14,5);
        matches.add(15,4);
        matches.add(16,3);
        matches.add(17,2);
        matches.add(18,1);
        matches.add(19,0);



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
            if(isNew)
            {
                bw.write("0\n3\n0");
            }

            //check if empty
            CharSequence fileContents = "";
            bw.append(fileContents);
            if(fileContents.length()<3)
                bw.write("0\n3\n0");


            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //check if the guess is right
    public boolean checkMatch(int index, int value)
    {
        if(matches.get(index) == value)
            return true;
        else
        return false;
    }
    //todo
    public void updateFile()
    {

    }


    //if achieved 20 right guesses -> award achievement
    public boolean checkAchievement(int no)
    {
        if(no==20)
        {
            this.achieved = true;

            return true;
        }

        return false;
    }




}
