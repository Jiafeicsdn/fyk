package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DetialAndHotCityImpl;
import com.lvgou.distribution.view.DetialHotCityView;

/**
 * Created by Snow on 2016/9/28.
 */
public class DetialHotCityPresenter extends BasePresenter<DetialHotCityView> {

    private DetialHotCityView detialHotCityView;
    private DetialAndHotCityImpl detialAndHotCityImpl;
    private Handler mHanlder;


    public DetialHotCityPresenter(DetialHotCityView detialHotCityView) {
        this.detialHotCityView = detialHotCityView;
        detialAndHotCityImpl = new DetialAndHotCityImpl();
        mHanlder = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取信息
     *
     * @param distributorid
     * @param seekid
     * @param sign
     */
    public void getDetialHotCity(String distributorid, String seekid, String sign) {
        detialAndHotCityImpl.getGroupDetial(distributorid, seekid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 获取报名列表
     *
     * @param seekid
     * @param pageindex
     * @param sign
     */
    public void getSinUpList(String seekid, String pageindex, String sign) {
        detialAndHotCityImpl.getSignUpList(seekid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }


    /**
     * 取消派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     */
    public void canclePublishGroup(String distributorid, String seekid, String sign) {
        detialAndHotCityImpl.canclePublishGroup(distributorid, seekid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousSuccCallBack("3", s);
                    }
                });
            }


            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousFialCallBack("3", s);
                    }
                });
            }
        });
    }


    /**
     * 删除派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     */
    public void deletePublishGroup(String distributorid, String seekid, String sign) {
        detialAndHotCityImpl.deletePublishGroup(distributorid, seekid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousSuccCallBack("4", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousFialCallBack("4", s);
                    }
                });
            }
        });
    }

    /**
     * 报名派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     */
    public void doSignUp(String distributorid, String seekid, String sign) {
        detialAndHotCityImpl.doSignUp(distributorid, seekid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousSuccCallBack("5", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        detialHotCityView.closeProgress();
                        detialHotCityView.OnFamousFialCallBack("5", s);
                    }
                });
            }
        });
    }
}
