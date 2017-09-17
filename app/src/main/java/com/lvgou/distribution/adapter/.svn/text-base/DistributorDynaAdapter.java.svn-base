package com.lvgou.distribution.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.FengwenDelPresenter;
import com.lvgou.distribution.presenter.FengwenUnZanPresenter;
import com.lvgou.distribution.presenter.FengwenZanPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.FengwenDelView;
import com.lvgou.distribution.view.FengwenUnZanView;
import com.lvgou.distribution.view.FengwenZanView;
import com.lvgou.distribution.widget.CellLayout;
import com.wx.goodview.GoodView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DistributorDynaAdapter extends BaseAdapter implements FengwenZanView, FengwenUnZanView, FengwenDelView {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    GoodView goodView;
    private FengwenZanPresenter fengwenZanPresenter;
    private FengwenUnZanPresenter fengwenUnZanPresenter;
    private FengwenDelPresenter fengwenDelPresenter;
    private boolean isMe = false;

    public DistributorDynaAdapter(Context _context, boolean isMe) {
        this.context = _context;
        goodView = new GoodView(context);
        this.isMe = isMe;
        fengwenZanPresenter = new FengwenZanPresenter(this);
        fengwenUnZanPresenter = new FengwenUnZanPresenter(this);
        fengwenDelPresenter = new FengwenDelPresenter(this);
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        Log.e("kjaghsdjks", "------------" + info);
//        State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.teacher_dynamic_item);
        TextView summary = viewHolder.getView(R.id.summary, TextView.class);//内容
        CellLayout layout = viewHolder.getView(R.id.layout, CellLayout.class);//图片
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);//时间
        final TextView tv_ding_num = viewHolder.getView(R.id.tv_ding_num, TextView.class);//赞的数量
        TextView tv_pinglun_num = viewHolder.getView(R.id.tv_pinglun_num, TextView.class);//评论数量
        RelativeLayout rl_delete = viewHolder.getView(R.id.rl_delete, RelativeLayout.class);//删除
        RelativeLayout rl_ding = viewHolder.getView(R.id.rl_ding, RelativeLayout.class);//点赞
        RelativeLayout rl_pinglun = viewHolder.getView(R.id.rl_pinglun, RelativeLayout.class);//评论
        final ImageView im_ding = viewHolder.getView(R.id.im_ding, ImageView.class);//点在图片
        TextView tv_address = viewHolder.getView(R.id.tv_address, TextView.class);//地理位置
        if (isMe) {
            //如果是自己
            rl_delete.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_pinglun.getLayoutParams();
            lp.setMargins(0, 0, 50, 0);
            rl_pinglun.setLayoutParams(lp);
        } else {
            rl_delete.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_pinglun.getLayoutParams();
            lp.setMargins(0, 0, 15, 0);
            rl_pinglun.setLayoutParams(lp);
        }
        if (info.get("CurrentLocation") == null || info.get("CurrentLocation").toString().equals("") || info.get("CurrentLocation").toString().equals("null")) {
            tv_address.setVisibility(View.GONE);
        } else {
            tv_address.setVisibility(View.VISIBLE);
            tv_address.setText(info.get("CurrentLocation").toString());
        }
        if (null != info.get("TopicTitle") && !info.get("TopicTitle").toString().equals("") && !info.get("TopicTitle").toString().equals("null")) {
            FaXianParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString(), info.get("TopicTitle").toString(), info.get("TopicID").toString());
        } else {
            ParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString());
        }
