package com.example.grantify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.grantify.R;
import com.example.grantify.api.TokenManager;

public class MainActivity extends AppCompatActivity {
    private Handler splashScreenHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 3000);
    }

    private void checkLoginStatus() {
        TokenManager tokenManager = new TokenManager(this);
        if (tokenManager.getToken() != null) {
            // User sudah login, pindah ke HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Tampilkan OnboardingFragment jika belum login
            showOnboardingFragment();
        }
    }

    private void showOnboardingFragment() {
        // Menampilkan fragment onboarding
        FragmentOnboarding1 fragment = new FragmentOnboarding1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out); // Menambahkan animasi fade in dan fade out
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // Agar fragment bisa dikembalikan dengan tombol back
        fragmentTransaction.commit();
    }
}
