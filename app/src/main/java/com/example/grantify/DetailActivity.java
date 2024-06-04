package com.example.grantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView judulText = findViewById(R.id.titleTextView);
        TextView penyelenggaraText = findViewById(R.id.organizerTextView);
        ImageView gambarDetail = findViewById(R.id.posterImage);
        TextView categoryText = findViewById(R.id.category);
        TextView categoryToolbar = findViewById(R.id.categorytoolbar);
        Button buttonRegister = findViewById(R.id.registerButton);
        ImageView buttonBack = findViewById(R.id.back_detail);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.q-tin.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        String gambar = getIntent().getStringExtra("PROGRAM_IMAGE");

        String judul = getIntent().getStringExtra("PROGRAM_TITLE");
        String penyelenggara = getIntent().getStringExtra("PROGRAM_UPLOADER");
        String category = getIntent().getStringExtra("PROGRAM_CATEGORY");
        int categoryHex = getIntent().getIntExtra("hexCategory",0);

        judulText.setText(judul);
        penyelenggaraText.setText(penyelenggara);
        Glide.with(this)
                .load(gambar)
                .placeholder(R.drawable.ic_launcher_background) // Placeholder gambar sementara
                .error(R.drawable.ic_launcher_background) // Gambar yang ditampilkan jika terjadi kesalahan
                .into(gambarDetail);
        categoryText.setText(category);
        categoryText.setBackgroundResource(categoryHex);
        buttonRegister.setBackgroundResource(categoryHex);
        categoryToolbar.setText(category);

        if (categoryHex == R.drawable.category_item_volunteer) {
            categoryText.setTextColor(Color.parseColor("#6865fb"));
            buttonRegister.setTextColor(Color.parseColor("#6865fb"));
        }
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