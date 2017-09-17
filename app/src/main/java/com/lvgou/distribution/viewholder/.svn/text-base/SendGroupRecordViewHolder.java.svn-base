package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.SendGroupRecorderEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/14.
 */
public class SendGroupRecordViewHolder extends ViewHolderBase<SendGroupRecorderEntity> {

    private RelativeLayout rl_item;
    private TextView tv_title;
    private ImageView img_state;
    private TextView tv_time;
    private TextView tv_group_days;
    private TextView tv_destination;
    private TextView tv_sex;
    private TextView tv_daofu;
    private ImageView img_one, img_two, img_three, img_four, img_five;

    private static OnListItemClickListener<SendGroupRecorderEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_sent_group_recorder, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        img_state = (ImageView) view.findViewById(R.id.img_state);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_daofu = (TextView) view.findViewById(R.id.tv_daofu);
        tv_group_days = (TextView) view.findViewById(R.id.tv_group_days);
        tv_destination = (TextView) view.findViewById(R.id.tv_destination);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        img_five = (ImageView) view.findViewById(R.id.img_five);

        return view;
    }


    @Override
    public void showData(int position, final SendGroupRecorderEntity itemData) {
        Date start_date = null;
        Date end_date = null;
        tv_title.setText(itemData.getTitle());
        tv_group_days.setText(itemData.getDays() + "天");
        String str[] = itemData.getTime().split("T");
        tv_time.setText(str[0].split("-")[1] + "." + str[0].split("-")[2]);
        tv_destination.setText(itemData.getDestination());
        if (itemData.getSex().equals("0")) {
            tv_sex.setText("不限");
        } else if (itemData.getSex().equals("1")) {
            tv_sex.setText("男");
        } else if (itemData.getSex().equals("2")) {
            tv_sex.setText("女");
        }
        tv_daofu.setText("导服: " + itemData.getPrice() + "元/天");
        initGrade(itemData.getGrade());


        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            end_date = dfs.parse(dfs.format(new Date()));
            start_date = dfs.parse(itemData.getTime().split("T")[0] + " " + itemData.getTime().split("T")[1]);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 1=待审核，2=招募中，3=审核失败， 4=已结束
        // 页面判断状态为招募中并且出团时间已过则派团状态显示已结束
        if (itemData.getImg_state().equals("1")) {
            img_state.setBackgroundResource(R.mipmap.icon_group_list_check);
        } else if (itemData.getImg_state().equals("2")) {
            img_state.setBackgroundResource(R.mipmap.item_icon_going);
        } else if (itemData.getImg_state().equals("3")) {
            img_state.setBackgroundResource(R.mipmap.item_icon_cheack_fail);
        } else if (itemData.getImg_state().equals("2") && (start_date.getTime() - end_date.getTime()) / 1000 < 0) {
            img_state.setBackgroundResource(R.mipmap.icon_group_list_over);
        }


        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }


    public void initGrade(String grade) {
        switch (Integer.parseInt(grade)) {
            case 0:
                img_one.setBackgroundResource(R.mipmap.get_star_yet);
                img_two.setBackgroundResource(R.mipmap.get_star_yet);
                img_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 1:
                img_one.setBackgroundResource(R.mipmap.get_star_already);
                img_two.setBackgroundResource(R.mipmap.get_star_yet);
                img_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 2:
                img_one.setBackgroundResource(R.mipmap.get_star_already);
                img_two.setBackgroundResource(R.mipmap.get_star_already);
                img_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 3:
                img_one.setBackgroundResource(R.mipmap.get_star_already);
                img_two.setBackgroundResource(R.mipmap.get_star_already);
                img_three.setBackgroundResource(R.mipmap.get_star_already);
                img_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 4:
                img_one.setBackgroundResource(R.mipmap.get_star_already);
                img_two.setBackgroundResource(R.mipmap.get_star_already);
                img_three.setBackgroundResource(R.mipmap.get_star_already);
                img_four.setBackgroundResource(R.mipmap.get_star_already);
                img_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 5:
                img_one.setBackgroundResource(R.mipmap.get_star_already);
                img_two.setBackgroundResource(R.mipmap.get_star_already);
                img_three.setBackgroundResource(R.mipmap.get_star_already);
                img_four.setBackgroundResource(R.mipmap.get_star_already);
                img_five.setBackgroundResource(R.mipmap.get_star_already);
                break;
        }
    }

    public static void setOnListItemClickListener(OnListItemClickListener<SendGroupRecorderEntity> onListItemClickListener) {
        SendGroupRecordViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
