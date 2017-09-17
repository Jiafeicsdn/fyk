package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ImageHeadEntity;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/5/17 0017.
 */
public class ImageHeadViewHolder extends ViewHolderBase<ImageHeadEntity> {

    private ImageView img_head;
    DisplayImageOptions options_head;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_grid_tuanyuan_one, null);
        img_head = (ImageView) view.findViewById(R.id.img_head);
        return view;
    }

    @Override
    public void showData(int position, ImageHeadEntity itemData) {
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.studenthead_fail_bg)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.studenthead_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.studenthead_fail_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getPath(), img_head, options_head);
    }
}
