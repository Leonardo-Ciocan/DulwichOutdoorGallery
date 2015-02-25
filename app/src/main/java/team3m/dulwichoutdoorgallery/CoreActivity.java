package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.FileOutputStream;
import java.io.IOException;


public class CoreActivity extends ActionBarActivity {

    EditText searchBox;
    ActionBarDrawerToggle toggle;
    ExploreActivity.PlaceholderFragment ExploreFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        searchBox = (EditText) findViewById(R.id.search);
        /*Palette.generateAsync(BitmapFactory.decodeResource(getResources() , R.drawable.reka_europa_and_the_bull), new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                toolbar.setBackground(new ColorDrawable(palette.getVibrantColor(getResources().getColor(R.color.brand))));
            }
        });*/


        toolbar.setTitle("Explore");

        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);


        ExploreFragment = new ExploreActivity.PlaceholderFragment(searchBox);
        ExploreFragment.setRetainInstance(true);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentHolder, ExploreFragment)
                .commit();


        final LinearLayout buttonHolder = (LinearLayout) findViewById(R.id.buttonHolder);


        Button exploreButton = (Button)findViewById(R.id.btn_explore);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle("Explore");
                for(int x =0;x < buttonHolder.getChildCount();x++){
                    buttonHolder.getChildAt(x).setBackground(null);
                    ((Button)buttonHolder.getChildAt(x)).setTextColor(Color.BLACK);
                }
                v.setBackground(new ColorDrawable(getResources().getColor(R.color.brand)));
                ((Button)v).setTextColor(Color.WHITE);

                drawerLayout.closeDrawers();

                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        ExploreFragment = new ExploreActivity.PlaceholderFragment(searchBox);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentHolder, ExploreFragment)
                                .commit();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();

            }
        });

        Button routeButton = (Button)findViewById(R.id.btn_route);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
