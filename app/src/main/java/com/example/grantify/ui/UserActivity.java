package com.example.grantify.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.api.TokenManager;
import com.example.grantify.model.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewEmail, textViewCompany, textViewExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ImageButton buttonBack = findViewById(R.id.back);
        Button buttonLogout = findViewById(R.id.logoutButton);
        textViewUsername = findViewById(R.id.tvUsername);
        textViewEmail = findViewById(R.id.tvEmail);
        textViewCompany = findViewById(R.id.tvCompany);
        textViewExperience = findViewById(R.id.tvExperience);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat dan tampilkan AlertDialog konfirmasi logout
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("Logout Confirmation")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Hapus token saat pengguna logout
                                TokenManager tokenManager = new TokenManager(UserActivity.this);
                                tokenManager.deleteToken();

                                // Tampilkan pesan toast
                                Toast.makeText(UserActivity.this, "Successfully logout", Toast.LENGTH_SHORT).show();

                                // Pindah ke activity lain
                                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Tutup dialog tanpa melakukan apa-apa
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        fetchUser();
    }

    private void fetchUser() {
        RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile userProfile = response.body();
                    // Set data to TextViews
                    textViewUsername.setText(userProfile.getUsername());
                    textViewEmail.setText(userProfile.getEmail());
                    textViewCompany.setText(userProfile.getCompany() != null && !userProfile.getCompany().isEmpty() ? userProfile.getCompany() : "-");
                    textViewExperience.setText(userProfile.getExperience() != null && !userProfile.getExperience().isEmpty() ? userProfile.getExperience() : "-");
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Handle failure
                textViewUsername.setText("Failed to load");
                textViewEmail.setText("Failed to load");
                textViewCompany.setText("-");
                textViewExperience.setText("-");
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
