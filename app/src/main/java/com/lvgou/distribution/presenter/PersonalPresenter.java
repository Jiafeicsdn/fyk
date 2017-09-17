package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.entity.ApplyClassEntity;
import com.lvgou.distribution.model.impl.PersonalImpl;
import com.lvgou.distribution.view.PersonalView;

import cn.jpush.android.api.PushNotificationBuilder;

/**
 * Created by Snow on 2016/10/11.
 */
public class PersonalPresenter extends BasePresenter<PersonalView> {

    private PersonalView personalView;
    private PersonalImpl personalImpl;
    private Handler mHandler;

    public PersonalPresenter(PersonalView personalView) {
        this.personalView = personalView;
        personalImpl = new PersonalImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getData(String distributorid, String sign) {
        personalImpl.getPersonalData(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestFialCallBack("1", s);
                    }
                });
            }
        });
    }


    public void upLoadHead(String distributorid, String picurl, String sign) {
        personalImpl.upLoadHead(distributorid, picurl, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestFialCallBack("2", s);
                    }
                });
            }
        });
    }


    public void getMessageNum(String distributorid, String getNewestDistributorId, String sign) {
        personalImpl.getMessageNum(distributorid, getNewestDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("3", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestFialCallBack("3", s);
                    }
                });
            }
        });
    }


    public void getHotTag(String distributorid, String sign) {
        personalImpl.getHotTag(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("4", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestFialCallBack("4", s);
                    }
                });
            }
        });
    }

    public void doApplyClass(ApplyClassEntity applyClassEntity) {
        personalImpl.applyClass(applyClassEntity, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("5", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalView.closeProgress();
                        personalView.OnRequestSuccCallBack("5", s);
                    }
                });
            }
        });
    }

}
