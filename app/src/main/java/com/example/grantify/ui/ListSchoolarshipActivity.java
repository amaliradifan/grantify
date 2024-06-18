package com.example.grantify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.Program;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSchoolarshipActivity extends AppCompatActivity implements ProgramAdapter.OnItemClickListener {
    private static final String TAG = "ListSchoolarshipActivity";
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgramAdapter programAdapter;
    List<Program> programList = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schoolarship);

        ImageButton buttonBack = findViewById(R.id.back_list_schoolarship);
        searchView = findViewById(R.id.searchViewscholar);
        recyclerView = findViewById(R.id.rc_scholarships);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        programAdapter = new ProgramAdapter(programList, this);
        recyclerView.setAdapter(programAdapter);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fetchPrograms(""); // Fetch all programs initially

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchPrograms(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchPrograms(String query) {
        Log.d(TAG, "fetchPrograms: Fetching programs with query: " + query);
        RetrofitClient.getRetrofitClient(this).getPrograms("scholarship", query).enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Received " + response.body().size() + " programs");
                    programList.clear();
                    programList.addAll(response.body());
                    programAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse: No programs found or response not successful");
                    programList.clear();
                    programAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed to fetch programs", t);
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

    @Override
    public void onItemClick(Program program) {
        Intent intent = new Intent(ListSchoolarshipActivity.this, DetailActivity.class);
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
