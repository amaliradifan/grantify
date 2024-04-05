package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ListTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_training);

        LinearLayout siapKerja = findViewById(R.id.pelatihan_kerja);
        LinearLayout trainingk3l = findViewById(R.id.trainingk3l);
        ImageView buttonBack = findViewById(R.id.back_list_training);

        siapKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListTrainingActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.trainingposter1);
                intent.putExtra("judul", "Pelatihan Siap Kerja");
                intent.putExtra("penyelenggara", "Siap Kerja");
                intent.putExtra("category", "Training");
                intent.putExtra("hexCategory", R.drawable.category_item_training);
                startActivity(intent);
            }
        });

        trainingk3l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain dan membawa data
                Intent intent = new Intent(ListTrainingActivity.this, DetailActivity.class);
                intent.putExtra("gambar", R.drawable.trainingposter2);
                intent.putExtra("judul", "FGLWP Training K3L");
                intent.putExtra("penyelenggara", "FGLWP");
                intent.putExtra("category", "Training");
                intent.putExtra("hexCategory", R.drawable.category_item_training);
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