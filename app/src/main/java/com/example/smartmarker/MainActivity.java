package com.example.smartmarker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("설정"));
        tabLayout.addTab(tabLayout.newTab().setText("지도"));
        tabLayout.addTab(tabLayout.newTab().setText("내정보"));

        viewPager=findViewById(R.id.viewpager);

        ArrayList<Integer> image = new ArrayList<>();
        image.add(R.drawable.ic_baseline_settings_24);
        image.add(R.drawable.ic_baseline_map_24);
        image.add(R.drawable.ic_baseline_person_outline_24);

        for (int i = 0; i < 3; i++)
        {
            tabLayout.getTabAt(i).setIcon(image.get(i));
        }


        //ViewPager Fragment 연결
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}