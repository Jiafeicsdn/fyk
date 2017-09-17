package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FengwenSearchImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FengwenSearchPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private FengwenSearchImpl fengwenSearchImpl;
    private Handler mHandler;

    public FengwenSearchPresenter(GroupView groupView) {
        this.groupView = groupView;
        fengwenSearchImpl = new FengwenSearchImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取蜂文
     *
     * @param distributorId
     * @param keyword
     * @param tagId
     * @param currPage
     * @param sign
     */
    public void getFengwenList(String distributorId, String keyword, String tagId, String currPage, String sign) {
        fengwenSearchImpl.getFengwenList(distributorId, keyword, tagId, currPage, sign, new ICallBackListener() {
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
     * 获取用户列表
     *
     * @param distributorId
     * @param keyword
     * @param currPage
     * @param sign
     */
    public void getUserList(String distributorId, String keyword, String currPage, String sign) {
        fengwenSearchImpl.getUserList(distributorId, keyword, currPage, sign, new ICallBackListener() {
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
     * 点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     */
    public void doZan(String distributorId, String talkId, String sign) {
        fengwenSearchImpl.doZan(distributorId, talkId, sign, new ICallBackListener() {
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

    /**
     * 取消点赞
     *
     * @param distributorId
     * @param talkId
     * @param sign
     */
    public void cancleZan(String distributorId, String talkId, String sign) {
        fengwenSearchImpl.cancleZan(distributorId, talkId, sign, new ICallBackListener() {
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

    /**
     * 关注
     *
     * @param distributorId
     * @param friendId
     * @param sign
     */
    public void doFollow(String distributorId, String friendId, String sign) {
        fengwenSearchImpl.doFollow(distributorId, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("5", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("5", s);
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
        fengwenSearchImpl.cancleFollow(distributorId, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("6", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("6", s);
                    }
                });
            }
        });
    }
}
