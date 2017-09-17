package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.OrderCentralChildEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;


/**
 * Created by Snow on 2016/3/15 0015.
 */
public class OrderCentralChildViewHolder extends ViewHolderBase<OrderCentralChildEntity> {

    private Context context;
    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_style;
    private TextView tv_price;
    private TextView tv_num;
    DisplayImageOptions options;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_order_centrea_two, null);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_goods_name);
        tv_style = (TextView) view.findViewById(R.id.tv_style);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_num = (TextView) view.findViewById(R.id.tv_numer);
        return view;
    }

    @Override
    public void showData(int position, OrderCentralChildEntity itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        tv_name.setText(itemData.getGoods_name());
        tv_style.setText("规格" + itemData.getGoods_style());
        tv_price.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getGoods_price())));
        tv_num.setText(itemData.getGoods_num());
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bg_none_order)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_order)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
    }
}
