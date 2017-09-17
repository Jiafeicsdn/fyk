package com.lvgou.distribution.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2016/8/25.
 */
public class CustomeTextView extends LinearLayout {


    private TextView tv_text;

    public CustomeTextView(Context context) {
        super(context);
        initView(context);
    }

    public CustomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(final Context context) {
        View view = View.inflate(context, R.layout.text_layout, this);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
    }

    public TextView getText() {
        return tv_text;
    }

}
