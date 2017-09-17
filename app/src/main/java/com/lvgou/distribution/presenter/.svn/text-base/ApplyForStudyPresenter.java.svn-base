package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ApplyForStudyImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.ApplyForStudyView;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ApplyForStudyPresenter extends BasePresenter<ApplyForStudyView> {
    private ApplyForStudyImpl applyForStudyImpl;
    private ApplyForStudyView applyForStudyView;
    private Handler mHandler;

    public ApplyForStudyPresenter(ApplyForStudyView applyForStudyView) {
        this.applyForStudyView = applyForStudyView;
        applyForStudyImpl = new ApplyForStudyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 申请开课-提交
     *
     * @param distributorid 导游ID
     * @param theme         主题
     * @param starttime     直播时间(2017-3-13 18:30)
     * @param label         课程分类(103001,103002)
     * @param crowd         适合人群
     * @param themeinfo     主题简介
     * @param zbtype        开课形式 1=直播，2=录播
     * @param apply         报名团币 可为0
     * @param look          查看团币 可为0
     * @param sign          签名
     * @return
     */
    public void applyForStudy(String distributorid, String theme, String starttime, String label, String crowd, String themeinfo, String zbtype, String apply, String look, String sign) {
        applyForStudyImpl.applyForStudy(distributorid, theme, starttime, label, crowd, themeinfo, zbtype, apply, look, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyForStudyView.closeApplyForStudyProgress();
                        applyForStudyView.OnApplyForStudySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyForStudyView.closeApplyForStudyProgress();
                        applyForStudyView.OnApplyForStudyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}