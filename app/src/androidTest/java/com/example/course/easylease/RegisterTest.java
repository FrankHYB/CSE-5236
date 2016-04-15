package com.example.course.easylease;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by yubin on 4/14/16.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<Register> {

    private Solo solo;
    private String username = "YUBIN",
            password = "Yubin-He",
            email = "hyb940516@gmail.com",
            phoneNum = "6146197998";
    @SuppressWarnings("unchecked")
    public RegisterTest(){
        super("com.example.course.easylease.Login",Register.class);
    }
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRegister (){
        //solo.assertCurrentActivity("Wrong Activity", Login.class);
        //solo.clickOnButton(1);
        solo.assertCurrentActivity("This should be the Main Activity", Register.class);

        solo.enterText(0, username);
        solo.enterText(1, password);
        solo.enterText(2, email);
        solo.enterText(3, phoneNum);
        solo.clickOnButton(0);

        solo.waitForDialogToClose();
    }
}
