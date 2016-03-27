package com.example.course.easylease;

import android.app.Activity;
import android.os.AsyncTask;

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

            RequestBody body = new FormBody.Builder()
                    .add("latitude", String.valueOf(house.getLatitude()))
                    .add("longitude", String.valueOf(house.getLongitude()))
                    .add("address", house.getAddress())
                    .add("name", house.getName())
                    .add("description", house.getDescription())
                    .add("price", String.valueOf(house.getPrice()))
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
