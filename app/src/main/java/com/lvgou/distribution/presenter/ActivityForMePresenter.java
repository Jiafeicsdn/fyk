package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityForMeImpl;
import com.lvgou.distribution.view.ActivityForMeView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ActivityForMePresenter extends BasePresenter<ActivityForMeView> {
    private ActivityForMeImpl activityForMeImpl;
    private ActivityForMeView activityForMeView;
    private Handler mHandler;

    public ActivityForMePresenter(ActivityForMeView activityForMeView) {
        this.activityForMeView = activityForMeView;
        activityForMeImpl = new ActivityForMeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动列表-我的活动
     *
     * @param distributorid 导游ID
     * @param type          类型 0=全部，1=进行中，2=已结束
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void activityForMeDatas(String distributorid, int type, int pageindex, String sign) {
        activityForMeImpl.activityForMeDatas(distributorid, type, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityForMeView.closeActivityForMeProgress();
                        activityForMeView.OnActivityForMeSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityForMeView.closeActivityForMeProgress();
                        activityForMeView.OnActivityForMeFialCallBack("1", s);
                    }
                });
            }
        });
    }

}