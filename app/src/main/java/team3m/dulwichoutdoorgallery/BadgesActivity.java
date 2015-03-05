package team3m.dulwichoutdoorgallery;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import static team3m.dulwichoutdoorgallery.R.drawable.artlover;
import static team3m.dulwichoutdoorgallery.R.drawable.badge;
import static team3m.dulwichoutdoorgallery.R.drawable.baller;
import static team3m.dulwichoutdoorgallery.R.drawable.eatsleep;
import static team3m.dulwichoutdoorgallery.R.drawable.fakespotter;
import static team3m.dulwichoutdoorgallery.R.drawable.fiver;
import static team3m.dulwichoutdoorgallery.R.drawable.goldenspray;
import static team3m.dulwichoutdoorgallery.R.drawable.guardian;
import static team3m.dulwichoutdoorgallery.R.drawable.lordofarts;
import static team3m.dulwichoutdoorgallery.R.drawable.navigator;
import static team3m.dulwichoutdoorgallery.R.drawable.oddone;
import static team3m.dulwichoutdoorgallery.R.drawable.small;
import static team3m.dulwichoutdoorgallery.R.drawable.stickrunner;
import static team3m.dulwichoutdoorgallery.R.drawable.streetwatcher;
import static team3m.dulwichoutdoorgallery.R.drawable.warmup;
import static team3m.dulwichoutdoorgallery.R.id.badgesListView;


public class BadgesActivity extends ActionBarActivity {

    public static ArrayList<Badge> badges = new ArrayList<Badge>();
    public static CharSequence badgeTitle;
    private static TextView txt_achievementsStats;
    private static int badgesInTheBag;
    private static float achievementsPerc;
    //public static CharSequence badgeDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_badges, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressWarnings("ConstantConditions")
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


       View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_badges, container, false);

            /*
            Quick Fix to Badges Duplication : Quickly checks whether the badges array is already populated.
                        We only want to populate the list if the array is empty; if it isn't, then
                        just display the current list.
             */
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
            badges.add(new Badge("Baller", "You're a true baller across the streets", baller, false));
            badges.add(new Badge("Art Lover", "Checking out street art is your new passion", artlover, false));
            badges.add(new Badge("Warm-Up", "What a start! You already got 3 in the bag.", warmup, false));
            badges.add(new Badge("Got a Fiver", "5 arts in the bag already!", fiver, false));
            badges.add(new Badge("Navigator", "Ay Ay Cap'n!", navigator, false));
            badges.add(new Badge("Street Watcher", "Getting using monitoring street arts.", streetwatcher, false));
            badges.add(new Badge("Fake Spotter", "Mastering the spot of original arts.", fakespotter, false));
            badges.add(new Badge("E.S.A.R", "Eat.Sleep.Art.Repeat", eatsleep, false));
            badges.add(new Badge("Stick Runner", "Visit all the Stick Man Arts", stickrunner, false));
            badges.add(new Badge("Odd one out", "Visit Mear One US Art", oddone, false));
            badges.add(new Badge("Lord of the arts", "Visit Conor Harrington Art", lordofarts, false));
            badges.add(new Badge("The Golden Spray", "Visit System UK Rembrandt Art", goldenspray, false));
        }

        // Notifies the user whenever a badge is earned.
        private void displayNotification(View v){
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this.getActivity());
            notificationBuilder.setContentTitle("You just got a new achievement!");
            notificationBuilder.setContentText("You earned the " +badgeTitle + " badge");
            // Insert a new notification logo - this one is just a sample for testing
            notificationBuilder.setSmallIcon(R.drawable.ic_action_about);
            // Insert a new notification bgColor - this one is just a sample for testing
            notificationBuilder.setColor(getResources().getColor(R.color.bright_foreground_material_light));

            /*
                Works only with API 16+

            Intent notificationIntent = new Intent(this.getActivity(), BadgesActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getActivity());
            stackBuilder.addParentStack(BadgesActivity.class);
            stackBuilder.addNextIntent(notificationIntent);

            PendingIntent notificationPending = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(notificationPending);

            */

            Notification notification = notificationBuilder.build();
            NotificationManager notificationManager = (NotificationManager) this.getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }

        private void populateListView() {
            final ArrayAdapter<Badge> adapter = new MyListAdapter();
            ListView list = (ListView) rootView.findViewById(R.id.badgesListView);
            list.setAdapter(adapter);

            // Click on ListItem - ATM it triggers the action which displays the notification
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*Badge badgePos = adapter.getItem(position);
                    badgePos.setAchieved(true);
                    Core.notifyBadgeEarned(badgePos);
                    badgeTitle = badgePos.getTitle();
                    adapter.notifyDataSetChanged();
                    // Display Notification
                    displayNotification(view);*/
                }
            });
        }



        private class MyListAdapter extends ArrayAdapter<Badge>{

            public MyListAdapter(){
                super(PlaceholderFragment.this.getActivity(), R.layout.badge_view, badges);
            }

            public View getView(int pos, View convertView, ViewGroup parent){
                View itemView = convertView;
                if (itemView == null) {
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.badge_view, parent, false);
                }

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

            public void getStats(){
                for(int i=0; i<badges.size(); i++){
                    if(badges.get(i).getAchieved())
                        badgesInTheBag++;
                    achievementsPerc = (badgesInTheBag*100)/badges.size();
                }
                txt_achievementsStats.setText("You have unlocked "+badgesInTheBag+"/"+badges.size()+" ("+Math.round(achievementsPerc)+"%"+")");
            }


        }

        void share(){
            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");

            //TODO fix please
            intent2.putExtra(Intent.EXTRA_TEXT, "I've completed "+ Math.round(achievementsPerc) + "% of the badges etc" );
            startActivity(Intent.createChooser(intent2, "Share via"));
        }
    }
}
