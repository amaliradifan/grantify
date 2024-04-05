package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView cardViewUser = findViewById(R.id.user);
        CardView cardViewSchoolarship = findViewById(R.id.schoolarship);
        CardView cardViewVolunteer = findViewById(R.id.volunteer);
        CardView cardViewTraining = findViewById(R.id.training);
        LinearLayout sejutaCita = findViewById(R.id.sejuta_cita);
        LinearLayout schoolunteer = findViewById(R.id.schoolunteer);
        LinearLayout search = findViewById(R.id.btn_search);
        LinearLayout bookmark = findViewById(R.id.btn_bookmark);
        LinearLayout user = findViewById(R.id.btn_user);
        LinearLayout home = findViewById(R.id.btn_home);

        cardViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        cardViewSchoolarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, ListSchoolarshipActivity.class);
                startActivity(intent);
            }
        });

        cardViewVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, ListVolunteerActivity.class);
                startActivity(intent);
            }
        });

        cardViewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, ListTrainingActivity.class);
                startActivity(intent);
            }
        });

        sejutaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.schoolarposter1);
                intent.putExtra("judul", "Beasiswa Sejuta Cita");
                intent.putExtra("penyelenggara", "IND Beasiswa");
                intent.putExtra("category", "Schoolarship");
                intent.putExtra("hexCategory", R.drawable.category_item_background);
                startActivity(intent);
            }
        });

        schoolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.volunteerposter1);
                intent.putExtra("judul", "Schoolunteer 2024");
                intent.putExtra("penyelenggara", "Schoolunteer");
                intent.putExtra("category", "volunteer");
                intent.putExtra("hexCategory", R.drawable.category_item_volunteer);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
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
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}