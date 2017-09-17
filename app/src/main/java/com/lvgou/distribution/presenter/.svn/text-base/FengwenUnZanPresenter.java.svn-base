package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FengwenUnZanImpl;
import com.lvgou.distribution.view.FengwenUnZanView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FengwenUnZanPresenter extends BasePresenter<FengwenUnZanView> {
    private FengwenUnZanImpl fengwenUnZanImpl;
    private FengwenUnZanView fengwenUnZanView;
    private Handler mHandler;

    public FengwenUnZanPresenter(FengwenUnZanView fengwenUnZanView) {
        this.fengwenUnZanView = fengwenUnZanView;
        fengwenUnZanImpl = new FengwenUnZanImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 取消点赞
     *
     * @param distributorId 导游编号
     * @param talkId        蜂文编号
     * @param sign          签名
     * @return
     */
    public void fengwenUnZan(String distributorId, String talkId, String sign) {
        fengwenUnZanImpl.fengwenUnZan(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenUnZanView.closeFengwenUnZanProgress();
                        fengwenUnZanView.OnFengwenUnZanSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenUnZanView.closeFengwenUnZanProgress();
                        fengwenUnZanView.OnFengwenUnZanFialCallBack("1", s);
                    }
                });
            }
        });
    }

}