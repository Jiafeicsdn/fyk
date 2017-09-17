package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.ScreenUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/9.
 */

public class BoutiqueFragmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private int width = 0;

    public BoutiqueFragmentAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list, int width) {
        this.list = list;
        this.width = width;
        BoutiqueFragmentAdapter.this.notifyDataSetChanged();
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
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.boutique_fragment_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        ViewGroup.LayoutParams para = im_picture.getLayoutParams();
        int iheight = (int) ((width - ScreenUtils.dpToPx(context, 40)) * 34 / 67);

        para.height = iheight;// 控件的高强制设成
        im_picture.setLayoutParams(para);
        Glide.with(context).load(Url.ROOT + info.get("PicUrl").toString()).error(R.mipmap.pictures_no).into(im_picture);
        /*TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        ImageView img_head = viewHolder.getView(R.id.img_head, ImageView.class);
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);//导游类型
        img_teacher_label.setVisibility(View.VISIBLE);
        TextView tv_tuanbi_number = viewHolder.getView(R.id.tv_tuanbi_number, TextView.class);
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head);*/
        return viewHolder.convertView;
    }
}