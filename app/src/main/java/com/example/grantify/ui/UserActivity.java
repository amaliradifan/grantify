    package com.example.grantify.ui;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.example.grantify.R;
    import com.example.grantify.api.RetrofitClient;
    import com.example.grantify.api.TokenManager;
    import com.example.grantify.model.UserProfile;
    import com.google.android.material.imageview.ShapeableImageView;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class UserActivity extends AppCompatActivity implements EditProfileFragment.OnFragmentCloseListener {

        private TextView textViewUsername, textViewEmail, textViewCompany, textViewExperience;
        private ImageView profileView;
        String username, email, company, experience, image;

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
            profileView = findViewById(R.id.profileImage);


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

            ImageButton buttonEdit = findViewById(R.id.buttonEdit);

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME", username);
                    bundle.putString("COMPANY", company);
                    bundle.putString("EXPERIENCE", experience);

                    EditProfileFragment fragment = EditProfileFragment.newInstance(username, company, experience);
                    fragment.setOnFragmentCloseListener(UserActivity.this);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }

        private void fetchUser() {
            RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserProfile userProfile = response.body();
                        //input to variabel
                        username = userProfile.getUsername();
                        email = userProfile.getEmail();
                        company = userProfile.getCompany() != null && !userProfile.getCompany().isEmpty() ? userProfile.getCompany() : "-";
                        experience = userProfile.getExperience() != null && !userProfile.getExperience().isEmpty() ? userProfile.getExperience() : "-";
                        image = userProfile.getImage();
                        // Set data to TextViews
                        textViewUsername.setText(username);
                        textViewEmail.setText(email);
                        textViewCompany.setText(company);
                        textViewExperience.setText(experience);
                        Glide.with(UserActivity.this)
                                .load(image)
                                .placeholder(R.drawable.oliv)
                                .error(R.drawable.oliv)
                                .into(profileView);
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
        public void onFragmentClose() {
            fetchUser();
        }

        @Override
        public void onBackPressed() {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
