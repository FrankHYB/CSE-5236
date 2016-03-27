package com.example.course.easylease;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;

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


    private void checkLogin() {
        final String username = this.Username.getText().toString();
        final String password = this.Username.getText().toString();
        String url = "http://52.34.59.35/YBAndroid/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.d("login message:: ",response);
                        if (!errorMessage.equals(response)) {
                            //Anonymous class usage
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            eMessage.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TextView.setText("That didn't work!");
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };
        VolleyController.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                checkLogin();

                break;
            case R.id.bSignUp:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
    private class LoginTask extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
    }
}
