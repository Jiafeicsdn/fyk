package com.lvgou.distribution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.utils.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/12.
 */

public class TeacherListAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public TeacherListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerViewHolder(inflater.inflate(R.layout.teacher_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final HashMap<String, Object> info = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        ImageView im_picture = (ImageView) holder.itemView.findViewById(R.id.im_picture);        // 图片
        TextView tv_name = (TextView) holder.itemView.findViewById(R.id.tv_name);        // 名字
        TextView tv_teach_num = (TextView) holder.itemView.findViewById(R.id.tv_teach_num);        // 教授人数
        TextView tv_intro = (TextView) holder.itemView.findViewById(R.id.tv_intro);        // 讲师介绍
        View view_line = holder.itemView.findViewById(R.id.view_line);        // 讲师介绍
        tv_name.setText(info.get("RealName").toString());
        tv_teach_num.setText(info.get("Star").toString());
        tv_intro.setText(info.get("PicUrl").toString());//
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("ID").toString())).error(R.mipmap.teacher_default_head).into(im_picture);
        if (position%2==1){
            view_line.setVisibility(View.GONE);
        }else {
            view_line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 定义接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}