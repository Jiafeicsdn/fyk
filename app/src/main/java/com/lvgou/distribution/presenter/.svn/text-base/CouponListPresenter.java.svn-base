package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CouponListImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.CouponListView;

/**
 * Created by Administrator on 2017/3/29.
 */

public class CouponListPresenter extends BasePresenter<CouponListView> {
    private CouponListImpl couponListImpl;
    private CouponListView couponListView;
    private Handler mHandler;

    public CouponListPresenter(CouponListView couponListView) {
        this.couponListView = couponListView;
        couponListImpl = new CouponListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 听课券列表
     *
     * @param distributorid 导游ID
     * @param type          类型 1=未使用 2=已使用
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void couponListDatas(String distributorid, int type, int pageindex, String sign) {
        couponListImpl.couponListDatas(distributorid, type, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        couponListView.closeCouponListProgress();
                        couponListView.OnCouponListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        couponListView.closeCouponListProgress();
                        couponListView.OnCouponListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}