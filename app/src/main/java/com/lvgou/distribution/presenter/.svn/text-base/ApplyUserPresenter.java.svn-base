package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ApplyUserImpl;
import com.lvgou.distribution.view.ApplyUserView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ApplyUserPresenter extends BasePresenter<ApplyUserView> {
    private ApplyUserImpl applyUserImpl;
    private ApplyUserView applyUserView;
    private Handler mHandler;

    public ApplyUserPresenter(ApplyUserView applyUserView) {
        this.applyUserView = applyUserView;
        applyUserImpl = new ApplyUserImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动-报名人员
     *
     * @param activityid
     * @param pageindex
     * @param keyword
     * @param sign
     * @return
     */
    public void applyUser(String activityid, int pageindex, String keyword, String sign) {
        applyUserImpl.applyUser(activityid, pageindex, keyword, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyUserView.closeApplyUserProgress();
                        applyUserView.OnApplyUserSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyUserView.closeApplyUserProgress();
                        applyUserView.OnApplyUserFialCallBack("1", s);
                    }
                });
            }
        });
    }

}