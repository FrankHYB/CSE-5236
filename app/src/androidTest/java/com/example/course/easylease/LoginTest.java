package com.example.course.easylease;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/14/16.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    private Login loginAcitvity;
    private Solo solo;
    @SuppressWarnings("unchecked")
    public LoginTest(){
        super("com.example.course.easylease.Login",Login.class);
    }
    protected void setUp() throws Exception{
        loginAcitvity = getActivity();
        solo = new Solo(getInstrumentation(),loginAcitvity);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }




    public void testLogin(){
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        solo.enterText(0, "YUBIN");
        solo.enterText(1, "Yubin-He");
        solo.clickOnButton(0);

        solo.waitForActivity("com.example.course.easylease.MainActivity", 2000);
        solo.assertCurrentActivity("This should be the Main Activity", MainActivity.class);
        // Go back to Login class
       solo.clickOnButton(2);
       solo.assertCurrentActivity("This should be the Login class",Login.class);
        //Test Register button
        solo.clickOnButton(1);
        solo.assertCurrentActivity("This should be the Register class",Register.class);

    }


}
