package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.entity.CommentListEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.view.RotateTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Snow on 2016/7/25 0025.
 */
public class CommentListViewHolder extends ViewHolderBase<CommentListEntity> {
    private ImageView img_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;
    private RotateTextView tv_tuanbi_num;
    DisplayImageOptions options;
private RelativeLayout rl_content_view;
    private static OnClassifyPostionClickListener<CommentListEntity> OnClassifyPostionClickListener;
    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_comment_list, null);
        rl_content_view=(RelativeLayout)view.findViewById(R.id.rl_content_view);
        img_head = (ImageView) view.findViewById(R.id.img_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_tuanbi_num = (RotateTextView) view.findViewById(R.id.tv_paid_num);
        return view;
    }

    @Override
    public void showData(final int position,final CommentListEntity itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        int a = Integer.parseInt(itemData.getImg_head()) / 250000;
        int b = Integer.parseInt(itemData.getImg_head()) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(itemData.getImg_head()) % 500;
        String path = "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.studenthead_fail_bg)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.studenthead_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.studenthead_fail_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + path, img_head, options);
        tv_name.setText(itemData.getName());
        tv_content.setText(itemData.getContent());
        if (Integer.parseInt(itemData.getImg_icon()) > 0) {
            tv_tuanbi_num.setVisibility(View.VISIBLE);
        } else {
            tv_tuanbi_num.setVisibility(View.GONE);
        }
        tv_tuanbi_num.setText(itemData.getImg_icon() + "团币");
        if (itemData.getTime() != null && itemData.getTime().length() > 0) {
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
                if (minute1 == 0) {
                    tv_time.setText("刚刚");
                } else if (minute1 > 0 && minute1 < 60) {
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
        }
        rl_content_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnClassifyPostionClickListener != null) {
                    OnClassifyPostionClickListener.onClassifyPostionClick(itemData,position);
                }
            }
        });
    }
    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<CommentListEntity> OnClassifyPostionClickListener) {
        CommentListViewHolder.OnClassifyPostionClickListener = OnClassifyPostionClickListener;
    }
}