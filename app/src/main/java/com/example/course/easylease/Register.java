package com.example.course.easylease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.*;


public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bSignUp;
    EditText Email,UserName, PassWord, PhoneNum;
    TextView Message;
    private String result="";
    //private UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = (EditText) findViewById(R.id.Email);
        UserName = (EditText) findViewById(R.id.Username);
        PassWord = (EditText) findViewById(R.id.Password);
        PhoneNum = (EditText) findViewById(R.id.Phone);
        Message = (TextView) findViewById(R.id.message);
        bSignUp = (Button) findViewById(R.id.SignUp);
        bSignUp.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("Message: ", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
       // Log.d("Message: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("Message: ", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("Message: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("Message: ", "onDestroy");
    }

    private void LoadUserInfo() {
        final String user = UserName.getText().toString();
        final String password = PassWord.getText().toString();
        final String email = Email.getText().toString();
        final String phoneNum = PhoneNum.getText().toString();

        //RequestQueue queue=VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        //TODO: input the url
        String url="http://52.34.59.35/YBAndroid/register.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        result=response;
                        Message.setText(response.substring(0,50));
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TextView.setText("That didn't work!");
                }


        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user);
                params.put("email", email);
                params.put("password", password);
                params.put("phoneNum",phoneNum);
                return params;
            }

        };
        VolleyController.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
        //new UserDB(this).insert(new String[]{user, password, email, phoneNum});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUp:
                LoadUserInfo();
                if(result.length()==0)
                    finish();
                break;
        }
    }
}
