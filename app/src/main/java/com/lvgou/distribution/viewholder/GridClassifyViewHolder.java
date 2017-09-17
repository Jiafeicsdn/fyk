package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/5/4 0004.
 */
public class GridClassifyViewHolder extends ViewHolderBase<ClassifyEntity> {

    private Context context;
    private TextView tv_city;
    private View view_line;
    private RelativeLayout rl_item;

    private static OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_gridview_classify, null);
        tv_city = (TextView) view.findViewById(R.id.tv_name);
        view_line = (View) view.findViewById(R.id.view_line);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);

        return view;
    }

    @Override
    public void showData(int position, final ClassifyEntity itemData) {
        if (Constants.SELECTE_POSITION04.equals(itemData.getId())) {
            tv_city.setTextColor(context.getResources().getColor(R.color.bg_new_guide_black));
            view_line.setVisibility(View.VISIBLE);
        } else {
            tv_city.setTextColor(context.getResources().getColor(R.color.bg_bottom_gray));
            view_line.setVisibility(View.GONE);
        }

        tv_city.setText(itemData.getName());
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv_city.measure(width, height);

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECTE_POSITION04 = itemData.getId();
                if (classifyEntityOnClassifyPostionClickListener != null) {
                    classifyEntityOnClassifyPostionClickListener.onClassifyPostionClick(itemData, 4);
                }
            }
        });
    }

    public static void setClassifyEntityOnClassifyPostionClickListener(OnClassifyPostionClickListener<ClassifyEntity> classifyEntityOnClassifyPostionClickListener) {
        GridClassifyViewHolder.classifyEntityOnClassifyPostionClickListener = classifyEntityOnClassifyPostionClickListener;
    }
}
