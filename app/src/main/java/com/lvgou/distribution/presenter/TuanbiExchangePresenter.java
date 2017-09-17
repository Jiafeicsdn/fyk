package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.ExchangeBean;
import com.lvgou.distribution.model.impl.TuanbiExchangerImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/11/1.
 */
public class TuanbiExchangePresenter extends BasePresenter<GroupView> {


    private GroupView groupView;
    private TuanbiExchangerImpl tuanbiExchangerImpl;
    private Handler mHandler;


    public TuanbiExchangePresenter(GroupView groupView) {
        this.groupView = groupView;
        tuanbiExchangerImpl = new TuanbiExchangerImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getExchangeList(String distributorid, String pageindex, String sign) {
        tuanbiExchangerImpl.getExchangeList(distributorid, pageindex, sign, new ICallBackListener() {
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
     * 获取详情
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getExchangeDetial(String distributorid, String pageindex, String sign) {
        tuanbiExchangerImpl.getExchangeDetial(distributorid, pageindex, sign, new ICallBackListener() {
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
     * 兑换礼品
     */
    public void doExchangeCommit(ExchangeBean exchangeBean) {
        tuanbiExchangerImpl.doExchange(exchangeBean, new ICallBackListener() {
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

    /**
     * 获取充值列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getRechargeRecordList(String distributorid, String pageindex, String sign) {
        tuanbiExchangerImpl.getRechargeRecordList(distributorid, pageindex, sign, new ICallBackListener() {
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
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("4", s);
                    }
                });
            }
        });
    }

    /**
     * 添加充值记录
     *
     * @param distributorid
     * @param tuanbi
     * @param rmb
     * @param sign
     */
    public void addRechargeRecord(String distributorid, int payType,String tuanbi, String rmb, String sign) {
        tuanbiExchangerImpl.addRechargeReorder(distributorid, payType,tuanbi, rmb, sign, new ICallBackListener() {
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
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("5", s);
                    }
                });
            }
        });
    }

    /**
     * 加载数据
     *
     * @param distributorid
     * @param sign
     */
    public void exchangeTuanbi(String distributorid, String sign) {
        tuanbiExchangerImpl.getExchangetuanbi(distributorid, sign, new ICallBackListener() {
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


    /**
     * 获取列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getExchangelogList(String distributorid, String pageindex, String sign) {
        tuanbiExchangerImpl.getExchangelogList(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("7", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("7", s);
                    }
                });
            }
        });
    }


    /**
     * 获取列表
     *
     * @param distributorid
     * @param logid
     * @param sign
     */
    public void getExchangelogdetail(String distributorid, String logid, String sign) {
        tuanbiExchangerImpl.getExchangelogDetail(distributorid, logid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("8", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("8", s);
                    }
                });
            }
        });
    }


    /**
     * 支付宝回调
     *
     * @param logid
     * @param state
     * @param errormsg
     * @param sign
     */
    public void doAlipay(String logid, String state, String errormsg, String sign) {
        tuanbiExchangerImpl.doAlipaySuccess(logid, state, errormsg, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("9", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("9", s);
                    }
                });
            }
        });
    }
}



