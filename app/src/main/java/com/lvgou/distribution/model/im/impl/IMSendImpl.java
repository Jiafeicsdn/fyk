package com.lvgou.distribution.model.im.impl;

import android.util.Log;

import com.lvgou.distribution.api.Api2;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IIMServiceAPI;
import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.bean.JoinChatGroupBean;
import com.lvgou.distribution.model.IMModel;

import retrofit.http.Field;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/20.
 */
public class IMSendImpl implements IMModel{
    @Override
    public void addUser(String userName,AddUser id, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        Log.e("sakjdfhkash", "------userName**"+userName+"****"+id );
        mIServiceAPI.addUser(userName,id)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void JoinChatGroup(String access_token, JoinChatGroupBean id, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.JoinChatGroup(access_token, id)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void getServer(String access_token, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.getServer(access_token)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void GetNickName(String distributorid, String sign,ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.GetNickName(distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void member_list(String access_token, GetMemberList id, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.member_list(access_token, id)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void GetGroupMessageExt(String access_token, GetGroupMessage id, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.GetGroupMessageExt(access_token, id)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void GetGroupInfo(String access_token, String groupid, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        Log.e("khaskdfha", "groupid********"+groupid );
        mIServiceAPI.GetGroupInfo(access_token, groupid)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void RemoveMemberGroup(String access_token, JoinChatGroupBean bean, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.RemoveMemberGroup(access_token, bean)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void Prohibitlist(String studyid,String sign, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.Prohibitlist(studyid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void Releaseshutup(String kdbdistributorid,String studyid,String distributorid,String sign, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.Releaseshutup(kdbdistributorid, studyid, distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void Bannedtopost(String kdbdistributorid,String studyid,String distributorid,String sign, ICallBackListener mICallBackListener) {
        IIMServiceAPI mIServiceAPI = Api2.getInstance().getGankService();
        mIServiceAPI.Bannedtopost(kdbdistributorid, studyid, distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api2.getInstance().createSubscriber(mICallBackListener));
    }
}
