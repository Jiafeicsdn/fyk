package com.lvgou.distribution.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lvgou.distribution.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Snow on 2016/3/31 0031.
 * 自定义ItemView
 */
public class CustomerItemView extends LinearLayout {

    private String[] mVals = new String[]{"Hello", "Android", "TextView"};

    public CustomerItemView(Context context, List<String> lists) {
        super(context);
        initView(context);
    }

    public CustomerItemView(Context context, AttributeSet attrs, List<String> lists) {
        super(context, attrs);
        initView(context);
    }

    public CustomerItemView(Context context, AttributeSet attrs, int defStyleAttr, List<String> lists) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void initView(final Context context) {
        View view = View.inflate(context, R.layout.view_customer_classify, this);
        FlowLayout foldLayout = (FlowLayout) view.findViewById(R.id.foldlayout);
        List<String> lists=new ArrayList<String>();
        lists.add("测试1");
        lists.add("测试2");
        lists.add("测试3");
        lists.add("测试4");
        measureParent(view, lists, context, foldLayout);
    }

    public void measureParent(View view, List<String> lists, final Context context, final FlowLayout foldLayout) {
        ViewGroup group = (ViewGroup) findViewById(R.id.ll_item_parent);
        TextView[] textViews = new TextView[4];
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            textViews[i] = textView;
            for (int j = 0; j < 4; j++) {
                textView.setText(lists.get(j));
                group.addView(textView);
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取子数据，用来显示
                    LayoutInflater mInflater = LayoutInflater.from(context);
                    for (int i = 0; i < mVals.length; i++) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.tv, foldLayout, false);
                        tv.setText(mVals[i]);
                        foldLayout.addView(tv);
                    }
                }
            });
        }
    }

//    public void measureChild(View view, List<String> lists, Context context) {
//        ViewGroup group = (ViewGroup) findViewById(R.id.ll_item_child);
//        TextView[] textViews = new TextView[lists.size()];
//        for (int i = 0; i < textViews.length; i++) {
//            TextView textView = new TextView(context);
//            textView.setLayoutParams(new LayoutParams(
//                    LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT));
//            textViews[i] = textView;
//            for (int k = 0; k < lists.size(); k++) {
//                textView.setText(lists.get(k));
//                group.addView(textView);
//            }
//        }
//    }
}
