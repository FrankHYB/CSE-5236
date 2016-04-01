package com.example.course.easylease;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bLoginOut, bFinder, bPoster;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bLoginOut = (Button) findViewById(R.id.bLoginOut);
        bFinder = (Button) findViewById(R.id.bFinder);
        bPoster = (Button) findViewById(R.id.bPoster);
        bPoster.setOnClickListener(this);
        bFinder.setOnClickListener(this);
        bLoginOut.setOnClickListener(this);
        if (getIntent().getExtras().get("username") != null)
            user = getIntent().getExtras().get("username").toString();
        else
            user = "null";
        Log.d("Username: ", user);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLoginOut:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.bFinder:

                new GetAllHouseInfoTask().execute();
                break;
            case R.id.bPoster:
                Intent postClass = new Intent(this, PostHouse.class);
                postClass.putExtra("username", user);
                startActivity(postClass);
                break;
        }
    }

    private class GetAllHouseInfoTask extends AsyncTask<Void, Void, String> {
        private boolean isSearchSuccess = true;

        @Override
        protected String doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/query_houses.php";
            RequestBody requestBody = new FormBody.Builder()
                    .build();

            try {
                return HttpController.getInstance().run(url, requestBody);
            } catch (IOException e) {
                isSearchSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            if (isSearchSuccess) {
                try {
                    int size = new JSONArray(string).length();

                    if (size != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", string);

                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        intent.putExtras(bundle);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
