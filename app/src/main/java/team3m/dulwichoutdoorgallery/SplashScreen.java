package team3m.dulwichoutdoorgallery;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends ActionBarActivity {
    private static boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the splash screen only if the app hasn't been loaded before
        if(!isLoaded) {
            setContentView(R.layout.activity_splash_screen);
            isLoaded = true;

            Thread loadSplashScreen = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Intent splashIntent = new Intent(SplashScreen.this, CoreActivity.class);
                        startActivity(splashIntent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        finish();
                    }
                }
            });

            loadSplashScreen.start();
        }

        else {
            Intent homeIntent = new Intent(SplashScreen.this, CoreActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(homeIntent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
}
