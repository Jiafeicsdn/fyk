package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.StudyClassifyImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.StudyClassifyView;

/**
 * Created by Administrator on 2017/3/13.
 */

public class StudyClassifyPresenter extends BasePresenter<StudyClassifyView> {
    private StudyClassifyImpl studyClassifyImpl;
    private StudyClassifyView studyClassifyView;
    private Handler mHandler;

    public StudyClassifyPresenter(StudyClassifyView studyClassifyView) {
        this.studyClassifyView = studyClassifyView;
        studyClassifyImpl = new StudyClassifyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动列表
     *
     * @param distributorid 导游ID
     * @param label         标签路径
     * @param sign          签名
     * @return
     */
    public void studyClassifyDatas(String distributorid, String label, String sign) {
        studyClassifyImpl.studyClassifyDatas(distributorid, label, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studyClassifyView.closeProgress();
                        studyClassifyView.OnStudyClassifySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studyClassifyView.closeProgress();
                        studyClassifyView.OnStudyClassifyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
