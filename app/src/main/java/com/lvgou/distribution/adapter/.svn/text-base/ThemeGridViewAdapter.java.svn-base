package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ClassifyEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */
public class ThemeGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClassifyEntity> themeTypeList;

    public ThemeGridViewAdapter(Context context, List<ClassifyEntity> list) {
        mContext = context;
        themeTypeList = list;
    }

    public List<ClassifyEntity> getThemeTypeList() {
        return themeTypeList;
    }

    @Override
    public int getCount() {
        return themeTypeList.size();
    }

    @Override
    public ClassifyEntity getItem(int position) {
        return themeTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_themetype_item_grid, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.img_select_icon = (ImageView) convertView.findViewById(R.id.img_select_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(themeTypeList.get(position).getName());

        if (themeTypeList.get(position).isChecked()) {
            viewHolder.img_select_icon.setImageResource(R.mipmap.bg_languages_selected);
        } else {
            viewHolder.img_select_icon.setImageResource(R.mipmap.bg_languages_default);
        }
        return convertView;
    }

    public class ViewHolder {
        private RelativeLayout relativeLayout;
        private ImageView img_select_icon;
        private TextView tv_title;
    }
}
