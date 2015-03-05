package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class Core {

    final static public String APP_KEY = "taiirhe19lvs791";
    final static public String APP_SECRET = "wlq0gei4ofhppca";

    public static ArrayList<Art> Gallery = new ArrayList<Art>(){
        {
            add(new Art("Landscape with Sportsmen and Game, 2013" ,
                    "ROA's dog is almost the only painting that has caused some controversy. He was given a portrait orientated wall, ruling out many " +
                            "Dulwich Picture Gallery animals. He adapted the position of the 'scrawny dog' in the Pynacker painting to fit his 'canvas'." ,
                    "Roa" , new Art("Landscape with Sportsmen and Game(detail), c.1665" , "" ,
                    "Adam Pynacker" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "roa_landscape_with_sportsmen_and_game1") ,
                    new ArrayList<String>() , 51.467437 , -0.072308 , "roa_new"));

            add(new Art("Landscape with Windmills near Haarlem, 2014" ,
                    "Kershaw, 74, sat on scaffolding for 10 days to create this astonishing mural. On a couple of afternoons he offered passing children the " +
                            "opportunity to make their additions to the bottom of the wall, where the river is in the original. With the help of POW, he painted " +
                            "a continuation of an existing hedge on which people could add flowers, insects and butterflies to his original masterpiece." ,
                    "Walter Kershaw" , new Art("Landscape with Windmills near Haarlem, 1650-52" , "" ,
                    "Jacob Van Ruisdael" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "walter_kershaw_landscape_with_windmills_near_haarlem") ,
                    new ArrayList<String>(), 51.467357 , -0.072510 , "walter_kershaw_new"));

            add(new Art("Girl at a Window, 2013" ,
                    "This wall is a combnation of System's interpretation of 'Girl at a Window' by Rembrandt and Remi's interpretation of 'The Triumph of David' " +
                            "by Poussin." ,
                    "Remi Rough and System" , new Art("Girl at a Window, 1645" , "" ,
                    "Rembrandt van Rijn" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "remi_rough_and_system_girl_at_a_window") ,
                    new ArrayList<String>(), 51.461959 , -0.079336 , "remi_rough_and_system_new"));

            add(new Art("Fight Club, 2013" ,
                    "Conor is taking Le Brun's 'Massacre of the Innocents' as a source. His interpretation, filtered through the lens of George Bellows, is " +
                            "a portrayal of global powers turning on themselves ('the massacre of the not-so innocent'). The Dulwich wall was the first in a " +
                            "series of murals depicting this struggle, and is continued in New York and Puerto Rico." ,
                    "Conor Harrington" , new Art("The Massacre of the Innocents, c. 1660" , "" ,
                    "Charles Le Brun" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "conor_harrington_the_massacre_of_the_innocents") ,
                    new ArrayList<String>() , 51.460617 , -0.075041 , "conor_harrington_new"));

            add(new Art("Mandarina Chicharra, 2013" ,
                    "The Spanish-born Liqen painted a vibrant mural, covering the wall in the courtyard of a local school. His surreal imagery will either " +
                            "inspire the children by this hybrid orange and insect creature, or the children will have bad dreams." ,
                    "Liqen" , null ,
                    new ArrayList<String>() , 51.456748 , -0.071105 , ""));

            add(new Art("A Couple in a Landscape, 2012" ,
                    "The original painting is very probably a wedding portrait. It is likely to be a marriage of convenience as they don't seem to be in love " +
                            "and Gainsborough has separated them with a dying oak tree. Stik's couple are equally disinterested in each other and are " +
                            "separated by a drainpipe." ,
                    "Stik" , new Art("A Couple in a Landscape(detail), 1753" , "" ,
                    "Thomas Gainsborough" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_couple_in_a_landscape") ,
                    new ArrayList<String>() , 51.456641 , -0.075693 , "stik_couple_in_a_landscape_new"));

            add(new Art("Mushroom Markers" ,
                    "Although Christiaan's mushrooms do not appear in any Dulwich Picture Gallery paintings, he made good markers of a route for a walk that " +
                            "takes in most of the walls and ends at Dulwich Picture Gallery. He put up four mushrooms but one was objected to so the shop " +
                            "owner on whose roof it was, was forced to take it down." ,
                    "Christiaan Nagel " , null ,
                    new ArrayList<String>(), 51.455987 , -0.076507 , ""));

            add(new Art("The Guardian Angel, 2012" ,
                    "The guardian angel is showing mankind the divine light, the true path, and is preventing the child from 'stubbing its toe on the rock " +
                            "of injustice, scratching itself on the thorny branch of life, and tumbling down the precipice of sin'. Stik has used the low " +
                            "energy street light and the potted plants to good effect." ,
                    "Stik" , new Art("The Guardian Angel, 1716" , "" ,
                    "Marcantonio Franceschini" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_the_guardian_angel") ,
                    new ArrayList<String>() , 51.456128 , -0.077322 , "stik_the_guardian_angel_new"));

            add(new Art("Girl with owl head and St. Sebastian, 2014" ,
                    "Dscreet's first painting was an interpretation of 'Judith with the head of Holofernes', but the wall owner thought there were too many " +
                            "knives on the wall of her shop, so had Dscreet's piece painted over. So he did a piece with a mate that everyone could enjoy, a " +
                            "girl wearing an owl head." ,
                    "Dscreet and Michael Beerens" , null ,
                    new ArrayList<String>(), 51.455987 , -0.076507 , ""));

            add(new Art("New World Revolution, 2013" ,
                    "Mear's mixed race mother and child are challenging and demanding. Paradoxically they are more symbolic than the Catholic version " +
                            "('The Madonna of the Rosary'). His composition was not specifically designed for the wall. He used an existing work of his, " +
                            "then drew in the outlines and afterwards added colour. He changed the words on the halo and added a butterfly." ,
                    "Mear One 2013" , new Art("The Madonna of the Rossary(detail), 1670-80" , "" ,
                    "Bartolome Murillo" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "mear_one_the_virgin_of_the_rosary") ,
                    new ArrayList<String>() , 51.454564 , -0.077177 , "mear_one_new"));

            add(new Art("Art House, 2013" ,
                    "Lightbox offered a whole house, up for demolition, to the project. The house was painted inside and out by numerous artists and became " +
                            "the focal point for 'Baroque the Streets' Street Art Festival in May 2013." ,
                    "Run, The Rolling People, Christiaan Nagel, Citizen Kane, Malarki, My Dog Sighs" , null ,
                    new ArrayList<String>(), 51.453037 , -0.077218 , ""));

            add(new Art("The Fall of Man, 2012" ,
                    "Although Adam's eyes are firmly fixed on the fascinating apple, he is rejecting it. Stik saw this as Eve offering herself and said " +
                            "that Adam was 'a dick' for refusing her sweet and generous offer. Stik's figures are stripped down to the pure essence of a " +
                            "human being. His people are not individuals who may be beautiful or ugly, old or young, they are generalised figures packed with " +
                            "feelings that everyone can recognise." ,
                    "Stik" , new Art("The Fall of Man, c.1520-30" , "" ,
                    "Pieter Coecke van Aelst" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_the_fall_of_man") ,
                    new ArrayList<String>() , 51.453183 , -0.078809 , "stik_the_fall_of_man_new"));

            add(new Art("The Triumph of David, 2013" ,
                    "Phlegm originally wanted to interpret 'The Judde Memorial', British School. It includes a skull and a dead body, perfectly normal " +
                            "subjects for paintings in the 16th century, but not so acceptable nowadays. The wall is owned by Charlotte, mother of nine year " +
                            "old Kaius who goes to the school around the corner. It is passed by primary school children all the time. When Phlegm realised this, " +
                            "he decided that the subject of matter was not appropriate to the area and changed it to the trumpeter in the 'The Triumph of David'." ,
                    "Phlegm" , new Art("The Triumph of David, 1628-31" , "" ,
                    "Nicolas Poussin" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "phlegm_triumph_of_david") ,
                    new ArrayList<String>() , 51.451588 , -0.071564 , "phlegm_new"));

            add(new Art("Queen Bee on a Throne, 2013" ,
                    "" ,
                    "Nunca" , null ,
                    new ArrayList<String>() ,51.449180 , -0.074245 , ""));

            add(new Art("Elizabeth and Mary Linley, 2012" ,
                    "Elizabeth was one of the foremost sopranos of the day, talented, beautiful and earning a great deal from her concerts. Mary had a successful " +
                            "career as a singer and actress. The sisters are in costly, fashionable clothes and holding objects representing their careers. Stik had " +
                            "represented only the relationship between them. They are comfortable with each other, but in their own separate worlds. It is this, not their " +
                            "youth, beauty or talents, that Stik encapsulates." ,
                    "Stik" , new Art("Elizabeth and Mary Linley(detail), c.1722" , "" ,
                    "Thomas Gainsborough" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_elizabeth_and_mary_linley") ,
                    new ArrayList<String>() , 51.446656 , -0.073634 , "stik_elizabeth_and_mary_linley_new"));

            add(new Art("St. Sebastian, 2014" ,
                    "" ,
                    "Michael Beerens" , null ,
                    new ArrayList<String>(), 51.447706 , -0.076339 , ""));

            add(new Art("Three Boys, 2012" ,
                    "Street children in Seville, probably orphaned by the plague, are negociating for food and drink. Stik's children are negotiating too, but " +
                            "for what can only be guessed at. Class 3S of Dulwich Hamlet Primary School helped Stik to paint the 'Three Boys' mural: 'The children " +
                            "responded immediately to his gentle self-effacing manner and really engaged with the simplicity and emotions of his painting.'(Cathy " +
                            "Skelton, Class Teacher)." ,
                    "Stik" , new Art("Three Boys(detail), c.1670" , "" ,
                    "Bartolome Esteban Murillo" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_three_boys") ,
                    new ArrayList<String>() , 51.445235 , -0.078621 , "stik_three_boys_new"));

            add(new Art("Joseph receiving Pharaoh's Ring, 2013" ,
                    "The subject is from Genesis XLI, 42: appointing Joseph ruler over Egypt, Pharaoh 'took off his ring from his hand, and put it upon " +
                            "Joseph's hand'. Thierry Noir concentrates on the interaction(or lack of) between the people only. His simple technique works " +
                            "well next to Stik's wall and near the children's playground in Dulwich Park." ,
                    "Thierry Noir" , new Art("Joseph receiving Pharaoh's Ring, 1733-35" , "" ,
                    "Giambattista Tiepolo" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "thierry_noir_joseph_receiving_pharaohs_ring") ,
                    new ArrayList<String>() , 51.445275 , -0.079029 , "thierry_noir_new1"));

            add(new Art("Saint Catherine of Siena, 2013" ,
                    "Ben's tiny works of art are amazingly accurate reproductions of the Dulwich Picture Gallery paintings. He reproduces in miniature the " +
                            "composition, colour, tone, background and even the expressions on the faces. He adds his own quirky interpretations. Ben painted " +
                            "Catherine running away, having a vision of a banana." ,
                    "Ben Wilson" , new Art("Saint Catherine of Siena(detail), 1665-70" , "" ,
                    "Carlo Dolci" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "") ,
                    new ArrayList<String>(), 51.445548 , -0.085312 , "ben_wilson_new1"));

            add(new Art("Dulwich Picture Gallery" ,
                    "Dulwich Picture Gallery, founded in 1811, is England's oldest public art gallery. It has one of the finest collections of Western " +
                            "European Baroque paintings in the world." ,
                    "Dulwich Gallery" , null ,
                    new ArrayList<String>(), 51.445936 , -0.086170 , ""));

            add(new Art("Samson and Delilah, 2013" ,
                    "Discovering the painting by Anthony van Dick in Dulwich Picture Gallery, David saw the story coloured in and picked out some great visual " +
                            "references to draw on, breaking down the visual allegory into basic parts: the long hair, the scissors, the broken heart, the beautiful " +
                            "girl and the powerful man. He uses colour symbolically, red for drama, danger, violence and incorporates the sacred yet fascinated eyes of " +
                            "the onlookers." ,
                    "David Shillinglaw" , new Art("Samson and Delilah, c.1618" , "" ,
                    "Anthony van Dyck" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "david_shillinglaw_samson_and_delilah") ,
                    new ArrayList<String>() , 51.452870 , -0.103013 , "david_shillinglaw_new"));

            add(new Art("Vase with Flowers, 2013" ,
                    "You can look for hours at van Huysum's painting. As you peer you spot more and more tiny ants, flies, woodlice, ladybirds and even a " +
                            "minute red snail. It is exactly the same when looking at MadC's wall. Don't stride past it. It is surprising and rewarding to " +
                            "look at it carefully." ,
                    "Mad C" , new Art("Vase with Flowers, c.1720" , "" ,
                    "Jan van Huysum" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "madc_vase_with_flowers") ,
                    new ArrayList<String>() , 51.441563 , -0.091361 , "madc_new"));

            add(new Art("Europa and the Bull, 2013" ,
                    "Reka chose the piece 'Europa and the Bull' because the imagery lent itself to his style. He paint a lot of female figures in motion using " +
                            "flowing hair as a way to give the piece a dynamic quality. In a sense it is moving on the wall. The project has been a great experience " +
                            "for Reka, as he rarely use an image as a reference to my artworks." ,
                    "Reka" , new Art("Europa and the Bull(detail, 17th century" , "" ,
                    "Guido Reni" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "reka_europa_and_the_bull") ,
                    new ArrayList<String>() , 51.427814 , -0.086899 , "reka_new"));

            add(new Art("Happy Hour, 2013" ,
                    "The work of art from Dulwich Picture Gallery that AP chose to interpret is a sketch. Before the days of porn in magazines and on the internet, " +
                            "men who wanted to gaze at naked women employed artists. It was acceptable to have a painting of naked females from Greek mythology " +
                            "hanging in your study, so 'The Three Graces' was a popular subject in some circles." ,
                    "Agent Provocateur(AP)" , new Art("The Three Graces, c.1636" , "" ,
                    "Sir Peter Paul Rubens" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "agent_provocateur_the_three_graces") ,
                    new ArrayList<String>() , 51.441069 , -0.056676 , "agent_provocateur_new"));

            add(new Art("The Translation of Saint Rita of Cascia, 2013" ,
                    "Saint Rita had wished to become a nun, but she submitted to her parents' will and married. After the violent death of her cruel husband, " +
                            "she was miraculously transported to the Augustinian convent of Cascia, near Spoleto. She is the patron saint of abused wives. Wearing " +
                            "similar flowing clothes, RUN's Saint Rita is careering down into the convent whereas Poussin's saint is journeying in a more sedate manner." ,
                    "RUN" , new Art("The Translation of Saint Rita of Cascia(detail), c.1630" , "" ,
                    "Nicolas Poussin" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "run_the_translation_of_st_rita") ,
                    new ArrayList<String>() , 51.438862 , -0.053918 , "run_the_translation_of_st_rita_new"));

            add(new Art("Three Boys, 2013" ,
                    "" ,
                    "Michael Beerens" , null ,
                    new ArrayList<String>() , 51.447739 , -0.074982 , ""));

            add(new Art("Eliza and Mary Davidson, 2012" ,
                    "" ,
                    "Stik" , new Art("Eliza and Mary Davidson, 1784" , "" ,
                    "Tilly Kettle" , null , new ArrayList<String>() , 51.445936 , -0.086170 , "stik_eliza_and_mary_davidson") ,
                    new ArrayList<String>() , 51.447408 , -0.075845 , "stik_eliza_and_mary_davidson_new"));
        }

    };


    public static ArrayList<Art> getGallery(){
        return Gallery;
    }

    static BadgeEarnedListener badgeEarnedListener;
    public static void setBadgeListener(BadgeEarnedListener listener){
        badgeEarnedListener = listener;
    }
    public static void notifyBadgeEarned(Badge b){
        if(badgeEarnedListener != null) badgeEarnedListener.completedBadge(b);
    }

    public static boolean getBadgeStatus(int n){
        return CoreActivity.preferences.getBoolean("_badge"+n , false);
    }

    public static void setBadgeCompleted(int n){
        CoreActivity.preferences.edit().putBoolean("_badge"+n,true).commit();
    }

    public static boolean getLocationtatus(int n){
        return CoreActivity.preferences.getBoolean("_location"+n , false);
    }

    public static void setLocationVisited(int n){
        CoreActivity.preferences.edit().putBoolean("_location"+n,true).commit();
    }

    public static void updateBadges(){
        int count = 0;
        for(int x =0; x<getGallery().size();x++){
            if(getLocationtatus(x))count++;
        }
        if(count == 3){
            if(!getBadgeStatus(3)) {
                notifyBadgeEarned(BadgesActivity.badges.get(3));
                setBadgeCompleted(3);
            }
        }

        if(count == 5){
            if(!getBadgeStatus(4)) {
                notifyBadgeEarned(BadgesActivity.badges.get(4));
                setBadgeCompleted(4);
            }
        }
    }

    public static void update(Context c){
        DropboxAPI.Entry dirent = null;
        try {
            dirent = CoreActivity.mDBApi.metadata("/saved/", 1000, null, true, null);
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        ArrayList<DropboxAPI.Entry> files = new ArrayList<DropboxAPI.Entry>();
        ArrayList<String> dir=new ArrayList<String>();
        int i = 0;
        for (DropboxAPI.Entry ent: dirent.contents)
        {
            files.add(ent);// Add it to the list of thumbs we can choose from
            dir.add(files.get(i++).path);
        }

        for(String s : dir){
            String name = s.split("/")[2];
            File folder = c.getFilesDir();
            File file = new File(folder.getAbsolutePath()+File.separator+name);

            OutputStream outputStream = null;
            boolean filee = file.exists();
            try {
                outputStream = c.openFileOutput(name, c.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                DropboxAPI.DropboxFileInfo info = CoreActivity.mDBApi.getFile(s, null, outputStream, null);
            } catch (DropboxException e) {
                e.printStackTrace();
            }
            
            if (name.endsWith(".txt")) {
            try {

                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                            c.openFileInput(name)));
                    String inputString;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((inputString = inputReader.readLine()) != null) {
                        stringBuffer.append(inputString + "\n");
                    }

                    String[] lines = stringBuffer.toString().split("\n");
                    Art art = new Art(lines[0], lines[2], lines[1], new Art(lines[5], lines[7], lines[6], null, null, 0, 0,"",""), null, Float.parseFloat(lines[3]), Float.parseFloat(lines[4]));
                    Core.getGallery().add(art);
                    Log.e("wuwuuwuuw", stringBuffer.toString());
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}


