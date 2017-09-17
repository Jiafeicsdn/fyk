package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DelActCommentImpl;
import com.lvgou.distribution.view.DelActCommentView;

/**
 * Created by Administrator on 2017/5/5.
 */

public class DelActCommentPresenter extends BasePresenter<DelActCommentView> {
    private DelActCommentImpl delActCommentImpl;
    private DelActCommentView delActCommentView;
    private Handler mHandler;

    public DelActCommentPresenter(DelActCommentView delActCommentView) {
        this.delActCommentView = delActCommentView;
        delActCommentImpl = new DelActCommentImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 活动评论-删除
     *
     * @param distributorid
     * @param commentid
     * @param sign
     * @return
     */
    public void delActComment(String distributorid,String commentid, String sign) {
        delActCommentImpl.delActComment(distributorid, commentid,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delActCommentView.closeDelActCommentProgress();
                        delActCommentView.OnDelActCommentSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delActCommentView.closeDelActCommentProgress();
                        delActCommentView.OnDelActCommentFialCallBack("1", s);
                    }
                });
            }
        });
    }

}