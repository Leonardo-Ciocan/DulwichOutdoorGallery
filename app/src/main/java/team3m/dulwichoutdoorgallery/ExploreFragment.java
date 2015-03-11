package team3m.dulwichoutdoorgallery;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * The fragment that represents the explore page
 */
public class ExploreFragment extends Fragment {

    /**
     *
     * @param searchField This needs to be the search field in the activity
     *                    kept in order to hide it when needed
     */
    public ExploreFragment(EditText searchField) {
        this.searchField = searchField;
    }
    public ExploreFragment(){}

    /**
     * Used for the list of art
     */
    private ArrayAdapter<Art> adapter;

    /**
     * The EditText search field in the action bar
     */
    EditText searchField;

    /**
     * The button at the button , shown when the an art is artSelected
     */
    private Button btnInfo;

    /**
     * The list inside the card , shows the art , uses the adapter
     */
    private ListView list;

    /**
     * The button that brings up the card with the art list
     */
    private FloatingActionButton cardButton;

    /**
     * The container of the map
     */
    private FrameLayout mapHolder;

    /**
     * The fragment of the map
     */
    private SupportMapFragment mapFragment;

    /**
     * Starts the card animation
     */
    public void toggleCardAnim() {
        card.startAnimation(toggleCardAnim);
    }

    /**
     * Whether an art is selected
     */
    boolean artSelected;

    /**
     * The view that is inflated , contains all other views
     */
    View rootView;

    /**
     * Whether the card is up or down
     */
    public boolean isCardUp = false;

    /**
     * This animation hides the navigation button and the info button
     */
    Animation hideNavAnim;

    /**
     * Animation to toggle the card up or down
     */
    public Animation toggleCardAnim;

    /**
     * Shows the navigation button and info button
     */
    Animation showNavButton;

    /**
     * The card at the bottom of the screen
     */
    CardView card;

    /**
     * The button on the right , used to launch navigation
     */
    FloatingActionButton navigationButton;

    /**
     * The actual map
     */
    GoogleMap map;
    /**
     * Shows the selected art's title and author
     */
    TextView textAuthor, textTitle;

    /**
     * We keep track of all tracker on the map so we can highlight the one selected
     */
    ArrayList<Marker> markers = new ArrayList<>();
    /**
     * The current marker used to highlight newer selections
     */
    Marker currentMarker = null;

    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creating the UI
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        setup();

        setupGoogleMaps();

        createAnimations();

        createHandlers();

