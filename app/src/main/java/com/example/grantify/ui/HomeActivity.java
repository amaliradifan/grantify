package com.example.grantify.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.Program;
import com.example.grantify.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ProgramAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgramAdapter programAdapter;
    private List<Program> programList = new ArrayList<>();
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        CardView cardViewUser = findViewById(R.id.user);
        CardView cardViewSchoolarship = findViewById(R.id.schoolarship);
        CardView cardViewVolunteer = findViewById(R.id.volunteer);
        CardView cardViewTraining = findViewById(R.id.training);
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

        recyclerView = findViewById(R.id.rc_home);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        programAdapter = new ProgramAdapter(programList, this);
        recyclerView.setAdapter(programAdapter);

        fetchData();
    }

    private void fetchData() {
        // Show the progress bar and hide the main content
        progressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);

        // Fetch user and program data
        fetchPrograms();
        fetchUser();
    }

    private void fetchPrograms() {
        RetrofitClient.getRetrofitClient(this).getPrograms("", "").enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    programList.addAll(response.body());
                    programAdapter.notifyDataSetChanged();
                }
                checkFetchCompletion();
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                checkFetchCompletion();
            }
        });
    }

    private void fetchUser() {
        RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile userProfile = response.body();
                    // Set username to TextView
                    TextView textViewUsername = findViewById(R.id.textViewUsername);
                    ImageView imageViewProfile = findViewById(R.id.imageProfile);
                    textViewUsername.setText(userProfile.getUsername());
                    Glide.with(HomeActivity.this)
                            .load(userProfile.getImage())
                            .placeholder(R.drawable.oliv)
                            .error(R.drawable.oliv)
                            .into(imageViewProfile);
                }
                checkFetchCompletion();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                checkFetchCompletion();
            }
        });
    }

    private void checkFetchCompletion() {
        if (programList.size() > 0) {
            progressBar.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Program program) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("PROGRAM_ID", program.getId());
        intent.putExtra("PROGRAM_TITLE", program.getTitle());
        intent.putExtra("PROGRAM_OPEN_DATE", program.getOpenDate().getTime());
        intent.putExtra("PROGRAM_CLOSE_DATE", program.getCloseDate().getTime());
        intent.putExtra("PROGRAM_CATEGORY", program.getCategory());
        intent.putExtra("PROGRAM_CRITERIA", program.getCriteria());
        intent.putExtra("PROGRAM_IMAGE", program.getImage());
        intent.putExtra("PROGRAM_LINK", program.getLink());
        intent.putExtra("PROGRAM_PROFIL", program.getProfil());
        intent.putExtra("PROGRAM_ABOUT", program.getAbout());
        intent.putExtra("PROGRAM_UPLOADER", program.getUploader());
        intent.putExtra("PROGRAM_BENEFITS", program.getBenefits());
        intent.putExtra("PROGRAM_ELIGIBILITY", program.getEligibility());
        startActivity(intent);
    }

}