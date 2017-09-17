package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.PushSpeedDetialActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.GoodMarketChildEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;


/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class GoodMarketChildViewHolder extends ViewHolderBase<GoodMarketChildEntity> {

    private Context context;
    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_sale_price;
    private TextView tv_commission_price;
    private TextView tv_sale_num;
    private TextView tv_stock_num;
    private RelativeLayout rl_share;
    private LinearLayout ll_item;
    DisplayImageOptions options;
    private String Ratio;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_goos_market_child, null);
        Ratio = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_sale_price = (TextView) view.findViewById(R.id.tv_sale_price);
        tv_commission_price = (TextView) view.findViewById(R.id.tv_commission_price);
        tv_sale_num = (TextView) view.findViewById(R.id.tv_sale_num);
        tv_stock_num = (TextView) view.findViewById(R.id.tv_stock_num);
        rl_share = (RelativeLayout) view.findViewById(R.id.rl_share);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        return view;
    }

    @Override
    public void showData(int position, final GoodMarketChildEntity itemData) {
        tv_name.setText(itemData.getName());
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        tv_sale_price.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getSale_price())));
        tv_sale_num.setText(itemData.getSale_num());
        tv_stock_num.setText(itemData.getStock());

        double comssion = (Double.parseDouble(itemData.getSale_price()) - Double.parseDouble(itemData.getCommission())) * Double.parseDouble(Ratio) / 100;
        tv_commission_price.setText("¥" + decimalFormat.format(Float.parseFloat(comssion + "")));
        if (Integer.parseInt(itemData.getQuota()) > 0) {
            rl_share.setVisibility(View.VISIBLE);
            rl_share.setClickable(true);
        } else {
            rl_share.setVisibility(View.INVISIBLE);
            rl_share.setClickable(false);
        }
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle pBundle = new Bundle();
                pBundle.putString("goods_id", itemData.getId());
                pBundle.putString("index", "0");
                Intent intent_one = new Intent(context, PushSpeedActivity.class);
                intent_one.putExtras(pBundle);
                context.startActivity(intent_one);*/
            }
        });
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle pundle = new Bundle();
                pundle.putString("goods_id", itemData.getId());
                pundle.putString("type_share", "2");
                pundle.putString("shop_name", Constants.SHOP_NAME);
                Intent intent = new Intent(context, PushSpeedDetialActivity.class);
                intent.putExtras(pundle);
                context.startActivity(intent);
            }
        });
    }
}
