package com.xuhb.weibodiscoverlayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xuhb
 * @version 2.8.2
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * @date 16/12/28 上午11:15
 */
public class ColumnFragment extends Fragment {

    public static ColumnFragment newInstance(){
        return new ColumnFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_column, null);
        RecyclerView rv_weibo = (RecyclerView) view.findViewById(R.id.rv_weibo);
        String[] names = getResources().getStringArray(R.array.names);
        WeiboAdapter adapter = new WeiboAdapter(names);
        rv_weibo.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_weibo.setAdapter(adapter);
        return view;
    }
}
