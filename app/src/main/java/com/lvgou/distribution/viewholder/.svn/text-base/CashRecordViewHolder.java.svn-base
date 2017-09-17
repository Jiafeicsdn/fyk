package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.CashRecordEntitiy;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import org.w3c.dom.Text;

import java.text.DecimalFormat;



/**
 * Created by Snow on 2016/3/11 0011.
 */
public class CashRecordViewHolder extends ViewHolderBase<CashRecordEntitiy> {

    private Context context;
    private RelativeLayout rl_item;
    private TextView tv_money;
    private TextView tv_apply_time;
    private TextView tv_pay_time;
    private TextView tv_name;
    private TextView tv_status;
    private TextView tv_bank;
    private TextView tv_num;
    private TextView tv_2;


    private static OnListItemClickListener<CashRecordEntitiy> onCashRecordEntitiyOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_cash_record, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_apply_time = (TextView) view.findViewById(R.id.tv_time_apply);
        tv_pay_time = (TextView) view.findViewById(R.id.tv_time_pay);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_bank = (TextView) view.findViewById(R.id.tv_bank);
        tv_num = (TextView) view.findViewById(R.id.tv_order_num);
        tv_2 = (TextView) view.findViewById(R.id.tv_2);
        return view;
    }

    @Override
    public void showData(int position, final CashRecordEntitiy itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        tv_money.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getMoney())));
        if (itemData.getApply_time() != null && itemData.getApply_time().length() > 0) {
            String[] str = itemData.getApply_time().split("T");
            tv_apply_time.setText(str[0]);
        }
        if (itemData.getPay_time() != null && itemData.getPay_time().length() > 0) {
            String[] str = itemData.getPay_time().split("T");
            String time = str[1].substring(0, 5);
            tv_pay_time.setText(str[0] + " " + time);
        }
        tv_name.setText(itemData.getName());
        if (itemData.getStatus().equals("1")) {
            tv_status.setText("审核中");
            tv_pay_time.setVisibility(View.GONE);
            tv_2.setText("审核结果");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_code_number));
        } else if (itemData.getStatus().equals("2")) {
            tv_status.setText("已打款");
            tv_pay_time.setVisibility(View.VISIBLE);
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_income_blue));
            tv_2.setText("打款时间");
        } else if (itemData.getStatus().equals("3")) {
            tv_status.setText("审核失败");
            tv_pay_time.setVisibility(View.GONE);
            tv_2.setText("审核结果");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_new_guide_black));
        }

        tv_bank.setText(itemData.getBank_name());
        tv_num.setText(itemData.getBank_num());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCashRecordEntitiyOnListItemClickListener != null) {
                    onCashRecordEntitiyOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnCashRecordEntitiyOnListItemClickListener(OnListItemClickListener<CashRecordEntitiy> onCashRecordEntitiyOnListItemClickListener) {
        CashRecordViewHolder.onCashRecordEntitiyOnListItemClickListener = onCashRecordEntitiyOnListItemClickListener;
    }
}
