package com.example.course.easylease;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.*;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button bLogin, bSignUp;
    private EditText Username, Password;
    private TextView eMessage;
    private final String errorMessage = "Username or password is invalid";
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


    private String checkLogin() throws IOException{
        final String username = this.Username.getText().toString();
        final String password = this.Password.getText().toString();
        String url = "http://52.34.59.35/YBAndroid/login.php";
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        String response = HttpController.getInstance().run(url, requestBody);
        return response;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                new LoginTask().execute();
                break;
            case R.id.bSignUp:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
    private class LoginTask extends AsyncTask<Void, Void, Boolean>{
        boolean isNetworkSuccess = true;
        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                String response = checkLogin();
                if(!errorMessage.equals(response)){
                    return true;
                }else{
                    return false;
                }
            }catch (IOException e){
                isNetworkSuccess = false;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean && isNetworkSuccess){
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }else if(!aBoolean && isNetworkSuccess){
                eMessage.setVisibility(TextView.VISIBLE);
            }
        }
    }
}
