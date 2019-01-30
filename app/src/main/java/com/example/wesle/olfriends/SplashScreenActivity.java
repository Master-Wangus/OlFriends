package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* Creating a thread object for splash screen */
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try{
                    super.run();
                    sleep(3000);
                } catch (Exception e) {
                } finally {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        splashThread.start();
    }
}
