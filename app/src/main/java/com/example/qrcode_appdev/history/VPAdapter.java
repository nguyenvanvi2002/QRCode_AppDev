package com.example.qrcode_appdev.history;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.qrcode_appdev.history.fragment_create_history;
import com.example.qrcode_appdev.history.fragment_scan_history;

public class VPAdapter extends FragmentPagerAdapter {



    public VPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new fragment_scan_history();
            case 1: return new fragment_create_history();
            default: return new fragment_scan_history();
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0: title ="Quét"; break;
            case 1: title ="Tạo"; break;
        }
        return title;
    }
}
