package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CancelApplyActImpl;
import com.lvgou.distribution.view.CancelApplyActView;

/**
 * Created by Administrator on 2017/4/1.
 */

public class CancelApplyActPresenter extends BasePresenter<CancelApplyActView> {
    private CancelApplyActImpl cancelApplyActImpl;
    private CancelApplyActView cancelApplyActView;
    private Handler mHandler;

    public CancelApplyActPresenter(CancelApplyActView cancelApplyActView) {
        this.cancelApplyActView = cancelApplyActView;
        cancelApplyActImpl = new CancelApplyActImpl();
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
    public void cancelApply(String distributorid, String activityid, String sign) {
        cancelApplyActImpl.cancelApply(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cancelApplyActView.closeCancelApplyActProgress();
                        cancelApplyActView.OnCancelApplyActSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cancelApplyActView.closeCancelApplyActProgress();
                        cancelApplyActView.OnCancelApplyActFialCallBack("1", s);
                    }
                });
            }
        });
    }

}