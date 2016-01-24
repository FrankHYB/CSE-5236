package com.example.course.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bLoginOut,bMember,bGuest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bLoginOut=(Button) findViewById(R.id.bLoginOut);
        bMember=(Button) findViewById(R.id.bMemeber);
        bGuest=(Button) findViewById(R.id.bGuest);
        bLoginOut.setOnClickListener(this);
        bMember.setOnClickListener(this);
        bLoginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLoginOut:
                //startActivity(new Intent(this,Login.class));
                break;
            case R.id.bMemeber:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.bGuest:
                //startActivity(new Intent(th));
                break;
        }
    }
}
