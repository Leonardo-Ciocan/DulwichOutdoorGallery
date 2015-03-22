package team3m.dulwichoutdoorgallery;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Timer;

/**
* Routing pages
*/
public class RouteFragment extends Fragment {

    /**
     * Contains the cards at bottom of the container
     */
    private LinearLayout cardContainer;

    /**
     * The scrollview of the container
     */
    private HorizontalScrollView scrollView;

    /**
     * Whether the user finished the route
     */
    boolean hasFinished;

    /**
     * The last location of the user
     */
    private LatLng userLoc;

    public RouteFragment() {
    }


    /**
     * The index of the current navigation target in the order they are selected
     */
    int currentIndex = 0;

    /**
     * The index of the current target within the gallery
     */
    int current = -1;

    /**
     * The index of the last visited location
     */
    int lastVisited = 0;

    /**
     * The google map view
     */
    GoogleMap map;

    /**
     * The last coordonate
     */
    LatLng last;

    /**
     * Whether the user has reached the first point
     */
    boolean visitedStartingPoint = false;

    /**
     * The computed coordonate of the first point
     */
    LatLng first = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_route, container, false);
        //gets the map control
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        final FloatingActionButton fab            = (FloatingActionButton)    rootView.findViewById(R.id.fab);
        cardContainer = (LinearLayout)            rootView.findViewById(R.id.cardContainer);
        scrollView = (HorizontalScrollView)    rootView.findViewById(R.id.scrollView);




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
                        //We keep processing events until the we reached the last art
                        if (!hasFinished){
                            handleLocationChanged(location);
                        }
                    }
                };

                googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


                //We zoom in to the middle of the map
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation(), 12));

                last = Gallery.get(0).getLocation();

            }
        });


        fab.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                navigate();
                return false;
            }
        });

        //We populate the container with art cards
        for(int x =0;x< Core.getGallery().size();x++)
        {
            Art ax = Core.getGallery().get(x);
            cardContainer.addView(new LocationCardView(getActivity(), ax));
        }

        return rootView;
    }

    /**
     * This handles the user's movement
     * @param location The location to process
     */
    void handleLocationChanged(Location location){
        userLoc = new LatLng(location.getLatitude(),location.getLongitude());

        if(first != null) {

            if(visitedStartingPoint) {
                //If we visited first point , we check if we are close to the target
                int closest = getClosestWithinRange(location, -1);
                if(current == -1) current = closest;

                boolean withinRange = false;
                if(current != -1) {
                    withinRange = isWithinRange(current, location,
                            25f);
                }

                if (withinRange) {
                    //if we are close , we compute the next target
                    Core.setLocationVisited(current);
                    Core.updateBadges();

                    //Update the indicator
                    ((CoreActivity)getActivity()).routeIndicator.setSelected(
                            visited.size()-1
                    );

                   if (!visited.contains(current)){
                       visited.add(current);
                   }
                    lastVisited = current;
                    current = closest;

                    //We make sure to avoid multiple calls issues
                    if(current != lastVisited) {
                        currentIndex++;

                        //We reveal the card we arrived at and prepare the next one

                        ((LocationCardView) cardContainer.getChildAt(currentIndex - 1)).setArt(Core.getGallery().get(lastVisited));
                        ((LocationCardView) cardContainer.getChildAt(currentIndex - 1)).hideOverlay();

                        if (currentIndex <Core.getGallery().size()-1)
                            ((LocationCardView) cardContainer.getChildAt(currentIndex)).setArt(Core.getGallery().get(current));

                        //Scroll to the art
                        scrollView.smoothScrollTo((int) Core.convertDpToPixel((130 * (currentIndex - 2)), getActivity()), 0);

                    }

                }
                if (currentIndex <= Core.getGallery().size()-1) {
                    drawOverlay();
                }
                if (currentIndex > Core.getGallery().size()) {
                    hasFinished = true;
                }
            }
        }
        else{
            //If we havent reached the first
            //We compute the closest point and draw the line
            int closest = getClosestWithinRange(location, -1);
            lastVisited = closest;
            visited.add(closest);
            PolylineOptions options = new PolylineOptions();
            options.add(Core.getGallery().get(closest).getLocation());
            options.add(userLoc);

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
            //We check if we reached the first and prepare for the next location change
            if(isWithinRange(lastVisited , location , 40f)){
                visitedStartingPoint = true;
                ((LocationCardView)cardContainer.getChildAt(0)).hideOverlay();
                currentIndex++;
                handleLocationChanged(location);

                Core.setLocationVisited(lastVisited);
                Core.updateBadges();
            }
        }
    }

    /**
     * The list of visited indexes
     */
    static ArrayList<Integer> visited = new ArrayList<Integer>();

    /**
     * Finds the closest art within a range
     * @param user The location of the user
     * @param max The maximum distance radius
     * @return The closest index or -1 if none found
     */
    public static  int getClosestWithinRange(Location user,float max){
        float min = Float.MAX_VALUE;
        int minIndex=-1;

        //Linear search to look for closest
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

    /**
     * Checks whether a location is within a range
     * @param index The index of the art
     * @param user The location of the user
     * @param max The maximum distance that would qualify
     * @return Whether it is within the range
     */
    public static boolean isWithinRange(int index , Location user,float max){
        Location location = new Location("t" + "");
        location.setLatitude(Core.Gallery.get(index).getLatitude());
        location.setLongitude(Core.Gallery.get(index).getLongitude());
        Float dist = location.distanceTo(user);
        return  dist < max;
    }

    /**
     * Draws the markers and the lines to guide the user
     */
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

        Polyline polyline = map.addPolyline(options);
        polyline.setGeodesic(true);
        int color = getActivity().getResources().getColor(R.color.brand) ;
        polyline.setColor(color);
        polyline.setWidth(6);
    }


    /**
     * Starts a navigation intent so the user can navigate to a location
     */
    void navigate(){

        LatLng lastP = userLoc;
        LatLng nextP = null;
        if(current!= -1)nextP = Core.getGallery().get(current).getLocation();
        else {
            nextP = first;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW,

        Uri.parse("http://maps.google.com/maps?saddr=" + lastP.latitude + "," + lastP.longitude + "&daddr=" +
                nextP.latitude + "," + nextP.longitude));
        startActivity(intent);
        Intent intent1 = new Intent(getActivity() , CoreActivity.class);

        //We tell the user to come up after being done by using a notification
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



}
