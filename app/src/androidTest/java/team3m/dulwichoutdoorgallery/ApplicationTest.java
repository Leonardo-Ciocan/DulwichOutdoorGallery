package team3m.dulwichoutdoorgallery;

import android.app.Application;
import android.location.Location;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        assertEquals(checkClosestTest() == 0, true);
        assertEquals(checkWithinRange() ,true);
    }

    public int checkClosestTest(){
        Location location = new Location("");
        location.setLatitude(51.47421967);
        location.setLongitude(-0.0626564);

        int closest = RouteFragment.getClosestWithinRange(location,-1);
        return closest;
    }

    public boolean checkWithinRange(){
        Location user = new Location("");
        user.setLatitude(51.47140954);
        user.setLongitude(-0.06433547);

        return RouteFragment.isWithinRange(0 , user , 20f);
    }



}