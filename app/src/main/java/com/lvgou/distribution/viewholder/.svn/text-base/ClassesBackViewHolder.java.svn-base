package com.lvgou.distribution.viewholder;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/7/27 0027.
 */
public class ClassesBackViewHolder extends ViewHolderBase<ClassesBackEntity> {

    private RelativeLayout rl_item;
    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_signnum;
    private TextView tv_tuanbi;
    private ImageView img_course_state;

    DisplayImageOptions options_head;

    private static OnListItemClickListener<ClassesBackEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_class_back, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img);
        img_course_state=(ImageView)view.findViewById(R.id.img_course_state);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_title = (TextView) view.findViewById(R.id.tv_content);
        tv_signnum = (TextView) view.findViewById(R.id.tv_seek_num);
        tv_tuanbi = (TextView) view.findViewById(R.id.tv_tuanbi);
        return view;
    }

    @Override
    public void showData(int position, final ClassesBackEntity itemData) {
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.classback_fail_bg)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.classback_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.classback_fail_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_icon(), img_icon, options_head);
        tv_name.setText(itemData.getName());
        tv_title.setText(itemData.getTitle());
        tv_signnum.setText(itemData.getSign_num() + "人学过");
        if (itemData.getTuanbi().equals("0")) {
            tv_tuanbi.setText("免费");
        } else {
            tv_tuanbi.setText(itemData.getTuanbi() + "团币");
        }
        img_course_state.setVisibility(View.GONE);
        if(itemData.getState()==1){
            img_course_state.setVisibility(View.VISIBLE);
            img_course_state.setImageResource(R.mipmap.icon_course_apply_state);
        }else if(itemData.getState()==3){
            img_course_state.setVisibility(View.VISIBLE);
            img_course_state.setImageResource(R.mipmap.icon_course_start_state);
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

    public static void setOnListItemClickListener(OnListItemClickListener<ClassesBackEntity> onListItemClickListener) {
        ClassesBackViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
