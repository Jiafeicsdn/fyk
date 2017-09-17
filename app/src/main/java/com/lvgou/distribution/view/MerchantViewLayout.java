package com.lvgou.distribution.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CollegeSearchActivity;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.DensityUtil;

/**
 * Created by jiangjieqiang on 16/1/18.
 */
public class MerchantViewLayout extends LinearLayout{

    private View topView;
    private DragScaleImageView drag_imgview;
    private HorizontalListView menu;
    private ViewPager viewPager;
    private View view_line;
    private ListView listView;
    private PullToRefreshListView pull_refresh_list;
    private OverScroller scroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private int distanceFromViewPagerToX;
    private float mLastY;

    private boolean mDragging;
    private boolean isInControl = false;
    private boolean isTopHidden = false;
    private int scrollY=0;
    private Context context;
private LayoutScrollerListener scrollerListener;
    public MerchantViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        this.context=context;
        scroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

    }
public void setListener(LayoutScrollerListener scrollerListener){
    this.scrollerListener=scrollerListener;
}
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.layout_topView);
        drag_imgview=(DragScaleImageView)findViewById(R.id.drag_imgview);
        viewPager = (ViewPager)findViewById(R.id.id_viewpager);
        view_line=(View)findViewById(R.id.view_line);
        menu = (HorizontalListView)findViewById(R.id.id_horizontalmenu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - menu.getMeasuredHeight()-view_line.getMeasuredHeight()- 80;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        distanceFromViewPagerToX = topView.getMeasuredHeight()-view_line.getMeasuredHeight()-80;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                    scroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                //判断是滑动还是点击
                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }

                if (mDragging) {
                    scrollBy(0, (int) -dy);

                    // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    if (getScrollY() == distanceFromViewPagerToX && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }

                mLastY = y;
            break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                //初始化
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当滑动速度比较大的时候，实现快速滑动
     * @param velocityY
     */
    public void fling(int velocityY) {
        //
        scroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, distanceFromViewPagerToX);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > distanceFromViewPagerToX) {
            y = distanceFromViewPagerToX;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        scrollY= getScrollY();
        if(scrollY<=distanceFromViewPagerToX&&scrollY!=0){
            scrollerListener.showTopView(y,distanceFromViewPagerToX);
        }else if(scrollY==0){
            scrollerListener.hideTopView();
        }
        isTopHidden = getScrollY() == distanceFromViewPagerToX;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentListView();
                View view = listView.getChildAt(listView.getFirstVisiblePosition());
                if (!isInControl && view != null && view.getTop() == 0 && isTopHidden && dy > 0) {
                    isInControl = true;
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    MotionEvent ev2 = MotionEvent.obtain(ev);
                    dispatchTouchEvent(ev);
                    ev2.setAction(MotionEvent.ACTION_DOWN);
                    return dispatchTouchEvent(ev2);
                }
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    private void getCurrentListView() {
        int currentItem = viewPager.getCurrentItem();
        PagerAdapter a = viewPager.getAdapter();
        FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
        Fragment item = (Fragment) fadapter.instantiateItem(viewPager,
                currentItem);
        try {
            pull_refresh_list = (PullToRefreshListView) (item.getView().findViewById(R.id.pull_refresh_list));
            listView = pull_refresh_list.getRefreshableView();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                    getCurrentListView();
                    if (Math.abs(dy) > mTouchSlop) {
                        //滑动
                        mDragging = true;

                        View view = listView.getChildAt(listView.getFirstVisiblePosition());
                        // 拦截条件：topView没有隐藏
                        // 或listView在顶部 && topView隐藏 && 下拉
                        if (!isTopHidden && dy < 0 || !isTopHidden && view != null && view.getTop() == 0 || (view != null && view.getTop() == 0 && isTopHidden && dy > 0)) {
                            if (view != null && view.getTop() == 0 && scrollY == 0 && dy > 0) {
                                drag_imgview.onTouchEvent(ev);
                            } else {
                                initVelocityTrackerIfNotExists();
                                mLastY = y;
                                mVelocityTracker.addMovement(ev);
                                return true;
                            }

                        }
                    }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
public interface LayoutScrollerListener{
    public void showTopView(int y, int height);
    public void hideTopView();
}
}
