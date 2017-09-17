package com.lvgou.distribution.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lvgou.distribution.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 * com.lvgou.distribution.view.FlowLayoutView
 */
public class FlowLayoutView extends ViewGroup {
    private List<Line> lines = new ArrayList<>();
    private Line currentLine;//当前行
    private int useWidth;// 当前行使用的宽度
    private int HORIZONTAL_SPACING = UIUtils.dip2px(13);//横向的间隔
    private int VERTICAL_SPACING =  UIUtils.dip2px(13); //行间距

    public FlowLayoutView(Context context) {
        super(context);
    }

    public FlowLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /*测量   父view是有义务测量所有的子view*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        currentLine = null;
        useWidth = 0;

        //widthMeasureSpec 宽的尺子    heightMeasureSpec 高的尺子 测量规则
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//宽的模式   什么 模式 不清楚
//        MeasureSpec.AT_MOST;// wrap_content
//         MeasureSpec.EXACTLY // match_parent  100dp
//        MeasureSpec.UNSPECIFIED;// 未指定
        int width = MeasureSpec.getSize(widthMeasureSpec) -getPaddingLeft() - getPaddingRight();//宽的值

        // 想要 制定 子view 的测量规则   子view  mode
        //1.  父view 的mode  是 EXACTLY  子view 的mode  一定是 AT_MOST
        // 2. 父view 的mode 不是 EXACTLY  子view  的mode  一定和父view  一样
        int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;//子view 的宽的模式
/*        if (widthMode == MeasureSpec.EXACTLY){
            childeWidthMode = MeasureSpec.AT_MOST;
        }else{
            childeWidthMode = widthMode;
        }*/

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//高的模式
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();//高的值
        int childHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;

        // mode + size
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, childWidthMode);
        //高的测量规则
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, childHeightMode);

        // 测量所有的子view
        currentLine = new Line();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            // 获取子view 的宽和高
            int childWidth = child.getMeasuredWidth();
            useWidth += childWidth;
            if (useWidth <= width) {
                //可以放
                currentLine.addView(child);
                useWidth += HORIZONTAL_SPACING;//横向的间隔
                if (useWidth <= width) {
                    //  什么都不做
                } else {
                    //  换行
                    newLine();
                }
            } else {
                //换行
                newLine();
                //放进去之前的元素
                currentLine.addView(child);
                useWidth += childWidth;
            }
        }

        //最后一行 添加进去
        if (!lines.contains(currentLine)) {
            lines.add(currentLine);
        }


        int totalHeight = 0;//总高度
        for (int i = 0; i < lines.size(); i++) {
            totalHeight += lines.get(i).getHeight();
        }
        totalHeight += (lines.size() - 1) * VERTICAL_SPACING;
        // 指定子view 的测量规则  尺子
        //存储当前view  的宽& 高
        setMeasuredDimension(width+getPaddingLeft()+getPaddingRight(), totalHeight+getPaddingTop() + getPaddingBottom());
    }

    /*换行*/
    private void newLine() {
        lines.add(currentLine);
        currentLine = new Line();//创建新行
        useWidth = 0;//当前行使用的宽度
    }


    /**
     * 放置子view
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        t += getPaddingTop();
        l+= getPaddingLeft();
//摆放行
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.layout(l, t);//放置行
            t += line.getHeight();//行高
            t += VERTICAL_SPACING;//行间距
        }
    }

    //行
    class Line {
        List<View> children = new ArrayList<>();
        private int height;
        private int width;//当前行实际的宽度

        public void addView(View child) {
            children.add(child);
            if (height < child.getMeasuredHeight())
                height = child.getMeasuredHeight();
            width += child.getMeasuredWidth();
        }

        /**
         * 获取行高
         *
         * @return
         */
        public int getHeight() {
            return height;
        }

        /**
         * 放置行
         *
         * @param l
         * @param t
         */
        public void layout(int l, int t) {
            // 计算剩余宽度
            int ramind = getMeasuredWidth() -getPaddingLeft() - getPaddingRight()- width - (children.size() - 1) * HORIZONTAL_SPACING;
            //平均分配的宽度
            int r = ramind / children.size();


            for (int i = 0; i < children.size(); i++) {
                View view = children.get(i);
                view.layout(l, t, l + view.getMeasuredWidth() + r, t + view.getMeasuredHeight());
                l += view.getMeasuredWidth();
                l += HORIZONTAL_SPACING;
                l += r;
            }
        }
    }
}
