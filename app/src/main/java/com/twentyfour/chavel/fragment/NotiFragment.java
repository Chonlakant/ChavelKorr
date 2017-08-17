package com.twentyfour.chavel.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twentyfour.chavel.R;
import com.twentyfour.chavel.fragment.TabActivityFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NotiFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    TabLayout tabLayoutNoti;
    ViewPager viewPagerNoti;

    String[] iconsNoti = {"Following", "You"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_noti,null);
        ButterKnife.bind(getActivity());
        tabLayoutNoti = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPagerNoti = (ViewPager) view.findViewById(R.id.main_tab_content);
        setupViewPagerNoti(viewPagerNoti);

        tabLayoutNoti.setupWithViewPager(viewPagerNoti);

        for (int i = 0; i < iconsNoti.length; i++) {
            tabLayoutNoti.getTabAt(i).setText(iconsNoti[i]);
        }
        tabLayoutNoti.getTabAt(0).select();

        return view;

    }

    private void setupViewPagerNoti(ViewPager viewPager) {
        ViewPagerAdapterNoti adapter = new ViewPagerAdapterNoti(getActivity().getSupportFragmentManager());
        adapter.insertNewFragment(new TabActivityFragment());
        adapter.insertNewFragment(new TabActivityFragment());
        viewPager.setAdapter(adapter);

    }


    class ViewPagerAdapterNoti extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapterNoti(FragmentManager manager) {
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
