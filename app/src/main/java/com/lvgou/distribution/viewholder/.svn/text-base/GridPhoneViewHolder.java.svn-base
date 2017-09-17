package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.PhoneEntity;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/4/18 0018.
 */
public class GridPhoneViewHolder extends ViewHolderBase<PhoneEntity> {

    private TextView tv_phone;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_detial_grid, null);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        return view;
    }
    @Override
    public void showData(int position, PhoneEntity itemData) {
        tv_phone.setText(itemData.getPhone());
    }
}
