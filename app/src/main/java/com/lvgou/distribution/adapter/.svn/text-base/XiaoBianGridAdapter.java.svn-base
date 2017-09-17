package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/10.
 */

public class XiaoBianGridAdapter extends BaseAdapter {
    //上下文对象
    private Context context;
    private ArrayList<HashMap<String, Object>> xiaobList;
    private final int width;

    public XiaoBianGridAdapter(Context context) {
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    public void setDatas(ArrayList<HashMap<String, Object>> xiaobList) {
        this.xiaobList = xiaobList;
        XiaoBianGridAdapter.this.notifyDataSetChanged();
    }

    public int getCount() {
        if (xiaobList == null) {
            return 0;
        } else {
            return xiaobList.size();
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
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.grid_xiaobian_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        ViewGroup.LayoutParams para = im_picture.getLayoutParams();
        int iheight = (int) ((width - ScreenUtils.dpToPx(context, 55)) * 81 / 320);
        para.height = iheight;// 控件的高强制设成
        im_picture.setLayoutParams(para);
        Glide.with(context).load(Url.ROOT + xiaobList.get(position).get("PicUrl").toString()).into(im_picture);
        return viewHolder.convertView;
    }
}
