package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.HomePageImpl;
import com.lvgou.distribution.model.impl.MytalklistImpl;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.FengCircleView;
import com.lvgou.distribution.view.HomePageView;
import com.lvgou.distribution.view.MytalklistView;

/**
 * Created by Administrator on 2016/10/15.
 */
public class UserDynamicPresenter extends BasePresenter<MytalklistView> {
    private MytalklistImpl requestBiz;
    private Handler mHandler;
    private MytalklistView iView;

    public UserDynamicPresenter(MytalklistView mBaseView) {
        requestBiz = new MytalklistImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }


    public void mytalklist(String distributorid, String seeDistributorId, int currPage, String sign) {
        requestBiz.mytalklist(distributorid, seeDistributorId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.mytalklistResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void CircleZan(String distributorid, String talkId, String sign, final int position) {
        requestBiz.circleZan(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.zanResponse(s, position);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void CircleunZan(String distributorid, String talkId, String sign, final int position) {
        requestBiz.circleUnZan(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.unzanResponse(s, position);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void talkdel(String distributorid, String talkId, String sign) {
        requestBiz.talkdel(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.talkdelResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void distributormain(String distributorid,String seeDistributorId,String sign){
        requestBiz.distributormain(distributorid, seeDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.distributormainResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void CircleFollow(String distributorid,String friendId,String sign){
        requestBiz.circlefollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.followResponse(s, "circlefollow");
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    public void CircleUnFollow(String distributorid,String friendId,String sign){
        requestBiz.circleUnfollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.followResponse(s, "uncirclefollow");
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
}
