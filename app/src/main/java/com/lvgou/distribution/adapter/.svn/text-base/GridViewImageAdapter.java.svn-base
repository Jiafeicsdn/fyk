package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public class GridViewImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Bitmap> list = new ArrayList<Bitmap>();


    public GridViewImageAdapter() {
        super();
    }

    /**
     * 获取列表数据
     *
     * @param list
     */
    public void setList(List<Bitmap> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public GridViewImageAdapter(Context mContext, List<Bitmap> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 1;
        } else {
            return list.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        } else if (list == null || position - 1 < 0 || position > list.size()) {
            return null;
        } else {
            return list.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_publish, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.item_grid_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isShowAddItem(position)) {
            if (Constants.IS_SHOW_ADD.equals("1")) {
                holder.iv.setImageResource(R.mipmap.baobei_add);
                holder.iv.setVisibility(View.GONE);
            } else {
                holder.iv.setImageResource(R.mipmap.baobei_add);
                holder.iv.setVisibility(View.VISIBLE);
            }
        } else {
            holder.iv.setImageBitmap(list.get(position));
        }
        return convertView;
    }

    private boolean isShowAddItem(int position) {
        int size = list == null ? 0 : list.size();
        return position == size;
    }

    class ViewHolder {
        ImageView iv;
    }
}