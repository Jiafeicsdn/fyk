package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ApplicationImpl;
import com.lvgou.distribution.model.impl.ApplyImpl;
import com.lvgou.distribution.view.AppliactionView;

/**
 * Created by Snow on 2016/9/12.
 */
public class ApplicationPresenter extends BasePresenter<AppliactionView> {

    private ApplicationImpl applicationIml;
    private AppliactionView appliactionView;
    private ApplyImpl applyIml;
    private Handler mHandler;


    public ApplicationPresenter(AppliactionView appliactionView) {
        this.appliactionView = appliactionView;
        applicationIml = new ApplicationImpl();
        applyIml = new ApplyImpl();
        mHandler=new Handler(Looper.getMainLooper());
    }


    /**
     * 获取列表消息
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getApplicationData(String distributorid, String pageindex, String sign) {
        applicationIml.getApplicationData(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        appliactionView.closeProgress();
                        appliactionView.applcationSuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                      appliactionView.closeProgress();
                        appliactionView.applcationFailCallBck(s);
                    }
                });
            }
        });
    }

    /**
     * 去申请
     *
     * @param distributorid
     * @param applyId
     * @param sign
     */
    public void doApplyData(String distributorid, String applyId, String sign) {
        applyIml.applay(distributorid, applyId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                      appliactionView.closeProgress();
                        appliactionView.applySuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                      appliactionView.closeProgress();
                        appliactionView.applyFailCallBck(s);
                    }
                });
            }
        });
    }
}
