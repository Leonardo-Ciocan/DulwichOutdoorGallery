package team3m.dulwichoutdoorgallery;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.melnykov.fab.FloatingActionButton;

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

        boolean selected;
        View rootView;
        boolean isCardUp = false;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
             rootView = inflater.inflate(R.layout.fragment_explore, container, false);

            final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

            //gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);


            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //the map is ready here

                    ArrayList<Art> Gallery = Core.getGallery();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation(), 12));

                    for (int i = 0; i < Gallery.size(); i++) {

                        googleMap.addMarker(new MarkerOptions()
                                .title(String.valueOf(i))
                                .snippet(Gallery.get(i).getDescription())
                                .position(new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude())));

                    }
                    final TextView textAuthor= (TextView)getView().findViewById(R.id.author);

                    final TextView textTitle= (TextView)getView().findViewById(R.id.title);


                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {

                            textTitle.setText("");
                            textAuthor.setText("Select a piece of art");
                            selected = false;
                            final Animation a = new Animation() {
                                @Override
                                protected void applyTransformation(float interpolatedTime, Transformation t) {
                                    FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                                    params3.rightMargin = (int)(( 16 + (int)(-100*interpolatedTime))* getActivity().getResources().getDisplayMetrics().density);
                                    fab.setLayoutParams(params3);
                                    fab.setRotation(interpolatedTime*100);

                                    if(interpolatedTime>=1f){
                                        fab.setRotation(-100);
                                        this.cancel();
                                    }
                                }

                                @Override
                                public boolean willChangeBounds() {
                                    return true;
                                }
                            };

                            a.setInterpolator(new AccelerateDecelerateInterpolator());
                            a.setDuration(450);
                            fab.startAnimation(a);
                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {



                            if(!selected) {


                                final Animation a = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                                        params3.rightMargin = (int) ((16 - 100 + (int) (+100 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                                        fab.setLayoutParams(params3);

                                        fab.setRotation(-100 + interpolatedTime * 100);

                                    }

                                    @Override
                                    public boolean willChangeBounds() {
                                        return true;
                                    }
                                };


                                a.setInterpolator(new AccelerateDecelerateInterpolator());
                                a.setDuration(450);
                                fab.startAnimation(a);
                            }
                            String title= marker.getTitle();
                            int index= Integer.parseInt(title);
                            selected = true;

                           textTitle.setText(Core.getGallery().get(index).getName());

                            textAuthor.setText(Core.getGallery().get(index).getAuthor());




                            return true;
                        }
                    });

                }
            });

            final CardView card = (CardView) rootView.findViewById(R.id.card);

            final FloatingActionButton fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listLayout = (LinearLayout) rootView.findViewById(R.id.listLayout);
                    //listLayout.setVisibility(View.VISIBLE);
                    //populatedListView();
                    populatedListView();
                    final ListView list = (ListView) rootView.findViewById(R.id.artList);

                    final Animation a = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                                FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) card.getLayoutParams();
                                if(!isCardUp)
                                    params3.height = (int) ((200  + (250*interpolatedTime) ) * getActivity().getResources().getDisplayMetrics().density);
                                else
                                    params3.height = (int) ((200 + 250  - (250*interpolatedTime) ) * getActivity().getResources().getDisplayMetrics().density);
                                card.setLayoutParams(params3);

                                FrameLayout.LayoutParams params4 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                                if(!isCardUp)
                                    params4.bottomMargin = (int) ((115 + (250*interpolatedTime) ) * getActivity().getResources().getDisplayMetrics().density);
                                else
                                    params4.bottomMargin = (int) ((115 + 250 - (250*interpolatedTime) ) * getActivity().getResources().getDisplayMetrics().density);
                                fab2.setLayoutParams(params4);

                                list.setAlpha(((isCardUp) ? 1 - interpolatedTime : interpolatedTime*1));

                                if(interpolatedTime >= 1f){
                                    isCardUp = !isCardUp;
                                    this.cancel();

                                }
                            }

                        @Override
                        public boolean willChangeBounds() {
                            return true;
                        }
                    };


                    a.setInterpolator(new AccelerateDecelerateInterpolator());
                    a.setDuration(450);
                    fab.startAnimation(a);

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
