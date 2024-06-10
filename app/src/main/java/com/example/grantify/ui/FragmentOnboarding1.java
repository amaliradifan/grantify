package com.example.grantify.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.grantify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOnboarding1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOnboarding1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentOnboarding1() {
        // Required empty public constructor
    }

    public static FragmentOnboarding1 newInstance(String param1, String param2) {
        FragmentOnboarding1 fragment = new FragmentOnboarding1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding1, container, false);

        // Mengambil referensi button next
        Button btnNext = view.findViewById(R.id.btn_next1);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk beralih ke fragment onboarding kedua
                showNextFragment();
            }
        });

        return view;
    }

    private void showNextFragment() {
        FragmentOnboarding2 fragment = new FragmentOnboarding2();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Menambahkan animasi untuk transisi fragment
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


}
