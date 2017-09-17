package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DynamicDetailsImpl;
import com.lvgou.distribution.model.impl.HomePageImpl;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.DynamicDetailsView;
import com.lvgou.distribution.view.HomePageView;

/**
 * Created by Administrator on 2016/10/15.
 */
public class DynamicDetailsPresenter extends BasePresenter<DynamicDetailsView> {
    private DynamicDetailsImpl requestBiz;
    private HomePageImpl requestimpl;
    private Handler mHandler;
    private DynamicDetailsView iView;

    public DynamicDetailsPresenter(DynamicDetailsView mBaseView) {
        requestBiz = new DynamicDetailsImpl(this);
        requestimpl = new HomePageImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void talkisnormal(String talkId, String sign) {
        requestBiz.talkisnormal(talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.talkisnormalResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s,"");
            }
        });
    }


    public void commenttalk(String distributorid, String talkId, String commentId, String content,int tuanbi, String sign) {
        requestBiz.commenttalk(distributorid, talkId, commentId, content, tuanbi, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.commenttalkResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("commenttalk",s);
            }
        });
    }

    public void CircleZan(String distributorid, String talkId, String sign, final int position) {
        requestimpl.circleZan(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.zanResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("zan",s);
            }
        });
    }

    public void CircleunZan(String distributorid, String talkId, String sign, final int position) {
        requestimpl.circleUnZan(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.unzanResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("zan",s);
            }
        });
    }

    public void CircleFollow(String distributorid, String friendId, String sign, final int position) {
        requestimpl.circlefollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.followResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void CircleUnFollow(String distributorid, String friendId, String sign, final int position) {
        requestimpl.circleUnfollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.unfollowResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }


    public void zhuanFa(String distributorid, String talkId, String content, String sign) {
        requestBiz.zhuanFa(distributorid, talkId, content, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.zhuanfa(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void getTalkDetial(String distributorid, String talkId, String sign) {
        requestBiz.getTalkDetial(distributorid, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.getTalkDetial(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("Detial",s);
            }
        });
    }
    public void recommenttalkcontent(String talkId, String sign) {
        requestBiz.recommenttalkcontent(talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.recommenttalkcontent_response(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    /**
     *
     *蜂文收藏
     * @param talkId
     * @param sign
     */
    public void talkcollection(String distributorId,String talkId, String sign) {
        requestBiz.talkcollection(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.talkcollectionResponse("collect",s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }
    /**
     *
     *蜂文取消收藏
     * @param talkId
     * @param sign
     */
    public void talkuncollection(String distributorId,String talkId, String sign) {
        requestBiz.talkuncollection(distributorId, talkId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.talkcollectionResponse("uncollect",s);
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
