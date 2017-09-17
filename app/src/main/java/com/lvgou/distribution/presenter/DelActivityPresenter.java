package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DelActivityImpl;
import com.lvgou.distribution.view.DelActivityView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DelActivityPresenter extends BasePresenter<DelActivityView> {
    private DelActivityImpl delActivityImpl;
    private DelActivityView delActivityView;
    private Handler mHandler;

    public DelActivityPresenter(DelActivityView delActivityView) {
        this.delActivityView = delActivityView;
        delActivityImpl = new DelActivityImpl();
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
    public void delActivity(String distributorid, String activityid, String sign) {
        delActivityImpl.delActivity(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delActivityView.closeDelActivityProgress();
                        delActivityView.OnDelActivitySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delActivityView.closeDelActivityProgress();
                        delActivityView.OnDelActivityFialCallBack("1", s);
                    }
                });
            }
        });
    }

}