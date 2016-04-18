package com.example.course.easylease;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/17/16.
 */
public class OwnerInfoTest extends ActivityInstrumentationTestCase2<OwnerInfo> {

    private Solo solo;
    @SuppressWarnings("unchecked")
    public OwnerInfoTest(){
        super("com.example.course.easylease.MainActivity",OwnerInfo.class);
    }
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public OwnerInfo getActivity() {
        Intent intent = new Intent();
        intent.putExtra("owner","heyub");
        setActivityIntent(intent);
        return super.getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
    public void testOwnerInfo(){
        solo.assertCurrentActivity("Should be OwnerInfo Activity",OwnerInfo.class);
        assertEquals("Email:", solo.getText(1).getText().toString());
        assertEquals("hyb940516@gmail.com", solo.getText(2).getText().toString());
        assertEquals("Phone Number:", solo.getText(3).getText().toString());
        assertEquals("6143900555", solo.getText(4).getText().toString());

    }

}
