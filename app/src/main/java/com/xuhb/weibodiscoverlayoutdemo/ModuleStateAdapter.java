package com.xuhb.weibodiscoverlayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author xuhb
 * @version 2.7.1
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * @date 16/2/18 上午11:23
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
