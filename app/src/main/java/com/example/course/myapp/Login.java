package com.example.course.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button bLogin,bSignUp;
    EditText Username,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin=(Button) findViewById(R.id.bLogin);
        bSignUp=(Button) findViewById(R.id.bSignUp);
        Username=(EditText) findViewById(R.id.Username);
        Password=(EditText) findViewById(R.id.Passward);

        bLogin.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                break;
            case R.id.bSignUp:
                startActivity(new Intent(this,Register.class));
                break;
        }
    }
}
