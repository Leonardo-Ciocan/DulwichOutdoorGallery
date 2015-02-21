package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;


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

    public static class PlaceholderFragment extends Fragment {

        Polyline line ;
        public PlaceholderFragment() {
        }

        int lastVisited = 0;
        GoogleMap map;
        LatLng last;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            final RouteProgressIndicator indicator = (RouteProgressIndicator) rootView.findViewById(R.id.dotview);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    //the map is ready here
                    ArrayList<Art> Gallery= Core.getGallery();
                    map = googleMap;
                    googleMap.setMyLocationEnabled(true);
                    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                        Marker last;
                        @Override
                        public void onMyLocationChange(Location location) {
                            if(last != null)last.remove();
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                            last = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                    .defaultMarker(200)).position(loc));

                            /*
                            Location userLocation = new Location("t" + 0);
                            userLocation.setLatitude(Core.Gallery.get(0).getLatitude());
                            userLocation.setLongitude(Core.Gallery.get(0).getLongitude());
                            Float dist = userLocation.distanceTo(location);
                            rootView.setAlpha((dist>30?0.5f:1f));*/

                            int closest = getClosestWithinRange(location , 20f);
                            if(closest != lastVisited && closest > lastVisited){
                                lastVisited = closest;
                                Toast.makeText(getActivity() , lastVisited +"",Toast.LENGTH_SHORT).show();
                                indicator.setSelected(closest);
                                drawOverlay();
                            }
                        }
                    };

                    googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation() ,  12));

                    last = Gallery.get(0).getLocation();


                    drawOverlay();


                }
            });
            return rootView;
        }

        int getClosestWithinRange(Location user,float max){

            float min = Float.MAX_VALUE;
            int minIndex=0;
            for( int x =0; x< Core.getGallery().size();x++){
                Location location = new Location("t" + x);
                location.setLatitude(Core.Gallery.get(x).getLatitude());
                location.setLongitude(Core.Gallery.get(x).getLongitude());
                Float dist = location.distanceTo(user);
                if(dist < min && dist < max){
                    minIndex = x;
                }
            }
            return minIndex;
        }


        void drawOverlay(){
            map.clear();
            ArrayList<Art> Gallery = Core.getGallery();
            for(int i=0; i< Core.getGallery().size(); i++){
                LatLng point = new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude());
                map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .title(Gallery.get(i).getName())
                        .snippet(Gallery.get(i).getDescription())
                        .position(point));


                PolylineOptions options = new PolylineOptions();
                options.add(last);
                options.add(point);

                last = point;

                //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                if(i>=1) {
                    Polyline polyline = map.addPolyline(options);
                    polyline.setGeodesic(true);
                    int color = (i == lastVisited+1) ? getActivity().getResources().getColor(R.color.brand) : Color.GRAY;
                    polyline.setColor(color);
                    polyline.setWidth(6);
                }
            }
        }

        void share(){
            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
            startActivity(Intent.createChooser(intent2, "Share via"));
        }
    }
}
