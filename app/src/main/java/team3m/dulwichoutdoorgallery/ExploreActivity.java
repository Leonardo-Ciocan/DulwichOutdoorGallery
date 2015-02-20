package team3m.dulwichoutdoorgallery;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class ExploreActivity extends ActionBarActivity {

    protected static LinearLayout listLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        if (savedInstanceState == null) {
            //This activity is empty , the fragment holds the actual UI
            //This is useful because we can move around that fragment in a tablet layout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explore, menu);
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


    public void onBackPressed(){

        if(listLayout.getVisibility()== View.VISIBLE)
            listLayout.setVisibility(View.INVISIBLE);
        else
            super.onBackPressed();
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //the map is ready here
                    ArrayList<Art> Gallery = Core.getGallery();

                    for (int i = 0; i < Gallery.size(); i++) {

                        googleMap.addMarker(new MarkerOptions()
                                .title(Gallery.get(i).getName())
                                .snippet(Gallery.get(i).getDescription())
                                .position(new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude())));

                    }

                }
            });

            Button List = (Button) rootView.findViewById(R.id.buttonList);
            List.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listLayout = (LinearLayout) rootView.findViewById(R.id.listLayout);
                    listLayout.setVisibility(View.VISIBLE);
                    populatedListView();

                }
            });
            return rootView;
        }


        public void populatedListView() {
            ArrayAdapter<Art> adapter = new MyAdapter(getActivity(), R.layout.item_view, Core.getGallery());
            ListView list = (ListView) getView().findViewById(R.id.artList);
            list.setAdapter(adapter);
        }

    }


}
