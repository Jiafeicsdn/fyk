package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ActivitySubmitImpl;
import com.lvgou.distribution.view.ActivitySubmitView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ActivitySubmitPresenter extends BasePresenter<ActivitySubmitView> {
    private ActivitySubmitImpl activitySubmitImpl;
    private ActivitySubmitView activitySubmitView;
    private Handler mHandler;

    public ActivitySubmitPresenter(ActivitySubmitView activitySubmitView) {
        this.activitySubmitView = activitySubmitView;
        activitySubmitImpl = new ActivitySubmitImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 发布活动
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID(发布新活动传0)
     * @param picurl        活动图片路径
     * @param title         活动标题
     * @param starttime     活动开始时间 格式:2017-3-10 10:00
     * @param endtime       活动结束时间 格式:2017-3-10 10:00
     * @param countrypath   城市节点
     * @param address       详细地址
     * @param maxpeople     活动上限人数
     * @param info          活动简介
     * @param sign          签名
     */
    public void activitySubmitDatas(String distributorid, String activityid, String picurl, String title, String starttime, String endtime, String countrypath, String address, int maxpeople, String info, String sign) {
        activitySubmitImpl.activitySubmitDatas(distributorid, activityid, picurl, title, starttime, endtime, countrypath, address, maxpeople, info, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activitySubmitView.closeActivitySubmitProgress();
                        activitySubmitView.OnActivitySubmitSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        activitySubmitView.closeActivitySubmitProgress();
                        activitySubmitView.OnActivitySubmitFialCallBack("1", s);
                    }
                });
            }
        });
    }

}