package com.wmj.newzhihu.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.Stories;

import java.util.List;
import com.bumptech.glide.Glide;

/**
 * Created by wmj on 2017-3-4.
 */

public class ThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Stories> mList;
    public ThemeAdapter(Context context, List<Stories> list){
        mContext = context;
        mList = list;
    }

    public static final int CONTENT = 1;
    public static final int CONTENT_NO_IMAGE=2;

    static class ContentHolder extends RecyclerView.ViewHolder{
        ImageView img ;
        TextView tv;
        public ContentHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.news_image);
            tv = (TextView) itemView.findViewById(R.id.news_name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContentHolder holder = new ContentHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(null!=mList.get(position).getImages()) {
            ((ContentHolder) holder).img.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mList.get(position).getImages().get(0)).
                    into(((ContentHolder) holder).img);
        }else{
            ((ContentHolder) holder).img.setVisibility(View.GONE);
        }
        ((ContentHolder)holder).tv.setText(mList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
