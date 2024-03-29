package team3m.dulwichoutdoorgallery;

import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class IntroActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private static TextView titleSection, descriptionSection;
    private static ImageView screenView, pagerView;
    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!isLoaded){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);
            isLoaded = true;

            // Create the adapter that will return a fragment for each section
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }

        else{
            startActivity(new Intent(IntroActivity.this, CoreActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return IntroFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 6-1 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class IntroFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static IntroFragment newInstance(int sectionNumber) {
            IntroFragment fragment = new IntroFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public IntroFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_intro, container, false);

            int position = getArguments().getInt(ARG_SECTION_NUMBER)-1;
            titleSection = (TextView) rootView.findViewById(R.id.titleSection);
            descriptionSection = (TextView) rootView.findViewById(R.id.descriptionSection);
            screenView = (ImageView) rootView.findViewById(R.id.screenView);
            pagerView = (ImageView) rootView.findViewById(R.id.stagerView);

            switch (position) {
                case 0:
                    screenView.setImageResource(R.drawable.logo_medium);
                    titleSection.setText(R.string.title_0);
                    descriptionSection.setText(R.string.desc_0);
                    pagerView.setImageResource(R.drawable.sp1);
                    break;
                case 1:
                    screenView.setImageResource(R.drawable.map32);
                    titleSection.setText(R.string.title_1);
                    descriptionSection.setText(R.string.desc_1);
                    pagerView.setImageResource(R.drawable.sp2);
                    break;
                case 2:
                    screenView.setImageResource(R.drawable.tour);
                    titleSection.setText(R.string.title_2);
                    descriptionSection.setText(R.string.desc_2);
                    pagerView.setImageResource(R.drawable.sp3);
                    break;
                case 3:
                    screenView.setImageResource(R.drawable.mark1);
                    titleSection.setText(R.string.title_3);
                    descriptionSection.setText(R.string.desc_3);
                    pagerView.setImageResource(R.drawable.sp4);
                    break;
                case 4:
                    screenView.setImageResource(R.drawable.puzzle37);
                    titleSection.setText(R.string.title_4);
                    descriptionSection.setText(R.string.desc_4);
                    pagerView.setImageResource(R.drawable.sp5);
                    break;
                case 5:
                    screenView.setImageResource(R.drawable.logo_medium);
                    titleSection.setText(R.string.title_5);
                    descriptionSection.setText(R.string.desc_5);
                    pagerView.setImageResource(R.drawable.btn_start);

                    pagerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), CoreActivity.class));
                        }
                    });
                    break;

            }

            return rootView;
        }
    }

}
