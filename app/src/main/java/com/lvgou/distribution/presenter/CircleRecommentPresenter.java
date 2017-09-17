package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CircleRecomendImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CircleRecommentPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private CircleRecomendImpl circleRecomendImpl;
    private Handler mHandler;

    public CircleRecommentPresenter(GroupView groupView) {
        this.groupView = groupView;
        circleRecomendImpl = new CircleRecomendImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     */
    public void doZan(String distributorId, String talkId, String sign) {
        circleRecomendImpl.doZan(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 取消点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     */
    public void cancleZan(String distributorId, String talkId, String sign) {
        circleRecomendImpl.cancleZan(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }

    /**
     * 推荐列表
     *
     * @param distributorId
     * @param talkId
     * @param sign
     */
    public void getRecommendList(String distributorId, String prePageLastDataObjectId, String talkId, String sign) {
        circleRecomendImpl.getCircleRecomment(distributorId, prePageLastDataObjectId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("3", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("3", s);
                    }
                });
            }
        });
    }

    public void distributormain(String distributorid, String seeDistributorId, String sign) {
        circleRecomendImpl.distributormain(distributorid, seeDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("4", s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("4", s);
            }
        });
    }
}
