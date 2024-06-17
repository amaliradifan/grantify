package com.example.grantify.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.api.TokenManager;
import com.example.grantify.model.Program;
import com.example.grantify.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ProgramAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgramAdapter programAdapter;
    List<Program> programList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        fetchPrograms();
        fetchUser();
    }

    private void fetchPrograms(){
        RetrofitClient.getRetrofitClient(this).getPrograms("", "").enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                if(response.isSuccessful() && response.body() != null){
                    programList.addAll(response.body());
                    programAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {

            }
        });
    }

    private void fetchUser(){
        RetrofitClient.getRetrofitClient(this).getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful() && response.body() != null){
                    UserProfile userProfile = response.body();
                    // Set username to TextView
                    TextView textViewUsername = findViewById(R.id.textViewUsername);
                    textViewUsername.setText(userProfile.getUsername());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(Program program) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("PROGRAM_ID", program.getId());
        intent.putExtra("PROGRAM_TITLE", program.getTitle());
        intent.putExtra("PROGRAM_CATEGORY", program.getCategory());
        intent.putExtra("PROGRAM_IMAGE", program.getImage());
        intent.putExtra("PROGRAM_UPLOADER", program.getUploader());
        intent.putExtra("PROGRAM_CRITERIA", program.getCriteria());
        startActivity(intent);
    }
}