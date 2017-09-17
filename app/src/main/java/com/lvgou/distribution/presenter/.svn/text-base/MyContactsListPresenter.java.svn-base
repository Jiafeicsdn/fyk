package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyContactsListImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/20.
 */
public class MyContactsListPresenter extends BasePresenter<GroupView> {


    private GroupView groupView;
    private MyContactsListImpl myContactsListImpl;
    private Handler mHandler;

    public MyContactsListPresenter(GroupView groupView) {
        this.groupView = groupView;
        myContactsListImpl = new MyContactsListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取可能认识的人
     *
     * @param distributorId
     * @param mobiles
     * @param sign
     */
    public void getContactsList(String distributorId, String mobiles, String sign) {
        myContactsListImpl.getContactsList(distributorId, mobiles, sign, new ICallBackListener() {
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
     * 获取可能认识的人
     *
     * @param distributorId
     * @param friendId
     * @param sign
     */
    public void doFollow(String distributorId, String friendId, String sign) {
        myContactsListImpl.doFollow(distributorId, friendId, sign, new ICallBackListener() {
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
     * 获取可能认识的人
     *
     * @param distributorId
     * @param friendId
     * @param sign
     */
    public void cancleFollow(String distributorId, String friendId, String sign) {
        myContactsListImpl.cancleFollow(distributorId, friendId, sign, new ICallBackListener() {
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
