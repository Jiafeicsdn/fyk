package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CommentSubmitImpl;
import com.lvgou.distribution.view.CommentSubmitView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/23.
 */

public class CommentSubmitPresenter extends BasePresenter<CommentSubmitView> {
    private CommentSubmitImpl commentSubmitImpl;
    private CommentSubmitView commentSubmitView;
    private Handler mHandler;

    public CommentSubmitPresenter(CommentSubmitView commentSubmitView) {
        this.commentSubmitView = commentSubmitView;
        commentSubmitImpl = new CommentSubmitImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动评论-发布
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param comment       评论内容
     * @param sign          签名
     * @return
     */
    public void commentSubmit(String distributorid, String activityid, String comment, String sign) {
        commentSubmitImpl.commentSubmit(distributorid, activityid, comment, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentSubmitView.closeCommentSubmitProgress();
                        commentSubmitView.OnCommentSubmitSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentSubmitView.closeCommentSubmitProgress();
                        commentSubmitView.OnCommentSubmitFialCallBack("1", s);
                    }
                });
            }
        });
    }

}