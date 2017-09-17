package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.UnreadCountImpl;
import com.lvgou.distribution.view.UnreadCountView;

/**
 * Created by Administrator on 2017/4/21.
 */

public class UnreadCountPresenter extends BasePresenter<UnreadCountView> {
    private UnreadCountImpl unreadCountImpl;
    private UnreadCountView unreadCountView;
    private Handler mHandler;

    public UnreadCountPresenter(UnreadCountView unreadCountView) {
        this.unreadCountView = unreadCountView;
        unreadCountImpl = new UnreadCountImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 未读消息数
     * @param distributorid
     * @param getNewestDistributorId  	是否获取最新评论或者点赞的用户编号 1=获取 其余=不获取
     * @param sign
     * @return
     */
    public void unreadCount(String distributorid, int getNewestDistributorId,String sign) {
        unreadCountImpl.unreadCount(distributorid, getNewestDistributorId,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        unreadCountView.closeUnreadCountProgress();
                        unreadCountView.OnUnreadCountSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        unreadCountView.closeUnreadCountProgress();
                        unreadCountView.OnUnreadCountFialCallBack("1", s);
                    }
                });
            }
        });
    }

}