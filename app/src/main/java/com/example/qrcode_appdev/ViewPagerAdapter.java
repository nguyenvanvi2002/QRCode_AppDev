package com.example.qrcode_appdev;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new QrScanFragment();
            case 1:
                return new CreateFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new SettingFragment();
            default: return new QrScanFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
