package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CheckLevelUpImpl;
import com.lvgou.distribution.view.CheckLevelUpView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class CheckLevelUpPresenter extends BasePresenter<CheckLevelUpView> {
    private CheckLevelUpImpl checkLevelUpImpl;
    private CheckLevelUpView checkLevelUpView;
    private Handler mHandler;

    public CheckLevelUpPresenter(CheckLevelUpView checkLevelUpView) {
        this.checkLevelUpView = checkLevelUpView;
        checkLevelUpImpl = new CheckLevelUpImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 等级自动升级(蜂圈接口)
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void checkLevelUp(String distributorid, String sign) {
        checkLevelUpImpl.checkLevelUp(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        checkLevelUpView.closeCheckLevelUpProgress();
                        checkLevelUpView.OnCheckLevelUpSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        checkLevelUpView.closeCheckLevelUpProgress();
                        checkLevelUpView.OnCheckLevelUpFialCallBack("1", s);
                    }
                });
            }
        });
    }

}