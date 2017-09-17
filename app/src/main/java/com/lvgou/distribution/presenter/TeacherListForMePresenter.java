package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherListForMeImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.TeacherListForMeView;

/**
 * Created by Administrator on 2017/3/24.
 */

public class TeacherListForMePresenter extends BasePresenter<TeacherListForMeView> {
    private TeacherListForMeImpl teacherListForMeImpl;
    private TeacherListForMeView teacherListForMeView;
    private Handler mHandler;

    public TeacherListForMePresenter(TeacherListForMeView teacherListForMeView) {
        this.teacherListForMeView = teacherListForMeView;
        teacherListForMeImpl = new TeacherListForMeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我开的课
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void teacherListForMeDatas(String distributorid, int pageindex, String sign) {
        teacherListForMeImpl.teacherListForMeDatas(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherListForMeView.closeTeacherListForMeProgress();
                        teacherListForMeView.OnTeacherListForMeSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherListForMeView.closeTeacherListForMeProgress();
                        teacherListForMeView.OnTeacherListForMeFialCallBack("1", s);
                    }
                });
            }
        });
    }

}