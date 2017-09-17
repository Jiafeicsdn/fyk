package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CommentTeacherImpl;
import com.lvgou.distribution.view.CommentTeacherView;

/**
 * Created by Administrator on 2017/4/6.
 */

public class CommentTeacherPresenter extends BasePresenter<CommentTeacherView> {
    private CommentTeacherImpl commentTeacherImpl;
    private CommentTeacherView commentTeacherView;
    private Handler mHandler;

    public CommentTeacherPresenter(CommentTeacherView commentTeacherView) {
        this.commentTeacherView = commentTeacherView;
        commentTeacherImpl = new CommentTeacherImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 学院评论
     *
     * @param distributorid 导游ID
     * @param teacherId     课堂ID
     * @param content       评论内容(如果为打赏传空)
     * @param tuanbi        打赏团币个数(如果为评价传0)
     * @param sign          签名
     * @return
     */
    public void commentTeacher(String distributorid, String teacherId, String content, int tuanbi, String sign) {
        commentTeacherImpl.commentTeacher(distributorid, teacherId, content, tuanbi, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentTeacherView.closeCommentTeacherProgress();
                        commentTeacherView.OnCommentTeacherSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentTeacherView.closeCommentTeacherProgress();
                        commentTeacherView.OnCommentTeacherFialCallBack("1", s);
                    }
                });
            }
        });
    }

}