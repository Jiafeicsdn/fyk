package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityImpl;
import com.lvgou.distribution.view.ActivityView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ActivityPresenter extends BasePresenter<ActivityView> {
    private ActivityImpl activityImpl;
    private ActivityView activityView;
    private Handler mHandler;

    public ActivityPresenter(ActivityView activityView) {
        this.activityView = activityView;
        activityImpl = new ActivityImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动列表
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void activityDatas(String distributorid, int pageindex,String sign) {
        activityImpl.activityDatas(distributorid, pageindex,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityView.closeProgress();
                        activityView.OnActivitySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityView.closeProgress();
                        activityView.OnActivityFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
