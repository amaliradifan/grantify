package com.example.grantify.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.Program;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements ProgramAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProgramAdapter programAdapter;
    private List<Program> programList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve arguments if any
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ImageView buttonDelete = view.findViewById(R.id.back_search);
        SearchView searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.rc_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        programAdapter = new ProgramAdapter(programList, this);
        recyclerView.setAdapter(programAdapter);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(SearchFragment.this).commit();
                fragmentManager.popBackStack();
            }
        });

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

        return view;
    }

    private void fetchPrograms(String query) {
        RetrofitClient.getRetrofitClient(requireContext()).searchPrograms(query).enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    programList.clear();
                    programList.addAll(response.body());
                    programAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public void onItemClick(Program program) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("PROGRAM_TITLE", program.getTitle());
        intent.putExtra("PROGRAM_CATEGORY", program.getCategory());
        intent.putExtra("PROGRAM_IMAGE", program.getImage());
        intent.putExtra("PROGRAM_COMPANY", program.getUploader());
        startActivity(intent);
    }
}
