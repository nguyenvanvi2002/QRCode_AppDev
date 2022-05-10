package com.example.qrcode_appdev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    private ViewPager mViewPager;
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

        setupViewPager();

        getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, scanFragment).commit(); //set the begin fragment to scan
        bottomNavigationView.setSelectedItemId(R.id.action_scan); //set the begin tab to scan

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_scan:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.action_create:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.action_history:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.action_setting:
                        mViewPager.setCurrentItem(3);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_scan).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_create).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_history).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_setting).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}