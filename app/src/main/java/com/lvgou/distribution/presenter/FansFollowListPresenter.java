package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FansFollowListImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/21.
 */
public class FansFollowListPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private FansFollowListImpl fansFollowListImpl;
    private Handler mHandler;

    public FansFollowListPresenter(GroupView groupView) {
        this.groupView = groupView;
        fansFollowListImpl = new FansFollowListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getFollowList(String distributorId, String seeDistributorId, String currPage, String sign) {
        fansFollowListImpl.getFollowFriend(distributorId, seeDistributorId, currPage, sign, new ICallBackListener() {
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


    public void getFansList(String distributorId, String seeDistributorId, String currPage, String sign) {
        fansFollowListImpl.getFansFriend(distributorId, seeDistributorId, currPage, sign, new ICallBackListener() {
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

    public void doFollow(String distributorId, String friendId, String sign) {
        fansFollowListImpl.doFollow(distributorId, friendId, sign, new ICallBackListener() {
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

    public void cancleFollow(String distributorId, String friendId, String sign) {
        fansFollowListImpl.cancleFollow(distributorId, friendId, sign, new ICallBackListener() {
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
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("4", s);
                    }
                });
            }
        });
    }
}
