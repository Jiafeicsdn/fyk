package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.model.im.impl.IMSendImpl;
import com.lvgou.distribution.view.IMView;

/**
 * Created by Administrator on 2016/9/20.
 */
public class IMPersenter extends BasePresenter<IMView> {
    private IMSendImpl requestBiz;
    private Handler mHandler;
    private IMView iView;

    public IMPersenter(IMView mBaseView) {
        requestBiz = new IMSendImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void addUser(String userName){
        requestBiz.addUser(userName, iView.getParamenters(),new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        iView.closeProgress();
                        iView.addUser_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }

    //群关联用户列表
    public void JoinChatGroup() {
        requestBiz.JoinChatGroup("1", iView.getParamenters2(), new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        iView.closeProgress();
                        iView.JoinChatGroup_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }

    //获取服务器列表
    public void getServer() {
        requestBiz.getServer(iView.getParamenters3(), new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.getServer_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.getServer_error();
                    }
                });
            }
        });
    }

    //获取成员列表
    public void member_list(String access_token,GetMemberList getMemberList) {
        requestBiz.member_list(access_token, getMemberList, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.member_list_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
    //获取成员列表用户名称
    public void GetNickName(String distributorid, String sign) {
        requestBiz.GetNickName(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.getNickNameResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
    //获取历史消息
    public void GetGroupMessageExt(String access_token,GetGroupMessage getGroupMessage) {
        requestBiz.GetGroupMessageExt(access_token, getGroupMessage, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.GetGroupMessageExt_response(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteGroupMessageFailedCallBack(s);
                    }
                });
            }
        });
    }
    public void GetGroupInfo(String access_token,String groupId){
        requestBiz.GetGroupInfo(access_token, groupId, new ICallBackListener() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFaild(String s) {

            }
        });
    }

    public void RemoveMemberGroup(String access_token){
        requestBiz.RemoveMemberGroup(access_token, iView.getParamenters2(), new ICallBackListener() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFaild(String s) {

            }
        });
    }

    public void Prohibitlist(String studyid,String sign){
        requestBiz.Prohibitlist(studyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.prohibitlistResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
    public void Releaseshutup(String kdbdistributorid,String studyid,String distributorid,String sign){
        requestBiz.Releaseshutup(kdbdistributorid, studyid, distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.ReleaseshutupResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }

    /**
     * 直播间禁言
     * @param kdbdistributorid
     * @param studyid
     * @param distributorid
     * @param sign
     */
    public void Bannedtopost(String kdbdistributorid,String studyid,String distributorid,String sign){
        requestBiz.Bannedtopost(kdbdistributorid, studyid, distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
