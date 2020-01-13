package com.example.classmate.Student_Activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class fragment_adapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public fragment_adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title ;
        if (position == 1){
            title= "Home Page";
        }else {
             title = getItem(position).getClass().getName();
        }
        return title.subSequence(title.lastIndexOf(".") + 1, title.length());
    }
}
