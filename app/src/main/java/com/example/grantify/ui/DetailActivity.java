package com.example.grantify.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.Bookmark;
import com.example.grantify.model.BookmarkRequest;
import com.example.grantify.model.BookmarkResponse;
import com.example.grantify.model.Program;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private static final int BOOKMARK_DELAY = 2000; // Waktu jeda dalam milidetik (2 detik)
    private boolean isBookmarked = false;
    private String programId;
    private String bookmarkId; // Menyimpan ID bookmark
    private ImageView bookmarkIcon;
    private List<Program> bookmarkList = new ArrayList<>();
    private boolean isBookmarking = false; // Untuk mengatur waktu jeda

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
        bookmarkIcon = findViewById(R.id.bookmarkIcon);

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
        int categoryHex = getIntent().getIntExtra("hexCategory", 0);
        programId = getIntent().getStringExtra("PROGRAM_ID");

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

        // Fetch bookmarks to check if the program is already bookmarked
        fetchBookmarks();

        // Set up the bookmark icon click listener
        bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBookmarking) {
                    Toast.makeText(DetailActivity.this, "Please wait before performing another action", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isBookmarked) {
                    createBookmark();
                } else {
                    deleteBookmark();
                }
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
                        if (program.getId().equals(programId)) {
                            bookmarkId = bookmark.getId(); // Simpan ID bookmark
                            isBookmarked = true;
                        }
                        bookmarkList.add(program);
                    }
                    updateBookmarkIcon();
                } else {
                    Log.d(TAG, "onResponse: No bookmarks found or response not successful");
                    bookmarkList.clear();
                }
            }

            @Override
            public void onFailure(Call<List<Bookmark>> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed to fetch bookmarks", t);
            }
        });
    }

    private void updateBookmarkIcon() {
        if (isBookmarked) {
            bookmarkIcon.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkIcon.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    private void createBookmark() {
        isBookmarking = true;
        BookmarkRequest bookmarkRequest = new BookmarkRequest(programId);
        RetrofitClient.getRetrofitClient(this).createBookmark(bookmarkRequest).enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                isBookmarking = false;
                if (response.isSuccessful()) {
                    isBookmarked = true;
                    updateBookmarkIcon();
                    bookmarkId = response.body().getId();
                    Toast.makeText(DetailActivity.this, "Program bookmarked successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Failed to bookmark program", Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBookmarking = false;
                    }
                }, BOOKMARK_DELAY);
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                isBookmarking = false;
                Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBookmarking = false;
                    }
                }, BOOKMARK_DELAY);
            }
        });
    }

    private void deleteBookmark() {
        isBookmarking = true;
        RetrofitClient.getRetrofitClient(this).deleteBookmark(bookmarkId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isBookmarking = false;
                if (response.isSuccessful()) {
                    isBookmarked = false;
                    updateBookmarkIcon();
                    bookmarkId = null; // Setelah berhasil menghapus, set bookmarkId menjadi null
                    Toast.makeText(DetailActivity.this, "Bookmark deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Failed to delete bookmark", Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBookmarking = false;
                    }
                }, BOOKMARK_DELAY);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isBookmarking = false;
                Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBookmarking = false;
                    }
                }, BOOKMARK_DELAY);
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
