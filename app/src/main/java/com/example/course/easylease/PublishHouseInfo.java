package com.example.course.easylease;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishHouseInfo extends Activity {
    private class PublishHouseInfoTask extends AsyncTask<House, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(House... params) {
            House house = params[0];
            Gson gson = new Gson();

            RequestBody body = new FormBody.Builder()
                    .add("data", gson.toJson(house))
                    .build();

            Request request = new Request.Builder()
                    // TODO: Fix the url
                    .url("url")
                    .post(body)
                    .build();

            try {
                Response response = new OkHttpClient().newCall(request).execute();
                return response.isSuccessful();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
