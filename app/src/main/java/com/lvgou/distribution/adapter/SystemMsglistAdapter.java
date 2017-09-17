package com.lvgou.distribution.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DynamicDetailsActivity;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.SystemMessageAcitivity;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.SysMsgData;
import com.lvgou.distribution.bean.SystemMessageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.xdroid.common.utils.PreferenceHelper;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Snow on 2016/8/11
 */
public class SystemMsglistAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<SystemMessageBean> systemMessageBeans;
    private String distributorid;
    private boolean msg_more_flag = true;
    private int txt_content_line;
    private List<SysMsgData> textViewList;

    public SystemMsglistAdapter(Context context) {
        this.mContext = context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        inflater = LayoutInflater.from(context);
        systemMessageBeans = new ArrayList<>();
        textViewList = new ArrayList<>();
    }

    public void setListData(List<SystemMessageBean> systemMsgeBeans) {
        this.systemMessageBeans.addAll(systemMsgeBeans);
        textViewList.clear();
        for (int i = 0; i < systemMessageBeans.size(); i++) {
            SysMsgData sysMsgData = new SysMsgData();
            textViewList.add(sysMsgData);
        }
    }

    public List<SystemMessageBean> getListData() {
        return systemMessageBeans;
    }

    @Override
    public int getCount() {
        return systemMessageBeans.size();
    }

    @Override
    public SystemMessageBean getItem(int position) {
        return systemMessageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    ViewHolder viewHolder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_system_msg_view, null);
            viewHolder.img_msg_icon = (ImageView) convertView.findViewById(R.id.img_msg_icon);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
            viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            viewHolder.img_msg_down = (ImageView) convertView.findViewById(R.id.img_msg_down);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (systemMessageBeans.get(position) != null) {
            if (systemMessageBeans.get(position).getIsRead() == 1) {
                viewHolder.img_msg_icon.setImageResource(R.mipmap.icon_sysmsg_read);
                viewHolder.txt_content.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
                viewHolder.txt_title.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            } else {
                viewHolder.img_msg_icon.setImageResource(R.mipmap.icon_sysmsg_readed);
                viewHolder.txt_content.setTextColor(mContext.getResources().getColor(R.color.text_color_999999));
                viewHolder.txt_title.setTextColor(mContext.getResources().getColor(R.color.text_color_999999));
            }
            viewHolder.txt_content.setText(systemMessageBeans.get(position).getContent());
            viewHolder.txt_title.setText(systemMessageBeans.get(position).getTitle());
            textViewList.get(position).setTxt_content(viewHolder.txt_content);
            textViewList.get(position).setImg_up_down(viewHolder.img_msg_down);
            ViewTreeObserver vto = textViewList.get(position).getTxt_content().getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    txt_content_line = textViewList.get(position).getTxt_content().getLineCount();
                    textViewList.get(position).getTxt_content().getViewTreeObserver().removeOnPreDrawListener(this);
//                    viewHolder.txt_content.setTag(txt_content_line);
//                    viewHolder.img_msg_down.setTag(viewHolder.txt_content);
                    textViewList.get(position).setText_lines(txt_content_line);
                    if (txt_content_line > 2) {
                        textViewList.get(position).getTxt_content().setMaxLines(2);
                        msg_more_flag = true;
                        textViewList.get(position).getImg_up_down().setImageResource(R.mipmap.xiala_down);
                        textViewList.get(position).getImg_up_down().setVisibility(View.VISIBLE);
                    } else {
                        textViewList.get(position).getImg_up_down().setVisibility(View.GONE);
                    }
                    return false;
                }

            });
            if (systemMessageBeans.get(position).getCreateTime() != null && systemMessageBeans.get(position).getCreateTime().length() > 0) {
                String[] str = systemMessageBeans.get(position).getCreateTime().split("T");
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
            viewHolder.img_msg_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v.getTag();
                    if (msg_more_flag) {
                        textViewList.get(position).getTxt_content().setMaxLines(textViewList.get(position).getText_lines());
                        msg_more_flag = false;
                        textViewList.get(position).getImg_up_down().setImageResource(R.mipmap.shouqi_up);
                    } else {
                        textViewList.get(position).getTxt_content().setMaxLines(2);
                        msg_more_flag = true;
                        textViewList.get(position).getImg_up_down().setImageResource(R.mipmap.xiala_down);
                    }

                }
            });
        }
        return convertView;
    }


    /**
     * 左边布局
     */
    public class ViewHolder {
        ImageView img_msg_icon;
        TextView txt_issue_time;
        TextView txt_title;
        TextView txt_content;
        ImageView img_msg_down;
    }
}