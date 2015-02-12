package team3m.dulwichoutdoorgallery;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static team3m.dulwichoutdoorgallery.R.drawable.artlover;
import static team3m.dulwichoutdoorgallery.R.drawable.badge;
import static team3m.dulwichoutdoorgallery.R.drawable.baller;
import static team3m.dulwichoutdoorgallery.R.drawable.eatsleep;
import static team3m.dulwichoutdoorgallery.R.drawable.fakespotter;
import static team3m.dulwichoutdoorgallery.R.drawable.fiver;
import static team3m.dulwichoutdoorgallery.R.drawable.guardian;
import static team3m.dulwichoutdoorgallery.R.drawable.navigator;
import static team3m.dulwichoutdoorgallery.R.drawable.small;
import static team3m.dulwichoutdoorgallery.R.drawable.streetwatcher;
import static team3m.dulwichoutdoorgallery.R.drawable.warmup;
import static team3m.dulwichoutdoorgallery.R.id.badgesListView;


public class BadgesActivity extends ActionBarActivity {

    public static ArrayList<Badge> badges = new ArrayList<Badge>();

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

            populateBadgeList();
            populateListView();

            return rootView;
        }

        private void populateBadgeList(){
            badges.add(new Badge("The Gladiator", "You conquered all of the street arts", guardian, true));
            badges.add(new Badge("Baller", "You're a true baller across the streets", baller, true));
            badges.add(new Badge("Art Lover", "Checking out street art is your new passion", artlover, true));
            badges.add(new Badge("Warm-Up", "What a start! You already got 3 in the bag.", warmup, true));
            badges.add(new Badge("Got a Fiver", "5 arts in the bag already!", fiver, false));
            badges.add(new Badge("Navigator", "Ay Ay Cap'n!", navigator, false));
            badges.add(new Badge("Street Watcher", "Getting using monitoring street arts.", streetwatcher, false));
            badges.add(new Badge("Fake Spotter", "Mastering the spot of original arts.", fakespotter, false));
            badges.add(new Badge("E.S.A.R", "Eat.Sleep.Art.Repeat", eatsleep, false));
        }

        private void populateListView() {
            ArrayAdapter<Badge> adapter = new MyListAdapter();
            ListView list = (ListView) rootView.findViewById(R.id.badgesListView);
            list.setAdapter(adapter);
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

                Badge currentBadge = badges.get(pos);

                ImageView imageView = (ImageView) itemView.findViewById(R.id.badge_icon);
                imageView.setImageResource(currentBadge.getBadgeID());

                TextView makeTitle = (TextView) itemView.findViewById(R.id.badge_titleView);
                makeTitle.setText(currentBadge.getTitle());

                TextView makeDesctiption = (TextView) itemView.findViewById(R.id.badge_descriptionView);
                makeDesctiption.setText(currentBadge.getDescription());

                return itemView;
            }


        }


    }
}
