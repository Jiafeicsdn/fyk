package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CommentListImpl;
import com.lvgou.distribution.view.CommentListView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ActCommentListPresenter extends BasePresenter<CommentListView> {
    private CommentListImpl commentListImpl;
    private CommentListView commentListView;
    private Handler mHandler;

    public ActCommentListPresenter(CommentListView commentListView) {
        this.commentListView = commentListView;
        commentListImpl = new CommentListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动评论-列表
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void commentList(String distributorid, String activityid, int pageindex, String sign) {
        commentListImpl.commentList(distributorid, activityid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentListView.closeCommentListProgress();
                        commentListView.OnCommentListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentListView.closeCommentListProgress();
                        commentListView.OnCommentListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}