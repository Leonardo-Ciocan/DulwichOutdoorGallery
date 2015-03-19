package team3m.dulwichoutdoorgallery;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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
        private LocationCardView cardA;
        private LocationCardView cardB;
        private LocationCardView cardC;
        private LinearLayout cardContainer;
        private HorizontalScrollView scrollView;
        private Timer timer;
        boolean hasFinished;

        public PlaceholderFragment() {
        }

        int iCurrent = 0;
        int current = -1;
        int lastVisited = 0;
        GoogleMap map;
        LatLng last;
        boolean collapsed;
        Art art;
        Animation nextCardAnim;

        boolean visitedStartingPoint = false;
        LatLng first = null;

        LatLng user;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            final TextView               titleView      = (TextView)                rootView.findViewById(R.id.title);
            final TextView               authorView     = (TextView)                rootView.findViewById(R.id.author);
            final TextView               title          = (TextView)                rootView.findViewById(R.id.title);
            final TextView               author         = (TextView)                rootView.findViewById(R.id.author);
            final FloatingActionButton   fab            = (FloatingActionButton)    rootView.findViewById(R.id.fab);
            cardContainer = (LinearLayout)            rootView.findViewById(R.id.cardContainer);
            scrollView = (HorizontalScrollView)    rootView.findViewById(R.id.scrollView);
            cardA = (LocationCardView)        rootView.findViewById(R.id.cardA);
            cardB = (LocationCardView)        rootView.findViewById(R.id.cardB);
            cardC = (LocationCardView)        rootView.findViewById(R.id.cardC);




            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    //the map is ready here
                    ArrayList<Art> Gallery = Core.getGallery();
                    map = googleMap;
                    googleMap.setMyLocationEnabled(true);
                    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
                            if (!hasFinished){
                                handleLocationChanged(location);
                            }
                        }
                    };

                    googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation(), 12));

                    last = Gallery.get(0).getLocation();

                }
            });

            collapsed = true;

            fab.setOnTouchListener(new View.OnTouchListener() {


                @Override
                public boolean onTouch(final View v, MotionEvent event) {
                    navigate();
                    return false;
                }
            });

            for(int x =0;x< Core.getGallery().size();x++)
            {
                Art ax = Core.getGallery().get(x);
                cardContainer.addView(new LocationCardView(getActivity(), ax));
            }

            timer = new Timer();

            return rootView;
        }

        Marker m;
        void handleLocationChanged(Location location){

            user = new LatLng(location.getLatitude() , location.getLongitude());
            if(m==null){
                m = map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(66))
                .position(user));
            }
            else{
                m.remove();
                m = map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(66))
                        .position(user));
            }
            if(first != null) {

                if(visitedStartingPoint) {

                    int closest = getClosestWithinRange(location, -1);
                    if(current == -1) current = closest;

                    boolean withinRange = false;
                    if(current != -1) {
                        withinRange = isWithinRange(current, location,
                                25f);
                    }
                    //Doge

                    if (withinRange) {
                        Core.setLocationVisited(current);
                        Log.e("wubwu",current+" "+iCurrent);
                        Core.updateBadges();

                        ((CoreActivity)getActivity()).routeIndicator.setSelected(
                                visited.size()-1
                        );
                       if (!visited.contains(current)){
                           visited.add(current);
                       }
                        lastVisited = current;
                        current = closest;

                        if(current != lastVisited) {
                            iCurrent++;

                            ((LocationCardView) cardContainer.getChildAt(iCurrent - 1)).setArt(Core.getGallery().get(lastVisited));
                            ((LocationCardView) cardContainer.getChildAt(iCurrent - 1)).hideOverlay();

                            if (iCurrent<Core.getGallery().size()-1)
                                ((LocationCardView) cardContainer.getChildAt(iCurrent)).setArt(Core.getGallery().get(current));

                            scrollView.smoothScrollTo((int) Core.convertDpToPixel((130 * (iCurrent - 2)), getActivity()), 0);
                        }

                    }
                    if (iCurrent < Core.getGallery().size()-1) {
                        drawOverlay();
                    }
                    if (iCurrent == Core.getGallery().size()-1) {
                        hasFinished = true;
                    }
                }
            }
            else{
                int closest = getClosestWithinRange(location, -1);
                lastVisited = closest;
                visited.add(closest);
                PolylineOptions options = new PolylineOptions();
                options.add(Core.getGallery().get(closest).getLocation());
                options.add(new LatLng(location.getLatitude(),location.getLongitude()));

                map.addMarker(new MarkerOptions()
                        .title(Core.getGallery().get(closest).getName())
                        .snippet(Core.getGallery().get(closest).getDescription())
                        .position(options.getPoints().get(0)));


                Polyline polyline = map.addPolyline(options);
                polyline.setGeodesic(true);
                int color = getActivity().getResources().getColor(R.color.brand);
                polyline.setColor(color);
                polyline.setWidth(6);

                first = Core.getGallery().get(closest).getLocation();
                ((LocationCardView)cardContainer.getChildAt(0)).setArt(Core.getGallery().get(closest));
            }

            if(!visitedStartingPoint && first != null){

                if(isWithinRange(lastVisited , location , 40f)){
                    visitedStartingPoint = true;
                    ((LocationCardView)cardContainer.getChildAt(0)).hideOverlay();
                    iCurrent++;
                    handleLocationChanged(location);

                    Core.setLocationVisited(lastVisited);
                    Core.updateBadges();
                }
            }
        }

        static ArrayList<Integer> visited = new ArrayList<Integer>();
        public static  int getClosestWithinRange(Location user,float max){
            float min = Float.MAX_VALUE;
            int minIndex=-1;


            for( int x =0; x < Core.getGallery().size();x++){
                Location location = new Location("t" + x);
                location.setLatitude(Core.Gallery.get(x).getLatitude());
                location.setLongitude(Core.Gallery.get(x).getLongitude());
                Float dist = location.distanceTo(user);
                boolean isWithinRing = (dist < max || max == -1);
                if(dist < min && isWithinRing && !visited.contains(x)){
                    minIndex = x;
                    min = dist;
                }
            }
            return minIndex;
        }

        public static boolean isWithinRange(int index , Location user,float max){
            Location location = new Location("t" + "");
            location.setLatitude(Core.Gallery.get(index).getLatitude());
            location.setLongitude(Core.Gallery.get(index).getLongitude());
            Float dist = location.distanceTo(user);
            return  dist < max;
        }

        void drawOverlay(){
            map.clear();
            ArrayList<Art> Gallery = Core.getGallery();

            int i = lastVisited;

            LatLng point = new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude());
            map.addMarker(new MarkerOptions()
                    .title(Gallery.get(i).getName())
                    .snippet(Gallery.get(i).getDescription())
                    .position(point));


            PolylineOptions options = new PolylineOptions();

            options.add(point);

            last = point;

            LatLng point2 = new LatLng(Gallery.get(current).getLatitude(), Gallery.get(current).getLongitude());

            map.addMarker(new MarkerOptions()
                    .title(Gallery.get(current).getName())
                    .snippet(Gallery.get(current).getDescription())
                    .position(point2));

            options.add(point2);


            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                Polyline polyline = map.addPolyline(options);
                polyline.setGeodesic(true);
                int color = getActivity().getResources().getColor(R.color.brand) ;
                polyline.setColor(color);
                polyline.setWidth(6);


            /*LatLngBounds position = new LatLngBounds(
                    point , point2);

            map.moveCamera(CameraUpdateFactory.newLatLngBounds(position, 0));
*/


        }

        void share(){
            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "I'm on the grand tour of the Dulwich outdoor gallery" );
            startActivity(Intent.createChooser(intent2, "Share via"));
        }

        void navigate(){
            LatLng last = Core.getGallery().get(lastVisited).getLocation();
            LatLng next =null;


            if(current == -1){
                next = first;
                last = user;
            }
            else{
                next= Core.getGallery().get(current).getLocation();
            }

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?saddr="+ last.latitude+ "," + last.longitude+"&daddr="+ next.latitude+ "," + next.longitude));
            startActivity(intent);
            Intent intent1 = new Intent(getActivity() , CoreActivity.class);
            Notification.Builder builder = new Notification.Builder(getActivity().getApplicationContext());
            builder.setContentTitle("Dulwich Outdoor Gallery");
            builder.setSubText("When you are done navigating , come back to the app.");
            builder.setNumber(101);
            builder.setTicker("When you're done, come back in the app.");
            builder.setSmallIcon(R.drawable.ic_launcher);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pd = PendingIntent.getActivity(getActivity(), 01,intent1 , 0);

            builder.setContentIntent(pd);
            builder.setAutoCancel(true);
            builder.setPriority(0);
            Notification notification = builder.build();
            NotificationManager notificationManger =
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManger.notify(01, notification);
        }

        void shareArt(){
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");

            //TODO fix please
            intent2.putExtra(Intent.EXTRA_TEXT, "" );
            intent2.setType("image/*");
            Uri uri = Uri.parse("android.resource://team3m.dulwichoutdoorgallery/" + getActivity().getResources()
                    .getIdentifier(art.getPhoto(), "drawable", getActivity().getPackageName()));
            intent2.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent2, "Share via"));
        }


    }
}
