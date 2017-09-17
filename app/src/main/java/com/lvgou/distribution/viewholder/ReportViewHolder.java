package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ReportEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Snow on 2016/7/2 0002.
 */
public class ReportViewHolder extends ViewHolderBase<ReportEntity> {

    private TextView tv_date;
    private TextView tv_name;
    private TextView tv_people_num;
    private TextView tv_state;
    private Context context;
    private RelativeLayout ll_item;

    private static OnListItemClickListener<ReportEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_report, null);
        tv_date = (TextView) view.findViewById(R.id.tv_time);
        tv_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_people_num = (TextView) view.findViewById(R.id.tv_people_num);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        ll_item = (RelativeLayout) view.findViewById(R.id.ll_item);
        return view;
    }

    @Override
    public void showData(int position, final ReportEntity itemData) {

        tv_date.setText(itemData.getDate().substring(5, 10));
        tv_name.setText(itemData.getName());
        tv_people_num.setText(itemData.getPeople_num() + "人");
        if (itemData.getState().equals("1")) {
            tv_state.setText("待确认");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
        } else if (itemData.getState().equals("2")) {
            tv_state.setText("已确认");
            tv_state.setTextColor(context.getResources().getColor(R.color.baobei_state_green));
        } else if (itemData.getState().equals("3")) {
            tv_state.setText("已取消");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_bottom_gray));
        }

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnListItemClickListener<ReportEntity> onListItemClickListener) {
        ReportViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
