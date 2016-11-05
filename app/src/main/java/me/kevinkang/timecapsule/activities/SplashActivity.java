package me.kevinkang.timecapsule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.kevinkang.timecapsule.R;

public class SplashActivity extends AppCompatActivity  {
    private static String TAG = "SplashActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startMainActivity();
        } else {
            startLoginActivity();
        }
    }

    /**
     * If the user is logged in, start the main activity
     */
    private void startMainActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    /**
     * If the user is not logged in, start the login activity
     */
    private void startLoginActivity(){
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        this.finish();
    }
}
