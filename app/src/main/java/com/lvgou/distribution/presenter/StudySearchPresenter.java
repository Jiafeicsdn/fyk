package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.StudySearchImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.StudySearchView;

/**
 * Created by Administrator on 2017/3/14.
 */

public class StudySearchPresenter extends BasePresenter<StudySearchView> {
    private StudySearchImpl studySearchImpl;
    private StudySearchView studySearchView;
    private Handler mHandler;

    public StudySearchPresenter(StudySearchView studySearchView) {
        this.studySearchView = studySearchView;
        studySearchImpl = new StudySearchImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 全局搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void studySearchDatas(String distributorid, String searchword, int pageindex, String sign) {
        studySearchImpl.studySearchDatas(distributorid, searchword, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studySearchView.closeStudySearchProgress();
                        studySearchView.OnStudySearchSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studySearchView.closeStudySearchProgress();
                        studySearchView.OnStudySearchFialCallBack("1", s);
                    }
                });
            }
        });
    }

}