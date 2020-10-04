package com.devsteve.juego_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.devsteve.juego_apps.signin_up.Registro_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(3000);
    }

    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            Intent regIntent = new Intent(SplashActivity.this, Registro_Activity.class);
            startActivity(regIntent);
            finish();
        }else{
            Intent logIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(logIntent);
            finish();
        }
    }
}