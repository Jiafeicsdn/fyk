package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.RankEntitiy;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/6/13 0013.
 */
public class RankViewHolder extends ViewHolderBase<RankEntitiy> {

    private ImageView img_num;
    private TextView tv_num;
    private TextView tv_name;
    private TextView tv_money;
    private LinearLayout ll_bg;
    private Context context;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_rank_list, null);
        img_num = (ImageView) view.findViewById(R.id.img_icon);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        tv_name = (TextView) view.findViewById(R.id.nick_name);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
        return view;
    }

    @Override
    public void showData(int position, RankEntitiy itemData) {

        if (itemData.getOrder_num().equals((Integer.parseInt(Constants.MY_RANK_NUM)-1)+"")) {
            ll_bg.setBackgroundColor(context.getResources().getColor(R.color.bg_baoming_yellow));
        } else {
            ll_bg.setBackgroundColor(context.getResources().getColor(R.color.bg_rank_order_num));
        }
        if (itemData.getOrder_num().equals("0")) {
            img_num.setBackgroundResource(R.mipmap.rank_one);
            img_num.setVisibility(View.VISIBLE);
            tv_num.setVisibility(View.GONE);
            tv_money.setText(itemData.getMoney());
            tv_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.fourteen));
            tv_money.setTextColor(context.getResources().getColor(R.color.bg_rank_first));
        } else if (itemData.getOrder_num().equals("1")) {
            img_num.setBackgroundResource(R.mipmap.rank_two);
            img_num.setVisibility(View.VISIBLE);
            tv_num.setVisibility(View.GONE);
            tv_money.setText(itemData.getMoney());
            tv_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.fourteen));
            tv_money.setTextColor(context.getResources().getColor(R.color.bg_rank_two));
        } else if (itemData.getOrder_num().equals("2")) {
            img_num.setBackgroundResource(R.mipmap.rank_three);
            img_num.setVisibility(View.VISIBLE);
            tv_num.setVisibility(View.GONE);
            tv_money.setText(itemData.getMoney());
            tv_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.fourteen));
            tv_money.setTextColor(context.getResources().getColor(R.color.bg_rank_three));
        } else {
            img_num.setVisibility(View.GONE);
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText((Integer.parseInt(itemData.getOrder_num()) + 1) + "");
            tv_money.setText(itemData.getMoney());
            tv_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.thirteen));
            tv_money.setTextColor(context.getResources().getColor(R.color.bg_button_text_black));
        }
        tv_name.setText(itemData.getName());
        if (itemData.getName().length() == 1) {
            tv_name.setText(itemData.getName());
        } else if (itemData.getName().length() == 2) {
            tv_name.setText("*" + itemData.getName().substring(1, 2));
        } else if (itemData.getName().length() > 2) {
            String str = "";
            int size = itemData.getName().length() - 2;
            for (int i = 0; i < size; i++) {
                str += str.concat("*");
            }
            tv_name.setText(itemData.getName().substring(0, 1) + str + itemData.getName().substring(itemData.getName().length() - 1, itemData.getName().length()));
        }
    }
}
