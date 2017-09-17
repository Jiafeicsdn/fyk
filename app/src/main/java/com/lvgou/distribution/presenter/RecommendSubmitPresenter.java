package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.RecommendSubmitImpl;
import com.lvgou.distribution.view.RecommendSubmitView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class RecommendSubmitPresenter extends BasePresenter<RecommendSubmitView> {
    private RecommendSubmitImpl recommendSubmitImpl;
    private RecommendSubmitView recommendSubmitView;
    private Handler mHandler;

    public RecommendSubmitPresenter(RecommendSubmitView recommendSubmitView) {
        this.recommendSubmitView = recommendSubmitView;
        recommendSubmitImpl = new RecommendSubmitImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 推荐讲师-提交
     *
     * @param distributorid
     * @param teachername
     * @param weixin
     * @param mobile
     * @param intro
     * @param sign
     * @return
     */
    public void recommendSubmit(String distributorid, String teachername, String weixin, String mobile, String intro, String sign) {
        recommendSubmitImpl.recommendSubmit(distributorid, teachername, weixin, mobile, intro, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendSubmitView.closeRecommendSubmitProgress();
                        recommendSubmitView.OnRecommendSubmitSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendSubmitView.closeRecommendSubmitProgress();
                        recommendSubmitView.OnRecommendSubmitFialCallBack("1", s);
                    }
                });
            }
        });
    }

}