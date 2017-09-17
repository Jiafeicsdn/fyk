package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CompetitiveStudyImpl;
import com.lvgou.distribution.view.CompetitiveStudyView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/10.
 */

public class CompetitiveStudyPresenter extends BasePresenter<CompetitiveStudyView> {
    private CompetitiveStudyImpl competitiveStudyImpl;
    private CompetitiveStudyView competitiveStudyView;
    private Handler mHandler;

    public CompetitiveStudyPresenter(CompetitiveStudyView competitiveStudyView) {
        this.competitiveStudyView = competitiveStudyView;
        competitiveStudyImpl = new CompetitiveStudyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *  精品课程
     * @param distributorid 导游ID
     * @param sign          签名
     */
    public void competitiveStudy(String distributorid, String sign) {
        competitiveStudyImpl.competitiveStudy(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        competitiveStudyView.closeProgress();
                        competitiveStudyView.OnCompetitiveStudySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        competitiveStudyView.closeProgress();
                        competitiveStudyView.OnCompetitiveStudyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
