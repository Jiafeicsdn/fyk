package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherDetailImpl;
import com.lvgou.distribution.view.TeacherDetailView;

/**
 * Created by Administrator on 2017/4/5.
 */

public class TeacherDetailPresenter extends BasePresenter<TeacherDetailView> {
    private TeacherDetailImpl teacherDetailImpl;
    private TeacherDetailView teacherDetailView;
    private Handler mHandler;

    public TeacherDetailPresenter(TeacherDetailView teacherDetailView) {
        this.teacherDetailView = teacherDetailView;
        teacherDetailImpl = new TeacherDetailImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 名师课堂详情(4.0)
     *
     * @param distributorid 导游ID
     * @param id            课堂ID
     * @param sign          签名
     * @return
     */
    public void teacherDetail(String distributorid, String id, String sign) {
        teacherDetailImpl.teacherDetail(distributorid, id, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherDetailView.closeTeacherDetailProgress();
                        teacherDetailView.OnTeacherDetailSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherDetailView.closeTeacherDetailProgress();
                        teacherDetailView.OnTeacherDetailFialCallBack("1", s);
                    }
                });
            }
        });
    }

}