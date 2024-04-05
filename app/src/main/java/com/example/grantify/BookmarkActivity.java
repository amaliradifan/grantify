package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BookmarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ImageView buttonBack = findViewById(R.id.back_bookmark);
        LinearLayout sejutaCita = findViewById(R.id.sejuta_cita);
        LinearLayout siapKerja = findViewById(R.id.siap_kerja);
        LinearLayout search = findViewById(R.id.btn_search);
        LinearLayout bookmark = findViewById(R.id.btn_bookmark);
        LinearLayout user = findViewById(R.id.btn_user);
        LinearLayout home = findViewById(R.id.btn_home);


        sejutaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(BookmarkActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.schoolarposter1);
                intent.putExtra("judul", "Beasiswa Sejuta Cita");
                intent.putExtra("penyelenggara", "IND Beasiswa");
                intent.putExtra("category", "Schoolarship");
                intent.putExtra("hexCategory", R.drawable.category_item_background);
                startActivity(intent);
            }
        });

        siapKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(BookmarkActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.trainingposter1);
                intent.putExtra("judul", "Pelatihan Siap Kerja");
                intent.putExtra("penyelenggara", "Siap Kerja");
                intent.putExtra("category", "Training");
                intent.putExtra("hexCategory", R.drawable.category_item_training);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(BookmarkActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(BookmarkActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan instance dari FragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager();
                SearchFragment fragment = new SearchFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_containerHome, fragment);
                transaction.commit();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(BookmarkActivity.this, HomeActivity.class);
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