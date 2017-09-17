package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.CashRecordEntity;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/11/2.
 */
public class RechargeecordViewHolder extends ViewHolderBase<CashRecordEntity> {

    private TextView tv_title;
    private Context context;
    private TextView tv_time;
    private TextView tv_tuanbi;
    private TextView tv_state;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_recharge_record, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_tuanbi = (TextView) view.findViewById(R.id.tv_tuanbi);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        return view;
    }


    @Override
    public void showData(int position, CashRecordEntity itemData) {
        tv_title.setText("充值:  " + itemData.getTitle() + "元");
        tv_tuanbi.setText("+" + itemData.getTuanbi() + "团币");
        tv_time.setText(itemData.getTime().split("T")[0] + " " + itemData.getTime().split("T")[1].substring(0, 8));
        if (itemData.getState().equals("1")) {
            tv_state.setText("待支付");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_code_number));
        } else if (itemData.getState().equals("2")) {
            tv_state.setText("充值成功");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_income_blue));
        } else if (itemData.getState().equals("3")) {
            tv_state.setText("充值失败");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_code_number));
        } else if (itemData.getState().equals("4")) {
            tv_state.setText("支付失败");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_code_number));
        }
    }
}
