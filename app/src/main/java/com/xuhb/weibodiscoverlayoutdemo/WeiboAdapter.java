package com.xuhb.weibodiscoverlayoutdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author xuhb
 * @version 2.8.2
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * @date 16/12/28 上午11:15
 */
public class WeiboAdapter extends RecyclerView.Adapter<WeiboAdapter.WeiboViewHolder>{

    private String[] weibos;

    public WeiboAdapter(String[] weibos) {
        this.weibos = weibos;
    }

    @Override
    public WeiboViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeiboViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo, parent, false));
    }

    @Override
    public void onBindViewHolder(WeiboViewHolder holder, int position) {
        holder.tv_name.setText(weibos[position]);
    }

    @Override
    public int getItemCount() {
        return weibos.length;
    }

    class WeiboViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public WeiboViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
