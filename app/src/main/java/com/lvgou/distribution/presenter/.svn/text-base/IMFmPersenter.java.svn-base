package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.model.im.impl.IMSendImpl;
import com.lvgou.distribution.view.IMFmView;
import com.lvgou.distribution.view.IMView;

/**
 * Created by Administrator on 2016/9/20.
 */
public class IMFmPersenter extends BasePresenter<IMView> {
    private IMSendImpl requestBiz;
    private Handler mHandler;
    private IMFmView iView;

    public IMFmPersenter(IMFmView mBaseView) {
        requestBiz = new IMSendImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    //获取历史消息
    public void GetGroupMessageExt(String access_token,GetGroupMessage getGroupMessage) {
        requestBiz.GetGroupMessageExt(access_token, getGroupMessage, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.GetGroupMessageExt_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
