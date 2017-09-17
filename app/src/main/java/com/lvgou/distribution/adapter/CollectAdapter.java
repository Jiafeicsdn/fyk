package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.bean.CollectListBean;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<CollectListBean> collectlist;

    public CollectAdapter(Context context, List<CollectListBean> list) {
        mContext = context;
        this.collectlist = list;
    }

    public void setListData(List<CollectListBean> list) {
        collectlist.addAll(list);
    }

    public List<CollectListBean> getCollectList() {
        return collectlist;
    }

    @Override
    public int getCount() {
        return collectlist.size();
    }

    @Override
    public Object getItem(int position) {
        return collectlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.collect_list_view, null);
            viewHolder.rl_contentView=(RelativeLayout)convertView.findViewById(R.id.rl_contentView);
            viewHolder.img_head_pic = (CircleImageView) convertView.findViewById(R.id.img_head_pic);
            viewHolder.img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
            viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String path = ImageUtils.getInstance().getPath(String.valueOf(collectlist.get(position).getFengwenDistributorID()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片


        ImageLoader.getInstance().displayImage(path, viewHolder.img_head_pic, options);
//        if (collectlist.get(position).getFengwenUserType() == 3) {
//            viewHolder.img_teacher_label.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.img_teacher_label.setVisibility(View.GONE);
//        }

        if("1".equals(collectlist.get(position).getFengwenIsRZ())){
            viewHolder.img_teacher_label.setVisibility(View.VISIBLE);
            if (collectlist.get(position).getFengwenUserType() == 3) {
                viewHolder.img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            } else if(collectlist.get(position).getFengwenUserType() == 2){
                viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
            }
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        }else{
            viewHolder.img_teacher_label.setVisibility(View.GONE);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
        }
        viewHolder.rl_contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(collectlist.get(position).getFengwenUserType()==1){
                    intent.setClass(mContext,NewRecomFengWenDetailsActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("talkId", collectlist.get(position).getFengwenID());
                }else {
                    intent.setClass(mContext, NewDynamicDetailsActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("talkId", collectlist.get(position).getFengwenID());
                }
                mContext.startActivity(intent);
            }
        });
        if (collectlist.get(position).getCreateTime() != null && collectlist.get(position).getCreateTime().length() > 0) {
            String[] str = collectlist.get(position).getCreateTime().split("T");
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
                }else if (month1<12){
                    viewHolder.txt_issue_time.setText(month1 + "月前");
                } else {
                    viewHolder.txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        viewHolder.txt_title.setText(collectlist.get(position).getFengwenTitle());
        viewHolder.txt_user_name.setText(collectlist.get(position).getFengwenDistributorName());
        return convertView;
    }

    private class ViewHolder {
        private RelativeLayout rl_contentView;
        private CircleImageView img_head_pic;
        private ImageView img_teacher_label;
        private TextView txt_user_name;
        private TextView txt_issue_time;
        private TextView txt_title;
    }
}
