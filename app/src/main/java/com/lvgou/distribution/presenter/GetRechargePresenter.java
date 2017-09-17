package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.GetRechargeImpl;
import com.lvgou.distribution.view.GetRechargeView;

/**
 * Created by Administrator on 2017/5/5.
 */

public class GetRechargePresenter extends BasePresenter<GetRechargeView> {
    private GetRechargeImpl getRechargeImpl;
    private GetRechargeView getRechargeView;
    private Handler mHandler;

    public GetRechargePresenter(GetRechargeView getRechargeView) {
        this.getRechargeView = getRechargeView;
        getRechargeImpl = new GetRechargeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 团币充值优惠
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void getRecharge(String distributorid, String sign) {
        getRechargeImpl.getRecharge(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getRechargeView.closeGetRechargeProgress();
                        getRechargeView.OnGetRechargeSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getRechargeView.closeGetRechargeProgress();
                        getRechargeView.OnGetRechargeFialCallBack("1", s);
                    }
                });
            }
        });
    }

}