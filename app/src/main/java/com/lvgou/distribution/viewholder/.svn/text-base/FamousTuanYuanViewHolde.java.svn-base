package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.TuanYuanEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/5/16 0016.
 */
public class FamousTuanYuanViewHolde extends ViewHolderBase<TuanYuanEntity> {

    private LinearLayout ll_item;
    private ImageView img_head;
    private TextView tv_name;
    DisplayImageOptions options_head;

    private static OnListItemClickListener<TuanYuanEntity> tuanYuanEntityOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_grid_tuanyuan, null);
        img_head = (ImageView) view.findViewById(R.id.img_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        return view;
    }

    @Override
    public void showData(int position, final TuanYuanEntity itemData) {
        if (itemData.getName().length() > 4) {
            tv_name.setText(itemData.getName().substring(0, 4) + "...");
        } else {
            tv_name.setText(itemData.getName());
        }
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.studenthead_fail_bg)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.studenthead_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.studenthead_fail_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_head, options_head);

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuanYuanEntityOnListItemClickListener != null) {
                    tuanYuanEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setTuanYuanEntityOnListItemClickListener(OnListItemClickListener<TuanYuanEntity> tuanYuanEntityOnListItemClickListener) {
        FamousTuanYuanViewHolde.tuanYuanEntityOnListItemClickListener = tuanYuanEntityOnListItemClickListener;
    }
}
