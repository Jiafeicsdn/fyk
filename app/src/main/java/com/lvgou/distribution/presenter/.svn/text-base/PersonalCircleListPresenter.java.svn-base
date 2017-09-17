package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lidroid.xutils.cache.FileNameGenerator;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.PersonalCircleListImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/10/18.
 */
public class PersonalCircleListPresenter extends BasePresenter<GroupView> {


    private GroupView groupView;
    private PersonalCircleListImpl personalCircleListImpl;
    private Handler mHandler;

    public PersonalCircleListPresenter(GroupView groupView) {
        this.groupView = groupView;
        personalCircleListImpl = new PersonalCircleListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }
    public void getTalkDetial(String distributorid, String talkId, String sign) {
        groupView.showProgress();
        personalCircleListImpl.getTalkDetial(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.OnFamousSuccCallBack("Detial",s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                groupView.OnFamousFialCallBack("Detial",s);
            }
        });
    }
    /**
     * 评论列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    public void getPersonalComment(String distributorId, String prePageLastDataObjectId, String currPage, String sign) {
        personalCircleListImpl.getCommentList(distributorId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("1", s);
            }
        });
    }


    /**
     * 点赞列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    public void getPersonalZan(String distributorId, String prePageLastDataObjectId, String currPage, String sign) {
        personalCircleListImpl.getZanList(distributorId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("2", s);
            }
        });
    }
    /**
     * 点赞列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    public void getMyZanList1(String distributorId, String prePageLastDataObjectId,int  currPage, String sign) {
        personalCircleListImpl.getMyZanList1(distributorId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("2", s);
            }
        });
    }
    /**
     * 评论列表
     *
     * @param distributorId
     * @param currPage
     * @param sign
     */
    public void getMycommentlist1(String distributorId, String prePageLastDataObjectId,int  currPage, String sign) {
        personalCircleListImpl.getMycommentlist1(distributorId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("2", s);
            }
        });
    }

    /**
     * 评论回复
     *
     * @param distributorId
     * @param talkId
     * @param commentId
     * @param content
     * @param sign
     */
    public void doCommentReplay(String distributorId, String talkId, String commentId, String content,int tuanbi, String sign) {
        personalCircleListImpl.doComment(distributorId, talkId, commentId, content,tuanbi, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("3", s);
            }
        });
    }

    /**
     * 获取未读消息列表
     *
     * @param distributorId
     * @param sign
     */
    public void getReadUnreadMessageList(String distributorId, String sign) {
        personalCircleListImpl.unReadMessageList(distributorId, sign, new ICallBackListener() {
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

    /**
     * 获取未读消息列表
     *
     * @param distributorId
     * @param sign
     */
    public void seekReadUnreadMessageList(String distributorId, String messageType, String sign) {
        personalCircleListImpl.seekUnReadMessage(distributorId, messageType, sign, new ICallBackListener() {
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
            public void onFaild(String s) {
                groupView.closeProgress();
                groupView.OnFamousFialCallBack("5", s);
            }
        });
    }

    public void getUserType(String distributorId, String talkId, String sign) {
        personalCircleListImpl.getUserType(distributorId, talkId, sign, new ICallBackListener() {
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
