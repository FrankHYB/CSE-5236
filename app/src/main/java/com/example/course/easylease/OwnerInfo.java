package com.example.course.easylease;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class OwnerInfo extends AppCompatActivity {
    TextView tEmail, tPhone;
    private String ownerName;
    private String email, phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);
        tEmail = (TextView) findViewById(R.id.emailAddress);
        tPhone = (TextView) findViewById(R.id.phoneNum);
        ownerName = getIntent().getExtras().getString("owner");
        new GetOwnerInfo().execute();
    }




    private class GetOwnerInfo extends AsyncTask<Void,Void,Boolean>{
        private boolean isSearchSuccess = true;
        private ProgressDialog progressDialog;
        private String response;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(OwnerInfo.this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/OwnerInfo.php";
            RequestBody requestBody = new FormBody.Builder()
                    .add("owner",OwnerInfo.this.ownerName)
                    .build();

            try {
                response = HttpController.getInstance().run(url, requestBody);
            } catch (IOException e) {
                isSearchSuccess = false;
            }
            return isSearchSuccess;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(isSearchSuccess && response != null){
                try {
                    JSONArray array = new JSONArray(response);
                    tEmail.setText(array.get(0).toString());
                    tPhone.setText(array.get(1).toString());
                }catch(JSONException e){
                    Toast.makeText(OwnerInfo.this,"Json exception",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(OwnerInfo.this,"Network error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
