package com.example.course.easylease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class HouseDetailActivity extends AppCompatActivity {
    TextView Name, Address, Price, Rooms, Owner, Description;
    ImageView Image;
    private String houseInfo;
    private Bitmap image;
    private House house;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_house_detail);

            houseInfo = getIntent().getExtras().getString("house_info");
            Name = (TextView) findViewById(R.id.name);
            Address = (TextView) findViewById(R.id.address);
            Price = (TextView) findViewById(R.id.price);
            Rooms = (TextView) findViewById(R.id.rooms);
            Owner = (TextView) findViewById(R.id.owner);
            Description = (TextView) findViewById(R.id.description);

            Image = (ImageView) findViewById(R.id.pic);
            parseHouseInfo(houseInfo);

            Owner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HouseDetailActivity.this,OwnerInfo.class);
                    i.putExtra("owner",Owner.getText().toString());
                    startActivity(i);
                }
            });
            // if (textView != null) {
           //     textView.setText(houseInfo);
            //}
        }
    private void parseHouseInfo(String houseInfo){
            Gson gson = new Gson();
            //this.house = gson.fromJson(houseInfo,House.class);
        /*try {
            JSONObject jsonObject = new JSONObject(houseInfo);

            String name = jsonObject.getString("name");
            String zipcode = jsonObject.getString("zipcode");
            int rooms = Integer.parseInt(jsonObject.getString("rooms"));
            int price = Integer.parseInt(jsonObject.getString("price"));
            String address = jsonObject.getString("address");
            String description = jsonObject.getString("description");
            String owner = jsonObject.getString("owner");
            this.house = new House(address, zipcode, name, description, price, rooms, owner, this);
        }catch(JSONException e){
            e.printStackTrace();
        }*/
            house = gson.fromJson(houseInfo,House.class);
            new GetImageFromServer(house.getAddress()).execute();


    }
    private void setOutput(House house){
        Name.setText(house.getName());
        Address.setText(house.getAddress());
        Price.setText(house.getPrice());
        Rooms.setText(house.getRooms());
        Owner.setText(house.getOwner());
        Description.setText(house.getDescription());
        if(this.image != null){
            Image.setImageBitmap(this.image);
        }
    }
    private class GetImageFromServer extends AsyncTask<Void, Void, String>{
        private boolean isSearchSuccess = true;
        private String address;
        private ProgressDialog progressDialog;
        public GetImageFromServer(String Address){
            progressDialog = new ProgressDialog(HouseDetailActivity.this, ProgressDialog.STYLE_SPINNER);
            address = Address;
        }
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading Pictures..");
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/House_Details.php";
            RequestBody requestBody = new FormBody.Builder()
                    .add("address",this.address)
                    .build();

            try {
                return HttpController.getInstance().run(url, requestBody);
            } catch (IOException e) {
                isSearchSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s!=null&&isSearchSuccess && !s.equals("DB Error")) {
                if (!s.equals("")) {
                    InputStream stream = new ByteArrayInputStream(Base64.decode(s.getBytes(), Base64.DEFAULT));
                    HouseDetailActivity.this.image = BitmapFactory.decodeStream(stream, null, null);
                } else {
                    HouseDetailActivity.this.image = null;
                }
            }else{
                Toast.makeText(HouseDetailActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
            setOutput(HouseDetailActivity.this.house);
        }
    }

}
