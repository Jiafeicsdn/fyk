package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CollectImpl;
import com.lvgou.distribution.view.CmentFgView;
import com.lvgou.distribution.view.MyCollectView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class CollectListPresenter extends BasePresenter<MyCollectView>{
private CollectImpl requestimpl;
    private Handler mHandler;
    private MyCollectView iView;

    public CollectListPresenter(MyCollectView mBaseView) {
        requestimpl = new CollectImpl(this);
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }
    public void talkcollectionlist(String distributorid, String prePageLastDataObjectId,int currPage, String sign) {
        requestimpl.talkcollectionlist(distributorid, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack("collect", s);
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
