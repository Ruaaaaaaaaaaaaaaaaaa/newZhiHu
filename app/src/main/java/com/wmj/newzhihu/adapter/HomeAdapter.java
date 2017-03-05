package com.wmj.newzhihu.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.Stories;

import java.util.List;

/**
 * Created by wmj on 2017-3-4.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<BeforeNewsBean> mList;
    public HomeAdapter(Context context, List<BeforeNewsBean> list){
        mContext = context;
        mList = list;
    }

    static class Holder extends RecyclerView.ViewHolder{
        RecyclerView nestRecycler;
        TextView textView;
        NestAdapter adapter;
        public Holder(View itemView) {
            super(itemView);
            nestRecycler = (RecyclerView) itemView.findViewById(R.id.nestedRecyclerView);
            textView = (TextView) itemView.findViewById(R.id.textview_date);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.home_data_item,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).adapter = new NestAdapter(mContext,mList.get(position).getStories());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) ;

        ((Holder)holder).nestRecycler.setLayoutManager(layoutManager);
        ((Holder)holder).nestRecycler.setAdapter(((Holder)holder).adapter);
        ((Holder)holder).textView.setText(mList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
