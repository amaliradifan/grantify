package com.example.grantify.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private TextView textViewUsername, textViewEmail, textViewCompany, textViewExperience;
    private ImageView profileView;
    private FrameLayout progressBar;
    private String username, email, company, experience, image;

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
        progressBar = findViewById(R.id.progressBarHolder);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        fetchUser();

        ImageButton buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfileFragment();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void fetchUser() {
        RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUserProfile(response.body());
                } else {
                    Toast.makeText(UserActivity.this, "Failed to load user profile", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserProfile(UserProfile userProfile) {
        username = userProfile.getUsername();
        email = userProfile.getEmail();
        company = userProfile.getCompany() != null && !userProfile.getCompany().isEmpty() ? userProfile.getCompany() : "-";
        experience = userProfile.getExperience() != null && !userProfile.getExperience().isEmpty() ? userProfile.getExperience() : "-";
        image = userProfile.getImage();

        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewCompany.setText(company);
        textViewExperience.setText(experience);

        Glide.with(this)
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
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(UserActivity.this)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        performLogout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void performLogout() {
        TokenManager tokenManager = new TokenManager(UserActivity.this);
        tokenManager.deleteToken();
        Toast.makeText(UserActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openEditProfileFragment() {
        EditProfileFragment fragment = EditProfileFragment.newInstance(username, company, experience);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                uploadImage(selectedImageUri);
            }
        }
    }

    private void uploadImage(Uri imageUri) {
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {
            if (inputStream == null) {
                Toast.makeText(this, "Failed to open image", Toast.LENGTH_SHORT).show();
                return;
            }

            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);

            RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), imageBytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", getFileName(imageUri), requestFile);

            RetrofitClient.getRetrofitClient(this).uploadProfileImage(body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UserActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        fetchUser(); // Refresh user profile after successful upload
                    } else {
                        Toast.makeText(UserActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result != null ? result.lastIndexOf('/') : -1;
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
