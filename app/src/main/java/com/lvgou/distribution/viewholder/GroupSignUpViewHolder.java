package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.GroupSignEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/9/29.
 */
public class GroupSignUpViewHolder extends ViewHolderBase<GroupSignEntity> {


    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_head;
    private TextView tv_name;
    private ImageView img_1, img_2, img_3, img_4, img_5;
    private TextView tv_time;
    private ImageView img_status;
    private ImageView img_next;
    DisplayImageOptions options;

    private static OnListItemClickListener<GroupSignEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_group_signup, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_1 = (ImageView) view.findViewById(R.id.img_01);
        img_2 = (ImageView) view.findViewById(R.id.img_02);
        img_3 = (ImageView) view.findViewById(R.id.img_03);
        img_4 = (ImageView) view.findViewById(R.id.img_04);
        img_5 = (ImageView) view.findViewById(R.id.img_05);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        img_status = (ImageView) view.findViewById(R.id.img_status);
        img_next = (ImageView) view.findViewById(R.id.img_next);
        return view;
    }

    @Override
    public void showData(int position, final GroupSignEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(itemData.getImg_path()), img_head, options);

        if (itemData.getName().length() > 1) {
            tv_name.setText("*" + itemData.getName().substring(itemData.getName().length() - 1, itemData.getName().length()));
        } else {
            tv_name.setText(itemData.getName());
        }

        if (itemData.getStar().equals("1")) {
            img_1.setBackgroundResource(R.mipmap.get_star_already);
            img_2.setBackgroundResource(R.mipmap.get_star_yet);
            img_3.setBackgroundResource(R.mipmap.get_star_yet);
            img_4.setBackgroundResource(R.mipmap.get_star_yet);
            img_5.setBackgroundResource(R.mipmap.get_star_yet);
        } else if (itemData.getStar().equals("2")) {
            img_1.setBackgroundResource(R.mipmap.get_star_already);
            img_2.setBackgroundResource(R.mipmap.get_star_already);
            img_3.setBackgroundResource(R.mipmap.get_star_yet);
            img_4.setBackgroundResource(R.mipmap.get_star_yet);
            img_5.setBackgroundResource(R.mipmap.get_star_yet);
        } else if (itemData.getStar().equals("3")) {
            img_1.setBackgroundResource(R.mipmap.get_star_already);
            img_2.setBackgroundResource(R.mipmap.get_star_already);
            img_3.setBackgroundResource(R.mipmap.get_star_already);
            img_4.setBackgroundResource(R.mipmap.get_star_yet);
            img_5.setBackgroundResource(R.mipmap.get_star_yet);
        } else if (itemData.getStar().equals("4")) {
            img_1.setBackgroundResource(R.mipmap.get_star_already);
            img_2.setBackgroundResource(R.mipmap.get_star_already);
            img_3.setBackgroundResource(R.mipmap.get_star_already);
            img_4.setBackgroundResource(R.mipmap.get_star_already);
            img_5.setBackgroundResource(R.mipmap.get_star_yet);
        } else if (itemData.getStar().equals("5")) {
            img_1.setBackgroundResource(R.mipmap.get_star_already);
            img_2.setBackgroundResource(R.mipmap.get_star_already);
            img_3.setBackgroundResource(R.mipmap.get_star_already);
            img_4.setBackgroundResource(R.mipmap.get_star_already);
            img_5.setBackgroundResource(R.mipmap.get_star_already);
        }

        tv_time.setText(itemData.getTime().substring(5, 10) + "  " + itemData.getTime().substring(11, 16));

        if (itemData.getState().equals("0")) {
            img_status.setVisibility(View.INVISIBLE);
        } else {
            img_status.setVisibility(View.VISIBLE);
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });

        if (itemData.getIsPublisher().equals("1")) {
            img_next.setVisibility(View.VISIBLE);
        } else {
            img_next.setVisibility(View.INVISIBLE);
        }
    }

    public static void setOnListItemClickListener(OnListItemClickListener<GroupSignEntity> onListItemClickListener) {
        GroupSignUpViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
