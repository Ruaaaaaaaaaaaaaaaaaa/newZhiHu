package com.wmj.newzhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmj.newzhihu.R;
import com.wmj.newzhihu.bean.BeforeNewsBean;
import com.wmj.newzhihu.bean.Comment;
import com.wmj.newzhihu.utils.WmjUtils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wmj on 2017-3-8.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Comment> mList;

    public CommentAdapter(Context context ,List<Comment> list){
        mContext = context;
        mList = list;
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView tvTime;
        TextView tvAuthor;
        TextView tvContent;
        CircleImageView img;
        public Holder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.time);
            tvAuthor = (TextView) itemView.findViewById(R.id.author);
            tvContent = (TextView) itemView.findViewById(R.id.content);
            img = (CircleImageView) itemView.findViewById(R.id.avatar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.comment_recyclerview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).tvAuthor.setText(mList.get(position).getAuthor());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long l = Long.parseLong(mList.get(position).getTime());
        Date date= new Date(l);

        ((Holder)holder).tvTime.setText(sdf.format(date));
        ((Holder)holder).tvContent.setText(mList.get(position).getContent());
        Glide.with(mContext).load(mList.get(position).getAvatar()).into(((Holder)holder).img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