//        summary.setText(info.get("Title").toString());
        tv_ding_num.setText(info.get("ZanCount").toString().equals("0") ? "赞" : info.get("ZanCount").toString());
        tv_pinglun_num.setText(info.get("CommentCount").toString().equals("0") ? "评论" : info.get("CommentCount").toString());

        String[] str = info.get("CreateTime").toString().split("T");
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
                tv_time.setText("刚刚");
            } else if (minute1 < 60) {
                tv_time.setText(minute1 + "分钟前");
            } else if (hour1 < 24) {
                tv_time.setText(hour1 + "小时前");
            } else if (day1 < 30) {
                tv_time.setText(day1 + "天前");
            } else if (month1 < 12) {
                tv_time.setText(month1 + "月前");
            } else {
                tv_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String picJson = info.get("PicJson").toString();
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        dataList.clear();
        try {
            JSONArray jsonArray = new JSONArray(picJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = new JSONObject(jsonArray.get(i).toString());
//                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataList.add(map1);
            }
            if (dataList.size() == 0) {
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                layout.setData(dataList, context);
            }
        } catch (JSONException e) {
        }
        if (info.get("Zaned").toString().equals("0")) {
            //表示没有赞
            im_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
            tv_ding_num.setTextColor(Color.parseColor("#bababa"));
        } else if (info.get("Zaned").toString().equals("1")) {
            //表示已经赞赞
            im_ding.setBackgroundResource(R.mipmap.ding_select_icon);
            tv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
        }
        layout.setOnItemClickListener(new CellLayout.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<String> images, int position) {
                Intent intent = new Intent(context, ImagePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls", images);
                bundle.putInt("image_index", position);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        rl_ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶
                opePosition = position;
                mim_ding = im_ding;
                mtv_ding_num = tv_ding_num;
                String talkId = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

                if (info.get("Zaned").toString().equals("0")) {
                    //表示没有赞
                    String sign = TGmd5.getMD5("" + distributorid + talkId);
                    ((DistributorHomeActivity) context).showLoadingProgressDialog(context, "");
                    fengwenZanPresenter.fengwenZan(distributorid, talkId, sign);
                } else if (info.get("Zaned").toString().equals("1")) {
                    //如果已经赞了
                    String sign = TGmd5.getMD5("" + distributorid + talkId);
                    ((DistributorHomeActivity) context).showLoadingProgressDialog(context, "");
                    fengwenUnZanPresenter.fengwenUnZan(distributorid, talkId, sign);

                }
            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除蜂文

                LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
                TextView text1 = (TextView) view.findViewById(R.id.text1);
                TextView yes = (TextView) view.findViewById(R.id.yes);
                TextView cancle = (TextView) view.findViewById(R.id.cancle);
                final AlertDialog mAlertDialog = builder.create();
                mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                mAlertDialog.show();
                mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                mAlertDialog.getWindow().setContentView(view, pm);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        opePosition = position;
                        String talkId = info.get("ID").toString();
                        String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                        String sign = TGmd5.getMD5("" + distributorid + talkId);
                        ((DistributorHomeActivity) context).showLoadingProgressDialog(context, "");
                        fengwenDelPresenter.fengwenDel(distributorid, talkId, sign);
                        mAlertDialog.dismiss();
                    }
                });


            }
        });

        return viewHolder.convertView;
    }

    private ImageView mim_ding;
    private TextView mtv_ding_num;
    private int opePosition = 0;

    @Override
    public void OnFengwenZanSuccCallBack(String state, String respanse) {
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        int dingnum = Integer.parseInt(stringObjectHashMap.get("ZanCount").toString()) + 1;
        mim_ding.setBackgroundResource(R.mipmap.ding_select_icon);
        mtv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
        mtv_ding_num.setText(dingnum + "");
        list.get(opePosition).put("Zaned", "1");
        list.get(opePosition).put("ZanCount", dingnum + "");
        stringObjectHashMap.put("Zaned", "1");
        stringObjectHashMap.put("ZanCount", dingnum + "");
        adapterToFra.doSomeThing(stringObjectHashMap);
        goodView.setImage(R.mipmap.ding_select_icon);
        goodView.show(mim_ding);
    }

    @Override
    public void OnFengwenZanFialCallBack(String state, String respanse) {
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFengwenZanProgress() {

    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }

    @Override
    public void OnFengwenUnZanSuccCallBack(String state, String respanse) {
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
        //取消赞
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        int dingnum = Integer.parseInt(stringObjectHashMap.get("ZanCount").toString()) - 1;
        mim_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
        mtv_ding_num.setTextColor(Color.parseColor("#bababa"));
        mtv_ding_num.setText(dingnum > 0 ? dingnum + "" : "赞");
        list.get(opePosition).put("Zaned", "0");
        list.get(opePosition).put("ZanCount", dingnum + "");
        stringObjectHashMap.put("Zaned", "0");
        stringObjectHashMap.put("ZanCount", dingnum + "");
        adapterToFra.doSomeThing(stringObjectHashMap);
        goodView.setImage(R.mipmap.ding_normal_icon);
        goodView.show(mim_ding);
    }

    @Override
    public void OnFengwenUnZanFialCallBack(String state, String respanse) {
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFengwenUnZanProgress() {

    }

    @Override
    public void OnFengwenDelSuccCallBack(String state, String respanse) {
        //删除蜂文
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
//        list.remove(opePosition);
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        stringObjectHashMap.put("ZanCount", "delete");
        adapterToFra.doSomeThing(stringObjectHashMap);
//        TeacherDynaAdapter.this.notifyDataSetChanged();
    }

    @Override
    public void OnFengwenDelFialCallBack(String state, String respanse) {
        ((DistributorHomeActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFengwenDelProgress() {

    }
}