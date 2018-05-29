package com.pllug.course.ivankiv.passmanager.ui.splashscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.authorization.AuthorizationActivity;


public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView boldText, simpleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initAnimation();
        goToNextActivity();

    }

    private void initView() {
        logo = findViewById(R.id.splash_logo);
        boldText = findViewById(R.id.splash_bold_text);
        simpleText = findViewById(R.id.splash_simple_text);
    }

    private void initAnimation() {
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        logo.startAnimation(myAnim);
        boldText.startAnimation(myAnim);
        simpleText.startAnimation(myAnim);
    }

    private void goToNextActivity() {
        final Intent intent = new Intent(this, AuthorizationActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
