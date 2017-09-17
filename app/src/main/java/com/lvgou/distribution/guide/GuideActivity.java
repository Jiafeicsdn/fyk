package com.lvgou.distribution.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.LoginActivity;
import com.lvgou.distribution.adapter.ViewPagerAdapter;
import com.lvgou.distribution.view.MyViewPage;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends Activity implements OnPageChangeListener {
    private MyViewPage vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private GestureDetector mGestureDetector;
    private ImageView img_view;

    private View view;
    int postion_ = 0;
    private int lastX;
    private int lastY;
    private LinearLayout enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initView();
        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());
    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guide_one, null));
        views.add(inflater.inflate(R.layout.guide_two, null));
//        views.add(inflater.inflate(R.layout.guide_thre, null));
        views.add(inflater.inflate(R.layout.guider_five, null));
        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (MyViewPage) findViewById(R.id.viewPager);
        vp.setAdapter(vpAdapter);
        img_view = (ImageView) views.get(2).findViewById(R.id.img);
        enter = (LinearLayout) views.get(2).findViewById(R.id.ll_go);
        enter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });
        vp.setOnPageChangeListener(this);

        img_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                // 一定要返回true，不然获取不到完整的事件
                return true;
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public void onPageSelected(int postion) {
        postion_ = postion;
    }


    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if ((e1.getX() - e2.getX()) > 10) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";
                break;
            }
            default:
                break;
        }
        return name;
    }
}
