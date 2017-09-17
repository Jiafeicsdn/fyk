package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ProfitEntity;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/4/29 0029.
 */
public class ProfitViewHolder extends ViewHolderBase<ProfitEntity> {

    private Context context;
    private TextView tv_time;
    private TextView tv_name;
    private TextView tv_weixin_num;
    private TextView tv_user;
    private TextView tv_status;
    private TextView tv_price;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_fragment_profit, null);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_weixin_num = (TextView) view.findViewById(R.id.tv_weixin_num);
        tv_user = (TextView) view.findViewById(R.id.tv_user_status);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        return view;
    }

    @Override
    public void showData(int position, ProfitEntity itemData) {

        tv_time.setText("收益时期 " + itemData.getTime().split("T")[0]);

        tv_name.setText(itemData.getName());
        tv_weixin_num.setText(itemData.getWeixin_num());
        if (itemData.getUser_type().equals("1")) {
            tv_user.setText("成功");
            tv_user.setTextColor(context.getResources().getColor(R.color.bg_blue_order));
        } else if (itemData.getUser_type().equals("2")) {
            tv_user.setText("老用户");
            tv_user.setTextColor(context.getResources().getColor(R.color.bg_profit_time));
        } else if (itemData.getUser_type().equals("3")) {
            tv_user.setText("未下载");
            tv_user.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        }

        if (itemData.getUser_type().equals("1")) {
            if (itemData.getStatus().equals("1")) {
                tv_status.setText("未结算");
                tv_status.setTextColor(context.getResources().getColor(R.color.bg_blue_order));
            } else if (itemData.getStatus().equals("2")) {
                tv_status.setText("申请中");
                tv_status.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
            } else if (itemData.getStatus().equals("3")) {
                tv_status.setText("已结算");
                tv_status.setTextColor(context.getResources().getColor(R.color.bg_profit_time));
            } else {
                tv_status.setText("");
            }
        } else {
            tv_status.setText("");
        }

        if (itemData.getUser_type().equals("1")) {
            tv_price.setText("+" + itemData.getPrice() + "元");
            tv_price.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        } else {
            tv_price.setText("0 元");
            tv_price.setTextColor(context.getResources().getColor(R.color.bg_new_guide_black));
        }

    }
}
