package team3m.dulwichoutdoorgallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

/**
 * This activity contains all the pages , and the root drawer
 */
public class CoreActivity extends ActionBarActivity {

    //These constants are used as IDs for fragment
    static int EXPLORE = 0;
    static int ROUTE = 1;
    static int BADGES = 2;
    static int GAME = 3;
    static int ABOUT = 4;

    /**
     * The titles of the fragments
     * titles[EXPLORE] retrieves the first title
     */
    static String[] titles = new String[]{"Explore" , "Route" , "Badges" , "Game","About"};

    /**
     * Used to save visited locations and other settings
     */
    public static SharedPreferences preferences ;

    /**
     * The indicator shown at the top of the routing page
     * It's in the CoreActivity because it resides outside the fragment in the Toolbar
     */
    public RouteProgressIndicator routeIndicator;

    /**
     * This object is used to interact with the dropbox API
     */
    public static DropboxAPI<AndroidAuthSession> sessionDropboxAPI;

    /**
     * The searchbox in the Toolbar , used to search the list in the Explore fragment
     */
    EditText searchBox;

    /**
     * Used to set up the drawer back button
     */
    ActionBarDrawerToggle toggle;

    /**
     * We keep a reference of the Explore fragment so we can trigger the search UI
     */
    team3m.dulwichoutdoorgallery.ExploreFragment ExploreFragment;

    /**
     * The Toolbar/ActionBar
     */
    private Toolbar toolbar;

    /**
     * The layout containing the drawer content and list items
     */
    private DrawerLayout drawerLayout;

    /**
     * Holds the buttons for the screen inside the drawer
     */
    private LinearLayout buttonHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets up the preferences object
        preferences = getSharedPreferences(
                "team3m.dulwichoutdoorgallery", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_core);
        super.onCreate(savedInstanceState);

        //This thread will pull new art from Dropbox
        new Thread(new Runnable() {
            @Override
            public void run() {
                Core.update(CoreActivity.this);
            }
        }).start();


