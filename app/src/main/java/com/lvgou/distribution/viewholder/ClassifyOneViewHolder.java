package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/25 0025.
 */
public class ClassifyOneViewHolder extends ViewHolderBase<ClassifyEntity> {

    private Context context;
    private LinearLayout ll_item;
    private ImageView img_right;
    private TextView tv_name;
    private TextView tv_num;
    private static OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_classify_list, null);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_second_classifying);
        img_right = (ImageView) view.findViewById(R.id.image);
        tv_name = (TextView) view.findViewById(R.id.tv_title);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        return view;
    }

    @Override
    public void showData(int position, final ClassifyEntity itemData) {
        if (Constants.SELECTE_POSITION03.equals(itemData.getId())) {
            tv_name.setTextColor(context.getResources().getColor(R.color.bg_baoming_yellow));
            tv_num.setTextColor(context.getResources().getColor(R.color.bg_baoming_yellow));
//            img_right.setVisibility(View.VISIBLE);
        }
        tv_name.setText(itemData.getName());
        tv_num.setText(itemData.getNum());
        tv_num.setTextColor(context.getResources().getColor(R.color.bg_push_time));
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECTE_POSITION03 = itemData.getId();
                if (classifyEntityOnClassifyPostionClickListener != null) {
                    classifyEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });
    }

    public static void setClassifyEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener) {
        ClassifyOneViewHolder.classifyEntityOnClassifyPostionClickListener = classifyEntityOnClassifyPostionClickListener;
    }
}