package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/7/28 0028.
 */
public class GridBackViewHolder extends ViewHolderBase<ClassifyEntity> {

    private Context context;
    private TextView tv_city;
    private RelativeLayout rl_item;

    private static OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_gridview_classify, null);
        tv_city = (TextView) view.findViewById(R.id.tv_name);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);

        return view;
    }

    @Override
    public void showData(int position, final ClassifyEntity itemData) {
        if (Constants.SELECTE_POSITION06.equals(itemData.getName())) {
            tv_city.setBackgroundResource((R.drawable.bg_conner_claissfy_shape));
        } else {
            tv_city.setBackgroundResource((R.drawable.bg_conner_classify_one_shape));
        }

        tv_city.setText(itemData.getName());

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECTE_POSITION06 = itemData.getName();
                if (classifyEntityOnClassifyPostionClickListener != null) {
                    classifyEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 4);
                }
            }
        });
    }

    public static void setClassifyEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener) {
        GridBackViewHolder.classifyEntityOnClassifyPostionClickListener = classifyEntityOnClassifyPostionClickListener;
    }
}
