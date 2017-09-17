package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.MoreFriendsEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/10/21.
 */
public class MoreFriendViewHolder extends ViewHolderBase<MoreFriendsEntity> {
    private Context context;
    private RelativeLayout rl_item;
    private View view_top;
    private LinearLayout ll_01;
    private TextView tv_title;
    private RelativeLayout rl_more_friends;
    private ImageView img_teacher_head;
    private ImageView img_sex;
    private TextView tv_name;
    private TextView tv_fengwen_num;
    private TextView tv_fans_num;
    private ImageView img_follow;
    private ImageView img_teacher_label;
    DisplayImageOptions options;

    private static OnClassifyPostionClickListener<MoreFriendsEntity> friendsEntityOnClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_more_friend, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        ll_01 = (LinearLayout) view.findViewById(R.id.ll_01);
        view_top = (View) view.findViewById(R.id.view_top);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        rl_more_friends = (RelativeLayout) view.findViewById(R.id.rl_more_friends);
        img_teacher_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        img_teacher_label = (ImageView) view.findViewById(R.id.img_teacher_label);
        img_sex = (ImageView) view.findViewById(R.id.img_sex);
        img_sex.setVisibility(View.GONE);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_fengwen_num = (TextView) view.findViewById(R.id.tv_fengwen_num);
        tv_fans_num = (TextView) view.findViewById(R.id.tv_fans_num);
        img_follow = (ImageView) view.findViewById(R.id.img_follow);
        img_follow.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void showData(int position, final MoreFriendsEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.teacher_default_head)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.teacher_default_head)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.teacher_default_head)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(itemData.getId()), img_teacher_head, options);
        tv_name.setText(itemData.getName());
        tv_fengwen_num.setText(itemData.getFengwen_num());
        tv_fans_num.setText(itemData.getFans_num());

        if (itemData.getIsFollow().equals("1")) {
            img_follow.setBackgroundResource(R.mipmap.circle_add_follow);
        } else if (itemData.getIsFollow().equals("2")) {
            img_follow.setBackgroundResource(R.mipmap.grey_follow_already);
        } else if (itemData.getIsFollow().equals("3")) {
            img_follow.setBackgroundResource(R.mipmap.huxiang_follow);
        }

       /* if (itemData.getSex().equals("1")) {
            img_sex.setBackgroundResource(R.mipmap.icon_man);
        } else if (itemData.getSex().equals("2")) {
            img_sex.setBackgroundResource(R.mipmap.icon_woman);
        }*/
        if (itemData.getUserType() == 3) {
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if (itemData.getUserType() == 2) {
            /*if (itemData.getState() == 5) {
                img_teacher_label.setImageResource(R.mipmap.icon_certified);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else {
                img_teacher_label.setVisibility(View.GONE);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }*/
            img_teacher_label.setVisibility(View.GONE);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
        } else if (itemData.getUserType() == 1) {
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }
        if (itemData.getType().equals("1")) {
            if (position == 0) {
                tv_title.setText("通讯录好友");
                ll_01.setVisibility(View.VISIBLE);
                if (Integer.parseInt(itemData.getContacts_num()) >= 4) {
                    rl_more_friends.setVisibility(View.VISIBLE);
                } else {
                    rl_more_friends.setVisibility(View.GONE);
                }
            } else {
                ll_01.setVisibility(View.GONE);
            }
        } else if (itemData.getType().equals("2")) {
            if (itemData.getContacts_num().length() == 0) {
                if (position == 0) {
                    tv_title.setText("推荐好友");
                    ll_01.setVisibility(View.VISIBLE);
                    view_top.setVisibility(View.GONE);
                    if (Integer.parseInt(itemData.getTuijian_num()) >= 4) {
                        rl_more_friends.setVisibility(View.VISIBLE);
                    } else {
                        rl_more_friends.setVisibility(View.GONE);
                    }
                } else {
                    ll_01.setVisibility(View.GONE);
                }
            } else {
                if (position == Integer.parseInt(itemData.getContacts_num())) {
                    tv_title.setText("推荐好友");
                    ll_01.setVisibility(View.VISIBLE);
                    view_top.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(itemData.getTuijian_num()) >= 4) {
                        rl_more_friends.setVisibility(View.VISIBLE);
                    } else {
                        rl_more_friends.setVisibility(View.GONE);
                    }
                } else {
                    ll_01.setVisibility(View.GONE);
                }
            }
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsEntityOnClassifyPostionClickListener != null) {
                    friendsEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });

        rl_more_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsEntityOnClassifyPostionClickListener != null) {
                    friendsEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });

        img_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsEntityOnClassifyPostionClickListener != null) {
                    friendsEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 3);
                }
            }
        });
    }

    public static void setFriendsEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<MoreFriendsEntity> friendsEntityOnClassifyPostionClickListener) {
        MoreFriendViewHolder.friendsEntityOnClassifyPostionClickListener = friendsEntityOnClassifyPostionClickListener;
    }
}
