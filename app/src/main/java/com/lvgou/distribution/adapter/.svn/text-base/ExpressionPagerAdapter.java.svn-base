package com.lvgou.distribution.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ExpressionPagerAdapter extends PagerAdapter{
    private List<View> views;

    public ExpressionPagerAdapter(List<View> paramList)
    {
        this.views = paramList;
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
        ((ViewPager)paramView).removeView((View)this.views.get(paramInt));
    }

    public int getCount()
    {
        return this.views.size();
    }

    public Object instantiateItem(View paramView, int paramInt)
    {
        ((ViewPager)paramView).addView((View)this.views.get(paramInt));
        return this.views.get(paramInt);
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
        return paramView == paramObject;
    }
}
