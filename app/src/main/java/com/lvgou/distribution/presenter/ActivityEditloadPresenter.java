package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityEditloadImpl;
import com.lvgou.distribution.view.ActivityEditloadView;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ActivityEditloadPresenter extends BasePresenter<ActivityEditloadView> {
    private ActivityEditloadImpl activityEditloadImpl;
    private ActivityEditloadView activityEditloadView;
    private Handler mHandler;

    public ActivityEditloadPresenter(ActivityEditloadView activityEditloadView) {
        this.activityEditloadView = activityEditloadView;
        activityEditloadImpl = new ActivityEditloadImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动编辑加载
     *
     * @param distributorid
     * @param activityid    活动ID
     * @param sign
     * @return
     */
    public void activityEditload(String distributorid, String activityid, String sign) {
        activityEditloadImpl.activityEditload(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityEditloadView.closeActivityEditloadProgress();
                        activityEditloadView.OnActivityEditloadSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityEditloadView.closeActivityEditloadProgress();
                        activityEditloadView.OnActivityEditloadFialCallBack("1", s);
                    }
                });
            }
        });
    }

}