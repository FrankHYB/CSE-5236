package com.example.course.easylease;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/16/16.
 */
public class HouseDetailTest extends ActivityInstrumentationTestCase2<HouseDetailActivity> {

    private Solo solo;
    @SuppressWarnings("unchecked")
    public HouseDetailTest(){
        super("com.example.course.easylease.MainActivity",HouseDetailActivity.class);
    }
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public HouseDetailActivity getActivity() {
        House house = new House("523 Harley Dr", "43202", "UV", "University Village",970, 2,"heyu");
        Intent intent = new Intent();
        intent.putExtra("house_info",house.toJsonString());
        setActivityIntent(intent);
        return super.getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
    public void testHouse(){
        solo.assertCurrentActivity("Should be HouseDetail Activity", HouseDetailActivity.class);
        //solo.waitForDialogToClose();
        String name = solo.getText(1).getText().toString();
        assertEquals("UV",name);
        String address = solo.getText(3).getText().toString();
        assertEquals("523 Harley Dr",address);
        String price = solo.getText(5).getText().toString();
        assertEquals("970",price);
        String rooms = solo.getText(7).getText().toString();
        assertEquals("2",rooms);
        String des = solo.getText(9).getText().toString();
        assertEquals("University Village",des);
        String owner = solo.getText(11).getText().toString();
        assertEquals("heyu",owner);

        solo.clickOnText("heyu");  // Problem
        solo.waitForDialogToClose();
        solo.assertCurrentActivity("Should be Owner Info",OwnerInfo.class);
    }
}
