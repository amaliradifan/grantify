package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ImageView buttonBack = findViewById(R.id.back);
        Button buttonLogout = findViewById(R.id.logoutButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Cek apakah ada tumpukan fragmen pada back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Jika ada, pop fragmen teratas dari back stack
            getSupportFragmentManager().popBackStack();
        } else {
            // Jika tidak, lanjutkan ke perilaku default dari tombol back
            super.onBackPressed();
        }
    }
}