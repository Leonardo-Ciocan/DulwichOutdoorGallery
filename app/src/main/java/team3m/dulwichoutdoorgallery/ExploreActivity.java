package team3m.dulwichoutdoorgallery;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class ExploreActivity extends ActionBarActivity {
    protected static LinearLayout listLayout;

    PlaceholderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        if (savedInstanceState == null) {
            //This activity is empty , the fragment holds the actual UI
            //This is useful because we can move around that fragment in a tablet layout
            fragment = new PlaceholderFragment(null);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
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


    public void onBackPressed() {

        if (fragment.isCardUp)
            fragment.toggleCardAnim();
        else
            super.onBackPressed();
    }

    public static class PlaceholderFragment extends Fragment {

        private ArrayAdapter<Art> adapter;

        EditText searchField;
        public PlaceholderFragment(EditText searchField) {
            this.searchField = searchField;
        }

        public void toggleCardAnim() {
            card.startAnimation(toggleCardAnim);
        }

        boolean selected;
        View rootView;
        public boolean isCardUp = false;
        Animation hideNavAnim;
        public Animation toggleCardAnim;
        Animation toggleNavButton;
        CardView card;
        FloatingActionButton fab;
        GoogleMap map;
        TextView textAuthor, textTitle;

        ArrayList<Marker> markers = new ArrayList<Marker>();
        Marker lastMaker = null;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_explore, container, false);

            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//gets the map control
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            card = (CardView) rootView.findViewById(R.id.card);

            final FloatingActionButton fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab2);
            final ListView list = (ListView) rootView.findViewById(R.id.artList);
            final FrameLayout mapHolder = (FrameLayout) rootView.findViewById(R.id.mapHolder);
            textAuthor = (TextView) rootView.findViewById(R.id.author);

            textTitle = (TextView) rootView.findViewById(R.id.title);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    card.startAnimation(toggleCardAnim);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(position).getLocation(), 15));
                    selectArt(position , false);
                }
            });
            final Button btnInfo  = (Button) rootView.findViewById(R.id.info);
            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity() , InfoActivity.class);
                    String title = lastMaker.getTitle();
                    int index = Integer.parseInt(title);
                    i.putExtra("index" , index);
                    startActivity(i);
                }
            });

            hideNavAnim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                    params3.rightMargin = (int) ((16 + (int) (-100 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    fab.setLayoutParams(params3);
                    fab.setRotation(interpolatedTime * 100);

                    FrameLayout.LayoutParams params6 = (FrameLayout.LayoutParams) btnInfo.getLayoutParams();
                    params6.bottomMargin = (int) ((5 - (45 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    btnInfo.setLayoutParams(params6);

                    if (interpolatedTime >= 1f) {

                        fab.setRotation(-100);
                        this.cancel();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            hideNavAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            hideNavAnim.setDuration(450);

            toggleNavButton = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                    params3.rightMargin = (int) ((16 - 100 + (int) (+100 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    fab.setLayoutParams(params3);

                    fab.setRotation(-100 + interpolatedTime * 100);

                    FrameLayout.LayoutParams params6 = (FrameLayout.LayoutParams) btnInfo.getLayoutParams();
                    params6.bottomMargin = (int) ((-40 + (45 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    btnInfo.setLayoutParams(params6);

                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };


            toggleNavButton.setInterpolator(new AccelerateDecelerateInterpolator());
            toggleNavButton.setDuration(450);

            toggleCardAnim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) card.getLayoutParams();
                    if (!isCardUp)
                        params3.height = (int) ((200 + (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    else
                        params3.height = (int) ((200 + 250 - (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    card.setLayoutParams(params3);

                    FrameLayout.LayoutParams params4 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                    if (!isCardUp)
                        params4.bottomMargin = (int) ((115 + (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    else
                        params4.bottomMargin = (int) ((115 + 250 - (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    fab2.setLayoutParams(params4);

                    /*FrameLayout.LayoutParams params5 = (FrameLayout.LayoutParams) searchCard.getLayoutParams();
                    if (isCardUp)
                        params5.topMargin = (int) (((-200 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    else
                        params5.topMargin = (int) ((-200 + (207 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                    searchCard.setLayoutParams(params5);

                    searchCard.setAlpha(((isCardUp) ? 1 - interpolatedTime : interpolatedTime * 1));*/
                    list.setAlpha(((isCardUp) ? 1 - interpolatedTime : interpolatedTime * 1));

                    mapHolder.setAlpha(((!isCardUp) ? 1.5f - interpolatedTime : 0.5f + interpolatedTime * 1));

                    fab2.setImageDrawable(getResources().getDrawable(!isCardUp ? R.drawable.ic_action_content_add : R.drawable.ic_navigation_menu));
                    fab2.setRotation(((isCardUp) ? 135 - interpolatedTime * 135 : interpolatedTime * 135));



                    if (interpolatedTime >= 1f) {
                        if(isCardUp && selected) fab.startAnimation(toggleNavButton);
                        if(isCardUp && searchField != null)searchField.setVisibility(View.GONE);
                        isCardUp = !isCardUp;
                        this.cancel();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };


            toggleCardAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            toggleCardAnim.setDuration(450);


            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //the map is ready here

                    map = googleMap;
                    ArrayList<Art> Gallery = Core.getGallery();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation(), 12));

                    for (int i = 0; i < Gallery.size(); i++) {

                        Marker m = googleMap.addMarker(new MarkerOptions()
                                .title(String.valueOf(i))
                                .snippet(Gallery.get(i).getDescription())
                                .position(new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude())));
                        markers.add(m);

                    }


                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {

                            if (!isCardUp) {
                                textTitle.setText("");
                                textAuthor.setText("Select a piece of art");

                                if (selected) fab.startAnimation(hideNavAnim);
                                selected = false;
                                deselectLast();
                            } else {
                                card.startAnimation(toggleCardAnim);
                            }
                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            if(!isCardUp) {
                                String title = marker.getTitle();
                                int index = Integer.parseInt(title);

                                selectArt(index , true);
                            }

                            return true;
                        }
                    });

                    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                        Marker last;

                        @Override
                        public void onMyLocationChange(Location location) {
                            if (last != null) last.remove();
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                            last = map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                    .defaultMarker(100)).position(loc));


                        }
                    };

                    map.setMyLocationEnabled(true);
                    map.setOnMyLocationChangeListener(myLocationChangeListener);

                }
            });


            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listLayout = (LinearLayout) rootView.findViewById(R.id.listLayout);
                    //listLayout.setVisibility(View.VISIBLE);
                    //populatedListView();
                    populatedListView();

                    if (selected && !isCardUp) {
                        fab.startAnimation(hideNavAnim);
                        //selected = false;
                    }
                    fab2.startAnimation(toggleCardAnim);

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = lastMaker.getTitle();
                    int index = Integer.parseInt(title);
                    navigate(index);
                }
            });

           /* final TextView search = (TextView) rootView.findViewById(R.id.search);
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams) list.getLayoutParams();
                    params7.topMargin = (int) ((hasFocus ? 100 : 0) * getActivity().getResources().getDisplayMetrics().density);
                    list.setLayoutParams(params7);
                }
            });*/
            adapter = new MyAdapter(getActivity(), R.layout.item_view, Core.getGallery());
            ListView list2 = (ListView)rootView.findViewById(R.id.artList);
            list2.setAdapter(adapter);

            return rootView;
        }


        public void selectArt(int pos , boolean shouldSkipAnimation) {
            if (!selected && shouldSkipAnimation) {
                fab.startAnimation(toggleNavButton);
            }


            selected = true;

            textTitle.setText(Core.getGallery().get(pos).getName());

            textAuthor.setText(Core.getGallery().get(pos).getAuthor());

            if (lastMaker != null) {
                deselectLast();
            }

            markers.get(pos).remove();
            Marker m = map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(200))
                    .title(String.valueOf(pos))
                    .snippet(Core.getGallery().get(pos).getDescription())
                    .position(new LatLng(Core.getGallery().get(pos).getLatitude(), Core.getGallery().get(pos).getLongitude())));
            markers.remove(pos);
            markers.add(pos, m);

            lastMaker = m;
        }

        void deselectLast() {
            if(lastMaker != null) {
                lastMaker.remove();
                markers.remove(lastMaker);
                String title = lastMaker.getTitle();
                int index = Integer.parseInt(title);
                Marker m2 = map.addMarker(new MarkerOptions()
                        .title(String.valueOf(index))
                        .snippet(Core.getGallery().get(index).getDescription())
                        .position(new LatLng(Core.getGallery().get(index).getLatitude(), Core.getGallery().get(index).getLongitude())));
                markers.add(index, m2);
            }
        }

        public void populatedListView() {


        }

        void navigate(int index) {
            LatLng last = Core.getGallery().get(index).getLocation();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + last.latitude + "," + last.longitude));
            startActivity(intent);
        }

        public void search(String text){
            if (selected && !isCardUp) {
                fab.startAnimation(hideNavAnim);
                //selected = false;
            }
            if(!isCardUp)toggleCardAnim();
            adapter.getFilter().filter(text);
        }


    }

}
