package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DynamicDetailsImpl;
import com.lvgou.distribution.model.impl.HomePageImpl;
import com.lvgou.distribution.view.CmentFgView;
import com.lvgou.distribution.view.DynamicDetailsView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class CommentListPresenter extends BasePresenter<CmentFgView>{

    private DynamicDetailsImpl requestBiz;
    private HomePageImpl requestimpl;
    private Handler mHandler;
    private CmentFgView iView;

    public CommentListPresenter(CmentFgView mBaseView) {
        requestBiz = new DynamicDetailsImpl(this);
        requestimpl = new HomePageImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }
    public void talkcommentlist(String talkId, String prePageLastDataObjectId,int currPage, String sign) {
        requestBiz.talkcommentlist(talkId, prePageLastDataObjectId,currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack("talkcoment",s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void talkcommentdel(String distributorId, String talkCommentId,String sign) {
        requestBiz.talkcommentdel(distributorId, talkCommentId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack("talkcomentdel", s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void commenttalk(String distributorId, String talkId,String commentId,String content,int tuanbi,String sign) {
        requestBiz.commenttalk(distributorId, talkId,commentId,content,tuanbi,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack("commenttalk", s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
}
