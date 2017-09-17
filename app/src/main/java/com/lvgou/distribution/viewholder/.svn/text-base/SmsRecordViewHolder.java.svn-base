package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.SmsRecordEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/4/18 0018.
 */
public class SmsRecordViewHolder extends ViewHolderBase<SmsRecordEntity> {

    private RelativeLayout rl_item;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;

    private static OnListItemClickListener<SmsRecordEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_sms_record, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void showData(int position, final SmsRecordEntity itemData) {
        tv_name.setText(itemData.getName());
        tv_content.setText(itemData.getContent());
        String[] str = itemData.getTime().split("T");
        tv_time.setText(str[0].substring(5, 10));
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnListItemClickListener<SmsRecordEntity> onListItemClickListener) {
        SmsRecordViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
