package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherSeatImpl;
import com.lvgou.distribution.view.TeacherSeatView;

import retrofit.http.Field;

/**
 * Created by Administrator on 2016/9/23.
 */
public class TeacherSeatPresenter extends BasePresenter<TeacherSeatView> {

    private TeacherSeatView teacherSeatView;
    private TeacherSeatImpl teacherSeatImpl;
    private Handler mHandler;

    public TeacherSeatPresenter(TeacherSeatView teacherSeatView) {
        this.teacherSeatView = teacherSeatView;
        teacherSeatImpl = new TeacherSeatImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取讲师列表
     *
     * @param pageindex
     * @param key
     * @param sign
     */
    public void getTeacherSeatData(String distributorid, String pageindex, String key, String sign) {
        teacherSeatImpl.getTeacherSeatData(distributorid, pageindex, key, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSeatView.closeProgress();
                        teacherSeatView.OnRequestSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSeatView.closeProgress();
                        teacherSeatView.OnRequestFialCallBack("2", s);
                    }
                });
            }
        });
    }

    /**
     * 申请讲师状态
     *
     * @param distributorid
     * @param sign
     */
    public void getApplyState(String distributorid, String sign) {
        teacherSeatImpl.getSatte(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSeatView.closeProgress();
                        teacherSeatView.OnRequestSuccCallBack("2", s);
                    }
                });
            }


            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSeatView.closeProgress();
                        teacherSeatView.OnRequestFialCallBack("2", s);
                    }
                });
            }
        });
    }

}
