package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MyFansActivity;
import com.lvgou.distribution.activity.MyFocusActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.UseFollowPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.UseFollowView;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyFansAdapter extends BaseAdapter implements UseFollowView {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private String distributorId;
    private UseFollowPresenter useFollowPresenter;

    public MyFansAdapter(Context _context) {
        this.context = _context;
        useFollowPresenter = new UseFollowPresenter(this);
        distributorId = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
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
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.myfocus_list_item);
        ImageView image = viewHolder.getView(R.id.image, ImageView.class);//头像
        ImageView img_user_identify = viewHolder.getView(R.id.img_user_identify, ImageView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
//        ImageView img_sex = viewHolder.getView(R.id.img_sex, ImageView.class);
        TextView tv_fengwen_num = viewHolder.getView(R.id.tv_fengwen_num, TextView.class);
        TextView tv_fans_num = viewHolder.getView(R.id.tv_fans_num, TextView.class);
        final TextView attension = viewHolder.getView(R.id.attension, TextView.class);
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("ID").toString())).error(R.mipmap.teacher_default_head).into(image);
        tv_name.setText(info.get("RealName").toString());
        tv_fengwen_num.setText(info.get("FengwenCount").toString());
        tv_fans_num.setText(info.get("FansCount").toString());
        Log.e("kjhaskdfj", "--------" + info.get("TuanBi").toString());

        if (!distributorId.equals(info.get("ID").toString())) {
            if (info.get("TuanBi").toString().equals("1")) {
                attension.setVisibility(View.VISIBLE);
            } else {
                attension.setVisibility(View.GONE);
            }
        } else {
            attension.setVisibility(View.GONE);
        }


   /*     if (info.get("Attr").toString().equals("1")) {
            img_sex.setBackgroundResource(R.mipmap.icon_man);
        } else {
            img_sex.setBackgroundResource(R.mipmap.icon_woman);
        }*/
        if (info.get("UserType").toString().equals("3")) {
            img_user_identify.setImageResource(R.mipmap.bg_tecaher_authentication);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if (info.get("UserType").toString().equals("2")) {
            /*if (info.get("State").toString().equals("5")) {
                img_user_identify.setImageResource(R.mipmap.icon_certified);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else {
                img_user_identify.setVisibility(View.GONE);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }*/
            img_user_identify.setVisibility(View.GONE);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
        } else if (info.get("UserType").toString().equals("1")) {
            img_user_identify.setImageResource(R.mipmap.icon_official_certified);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }
        attension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mposi = position;
                mattension = attension;
                String seeDistributorId = info.get("ID").toString();
                String sign = TGmd5.getMD5("" + distributorId + seeDistributorId);
                ((MyFansActivity) context).showLoadingProgressDialog(context, "");
                useFollowPresenter.useFollow(distributorId, seeDistributorId, sign);
            }
        });

        return viewHolder.convertView;
    }

    private int mposi = 0;
    private TextView mattension;

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }

    @Override
    public void OnUseFollowSuccCallBack(String state, String respanse) {
        //关注成功
        ((MyFansActivity) context).closeLoadingProgressDialog();
        mattension.setVisibility(View.GONE);
        HashMap<String, Object> hashMap = list.get(mposi);
        list.get(mposi).put("TuanBi", "2");
        hashMap.put("TuanBi", "2");
        adapterToFra.doSomeThing(hashMap);

    }

    @Override
    public void OnUseFollowFialCallBack(String state, String respanse) {
        //关注失败
        ((MyFansActivity) context).closeLoadingProgressDialog();
    }

    @Override
    public void closeUseFollowProgress() {

    }
}