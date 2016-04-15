package com.example.course.easylease;

/**
 * Created by yubin on 4/14/16.
 */
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/14/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    @SuppressWarnings("unchecked")
    public MainActivityTest(){
        super("com.example.course.easylease.MainActivity",MainActivity.class);
    }
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public MainActivity getActivity() {
        Intent intent = new Intent();
        intent.putExtra("username","heyub");
        setActivityIntent(intent);
        return super.getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

     public void testMainActivity(){
        solo.assertCurrentActivity("Should be MainActivity", MainActivity.class);
        solo.clickOnButton(0);
        solo.assertCurrentActivity("Should be the map class", MapActivity.class);
        solo.goBackToActivity("MainActivity");

        // solo.waitForActivity(MainActivity.class, 5000);

         solo.assertCurrentActivity("Should be MainActivity", MainActivity.class);

        solo.clickOnButton(1);
        solo.assertCurrentActivity("Should be the upload class",PostHouse.class);

        solo.goBack();
    }
}

