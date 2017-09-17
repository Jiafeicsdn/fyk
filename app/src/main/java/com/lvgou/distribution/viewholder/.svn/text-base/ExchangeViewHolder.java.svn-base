package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ClassfiyExpandAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ExchangeEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.view.ReportShopView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/10/31.
 */
public class ExchangeViewHolder extends ViewHolderBase<ExchangeEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_icon;
    private TextView tv_title;
    private TextView tv_tuanbi;
    private TextView tv_price;
    private TextView tv_people_num;
    private TextView tv_kucun;
    private ImageView img_state;


    DisplayImageOptions options;

    private static OnClassifyPostionClickListener<ExchangeEntity> onClassifyPostionClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_exchange_list, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_tuanbi = (TextView) view.findViewById(R.id.tv_tuanbi);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_kucun = (TextView) view.findViewById(R.id.tv_kucun);
        tv_people_num = (TextView) view.findViewById(R.id.tv_num);
        img_state = (ImageView) view.findViewById(R.id.img_state);
        return view;
    }

    @Override
    public void showData(int position, final ExchangeEntity itemData) {
        final String tuanbi = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_order)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_order)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_order)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                        // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
//        Glide.with(context).load(Url.ROOT + itemData.getImg_path()).error(R.mipmap.bg_none_order).into(img_icon);
        tv_title.setText(itemData.getTitle());
        tv_tuanbi.setText(itemData.getTuanbi() + "团币");
        tv_price.setText("¥" + itemData.getPrice());
        tv_people_num.setText(itemData.getPeople() + "人兑换");
        tv_kucun.setText(itemData.getKucun() + "库存");
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 中划线

        if (itemData.getState().equals("0")) {
            img_state.setBackgroundResource(R.mipmap.qiangguangle_icon);
        } else {
            if (Integer.parseInt(itemData.getTuanbi()) > Integer.parseInt(tuanbi)) {
                img_state.setBackgroundResource(R.mipmap.tuanbi_buzu);
            } else {
                img_state.setBackgroundResource(R.mipmap.duihuan_yellow_icon);
            }
        }

        img_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemData.getState().equals("0") && (Integer.parseInt(itemData.getTuanbi()) <= Integer.parseInt(tuanbi)))
                    if (onClassifyPostionClickListener != null) {
                        onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                    }
            }
        });

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });
    }


    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<ExchangeEntity> onClassifyPostionClickListener) {
        ExchangeViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
