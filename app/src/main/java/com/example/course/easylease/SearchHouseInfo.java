package com.example.course.easylease;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SearchHouseInfo extends AppCompatActivity implements View.OnClickListener {
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        price = (EditText) findViewById(R.id.max_price);
        Button button = (Button) findViewById(R.id.search_by_price);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_by_price:
                if (TextUtils.isEmpty(price.getText())) {
                    Toast.makeText(this, "Enter max price", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int maxPrice = Integer.valueOf(price.getText().toString());
                        new SearchHouseInfoTask().execute(maxPrice);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Number format error", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private class SearchHouseInfoTask extends AsyncTask<Integer, Void, String> {
        private boolean isSearchSuccess = true;
        ProgressDialog progressDialog = new ProgressDialog(SearchHouseInfo.this,ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            int price = params[0];

            String url = "http://52.34.59.35/YBAndroid/Map_results.php";
            RequestBody requestBody = new FormBody.Builder()
                    .add("price", String.valueOf(price))
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
            progressDialog.dismiss();
            if (isSearchSuccess) {
                try {
                    int size = new JSONArray(string).length();

                    if (size != 0) {

                        Intent intent = new Intent(SearchHouseInfo.this, MapActivity.class);
                        intent.putExtra("houses",string);
                        SearchHouseInfo.this.startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SearchHouseInfo.this, "No Result", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(SearchHouseInfo.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SearchHouseInfo.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
