package com.custu.project.walktogether.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.caretaker.ReHomeCaretakerActivity;
import com.custu.project.walktogether.controller.patient.ReHomePatientActivity;
import com.custu.project.walktogether.util.UserManager;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        ProgressBar progressBar = findViewById(R.id.progress);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        int splashInterval = 2000;
        new Handler().postDelayed(() -> {
            Intent intent;
            if (UserManager.getInstance(SplashScreenActivity.this).getCaretaker() != null) {
                intent = new Intent(SplashScreenActivity.this, ReHomeCaretakerActivity.class);
                startActivity(intent);
            } else if (UserManager.getInstance(SplashScreenActivity.this).getPatient() != null) {
                intent = new Intent(SplashScreenActivity.this, ReHomePatientActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }, splashInterval);
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    @Override
    public void onBackPressed() {
    }
}

