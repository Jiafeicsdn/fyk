package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/10/9.
 */
public class DaySelectViewHolder extends ViewHolderBase<ProvinceEntity> {


    private RelativeLayout rl_item;
    private TextView tv_title;
    private Context context;
    private static OnClassifyPostionClickListener<ProvinceEntity> onClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_select_all_by_day, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_title = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void showData(int position, final ProvinceEntity itemData) {
        if (Constants.ALL_SELECT_DAY.equals(itemData.getName())) {
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_button_green));
        } else {
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
        }

        tv_title.setText(itemData.getName());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ALL_SELECT_DAY = itemData.getName();
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 5);
                }
            }
        });
    }


    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<ProvinceEntity> onClassifyPostionClickListener) {
        DaySelectViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
