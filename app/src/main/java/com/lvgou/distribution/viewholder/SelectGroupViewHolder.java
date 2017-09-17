package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.GroupEntity;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/4/18 0018.
 */
public class SelectGroupViewHolder extends ViewHolderBase<GroupEntity> {

    private RelativeLayout rl_item;
    private TextView tv_name;
    private ImageView img_selected;
    private ImageView img_edit;
    private ImageView img_delete;

    private static OnPushSpeedClickListener<GroupEntity> groupEntityOnPushSpeedClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_select_group, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_selected = (ImageView) view.findViewById(R.id.img_right);
        img_edit = (ImageView) view.findViewById(R.id.img_edit);
        img_delete = (ImageView) view.findViewById(R.id.img_delete);
        return view;
    }

    @Override
    public void showData(int position, final GroupEntity itemData) {
        if (Constants.SELECTED_GROUP_ID.equals(itemData.getId())) {
            img_selected.setVisibility(View.VISIBLE);
        } else {
            img_selected.setVisibility(View.GONE);
        }
        tv_name.setText(itemData.getName());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupEntityOnPushSpeedClickListener != null) {
                    groupEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 1);
                }
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupEntityOnPushSpeedClickListener != null) {
                    groupEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 2);
                }
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupEntityOnPushSpeedClickListener != null) {
                    groupEntityOnPushSpeedClickListener.onPushSpeedListener(itemData, 3);
                }
            }
        });
    }

    public static void setGroupEntityOnPushSpeedClickListener(OnPushSpeedClickListener<GroupEntity> groupEntityOnPushSpeedClickListener) {
        SelectGroupViewHolder.groupEntityOnPushSpeedClickListener = groupEntityOnPushSpeedClickListener;
    }
}
