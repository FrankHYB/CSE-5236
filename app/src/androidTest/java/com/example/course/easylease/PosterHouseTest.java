package com.example.course.easylease;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/14/16.
 */
public class PosterHouseTest extends ActivityInstrumentationTestCase2<PostHouse> {

    private Solo solo;
    @SuppressWarnings("unchecked")
    public PosterHouseTest(){
        super("com.example.course.easylease.PostHouse",PostHouse.class);
    }
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

        @Override
        public PostHouse getActivity() {
            Intent intent = new Intent();
            intent.putExtra("username","heyub");
            setActivityIntent(intent);
            return super.getActivity();
        }


    public void testPostHouse(){
        solo.assertCurrentActivity("Should be the PostHouse activity", PostHouse.class);
        //Make sure the activity can scroll down

        //TODO: Open the gallery and upload a picture
        solo.enterText(0, "OV river");
        solo.enterText(1, "100 N High Street");
        solo.enterText(2, "43202");
        solo.enterText(3, "1070");
        solo.enterText(4, "2");
        solo.enterText(5,"Aparment near to campus");

        solo.clickOnButton(0);
        solo.waitForDialogToClose();
    }

}
