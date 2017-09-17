package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ReportShopActivity;
import com.lvgou.distribution.entity.ReportShopEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

import org.w3c.dom.Text;

/**
 * Created by Snow on 2016/7/2 0002.
 */
public class ReportShopViewHolder extends ViewHolderBase<ReportShopEntity> {


    private TextView tv_name;

    private static OnListItemClickListener<ReportShopEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_report_search, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void showData(int position, final ReportShopEntity itemData) {
        tv_name.setText(itemData.getName());
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnListItemClickListener<ReportShopEntity> onListItemClickListener) {
        ReportShopViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
