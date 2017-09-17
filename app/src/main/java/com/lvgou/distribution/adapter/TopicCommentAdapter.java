package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.PersonalCenterActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.DynamicDetailsPresenter;
import com.lvgou.distribution.presenter.TopicDetailsPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class TopicCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CircleCommentBean> circleCommentBeanList;
    TopicDetailsPresenter topicDetailsPresenter;
    CircleCommentCallBack circleCommentCallBack;
    private String distributorid;

    public void setListener(CircleCommentCallBack circleCommentCallBack) {
        this.circleCommentCallBack = circleCommentCallBack;
    }

    public TopicCommentAdapter(Context context, TopicDetailsPresenter topicDetailsPresenter) {
        circleCommentBeanList = new ArrayList<>();
        mContext = context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        this.topicDetailsPresenter = topicDetailsPresenter;
    }

    public void setcircleCommentData(List<CircleCommentBean> circleCommentBeanList) {
        this.circleCommentBeanList.addAll(circleCommentBeanList);
    }

    public List<CircleCommentBean> getcircleCommentData() {
        return circleCommentBeanList;
    }

    public void addcircleCommentData(CircleCommentBean circleCommentBean) {
//        this.circleCommentBeanList.add(circleCommentBean);
        List<CircleCommentBean> circleCommentBeanList2 = new ArrayList<>();
        circleCommentBeanList2.addAll(circleCommentBeanList);
        this.circleCommentBeanList.clear();
        this.circleCommentBeanList.add(circleCommentBean);
        this.circleCommentBeanList.addAll(circleCommentBeanList2);
    }

    @Override
    public int getCount() {
        return circleCommentBeanList.size();
    }

    @Override
    public CircleCommentBean getItem(int position) {
        return circleCommentBeanList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.circle_comment_item, null);
            viewHolder.ll_contentView = (LinearLayout) convertView.findViewById(R.id.ll_contentView);
            viewHolder.img_head_pic = (CircleImageView) convertView.findViewById(R.id.img_head_pic);
            viewHolder.img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
            viewHolder.txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);
            viewHolder.txt_layer = (TextView) convertView.findViewById(R.id.txt_layer);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
            viewHolder.txt_comment_content = (TextView) convertView.findViewById(R.id.txt_comment_content);
            viewHolder.img_reply = (ImageView) convertView.findViewById(R.id.img_reply);
            viewHolder.layout_comment = (LinearLayout) convertView.findViewById(R.id.layout_comment);
            viewHolder.img_del = (ImageView) convertView.findViewById(R.id.img_del);
