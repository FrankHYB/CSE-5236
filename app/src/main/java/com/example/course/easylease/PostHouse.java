package com.example.course.easylease;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PostHouse extends AppCompatActivity implements View.OnClickListener {
    EditText Name, Address, Price, Rooms ,Description, Zipcode;
    TextView response, alert;
    Button bPost, bCancel,bUpload;
    ImageView image1;
    private String user;
    private String name,address,price,rooms,description,zipcode,latitude,longtitude;
    private Bitmap imageOne = null;
    private final int RESULT_CODE = 1;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_house);
        Name = (EditText) findViewById(R.id.Name);
        Address = (EditText) findViewById(R.id.Address);
        Zipcode = (EditText) findViewById(R.id.Zip);
        Price = (EditText) findViewById(R.id.Price);
        Rooms = (EditText) findViewById(R.id.Rooms);
        Description = (EditText) findViewById(R.id.Description);

        response = (TextView) findViewById(R.id.response);
        alert = (TextView) findViewById(R.id.alert);

        bPost = (Button) findViewById(R.id.bPost);
        bCancel = (Button) findViewById(R.id.bCancel);
        bUpload = (Button) findViewById(R.id.bUploadImage);

        image1 = (ImageView) findViewById(R.id.image_1);

        bPost.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bUpload.setOnClickListener(this);
        context = getApplicationContext();
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
                rooms = Rooms.getText().toString();
                description = Description.getText().toString();
                zipcode = Zipcode.getText().toString();
                House house = new House(address,zipcode,name,description,Integer.parseInt(price)
                        ,Integer.parseInt(rooms), context);
                longtitude = Double.toString(house.getLongitude());
                latitude = Double.toString(house.getLatitude());
                if(checkVaildity()){
                    response.setText("Please fill up all fields");
                }else {
                    new publishHouseInfo(imageOne).execute();
                }
                break;
            case R.id.bUploadImage:
                if(image1.getDrawable() == null) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, this.RESULT_CODE);
                }else{
                    alert.setVisibility(TextView.VISIBLE);
                }
                break;
            case R.id.bCancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_CODE && resultCode == RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            try {
                if(image1.getDrawable()==null) {
                    imageOne = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    int newH = (int) (imageOne.getHeight() * (512.0 / imageOne.getWidth()));
                    imageOne = Bitmap.createScaledBitmap(imageOne,512,newH,true);
                    image1.setImageBitmap(imageOne);
                }
            }catch (IOException e){
            }
        }

    }

    private boolean checkVaildity(){
        return TextUtils.isEmpty(this.address) || TextUtils.isEmpty(this.name) || TextUtils.isEmpty(this.address)
                || TextUtils.isEmpty(this.description) || TextUtils.isEmpty(this.zipcode) || TextUtils.isEmpty(this.user);
    }

    private class publishHouseInfo extends AsyncTask<Void, Void, Boolean> {
        boolean isNetworkSuccess = true;
        String responseFromServer="";
        String encodeImage;
        Bitmap bitmap;
        private ProgressDialog progress;
        public publishHouseInfo(Bitmap image){
            bitmap = image;
            progress = new ProgressDialog(PostHouse.this, ProgressDialog.STYLE_HORIZONTAL);
        }

        @Override
        protected void onPreExecute() {
            progress.onStart();
            progress.setMessage("Uploading ....");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/post_house.php";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            }else{
                encodeImage = "";
            }
            RequestBody body = new FormBody.Builder()
                    .add("address", address)
                    .add("name", name)
                    .add("price", price)
                    .add("rooms", rooms)
                    .add("zipcode", zipcode)
                    .add("description", description)
                    .add("longtitude", longtitude)
                    .add("latitude", latitude)
                    .add("user", user)
                    .add("image",encodeImage)
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
            progress.dismiss();
            if (isNetworkSuccess) {
                response.setText(responseFromServer);
                finish();
            }
        }
    }
}