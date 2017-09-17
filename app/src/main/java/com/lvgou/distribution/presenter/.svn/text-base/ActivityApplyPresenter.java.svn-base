package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityApplyImpl;
import com.lvgou.distribution.view.ActivityApplyView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ActivityApplyPresenter extends BasePresenter<ActivityApplyView> {
    private ActivityApplyImpl activityApplyImpl;
    private ActivityApplyView activityApplyView;
    private Handler mHandler;

    public ActivityApplyPresenter(ActivityApplyView activityApplyView) {
        this.activityApplyView = activityApplyView;
        activityApplyImpl = new ActivityApplyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动详情
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    public void activityApply(String distributorid, String activityid, String sign) {
        activityApplyImpl.activityApply(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityApplyView.closeActivityApplyProgress();
                        activityApplyView.OnActivityApplySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityApplyView.closeActivityApplyProgress();
                        activityApplyView.OnActivityApplyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}