package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.HotTeacherEntity;
import com.lvgou.distribution.inter.OnImageItemListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/9/22.
 */
public class HotTeacherViewHolder extends ViewHolderBase<HotTeacherEntity> {

    private ImageView image_head;
    private ImageView img_icon;
    private TextView tv_name;
    DisplayImageOptions options;

    private static OnImageItemListener<HotTeacherEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_hot_teacher, null);
        image_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        img_icon = (ImageView) view.findViewById(R.id.img_authen_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }


    @Override
    public void showData(int position, final HotTeacherEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), image_head, options);
        if (itemData.getIs_authen().equals("5")) {
            img_icon.setVisibility(View.VISIBLE);
        } else {
            img_icon.setVisibility(View.GONE);
        }
        tv_name.setText(itemData.getName());


        image_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onImageItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnImageItemListener<HotTeacherEntity> onListItemClickListener) {
        HotTeacherViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
