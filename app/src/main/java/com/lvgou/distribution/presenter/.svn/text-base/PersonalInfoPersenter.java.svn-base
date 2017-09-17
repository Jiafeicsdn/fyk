package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.PersonalInfoImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/10/14.
 */
public class PersonalInfoPersenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private PersonalInfoImpl personalInfoImpl;
    private Handler mHander;

    public PersonalInfoPersenter(GroupView groupView) {
        this.groupView = groupView;
        personalInfoImpl = new PersonalInfoImpl();
        mHander = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取个人信息
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     */
    public void getPersonalInfo(String distributorid, String seekid, String applyid, String sign) {
        personalInfoImpl.getPersonalInfo(distributorid, seekid, applyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHander.post(new Runnable() {
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
     * 录用导游
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     */
    public void doEmployment(String distributorid, String seekid, String applyid, String sign) {
        personalInfoImpl.doEmployment(distributorid, seekid, applyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }
}
