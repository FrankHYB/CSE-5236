package com.example.course.easylease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import java.util.*;


public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bSignUp;
    EditText Email, UserName, PassWord, PhoneNum;

    //private UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = (EditText) findViewById(R.id.Email);
        UserName = (EditText) findViewById(R.id.Username);
        PassWord = (EditText) findViewById(R.id.Password);
        PhoneNum = (EditText) findViewById(R.id.Phone);
        bSignUp = (Button) findViewById(R.id.SignUp);
        bSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Message: ", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Message: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Message: ", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Message: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Message: ", "onDestroy");
    }

    private void LoadUserInfo() {
        String user = UserName.getText().toString();
        String password = PassWord.getText().toString();
        String email = Email.getText().toString();
        String phoneNum = PhoneNum.getText().toString();

        new UserDB(this).insert(new String[]{user, password, email, phoneNum});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUp:
                LoadUserInfo();
                finish();
                break;
        }
    }
}
