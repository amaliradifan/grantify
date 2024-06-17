package com.example.grantify.ui;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.grantify.R;
import com.example.grantify.api.RetrofitClient;
import com.example.grantify.model.EditUserRequest;
import com.example.grantify.model.UserProfile;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    public interface OnFragmentCloseListener {
        void onFragmentClose();
    }

    private OnFragmentCloseListener listener;

    public void setOnFragmentCloseListener(OnFragmentCloseListener listener) {
        this.listener = listener;
    }

    private static final String ARG_USERNAME = "username";
    private static final String ARG_COMPANY = "company";
    private static final String ARG_EXPERIENCE = "experience";

    private String username;
    private String company;
    private String experience;

    public static EditProfileFragment newInstance(String username, String company, String experience) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_COMPANY, company);
        args.putString(ARG_EXPERIENCE, experience);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            company = getArguments().getString(ARG_COMPANY);
            experience = getArguments().getString(ARG_EXPERIENCE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        EditText etUsername = view.findViewById(R.id.etUsername);
        EditText etCompany = view.findViewById(R.id.etCompany);
        EditText etExperience = view.findViewById(R.id.etExperience);


         // Set the text of EditText fields
        if (username != null) {
            etUsername.setText(username);
        }
        if (company != null) {
            etCompany.setText(company);
        }
        if (experience != null) {
            etExperience.setText(experience);
        }


        // Find the back button and set its click listener
        ImageButton back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String company = etCompany.getText().toString();
                String experience = etExperience.getText().toString();

                // Validasi input pengguna jika diperlukan

                updateUser(username, company, experience);
            }
        });


        // Initialize other views and perform any other setup

        return view;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
        if (listener != null) {
            listener.onFragmentClose();
        }
    }

    private void updateUser(String username, String company, String experience) {
        RetrofitClient.getRetrofitClient(requireContext())
                .editUser(new EditUserRequest(username, company, experience))
                .enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireContext(), "Edit Profile successful", Toast.LENGTH_SHORT).show();
                            closeFragment();
                        } else {
                            try {
                                String errorResponse = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                String errorMessage = jsonObject.getString("message");

                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        // Handle failure
                    }
                });
    }

}
