package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.SharedPreferences;
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

    // In the class declaration section:
    public static DropboxAPI<AndroidAuthSession> mDBApi;
    EditText searchBox;
    ActionBarDrawerToggle toggle;
    team3m.dulwichoutdoorgallery.ExploreFragment ExploreFragment;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout buttonHolder;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        Log.v("xwuwuuwuwu" , "creating activity");


        // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(Core.APP_KEY, Core.APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
       // mDBApi.getSession().startOAuth2Authentication(CoreActivity.this);
        mDBApi.getSession().setOAuth2AccessToken("cu234J18AOAAAAAAAAAABLG6O6CmDp57aRQ3frKJ2aNhrKumpe9M10HatiCxFDJN");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Core.update(CoreActivity.this);
            }
        }).start();
/*

        new Thread(new Runnable() {
            @Override
            public void run() {

                //File file = new File("");

                DropboxAPI.Entry dirent = null;
                try {
                    dirent = mDBApi.metadata("/saved/", 1000, null, true, null);
                } catch (DropboxException e) {
                    e.printStackTrace();
                }
                ArrayList<DropboxAPI.Entry> files = new ArrayList<DropboxAPI.Entry>();
                ArrayList<String> dir=new ArrayList<String>();
                int i = 0;
                for (DropboxAPI.Entry ent: dirent.contents)
                {
                    files.add(ent);// Add it to the list of thumbs we can choose from
                    //dir = new ArrayList<String>();
                    dir.add(files.get(i++).path);
                }

                File folder = getApplicationContext().getFilesDir();
                File file = new File(folder.getAbsolutePath()+File.separator+"hi.txt");

                // OutputStream outputStream;
                boolean filee = file.exists();
                try {
                    outputStream = getApplicationContext().openFileOutput("hi.txt" , MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    DropboxAPI.DropboxFileInfo info = mDBApi.getFile("/saved/hi.txt", null, outputStream, null);
                } catch (DropboxException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                            openFileInput("hi.txt")));
                    String inputString;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((inputString = inputReader.readLine()) != null) {
                        stringBuffer.append(inputString + "\n");
                    }
                    Log.e("wuwuuwuuw", stringBuffer.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/


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
                        RouteActivity.AboutFragment routeFragment = new RouteActivity.AboutFragment();

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
                        BadgesActivity.AboutFragment fragment = new  BadgesActivity.AboutFragment();

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

        BadgesActivity.PlaceholderFragment.populateBadgeList();

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