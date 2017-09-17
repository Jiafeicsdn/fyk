package com.lvgou.distribution.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.CertificationActivity;
import com.lvgou.distribution.activity.CommitSuccessActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.FunctionUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ActBMAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public ActBMAdapter(Context _context) {
        this.context = _context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.act_bm_item);
        ImageView im_header = viewHolder.getView(R.id.im_header, ImageView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_phone_num = viewHolder.getView(R.id.tv_phone_num, TextView.class);
        ImageView im_call = viewHolder.getView(R.id.im_call, ImageView.class);
        tv_name.setText(info.get("DistributorName").toString());
        tv_phone_num.setText("联系方式：" + info.get("Mobile").toString());
        Glide.with(context).load(Url.ROOT + info.get("ActivityTitle").toString()).error(R.mipmap.teacher_default_head).into(im_header);
        im_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCallDialog(info.get("Mobile").toString());
            }
        });
        return viewHolder.convertView;
    }

    public void showCallDialog(final String mobile) {

        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定拨打" + mobile + "吗？");
        View view_line = view.findViewById(R.id.view_line);
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
                FunctionUtils.jump2PhoneView(context, mobile);
                mAlertDialog.dismiss();
            }
        });
    }
}