package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnClassifyPostionOneClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Swno on 2016/10/18.
 */
public class CircleRecommentViewHolder extends ViewHolderBase<CircleRecommentEntity> {

    private LinearLayout ll_item;
    private TextView tv_title;
    private TextView txt_read_count;
    private TextView tv_subtitle;
    private ImageView img_icon;
    private TextView tv_01;
    private TextView tv_name;
    private LinearLayout rl_zan;
    private ImageView img_zan;
    private TextView tv_zan;
    private TextView tv_comment;
    DisplayImageOptions options;

    private static OnClassifyPostionOneClickListener<CircleRecommentEntity> onClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_circle_recommend, null);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        txt_read_count=(TextView)view.findViewById(R.id.txt_read_count);
        tv_title = (TextView) view.findViewById(R.id.txt_title);
        img_icon = (ImageView) view.findViewById(R.id.img_teacher_head);
        tv_01 = (TextView) view.findViewById(R.id.tv_01);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_zan = (LinearLayout) view.findViewById(R.id.rl_zan);
        img_zan = (ImageView) view.findViewById(R.id.img_zan);
        tv_zan = (TextView) view.findViewById(R.id.tv_zan);
        tv_comment = (TextView) view.findViewById(R.id.tv_comment);

        return view;
    }

    @Override
    public void showData(final int position, final CircleRecommentEntity itemData) {
        tv_title.setText(itemData.getTitle());
        if(!"null".equals(itemData.getSubTitle())&&!"".equals(itemData.getSubTitle())){
            tv_subtitle.setVisibility(View.VISIBLE);
            tv_subtitle.setText(itemData.getSubTitle());
        }else{
            tv_subtitle.setVisibility(View.GONE);
        }

        if(itemData.getHits()>10000){
            int count=itemData.getHits()/10000;
            txt_read_count.setText(count+"万+阅读");
        }else {
            txt_read_count.setText(itemData.getHits() + "阅读");
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.bg_none_guider)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_guider)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_guider)            // 设置图片加载或解码过程中发生错误显示的图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getPicUrl(), img_icon, options);

        if (Integer.parseInt(itemData.getSourceDistributorID()) > 0) {
            tv_01.setText("转自:");
            tv_name.setText(" @" + itemData.getSourceDistributorName());
        } else {
            tv_01.setText("来自:");
            tv_name.setText(" @官方");
        }

        if (itemData.getZaned().equals("1")) {
            img_zan.setImageResource(R.mipmap.icon_fengwen_zaned);
        } else {
            img_zan.setImageResource(R.mipmap.icon_fengwen_zan);
        }

        if (Integer.parseInt(itemData.getZanCount()) > 0) {
            tv_zan.setText(itemData.getZanCount());
        } else {
            tv_zan.setText("赞");
        }

        if (Integer.parseInt(itemData.getCommentCount()) > 0) {
            tv_comment.setText(itemData.getCommentCount());
        } else {
            tv_comment.setText("评论");
        }

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, position, "1");
                }
            }
        });

        rl_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, position, "2");
                }
            }
        });


        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, position, "3");

                }
            }
        });
    }

    public static void setOnClassifyPostionClickListener(OnClassifyPostionOneClickListener<CircleRecommentEntity> onClassifyPostionClickListener) {
        CircleRecommentViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
