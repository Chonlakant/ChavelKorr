package com.twentyfour.chavel.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.twentyfour.chavel.R;

import java.util.ArrayList;
import java.util.List;


public class NewRouteTabFragment extends AppCompatActivity {

    String[] icons = {"New Route", "From Draft"};

    TabLayout tabLayout2;
    ViewPager viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_router);


        tabLayout2 = (TabLayout) findViewById(R.id.tab_layout);
        viewPager2 = (ViewPager) findViewById(R.id.main_tab_content);
        viewPager2.setOffscreenPageLimit(5);
        setupViewPager2(viewPager2);

        tabLayout2.setupWithViewPager(viewPager2);

        for (int i = 0; i < icons.length; i++) {
            tabLayout2.getTabAt(i).setText(icons[i]);
        }
        tabLayout2.getTabAt(0).select();


    }

    private void setupViewPager2(ViewPager viewPager2) {
        ViewPagerAdapter adapter2 = new ViewPagerAdapter(getSupportFragmentManager());
        adapter2.insertNewFragment(new NewRouteFragment());
        adapter2.insertNewFragment(new DraftFragment());
        viewPager2.setAdapter(adapter2);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }


}
