package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ApplicationEntity;
import com.lvgou.distribution.inter.OnApplicationClickListener;
import com.lvgou.distribution.view.MyProgress;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;

/**
 * Created by Snow on 2016/4/27 0027.
 */
public class ApplicationViewHolder extends ViewHolderBase<ApplicationEntity> {

    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_production;
    private TextView tv_price;
    private TextView tv_extension;
    private TextView tv_total;
    private MyProgress progress;
    private MyProgress progress_one;
    DisplayImageOptions options;
    private ImageView img_star_one;
    private ImageView img_star_two;
    private ImageView img_star_three;
    private ImageView img_star_four;
    private ImageView img_star_five;
    private TextView tv_progress_jindu;
    private RelativeLayout rl_item;
    private Context context;


    private static OnApplicationClickListener<ApplicationEntity> onApplicationClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_fragment_appliaction, null);
        img_icon = (ImageView) view.findViewById(R.id.img);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_production = (TextView) view.findViewById(R.id.tv_production);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        progress = (MyProgress) view.findViewById(R.id.pgsBar);
        progress_one = (MyProgress) view.findViewById(R.id.pgsBar_one);// android:endColor="#ffd600"
        img_star_one = (ImageView) view.findViewById(R.id.img_1);
        img_star_one.setVisibility(View.GONE);
        img_star_two = (ImageView) view.findViewById(R.id.img_2);
        img_star_two.setVisibility(View.GONE);
        img_star_three = (ImageView) view.findViewById(R.id.img_3);
        img_star_three.setVisibility(View.GONE);
        img_star_four = (ImageView) view.findViewById(R.id.img_4);
        img_star_four.setVisibility(View.GONE);
        img_star_five = (ImageView) view.findViewById(R.id.img_5);
        img_star_five.setVisibility(View.GONE);
        tv_extension = (TextView) view.findViewById(R.id.tv_extension);
        tv_progress_jindu = (TextView) view.findViewById(R.id.tv_progress_jindu);
        return view;
    }
     
    @Override
    public void showData(int position, final ApplicationEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
        if (itemData.getState().equals("2")) {
            tv_extension.setText("已结束");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.thirteen));
            tv_extension.setTextColor(context.getResources().getColor(R.color.bg_notice_gray));
            tv_extension.setBackgroundResource(R.drawable.bg_light_gray_shape);
            progress.setProgress(100);
            tv_progress_jindu.setText("100%");
            progress.setVisibility(View.VISIBLE);
            progress_one.setVisibility(View.GONE);
        } else if (itemData.getState().equals("1") && itemData.getDownload().equals("0")) {
            tv_extension.setText("去申请");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.thirteen));
            tv_extension.setTextColor(Color.parseColor("#444444"));
            tv_extension.setBackgroundResource(R.drawable.bg_yellow_shape);
            progress.setProgress(Integer.parseInt(itemData.getPorgress()));
            tv_progress_jindu.setText(itemData.getPorgress()+"%");
            progress.setVisibility(View.VISIBLE);
            progress_one.setVisibility(View.GONE);
        } else if (itemData.getState().equals("1") && itemData.getDownload().equals("1")) {
            tv_extension.setText("待审核");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.thirteen));
            tv_extension.setTextColor(context.getResources().getColor(R.color.bg_white));
            tv_extension.setBackgroundResource(R.drawable.bg_task_black_shape);
            progress.setProgress(Integer.parseInt(itemData.getPorgress()));
            tv_progress_jindu.setText(itemData.getPorgress() + "%");
            progress.setVisibility(View.VISIBLE);
            progress_one.setVisibility(View.GONE);
        } else if (itemData.getState().equals("1") && itemData.getDownload().equals("2")) {
            tv_extension.setText("去赚钱");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.thirteen));
            tv_extension.setBackgroundResource(R.drawable.bg_yellow_shape);
            tv_extension.setTextColor(Color.parseColor("#333333"));
            progress.setProgress(Integer.parseInt(itemData.getPorgress()));
            tv_progress_jindu.setText(itemData.getPorgress() + "%");
            progress.setVisibility(View.VISIBLE);
            progress_one.setVisibility(View.GONE);
        } else if (itemData.getState().equals("1") && itemData.getDownload().equals("3")) {
            tv_extension.setText("审核失败");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.twelve));
            tv_extension.setTextColor(context.getResources().getColor(R.color.bg_notice_gray));
            tv_extension.setBackgroundResource(R.drawable.bg_light_gray_shape);
            progress.setProgress(Integer.parseInt(itemData.getPorgress()));
            tv_progress_jindu.setText(itemData.getPorgress() + "%");
            progress.setVisibility(View.VISIBLE);
            progress_one.setVisibility(View.GONE);
        } else if (itemData.getState().equals("4")) {
            tv_extension.setText("即将上线");
            tv_extension.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.twelve));
            tv_extension.setTextColor(context.getResources().getColor(R.color.bg_white));
            tv_extension.setBackgroundResource(R.drawable.bg_task_black_shape);
            progress_one.setProgress(Integer.parseInt(itemData.getPorgress()));
            tv_progress_jindu.setText(itemData.getPorgress()+"%");
            progress.setVisibility(View.GONE);
            progress_one.setVisibility(View.VISIBLE);
        }

        if (itemData.getProduct().equals("1")) {
            tv_production.setText("扫码注册");
        } else if (itemData.getProduct().equals("2")) {
            tv_production.setText("注册下载");
        } else if (itemData.getProduct().equals("3")) {
            tv_production.setText("关注服务号");
        } else if (itemData.getProduct().equals("4")) {
            tv_production.setText("微信加好友");
        } else if (itemData.getProduct().equals("5")) {
            tv_production.setText("网页购买");
        } else if (itemData.getProduct().equals("6")) {
            tv_production.setText("下载购买");
        }

        if (itemData.getStar().equals("1")) {
            img_star_one.setVisibility(View.VISIBLE);
            img_star_two.setVisibility(View.GONE);
            img_star_three.setVisibility(View.GONE);
            img_star_four.setVisibility(View.GONE);
            img_star_five.setVisibility(View.GONE);
        } else if (itemData.getStar().equals("2")) {
            img_star_one.setVisibility(View.VISIBLE);
            img_star_two.setVisibility(View.VISIBLE);
            img_star_three.setVisibility(View.GONE);
            img_star_four.setVisibility(View.GONE);
            img_star_five.setVisibility(View.GONE);
        } else if (itemData.getStar().equals("3")) {
            img_star_one.setVisibility(View.VISIBLE);
            img_star_two.setVisibility(View.VISIBLE);
            img_star_three.setVisibility(View.VISIBLE);
            img_star_four.setVisibility(View.GONE);
            img_star_five.setVisibility(View.GONE);
        } else if (itemData.getStar().equals("4")) {
            img_star_one.setVisibility(View.VISIBLE);
            img_star_two.setVisibility(View.VISIBLE);
            img_star_three.setVisibility(View.VISIBLE);
            img_star_four.setVisibility(View.VISIBLE);
            img_star_five.setVisibility(View.GONE);
        } else if (itemData.getStar().equals("5")) {
            img_star_one.setVisibility(View.VISIBLE);
            img_star_two.setVisibility(View.VISIBLE);
            img_star_three.setVisibility(View.VISIBLE);
            img_star_four.setVisibility(View.VISIBLE);
            img_star_five.setVisibility(View.VISIBLE);
        }else {
            img_star_one.setVisibility(View.GONE);
            img_star_two.setVisibility(View.GONE);
            img_star_three.setVisibility(View.GONE);
            img_star_four.setVisibility(View.GONE);
            img_star_five.setVisibility(View.GONE);
        }

        tv_name.setText(itemData.getName());
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        int wan = Integer.parseInt(itemData.getTotal()) / 10000;
        int qian = Integer.parseInt(itemData.getTotal()) % 10000;
        int qianwei_ = qian / 1000;
        int baiwei_ = qian % 1000;
        int baiwei = baiwei_ / 100;
        String str_fen = wan + "." + qianwei_ + baiwei;
        tv_total.setText("总额: " + decimalFormat.format(Float.parseFloat(str_fen)) + "万");
        tv_price.setText(itemData.getPrice() + "元/个");


        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onApplicationClickListener != null) {
                    onApplicationClickListener.onApplicationClickListener(itemData, tv_extension, 1);
                }
            }
        });

        tv_extension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onApplicationClickListener != null) {
                    onApplicationClickListener.onApplicationClickListener(itemData, tv_extension, 2);
                }
            }
        });
    }

    public static void setOnApplicationClickListener(OnApplicationClickListener<ApplicationEntity> onApplicationClickListener) {
        ApplicationViewHolder.onApplicationClickListener = onApplicationClickListener;
    }

}
