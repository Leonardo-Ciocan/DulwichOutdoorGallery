package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class CoreActivity extends ActionBarActivity {

    static int EXPLORE = 0;
    static int ROUTE = 1;
    static int BADGES = 2;
    static int GAME = 3;
    static int ABOUT = 4;

    static String[] titles = new String[]{"Explore" , "Route" , "Badges" , "Game","About"};

    public static SharedPreferences preferences ;
    public RouteProgressIndicator routeIndicator;

    // In the class declaration section:
    public static DropboxAPI<AndroidAuthSession> mDBApi;
    EditText searchBox;
    ActionBarDrawerToggle toggle;
    team3m.dulwichoutdoorgallery.ExploreFragment ExploreFragment;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout buttonHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_core);
        super.onCreate(savedInstanceState);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Core.update(CoreActivity.this);
            }
        }).start();*/
        Log.v("xwuwuuwuwu" , "creating activity");



        final ImageView logo = (ImageView) findViewById(R.id.screenView);
        final FrameLayout logoOverlay = (FrameLayout) findViewById(R.id.logoOverlay);
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





// And later in some initialization function:
       AppKeyPair appKeys = new AppKeyPair(Core.APP_KEY, Core.APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
       // mDBApi.getSession().startOAuth2Authentication(CoreActivity.this);
        mDBApi.getSession().setOAuth2AccessToken("cu234J18AOAAAAAAAAAABLG6O6CmDp57aRQ3frKJ2aNhrKumpe9M10HatiCxFDJN");

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


    void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        searchBox = (EditText) findViewById(R.id.search);
        routeIndicator = (RouteProgressIndicator) findViewById(R.id.routeIndicator);
        toolbar.setTitle("Explore");

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


        ExploreFragment = new team3m.dulwichoutdoorgallery.ExploreFragment(searchBox);
        ExploreFragment.setRetainInstance(true);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentHolder, ExploreFragment)
                .commit();


        buttonHolder = (LinearLayout) findViewById(R.id.buttonHolder);


        LinearLayout exploreButton = (LinearLayout)findViewById(R.id.btn_explore);
        LinearLayout routeButton = (LinearLayout)findViewById(R.id.btn_route);
        LinearLayout badgeButton = (LinearLayout)findViewById(R.id.btn_badges);
        LinearLayout gameButton = (LinearLayout)findViewById(R.id.btn_game);
        LinearLayout aboutButton = (LinearLayout)findViewById(R.id.btn_about);

        makeButtonClickable(exploreButton , EXPLORE);
        makeButtonClickable(routeButton , ROUTE);
        makeButtonClickable(badgeButton , BADGES);
        makeButtonClickable(gameButton , GAME);
        makeButtonClickable(aboutButton , ABOUT);

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

        final BadgeNotification badgeNotification = (BadgeNotification) findViewById(R.id.badgeNotification);
        //new Thread().start();

        Core.setBadgeListener(new BadgeEarnedListener() {
            @Override
            public void completedBadge(Badge badge) {
                badgeNotification.show(badge);
            }
        });

        BadgesActivity.PlaceholderFragment.populateBadgeList();
        onPostCreate(null);

        //Core.notifyBadgeEarned(BadgesActivity.badges.get(0));


    }
    void makeButtonClickable(LinearLayout exploreButton  , final int id){
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("x--a" , "hello it is app");
                toolbar.getMenu().getItem(0).setVisible( id == EXPLORE);
                toolbar.setTitle(titles[id]);

                for(int x =0;x < buttonHolder.getChildCount();x++){
                    buttonHolder.getChildAt(x).setBackground(null);
                    TextView txt = (TextView) ((LinearLayout)buttonHolder.getChildAt(x)).getChildAt(1);
                    ((ImageView)( ((LinearLayout) buttonHolder.getChildAt(x)).getChildAt(0))).setImageDrawable(getResources()
                            .getDrawable(getResources().getIdentifier(titles[x].toLowerCase()+"_dark", "drawable", getApplicationContext().getPackageName())));
                    txt.setTextColor(Color.parseColor("#bf000000"));
                }
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                //((Button)v).setTextColor(Color.WHITE);
                TextView txt = (TextView) ((LinearLayout)v).getChildAt(1);
                txt.setTextColor(Color.WHITE);
                ((ImageView)( ((LinearLayout) v)).getChildAt(0)).setImageDrawable(getResources()
                        .getDrawable(getResources().getIdentifier(titles[id].toLowerCase() + "_light", "drawable", getApplicationContext().getPackageName())));

                drawerLayout.closeDrawers();

                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        //ExploreFragment = new ExploreActivity.AboutFragment(searchBox);
                        Fragment fragment = null;
                        if(id == EXPLORE){
                            fragment = new team3m.dulwichoutdoorgallery.ExploreFragment(searchBox);
                            ExploreFragment = (ExploreFragment)fragment;
                        }
                        else if(id == ROUTE){
                            fragment = new RouteActivity.PlaceholderFragment();
                        }else if(id == BADGES){
                            fragment = new BadgesActivity.PlaceholderFragment();
                        }else if(id == GAME){
                            fragment = new GameFragment();
                        }
                        else if(id == ABOUT){
                            fragment = new AboutFragment();
                        }
                        toolbar.getMenu().getItem(1).setVisible(id==ROUTE);

                        routeIndicator.setVisibility(id == ROUTE ? View.VISIBLE : View.GONE);

                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.show , R.anim.hide)
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

        if(item.getItemId() == R.id.search){
            searchBox.setVisibility(View.VISIBLE);
            searchBox.setText("");
            ExploreFragment.search("");
            searchBox.requestFocus();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}