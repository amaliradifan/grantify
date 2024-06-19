package com.example.grantify.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.api.TokenManager;
import com.example.grantify.model.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements EditProfileFragment.OnFragmentCloseListener {

    private TextView textViewUsername, textViewEmail, textViewCompany, textViewExperience;
    private ImageView profileView;
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.progressBar);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and display the logout confirmation dialog
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("Logout Confirmation")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete token on logout
                                TokenManager tokenManager = new TokenManager(UserActivity.this);
                                tokenManager.deleteToken();

                                // Show toast message
                                Toast.makeText(UserActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();

                                // Navigate to another activity
                                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Close dialog without doing anything
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
        // Show ProgressBar while fetching data
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                // Hide ProgressBar after data is loaded
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    UserProfile userProfile = response.body();
                    // Assign data to variables
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

                    // Show ProgressBar while loading the profile image
                    progressBar.setVisibility(View.VISIBLE);

                    // Load image with Glide, using placeholder and error drawable
                    Glide.with(UserActivity.this)
                            .load(image)
                            .apply(new RequestOptions().placeholder(R.drawable.oliv).error(R.drawable.oliv))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(profileView);
                } else {
                    // Handle failure case, show default values or error message
                    textViewUsername.setText("Failed to load");
                    textViewEmail.setText("Failed to load");
                    textViewCompany.setText("-");
                    textViewExperience.setText("-");
                    // Show default image or error image
                    profileView.setImageResource(R.drawable.oliv);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Hide ProgressBar on failure
                progressBar.setVisibility(View.GONE);

                // Handle failure
                textViewUsername.setText("Failed to load");
                textViewEmail.setText("Failed to load");
                textViewCompany.setText("-");
                textViewExperience.setText("-");
                // Show default image or error image
                profileView.setImageResource(R.drawable.oliv);
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
