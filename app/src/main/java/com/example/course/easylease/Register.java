package com.example.course.easylease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bSignUp;
    EditText Email,UserName,PassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email=(EditText) findViewById(R.id.Email);
        UserName=(EditText) findViewById(R.id.Username);
        PassWord=(EditText) findViewById(R.id.Passward);
        bSignUp=(Button) findViewById(R.id.SignUp);
        bSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                break;
        }
    }
}
