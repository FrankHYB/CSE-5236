package com.example.course.easylease;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestSuite;

/**
 * Created by yubin on 4/14/16.
 */
public class AllTest extends ActivityInstrumentationTestCase2<Activity> {
    public AllTest(Class<Activity> activityClass) {
        super(activityClass);
    }
    public static TestSuite suite(){
        TestSuite t = new TestSuite();
        t.addTestSuite(RegisterTest.class);
        t.addTestSuite(LoginTest.class);
        t.addTestSuite(MainActivityTest.class);
        t.addTestSuite(PosterHouseTest.class);
        t.addTestSuite(HouseDetailTest.class);
        t.addTestSuite(OwnerInfoTest.class);
        return t;
    }

}
