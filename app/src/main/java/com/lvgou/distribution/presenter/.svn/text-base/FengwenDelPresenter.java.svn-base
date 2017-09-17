package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FengwenDelImpl;
import com.lvgou.distribution.view.FengwenDelView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FengwenDelPresenter extends BasePresenter<FengwenDelView> {
    private FengwenDelImpl fengwenDelImpl;
    private FengwenDelView fengwenDelView;
    private Handler mHandler;

    public FengwenDelPresenter(FengwenDelView fengwenDelView) {
        this.fengwenDelView = fengwenDelView;
        fengwenDelImpl = new FengwenDelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂文删除
     *
     * @param distributorId 导游编号
     * @param talkId        蜂文编号
     * @param sign          签名
     * @return
     */
    public void fengwenDel(String distributorId, String talkId, String sign) {
        fengwenDelImpl.fengwenDel(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenDelView.closeFengwenDelProgress();
                        fengwenDelView.OnFengwenDelSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengwenDelView.closeFengwenDelProgress();
                        fengwenDelView.OnFengwenDelFialCallBack("1", s);
                    }
                });
            }
        });
    }

}