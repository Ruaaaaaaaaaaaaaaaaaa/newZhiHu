package com.wmj.newzhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.activity.NewsDetailsActivity;
import com.wmj.newzhihu.bean.Stories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmj on 2017-3-5.
 */

public class NestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Stories> mList;
    public NestAdapter(Context context, List<Stories> list){
        mContext = context;
        mList = list;

    }

    static class Holder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;
        CardView cardView;
        public Holder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.news_image);
            tv = (TextView) itemView.findViewById(R.id.news_name);
            cardView = (CardView) itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NestAdapter.Holder holder = new NestAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.cardview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Holder)holder).tv.setText(mList.get(position).getTitle());
        Glide.with(mContext).load(mList.get(position).getImages().get(0)).into(((Holder)holder).img);
        ((Holder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("id",""+mList.get(position).getStoryId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
