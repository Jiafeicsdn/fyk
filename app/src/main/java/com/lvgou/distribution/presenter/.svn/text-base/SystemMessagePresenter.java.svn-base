package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CollectImpl;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.MyCollectView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class SystemMessagePresenter extends BasePresenter<BaseView> {
    private CollectImpl requestimpl;
    private Handler mHandler;
    private BaseView iView;

    public SystemMessagePresenter(BaseView mBaseView) {
        requestimpl = new CollectImpl(this);
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void sysmsglist(String distributorid, int currPage, String sign) {
        requestimpl.sysmsglist(distributorid, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
}
