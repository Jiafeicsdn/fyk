package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.PushSpeedDetialActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.OutSeaEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;


/**
 * Created by Snow on 2016/3/29 0029.
 */
public class OutSeaViewHolder extends ViewHolderBase<OutSeaEntity> {

    private Context context;
    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_price;// 原价
    private TextView tv_disount;
    private TextView tv_commsion;
    private String Ratio;
    private LinearLayout ll_item;
    DisplayImageOptions options;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        Ratio = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO);
        View view = layoutInflater.inflate(R.layout.item_out_sea, null);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_price = (TextView) view.findViewById(R.id.tv_price_origin);
        tv_disount = (TextView) view.findViewById(R.id.tv_sale_num);
        tv_commsion = (TextView) view.findViewById(R.id.tv_save_num);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        return view;
    }

    @Override
    public void showData(final int position, final OutSeaEntity itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        tv_name.setText(itemData.getName());
        tv_price.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getPrice())));
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tv_disount.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getDiscount_price())));
        double commison = (Double.parseDouble(itemData.getDiscount_price()) - Double.parseDouble(itemData.getCommission())) * Double.parseDouble(Ratio) / 100;
        tv_commsion.setText("¥" + decimalFormat.format(Float.parseFloat(commison + "")));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle pundle = new Bundle();
                pundle.putString("goods_id", itemData.getId());
                pundle.putString("type_share", "3");
                pundle.putString("shop_name", Constants.SHOP_NAME);
                Intent intent = new Intent(context, PushSpeedDetialActivity.class);
                intent.putExtras(pundle);
                context.startActivity(intent);
            }
        });
    }
}
