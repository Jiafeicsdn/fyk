package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.ProfitEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Snow on 2016/5/3
 * 推广收益
 */
public class ExtensionProfitAdapter extends BaseAdapter {

    private Context context;
    private List<ProfitEntity> profitEntityLists;
    private LayoutInflater inflater;

    public ExtensionProfitAdapter(Context context, List<ProfitEntity> profitEntityLists) {
        this.context = context;
        this.profitEntityLists = profitEntityLists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return profitEntityLists.size();
    }

    @Override
    public Object getItem(int position) {
        return profitEntityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_fragment_profit, null);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_weixin_num = (TextView) convertView.findViewById(R.id.tv_weixin_num);
            viewHolder.tv_user = (TextView) convertView.findViewById(R.id.tv_user_status);
            viewHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.rl_time = (RelativeLayout) convertView.findViewById(R.id.rl_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.tv_time.setText("收益时期 " + profitEntityLists.get(position).getTime().split("T")[0]);
            viewHolder.rl_time.setVisibility(View.VISIBLE);
        } else {
            if (profitEntityLists.get(position).getTime().split("T")[0].equals(profitEntityLists.get(position - 1).getTime().split("T")[0])) {
                viewHolder.rl_time.setVisibility(View.GONE);
            } else {
                viewHolder.tv_time.setText("收益时期 " + profitEntityLists.get(position).getTime().split("T")[0]);
                viewHolder.rl_time.setVisibility(View.VISIBLE);
            }
        }
        if (profitEntityLists.get(position).getName().length() > 5) {
            viewHolder.tv_name.setText(profitEntityLists.get(position).getName().substring(0, 5) + "..");
        } else {
            viewHolder.tv_name.setText(profitEntityLists.get(position).getName());
        }
//        if (profitEntityLists.get(position).getWeixin_num().length() > 10) {
//            viewHolder.tv_weixin_num.setText(profitEntityLists.get(position).getWeixin_num().substring(0, 10) + "..");
//        } else {
        viewHolder.tv_weixin_num.setText(profitEntityLists.get(position).getWeixin_num());
//        }
        if (profitEntityLists.get(position).getUser_type().equals("1")) {
            viewHolder.tv_user.setText("成功/");
            viewHolder.tv_user.setTextColor(context.getResources().getColor(R.color.bg_blue_order));
        } else if (profitEntityLists.get(position).getUser_type().equals("2")) {
            viewHolder.tv_user.setText("老用户");
            viewHolder.tv_user.setTextColor(context.getResources().getColor(R.color.bg_profit_time));
        } else if (profitEntityLists.get(position).getUser_type().equals("3")) {
            viewHolder.tv_user.setText("未下载");
            viewHolder.tv_user.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        } else if (profitEntityLists.get(position).getUser_type().equals("4")) {
            viewHolder.tv_user.setText("待审/");
            viewHolder.tv_user.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        } else if (profitEntityLists.get(position).getUser_type().equals("5")) {
            viewHolder.tv_user.setText("未交易/");
            viewHolder.tv_user.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        }

        if (profitEntityLists.get(position).getUser_type().equals("1") || profitEntityLists.get(position).getUser_type().equals("4") || profitEntityLists.get(position).getUser_type().equals("5")) {
            if (profitEntityLists.get(position).getStatus().equals("1")) {
                viewHolder.tv_status.setText("未结");
                viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.bg_blue_order));
            } else if (profitEntityLists.get(position).getStatus().equals("2")) {
                viewHolder.tv_status.setText("待审");
                viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
            } else if (profitEntityLists.get(position).getStatus().equals("3")) {
                viewHolder.tv_status.setText("已结");
                viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.bg_profit_time));
            } else {
                viewHolder.tv_status.setText("");
            }
        } else {
            viewHolder.tv_status.setText("");
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        if (profitEntityLists.get(position).getUser_type().equals("1") || profitEntityLists.get(position).getUser_type().equals("4")) {
            viewHolder.tv_price.setText("+" + Integer.parseInt(profitEntityLists.get(position).getPrice()) + "元");
            viewHolder.tv_price.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        } else {
            viewHolder.tv_price.setText("0 元");
            viewHolder.tv_price.setTextColor(context.getResources().getColor(R.color.bg_new_guide_black));
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_time;
        private TextView tv_name;
        private TextView tv_weixin_num;
        private TextView tv_user;
        private TextView tv_status;
        private TextView tv_price;
        private RelativeLayout rl_time;
    }
}
