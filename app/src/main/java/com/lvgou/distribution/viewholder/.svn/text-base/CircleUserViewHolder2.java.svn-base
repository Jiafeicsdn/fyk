package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CircleUserViewHolder2 extends ViewHolderBase<CircleUserEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_head;
    private ImageView img_user_identify;
    private TextView tv_name;
    private TextView tv_fegnwen_num;
    private TextView tv_fangs_num;
    private ImageView img_follow;
    private ImageView img_sex;
    DisplayImageOptions options;
    private String distributorId = "";

    private static OnClassifyPostionClickListener<CircleUserEntity> circleUserEntityOnClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_circle_user, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        img_user_identify=(ImageView)view.findViewById(R.id.img_user_identify);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_fegnwen_num = (TextView) view.findViewById(R.id.tv_fengwen_num);
        tv_fangs_num = (TextView) view.findViewById(R.id.tv_fans_num);
        img_follow = (ImageView) view.findViewById(R.id.img_follow);
        img_sex = (ImageView) view.findViewById(R.id.img_sex);
        return view;
    }

    @Override
    public void showData(int position, final CircleUserEntity itemData) {
        distributorId = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.teacher_default_head)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.teacher_default_head)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.teacher_default_head)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(itemData.getID()), img_head, options);
        tv_name.setText(itemData.getRealName());
        tv_fegnwen_num.setText(itemData.getFengwenCount());
        tv_fangs_num.setText(itemData.getFansCount());

        if (itemData.getUserType() == 3) {
            img_user_identify.setImageResource(R.mipmap.bg_tecaher_authentication);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if(itemData.getUserType() == 2){
            if(itemData.getState()==5){
                img_user_identify.setImageResource(R.mipmap.icon_certified);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            }else{
                img_user_identify.setVisibility(View.GONE);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }
        }else if(itemData.getUserType() == 1){
            img_user_identify.setImageResource(R.mipmap.icon_official_certified);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }

        if (distributorId.equals(itemData.getID())) {
            img_follow.setVisibility(View.INVISIBLE);
        } else {
            img_follow.setVisibility(View.VISIBLE);
        }

        if (itemData.getTuanBi().equals("1")) {
            img_follow.setBackgroundResource(R.mipmap.circle_add_follow);
        } else if (itemData.getTuanBi().equals("2")) {
            img_follow.setBackgroundResource(R.mipmap.yiguanzhu);
        } else if (itemData.getTuanBi().equals("3")) {
            img_follow.setBackgroundResource(R.mipmap.huxiangguanzhu);
        }

        if (itemData.getAttr().equals("1")) {
            img_sex.setImageResource(R.mipmap.icon_man);
        } else if (itemData.getAttr().equals("2")) {
            img_sex.setImageResource(R.mipmap.icon_woman);
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleUserEntityOnClassifyPostionClickListener != null) {
                    circleUserEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });

        img_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleUserEntityOnClassifyPostionClickListener != null) {
                    circleUserEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });


    }

    public static void setCircleUserEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<CircleUserEntity> circleUserEntityOnClassifyPostionClickListener) {
        CircleUserViewHolder2.circleUserEntityOnClassifyPostionClickListener = circleUserEntityOnClassifyPostionClickListener;
    }
}