//            viewHolder.img_sex = (ImageView) convertView.findViewById(R.id.img_sex);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.img_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleCommentCallBack.showComment(circleCommentBeanList.get(position), 1, -1);
            }
        });
        Log.e("akjsdhfkjshs", "-------------" + circleCommentBeanList);
        String path = ImageUtils.getInstance().getPath(String.valueOf(circleCommentBeanList.get(position).getDistributorID()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片


        ImageLoader.getInstance().displayImage(path, viewHolder.img_head_pic, options);
        /*if (circleCommentBeanList.get(position).getSex().equals("1")) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_man);
        } else if (circleCommentBeanList.get(position).getSex().equals("2")) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_woman);
        }*/
        if (distributorid.equals(String.valueOf(circleCommentBeanList.get(position).getDistributorID()))) {
            //如果是自己
            viewHolder.img_del.setVisibility(View.VISIBLE);
            viewHolder.img_reply.setVisibility(View.GONE);
        } else {
            viewHolder.img_del.setVisibility(View.GONE);
            viewHolder.img_reply.setVisibility(View.VISIBLE);
        }
        viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleCommentCallBack.showDelPopWindow(circleCommentBeanList.get(position).getID(), -1, position);
            }
        });
        viewHolder.img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleCommentBeanList.get(position).getUserType() == 3) {
                    Intent intent1 = new Intent(mContext, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", circleCommentBeanList.get(position).getDistributorID()+"");
                    mContext.startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(mContext, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", circleCommentBeanList.get(position).getDistributorID()+"");
                    mContext.startActivity(intent1);
                }
            }
        });

        if (circleCommentBeanList.get(position).getUserType() == 3) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        } else if (circleCommentBeanList.get(position).getUserType() == 2) {
           /* if(circleCommentBeanList.get(position).getIsRZ()==1){
                viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
            }else{
                viewHolder.img_teacher_label.setVisibility(View.GONE);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            }*/
            viewHolder.img_teacher_label.setVisibility(View.GONE);
            viewHolder.txt_user_name.setTextColor(Color.parseColor("#7b7b7b"));
        } else if (circleCommentBeanList.get(position).getUserType() == 1) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        }
        viewHolder.txt_user_name.setText(circleCommentBeanList.get(position).getDistributorName());
        viewHolder.txt_layer.setText((position + 1) + "楼");
        viewHolder.txt_comment_content.setText(circleCommentBeanList.get(position).getContent());
        if (circleCommentBeanList.get(position).getCreateTime() != null && circleCommentBeanList.get(position).getCreateTime().length() > 0) {
            String[] str = circleCommentBeanList.get(position).getCreateTime().split("T");
            //2016-04-22 16:32:50
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_e = dfs.format(new Date());
            String date_b = str[0] + " " + str[1];
            try {
                Date begin = dfs.parse(date_b);
                Date end = dfs.parse(date_e);
                long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                long month1 = between / (30 * 24 * 3600);
                long day1 = between / (24 * 3600);
                long hour1 = between / 3600;
                long minute1 = between / 60;
                if (minute1 == 0) {
                    viewHolder.txt_issue_time.setText("刚刚");
                } else if (minute1 < 60) {
                    viewHolder.txt_issue_time.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    viewHolder.txt_issue_time.setText(hour1 + "小时前");
                } else if (day1 < 30) {
                    viewHolder.txt_issue_time.setText(day1 + "天前");
                } else if (month1 < 12) {
                    viewHolder.txt_issue_time.setText(month1 + "月前");
                } else {
                    viewHolder.txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*viewHolder.ll_contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (distributorid.equals(String.valueOf(circleCommentBeanList.get(position).getDistributorID()))) {
                        circleCommentCallBack.showDelPopWindow(circleCommentBeanList.get(position).getID(), -1, position);
                    }
                }
            });*/
            viewHolder.layout_comment.removeAllViews();
            if (circleCommentBeanList.get(position).getItem_circleCommentBeans() != null && circleCommentBeanList.get(position).getItem_circleCommentBeans().size() > 0) {
                List<CircleCommentBean> list = circleCommentBeanList.get(position).getItem_circleCommentBeans();
                for (int i = 0; i < list.size(); i++) {
                    int length = 0;
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(list.get(i).getDistributorName());
                    stringBuffer.append(":");
                    if (list.get(i).getReplyDistributorID() > 0) {
                        if (list.get(i).getReplyDistributorID() == circleCommentBeanList.get(position).getDistributorID()) {
                            stringBuffer.append("");
                            length = 0;
                        } else {
                            stringBuffer.append("@");
                            stringBuffer.append(list.get(i).getReplyDistributorName());
                            length = list.get(i).getReplyDistributorName().length() + 1;
                        }
                    }
                    stringBuffer.append(list.get(i).getContent());
                    SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
//                style.setSpan(new ForegroundColorSpan(Color.RED), 1, soureceLength + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            MyToast.makeText(mContext, "点击", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_one));
                            // 去掉下划线
                            ds.setUnderlineText(false);
                        }
                    }, 0, list.get(i).getDistributorName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            MyToast.makeText(mContext, "点击", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_one));
                            // 去掉下划线
                            ds.setUnderlineText(false);
                        }
                    }, list.get(i).getDistributorName().length() + 1, list.get(i).getDistributorName().length() + 1 + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    View linearLayout = LayoutInflater.from(mContext).inflate(R.layout.fengwen_comment_list_item_sublevel, null);
                    TextView textView = (TextView) linearLayout.findViewById(R.id.txt_comment_sublevel);
                    textView.setText(style);
                    linearLayout.setTag(i);
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = (int) v.getTag();
                            if (distributorid.equals(String.valueOf(circleCommentBeanList.get(position).getItem_circleCommentBeans().get(index).getDistributorID()))) {
                                circleCommentCallBack.showDelPopWindow(circleCommentBeanList.get(position).getItem_circleCommentBeans().get(index).getID(), index, position);
                            } else {
                                circleCommentCallBack.showComment(circleCommentBeanList.get(position), 2, index);
                            }
                        }
                    });
                    viewHolder.layout_comment.addView(linearLayout);
                }

            }
        }
        return convertView;
    }

    /**
     * 左边布局
     */
    public class ViewHolder {
        LinearLayout ll_contentView;
        CircleImageView img_head_pic;
        ImageView img_teacher_label;
        TextView txt_user_name;
        TextView txt_layer;
        TextView txt_issue_time;
        ImageView img_reply;
        LinearLayout layout_comment;
        TextView txt_comment_content;
        //        ImageView img_sex;
        ImageView img_del;
    }

    public String getPath(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        return path;
    }

    public interface CircleCommentCallBack {
        public void showComment(CircleCommentBean circleCommentBean, int layer, int position);

        public void showDelPopWindow(String talkcommentId, int child_level, int del_layer);
    }
}
