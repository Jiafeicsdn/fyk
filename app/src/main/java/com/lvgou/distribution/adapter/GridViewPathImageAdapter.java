package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Snow on 2016/7/18 0018.
 */
public class GridViewPathImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;


    public GridViewPathImageAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    public GridViewPathImageAdapter() {
    }

    /**
     * 获取列表数据
     *
     * @param list
     */
    public void setList(List<String> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 1;
        } else if (lists.size() == 4) {
            return 4;
        } else {
            return lists.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        if (lists != null && lists.size() == 4) {
            return lists.get(position);
        } else if (lists == null || position - 1 < 0 || position > lists.size()) {
            return null;
        } else {
            return lists.get(position - 1);
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
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_publish, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_grid_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (isShowAddItem(position)) {
            if (Constants.IS_SHOW_ADD.equals("1")) {
                holder.iv.setVisibility(View.GONE);
            } else {
                ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.baobei_add, holder.iv);
                holder.iv.setVisibility(View.VISIBLE);
            }
        } else {
            holder.options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)                     //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .build();
            ImageLoader.getInstance().displayImage(Url.ROOT + lists.get(position), holder.iv, holder.options);

        }
        return convertView;
    }

    private boolean isShowAddItem(int position) {
        int size = lists == null ? 0 : lists.size();
        return position == size;
    }

    static class ViewHolder {
        ImageView iv;
        DisplayImageOptions options;
    }
}