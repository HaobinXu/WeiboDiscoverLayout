package com.xuhb.weibodiscoverlayoutdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabLayout();
    }

    private void initTabLayout() {
        ViewPager vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        TabLayout tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        Fragment[] fragments = new Fragment[]{ColumnFragment.newInstance(), ColumnFragment.newInstance(), ColumnFragment.newInstance(), ColumnFragment.newInstance()};
        String[] titles = getResources().getStringArray(R.array.columns);

        ModuleStateAdapter adapter = new ModuleStateAdapter(getSupportFragmentManager(), fragments);
        vp_pager.setAdapter(adapter);
        vp_pager.setOffscreenPageLimit(3);
        tab_layout.setupWithViewPager(vp_pager);
        for (int i = 0; i < titles.length; i++) {
            tab_layout.getTabAt(i).setText(titles[i]);
        }
    }
}
