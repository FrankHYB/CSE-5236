package com.example.course.easylease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView spl;
    Animation anmia,anmia2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        spl=(ImageView) findViewById(R.id.sp);
        anmia= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        anmia2=AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        spl.startAnimation(anmia);
        anmia.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                spl.startAnimation(anmia2);
                finish();
                startActivity(new Intent(getBaseContext(),Login.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("Message: ", "onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Message: ","onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("Message: ","onPause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("Message: ","onStop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("Message: ", "onDestroy");
    }

}
