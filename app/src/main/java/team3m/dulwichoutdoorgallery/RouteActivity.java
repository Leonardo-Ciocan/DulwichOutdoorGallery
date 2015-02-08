package team3m.dulwichoutdoorgallery;

import android.graphics.Color;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class RouteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //the map is ready here
                    ArrayList<Art> Gallery= Core.getGallery();

                    PolylineOptions shape = new PolylineOptions();

                    ArrayList<Art> Sorted = new ArrayList<Art>();

                    Art closest = null;
                    while(Sorted.size() != Gallery.size()){
                        for(int x =0; x< Gallery.size();x++){
                            if(closest == null) closest = Gallery.get(x);

                        }
                    }

                    for(int i=0; i<Gallery.size(); i++){

                        LatLng point = new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude());
                        googleMap.addMarker(new MarkerOptions()
                                .title(Gallery.get(i).getName())
                                .snippet(Gallery.get(i).getDescription())
                                .position(point));

                        shape.add(point);




                    }

                    Polyline polyline = googleMap.addPolyline(shape);
                    polyline.setColor(Color.RED);


                }
            });
            return rootView;
        }
    }
}
