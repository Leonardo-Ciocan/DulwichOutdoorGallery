package team3m.dulwichoutdoorgallery;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.melnykov.fab.FloatingActionButton;


public class InfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


        ImageView i1;
        ImageView i2;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.info_fragment, container, false);


            final FrameLayout imgHolder = (FrameLayout) rootView.findViewById(R.id.imgHolder);

            final ImageView header = (ImageView) rootView.findViewById(R.id.header);

            final ImageView header2 = (ImageView) rootView.findViewById(R.id.header2);

            final ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

            final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

                @Override
                public void onScrollChanged() {
                    int scrollY = scrollView.getScrollY(); //for verticalScrollView
                    float percentage = scrollY / scrollView.getMaxScrollAmount();

                    FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) header.getLayoutParams();
                    params2.topMargin = (int)(-scrollY / 2.5f);
                    //header.setScaleX(1 + scrollY / 7.5f);
                    //header.setScaleY(1 + scrollY / 7.5f);
                    //header.setAlpha(1 - scrollY/200.0f);
                    //Resources resources = getActivity().getResources();
                    //DisplayMetrics metrics = resources.getDisplayMetrics();
                    //float px = 400 * (metrics.densityDpi / 160f);
                    //params2.height =  (int)(px - scrollY);
                    header.setLayoutParams(params2);



                    /**final Animation a = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {

                            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                            params2.rightMargin = (int)(-200 * interpolatedTime);
                            fab.setLayoutParams(params2);
                        }

                        @Override
                        public boolean willChangeBounds() {
                            return true;
                        }
                    };

                    a.setInterpolator(new AccelerateDecelerateInterpolator());
                    a.setDuration(500);
                    header.startAnimation(a);**/


                    FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) fab.getLayoutParams();
                    params3.rightMargin = (int)(-scrollY/1.875f);
                    fab.setLayoutParams(params3);
                    fab.setRotation(scrollY);

                }
            });




            i1 = header;
            i2 = header2;

            imgHolder.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


            fab.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Animation a = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            if(interpolatedTime < 0.5f){


                                i1.setAlpha(255);
                                i2.setAlpha(0);
                                //i1.setAlpha(0);
                                i1.setRotationY(90 * (2f * interpolatedTime));
                            }
                            else{

                                i1.setAlpha(0);
                                i2.setAlpha(255);
                                i2.setRotationY(180 + 90 * ( interpolatedTime * 2f));
                            }

                            if(interpolatedTime >= 1f){
                                final ImageView temp = i1;
                                i1 = i2;
                                i2 = temp;
                                //i2.setRotationY(90);
                                this.cancel();
                            }
                        }

                        @Override
                        public boolean willChangeBounds() {
                            return true;
                        }
                    };

                    a.setInterpolator(new AccelerateDecelerateInterpolator());
                    a.setDuration(2000);
                    header.startAnimation(a);
                }
            });
            return rootView;
        }
    }
}
