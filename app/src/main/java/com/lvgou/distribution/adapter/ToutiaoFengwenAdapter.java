package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */
public class ToutiaoFengwenAdapter extends BaseAdapter {
    DisplayImageOptions options;
    private Context mContext;
    GoodView goodView;
    private List<CircleRecommentEntity> circleRecommentEntityList;
    private ItemClickListener itemClickListener;

    public ToutiaoFengwenAdapter(Context context) {
        mContext = context;
        circleRecommentEntityList = new ArrayList<>();
        goodView = new GoodView(context);
    }

    public void setClickListener(ItemClickListener clickListener) {
        itemClickListener = clickListener;
    }

    public void setFengcircleData(List<CircleRecommentEntity> circleRecommentEntities) {
        this.circleRecommentEntityList.addAll(circleRecommentEntities);
    }

    public List<CircleRecommentEntity> getCircleRecommentEntityList() {
        return circleRecommentEntityList;
    }

    @Override
    public int getCount() {
        return circleRecommentEntityList.size();
    }

    @Override
    public CircleRecommentEntity getItem(int position) {
        return circleRecommentEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_recommend, null);
            viewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            viewHolder.tv_subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
            viewHolder.txt_read_count = (TextView) convertView.findViewById(R.id.txt_read_count);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_teacher_head);
            viewHolder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.rl_zan = (LinearLayout) convertView.findViewById(R.id.rl_zan);
            viewHolder.img_zan = (ImageView) convertView.findViewById(R.id.img_zan);
            viewHolder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ParseEmojiMsgUtil.getExpressionString(mContext, circleRecommentEntityList.get(position).getID(), viewHolder.tv_title, circleRecommentEntityList.get(position).getTitle());
//        viewHolder.tv_title.setText(circleRecommentEntityList.get(position).getTitle());
        if (null != circleRecommentEntityList.get(position).getSubTitle() && !"".equals(circleRecommentEntityList.get(position).getSubTitle())) {
            viewHolder.tv_subtitle.setVisibility(View.VISIBLE);
            viewHolder.tv_subtitle.setText(circleRecommentEntityList.get(position).getSubTitle());
        } else {
            viewHolder.tv_subtitle.setVisibility(View.GONE);
        }
        viewHolder.rl_zan.setTag(viewHolder.img_zan);
        if (circleRecommentEntityList.get(position).getHits() > 10000) {
            int count = circleRecommentEntityList.get(position).getHits() / 10000;
            viewHolder.txt_read_count.setText(count + "万+阅读");
        } else {
            viewHolder.txt_read_count.setText(circleRecommentEntityList.get(position).getHits() + "阅读");
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.pictures_no)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.pictures_no)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.pictures_no)            // 设置图片加载或解码过程中发生错误显示的图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + circleRecommentEntityList.get(position).getPicUrl(), viewHolder.img_icon, options);

        if (Integer.parseInt(circleRecommentEntityList.get(position).getSourceDistributorID()) > 0) {
            viewHolder.tv_01.setText("转自:");
            viewHolder.tv_name.setText(" @" + circleRecommentEntityList.get(position).getSourceDistributorName());
        } else {
            viewHolder.tv_01.setText("来自:");
            viewHolder.tv_name.setText(" @官方");
        }

        if (circleRecommentEntityList.get(position).getZaned().equals("1")) {
            viewHolder.img_zan.setImageResource(R.mipmap.ding_select_icon);
            viewHolder.tv_zan.setTextColor(Color.parseColor("#d5aa5f"));
        } else {
            viewHolder.img_zan.setImageResource(R.mipmap.ding_normal_icon);
            viewHolder.tv_zan.setTextColor(Color.parseColor("#a3a3a3"));
        }

        if (Integer.parseInt(circleRecommentEntityList.get(position).getZanCount()) > 0) {
            viewHolder.tv_zan.setText(circleRecommentEntityList.get(position).getZanCount());
        } else {
            viewHolder.tv_zan.setText("赞");
        }

        if (Integer.parseInt(circleRecommentEntityList.get(position).getCommentCount()) > 0) {
            viewHolder.tv_comment.setText(circleRecommentEntityList.get(position).getCommentCount());
        } else {
            viewHolder.tv_comment.setText("评论");
        }

        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClassifyPostionClick(circleRecommentEntityList.get(position), position, "1");
                }
            }
        });

        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.rl_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.getTag();
                if (circleRecommentEntityList.get(position).getZaned().equals("1")) {
                    imageView.setImageResource(R.mipmap.ding_normal_icon);
                    finalViewHolder.tv_zan.setTextColor(Color.parseColor("#a3a3a3"));
                    goodView.setImage(R.mipmap.ding_normal_icon);
                    goodView.show(finalViewHolder.rl_zan);
                    if (Integer.parseInt(circleRecommentEntityList.get(position).getZanCount())-1 > 0) {
                        finalViewHolder1.tv_zan.setText(Integer.parseInt(circleRecommentEntityList.get(position).getZanCount())-1+"");
                    } else {
                        finalViewHolder1.tv_zan.setText("赞");
                    }

                } else {
                    imageView.setImageResource(R.mipmap.ding_select_icon);
                    finalViewHolder.tv_zan.setTextColor(Color.parseColor("#d5aa5f"));
                    goodView.setImage(R.mipmap.ding_select_icon);
                    goodView.show(finalViewHolder.rl_zan);
                    if (Integer.parseInt(circleRecommentEntityList.get(position).getZanCount())+1 > 0) {
                        finalViewHolder1.tv_zan.setText(Integer.parseInt(circleRecommentEntityList.get(position).getZanCount())+1+"");
                    } else {
                        finalViewHolder1.tv_zan.setText("赞");
                    }
                }

                /*imageView.startAnimation(AnimationUtils.loadAnimation(
                        mContext, R.anim.dianzan_anim));*/
                if (itemClickListener != null) {
                    itemClickListener.onClassifyPostionClick(circleRecommentEntityList.get(position), position, "2");
                }
            }
        });


        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClassifyPostionClick(circleRecommentEntityList.get(position), position, "3");

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
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
    }

    public interface ItemClickListener {
        public void onClassifyPostionClick(CircleRecommentEntity circleRecommentEntity, int position, String type);
    }
}
