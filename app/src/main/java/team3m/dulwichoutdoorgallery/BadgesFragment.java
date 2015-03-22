package team3m.dulwichoutdoorgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import static team3m.dulwichoutdoorgallery.R.drawable.artlover;
import static team3m.dulwichoutdoorgallery.R.drawable.baller;
import static team3m.dulwichoutdoorgallery.R.drawable.eatsleep;
import static team3m.dulwichoutdoorgallery.R.drawable.fakespotter;
import static team3m.dulwichoutdoorgallery.R.drawable.fiver;
import static team3m.dulwichoutdoorgallery.R.drawable.goldenspray;
import static team3m.dulwichoutdoorgallery.R.drawable.guardian;
import static team3m.dulwichoutdoorgallery.R.drawable.lordofarts;
import static team3m.dulwichoutdoorgallery.R.drawable.navigator;
import static team3m.dulwichoutdoorgallery.R.drawable.oddone;
import static team3m.dulwichoutdoorgallery.R.drawable.stickrunner;
import static team3m.dulwichoutdoorgallery.R.drawable.streetwatcher;
import static team3m.dulwichoutdoorgallery.R.drawable.warmup;

/**
 * Badges page
 */
@SuppressWarnings("ConstantConditions")
public class BadgesFragment extends Fragment {

    /**
     * The badges
     */
    public static ArrayList<Badge> badges = new ArrayList<Badge>();

    /**
     * The title of a badge
     */
    public static CharSequence badgeTitle;

    /**
     * The label for the achievement progress
     */
    private static TextView txt_achievementsStats;

    /**
     * Badges achieved
     */
    private static int badgesInTheBag;

    /**
     * Percentage achieved
     */
    private static float achievementsPerc;


    public BadgesFragment() {
    }

    /**
     * The main view of the fragment
     */
   View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_badges, container, false);

        if(badges.size()==0){
            populateBadgeList();
        }

        final FloatingActionButton shareBtn = (FloatingActionButton) rootView.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        populateListView();
        return rootView;
    }

    /*
    GOOGLE CARDS GUIDELINES
    -------------------------------------------------------
    1.2dp rounded corners
    2.Body Type: 14sp or 16sp
    3.Headlines: 24sp or larger
    4.Padding from edge of screen to card: 8dp
    5.Gutters between cards: 8dp
    6.Content padding: 16dp
     */

    /*
    GOOGLE LISTS GUIDELINES
    1.Primary Font: Roboto Regular 16sp
    2.Secondary Font: Roboto Regular 14sp
    3.Tile height: 88dp
    4.Left Avatar Padding: 16dp
    5.Text Left Padding: 72dp
    6.Text Top Padding: 16dp
    7.Text Bottom Padding: 20dp
    8.Top Align avatar and icon with primary text
    9.Add 8dp padding at the top and bottom of a list.
     */

    public static void populateBadgeList(){
        badges.add(new Badge("The Gladiator", "You conquered all of the street arts", guardian, false));
        badges.add(new Badge("Baller", "See the street art situated in Dulwich Park", baller, false));
        badges.add(new Badge("Art Lover", "Checking out street art is your new passion", artlover, false));
        badges.add(new Badge("Warm-Up", "What a start! You already got 3 in the bag.", warmup, false));
        badges.add(new Badge("Got a Fiver", "5 arts in the bag already!", fiver, false));
        badges.add(new Badge("Navigator", "Visit the art located next to a railway station", navigator, false));
        badges.add(new Badge("Street Watcher", "See the street art on Lordship Ln..", streetwatcher, false));
        badges.add(new Badge("Fake Spotter", "Mastering the spot of original arts.", fakespotter, false));
        badges.add(new Badge("E.S.A.R", "Eat.Sleep.Art.Repeat", eatsleep, false));
        badges.add(new Badge("Stick Runner", "Visit all the Stick Man Arts", stickrunner, false));
        badges.add(new Badge("Odd one out", "Visit Conor Harrington Art", oddone, false));
        badges.add(new Badge("Lord of the arts", "Visit Mear One US Art", lordofarts, false));
        badges.add(new Badge("The Golden Spray", "Visit System UK Rembrandt Art", goldenspray, false));
    }

    /**
     * Populates the list with badges
     */
    private void populateListView() {
        final ArrayAdapter<Badge> adapter = new BadgesAdapter();
        ListView list = (ListView) rootView.findViewById(R.id.badgesListView);
        list.setAdapter(adapter);
    }

    private class BadgesAdapter extends ArrayAdapter<Badge>{

        public BadgesAdapter(){
            super(BadgesFragment.this.getActivity(), R.layout.badge_view, badges);
        }

        public View getView(int pos, View convertView, ViewGroup parent){
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.badge_view, parent, false);
            }

            //We fill each row with information
            Badge currentBadge;
            currentBadge = badges.get(pos);

            badgesInTheBag = 0;
            txt_achievementsStats = (TextView)rootView.findViewById(R.id.txt_achievementsStats);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.badge_icon);
            imageView.setImageResource(currentBadge.getBadgeID());

            TextView makeTitle = (TextView) itemView.findViewById(R.id.badge_title);
            makeTitle.setText(currentBadge.getTitle());

            TextView makeDescription = (TextView) itemView.findViewById(R.id.badge_description);
            makeDescription.setText(currentBadge.getDescription());

            checkAchieved(currentBadge, imageView, makeTitle, makeDescription);
            getStats();

            return itemView;

        }

        /**
         * Changes the opacity if it's achieved or not
         */
        private void checkAchieved(Badge badge, ImageView img, TextView title, TextView desc){
            if(!Core.getBadgeStatus(badges.indexOf(badge))){
                img.setAlpha(0.4f);
                title.setAlpha(0.4f);
                desc.setAlpha(0.4f);
            }else{
                img.setAlpha(1.0f);
                title.setAlpha(1.0f);
                desc.setAlpha(1.0f);
            }
        }

        /**
         * Computes the percentage and displays it
         */
        public void getStats(){
            for(int i=0; i<badges.size(); i++){
                if(Core.getBadgeStatus(i))
                    badgesInTheBag++;
                achievementsPerc = (badgesInTheBag*100)/badges.size();
            }
            txt_achievementsStats.setText("You have unlocked "+badgesInTheBag+"/"+badges.size()+" ("+Math.round(achievementsPerc)+"%"+")");
        }
    }

    /**
     * Shares the badge percentage
     */
    void share(){
        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");

        intent2.putExtra(Intent.EXTRA_TEXT, "I've completed "+ Math.round(achievementsPerc) + "% of the badges etc" );
        startActivity(Intent.createChooser(intent2, "Share via"));
    }
}
