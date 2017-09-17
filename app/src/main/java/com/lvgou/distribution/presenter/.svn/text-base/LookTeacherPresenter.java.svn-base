package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.LookTeacherImpl;
import com.lvgou.distribution.view.LookTeacherView;

/**
 * Created by Administrator on 2017/4/19.
 */

public class LookTeacherPresenter extends BasePresenter<LookTeacherView> {
    private LookTeacherImpl lookTeacherImpl;
    private LookTeacherView lookTeacherView;
    private Handler mHandler;

    public LookTeacherPresenter(LookTeacherView lookTeacherView) {
        this.lookTeacherView = lookTeacherView;
        lookTeacherImpl = new LookTeacherImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 名师讲堂-查看
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    public void lookTeacher(String distributorid, String id, String couponid, String sign) {
        lookTeacherImpl.lookTeacher(distributorid, id, couponid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lookTeacherView.closeLookTeacherProgress();
                        lookTeacherView.OnLookTeacherSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lookTeacherView.closeLookTeacherProgress();
                        lookTeacherView.OnLookTeacherFialCallBack("1", s);
                    }
                });
            }
        });
    }

}