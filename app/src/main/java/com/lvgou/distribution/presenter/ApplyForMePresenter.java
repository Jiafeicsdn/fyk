package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ApplyForMeImpl;
import com.lvgou.distribution.view.ApplyForMeView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ApplyForMePresenter extends BasePresenter<ApplyForMeView> {
    private ApplyForMeImpl applyForMeImpl;
    private ApplyForMeView applyForMeView;
    private Handler mHandler;

    public ApplyForMePresenter(ApplyForMeView applyForMeView) {
        this.applyForMeView = applyForMeView;
        applyForMeImpl = new ApplyForMeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动列表-我报名的活动
     *
     * @param distributorid 导游ID
     * @param type          类型 0=全部，1=进行中，2=已结束
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void applyForMeDatas(String distributorid, int type, int pageindex, String sign) {
        applyForMeImpl.applyForMeDatas(distributorid, type, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyForMeView.closeApplyForMeProgress();
                        applyForMeView.OnApplyForMeSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyForMeView.closeApplyForMeProgress();
                        applyForMeView.OnApplyForMeFialCallBack("1", s);
                    }
                });
            }
        });
    }

}