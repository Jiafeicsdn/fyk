package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.GamesActivity;
import com.lvgou.distribution.activity.GamesWebActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.TugouGameHitsPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.TugouGameHitsView;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/1.
 */

public class GamesAdapter extends BaseAdapter implements TugouGameHitsView {
    //上下文对象
    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private TugouGameHitsPresenter tugouGameHitsPresenter;

    public GamesAdapter(Context context) {
        this.context = context;
        tugouGameHitsPresenter = new TugouGameHitsPresenter(this);
    }

    public void setDatas(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
    }

    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public Object getItem(int item) {
        return item;
    }

    public long getItemId(int id) {
        return id;
    }

    //创建View方法
    public View getView(int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.games_list_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_start = viewHolder.getView(R.id.tv_start, TextView.class);
        TextView tv_num = viewHolder.getView(R.id.tv_num, TextView.class);
        tv_name.setText(info.get("Title").toString());
        tv_content.setText(info.get("Intro").toString());
        if (Integer.parseInt(info.get("Other").toString()) > 10000) {
            Double aaa = Integer.parseInt(info.get("Other").toString()) / 10000.0;
            tv_num.setText(aaa + "万人");
        } else {
            tv_num.setText(info.get("Other").toString() + "人");
        }


        Glide.with(context).load(Url.ROOT + info.get("PicUrl").toString()).into(im_picture);
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameid = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + gameid);
                ((GamesActivity) context).showLoadingProgressDialog(context, "");
                title = info.get("Title").toString();
                linkUrl = info.get("LinkUrl").toString();
                tugouGameHitsPresenter.tugouGamesHits(distributorid, gameid, sign);
            }
        });
        return viewHolder.convertView;
    }

    private String linkUrl = "";
    private String title = "";

    @Override
    public void OnTugouGameHitsSuccCallBack(String state, String respanse) {
        ((GamesActivity) context).closeLoadingProgressDialog();
        Intent intent = new Intent(context, GamesWebActivity.class);
        intent.putExtra("LinkUrl", linkUrl);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void OnTugouGameHitsFialCallBack(String state, String respanse) {
        ((GamesActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeTugouGameHitsProgress() {

    }
}