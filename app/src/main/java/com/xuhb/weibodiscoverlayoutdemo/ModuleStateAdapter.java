package com.xuhb.weibodiscoverlayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * fragment适配器
 */
public class ModuleStateAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments;

    public ModuleStateAdapter(FragmentManager fm, Fragment[]fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
