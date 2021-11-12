package com.example.smartmarker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments=new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new SettingsFragment());
        fragments.add(new MapFragment());
        fragments.add(new MyPageFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
