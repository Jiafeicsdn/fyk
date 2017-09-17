package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.GetUserInfoImpl;
import com.lvgou.distribution.view.GetUserInfoView;

/**
 * Created by Administrator on 2017/5/5.
 */

public class GetUserInfoPresenter extends BasePresenter<GetUserInfoView> {
    private GetUserInfoImpl getUserInfoImpl;
    private GetUserInfoView getUserInfoView;
    private Handler mHandler;

    public GetUserInfoPresenter(GetUserInfoView getUserInfoView) {
        this.getUserInfoView = getUserInfoView;
        getUserInfoImpl = new GetUserInfoImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 导游信息获取
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void getUserInfo(String distributorid, String sign) {
        getUserInfoImpl.getUserInfo(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getUserInfoView.closeGetUserInfoProgress();
                        getUserInfoView.OnGetUserInfoSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getUserInfoView.closeGetUserInfoProgress();
                        getUserInfoView.OnGetUserInfoFialCallBack("1", s);
                    }
                });
            }
        });
    }

}