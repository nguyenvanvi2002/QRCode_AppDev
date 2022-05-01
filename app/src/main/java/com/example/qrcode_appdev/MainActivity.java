package com.example.qrcode_appdev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    QrScanFragment scanFragment = new QrScanFragment();
    CreateFragment createFragment = new CreateFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, scanFragment).commit(); //set the begin fragment to scan
        bottomNavigationView.setSelectedItemId(R.id.qrscan); //set the begin tab to scan

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.qrscan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, scanFragment).commit();
                        return true;
                    case R.id.create:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, createFragment).commit();
                        return true;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, historyFragment).commit();
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}