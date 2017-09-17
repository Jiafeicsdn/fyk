package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.RecommentFrientImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/20.
 */
public class RecommendFriendPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private RecommentFrientImpl recommentFrientImpl;
    private Handler mHandler;

    public RecommendFriendPresenter(GroupView groupView) {
        this.groupView = groupView;
        recommentFrientImpl = new RecommentFrientImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    public void getRecommendFriend(String distributorId, String currPage, String sign) {
        recommentFrientImpl.getRecommendFriend(distributorId, currPage, sign, new ICallBackListener() {
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
     * 关注
     *
     * @param distributorId
     * @param friendId
     * @param sign
     */
    public void doFollow(String distributorId, String friendId, String sign) {
        recommentFrientImpl.doFollow(distributorId, friendId, sign, new ICallBackListener() {
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
     * 取消关注
     *
     * @param distributorId
     * @param friendId
     * @param sign
     */
    public void cancleFollow(String distributorId, String friendId, String sign) {
        recommentFrientImpl.cancleFollow(distributorId, friendId, sign, new ICallBackListener() {
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
}
