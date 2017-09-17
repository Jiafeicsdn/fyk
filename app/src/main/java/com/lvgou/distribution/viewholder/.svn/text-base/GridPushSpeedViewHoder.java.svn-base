package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/24 0024.
 */
public class GridPushSpeedViewHoder extends ViewHolderBase<ImageViewEntity> {

    private Context context;
    private ImageView img_icon;
    private RelativeLayout rl_item;
    DisplayImageOptions options;

    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_grid_view, null);
        img_icon = (ImageView) view.findViewById(R.id.img);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        return view;
    }

    public void showData(int position, ImageViewEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_order)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_order)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_order)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getPath(), img_icon, options);
    }
}

