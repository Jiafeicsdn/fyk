package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FindCircleTopImpl;
import com.lvgou.distribution.view.FindCircleTopView;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FindCircleTopPresenter extends BasePresenter<FindCircleTopView> {
    private FindCircleTopImpl findCircleTopImpl;
    private FindCircleTopView findCircleTopView;
    private Handler mHandler;

    public FindCircleTopPresenter(FindCircleTopView findCircleTopView) {
        this.findCircleTopView = findCircleTopView;
        findCircleTopImpl = new FindCircleTopImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂圈-发现-头部
     *
     * @param distributorId 导游编号
     * @param sign          签名
     * @return
     */
    public void findCircleTop(String distributorId, String sign) {
        findCircleTopImpl.findCircleTop(distributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        findCircleTopView.closeFindCircleTopProgress();
                        findCircleTopView.OnFindCircleTopSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        findCircleTopView.closeFindCircleTopProgress();
                        findCircleTopView.OnFindCircleTopFialCallBack("1", s);
                    }
                });
            }
        });
    }

}