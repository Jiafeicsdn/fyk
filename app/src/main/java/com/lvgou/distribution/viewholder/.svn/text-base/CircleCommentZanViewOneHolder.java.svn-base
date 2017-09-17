package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CircleCommentZanViewOneHolder extends ViewHolderBase<CircleCommZanEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_teacher_head;
    private ImageView img_teacher_icon;
    private TextView tv_name;
    private TextView tv_me;
    private TextView tv_replay;
    private TextView tv_time;
    private TextView tv_replay_title;
    private TextView tv_replay_content;

    DisplayImageOptions options;

    private static OnClassifyPostionClickListener<CircleCommZanEntity> circleCommZanEntityOnClassifyPostionClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_persoanl_comment, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_teacher_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        img_teacher_icon = (ImageView) view.findViewById(R.id.img_teacher_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_me = (TextView) view.findViewById(R.id.tv_me);
        tv_replay = (TextView) view.findViewById(R.id.tv_replay);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_replay_title = (TextView) view.findViewById(R.id.tv_replay_title);
        tv_replay_content = (TextView) view.findViewById(R.id.tv_replay_content);
        return view;
    }


    @Override
    public void showData(int position, final CircleCommZanEntity itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.studenthead_fail_bg)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.studenthead_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.studenthead_fail_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(itemData.getUserId()), img_teacher_head, options);

        if (itemData.getTime() != null && itemData.getTime().length() > 0) {
            String[] str = itemData.getTime().split("T");
            //2016-04-22 16:32:50
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_e = dfs.format(new Date());
            String date_b = str[0] + " " + str[1];
            try {
                Date begin = dfs.parse(date_b);
                Date end = dfs.parse(date_e);
                long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                long month1= between / (30*24 * 3600);
                long day1 = between / (24 * 3600);
                long hour1 = between / 3600;
                long minute1 = between / 60;
                if (minute1 == 0) {
                    tv_time.setText("刚刚");
                } else if (minute1 > 0 && minute1 < 60) {
                    tv_time.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    tv_time.setText(hour1 + "小时前");
                } else if (day1 <30) {
                    tv_time.setText(day1 + "天前");
                } else if (month1<12){
                    tv_time.setText(month1 + "月前");
                }else {
                    tv_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (itemData.getUserType().equals("3")) {
            img_teacher_icon.setVisibility(View.VISIBLE);
        } else {
            img_teacher_icon.setVisibility(View.INVISIBLE);
        }


        img_teacher_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                    circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });


        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                    circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });

        tv_name.setText(itemData.getUserName());
        /*if (null != info.get("TopicTitle") && !info.get("TopicTitle").toString().equals("") && !info.get("TopicTitle").toString().equals("null")) {
            FaXianParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString(), info.get("TopicTitle").toString(), info.get("TopicID").toString());
        } else {
            ParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString());
        }*/
        ParseEmojiMsgUtil.getExpressionString(context, itemData.getId(), tv_replay_content, itemData.getContent());
//        tv_replay_content.setText(itemData.getContent());

        if (itemData.getMessageType().equals("1")) {/**********评论列表***********/
            if (itemData.getUserId().equals(itemData.getDistributorID())) {
                tv_replay.setVisibility(View.GONE);
                tv_me.setVisibility(View.VISIBLE);
                if (Integer.parseInt(itemData.getReplyId()) > 0) {
                    int soureceLength = itemData.getReplayName().length();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("回复");
                    stringBuffer.append("@");
                    stringBuffer.append(itemData.getReplayName());
                    stringBuffer.append(" " + itemData.getTitle());
                    SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
                    style.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                                circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 5);
                            }
                        }
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            // 去掉下划线
                            ds.setUnderlineText(false);
                            ds.setColor(context.getResources().getColor(R.color.bg_daoliu_yellow_two));
                        }
                    }, 2, soureceLength + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_replay_title.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
                    tv_replay_title.setText(style);
                } else {
                    tv_replay_title.setText(itemData.getTitle());
                }
            } else {
                tv_replay_title.setText(itemData.getTitle());
                tv_replay.setVisibility(View.VISIBLE);
                tv_me.setVisibility(View.GONE);
            }
        } else { /**********点赞列表***********/
            tv_replay.setVisibility(View.GONE);
            if (itemData.getUserId().equals(itemData.getDistributorID())) {
                int soureceLength = itemData.getReplayName().length();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("赞了");
                stringBuffer.append("@");
                stringBuffer.append(itemData.getReplayName());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
//                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.bg_daoliu_yellow_two)), 2, soureceLength + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                            circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 3);
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        // 去掉下划线
                        ds.setUnderlineText(false);
                        ds.setColor(context.getResources().getColor(R.color.bg_balck_three));
                    }
                }, 2, soureceLength + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_replay_title.setText(style);
            } else {
                tv_replay_title.setText("赞了你");
            }
        }


        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                    circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });


        tv_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleCommZanEntityOnClassifyPostionClickListener != null) {
                    circleCommZanEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 4);
                }
            }
        });
    }

    public static void setCircleCommZanEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<CircleCommZanEntity> circleCommZanEntityOnClassifyPostionClickListener) {
        CircleCommentZanViewOneHolder.circleCommZanEntityOnClassifyPostionClickListener = circleCommZanEntityOnClassifyPostionClickListener;
    }
}

