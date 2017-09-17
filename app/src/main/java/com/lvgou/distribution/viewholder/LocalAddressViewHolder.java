package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.LocalAddressEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/11/5.
 */
public class LocalAddressViewHolder extends ViewHolderBase<LocalAddressEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_icon;
    private TextView tv_title;
    private TextView tv_address;
    private TextView tv_none_show;
    private ImageView img_select_icon;

    private static OnListItemClickListener<LocalAddressEntity> localAddressEntityOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_select_local_address, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_02);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_none_show = (TextView) view.findViewById(R.id.tv_none_show);
        img_select_icon = (ImageView) view.findViewById(R.id.img_select_icon);
        return view;
    }

    @Override
    public void showData(int position, final LocalAddressEntity itemData) {
        if (Constants.SELECT_LOCAL_ADDRESS.equals(itemData.getTitle())) {
            tv_none_show.setTextColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
            tv_address.setTextColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
            img_select_icon.setVisibility(View.VISIBLE);
        } else {
            tv_none_show.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
            tv_address.setTextColor(context.getResources().getColor(R.color.bg_gray_two));
            img_select_icon.setVisibility(View.GONE);
        }

        if (itemData.getTitle().equals("不显示位置")) {
            img_icon.setVisibility(View.INVISIBLE);
            tv_title.setVisibility(View.INVISIBLE);
            tv_address.setVisibility(View.INVISIBLE);
            tv_none_show.setVisibility(View.VISIBLE);
            tv_none_show.setText("不显示位置");
        } else {
            img_icon.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.VISIBLE);
            tv_address.setVisibility(View.VISIBLE);
            tv_none_show.setVisibility(View.GONE);
            tv_title.setText(itemData.getTitle());
            tv_address.setText(itemData.getName());
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECT_LOCAL_ADDRESS = itemData.getTitle();
                if (localAddressEntityOnListItemClickListener != null) {
                    localAddressEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });

    }

    public static void setLocalAddressEntityOnListItemClickListener(OnListItemClickListener<LocalAddressEntity> localAddressEntityOnListItemClickListener) {
        LocalAddressViewHolder.localAddressEntityOnListItemClickListener = localAddressEntityOnListItemClickListener;
    }
}
