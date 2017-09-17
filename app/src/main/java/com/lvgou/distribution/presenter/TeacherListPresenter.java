package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherListImpl;
import com.lvgou.distribution.view.TeacherListView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class TeacherListPresenter extends BasePresenter<TeacherListView> {
    private TeacherListImpl teacherListImpl;
    private TeacherListView teacherListView;
    private Handler mHandler;

    public TeacherListPresenter(TeacherListView teacherListView) {
        this.teacherListView = teacherListView;
        teacherListImpl = new TeacherListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂优讲师
     *
     * @param distributorid 讲课Id
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void teacherList(String distributorid, int pageindex, String sign) {
        teacherListImpl.teacherList(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherListView.closeTeacherListProgress();
                        teacherListView.OnTeacherListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherListView.closeTeacherListProgress();
                        teacherListView.OnTeacherListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}