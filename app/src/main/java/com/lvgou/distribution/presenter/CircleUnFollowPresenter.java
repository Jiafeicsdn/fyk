package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CircleUnFollowImpl;
import com.lvgou.distribution.view.CircleUnFollowView;

/**
 * Created by Administrator on 2017/4/26.
 */

public class CircleUnFollowPresenter extends BasePresenter<CircleUnFollowView> {
    private CircleUnFollowImpl circleUnFollowImpl;
    private CircleUnFollowView circleUnFollowView;
    private Handler mHandler;

    public CircleUnFollowPresenter(CircleUnFollowView circleUnFollowView) {
        this.circleUnFollowView = circleUnFollowView;
        circleUnFollowImpl = new CircleUnFollowImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 取消关注好友
     *
     * @param distributorId 导游编号
     * @param friendId      被关注导游编号
     * @param sign          签名
     * @return
     */
    public void circleUnFollow(String distributorId, String friendId, String sign) {
        circleUnFollowImpl.circleUnFollow(distributorId, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        circleUnFollowView.closeCircleUnFollowProgress();
                        circleUnFollowView.OnCircleUnFollowSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        circleUnFollowView.closeCircleUnFollowProgress();
                        circleUnFollowView.OnCircleUnFollowFialCallBack("1", s);
                    }
                });
            }
        });
    }

}