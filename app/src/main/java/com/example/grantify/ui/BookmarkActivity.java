package com.example.grantify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.Bookmark;
import com.example.grantify.model.Program;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity implements ProgramAdapter.OnItemClickListener {

    private static final String TAG = "BookmarkActivity";

    private List<Program> bookmarkList;
    private ProgramAdapter programAdapter;
    private TextView noBookmarksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        bookmarkList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerviewBookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        programAdapter = new ProgramAdapter(bookmarkList, this);
        recyclerView.setAdapter(programAdapter);

        noBookmarksTextView = findViewById(R.id.text_no_bookmarks);

        fetchBookmarks();

        ImageView buttonBack = findViewById(R.id.back_bookmark);
        LinearLayout search = findViewById(R.id.btn_search);
        LinearLayout bookmark = findViewById(R.id.btn_bookmark);
        LinearLayout user = findViewById(R.id.btn_user);
        LinearLayout home = findViewById(R.id.btn_home);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(BookmarkActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity lain
                Intent intent = new Intent(BookmarkActivity.this, UserActivity.class);
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
                Intent intent = new Intent(BookmarkActivity.this, HomeActivity.class);
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

    private void fetchBookmarks() {
        Log.d(TAG, "fetchBookmarks: Fetching bookmarks");
        RetrofitClient.getRetrofitClient(this).getBookmarks().enqueue(new Callback<List<Bookmark>>() {
            @Override
            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Received " + response.body().size() + " bookmarks");
                    bookmarkList.clear();
                    for (Bookmark bookmark : response.body()) {
                        Program program = bookmark.getProgram();
                        bookmarkList.add(program);
                    }
                    programAdapter.notifyDataSetChanged();
                    checkIfNoBookmarks();
                } else {
                    Log.d(TAG, "onResponse: No bookmarks found or response not successful");
                    bookmarkList.clear();
                    programAdapter.notifyDataSetChanged();
                    checkIfNoBookmarks();
                }
            }

            @Override
            public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed to fetch bookmarks", t);
            }
        });
    }

    private void checkIfNoBookmarks() {
        if (bookmarkList.isEmpty()) {
            noBookmarksTextView.setVisibility(View.VISIBLE);
        } else {
            noBookmarksTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Program program) {
        // Implement action when item is clicked
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
