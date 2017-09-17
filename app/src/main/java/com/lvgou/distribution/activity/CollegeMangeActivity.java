package com.lvgou.distribution.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.fragment.AllClassesFragment;
import com.lvgou.distribution.fragment.RecommendBeeFragment;
import com.lvgou.distribution.fragment.TeacherSeatFragment;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/19.
 * 学院首页
 */
public class CollegeMangeActivity extends BaseActivity {

    @ViewInject(R.id.tv_recommend)
    private TextView tv_recommend;
    @ViewInject(R.id.tv_classes)
    private TextView tv_classes;
    @ViewInject(R.id.tv_teacher)
    private TextView tv_teacher;

    private RecommendBeeFragment recommendBeeFragment;
    private AllClassesFragment allClassesFragment;
    private TeacherSeatFragment teacherSeatFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_manger);
        ViewUtils.inject(this);
        fragmentManager = getFragmentManager();
        EventBus.getDefault().register(this);
        initSelected();
        tv_recommend.setTextColor(getResources().getColor(R.color.bg_white));
        tv_recommend.setBackgroundResource(R.drawable.bg_college_balck_shape);
        selectTab(1);
    }

    /**
     * 按钮 切换事件
     *
     * @param view
     */
    @OnClick({R.id.tv_recommend, R.id.tv_classes, R.id.tv_teacher})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recommend:
                initSelected();
                tv_recommend.setTextColor(getResources().getColor(R.color.bg_white));
                tv_recommend.setBackgroundResource(R.drawable.bg_college_balck_shape);
                selectTab(1);
                Constants.SELECTE_POSITION06 = "全部";
                break;
            case R.id.tv_classes:
                initSelected();
                tv_classes.setTextColor(getResources().getColor(R.color.bg_white));
                tv_classes.setBackgroundResource(R.drawable.bg_college_balck_shape);
                selectTab(2);
                break;
            case R.id.tv_teacher:
                initSelected();
                tv_teacher.setTextColor(getResources().getColor(R.color.bg_white));
                tv_teacher.setBackgroundResource(R.drawable.bg_college_balck_shape);
                selectTab(3);
                Constants.SELECTE_POSITION06 = "全部";
                break;
        }
    }

    /**
     * 初始化，选中状态
     */
    public void initSelected() {
        tv_recommend.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_classes.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_teacher.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_recommend.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_classes.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_teacher.setBackgroundResource(R.drawable.bg_college_white_shape);
    }

    /**
     * 选中切换
     *
     * @param tabs
     */
    public void selectTab(int tabs) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabs) {
            case 1:
                if (recommendBeeFragment == null) {
                    recommendBeeFragment = new RecommendBeeFragment();
                    transaction.add(R.id.content, recommendBeeFragment);
                } else {
                    transaction.show(recommendBeeFragment);
                }
                break;
            case 2:
                if (allClassesFragment == null) {
                    allClassesFragment = new AllClassesFragment();
                    transaction.add(R.id.content, allClassesFragment);
                } else {
                    transaction.show(allClassesFragment);
                }
                break;
            case 3:
                if (teacherSeatFragment == null) {
                    teacherSeatFragment = new TeacherSeatFragment();
                    transaction.add(R.id.content, teacherSeatFragment);
                } else {
                    transaction.show(teacherSeatFragment);
                }
                break;
        }
        transaction.commit();
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    public void hideFragments(FragmentTransaction transaction) {
        if (recommendBeeFragment != null) {
            transaction.hide(recommendBeeFragment);
        }

        if (allClassesFragment != null) {
            transaction.hide(allClassesFragment);
        }

        if (teacherSeatFragment != null) {
            transaction.hide(teacherSeatFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.TURN_CLASSES_FRAGMENT) {
            initSelected();
            tv_recommend.setTextColor(getResources().getColor(R.color.bg_white));
            tv_recommend.setBackgroundResource(R.drawable.bg_college_balck_shape);
            selectTab(1);
        } else if (event.getEventType() == EventType.TURN_TECHER_FRGMENT) {
            initSelected();
            tv_teacher.setTextColor(getResources().getColor(R.color.bg_white));
            tv_teacher.setBackgroundResource(R.drawable.bg_college_balck_shape);
            selectTab(3);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}