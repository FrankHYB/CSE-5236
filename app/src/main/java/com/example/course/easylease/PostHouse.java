package com.example.course.easylease;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
    private String name,address,price,description,zipcode;
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
        Log.d("Username: ", user);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPost:
                name = Name.getText().toString();
                address = Address.getText().toString();
                price = Price.getText().toString();
                description = Description.getText().toString();
                zipcode = Zipcode.getText().toString();
                if(checkVaildity()){
                    response.setText("Please fill up all fields");
                }else {
                    new publishHouseInfo().execute();
                }
                break;
            case R.id.bCancel:
                finish();
                break;
        }
    }

    private boolean checkVaildity(){
        return TextUtils.isEmpty(this.address) || TextUtils.isEmpty(this.name) || TextUtils.isEmpty(this.address)
                || TextUtils.isEmpty(this.description) || TextUtils.isEmpty(this.zipcode) || TextUtils.isEmpty(this.user);
    }

    private class publishHouseInfo extends AsyncTask<Void, Void, Boolean> {
        boolean isNetworkSuccess = true;
        String responseFromServer="";
        @Override
        protected Boolean doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/post_house.php";

            RequestBody body = new FormBody.Builder()
                    .add("address", address)
                    .add("name", name)
                    .add("price", price)
                    .add("zipcode", zipcode)
                    .add("description", description)
                    .add("user", user)
                    .build();
            try {
                responseFromServer = HttpController.getInstance().run(url, body);
                Log.d("Message post: ", responseFromServer);
                return true;
                //TODO: Inhibit the same address
            } catch (IOException e) {
                isNetworkSuccess=false;
                return isNetworkSuccess;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (isNetworkSuccess) {
                response.setText(responseFromServer);
                finish();
            }
        }
    }
}