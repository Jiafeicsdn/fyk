package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.view.ReportShopView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/9/22.
 */
public class ClassesViewHolder extends ViewHolderBase<ImageViewEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_icon;
    private TextView tv_title;
    DisplayImageOptions options_head;

    private static OnListItemClickListener<ImageViewEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_grid_clasess, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_production);
        return view;
    }

    @Override
    public void showData(int position, final ImageViewEntity itemData) {
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getPath(), img_icon, options_head);
        if (StringUtils.isEmpty(itemData.getTitle())) {
            tv_title.setText(" ");
        } else {
            tv_title.setText(itemData.getTitle());
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

    public static void setOnListItemClickListener(OnListItemClickListener<ImageViewEntity> onListItemClickListener) {
        ClassesViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
