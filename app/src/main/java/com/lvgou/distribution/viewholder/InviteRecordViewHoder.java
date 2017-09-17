package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.InviteRecordEntity;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/4/6 0006.
 */
public class InviteRecordViewHoder extends ViewHolderBase<InviteRecordEntity> {

    private Context context;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_status;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_grid_invite_recoder, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_status = (TextView) view.findViewById(R.id.tv_state);
        return view;
    }


    @Override
    public void showData(int position, final InviteRecordEntity itemData) {
        tv_name.setText(itemData.getName());
        String[] str = itemData.getTime().split("T");
//        if (!str[0].equals("1900-01-01")) {
        tv_time.setText(str[0]);
//        } else {
//            tv_time.setText("");
//        }

        if (itemData.getIsGet().equals("1")) {
            tv_status.setText("(已完成)");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_my_task_blue));
        } else if (itemData.getIsGet().equals("2") && itemData.getState().equals("1")) {
            tv_status.setText("(待审核)");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_my_task_num_color));
        } else if (itemData.getIsGet().equals("2") && itemData.getState().equals("3")) {
            tv_status.setText("(未通过)");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_my_task_text_title));
        }
    }

}
