package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.HomePageImpl;
import com.lvgou.distribution.model.impl.ZanListImpl;
import com.lvgou.distribution.view.ZanFgView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ZanPresenter extends BasePresenter<ZanFgView>{
    private ZanFgView zanFgView;
    private ZanListImpl zanListImpl;
    private HomePageImpl homePageImpl;
    private Handler mHandler;

    public ZanPresenter(ZanFgView zanFgView) {
        this.zanFgView = zanFgView;
        zanListImpl = new ZanListImpl();
        homePageImpl = new HomePageImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void talkzanlist(String distributorId, String talkId,String prePageLastDataObjectId, int currPage, String sign) {
        zanListImpl.talkzanlist(distributorId, talkId, prePageLastDataObjectId,currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        zanFgView.closeProgress();
                        zanFgView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        zanFgView.closeProgress();
                        zanFgView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
    public void CircleFollow(String distributorid, String friendId, String sign, final int position) {
        homePageImpl.circlefollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        zanFgView.excuteSuccessCallBack("follow",s,position);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                zanFgView.excuteFailedCallBack(s);
            }
        });
    }

    public void CircleUnFollow(String distributorid, String friendId, String sign, final int position) {
        homePageImpl.circleUnfollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        zanFgView.excuteSuccessCallBack("unfollow",s,position);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                zanFgView.excuteFailedCallBack(s);
            }
        });
    }

}
