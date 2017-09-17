package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.ApplyTeaacherBean;
import com.lvgou.distribution.model.impl.ApplyTeacherImpl;
import com.lvgou.distribution.view.ApplyTeacherView;

/**
 * Created by Swno on 2016/9/24.
 */
public class ApplyTeacherPresenter extends BasePresenter<ApplyTeacherView>{

    private ApplyTeacherView applyTeacherView;
    private ApplyTeacherImpl applyTeacherImpl;
    private Handler mHandler;

    public ApplyTeacherPresenter(ApplyTeacherView applyTeacherView) {
        this.applyTeacherView = applyTeacherView;
        applyTeacherImpl = new ApplyTeacherImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 申请讲师
     * @param applyTeaacherBean
     */
    public void applyTeacher(ApplyTeaacherBean applyTeaacherBean) {
        applyTeacherImpl.doApplyTeacher(applyTeaacherBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyTeacherView.closeProgress();
                        applyTeacherView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyTeacherView.closeProgress();
                        applyTeacherView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
