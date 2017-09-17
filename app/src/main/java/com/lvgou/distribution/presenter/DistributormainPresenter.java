package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DistributormainImpl;
import com.lvgou.distribution.view.UserPersonalView;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DistributormainPresenter extends BasePresenter<UserPersonalView>{
    private DistributormainImpl requestBiz;
    private Handler mHandler;
    private UserPersonalView iView;
    public DistributormainPresenter(UserPersonalView mBaseView) {
        requestBiz = new DistributormainImpl(this);
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void distributormain(String distributorid,String seeDistributorId,String sign){
        requestBiz.distributormain(distributorid, seeDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                           iView.distributormainResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void CircleFollow(String distributorid,String friendId,String sign){
        requestBiz.circlefollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                            iView.followResponse(s, "circlefollow");
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void CircleUnFollow(String distributorid,String friendId,String sign){
        requestBiz.circleUnfollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.followResponse(s, "uncirclefollow");
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
