package com.example.course.easylease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class HouseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        String houseInfo = getIntent().getExtras().getString("house_info");

        TextView textView = (TextView) findViewById(R.id.house_info_text);
        if (textView != null) {
            textView.setText(houseInfo);
        }
    }
}
