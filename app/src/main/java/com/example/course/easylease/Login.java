package com.example.course.easylease;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button bLogin, bSignUp;
    private EditText Username, Password;
    private TextView eMessage;
    private final String errorMessage = "Username or password is invalid";
    private Context context;
    String fbUsername, fbEmail,fbId;
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
        context = getApplicationContext();

    }


    private boolean checkValidity(String username, String password) {
        return !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
    }

    private String checkLogin() throws IOException {
        final String username = this.Username.getText().toString();
        final String password = this.Password.getText().toString();
        if (!checkValidity(username, password)) {
            return errorMessage;
        }
        String url = "http://52.34.59.35/YBAndroid/login.php";
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        return HttpController.getInstance().run(url, requestBody);

    }

    private boolean hasNetworkConnection(){
        ConnectivityManager connectivityManager	=
                (ConnectivityManager)	getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo	=
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiAvailable	=	networkInfo.isAvailable();
        boolean isWifiConnected	=	networkInfo.isConnected();
        networkInfo	=
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileAvailable	=	networkInfo.isAvailable();
        boolean isMobileConnnected	=	networkInfo.isConnected();

        return (isWifiAvailable && isWifiConnected) || (isMobileAvailable && isMobileConnnected);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                if(hasNetworkConnection()) {
                    new LoginTask().execute();
                }else{
                    Toast.makeText(context,"No network connection",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bSignUp:
                startActivity(new Intent(this, Register.class));
                break;

        }
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        boolean isNetworkSuccess = true;
        private ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(Login.this, ProgressDialog.STYLE_SPINNER);
            progress.show();
            progress.setMessage("Loading ...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String response = checkLogin();
                return !errorMessage.equals(response);
            } catch (IOException e) {
                isNetworkSuccess = false;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progress.dismiss();
            if (aBoolean && isNetworkSuccess) {
                Intent main = new Intent(Login.this, MainActivity.class);
                main.putExtra("username", Username.getText().toString());
                startActivity(main);
                finish();
            } else if (!aBoolean && isNetworkSuccess) {
                eMessage.setVisibility(TextView.VISIBLE);
            }
        }
    }
}
