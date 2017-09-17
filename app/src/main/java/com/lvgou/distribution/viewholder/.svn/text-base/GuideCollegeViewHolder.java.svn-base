package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.GuiderCollegeEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/21 0021.
 */
public class GuideCollegeViewHolder extends ViewHolderBase<GuiderCollegeEntity> {

    private RelativeLayout rl_item;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_name;
    private ImageView img_icon;
    private TextView tv_content;
    DisplayImageOptions options;


    private static OnListItemClickListener<GuiderCollegeEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_guider_college, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void showData(int position, final GuiderCollegeEntity itemData) {
        tv_title.setText(itemData.getTitle());
        String[] str = itemData.getTime().split("T");
        tv_time.setText(str[0]);
        tv_name.setText(itemData.getName());
        tv_content.setText(itemData.getContent());
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bg_none_guider)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_guider)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnListItemClickListener<GuiderCollegeEntity> onListItemClickListener) {
        GuideCollegeViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
