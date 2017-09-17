package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.lvgou.distribution.inter.OnImageItemListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/21 0021.
 */
public class CollegeIconViewHolder extends ViewHolderBase<ImageViewEntity> {

    private RelativeLayout rl_item;
    private ImageView img_icon;
    private Context context;
    private static OnImageItemListener<ImageViewEntity> onImageItemListener;
    DisplayImageOptions options;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_college_interpretation, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        return view;
    }

    @Override
    public void showData(int position, final ImageViewEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_guider)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_guider)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_guider)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getPath(), img_icon, options);


        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageItemListener != null) {
                    onImageItemListener.onImageItemClick(itemData);
                }
            }
        });
    }

    public static void setOnImageItemListener(OnImageItemListener<ImageViewEntity> onImageItemListener) {
        CollegeIconViewHolder.onImageItemListener = onImageItemListener;
    }
}
