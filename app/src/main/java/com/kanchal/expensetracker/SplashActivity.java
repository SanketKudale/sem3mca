package com.kanchal.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    //Variables
    Animation topAnime;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        FirebaseApp.initializeApp(this);

        //Animations
        topAnime = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //Hooks
        logo=findViewById(R.id.logo);
        //setAnimation
        logo.setAnimation(topAnime);

        int SPLASH_SCREEN = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent=new Intent(SplashActivity.this,Login.class);
                SharedPreferences sp1 = getSharedPreferences("Login_Flag", Context.MODE_PRIVATE);
                String logkey = sp1.getString("loginSet","0");
                assert logkey != null;
                if (logkey.equals("1log"))
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this,Login.class));


                finish();
            }
        }, SPLASH_SCREEN);
    }
}