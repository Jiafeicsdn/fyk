package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/9/28.
 */
public class SelectCityViewHolder extends ViewHolderBase<ProvinceEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private TextView tv_name;

    private static OnClassifyPostionClickListener<ProvinceEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_select_address, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_name = (TextView) view.findViewById(R.id.tv_address);
        return view;
    }


    @Override
    public void showData(int position, final ProvinceEntity itemData) {
        tv_name.setText(itemData.getName());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onClassifyPostionClick(itemData, 3);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnClassifyPostionClickListener<ProvinceEntity> onListItemClickListener) {
        SelectCityViewHolder.onListItemClickListener = onListItemClickListener;
    }
}

