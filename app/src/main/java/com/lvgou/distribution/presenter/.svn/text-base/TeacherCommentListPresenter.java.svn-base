package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherCommentListImpl;
import com.lvgou.distribution.view.TeacherCommentListView;

/**
 * Created by Administrator on 2017/4/6.
 */

public class TeacherCommentListPresenter extends BasePresenter<TeacherCommentListView> {
    private TeacherCommentListImpl teacherCommentListImpl;
    private TeacherCommentListView teacherCommentListView;
    private Handler mHandler;

    public TeacherCommentListPresenter(TeacherCommentListView teacherCommentListView) {
        this.teacherCommentListView = teacherCommentListView;
        teacherCommentListImpl = new TeacherCommentListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 名师课堂详情-评论列表
     *
     * @param teacherId 讲课Id
     * @param pageindex 当前页码
     * @param sign      签名
     * @return
     */
    public void teacherCommentList(String teacherId, int pageindex, String sign) {
        teacherCommentListImpl.teacherCommentList(teacherId, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherCommentListView.closeTeacherCommentListProgress();
                        teacherCommentListView.OnTeacherCommentListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherCommentListView.closeTeacherCommentListProgress();
                        teacherCommentListView.OnTeacherCommentListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}