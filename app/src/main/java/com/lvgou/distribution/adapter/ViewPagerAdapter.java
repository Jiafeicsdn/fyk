package com.lvgou.distribution.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context) {
        super();
        this.views = views;
        this.context = context;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return views.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        // TODO Auto-generated method stub
        return (view == arg1);
    }

}
