package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.AllTopicEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AllTopicViewHolder extends ViewHolderBase<AllTopicEntity> {

    private RelativeLayout rl_item;
    private ImageView img_bg;
    private TextView tv_title;
    private TextView tv_left;
    private TextView tv_right;
    DisplayImageOptions options;

    private static OnListItemClickListener<AllTopicEntity> allTopicEntityOnListItemClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_all_topic, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_bg = (ImageView) view.findViewById(R.id.img_bg);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        return view;
    }


    @Override
    public void showData(int position, final AllTopicEntity itemData) {
        tv_title.setText("#"+itemData.getTitle()+"#");
        tv_left.setText("阅读" + itemData.getReadNum());
        tv_right.setText("讨论" + itemData.getTopicNum());
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.pictures_no)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.pictures_no)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.pictures_no)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_bg, options);

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTopicEntityOnListItemClickListener != null) {
                    allTopicEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setAllTopicEntityOnListItemClickListener(OnListItemClickListener<AllTopicEntity> allTopicEntityOnListItemClickListener) {
        AllTopicViewHolder.allTopicEntityOnListItemClickListener = allTopicEntityOnListItemClickListener;
    }
}
