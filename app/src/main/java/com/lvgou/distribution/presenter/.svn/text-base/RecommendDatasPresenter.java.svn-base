package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.RecommendDatasImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.RecommendDatasView;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RecommendDatasPresenter extends BasePresenter<RecommendDatasView> {
    private RecommendDatasImpl recommendDatasImpl;
    private RecommendDatasView recommendDatasView;
    private Handler mHandler;

    public RecommendDatasPresenter(RecommendDatasView recommendDatasView) {
        this.recommendDatasView = recommendDatasView;
        recommendDatasImpl = new RecommendDatasImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *  精品课程
     * @param distributorid 导游ID
     * @param sign          签名
     */
    public void recommendDatas(String distributorid, String sign) {
        recommendDatasImpl.recommendDatas(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendDatasView.closeProgress();
                        recommendDatasView.OnRecommendDatasSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendDatasView.closeProgress();
                        recommendDatasView.OnRecommendDatasFialCallBack("1", s);
                    }
                });
            }
        });
    }

}