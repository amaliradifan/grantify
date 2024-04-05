package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ListSchoolarshipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schoolarship);

        LinearLayout sejutaCita = findViewById(R.id.sejuta_cita);
        LinearLayout djarum = findViewById(R.id.djarum);
        ImageView buttonBack = findViewById(R.id.back_list_schoolarship);

        sejutaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListSchoolarshipActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.schoolarposter1);
                intent.putExtra("judul", "Beasiswa Sejuta Cita");
                intent.putExtra("penyelenggara", "IND Beasiswa");
                intent.putExtra("category", "Schoolarship");
                intent.putExtra("hexCategory", R.drawable.category_item_background);
                startActivity(intent);
            }
        });

        djarum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListSchoolarshipActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.schoolarposter3);
                intent.putExtra("judul", "Beasiswa Djarum 2024");
                intent.putExtra("penyelenggara", "PT Djarum");
                intent.putExtra("category", "Schoolarship");
                intent.putExtra("hexCategory", R.drawable.category_item_background);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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