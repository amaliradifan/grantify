package com.example.grantify;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.grantify.FragmentOnboarding1;
import com.example.grantify.FragmentOnboarding2;
import com.example.grantify.FragmentOnboarding3;

public class OnboardingPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public OnboardingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOnboarding1();
            case 1:
                return new FragmentOnboarding2();
            case 2:
                return new FragmentOnboarding3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
