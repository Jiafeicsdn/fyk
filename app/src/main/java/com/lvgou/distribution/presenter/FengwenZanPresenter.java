package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FengwenZanImpl;
import com.lvgou.distribution.view.FengwenZanView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FengwenZanPresenter extends BasePresenter<FengwenZanView> {
    private FengwenZanImpl fengwenZanImpl;
    private FengwenZanView fengwenZanView;
    private Handler mHandler;

    public FengwenZanPresenter(FengwenZanView fengwenZanView) {
        this.fengwenZanView = fengwenZanView;
        fengwenZanImpl = new FengwenZanImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 点赞
     *
     * @param distributorId 导游编号
     * @param talkId        点赞的蜂文编号
     * @param sign          签名
     * @return
     */
    public void fengwenZan(String distributorId, String talkId, String sign) {
        fengwenZanImpl.fengwenZan(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenZanView.closeFengwenZanProgress();
                        fengwenZanView.OnFengwenZanSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenZanView.closeFengwenZanProgress();
                        fengwenZanView.OnFengwenZanFialCallBack("1", s);
                    }
                });
            }
        });
    }

}