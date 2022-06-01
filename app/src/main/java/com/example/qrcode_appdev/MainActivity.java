package com.example.qrcode_appdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.qrcode_appdev.create.CreateFragment;
import com.example.qrcode_appdev.scanner.QrScanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    FrameLayout mViewPager;
    QrScanFragment scanFragment = new QrScanFragment();
    CreateFragment createFragment = new CreateFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, scanFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.action_scan);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.action_scan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, scanFragment).commit();
                    return true;
                case R.id.action_create:
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, createFragment).commit();
                    return true;
                case R.id.action_history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, historyFragment).commit();
                    return true;
                case R.id.action_setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, settingFragment).commit();
                    return true;
            }
            return false;
        });

    }
}