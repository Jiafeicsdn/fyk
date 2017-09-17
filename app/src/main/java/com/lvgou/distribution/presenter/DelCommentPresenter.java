package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DelCommentImpl;
import com.lvgou.distribution.view.DelCommentView;

/**
 * Created by Administrator on 2017/5/2.
 */

public class DelCommentPresenter extends BasePresenter<DelCommentView> {
    private DelCommentImpl delCommentImpl;
    private DelCommentView delCommentView;
    private Handler mHandler;

    public DelCommentPresenter(DelCommentView delCommentView) {
        this.delCommentView = delCommentView;
        delCommentImpl = new DelCommentImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 删除课程评论
     *
     * @param distributorid 导游编号
     * @param commentid     评论ID
     * @param sign          签名
     * @return
     */
    public void delComment(String distributorid,String commentid, String sign) {
        delCommentImpl.delComment(distributorid,commentid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delCommentView.closeDelCommentProgress();
                        delCommentView.OnDelCommentSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delCommentView.closeDelCommentProgress();
                        delCommentView.OnDelCommentFialCallBack("1", s);
                    }
                });
            }
        });
    }

}