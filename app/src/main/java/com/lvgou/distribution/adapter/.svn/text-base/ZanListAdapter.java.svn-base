package com.lvgou.distribution.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.bean.ZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ZanPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.wechat.utils.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.xdroid.common.utils.PreferenceHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ZanListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ZanListBean> zanList;
    private ZanPresenter zanListpresenter;
    private String distributorid;

    public ZanListAdapter(Context context, List<ZanListBean> zanlist, ZanPresenter zanPresenter) {
        mContext = context;
        zanList = zanlist;
        zanListpresenter = zanPresenter;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
    }

    public void setZanList(List<ZanListBean> zanlist) {
        this.zanList.addAll(zanlist);
    }

    public void addcircleZanData(ZanListBean circleZanBean) {
        this.zanList.add(circleZanBean);
    }

    public List<ZanListBean> getZanList() {
        return zanList;
    }

    @Override
    public int getCount() {
        return zanList.size();
    }

    @Override
    public Object getItem(int position) {
        return zanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.zanlist_item_view, null);
            viewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.circleImageview);
            viewHolder.img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
//            viewHolder.img_gender = (ImageView) convertView.findViewById(R.id.img_gender);
//            viewHolder.img_user_follow = (ImageView) convertView.findViewById(R.id.img_user_follow);
            viewHolder.txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);

            viewHolder.txt_createTime = (TextView) convertView.findViewById(R.id.txt_createTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
        String path = ImageUtils.getInstance().getPath(String.valueOf(zanList.get(position).getDistributorID()));
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(path, viewHolder.circleImageView, options);
       /* if (zanList.get(position).getSex() == 1) {
            viewHolder.img_gender.setImageResource(R.mipmap.icon_man);
        } else {
            viewHolder.img_gender.setImageResource(R.mipmap.icon_woman);
        }*/
        /*if (zanList.get(position).getDistributorID().equals(distributorid)) {
            viewHolder.img_user_follow.setVisibility(View.GONE);
        } else {
            viewHolder.img_user_follow.setVisibility(View.VISIBLE);
        }
        if ("1".equals(zanList.get(position).getFengwenDistributorID())) {
            viewHolder.img_user_follow.setImageResource(R.mipmap.circle_add_follow);
        } else if ("2".equals(zanList.get(position).getFengwenDistributorID())) {
            viewHolder.img_user_follow.setImageResource(R.mipmap.yiguanzhu);
        } else {
            viewHolder.img_user_follow.setImageResource(R.mipmap.huxiang_follow);
        }*/

        if (zanList.get(position).getUserType() == 3) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        } else if (zanList.get(position).getUserType() == 2) {
            /*if (zanList.get(position).getIsRZ() == 1) {
                viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
            } else {
                viewHolder.img_teacher_label.setVisibility(View.GONE);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            }*/
            viewHolder.img_teacher_label.setVisibility(View.GONE);
            viewHolder.txt_user_name.setTextColor(Color.parseColor("#7b7b7b"));
        } else if (zanList.get(position).getUserType() == 1) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        }
        viewHolder.txt_user_name.setText(zanList.get(position).getDistributorName());
        if (zanList.get(position).getCreateTime() != null && zanList.get(position).getCreateTime().length() > 0) {
            String[] str = zanList.get(position).getCreateTime().split("T");
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
                    viewHolder.txt_createTime.setText("刚刚");
                } else if (minute1 < 60) {
                    viewHolder.txt_createTime.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    viewHolder.txt_createTime.setText(hour1 + "小时前");
                } else if (day1 < 30) {
                    viewHolder.txt_createTime.setText(day1 + "天前");
                } else if (month1 < 12) {
                    viewHolder.txt_createTime.setText(month1 + "月前");
                } else {
                    viewHolder.txt_createTime.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
       /* viewHolder.img_user_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendId = zanList.get(position).getDistributorID();
                String sign = TGmd5.getMD5(distributorid + friendId);
                if (zanList.get(position).getFengwenDistributorID().equals("1")) {
                    zanListpresenter.CircleFollow(distributorid, friendId, sign, position);
                } else {
                    showQuitDialog(String.valueOf(friendId), sign, position);

                }
            }
        });*/
        viewHolder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zanList.get(position).getUserType() == 3) {
                    Intent intent1 = new Intent(mContext, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", zanList.get(position).getDistributorID() + "");
                    mContext.startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(mContext, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", zanList.get(position).getDistributorID() + "");
                    mContext.startActivity(intent1);
                }
            }
        });
        viewHolder.txt_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zanList.get(position).getUserType() == 3) {
                    Intent intent1 = new Intent(mContext, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", zanList.get(position).getDistributorID() + "");
                    mContext.startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(mContext, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", zanList.get(position).getDistributorID() + "");
                    mContext.startActivity(intent1);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        CircleImageView circleImageView;
        ImageView img_teacher_label;
        //        ImageView img_gender;
        TextView txt_user_name;
        TextView txt_createTime;
//        ImageView img_user_follow;

    }

    /**
     * id
     * 删除取消弹框
     */
    private Dialog dialog_del_can;// 取消，删除弹框

    public void showQuitDialog(final String friendId, final String sign, final int position) {
        dialog_del_can = new Dialog(mContext, R.style.Mydialog);
        View view1 = View.inflate(mContext,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(mContext.getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                zanListpresenter.CircleUnFollow(distributorid, friendId, sign, position);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
            }
        });
        dialog_del_can.setContentView(view1);
        dialog_del_can.show();
    }
}
