package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.CarryGroupRecordEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Snow on 2016/10/14.
 */
public class CarryGroupRecordViewHolder extends ViewHolderBase<CarryGroupRecordEntity> {


    private LinearLayout rl_item;
    private TextView tv_title;
    private ImageView img_one, img_two, img_three, img_four, img_five;
    private TextView tv_time;
    private TextView tv_content;

    private static OnListItemClickListener<CarryGroupRecordEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_carry_record, null);
        rl_item = (LinearLayout) view.findViewById(R.id.rl_item);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        img_five = (ImageView) view.findViewById(R.id.img_five);
        tv_time = (TextView) view.findViewById(R.id.tv_day);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void showData(int position, final CarryGroupRecordEntity itemData) {
        tv_title.setText(itemData.getTitle());
        tv_content.setText(itemData.getContent());
        initGrade(itemData.getGrade());


        String[] str = itemData.getTime().split("T");
        //2016-04-22 16:32:50
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long month1= between / (30*24 * 3600);
            long day1 = between / (24 * 3600);
            long hour1 = between / 3600;
            long minute1 = between / 60;

            if (minute1 < 60) {
                tv_time.setText(minute1 + "分钟前");
            } else if (hour1 < 24) {
                tv_time.setText(hour1 + "小时前");
            } else if (day1 < 30) {
                tv_time.setText(day1 + "天前");
            }else if (month1<12){
                tv_time.setText(month1 + "月前");
            } else {
                tv_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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


    public static void setOnListItemClickListener(OnListItemClickListener<CarryGroupRecordEntity> onListItemClickListener) {
        CarryGroupRecordViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
