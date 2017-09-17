package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherDownloadListImpl;
import com.lvgou.distribution.view.TeacherDownloadListView;

/**
 * Created by Administrator on 2017/4/20.
 */

public class TeacherDownloadListPresenter extends BasePresenter<TeacherDownloadListView> {
    private TeacherDownloadListImpl teacherDownloadListImpl;
    private TeacherDownloadListView teacherDownloadListView;
    private Handler mHandler;

    public TeacherDownloadListPresenter(TeacherDownloadListView teacherDownloadListView) {
        this.teacherDownloadListView = teacherDownloadListView;
        teacherDownloadListImpl = new TeacherDownloadListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我的课程-可下载列表
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void teacherDownloadList(String distributorid, String sign) {
        teacherDownloadListImpl.teacherDownloadList(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherDownloadListView.closeTeacherDownloadListProgress();
                        teacherDownloadListView.OnTeacherDownloadListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherDownloadListView.closeTeacherDownloadListProgress();
                        teacherDownloadListView.OnTeacherDownloadListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}