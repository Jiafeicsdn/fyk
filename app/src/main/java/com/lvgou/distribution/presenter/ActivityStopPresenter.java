package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityStopImpl;
import com.lvgou.distribution.view.ActivityStopView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ActivityStopPresenter extends BasePresenter<ActivityStopView> {
    private ActivityStopImpl activityStopImpl;
    private ActivityStopView activityStopView;
    private Handler mHandler;

    public ActivityStopPresenter(ActivityStopView activityStopView) {
        this.activityStopView = activityStopView;
        activityStopImpl = new ActivityStopImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动停止
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    public void activityStop(String distributorid, String activityid, String sign) {
        activityStopImpl.activityStop(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityStopView.closeActivityStopProgress();
                        activityStopView.OnActivityStopSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityStopView.closeActivityStopProgress();
                        activityStopView.OnActivityStopFialCallBack("1", s);
                    }
                });
            }
        });
    }

}