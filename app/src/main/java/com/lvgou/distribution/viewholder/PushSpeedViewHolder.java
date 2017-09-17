package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.lvgou.distribution.entity.PushSpeedEntity;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.lvgou.distribution.inter.OnShowImageClickListener;
import com.lvgou.distribution.view.MyGridView;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Snow on 2016/3/17 0017.
 */
public class PushSpeedViewHolder extends ViewHolderBase<PushSpeedEntity> {

    private Context context;
    private TextView tv_name;
    private TextView tv_sale_price;
    private TextView tv_default_price;
    private TextView tv_commission_price;
    private TextView tv_content;
    private MyGridView grid_view;
    private ImageView img_detial;
    private TextView tv_clock;
    private TextView tv_copy;
    private TextView tv_save;
    private TextView tv_share;
    private ListViewDataAdapter<ImageViewEntity> imageViewEntityListViewDataAdapter;

    private static OnPushSpeedClickListener<PushSpeedEntity> onPushSpeedClickListener;
    private static OnShowImageClickListener<PushSpeedEntity> onShowImageClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_push_speed, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_sale_price = (TextView) view.findViewById(R.id.tv_sale_price);
        tv_default_price = (TextView) view.findViewById(R.id.tv_default_price);
        tv_commission_price = (TextView) view.findViewById(R.id.tv_commission_price);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        grid_view = (MyGridView) view.findViewById(R.id.gridview);
        img_detial = (ImageView) view.findViewById(R.id.img_detial);
        tv_clock = (TextView) view.findViewById(R.id.tv_clock);
        tv_copy = (TextView) view.findViewById(R.id.tv_copy);
        tv_save = (TextView) view.findViewById(R.id.tv_save_picture);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
        return view;
    }

    @Override
    public void showData(int position, final PushSpeedEntity itemData) {
        tv_name.setText(itemData.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        tv_sale_price.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getPrice_Sell())));
        tv_default_price.setText(decimalFormat.format(Float.parseFloat(itemData.getPrice_Market())));
        tv_commission_price.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getPrice_Profit())));
        tv_content.setText(itemData.getIntro());
        tv_default_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        if (itemData.getSendTime() != null && itemData.getSendTime().length() > 0) {
            String[] str = itemData.getSendTime().split("T");
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
                    tv_clock.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    tv_clock.setText(hour1 + "小时前");
                } else if (day1 < 7) {
                    tv_clock.setText(day1 + "天前");
                }else if (month1<12){
                    tv_clock.setText(month1 + "月前");
                } else {
                    tv_clock.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (itemData.getPicUrls() != null && itemData.getPicUrls().length() > 0) {
            try {
                imageViewEntityListViewDataAdapter = new ListViewDataAdapter<ImageViewEntity>();
                JSONArray array = new JSONArray(itemData.getPicUrls());
                imageViewEntityListViewDataAdapter.removeAll();
                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json_image = array.getJSONObject(i);
                        String pic_path = json_image.getString("fileUrl");
                        ImageViewEntity imageViewEntity = new ImageViewEntity(i + "", pic_path, "", "");
                        imageViewEntityListViewDataAdapter.append(imageViewEntity);
                    }
                }
                imageViewEntityListViewDataAdapter.setViewHolderClass(context, GridPushSpeedViewHoder.class);
                grid_view.setAdapter(imageViewEntityListViewDataAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onShowImageClickListener != null) {
                    onShowImageClickListener.onShowImageClickListener(itemData, position, 0);
                }
            }
        });

        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPushSpeedClickListener != null) {
                    onPushSpeedClickListener.onPushSpeedListener(itemData, 1);
                }
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPushSpeedClickListener != null) {
                    onPushSpeedClickListener.onPushSpeedListener(itemData, 2);
                }
            }
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPushSpeedClickListener != null) {
                    onPushSpeedClickListener.onPushSpeedListener(itemData, 3);
                }
            }
        });
        img_detial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPushSpeedClickListener != null) {
                    onPushSpeedClickListener.onPushSpeedListener(itemData, 4);
                }
            }
        });

    }

    public static void setOnPushSpeedClickListener(OnPushSpeedClickListener<PushSpeedEntity> onPushSpeedClickListener) {
        PushSpeedViewHolder.onPushSpeedClickListener = onPushSpeedClickListener;
    }

    public static void setOnShowImageClickListener(OnShowImageClickListener<PushSpeedEntity> onShowImageClickListener) {
        PushSpeedViewHolder.onShowImageClickListener = onShowImageClickListener;
    }
}
