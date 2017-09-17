package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.bean.MyCommentListBean;
import com.lvgou.distribution.bean.MyZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.xdroid.common.utils.PreferenceHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class MyCommentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyCommentListBean> mycommentList;
    private PersonalCircleListPresenter personalCircleListPresenter;
    private String distributorid;

    public MyCommentListAdapter(Context context, List<MyCommentListBean> list) {
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        mContext = context;
        mycommentList = list;
    }

    public void setPersonal(PersonalCircleListPresenter personal) {
        personalCircleListPresenter = personal;
    }

    public void setDataList(List<MyCommentListBean> list) {
        mycommentList.addAll(list);
    }

    public List<MyCommentListBean> getMycommentList() {
        return mycommentList;
    }

    @Override
    public int getCount() {
        return mycommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mycommentList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mycomment_itemlist_view, null);
            viewHolder.ll_contentView = (LinearLayout) convertView.findViewById(R.id.ll_contentView);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_time);
            viewHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
            viewHolder.txt_fengwen_title = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mycommentList.get(position).getCreateTime() != null && mycommentList.get(position).getCreateTime().length() > 0) {
            String[] str = mycommentList.get(position).getCreateTime().split("T");
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
                    viewHolder.txt_issue_time.setText("刚刚");
                } else if (minute1 < 60) {
                    viewHolder.txt_issue_time.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    viewHolder.txt_issue_time.setText(hour1 + "小时前");
                } else if (day1 < 30) {
                    viewHolder.txt_issue_time.setText(day1 + "天前");
                } else if (month1<12){
                    viewHolder.txt_issue_time.setText(month1 + "月前");
                }else {
                    viewHolder.txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (mycommentList.get(position).getReplyDistributorID() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("回复");
            stringBuffer.append("@");
            stringBuffer.append(mycommentList.get(position).getReplyDistributorName());
            stringBuffer.append(mycommentList.get(position).getContent());
            SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
            style.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(mContext, UserPersonalCenterActivity.class);
                    intent.putExtra("distributorid", mycommentList.get(position).getReplyDistributorID());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_two));
                    // 去掉下划线
                    ds.setUnderlineText(false);
                }
            }, 3, mycommentList.get(position).getReplyDistributorName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.txt_comment.setText(style);
        } else {
            viewHolder.txt_comment.setText(mycommentList.get(position).getContent());
        }


        viewHolder.txt_fengwen_title.setText(mycommentList.get(position).getFengwenTitle());
        viewHolder.txt_fengwen_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign_detial = TGmd5.getMD5(distributorid + mycommentList.get(position).getFengwenID());
                personalCircleListPresenter.getTalkDetial(distributorid, mycommentList.get(position).getFengwenID(), sign_detial);
            }
        });
        viewHolder.ll_contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign_detial = TGmd5.getMD5(distributorid + mycommentList.get(position).getFengwenID());
                personalCircleListPresenter.getTalkDetial(distributorid, mycommentList.get(position).getFengwenID(), sign_detial);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        LinearLayout ll_contentView;
        TextView txt_issue_time;
        TextView txt_comment;
        TextView txt_fengwen_title;
    }
}
