package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class Core {

    final static public String APP_KEY = "taiirhe19lvs791";
    final static public String APP_SECRET = "wlq0gei4ofhppca";

    public static ArrayList<Art> Gallery = new ArrayList<Art>(){
        {
            add(new Art("Europa and the Winged Bird, 2014" ,
                    "Faith selected Guido Reni's 'Europa and the Bull', which soon found its way onto the streets of Dulwich.  The absence of the bull and " +
                    "the introduction of a guiding bird are suggestive of a premonition of the abduction to come, her inner emotions and thoughts or perhaps " +
                    "a new interpretation of the ancient fable." ,
                    "Faith47" , new Art("Europa and the Bull, 17th century" , "Guido Reni managed one of the busiest studios of the 17th century. His most popular " +
                    "compositions can exist in several versions, with varying degrees of involvement of the master." ,
                    "Guido Reni", null, new ArrayList<String>(), 51.445936, -0.086170, "faith47_europa_and_the_bull", "a.1387311134920959.1073741826.1387308654921207/1387311151587624"),
                    new ArrayList<String>(), 51.471419, -0.064335, "faith47_new", "a.1387325508252855.1073741827.1387308654921207/1387328914919181"));

            add(new Art("Landscape with Sportsmen and Game, 2013" ,
                    "ROA's dog is almost the only painting that has caused some controversy. He was given a portrait orientated wall, ruling out many " +
                            "Dulwich Picture Gallery animals. He adapted the position of the 'scrawny dog' in the Pynacker painting to fit his 'canvas'." ,
                    "Roa" , new Art("Landscape with Sportsmen and Game, c.1665" , "This landscape is regarded as one of Pynacker's outstanding achievements, " +
                    "painted in his mature style of the 1660s when he was living in Amsterdam. It has a cool tonality and sharp definition, with an intense " +
                    "focus on the foreground group of birches to the left. The large blue leaves in the foreground are the result of a chemical change over time: " +
                    "yellow lake may have been laid as a glaze over the blue, which then either faded or was accidentally removed during former restorations." ,
                    "Adam Pynacker", null, new ArrayList<String>(), 51.445936, -0.086170, "roa_landscape_with_sportsmen_and_game1", "a.1387311134920959.1073741826.1387308654921207/1387317981586941"),
                    new ArrayList<String>(), 51.467437, -0.072308, "roa_new", "a.1387325508252855.1073741827.1387308654921207/1387331974918875"));

            add(new Art("Landscape with Windmills near Haarlem, 2014" ,
                    "Kershaw, 74, sat on scaffolding for 10 days to create this astonishing mural. On a couple of afternoons he offered passing children the " +
                            "opportunity to make their additions to the bottom of the wall, where the river is in the original. With the help of POW, he painted " +
                            "a continuation of an existing hedge on which people could add flowers, insects and butterflies to his original masterpiece." ,
                    "Walter Kershaw" , new Art("Landscape with Windmills near Haarlem, 1650-52" , "The Groote Kerk, Haarlem, is seen in the distance. A horse " +
                    "and figures centre left (since overpainted) and a rider and boy on the right were shown by pigment analysis to be post 17th-century additions " +
                    "and were removed in 1997. They are partially recorded in a copy made by Constable in 1831." ,
                    "Jacob van Ruisdael", null, new ArrayList<String>(), 51.445936, -0.086170, "walter_kershaw_landscape_with_windmills_near_haarlem", "a.1387311134920959.1073741826.1387308654921207/1387324591586280"),
                    new ArrayList<String>(), 51.467357, -0.072510, "walter_kershaw_new", "a.1387325508252855.1073741827.1387308654921207/1387337328251673"));

            add(new Art("Girl at a Window, 2013" ,
                    "This wall is a combnation of System's interpretation of 'Girl at a Window' by Rembrandt and Remi's interpretation of 'The Triumph of David' " +
                            "by Poussin." ,
                    "Remi Rough and System" , new Art("Girl at a Window, 1645" , "Painted when Rembrandt was 39, this painting falls somewhere between genre " +
                    "and portraiture. The girl’s identity remains uncertain; in the past she has been described as a courtesan, a Jewish bride or an historical " +
                    "or Biblical figure. It is more widely accepted that she is a servant girl; her rosy, tanned complexion along with her brown arms implies " +
                    "she worked outdoors." ,
                    "Rembrandt van Rijn", null, new ArrayList<String>(), 51.445936, -0.086170, "remi_rough_and_system_girl_at_a_window", "a.1387311134920959.1073741826.1387308654921207/1387317278253678"),
                    new ArrayList<String>(), 51.461959, -0.079336, "remi_rough_and_system_new", "a.1387325508252855.1073741827.1387308654921207/1387331571585582"));

            add(new Art("Fight Club, 2013" ,
                    "Conor is taking Le Brun's 'Massacre of the Innocents' as a source. His interpretation, filtered through the lens of George Bellows, is " +
                            "a portrayal of global powers turning on themselves ('the massacre of the not-so innocent'). The Dulwich wall was the first in a " +
                            "series of murals depicting this struggle, and is continued in New York and Puerto Rico." ,
                    "Conor Harrington" , new Art("The Massacre of the Innocents, c.1660" , "The subject is from Matthew II, 16-18: having learnt from the " +
                    "wise men of the birth in Bethlehem of the King of the Jews, Herod ordered that the young children of the city be put to death. The " +
                    "picture was begun in 1647, or shortly after, but was left unfinished probably until the mid-1660s, when it was completed for Gedeon du " +
                    "Metz, keeper of the Royal Treasury. There are several preliminary drawings in the Louvre." ,
                    "Charles Le Brun", null, new ArrayList<String>(), 51.445936, -0.086170, "conor_harrington_the_massacre_of_the_innocents", "a.1387311134920959.1073741826.1387308654921207/1387312898254116"),
                    new ArrayList<String>(), 51.460617, -0.075041, "conor_harrington_new", "a.1387325508252855.1073741827.1387308654921207/1387328184919254"));

            add(new Art("A Couple in a Landscape, 2012" ,
                    "The original painting is very probably a wedding portrait. It is likely to be a marriage of convenience as they don't seem to be in love " +
                            "and Gainsborough has separated them with a dying oak tree. Stik's couple are equally disinterested in each other and are " +
                            "separated by a drainpipe." ,
                    "Stik" , new Art("A Couple in a Landscape, 1753" , "This is one of Gainsborough’s early conversation pieces, which forms part of a " +
                    "succession of full-length double portraits of husband and wife, such as Mr and Mrs Andrews (National Gallery, London) and Sarah and John " +
                    "Joshua Kirby (National Portrait Gallery, London). There is equal emphasis upon the couple and the surrounding landscape. The decaying tree was a " +
                    "popular compositional device used in 17th-century Dutch landscape painting, especially the work of Jacob van Ruisdael and Jan Wijnants, whom" +
                    "Gainsborough greatly admired." ,
                    "Thomas Gainsborough", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_couple_in_a_landscape", "a.1387311134920959.1073741826.1387308654921207/1387319091586830"),
                    new ArrayList<String>(), 51.456641, -0.075693, "stik_couple_in_a_landscape_new", "a.1387325508252855.1073741827.1387308654921207/1387332984918774"));

            add(new Art("Mushroom Marker" ,
                    "Although Christiaan's mushrooms do not appear in any Dulwich Picture Gallery paintings, he made good markers of a route for a walk that " +
                            "takes in most of the walls and ends at Dulwich Picture Gallery. He put up four mushrooms but one was objected to so the shop " +
                            "owner on whose roof it was, was forced to take it down." ,
                    "Christiaan Nagel" , null ,
                    new ArrayList<String>(), 51.455979, -0.076067, "mushroom_marker1", "a.1387325508252855.1073741827.1387308654921207/1387375498247856"));

            add(new Art("Mushroom Marker" ,
                    "Although Christiaan's mushrooms do not appear in any Dulwich Picture Gallery paintings, he made good markers of a route for a walk that " +
                            "takes in most of the walls and ends at Dulwich Picture Gallery. He put up four mushrooms but one was objected to so the shop " +
                            "owner on whose roof it was, was forced to take it down." ,
                    "Christiaan Nagel" , null ,
                    new ArrayList<String>(), 51.455981, -0.076418, "mushroom_marker2", "a.1387325508252855.1073741827.1387308654921207/1387375528247853"));

            add(new Art("The Guardian Angel, 2012" ,
                    "The guardian angel is showing mankind the divine light, the true path, and is preventing the child from 'stubbing its toe on the rock " +
                            "of injustice, scratching itself on the thorny branch of life, and tumbling down the precipice of sin'. Stik has used the low " +
                            "energy street light and the potted plants to good effect." ,
                    "Stik" , new Art("The Guardian Angel, 1716" , "Franceschini was taught by Carlo Cignani, a pupil of Albani, who was in turn trained by " +
                    "the Carracci in Bologna and Rome - he therefore represents the continuation of the best of the Bolognese classical school of painting. " +
                    "His skill is very obvious in the beautiful drawing and colour of this work of his maturity. This work is possibly one of the versions of " +
                    "this subject noted in Franceschini's account book on 5 August and 20 December 1716." ,
                    "Marcantonio Franceschini", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_the_guardian_angel", "a.1387311134920959.1073741826.1387308654921207/1387321604919912"),
                    new ArrayList<String>(), 51.456128, -0.077322, "stik_the_guardian_angel_new", "a.1387325508252855.1073741827.1387308654921207/1387335994918473"));

            add(new Art("St Sebastian, 2014" ,
                    "St Sebastian is a gay icon, so Beerens gave him a rainbow halo. His stag is as simple and dramatic as Reni's saint, but black on white " +
                     "rather than white on black." ,
                    "Michael Beerens" , new Art("Saint Sebastian, 1620-30s" , "Sebastian was a Roman soldier condemned to death by the Emperor Diocletian for " +
                    "aiding the Christians; his arrow wounds were not fatal and he was later clubbed to death. Reni's painting was one of the most celebrated at " +
                    "Dulwich in the nineteenth century, but was catalogued in 1880 as a studio work and in 1980 as a copy." ,
                    "Guido Reni", null, new ArrayList<String>(), 51.445936, -0.086170, "beerens_st_sebastian_original", "a.1387311134920959.1073741826.1387308654921207/1387377008247705"),
                    new ArrayList<String>(), 51.455987, -0.076507, "beerens_st_sebastian", "a.1387325508252855.1073741827.1387308654921207/1387375668247839"));

            add(new Art("New World Revolution, 2013" ,
                    "Mear's mixed race mother and child are challenging and demanding. Paradoxically they are more symbolic than the Catholic version " +
                            "('The Madonna of the Rosary'). His composition was not specifically designed for the wall. He used an existing work of his, " +
                            "then drew in the outlines and afterwards added colour. He changed the words on the halo and added a butterfly." ,
                    "Mear One" , new Art("The Virgin of the Rosary, 1675-80" , "Murillo’s picture is an excellent example of the artist’s late style (his so-called estilo " +
                    "vaporoso or ‘vaporous style’). The contours of the figures of the Virgin and the Christ Child have been softened and light suffuses the composition, " +
                    "enveloping them in a gentle glow. Although both figures have been idealized to a great extent, they retain a familiar humanity, particularly in the carefully " +
                    "combed hair of the child and the engaging way he toys with the beads of the rosary in a characteristic Murillesque touch." ,
                    "Bartolome Esteban Murillo", null, new ArrayList<String>(), 51.445936, -0.086170, "mear_one_the_virgin_of_the_rosary", "a.1387311134920959.1073741826.1387308654921207/1387315221587217"),
                    new ArrayList<String>(), 51.454564, -0.077177, "mear_one_new", "a.1387325508252855.1073741827.1387308654921207/1387330554919017"));

            add(new Art("The Fall of Man, 2012" ,
                    "Although Adam's eyes are firmly fixed on the fascinating apple, he is rejecting it. Stik saw this as Eve offering herself and said " +
                            "that Adam was 'a dick' for refusing her sweet and generous offer. Stik's figures are stripped down to the pure essence of a " +
                            "human being. His people are not individuals who may be beautiful or ugly, old or young, they are generalised figures packed with " +
                            "feelings that everyone can recognise." ,
                    "Stik" , new Art("The Fall of Man, c.1520-30" , "At the edge of a forest, Eve hands Adam the forbidden fruit from the tree of the " +
                    "knowledge of good and evil (Genesis, Chapter III, 6). Although Adam recoils, his eyes are firmly fixed on the temptation. In the distance " +
                    "to the left is their resultant expulsion from Eden (Genesis, III, 23-4). Flemish master Pieter Coecke van Aelst established a successful studio " +
                    "in Brussels in 1544, where many important Flemish artists were trained - including possibly his future son-in-law, Pieter Brueghel the Elder." ,
                    "Follower of Pieter Coecke van Aelst", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_the_fall_of_man", "a.1387311134920959.1073741826.1387308654921207/1387320694920003"),
                    new ArrayList<String>(), 51.453183, -0.078809, "stik_the_fall_of_man_new", "a.1387325508252855.1073741827.1387308654921207/1387335448251861"));

            add(new Art("The Triumph of David, 2013" ,
                    "Phlegm originally wanted to interpret 'The Judde Memorial', British School. It includes a skull and a dead body, perfectly normal " +
                            "subjects for paintings in the 16th century, but not so acceptable nowadays. The wall is owned by Charlotte, mother of nine year " +
                            "old Kaius who goes to the school around the corner. It is passed by primary school children all the time. When Phlegm realised this, " +
                            "he decided that the subject of matter was not appropriate to the area and changed it to the trumpeter in the 'The Triumph of David'." ,
                    "Phlegm" , new Art("The Triumph of David, 1628-31" , "One of Poussin’s first great masterpieces, The Triumph of David demonstrates the " +
                    "artist’s increasing concern in the early 1630s with employing harmonious design to instil a sense of structural clarity to the human " +
                    "expression and dramatic storytelling in his paintings. Taken from the Bible’s Book of Samuel, the scene depicts David’s triumphal entry " +
                    "into Jerusalem after defeating the Philistines’ champion Goliath of Gath, an eight-foot giant who David had outwitted by striking his " +
                    "forehead with a well-aimed stone from his sling." ,
                    "Nicolas Poussin", null, new ArrayList<String>(), 51.445936, -0.086170, "phlegm_triumph_of_david", "a.1387311134920959.1073741826.1387308654921207/1387316671587072"),
                    new ArrayList<String>(), 51.451588, -0.071564, "phlegm_new", "a.1387325508252855.1073741827.1387308654921207/1387331068252299"));

            add(new Art("Elizabeth and Mary Linley, 2012" ,
                    "Elizabeth was one of the foremost sopranos of the day, talented, beautiful and earning a great deal from her concerts. Mary had a successful " +
                            "career as a singer and actress. The sisters are in costly, fashionable clothes and holding objects representing their careers. Stik had " +
                            "represented only the relationship between them. They are comfortable with each other, but in their own separate worlds. It is this, not their " +
                            "youth, beauty or talents, that Stik encapsulates." ,
                    "Stik" , new Art("Elizabeth and Mary Linley, c.1772" , "This double portrait of Elizabeth and Mary Linley, is the only known painting " +
                    "depicting both sisters together, each other’s closest companions. Gainsborough carried out this painting between early 1771 and March " +
                    "1772.  In 1785 upon the request of the Linley family, he re-touched the painting to depict the sitters in the fashion of the 1780s." ,
                    "Thomas Gainsborough", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_elizabeth_and_mary_linley", "a.1387311134920959.1073741826.1387308654921207/1387320374920035"),
                    new ArrayList<String>(), 51.446656, -0.073634, "stik_elizabeth_and_mary_linley_new", "a.1387325508252855.1073741827.1387308654921207/1387333444918728"));

            add(new Art("Three Boys, 2012" ,
                    "Street children in Seville, probably orphaned by the plague, are negociating for food and drink. Stik's children are negotiating too, but " +
                            "for what can only be guessed at. Class 3S of Dulwich Hamlet Primary School helped Stik to paint the 'Three Boys' mural: 'The children " +
                            "responded immediately to his gentle self-effacing manner and really engaged with the simplicity and emotions of his painting.'(Cathy " +
                            "Skelton, Class Teacher)." ,
                    "Stik" , new Art("Three Boys, c.1670" , "This painting is unique in Murillo's oeuvre in that he appears to have changed his mind as " +
                    "he painted, a rare occurrence for an artist who is thought to have carefully planned and drawn out most of his compositions." ,
                    "Bartolome Esteban Murillo", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_three_boys", "a.1387311134920959.1073741826.1387308654921207/1387323154919757"),
                    new ArrayList<String>(), 51.445235, -0.078621, "stik_three_boys_new", "a.1387325508252855.1073741827.1387308654921207/1387336264918446"));

            add(new Art("Joseph receiving Pharaoh's Ring, 2013" ,
                    "The subject is from Genesis XLI, 42: appointing Joseph ruler over Egypt, Pharaoh 'took off his ring from his hand, and put it upon " +
                            "Joseph's hand'. Thierry Noir concentrates on the interaction(or lack of) between the people only. His simple technique works " +
                            "well next to Stik's wall and near the children's playground in Dulwich Park." ,
                    "Thierry Noir" , new Art("Joseph receiving Pharaoh's Ring, 1733-35" , "Pharaoh hands Joseph a ring to show that he has chosen the young Jewish" +
                    " ex-slave as his second-in-command over all the land of Egypt (Genesis 41:42). DPG158 was catalogued by Richter in 1880 as the work of Giandomenico Tiepolo," +
                    " but Morassi returned the picture to Giambattista, an attribution that is now generally accepted. " ,
                    "Giambattista Tiepolo", null, new ArrayList<String>(), 51.445936, -0.086170, "thierry_noir_joseph_receiving_pharaohs_ring", "a.1387311134920959.1073741826.1387308654921207/1387323888253017"),
                    new ArrayList<String>(), 51.445275, -0.079029, "thierry_noir_new1", "a.1387325508252855.1073741827.1387308654921207/1387336761585063"));

            add(new Art("Saint Catherine of Siena, 2013" ,
                    "Ben's tiny works of art are amazingly accurate reproductions of the Dulwich Picture Gallery paintings. He reproduces in miniature the " +
                            "composition, colour, tone, background and even the expressions on the faces. He adds his own quirky interpretations. Ben painted " +
                            "Catherine running away, having a vision of a banana." ,
                    "Ben Wilson" , new Art("Saint Catherine of Siena, 1665-70" , "Saint Catherine is shown in the habit of a Dominican tertiary with a crown of thorns." +
                    " This refers to a vision in which Christ offered her the choice between a crown of gold and a crown of thoms and she chose the latter. " ,
                    "Carlo Dolci", null, new ArrayList<String>(), 51.445936, -0.086170, "multiple_st_catherine_of_siena", "a.1387311134920959.1073741826.1387308654921207/1387315718253834"),
                    new ArrayList<String>(), 51.445548, -0.085312, "ben_wilson_new1", "a.1387325508252855.1073741827.1387308654921207/1387380134914059"));

            add(new Art("Dulwich Picture Gallery" ,
                    "Dulwich Picture Gallery, founded in 1811, is England's oldest public art gallery. It has one of the finest collections of Western " +
                            "European Baroque paintings in the world." ,
                    "Dulwich Gallery" , null ,
                    new ArrayList<String>(), 51.445936, -0.086170, "dulwich_gallery", "a.1387379154914157.1073741828.1387308654921207/1387379161580823"));

            add(new Art("Samson and Delilah, 2013" ,
                    "Discovering the painting by Anthony van Dick in Dulwich Picture Gallery, David saw the story coloured in and picked out some great visual " +
                            "references to draw on, breaking down the visual allegory into basic parts: the long hair, the scissors, the broken heart, the beautiful " +
                            "girl and the powerful man. He uses colour symbolically, red for drama, danger, violence and incorporates the sacred yet fascinated eyes of " +
                            "the onlookers." ,
                    "David Shillinglaw" , new Art("Samson and Delilah, c.1618" , "This painting is regarded as one of Van Dyck’s first masterpieces, executed when he was around" +
                    " the age of 20 and working as Rubens’ studio assistant in Antwerp. It entered Bourgeois’ and Desenfans’ collection in 1783 as a work by Van Dyck, but the similarity " +
                    "in style between his early work and Rubens’ late work caused much confusion, and it was subsequently reattributed to the older master. It was not until the beginning " +
                    "of the 20th century that it was rightfully given back to Van Dyck." ,
                    "Anthony van Dyck", null, new ArrayList<String>(), 51.445936, -0.086170, "david_shillinglaw_samson_and_delilah", "a.1387311134920959.1073741826.1387308654921207/1387314174920655"),
                    new ArrayList<String>(), 51.452870, -0.103013, "david_shillinglaw_new", "a.1387325508252855.1073741827.1387308654921207/1387328374919235"));

            add(new Art("Vase with Flowers, 2013" ,
                    "You can look for hours at van Huysum's painting. As you peer you spot more and more tiny ants, flies, woodlice, ladybirds and even a " +
                            "minute red snail. It is exactly the same when looking at MadC's wall. Don't stride past it. It is surprising and rewarding to " +
                            "look at it carefully." ,
                    "MadC" , new Art("Vase with Flowers, c.1720" , "" ,
                    "Jan van Huysum", null, new ArrayList<String>(), 51.445936, -0.086170, "madc_vase_with_flowers", "a.1387311134920959.1073741826.1387308654921207/1387314651587274"),
                    new ArrayList<String>(), 51.441563, -0.091361, "madc_new", "a.1387325508252855.1073741827.1387308654921207/1387329291585810"));

            add(new Art("Europa and the Bull, 2013" ,
                    "Reka chose the piece 'Europa and the Bull' because the imagery lent itself to his style. He paint a lot of female figures in motion using " +
                            "flowing hair as a way to give the piece a dynamic quality. In a sense it is moving on the wall. The project has been a great experience " +
                            "for Reka, as he rarely use an image as a reference to my artworks." ,
                    "Reka" , new Art("Europa and the Bull, 17th century" , "Guido Reni managed one of the busiest studios of the 17th century. His most popular " +
                    "compositions can exist in several versions, with varying degrees of involvement of the master." ,
                    "Guido Reni", null, new ArrayList<String>(), 51.445936, -0.086170, "reka_europa_and_the_bull", "a.1387311134920959.1073741826.1387308654921207/1387311151587624"),
                    new ArrayList<String>(), 51.427814, -0.086899, "reka_new", "a.1387325508252855.1073741827.1387308654921207/1387331348252271"));

            add(new Art("Happy Hour, 2013" ,
                    "The work of art from Dulwich Picture Gallery that AP chose to interpret is a sketch. Before the days of porn in magazines and on the internet, " +
                            "men who wanted to gaze at naked women employed artists. It was acceptable to have a painting of naked females from Greek mythology " +
                            "hanging in your study, so 'The Three Graces' was a popular subject in some circles." ,
                    "Agent Provocateur(AP)" , new Art("The Three Graces, c.1636" , "The Graces - Euphrosyne, Thalia and Aglaea - were daughters of Zeus and goddesses " +
                    "of beauty and kindness. An irregular section above the heads of the figures has been cut away and replaced. Held suggests a date of c.1625/8, Jaff‚ " +
                    "gives c.1636. A preliminary drawing is at Christ Church, Oxford." ,
                    "Sir Peter Paul Rubens", null, new ArrayList<String>(), 51.445936, -0.086170, "agent_provocateur_the_three_graces", "a.1387311134920959.1073741826.1387308654921207/1387312191587520"),
                    new ArrayList<String>(), 51.441069, -0.056676, "agent_provocateur_new", "a.1387325508252855.1073741827.1387308654921207/1387325538252852"));

            add(new Art("The Translation of Saint Rita of Cascia, 2013" ,
                    "Saint Rita had wished to become a nun, but she submitted to her parents' will and married. After the violent death of her cruel husband, " +
                            "she was miraculously transported to the Augustinian convent of Cascia, near Spoleto. She is the patron saint of abused wives. Wearing " +
                            "similar flowing clothes, RUN's Saint Rita is careering down into the convent whereas Poussin's saint is journeying in a more sedate manner." ,
                    "RUN" , new Art("The Translation of Saint Rita of Cascia, c.1630" , "Saint Rita had wished to become a nun, but submitted to her parents' will and married; " +
                    "after the violent death of her cruel husband, she was miraculously transported to the Augustinian convent of Cascia, near Spoleto. She was canonised in 1900. " ,
                    "Nicolas Poussin", null, new ArrayList<String>(), 51.445936, -0.086170, "run_the_translation_of_st_rita", "a.1387311134920959.1073741826.1387308654921207"),
                    new ArrayList<String>(), 51.438862, -0.053918, "run_the_translation_of_st_rita_new", "a.1387325508252855.1073741827.1387308654921207/1387332194918853"));

            add(new Art("Three Boys, 2013" ,
                    "Based on part of 'Three Boys' by Bartolome Murillo where the white boy might be picking the pocket of the black boy, and is looking " +
                    "very mischievous. Beerens has transformed him into a white sheep looking sly, with a fox's tail." ,
                    "Michael Beerens" , new Art("Three Boys, c.1670" , "This painting is unique in Murillo's oeuvre in that he appears to have changed his mind as " +
                    "he painted, a rare occurrence for an artist who is thought to have carefully planned and drawn out most of his compositions." ,
                    "Bartolome Esteban Murillo", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_three_boys", "a.1387311134920959.1073741826.1387308654921207/1387323154919757"),
                    new ArrayList<String>(), 51.447739, -0.074982, "beerens_new1", "a.1387325508252855.1073741827.1387308654921207/1387375291581210"));

            add(new Art("Three Boys, 2013" ,
                    "Based on a part of 'Three Boys' by Bartolome Murillo where the black boy has his hand out for food and the white boy is refusing to give " +
                     "him any food. Beerens has transformed the black boy into a hungry black sheep standing on barren ground and the white boy into a white sheep " +
                     "standing on lush grass with its mouth full." ,
                    "Michael Beerens" , new Art("Three Boys, c.1670" , "This painting is unique in Murillo's oeuvre in that he appears to have changed his mind as " +
                    "he painted, a rare occurrence for an artist who is thought to have carefully planned and drawn out most of his compositions." ,
                    "Bartolome Esteban Murillo", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_three_boys", "a.1387311134920959.1073741826.1387308654921207/1387323154919757"),
                    new ArrayList<String>(), 51.447369, -0.075179, "beerens_new2", "a.1387325508252855.1073741827.1387308654921207/1387375441581195"));

            add(new Art("Eliza and Mary Davidson, 2012" ,
                    "" ,
                    "Stik" , new Art("Eliza and Mary Davidson, 1784" , "The portrait was painted in India c. 1784, it portrays the daughters of Alexander Davidson (d.1791), " +
                    "Governor of Madras, 1785-6" ,
                    "Tilly Kettle", null, new ArrayList<String>(), 51.445936, -0.086170, "stik_eliza_and_mary_davidson", "a.1387311134920959.1073741826.1387308654921207/1387319454920127"),
                    new ArrayList<String>(), 51.447408, -0.075845, "stik_eliza_and_mary_davidson_new", "a.1387325508252855.1073741827.1387308654921207/1387333244918748"));
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

    public static boolean isLocationVisited(int n){
        return CoreActivity.preferences.getBoolean("_location"+n , false);
    }



    public static void setLocationVisited(int n){
        CoreActivity.preferences.edit().putBoolean("_location"+n,true).commit();
    }

    public static void updateBadges(){

        int count = 0;

        for(int x =0; x<getGallery().size();x++){
            if(isLocationVisited(x))count++;
        }

        ArrayList<Integer> achived = new ArrayList<>();

        if(count == getGallery().size()){
            if(!getBadgeStatus(0)){
                notifyBadgeEarned(BadgesActivity.badges.get(0));
                //achived.add(0);
                setBadgeCompleted(0);
            }
        }

        if (isLocationVisited(14) && isLocationVisited(15)){
            if ((!getBadgeStatus(1))){
                notifyBadgeEarned(BadgesActivity.badges.get(1));
                //achived.add(1);
                setBadgeCompleted(1);
            }
        }

        if(count == 3){
            if(!getBadgeStatus(3)) {
                notifyBadgeEarned(BadgesActivity.badges.get(3));
                //achived.add(3);
                setBadgeCompleted(3);
            }
        }

        if(count == 5){
            if(!getBadgeStatus(4)) {
                notifyBadgeEarned(BadgesActivity.badges.get(4));
                //achived.add(4);
                setBadgeCompleted(4);
            }
        }

        if(isLocationVisited(4)){
            if(!getBadgeStatus(10)){
                notifyBadgeEarned(BadgesActivity.badges.get(10));
                //achived.add(10);
                setBadgeCompleted(10);
            }
        }

        if(isLocationVisited(10)){
            if(!getBadgeStatus(11)){
                notifyBadgeEarned(BadgesActivity.badges.get(11));
                //achived.add(11);
                setBadgeCompleted(11);
            }

        }

        if(isLocationVisited(3)){
            if (!getBadgeStatus(12)){
                notifyBadgeEarned(BadgesActivity.badges.get(12));
                //achived.add(12);
                setBadgeCompleted(12);
            }
        }

        if (isLocationVisited(5) && isLocationVisited(8) && isLocationVisited(11) && isLocationVisited(13)
                && isLocationVisited(14) && isLocationVisited(25)){
            if (!getBadgeStatus(9)){
                notifyBadgeEarned(BadgesActivity.badges.get(9));
                //achived.add(9);
                setBadgeCompleted(9);
            }
        }

        if (isLocationVisited(0) && isLocationVisited(18) && isLocationVisited(21) && isLocationVisited(20)){
            if (!getBadgeStatus(8)){
                notifyBadgeEarned(BadgesActivity.badges.get(8));
                //achived.add(8);
                setBadgeCompleted(8);
            }
        }

        if (isLocationVisited(4) && isLocationVisited(5) && isLocationVisited(6) &&isLocationVisited(7)
                && isLocationVisited(9) && isLocationVisited(10)){
            if (!getBadgeStatus(6)){
                notifyBadgeEarned(BadgesActivity.badges.get(6));
                //achived.add(6);
                setBadgeCompleted(6);
            }
        }

        if (isLocationVisited(19) && isLocationVisited(3) && isLocationVisited(22) && isLocationVisited(18)){
            if (!getBadgeStatus(5)){
                notifyBadgeEarned(BadgesActivity.badges.get(5));
                //achived.add(5);
                setBadgeCompleted(5);
            }
        }

        /*if (!achived.isEmpty()){
            if (achived.size()>1){
                while (!achived.isEmpty()){
                    notifyBadgeEarned(BadgesActivity.badges.get(achived.get(0)));


                    achived.remove(0);
                }
            }
        }*/



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

                    Art art = new Art(lines[0], lines[2], lines[1], new Art(lines[5], lines[7], lines[6], null, null, 0, 0,"",name.replace(".txt","a.png")), null, Float.parseFloat(lines[3]), Float.parseFloat(lines[4]),"",name.replace(".txt",".png"));
                    Core.getGallery().add(art);
                    if(MyAdapter.instance!=null)
                        ((Activity)c).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyAdapter.instance.notifyDataSetChanged();
                            }
                        });
                    Log.e("wuwuuwuuw", stringBuffer.toString());
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkInternetConnection() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name

            return !ipAddr.equals("");

        }
        catch (Exception e) {
            return false;
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}


