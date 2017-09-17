package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */
public class FengWenClassifyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ClassifyEntity> classifyEntities;

    private int numCount = 3;
    private static OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener;

    public FengWenClassifyAdapter(Context context, List<ClassifyEntity> classifyEntities) {
        this.context = context;
        this.classifyEntities = classifyEntities;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (classifyEntities.size() > 3) {
            return numCount;
        } else {
            return classifyEntities.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return classifyEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_city, null);
            viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            viewHolder.tv_city = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (classifyEntities.get(position).getNum().equals("1")) {
            viewHolder.tv_city.setBackgroundResource((R.drawable.bg_conner_claissfy_shape));
        } else {
            viewHolder.tv_city.setBackgroundResource((R.drawable.bg_conner_classify_one_shape));
        }

        viewHolder.tv_city.setText(classifyEntities.get(position).getName());
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classifyEntityOnClassifyPostionClickListener != null) {
                    classifyEntityOnClassifyPostionClickListener.onClassifyPostionClick(classifyEntities.get(position), 1);
                }
            }
        });

        return convertView;
    }


    public static class ViewHolder {
        private TextView tv_city;
        private RelativeLayout rl_item;
    }

    /**
     * 点击后设置Item的数量
     *
     * @param number
     */
    public void addItemNum(int number) {
        numCount = number;
    }


    public static void setClassifyEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener) {
        FengWenClassifyAdapter.classifyEntityOnClassifyPostionClickListener = classifyEntityOnClassifyPostionClickListener;
    }
}
