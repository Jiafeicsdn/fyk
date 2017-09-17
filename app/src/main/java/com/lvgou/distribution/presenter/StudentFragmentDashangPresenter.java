package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.StudentFragmentDashangImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.StudentFragmentDashangView;

/**
 * Created by Administrator on 2017/2/20.
 */

public class StudentFragmentDashangPresenter extends BasePresenter<StudentFragmentDashangView> {
    private StudentFragmentDashangImpl studentFragmentDashangImpl;
    private StudentFragmentDashangView studentFragmentDashangView;
    private Handler mHandler;

    public StudentFragmentDashangPresenter(StudentFragmentDashangView studentFragmentDashangView) {
        this.studentFragmentDashangView = studentFragmentDashangView;
        studentFragmentDashangImpl = new StudentFragmentDashangImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *
     * @param distributorid 导游ID
     * @param studyid       课堂ID
     * @param tuanbi        打赏团币数量
     * @param sign          签名
     */
    public void dashang(String distributorid, String studyid, int tuanbi, String sign) {
        studentFragmentDashangImpl.dashang(distributorid, studyid, tuanbi, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studentFragmentDashangView.closeProgress();
                        studentFragmentDashangView.OnStudentFragmentDashangSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studentFragmentDashangView.closeProgress();
                        studentFragmentDashangView.OnStudentFragmentDashangFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 打赏详情
     *
     * @param studyid 课堂ID
     * @param sign    签名
     */
    public void dashangDetail(String studyid, int pageindex, String sign) {
        studentFragmentDashangImpl.dashangDetail(studyid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studentFragmentDashangView.closeProgress();
                        studentFragmentDashangView.OnStudentFragmentDashangSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        studentFragmentDashangView.closeProgress();
                        studentFragmentDashangView.OnStudentFragmentDashangFialCallBack("1", s);
                    }
                });
            }
        });
    }
}
