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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Student_HomePage extends AppCompatActivity {

    private fragment_adapter fragment_adapter;
    private ViewPager viewPager;
    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        viewPager = findViewById(R.id.fragment_container);

        botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        List<Fragment> fragments  = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, lessons.class.getName()));
        fragments.add(Fragment.instantiate(this, home_page.class.getName()));
        fragments.add(Fragment.instantiate(this, requests.class.getName()));
        fragment_adapter = new fragment_adapter(this.getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragment_adapter);
        viewPager.setCurrentItem(1);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment;

            switch (menuItem.getItemId()){

                case R.id.nav_lessons:
                    selectedFragment = new lessons();
                    break;

                case R.id.nav_requests:
                    selectedFragment = new requests();
                    break;

                default:
                    selectedFragment = new home_page();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}