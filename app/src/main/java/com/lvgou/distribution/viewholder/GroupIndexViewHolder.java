package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.GroupIndexEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Snow on 2016/9/29.
 */
public class GroupIndexViewHolder extends ViewHolderBase<GroupIndexEntity> {

    private LinearLayout rl_item;
    private TextView tv_title;
    private ImageView img_state;
    private TextView tv_time;
    private TextView tv_days;
    private TextView tv_destination;
    private TextView tv_sex;
    private TextView tv_price;

    private static OnListItemClickListener<GroupIndexEntity> onClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_group_index, null);
        rl_item = (LinearLayout) view.findViewById(R.id.rl_item);
        img_state = (ImageView) view.findViewById(R.id.img_state);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_days = (TextView) view.findViewById(R.id.tv_group_days);
        tv_destination = (TextView) view.findViewById(R.id.tv_destination);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_price = (TextView) view.findViewById(R.id.tv_daofu);
        return view;
    }


    @Override
    public void showData(int position, final GroupIndexEntity itemData) {
        Date start_date = null;
        Date end_date = null;
        tv_title.setText(itemData.getTitle());
        String str[] = itemData.getTime().split("T");
        tv_time.setText(str[0].split("-")[1] + "." + str[0].split("-")[2]);
        tv_days.setText(itemData.getDays() + "天");
        tv_destination.setText(itemData.getCity() + "," + itemData.getDestination());
        if (itemData.getSex().equals("0")) {
            tv_sex.setText("不限");
        } else if (itemData.getSex().equals("1")) {
            tv_sex.setText("男");
        } else if (itemData.getSex().equals("2")) {
            tv_sex.setText("女");
        }
        tv_price.setText("导服: " + itemData.getPrice() + "元/天");


        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            end_date = dfs.parse(dfs.format(new Date()));
            start_date = dfs.parse(itemData.getTime().split("T")[0] + " " + itemData.getTime().split("T")[1]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 1=待审核，2=招募中，3=审核失败， 4=已结束
        // 页面判断状态为招募中并且出团时间已过则派团状态显示已结束
        if (((start_date.getTime() - end_date.getTime()) / 1000 > 0) && itemData.getImg_state().equals("2")) {
            img_state.setBackgroundResource(R.mipmap.item_icon_zhaopinzhong);
        } else if (itemData.getImg_state().equals("4") || (itemData.getImg_state().equals("2") && (start_date.getTime() - end_date.getTime()) / 1000 <= 0)) {
            img_state.setBackgroundResource(R.mipmap.icon_group_list_over);
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnClassifyPostionClickListener(OnListItemClickListener<GroupIndexEntity> onClassifyPostionClickListener) {
        GroupIndexViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
