package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.SmsModelEntity;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/4/18 0018.
 */
public class SmsModelViewHolder extends ViewHolderBase<SmsModelEntity> {

    private TextView tv_title;
    private TextView tv_content;
    private ImageView img_seleted;
    private ImageView img_edit;
    private ImageView img_delete;
    private RelativeLayout rl_item;

    private static OnPushSpeedClickListener<SmsModelEntity> smsModelEntityOnPushSpeedClickListener;// 1: item ,2:编辑 3：删除

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_send_sms, null);
        tv_title = (TextView) view.findViewById(R.id.tv_name);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        img_seleted = (ImageView) view.findViewById(R.id.img_right);
        img_edit = (ImageView) view.findViewById(R.id.img_edit);
        img_delete = (ImageView) view.findViewById(R.id.img_delete);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        return view;
    }

    @Override
    public void showData(int position, final SmsModelEntity itemData) {
        if (Constants.SELECTE_RESCORD_ID.equals(itemData.getId())) {
            img_seleted.setVisibility(View.VISIBLE);
        } else {
            img_seleted.setVisibility(View.GONE);
        }
        tv_title.setText(itemData.getName());
        tv_content.setText(itemData.getContent());
        if (itemData.getType().equals("1")) {
            img_edit.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
        } else if (itemData.getType().equals("2")) {
            img_edit.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.VISIBLE);
        }
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECTE_RESCORD_ID = itemData.getId();
                if (smsModelEntityOnPushSpeedClickListener != null) {
                    smsModelEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 1);
                }
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsModelEntityOnPushSpeedClickListener != null) {
                    smsModelEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 2);
                }
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsModelEntityOnPushSpeedClickListener != null) {
                    smsModelEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 3);
                }
            }
        });
    }

    public static void setSmsModelEntityOnPushSpeedClickListener(OnPushSpeedClickListener<SmsModelEntity> smsModelEntityOnPushSpeedClickListener) {
        SmsModelViewHolder.smsModelEntityOnPushSpeedClickListener = smsModelEntityOnPushSpeedClickListener;
    }
}