        //The logo on the splash screen
        final ImageView logo = (ImageView) findViewById(R.id.screenView);
        //The overlay with the logo on it
        final FrameLayout logoOverlay = (FrameLayout) findViewById(R.id.logoOverlay);
        //The entry animation for the splash screen
        final Animation entry = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                logo.setAlpha(1-interpolatedTime);
                logoOverlay.setAlpha(1-interpolatedTime);
                logo.setScaleX(1+interpolatedTime*2.5f);
                logo.setScaleY(1+interpolatedTime*2.5f);
            }
        };
        entry.setInterpolator(new AccelerateDecelerateInterpolator());
        entry.setDuration(2000);

        //Initializing the dropbox API
        AppKeyPair appKeys = new AppKeyPair(Core.APP_KEY, Core.APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        sessionDropboxAPI = new DropboxAPI<AndroidAuthSession>(session);
        //Since this uses only one dropbox account , we use an existing token
        sessionDropboxAPI.getSession().setOAuth2AccessToken("cu234J18AOAAAAAAAAAABLG6O6CmDp57aRQ3frKJ2aNhrKumpe9M10HatiCxFDJN");

        //We slightly delay the initialization of the UI
        //to ensure that the brand colored background is drawn as soon as possible
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                    }
                });
            }
        }).start();

        //After 1.5 seconds we starts the entry animation that hides the splash screen
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logo.startAnimation(entry);
                    }
                });
            }
        }).start();
       }

    /**
     * This method aquires the references to the controls and sets up the UI
     */
    void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        searchBox = (EditText) findViewById(R.id.search);
        routeIndicator = (RouteProgressIndicator) findViewById(R.id.routeIndicator);
        //We start on the explore page
        toolbar.setTitle("Explore");

        //This section sets up the action bar and the drawer
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(CoreActivity.this, drawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerClosed(View drawerView) {
                searchBox.setVisibility(View.GONE);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                searchBox.setVisibility(View.GONE);
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        //We start at the Explore fragment
        ExploreFragment = new team3m.dulwichoutdoorgallery.ExploreFragment(searchBox);
        ExploreFragment.setRetainInstance(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentHolder, ExploreFragment)
                .commit();

        buttonHolder = (LinearLayout) findViewById(R.id.buttonHolder);

        //All the buttons in the drawer
        LinearLayout exploreButton = (LinearLayout)findViewById(R.id.btn_explore);
        LinearLayout routeButton = (LinearLayout)findViewById(R.id.btn_route);
        LinearLayout badgeButton = (LinearLayout)findViewById(R.id.btn_badges);
        LinearLayout gameButton = (LinearLayout)findViewById(R.id.btn_game);
        LinearLayout aboutButton = (LinearLayout)findViewById(R.id.btn_about);

        //We make them clickable
        makeButtonClickable(exploreButton , EXPLORE);
        makeButtonClickable(routeButton , ROUTE);
        makeButtonClickable(badgeButton , BADGES);
        makeButtonClickable(gameButton , GAME);
        makeButtonClickable(aboutButton , ABOUT);

        //We search when the text in the search box changes
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ExploreFragment.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //The notification control is on top of all fragments
        final BadgeNotification badgeNotification = (BadgeNotification) findViewById(R.id.badgeNotification);

        //When a badge is earned we trigger the notification
        Core.setBadgeListener(new BadgeEarnedListener() {
            @Override
            public void completedBadge(Badge badge) {
                badgeNotification.show(badge);
            }
        });

        //We initialize the badge list
        BadgesFragment.populateBadgeList();
        onPostCreate(null);
    }

    /**
     * Makes a drawer button clickable and highlightable
     * @param button The layout that represents the button
     * @param id The id of the button to know which fragment to show
     */
    void makeButtonClickable(LinearLayout button  , final int id){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We hide the search button if a button other than explore is pressed
                toolbar.getMenu().getItem(0).setVisible(id == EXPLORE);
                //We set the associated title
                toolbar.setTitle(titles[id]);

                //When a button is pressed we must unhighlight all other buttons
                for (int x = 1; x < buttonHolder.getChildCount(); x++) {
                    //We set a transparent background
                    buttonHolder.getChildAt(x).setBackground(null);
                    //Change the icon and text to their white version
                    LinearLayout btn = (LinearLayout) buttonHolder.getChildAt(x);
                    TextView txt = (TextView) (btn).getChildAt(1);

                    int iconID = getResources().getIdentifier(
                            titles[x - 1].toLowerCase() + "_dark",
                            "drawable",
                            getApplicationContext().getPackageName());

                    ((ImageView) (btn).getChildAt(0)).setImageDrawable(getResources()
                            .getDrawable(iconID));
                    txt.setTextColor(Color.parseColor("#bf000000"));
                }

                //We set the selected background to the brand color
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                //Set the text color to white
                TextView txt = (TextView) ((LinearLayout) v).getChildAt(1);
                txt.setTextColor(Color.WHITE);
                ImageView img = ((ImageView) (((LinearLayout) v)).getChildAt(0));
                //Assign the light colored icon
                img.setImageDrawable(getResources()
                        .getDrawable(
                                getResources().getIdentifier(titles[id].toLowerCase() + "_light",
                                "drawable",
                                getApplicationContext().getPackageName())));


                drawerLayout.closeDrawers();

                //We introduce a 300ms delay to avoid any UI lag
                //This is consistent with Google's browser click delay
                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        //Depending on the choice we create the proper fragment
                        Fragment fragment = null;

                        if (id == EXPLORE) {
                            fragment = new team3m.dulwichoutdoorgallery.ExploreFragment(searchBox);
                            ExploreFragment = (ExploreFragment) fragment;
                        } else if (id == ROUTE) {
                            fragment = new RouteActivity.PlaceholderFragment();
                        } else if (id == BADGES) {
                            fragment = new BadgesFragment();
                        } else if (id == GAME) {
                            fragment = new GameFragment();
                        } else if (id == ABOUT) {
                            fragment = new AboutFragment();
                        }
                        //We show or hide the X button uneless we're making the route page
                        toolbar.getMenu().getItem(1).setVisible(id == ROUTE);
                        //Same for the indicator
                        routeIndicator.setVisibility(id == ROUTE ? View.VISIBLE : View.GONE);


                        //We replaced the content with the new fragment
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.show, R.anim.hide)
                                .replace(R.id.contentHolder, fragment)
                                .commit();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toggle!=null)toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_core, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;

        //On search we show the search box , and start with a clean search
        if(item.getItemId() == R.id.search){
            searchBox.setVisibility(View.VISIBLE);
            searchBox.setText("");
            ExploreFragment.search("");
            searchBox.requestFocus();

            return true;
        }

        //When pressing on the close button
        if(item.getItemId() == R.id.close){
            //We prompt the user to avoid accidental touches
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            //We create a new fragment and show it without animation to reset the
                            //route progress
                            Fragment fragment = null;

                            fragment = new RouteActivity.PlaceholderFragment();

                            routeIndicator.setVisibility(View.VISIBLE);

                            getSupportFragmentManager().beginTransaction()
                                    //.setCustomAnimations(R.anim.show , R.anim.hide)
                                    .replace(R.id.contentHolder, fragment)
                                    .commit();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You will lose your progress and a new route will be calculated.")
                    .setPositiveButton("Start new route", dialogClickListener)
                    .setNegativeButton("No", null).show();
        }

        return super.onOptionsItemSelected(item);
    }
}