        return rootView;
    }

    /**
     * Getting references to all needed controls
     */
    void setup(){
        navigationButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        mapFragment= (SupportMapFragment)   getChildFragmentManager().findFragmentById(R.id.map);
        card       = (CardView)             rootView.findViewById(R.id.card);
        cardButton = (FloatingActionButton) rootView.findViewById(R.id.fab2);
        list       = (ListView)             rootView.findViewById(R.id.artList);
        mapHolder  = (FrameLayout)          rootView.findViewById(R.id.mapHolder);
        textAuthor = (TextView)             rootView.findViewById(R.id.author);
        textTitle  = (TextView)             rootView.findViewById(R.id.title);
        btnInfo    = (Button)               rootView.findViewById(R.id.info);

        adapter = new MyAdapter(getActivity() , R.id.list_item , Core.getGallery());
        list.setAdapter(adapter);
    }

    /**
     * Creates reusable animations
     */
    void createAnimations(){


        //Hides the navigation button , and the info one
        hideNavAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                //Moving the button offscreen to the right
                FrameLayout.LayoutParams navBtnLayout = (FrameLayout.LayoutParams) navigationButton.getLayoutParams();
                navBtnLayout.rightMargin = (int) ((16 + (int) (-100 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                navigationButton.setLayoutParams(navBtnLayout);

                //Rotating it
                navigationButton.setRotation(interpolatedTime * 100);

                //Moving the info button below the bottom
                FrameLayout.LayoutParams infoBtnLayout = (FrameLayout.LayoutParams) btnInfo.getLayoutParams();
                infoBtnLayout.bottomMargin = (int) ((5 - (45 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                btnInfo.setLayoutParams(infoBtnLayout);

                //At the end reset rotation and finish animation
                if (interpolatedTime >= 1f) {
                    navigationButton.setRotation(-100);
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


        //Shows the navigation button , and the info one
        showNavButton = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                //Show nav button
                FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) navigationButton.getLayoutParams();
                params3.rightMargin = (int) ((16 - 100 + (int) (+100 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                navigationButton.setLayoutParams(params3);

                navigationButton.setRotation(-100 + interpolatedTime * 100);

                //Show info button
                FrameLayout.LayoutParams params6 = (FrameLayout.LayoutParams) btnInfo.getLayoutParams();
                params6.bottomMargin = (int) ((-40 + (45 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                btnInfo.setLayoutParams(params6);

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        showNavButton.setInterpolator(new AccelerateDecelerateInterpolator());
        showNavButton.setDuration(450);

        //Toggles the card up or down
        toggleCardAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                toggleCard(interpolatedTime);

                //When it ends , restore selected state and hide search field if needed
                if (interpolatedTime >= 1f) {
                    if(isCardUp && artSelected) navigationButton.startAnimation(showNavButton);
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
    }

    /**
     * Creates and connects the required event handlers
     */
    void createHandlers(){
        //When an item is pressed we hide the card and move to the location
        //We then use selectArt to change the rest of the UI
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                card.startAnimation(toggleCardAnim);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(position).getLocation(), 15));
                selectArt(position, false);
            }
        });

        //The info button goes to the InfoActivity , passes the index selected
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InfoActivity.class);
                String title = currentMarker.getTitle();
                int index = Integer.parseInt(title);
                i.putExtra("index", index);
                startActivity(i);
            }
        });


        //When the card button is clicked trigger the card animation and appropriate hiding anim
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (artSelected && !isCardUp) {
                    navigationButton.startAnimation(hideNavAnim);
                }
                cardButton.startAnimation(toggleCardAnim);

            }
        });

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = currentMarker.getTitle();
                int index = Integer.parseInt(title);
                navigate(index);
            }
        });
    }

    /**
     * Sets up Google Maps and related events
     */
    void setupGoogleMaps(){
        //Getting the maps
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //the map is ready here
                map = googleMap;
                ArrayList<Art> Gallery = Core.getGallery();
                //Move the map to the gallery location
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Core.Gallery.get(10).getLocation(), 12));

                //Add a marker for each art
                for (int i = 0; i < Gallery.size(); i++) {
                    //Core.getGallery().get(i).getDrawable(getActivity());
                    Marker m = googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(Core.getLocationtatus(i) ? 0:200))
                            .title(String.valueOf(i))
                            .snippet(Gallery.get(i).getDescription())
                            .position(new LatLng(Gallery.get(i).getLatitude(), Gallery.get(i).getLongitude())));
                    markers.add(m);

                }

                //When clicking on the map
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        //If the card is up , we dismiss the card
                        //Else we deselect the select art
                        if (!isCardUp) {
                            textTitle.setText("");
                            textAuthor.setText("Select a piece of art");

                            if (artSelected) navigationButton.startAnimation(hideNavAnim);
                            artSelected = false;
                            deselectLast();
                        } else {
                            card.startAnimation(toggleCardAnim);
                        }
                    }
                });

                //When an art is clicked we make it selected
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        if (!isCardUp) {
                            String title = marker.getTitle();
                            int index = Integer.parseInt(title);

                            selectArt(index, true);
                        }

                        return true;
                    }
                });

                //We check the user's location so we can show it on the map
                GoogleMap.OnMyLocationChangeListener myLocationChangeListener =
                    new GoogleMap.OnMyLocationChangeListener() {

                    Marker last;

                    @Override
                    public void onMyLocationChange(Location location) {
                        if (last != null) last.remove();
                        //LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        //We keep the marker location so we can update it on change
                        //last = map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.).position(loc));

                        int n = RouteActivity.PlaceholderFragment.getClosestWithinRange(location,11);
                        if(n != -1){
                            markers.get(n).setIcon(BitmapDescriptorFactory.defaultMarker(0));
                            Core.setLocationVisited(n);
                            Core.updateBadges();
                        }

                    }
                };

                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(myLocationChangeListener);

            }
        });
    }

    /**
     * Selects a piece of art
     * @param pos The index in Core.Gallery
     * @param shouldSkipAnimation Whether to skip the animation for button , this fixes
     *                            unintended triggering of the animation
     */
    public void selectArt(int pos , boolean shouldSkipAnimation) {
        if (!artSelected && shouldSkipAnimation) {
            navigationButton.startAnimation(showNavButton);
        }


        artSelected = true;

        textTitle.setText(Core.getGallery().get(pos).getName());

        textAuthor.setText(Core.getGallery().get(pos).getAuthor());

        if (currentMarker != null) {
            deselectLast();
        }

        markers.get(pos).remove();
        Marker m = map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory
                        .defaultMarker(Core.getLocationtatus(pos) ? 330:300))
                .title(String.valueOf(pos))
                .snippet(Core.getGallery().get(pos).getDescription())
                .position(new LatLng(Core.getGallery().get(pos).getLatitude(), Core.getGallery().get(pos).getLongitude())));
        markers.remove(pos);
        markers.add(pos, m);

        currentMarker = m;
    }

    /**
     * Deselects the last selected marker
     */
    void deselectLast() {
        if(currentMarker != null) {
            currentMarker.remove();
            markers.remove(currentMarker);
            String title = currentMarker.getTitle();
            int index = Integer.parseInt(title);
            Marker m2 = map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(Core.getLocationtatus(index) ? 0:200))
                    .title(String.valueOf(index))
                    .snippet(Core.getGallery().get(index).getDescription())
                    .position(new LatLng(Core.getGallery().get(index).getLatitude(), Core.getGallery().get(index).getLongitude())));
            markers.add(index, m2);
        }
    }

    /**
     * Launches navigation
     * @param index The index of the art to navigate to
     */
    void navigate(int index) {
        LatLng last = Core.getGallery().get(index).getLocation();
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + last.latitude + "," + last.longitude));
        startActivity(intent);
    }

    /**
     * Search for art
     * @param text What to search
     */
    public void search(String text){
        if (artSelected && !isCardUp) {
            navigationButton.startAnimation(hideNavAnim);
            //artSelected = false;
        }
        if(!isCardUp) toggleCardAnim();
        adapter.getFilter().filter(text);
    }

    /**
     * [temporary] replaces applyTransformations
     * @param interpolatedTime The current time
     */
    void toggleCard(float interpolatedTime){

        FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) card.getLayoutParams();
        if (!isCardUp)
            params3.height = (int) ((200 + (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
        else
            params3.height = (int) ((200 + 250 - (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
        card.setLayoutParams(params3);

        FrameLayout.LayoutParams params4 = (FrameLayout.LayoutParams) cardButton.getLayoutParams();
        if (!isCardUp)
            params4.bottomMargin = (int) ((115 + (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
        else
            params4.bottomMargin = (int) ((115 + 250 - (250 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
        cardButton.setLayoutParams(params4);

                /*FrameLayout.LayoutParams params5 = (FrameLayout.LayoutParams) searchCard.getLayoutParams();
                if (isCardUp)
                    params5.topMargin = (int) (((-200 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                else
                    params5.topMargin = (int) ((-200 + (207 * interpolatedTime)) * getActivity().getResources().getDisplayMetrics().density);
                searchCard.setLayoutParams(params5);

                searchCard.setAlpha(((isCardUp) ? 1 - interpolatedTime : interpolatedTime * 1));*/
        list.setAlpha(((isCardUp) ? 1 - interpolatedTime : interpolatedTime * 1));

        mapHolder.setAlpha(((!isCardUp) ? 1.5f - interpolatedTime : 0.5f + interpolatedTime * 1));

        cardButton.setImageDrawable(getResources().getDrawable(!isCardUp ? R.drawable.ic_action_content_add : R.drawable.ic_navigation_menu));
        cardButton.setRotation(((isCardUp) ? 135 - interpolatedTime * 135 : interpolatedTime * 135));

    }


}
