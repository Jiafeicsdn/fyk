package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.BaomingTeacherImpl;
import com.lvgou.distribution.view.BaomingTeacherView;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BaomingTeacherPresenter extends BasePresenter<BaomingTeacherView> {
    private BaomingTeacherImpl baomingTeacherImpl;
    private BaomingTeacherView baomingTeacherView;
    private Handler mHandler;

    public BaomingTeacherPresenter(BaomingTeacherView baomingTeacherView) {
        this.baomingTeacherView = baomingTeacherView;
        baomingTeacherImpl = new BaomingTeacherImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 名师讲堂-报名
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    public void applyTeacher(String distributorid, String id, String couponid, String sign) {
        baomingTeacherImpl.applyTeacher(distributorid, id, couponid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        baomingTeacherView.closeBaomingTeacherProgress();
                        baomingTeacherView.OnBaomingTeacherSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        baomingTeacherView.closeBaomingTeacherProgress();
                        baomingTeacherView.OnBaomingTeacherFialCallBack("1", s);
                    }
                });
            }
        });
    }

}