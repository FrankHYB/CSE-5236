package com.example.course.easylease;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class OwnerInfo extends AppCompatActivity implements View.OnClickListener {
    TextView tEmail, tPhone;
    Button btnEmail, btnPhoneCall;
    private static final int MY_PERMISSIONS_REQUEST_PHONE_CALL = 1;
    private String ownerName;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);
        tEmail = (TextView) findViewById(R.id.emailAddress);
        tPhone = (TextView) findViewById(R.id.phoneNum);
        btnEmail = (Button) findViewById(R.id.sendemail);
        btnPhoneCall = (Button) findViewById(R.id.phonecall);

        btnEmail.setOnClickListener(this);
        btnPhoneCall.setOnClickListener(this);
        ownerName = getIntent().getExtras().getString("owner");
        context = getApplicationContext();
        new GetOwnerInfo().execute();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendemail:
                sendEmail();
                break;
            case R.id.phonecall:
                makePhoneCall();
                break;
        }
    }

    @TargetApi(23)
    private void makePhoneCall() {
        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            Intent	phoneIntent	=
                        new	Intent(Intent.ACTION_CALL);
                String	phoneNumber	=
                        tPhone.getText().toString();	//	TIC	TAC	HELP
                String	uri	=	"tel:"	+	phoneNumber.trim();
                phoneIntent.setData(Uri.parse(uri));
                startActivity(phoneIntent);
        }else{
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.CALL_PHONE  },
                    MY_PERMISSIONS_REQUEST_PHONE_CALL);
        }
    }

    private void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto"+tEmail.getText().toString()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Interested in your house");

        emailIntent.setType("plain/text");

        startActivity(emailIntent);

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
