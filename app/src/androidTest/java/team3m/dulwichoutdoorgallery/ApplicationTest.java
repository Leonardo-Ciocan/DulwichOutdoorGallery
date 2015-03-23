package team3m.dulwichoutdoorgallery;

import android.app.Application;
import android.location.Location;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        assertEquals(checkClosestTest() == 0, true);
        assertEquals(checkWithinRange() ,true);
        assertEquals(checkArtFiltering().equals("Hello World") ,true);
        assertEquals(checkPopulateArtists() == 33 , true);
    }

    /**
     * Checks whether it can detect the closest art
     * @return 0 The index of the closest art
     */
    public int checkClosestTest(){
        Location location = new Location("");
        location.setLatitude(51.47421967);
        location.setLongitude(-0.0626564);

        int closest = RouteFragment.getClosestWithinRange(location,-1);
        return closest;
    }

    /**
     * Checks whether a point is close to another
     * @return Whether the test point is within the range of the first art
     */
    public boolean checkWithinRange(){
        Location user = new Location("");
        user.setLatitude(51.47140954);
        user.setLongitude(-0.06433547);
        return RouteFragment.isWithinRange(0 , user , 20f);
    }

    /**
     * Whether the art object is correctly serialized to string for filtering
     * @return Whether the string is correctly composed
     */
    public String checkArtFiltering(){
        Art a = new Art("World" , "" , "Hello" , null,0,0,"","");
        return a.toString();
    }

    /**
     * The artist information could leak to a memory leak if an if statement was removed so this
     * check if it keeps the same amount of data
     * @return How many artists there are
     */
    public int checkPopulateArtists(){
        ArtistInformation i1 = new ArtistInformation();
        ArtistInformation i2 = new ArtistInformation();
        ArtistInformation i3 = new ArtistInformation();

        return ArtistInformation.Authors.size();
    }



}