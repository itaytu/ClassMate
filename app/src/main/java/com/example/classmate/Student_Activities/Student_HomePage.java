package com.example.classmate.Student_Activities;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.classmate.R;
import com.example.classmate.Student_Activities.Fragments.Student_Home_Page;
import com.example.classmate.Student_Activities.Fragments.lessons;
import com.example.classmate.Student_Activities.Fragments.requests;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Student_HomePage extends AppCompatActivity {

    private fragment_adapter fragment_adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        List<Fragment> fragments  = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, lessons.class.getName()));
        fragments.add(Fragment.instantiate(this, Student_Home_Page.class.getName()));
        fragments.add(Fragment.instantiate(this, requests.class.getName()));
        fragment_adapter = new fragment_adapter(this.getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragment_adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
}
}