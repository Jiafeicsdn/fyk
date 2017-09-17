package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.NoneEntity;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/8/15 0015.
 */
public class ViewNoneHolder extends ViewHolderBase<NoneEntity> {



    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_none_date, null);
        return view;
    }

    @Override
    public void showData(int position, NoneEntity itemData) {

    }
}
