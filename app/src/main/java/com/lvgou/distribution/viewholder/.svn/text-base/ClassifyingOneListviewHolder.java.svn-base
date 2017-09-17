package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.BankEntity;
import com.lvgou.distribution.inter.OnClassifyClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/5/11 0011.
 */
public class ClassifyingOneListviewHolder extends ViewHolderBase<BankEntity> {
    private Context context;
    private TextView mFirstClassifyingTextView;
    private ImageView mImage;
    private LinearLayout mRootLinearLayout;

    private static OnClassifyClickListener<BankEntity> onFirstClassifyingItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_classify_listview,
                null);
        mFirstClassifyingTextView = (TextView) view.findViewById(R.id.tv_title);
        mRootLinearLayout = (LinearLayout) view.findViewById(R.id.ll_second_classifying);
        mImage = (ImageView) view.findViewById(R.id.image);
        mFirstClassifyingTextView.setTextColor(Color.BLACK);
        mFirstClassifyingTextView.setTextSize(14);
        return view;
    }

    @Override
    public void showData(final int position, final BankEntity itemData) {

        mFirstClassifyingTextView.setText(itemData.getName());

        mRootLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onFirstClassifyingItemClickListener != null) {
                    onFirstClassifyingItemClickListener.onClassifyClick(itemData);
                }
            }
        });
    }

    public static void setOnFirstClassifyingItemClickListener(OnClassifyClickListener<BankEntity> onFirstClassifyingItemClickListener) {
        ClassifyingOneListviewHolder.onFirstClassifyingItemClickListener = onFirstClassifyingItemClickListener;
    }
}

