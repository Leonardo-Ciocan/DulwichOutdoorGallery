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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.melnykov.fab.FloatingActionButton;

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
        boolean collapsed;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            final RouteProgressIndicator indicator      = (RouteProgressIndicator)  rootView.findViewById(R.id.dotview);
            final TextView               titleView      = (TextView)                rootView.findViewById(R.id.title);
            final TextView               authorView     = (TextView)                rootView.findViewById(R.id.author);
            final ImageView              artCardImage   = (ImageView)               rootView.findViewById(R.id.artCardImage);
            final ImageView              smallImage     = (ImageView)               rootView.findViewById(R.id.smallImage);
            final TextView               title          = (TextView)                rootView.findViewById(R.id.title);
            final TextView               author         = (TextView)                rootView.findViewById(R.id.author);
            final TextView               titleProgress  = (TextView)                rootView.findViewById(R.id.titleProgress);
            final CardView               navigationCard = (CardView)                rootView.findViewById(R.id.navigationCard);
            final CardView               currentArt     = (CardView)                rootView.findViewById(R.id.currentArt);
            final FloatingActionButton   fab            = (FloatingActionButton)    rootView.findViewById(R.id.fab);


            titleView.setText(Core.getGallery().get(0).getName());
            authorView.setText(Core.getGallery().get(0).getAuthor());

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
                                Art a = Core.Gallery.get(lastVisited);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a.getLocation() ,  12));
                                titleProgress.setText((lastVisited) + " : " + a.getName());
                                title.setText(a.getName());
                                Log.e("xyz" , a.getPhoto().toLowerCase());
                                Drawable d = getActivity().getResources().getDrawable(
                                        getActivity().getResources().getIdentifier(a.getPhoto() ,
                                                "drawable" ,
                                                getActivity().getPackageName()));
                                smallImage.setImageDrawable(d);
                                artCardImage.setImageDrawable(d);
                                //author.setText(a.getAuthor());
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
            final Button btnInfo  = (Button) rootView.findViewById(R.id.info);
            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity() , InfoActivity.class);

                    i.putExtra("index" , lastVisited);
                    startActivity(i);
                }
            });
            Button navigateBtn = (Button) rootView.findViewById(R.id.navigateBtn);
            navigateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigate();
                }
            });

            Button shareBtn = (Button) rootView.findViewById(R.id.shareBtn);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share();
                }
            });



            collapsed = true;


            final Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    Log.v("appi", interpolatedTime + " " + collapsed);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) currentArt.getLayoutParams();
                    params.bottomMargin = (int) ((-80 + (int) (70 * ((collapsed) ? interpolatedTime : 1 - interpolatedTime))) * getActivity().getResources().getDisplayMetrics().density);
                    currentArt.setLayoutParams(params);

                    FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) navigationCard.getLayoutParams();
                    params2.bottomMargin = (int) (-25 - (int) (190 * ((collapsed) ? interpolatedTime : 1 - interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    navigationCard.setLayoutParams(params2);

                           /* FrameLayout.LayoutParams params3 =(FrameLayout.LayoutParams) smallImage.getLayoutParams();
                            params3.topMargin = (int)(5 + (int)(30 * ((collapsed)?interpolatedTime:1-interpolatedTime))* getActivity().getResources().getDisplayMetrics().density);
                            smallImage.setLayoutParams(params3);
*/



                    artCardImage.setScaleY(2 - ((collapsed) ? interpolatedTime : 1 - interpolatedTime));
                    artCardImage.setScaleX(2 - ((collapsed) ? interpolatedTime : 1 - interpolatedTime));

                    navigationCard.setScaleY(1 + ((collapsed) ? interpolatedTime : 1 - interpolatedTime));
                    navigationCard.setScaleX(1 + ((collapsed) ? interpolatedTime : 1 - interpolatedTime));
                    navigationCard.setAlpha(1- ((collapsed) ? interpolatedTime : 1 - interpolatedTime));


                    artCardImage.setAlpha( ((collapsed) ? interpolatedTime : 1 - interpolatedTime));




                    FrameLayout.LayoutParams params55 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                    params55.bottomMargin = (int) ((180 + (int) (70 * ((collapsed) ? interpolatedTime : 1 - interpolatedTime))) * getActivity().getResources().getDisplayMetrics().density);
                    fab.setLayoutParams(params55);

                    fab.setRotation(-90 + 180 *( (collapsed) ? interpolatedTime : 1 - interpolatedTime));
                    fab.setAlpha(( 0.5f + 0.5f *( (!collapsed) ? interpolatedTime : 1 - interpolatedTime)));

                    if (interpolatedTime == 1) {
                        collapsed = !collapsed;
                        this.cancel();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.setDuration(655);
            fab.setOnTouchListener(new View.OnTouchListener() {


                @Override
                public boolean onTouch(final View v, MotionEvent event) {

                    v.startAnimation(a);
                    return false;
                }
            });

            return rootView;
        }

        public static int getClosestWithinRange(Location user,float max){

            float min = Float.MAX_VALUE;
            int minIndex=-1;
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
                map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(
                        i != lastVisited && i != lastVisited + 1 ? R.drawable.marker_off : R.drawable.marker))
                        .alpha(i != lastVisited && i != lastVisited + 1 ? 0.7f : 1f)
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
            intent2.putExtra(Intent.EXTRA_TEXT, "I'm on the grand tour of the Dulwich outdoor gallery" );
            startActivity(Intent.createChooser(intent2, "Share via"));
        }

        void navigate(){
            LatLng last = Core.getGallery().get(lastVisited).getLocation();
            LatLng next = Core.getGallery().get(lastVisited+1).getLocation();
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
    }
}
