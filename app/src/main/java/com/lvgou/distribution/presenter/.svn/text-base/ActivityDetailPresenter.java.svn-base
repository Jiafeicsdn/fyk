package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivityDetailImpl;
import com.lvgou.distribution.view.ActivityDetailView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;


/**
 * Created by Administrator on 2017/3/23.
 */

public class ActivityDetailPresenter extends BasePresenter<ActivityDetailView> {
    private ActivityDetailImpl activityDetailImpl;
    private ActivityDetailView activityDetailView;
    private Handler mHandler;

    public ActivityDetailPresenter(ActivityDetailView activityDetailView) {
        this.activityDetailView = activityDetailView;
        activityDetailImpl = new ActivityDetailImpl();
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
    public void activityDetailDatas(String distributorid, String activityid, String sign) {
        activityDetailImpl.activityDetailDatas(distributorid, activityid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityDetailView.closeActivityDetailProgress();
                        activityDetailView.OnActivityDetailSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityDetailView.closeActivityDetailProgress();
                        activityDetailView.OnActivityDetailFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
