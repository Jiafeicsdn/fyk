package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.HomePageImpl;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.FengCircleView;
import com.lvgou.distribution.view.HomePageView;

/**
 * Created by Administrator on 2016/10/15.
 */
public class HomePagePresenter extends BasePresenter<HomePageView> {
    private HomePageImpl requestBiz;
    private Handler mHandler;
    private BaseView iView;

    public HomePagePresenter(BaseView mBaseView) {
        requestBiz = new HomePageImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getIndex(String distributorid, String sign) {
        requestBiz.getIndex(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack(s);
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).indexresponse(s);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void getFindCircle(String distributorid, String keyword, String tagId, String prePageLastDataObjectId, int currPage, String sign) {
        requestBiz.getFindcircle(distributorid, keyword, tagId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();
                        iView.excuteSuccessCallBack(s);
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).findcircleResponse(s);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).findcircleResponse(s);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void unreadcount(String distributorid, int getNewestDistributorId, String sign) {
        requestBiz.unreadcount(distributorid, getNewestDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).unreadcount(s);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).unreadcountResponse(s);
                        }else if (iView instanceof BaseView) {
                            iView.excuteSuccessCallBack(s);
                        }
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
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).zanResponse(s, position);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).zanResponse(s, position);
                        }
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
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).unzanResponse(s, position);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).unzanResponse(s, position);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void CircleFollow(String distributorid, String friendId, String sign, final int position) {
        requestBiz.circlefollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).followResponse(s, position);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).followResponse(s, position);
                        }
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
        requestBiz.circleUnfollow(distributorid, friendId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof HomePageView) {
                            ((HomePageView) iView).unfollowResponse(s, position);
                        } else if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).unfollowResponse(s, position);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void findtagandtopic(String distributorid, String sign) {
        requestBiz.findtagandtopic(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).findtagandtopicResponse(s);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void followcircle(String distributorid, String prePageLastDataObjectId, int currPage, String sign) {
        requestBiz.followcircle(distributorid, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).followcircleResponse(s);
                        }
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack(s);
            }
        });
    }

    public void mayknowperson(String distributorid, String sign) {
        requestBiz.mayknowperson(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iView instanceof FengCircleView) {
                            ((FengCircleView) iView).mayknowpersonResponse(s);
                        }
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
