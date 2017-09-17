package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.StudyShareImpl;
import com.lvgou.distribution.view.StudyShareView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class StudySharePresenter extends BasePresenter<StudyShareView> {
    private StudyShareImpl studyShareImpl;
    private StudyShareView studyShareView;
    private Handler mHandler;

    public StudySharePresenter(StudyShareView studyShareView) {
        this.studyShareView = studyShareView;
        studyShareImpl = new StudyShareImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 每日课程分享奖励团币
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void studyShare(String distributorid, String sign) {
        studyShareImpl.studyShare(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studyShareView.closeStudyShareProgress();
                        studyShareView.OnStudyShareSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studyShareView.closeStudyShareProgress();
                        studyShareView.OnStudyShareFialCallBack("1", s);
                    }
                });
            }
        });
    }

}