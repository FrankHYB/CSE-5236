package com.example.course.easylease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

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
        user = getIntent().getExtras().get("username").toString();
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
                //startActivity(new Intent(this,Login.class));
                break;
            case R.id.bFinder:
                Intent houseClass = new Intent(this, MapActivity.class);
                houseClass.putExtra("username",user);
                startActivity(houseClass);
                finish();
                break;
            case R.id.bPoster:
                //startActivity(new Intent(th));
                break;
        }
    }
}
