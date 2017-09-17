package com.lvgou.distribution.viewholder;

import android.annotation.SuppressLint;
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
 * 一级分类holder
 *
 * @author Robin time 2015-04-15 17:15:23
 */
@SuppressLint("InflateParams")
public class ClassifyingListviewHolder extends ViewHolderBase<BankEntity> {
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
        mRootLinearLayout = (LinearLayout) view
                .findViewById(R.id.ll_second_classifying);
        mImage = (ImageView) view.findViewById(R.id.image);
        mFirstClassifyingTextView.setTextColor(Color.BLACK);
        mFirstClassifyingTextView.setTextSize(14);
        return view;
    }

    @Override
    public void showData(final int position, final BankEntity itemData) {
//		if (position == Constants.SELECTE_POSITION01){
//			mRootLinearLayout.setBackgroundColor(Color.parseColor("#F2F2F2"));
//			 mFirstClassifyingTextView.setTextColor(context.getResources().getColor(R.color.green));
//		} else {
//			mRootLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//			mFirstClassifyingTextView.setTextColor(context.getResources()
//					.getColor(R.color.text_black));
//		}
        mFirstClassifyingTextView.setText(itemData.getName());
        // mImage.setBackgroundResource(itemData.getUrl());
//		if (itemData.getUrl() != null && itemData.getUrl().length() > 0) {
//			mImage.setVisibility(View.VISIBLE);
//			ImageDisplayer.getInstance().dispalyImageWithRadius(
//					Url.IMAGR_ROOT + itemData.getUrl()
//							+ itemData.getImage_url(), mImage, 0);
//		} else {
//			mImage.setVisibility(View.GONE);
//		}
        mRootLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Constants.SELECTE_POSITION01 = position;
                if (onFirstClassifyingItemClickListener != null) {
                    onFirstClassifyingItemClickListener.onClassifyClick(itemData);

                }
            }
        });
    }

    public static void setOnFirstClassifyingItemClickListener(OnClassifyClickListener<BankEntity> onFirstClassifyingItemClickListener) {
        ClassifyingListviewHolder.onFirstClassifyingItemClickListener = onFirstClassifyingItemClickListener;
    }
}
