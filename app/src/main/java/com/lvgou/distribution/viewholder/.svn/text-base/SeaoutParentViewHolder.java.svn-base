package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.SeaoutClassifyEntity;
import com.lvgou.distribution.entity.SeaoutParentEntitiy;
import com.lvgou.distribution.view.MyGridView;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/3/30 0030.
 */
public class SeaoutParentViewHolder extends ViewHolderBase<SeaoutParentEntitiy> {

    private Context context;
    private TextView tv_name;
    private MyGridView myGridView;
    private ListViewDataAdapter<SeaoutClassifyEntity> seaoutClassifyEntityListViewDataAdapter;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_parent_seaout, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        myGridView = (MyGridView) view.findViewById(R.id.grid_view_child);
        return view;
    }


    @Override
    public void showData(int position, final SeaoutParentEntitiy itemData) {
        tv_name.setText(itemData.getName());
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                seaoutClassifyEntityListViewDataAdapter = new ListViewDataAdapter<SeaoutClassifyEntity>();
                for (int i = 0; i < itemData.getSeaoutClassifyEntityList().size(); i++) {
                    SeaoutClassifyEntity seaoutClassifyEntity = itemData.getSeaoutClassifyEntityList().get(i);
                    seaoutClassifyEntityListViewDataAdapter.append(seaoutClassifyEntity);
                }
                seaoutClassifyEntityListViewDataAdapter.setViewHolderClass(context, ClassifySeaoutViewHolder.class);
                myGridView.setAdapter(seaoutClassifyEntityListViewDataAdapter);
            }
        });
    }
}
