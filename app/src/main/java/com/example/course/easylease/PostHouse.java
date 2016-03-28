package com.example.course.easylease;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PostHouse extends AppCompatActivity implements View.OnClickListener {
    EditText Name, Address, Price, Description, Zipcode;
    TextView response;
    Button bPost, bCancel;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_house);
        Name = (EditText) findViewById(R.id.Name);
        Address = (EditText) findViewById(R.id.Address);
        Zipcode = (EditText) findViewById(R.id.Zip);
        Price = (EditText) findViewById(R.id.Price);
        Description = (EditText) findViewById(R.id.Description);
        response = (TextView) findViewById(R.id.response);
        bPost = (Button) findViewById(R.id.bPost);
        bCancel = (Button) findViewById(R.id.bCancel);
        bPost.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        if(getIntent().getExtras().get("username")!=null)
            user = getIntent().getExtras().get("username").toString();
        else{
            user="null";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPost:
                House house = new House(Address.getText().toString(),
                        Zipcode.getText().toString(),
                        Name.getText().toString(),
                        Description.getText().toString(),
                        Integer.parseInt(Price.getText().toString()),
                                getApplicationContext());
                new publishHouseInfo().execute(house);
                break;
            case R.id.bCancel:
                break;
        }
    }


    private class publishHouseInfo extends AsyncTask<House, Void, Boolean> {
        boolean isNetworkSuccess = true;

        @Override
        protected Boolean doInBackground(House... params) {
            String url = "http://52.34.59.35/YBAndroid/post_house.php";

            House house = params[0];
            Gson gson = new Gson();
            RequestBody body = new FormBody.Builder()
                    .add("data", gson.toJson(house))
                    .add("user", user)
                    .build();
            try {
                String response = HttpController.getInstance().run(url, body);
                return true;
                //TODO: Inhibit the same address
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (isNetworkSuccess) {
                response.setText("House successfully posted!");
            }
        }
    }
}