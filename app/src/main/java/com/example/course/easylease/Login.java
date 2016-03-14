package com.example.course.easylease;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.*;

import com.facebook.FacebookSdk;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button bLogin, bSignUp;
    private EditText Username, Password;
    private TextView eMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = (Button) findViewById(R.id.bLogin);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        eMessage = (TextView) findViewById(R.id.Error_message);
        bLogin.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
    }


    private boolean checkLogin() {
        String username = this.Username.getText().toString();
        String password = this.Username.getText().toString();

        return new UserDB(this).queryPassword(username, password);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                if (checkLogin()) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    eMessage.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bSignUp:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
