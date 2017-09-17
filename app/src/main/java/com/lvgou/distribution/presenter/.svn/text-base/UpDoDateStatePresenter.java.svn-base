package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.UpDoDateStateImpl;
import com.lvgou.distribution.view.UpDoDateStateView;

/**
 * Created by Administrator on 2017/4/19.
 */

public class UpDoDateStatePresenter extends BasePresenter<UpDoDateStateView> {
    private UpDoDateStateImpl upDoDateStateImpl;
    private UpDoDateStateView upDoDateStateView;
    private Handler mHandler;

    public UpDoDateStatePresenter(UpDoDateStateView upDoDateStateView) {
        this.upDoDateStateView = upDoDateStateView;
        upDoDateStateImpl = new UpDoDateStateImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @return
     */
    public void upDoDateState(String distributorid, String studyid, String sign) {
        upDoDateStateImpl.upDoDateState(distributorid, studyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        upDoDateStateView.closeUpDoDateStateProgress();
                        upDoDateStateView.OnUpDoDateStateSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        upDoDateStateView.closeUpDoDateStateProgress();
                        upDoDateStateView.OnUpDoDateStateFialCallBack("1", s);
                    }
                });
            }
        });
    }

}