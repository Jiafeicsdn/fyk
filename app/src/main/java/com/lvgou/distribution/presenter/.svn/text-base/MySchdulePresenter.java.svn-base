package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyScheduleImpl;
import com.lvgou.distribution.view.MyScheduleView;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MySchdulePresenter extends BasePresenter<MyScheduleView> {

    private MyScheduleView myScheduleView;
    private MyScheduleImpl myScheduleImpl;
    private Handler mHandler;

    public MySchdulePresenter(MyScheduleView myScheduleView) {
        this.myScheduleView = myScheduleView;
        myScheduleImpl = new MyScheduleImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取课程列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getMySchedule(String distributorid, String pageindex, String sign) {
        myScheduleImpl.getMyClass(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myScheduleView.closeProgress();
                        myScheduleView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myScheduleView.closeProgress();
                        myScheduleView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 课程评分
     *
     * @param distributorid
     * @param studyid
     * @param grade
     * @param comment
     * @param sign
     */
    public void doGradeSchedule(String distributorid, String studyid, String grade, String comment, String sign) {
        myScheduleImpl.doGrade(distributorid, studyid, grade, comment, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myScheduleView.closeProgress();
                        myScheduleView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myScheduleView.closeProgress();
                        myScheduleView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }
}
