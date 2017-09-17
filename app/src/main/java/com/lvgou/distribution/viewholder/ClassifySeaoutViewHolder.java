package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.SeaoutClassifyEntity;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/30 0030.
 */
public class ClassifySeaoutViewHolder extends ViewHolderBase<SeaoutClassifyEntity> {

    private Context context;
    private TextView tv_name;
    private RelativeLayout rl_item;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_gridview_seaout, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        return view;
    }

    @Override
    public void showData(int position, SeaoutClassifyEntity itemData) {
        tv_name.setText(itemData.getName());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show(context, "点击了分类");
            }
        });
    }
}
