package team3m.dulwichoutdoorgallery;

import android.content.Context;
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    //the map is ready here
                    ArrayList<Art> Gallery= Core.getGallery();

                    PolylineOptions shape = new PolylineOptions();

                    ArrayList<Art> Sorted = new ArrayList<Art>();

                    Art closest = null;
                    double dist = Integer.MAX_VALUE;
                    /*while(Sorted.size() != Gallery.size()){
                        for(int x =0; x< Gallery.size();x++){
                            if(closest == null) {
                                closest = Gallery.get(x);
                                float[] arr = new float[1] ;
                                Location.distanceBetween(closest.Latitude , closest.Longitude ,
                                        Core.Gallery.get(x).Latitude,Core.Gallery.get(x).Longitude,
                                        arr
                                        );
                                dist = arr[0];

                            }
                            float[] arr = new float[1] ;
                            Location.distanceBetween(closest.Latitude , closest.Longitude ,
                                    Core.Gallery.get(x).Latitude,Core.Gallery.get(x).Longitude,
                                    arr
                            );
                            if(arr[0] < dist) {
                                closest = Core.Gallery.get(x);
                                dist = arr[0];
                            }

                        }

                    }
                    LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    final LocationListener locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            LatLng point3 = new LatLng(location.getLatitude(),location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                    .defaultMarker(180))
                                    .title("User")
                                    .position(point3));
                        }
                    };

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
                    */




                    /*LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    LatLng point3 = new LatLng(location.getLatitude(),location.getLongitude());
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .defaultMarker(180))
                            .title("User")
                            .position(point3));*/

                    /*while(Sorted.size() != Gallery.size()){
                        for(int x =0; x< Gallery.size();x++){
                            if(closest == null) closest = Gallery.get(x);

                        }
                    }*/


                    googleMap.setMyLocationEnabled(true);
                    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                        Marker last;
                        @Override
                        public void onMyLocationChange(Location location) {
                            if(last != null)last.remove();
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                            last = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                    .defaultMarker(200)).position(loc));
                            double min = Integer.MAX_VALUE;
                            LatLng minPos = Core.Gallery.get(0).getLocation();



                            //Log.i("appx" , location.getLatitude() +"");
                            for(int x = 1; x < Core.Gallery.size();x++) {
                                Location userLocation = new Location("t" + x);
                                userLocation.setLatitude(Core.Gallery.get(x).getLatitude());
                                userLocation.setLatitude(Core.Gallery.get(x).getLongitude());

                                double dist = location.distanceTo(userLocation);




                                if(dist < min) {
                                    min = dist;
                                    minPos = Core.getGallery().get(x).getLocation();
                                }
                            }

                            Location userLocation = new Location("t" + 0);
                            userLocation.setLatitude(Core.Gallery.get(0).getLatitude());
                            userLocation.setLongitude(Core.Gallery.get(0).getLongitude());
                            Float dist = userLocation.distanceTo(location);
                            rootView.setAlpha((dist>30?0.5f:1f));
                            dist = dist /1000000.0f;
                            Log.v("aprp",dist.toString());

                            if(googleMap != null) {
                                if(line != null)line.remove();
                                PolylineOptions polylineOptions = new PolylineOptions();
                                polylineOptions.add(new LatLng(location.getLatitude() , location.getLongitude()));
                                polylineOptions.add(minPos);
                                //line = googleMap.addPolyline(polylineOptions);
                            }
                        }
                    };
                    googleMap.setOnMyLocationChangeListener(myLocationChangeListener);

                    LatLngBounds AUSTRALIA = new LatLngBounds(
                            new LatLng(Core.Gallery.get(10).getLatitude(), Core.Gallery.get(10).getLongitude()), new LatLng(Core.Gallery.get(0).getLatitude(), Core.Gallery.get(0).getLongitude()));

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 12));

                    Random rnd = new Random();
                    LatLng last = Gallery.get(0).getLocation();
                    int x =0;
                    for(int i=1; i<Gallery.size(); i++){
                        LatLng point = new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude());
                        googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                .title(Gallery.get(i).getName())
                                .snippet(Gallery.get(i).getDescription())
                                .position(point));


                        PolylineOptions options = new PolylineOptions();
                        options.add(last);
                        options.add(point);

                        last = point;
                        Polyline polyline = googleMap.addPolyline(options);
                        polyline.setGeodesic(true);
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        polyline.setColor(color);
                        polyline.setWidth(4);
                    }



                }
            });
            return rootView;
        }
    }
}
