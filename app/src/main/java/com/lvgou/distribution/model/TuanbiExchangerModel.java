package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.ExchangeBean;

/**
 * Created by Snow on 2016/11/1.
 */
public interface TuanbiExchangerModel {

    /**
     * 获取列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    void getExchangeList(String distributorid, String pageindex, String sign, ICallBackListener callBackListener);

    /**
     * 获取详情
     *
     * @param distributorid
     * @param productid
     * @param sign
     */
    void getExchangeDetial(String distributorid, String productid, String sign, ICallBackListener callBackListener);

    /**
     * 提交礼品兑换
     *
     * @param exchangeBean
     * @param callBackListener
     */
    void doExchange(ExchangeBean exchangeBean, ICallBackListener callBackListener);

    /**
     * 获取充值列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    void getRechargeRecordList(String distributorid, String pageindex, String sign, ICallBackListener callBackListener);

    /**
     * 添加充值记录
     *
     * @param distributorid
     * @param tuanbi
     * @param rmb
     * @param sign
     * @param callBackListener
     */
    void addRechargeReorder(String distributorid,int payType, String tuanbi, String rmb, String sign, ICallBackListener callBackListener);

    /**
     * 数据加载
     *
     * @param distributorid
     * @param sign
     */
    void getExchangetuanbi(String distributorid, String sign, ICallBackListener callBackListener);


    /**
     * 列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    void getExchangelogList(String distributorid, String pageindex, String sign, ICallBackListener callBackListener);


    /**
     * 列表详情
     *
     * @param distributorid
     * @param logid
     * @param sign
     * @param callBackListener
     */
    void getExchangelogDetail(String distributorid, String logid, String sign, ICallBackListener callBackListener);

    /**
     * 支付宝回调
     *
     * @param logid
     * @param state
     * @param errormsg
     * @param sign
     * @param callBackListener
     */
    void doAlipaySuccess(String logid, String state, String errormsg, String sign, ICallBackListener callBackListener);
}
