package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.TaskDetialEntity;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/3/30 0030.
 */
public class TaskDetialViewHolder extends ViewHolderBase<TaskDetialEntity> {

    private Context context;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_points;
    private View view_point;
    private RelativeLayout rl_red_circle;
    private RelativeLayout rl_gray_circle;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_task_detial, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_points = (TextView) view.findViewById(R.id.tv_points);
        view_point = (View) view.findViewById(R.id.view_circle);
        rl_red_circle = (RelativeLayout) view.findViewById(R.id.rl_red_circle);
        rl_gray_circle = (RelativeLayout) view.findViewById(R.id.rl_gray_circle);
        return view;
    }

    @Override
    public void showData(int position, TaskDetialEntity itemData) {
        if (position == 0) {
            rl_red_circle.setVisibility(View.VISIBLE);
            view_point.setVisibility(View.GONE);
            rl_gray_circle.setVisibility(View.GONE);
        } else {
            rl_red_circle.setVisibility(View.GONE);
            view_point.setVisibility(View.VISIBLE);
            rl_gray_circle.setVisibility(View.GONE);
        }
        tv_name.setText(itemData.getName());
        String[] str = itemData.getTiem().split("T");
        tv_time.setText(str[0]);

        if (itemData.getStatus().equals("1")) {
            tv_points.setText("+" + itemData.getPotions());
            tv_points.setTextColor(context.getResources().getColor(R.color.bg_new_guide_black));

        } else if (itemData.getStatus().equals("2")) {
            tv_points.setText("-" + itemData.getPotions());
            tv_points.setTextColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
        }
    }
}
