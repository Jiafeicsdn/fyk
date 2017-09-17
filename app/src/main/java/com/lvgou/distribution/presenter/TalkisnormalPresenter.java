package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TalkisnormalImpl;
import com.lvgou.distribution.view.TalkisnormalView;

/**
 * Created by Administrator on 2017/5/5.
 */

public class TalkisnormalPresenter extends BasePresenter<TalkisnormalView> {
    private TalkisnormalImpl talkisnormalImpl;
    private TalkisnormalView talkisnormalView;
    private Handler mHandler;

    public TalkisnormalPresenter(TalkisnormalView talkisnormalView) {
        this.talkisnormalView = talkisnormalView;
        talkisnormalImpl = new TalkisnormalImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 蜂文状态
     */
    public void talkisnormal(String talkId, String sign) {
        talkisnormalImpl.talkisnormal(talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkisnormalView.closeTalkisnormalProgress();
                        talkisnormalView.OnTalkisnormalSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkisnormalView.closeTalkisnormalProgress();
                        talkisnormalView.OnTalkisnormalFialCallBack("1", s);
                    }
                });
            }
        });
    }

}