package com.example.course.easylease;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bSignUp;
    EditText Email, UserName, PassWord, PhoneNum;
    TextView Message;

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

    private String LoadUserInfo() throws IOException {
        final String user = UserName.getText().toString();
        final String password = PassWord.getText().toString();
        final String email = Email.getText().toString();
        final String phoneNum = PhoneNum.getText().toString();

        String url = "http://52.34.59.35/YBAndroid/register.php";
        RequestBody requestBody = new FormBody.Builder().add("username", user)
                .add("password", password)
                .add("email", email)
                .add("phoneNum", phoneNum).build();

        return HttpController.getInstance().run(url, requestBody);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUp:
                new LoginTask().execute();
                break;
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private boolean isNetworkSuccess = true;
        String response = "";
        ProgressDialog progressDialog = new ProgressDialog(Register.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                response = LoadUserInfo();
                return response.length() == 0;
            } catch (IOException e) {
                Log.d("Message: ", "" + e);
                isNetworkSuccess = false;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if (aBoolean && isNetworkSuccess) {
                finish();
            } else if (!aBoolean) {
                Message.setText(response);
            }
        }
    }
}