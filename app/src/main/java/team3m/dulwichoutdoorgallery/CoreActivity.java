package team3m.dulwichoutdoorgallery;

import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CoreActivity extends ActionBarActivity {

    static int EXPLORE = 0;
    static int ROUTE = 1;
    static int BADGES = 2;
    static int GAME = 3;
    static int ABOUT = 4;

    static String[] titles = new String[]{"Explore" , "Route" , "Badges" , "Game","About"};

    EditText searchBox;
    ActionBarDrawerToggle toggle;
    team3m.dulwichoutdoorgallery.ExploreFragment ExploreFragment;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout buttonHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        searchBox = (EditText) findViewById(R.id.search);
        /*Palette.generateAsync(BitmapFactory.decodeResource(getResources() , R.drawable.reka_europa_and_the_bull), new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                toolbar.setBackground(new ColorDrawable(palette.getVibrantColor(getResources().getColor(R.color.brand))));
            }
        });*/

        toolbar.setTitle("Explore");

        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
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

        /*
        Button routeButton = (Button)findViewById(R.id.btn_route);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.getMenu().getItem(0).setVisible(false);

                toolbar.setTitle("Tours");
                for(int x =0;x < buttonHolder.getChildCount();x++){
                    buttonHolder.getChildAt(x).setBackground(null);
                    ((Button)buttonHolder.getChildAt(x)).setTextColor(Color.BLACK);
                }
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                ((Button)v).setTextColor(Color.WHITE);

                drawerLayout.closeDrawers();

                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        RouteActivity.PlaceholderFragment routeFragment = new RouteActivity.PlaceholderFragment();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentHolder, routeFragment)
                                .commit();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        });

        Button gameButton = (Button)findViewById(R.id.btn_game);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.getMenu().getItem(0).setVisible(false);

                toolbar.setTitle("Game");
                for(int x =0;x < buttonHolder.getChildCount();x++){
                    buttonHolder.getChildAt(x).setBackground(null);
                    ((Button)buttonHolder.getChildAt(x)).setTextColor(Color.BLACK);
                }
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                ((Button)v).setTextColor(Color.WHITE);

                drawerLayout.closeDrawers();

                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        GameFragment gameFragment = GameFragment.newInstance();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentHolder, gameFragment)
                                .commit();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        });


        Button badgesButton = (Button)findViewById(R.id.btn_badge);
        badgesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.getMenu().getItem(0).setVisible(false);


                toolbar.setTitle("Badges");
                for(int x =0;x < buttonHolder.getChildCount();x++){
                    buttonHolder.getChildAt(x).setBackground(null);
                    ((Button)buttonHolder.getChildAt(x)).setTextColor(Color.BLACK);
                }
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                ((Button)v).setTextColor(Color.WHITE);

                drawerLayout.closeDrawers();

                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        BadgesActivity.PlaceholderFragment fragment = new  BadgesActivity.PlaceholderFragment();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentHolder, fragment)
                                .commit();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        });

*/
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
                        //ExploreFragment = new ExploreActivity.PlaceholderFragment(searchBox);
                        Fragment fragment = null;
                        if(id == EXPLORE){
                            fragment = new team3m.dulwichoutdoorgallery.ExploreFragment(searchBox);
                        }
                        else if(id == ROUTE){
                            fragment = new RouteActivity.PlaceholderFragment();
                        }else if(id == BADGES){
                            fragment = new BadgesActivity.PlaceholderFragment();
                        }else if(id == GAME){
                            fragment = new GameFragment();
                        }
                        else if(id == ABOUT){
                            fragment = new GameFragment();
                        }
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
        toggle.syncState();
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