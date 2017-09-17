package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.UserInviteImpl;
import com.lvgou.distribution.view.UserInviteView;

/**
 * Created by Administrator on 2017/4/1.
 */

public class UserInvitePresenter extends BasePresenter<UserInviteView> {
    private UserInviteImpl userInviteImpl;
    private UserInviteView userInviteView;
    private Handler mHandler;

    public UserInvitePresenter(UserInviteView userInviteView) {
        this.userInviteView = userInviteView;
        userInviteImpl = new UserInviteImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 邀请导游有奖
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    public void userInvite(String distributorid, String sign) {
        userInviteImpl.userInvite(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userInviteView.closeUserInviteProgress();
                        userInviteView.OnUserInviteSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userInviteView.closeUserInviteProgress();
                        userInviteView.OnUserInviteFialCallBack("1", s);
                    }
                });
            }
        });
    }

}