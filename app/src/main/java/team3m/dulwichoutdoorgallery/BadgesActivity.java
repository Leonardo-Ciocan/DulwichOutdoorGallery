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

import java.util.ArrayList;

import static team3m.dulwichoutdoorgallery.R.drawable.badge;
import static team3m.dulwichoutdoorgallery.R.drawable.small;
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
            badges.add(new Badge("Title", "description", badge, false));
            badges.add(new Badge("Title", "description", badge, false));
            badges.add(new Badge("Title", "description", small, false));
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


                return itemView;
            }


        }


    }
}
