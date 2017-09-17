package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.UseFollowImpl;
import com.lvgou.distribution.view.UseFollowView;

/**
 * Created by Administrator on 2017/4/17.
 */

public class UseFollowPresenter extends BasePresenter<UseFollowView> {
    private UseFollowImpl useFollowImpl;
    private UseFollowView useFollowView;
    private Handler mHandler;

    public UseFollowPresenter(UseFollowView useFollowView) {
        this.useFollowView = useFollowView;
        useFollowImpl = new UseFollowImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 关注好友
     *
     * @param distributorId 导游编号
     * @param friendId      被关注导游编号
     * @param sign          签名
     * @return
     */
    public void useFollow(String distributorId, String friendId, String sign) {
        useFollowImpl.useFollow(distributorId, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        useFollowView.closeUseFollowProgress();
                        useFollowView.OnUseFollowSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        useFollowView.closeUseFollowProgress();
                        useFollowView.OnUseFollowFialCallBack("1", s);
                    }
                });
            }
        });
    }

}