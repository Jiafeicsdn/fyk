package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.StudySearchloadImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.StudySearchloadView;

/**
 * Created by Administrator on 2017/3/14.
 */

public class StudySearchloadPresenter extends BasePresenter<StudySearchloadView> {
    private StudySearchloadImpl studySearchloadImpl;
    private StudySearchloadView studySearchloadView;
    private Handler mHandler;

    public StudySearchloadPresenter(StudySearchloadView studySearchloadView) {
        this.studySearchloadView = studySearchloadView;
        studySearchloadImpl = new StudySearchloadImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 全局搜索-加载
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    public void studySearchloadDatas(String distributorid, String sign) {
        studySearchloadImpl.studySearchloadDatas(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studySearchloadView.closeStudySearchloadProgress();
                        studySearchloadView.OnStudySearchloadSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studySearchloadView.closeStudySearchloadProgress();
                        studySearchloadView.OnStudySearchloadFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
