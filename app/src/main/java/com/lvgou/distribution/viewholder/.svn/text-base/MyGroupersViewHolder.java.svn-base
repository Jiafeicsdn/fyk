package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.MyGroupEntity;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/3/21 0021.
 */
public class MyGroupersViewHolder extends ViewHolderBase<MyGroupEntity> {

    private Context context;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_time;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_grid_my_team, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        return view;
    }

    @Override
    public void showData(int position, MyGroupEntity itemData) {
        tv_name.setText(itemData.getName());
        tv_phone.setText(itemData.getPhone());
        if (itemData.getTiem() != null && itemData.getTiem().length() > 0) {
            String[] str = itemData.getTiem().split("T");
            String time = str[0];
            if (time.equals("1900-01-01")) {
                tv_time.setText("");
            } else {
                tv_time.setText(time);
            }
        }
    }
}
