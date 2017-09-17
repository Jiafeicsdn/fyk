package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.RecommentClassesEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/9/20.
 */
public class RecommentClassViewHolder extends ViewHolderBase<RecommentClassesEntity> {


    private Context context;
    private ImageView img_icon;
    private TextView tv_content;
    private TextView tv_sign_num;
    private RelativeLayout rl_item;
    DisplayImageOptions options;

    private static OnListItemClickListener<RecommentClassesEntity> onClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_recomment_classes, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_content = (TextView) view.findViewById(R.id.tv_production);
        tv_sign_num = (TextView) view.findViewById(R.id.tv_seek_num);
        return view;
    }

    @Override
    public void showData(int position, final RecommentClassesEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(13))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);

        tv_content.setText(itemData.getContent());
        tv_sign_num.setText(itemData.getSign_num() + "人学过");
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnClassifyPostionClickListener(OnListItemClickListener<RecommentClassesEntity> onClassifyPostionClickListener) {
        RecommentClassViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
