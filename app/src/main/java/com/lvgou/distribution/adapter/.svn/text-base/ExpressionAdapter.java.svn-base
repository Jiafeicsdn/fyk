package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.lvgou.distribution.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ExpressionAdapter extends ArrayAdapter<String> {
    public ExpressionAdapter(Context paramContext, int paramInt, List<String> paramList)
    {
        super(paramContext, paramInt, paramList);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        if (paramView == null)
            paramView = View.inflate(getContext(), R.layout.row_expression, null);
        ImageView localImageView = (ImageView)paramView.findViewById(R.id.iv_expression);
        String str = (String)getItem(paramInt);
        localImageView.setImageResource(getContext().getResources().getIdentifier(str, "mipmap", getContext().getPackageName()));
        return paramView;
    }
}
