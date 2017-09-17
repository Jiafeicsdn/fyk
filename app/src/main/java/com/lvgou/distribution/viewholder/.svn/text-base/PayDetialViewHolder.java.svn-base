package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.PayDetialEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;


/**
 * Created by Snow on 2016/3/11 0011.
 */
public class PayDetialViewHolder extends ViewHolderBase<PayDetialEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private TextView tv_status;
    private TextView tv_money;
    private TextView tv_time;
    private TextView tv_ordernum;

    private static OnListItemClickListener<PayDetialEntity> payDetialEntityOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_cost_detial, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_ordernum = (TextView) view.findViewById(R.id.tv_order_num);
        return view;
    }

    @Override
    public void showData(int position, final PayDetialEntity itemData) {
        // 0=可提现，1=申请中，2=已结算，3=锁定中，4=已取消
        if (itemData.getStatus().equals("0")) {
            tv_status.setText("可提现:");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_blue_cose));
        } else if (itemData.getStatus().equals("1")) {
            tv_status.setText("申请中:");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        } else if (itemData.getStatus().equals("2")) {
            tv_status.setText("已结算:");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_text_black));
        } else if (itemData.getStatus().equals("3")) {
            tv_status.setText("锁定中:");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        } else if (itemData.getStatus().equals("4")) {
            tv_status.setText("已取消:");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_text_black));
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");

        tv_money.setText("¥" +decimalFormat.format(Float.parseFloat(itemData.getMoney())));
        if (itemData.getTime() != null && itemData.getTime().length() > 0) {
            String[] str = itemData.getTime().split("T");
            tv_time.setText(str[0]);
        }
        tv_ordernum.setText(itemData.getOrdername());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payDetialEntityOnListItemClickListener != null) {
                    payDetialEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setPayDetialEntityOnListItemClickListener(OnListItemClickListener<PayDetialEntity> payDetialEntityOnListItemClickListener) {
        PayDetialViewHolder.payDetialEntityOnListItemClickListener = payDetialEntityOnListItemClickListener;
    }
}
