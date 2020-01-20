package com.example.classmate.Student_Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.classmate.R;
import com.example.classmate.Student_Activities.Fragments.home_page;
import com.example.classmate.Student_Activities.Fragments.lessons;
import com.example.classmate.Student_Activities.Fragments.requests;
import com.example.classmate.Utils.CustomViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student_HomePage extends AppCompatActivity {

    private fragment_adapter fragment_adapter;
    private CustomViewPager viewPager;
    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        viewPager = findViewById(R.id.fragment_container);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        List<Fragment> fragments  = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, lessons.class.getName()));
        fragments.add(Fragment.instantiate(this, home_page.class.getName()));
        fragments.add(Fragment.instantiate(this, requests.class.getName()));
        fragment_adapter = new fragment_adapter(this.getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragment_adapter);
        viewPager.setCurrentItem(1);
        botNav.setSelectedItemId(R.id.nav_home);
    }

    public void setCurrentItem(int item) {
        viewPager.setCurrentItem(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_lessons:
                    viewPager.setCurrentItem(0);
                    break;

                case R.id.nav_requests:
                    viewPager.setCurrentItem(2);
                    break;

                case R.id.nav_home:
                    viewPager.setCurrentItem(1);
            }
            return true;
        }
    };


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    botNav.getMenu().findItem(R.id.nav_lessons).setChecked(true);
                    Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
                    break;
                case 1:
                    botNav.getMenu().findItem(R.id.nav_home).setChecked(true);
                    Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
                    break;
                case 2:
                    botNav.getMenu().findItem(R.id.nav_requests).setChecked(true);
                    Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };



}