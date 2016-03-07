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
    EditText Email,UserName,PassWord,PhoneNum;
    //private UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email=(EditText) findViewById(R.id.Email);
        UserName=(EditText) findViewById(R.id.Username);
        PassWord=(EditText) findViewById(R.id.Password);
        PhoneNum=(EditText) findViewById(R.id.Phone);
        bSignUp=(Button) findViewById(R.id.SignUp);
        bSignUp.setOnClickListener(this);
    }
    private void LoadUserInfo(){
        Mongodb db = new Mongodb();
        List<String> message=db.createNewUser(UserName.getText().toString(),PassWord.getText().toString(),Email.getText().toString(),
                PhoneNum.getText().toString());
        if(message.size()==0){
            Log.d("DB","Failed");
        }
        db.closeConn();
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
