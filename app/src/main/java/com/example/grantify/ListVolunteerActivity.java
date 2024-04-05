package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ListVolunteerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_volunteer);

        LinearLayout schoolunteerVolun = findViewById(R.id.schoolunteerVolun);
        LinearLayout ekspedisi = findViewById(R.id.ekspedisi);
        ImageView buttonBack = findViewById(R.id.back_list_volunteer);

        schoolunteerVolun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListVolunteerActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.volunteerposter1);
                intent.putExtra("judul", "Schoolunteer 2024");
                intent.putExtra("penyelenggara", "Schoolunteer");
                intent.putExtra("category", "volunteer");
                intent.putExtra("hexCategory", R.drawable.category_item_volunteer);
                startActivity(intent);
            }
        });

        ekspedisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListVolunteerActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.volunteerposter2);
                intent.putExtra("judul", "Ekspedisi Tebar Inspirasi");
                intent.putExtra("penyelenggara", "Ekspedisi");
                intent.putExtra("category", "volunteer");
                intent.putExtra("hexCategory", R.drawable.category_item_volunteer);
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