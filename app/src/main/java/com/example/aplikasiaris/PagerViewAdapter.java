package com.example.aplikasiaris;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super( fm );
    }

    public PagerViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super( fm, behavior );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            case 1:
                BuySellFragment buySellFragment = new BuySellFragment();
                return buySellFragment;

            case 2:
                TransactionFragment transactionFragment = new TransactionFragment();
                return transactionFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
