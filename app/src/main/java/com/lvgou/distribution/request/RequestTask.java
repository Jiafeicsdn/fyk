package com.lvgou.distribution.request;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.constants.Url;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.request.extension.IRequest;
import com.xdroid.request.extension.config.CacheConfig;
import com.xdroid.request.extension.config.Priority;
import com.xdroid.request.extension.config.RequestMethod;
import com.xdroid.request.extension.interfaces.OnRequestListener;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 请求任务类
 *
 * @author Snow
 * @since 2015/10/27.
 */
public class RequestTask {

	/*
     * ==================== 单例
	 */

    private RequestTask() {
    }

    private static class RequestTaskHolder {
        public static final RequestTask INSTANCE = new RequestTask();
    }

    public static RequestTask getInstance() {
        return RequestTaskHolder.INSTANCE;
    }

    /**
     * 获取缓存请求配置
     *
     * @return
     */
    private CacheConfig getCacheRequestConfig() {

        return RequestManager.getInstance().getCacheRequestConfig();
    }

    /**
     * 获取普通请求配置
     *
     * @return
     */
    private CacheConfig getRequestConfig() {
        return RequestManager.getInstance().getRequestConfig();
    }

    /**
     * 获取一个请求对象
     *
     * @param activity
     * @return
     */
    private IRequest getRequest(Activity activity) {
        IRequest request = new IRequest(activity);
        request.setTag(activity);
        request.getDefaultRequestBody().setMethod(RequestMethod.POST);
        request.setPriority(Priority.NORMAL);

        if (!StringUtils.isEmpty(CookieManager.getInstance().getCookies(
                activity))) {
            request.getDefaultRequestBody()
                    .getHeaders()
                    .put("Cookie",
                            CookieManager.getInstance().getCookies(activity));
            LogUtils.i("设置Cookie:"
                    + CookieManager.getInstance().getCookies(activity));
        }

        return request;
    }

    /**
     * 获取一个请求对象
     *
     * @param context
     * @return
     */
    private IRequest getRequest(Context context) {
        IRequest request = new IRequest(context);
        request.setTag(context);
        request.getDefaultRequestBody().setMethod(RequestMethod.POST);
        request.setPriority(Priority.NORMAL);

        if (!StringUtils.isEmpty(CookieManager.getInstance()
                .getCookies(context))) {
            request.getDefaultRequestBody()
                    .getHeaders()
                    .put("Cookie",
                            CookieManager.getInstance().getCookies(context));
            LogUtils.i("设置Cookie:"
                    + CookieManager.getInstance().getCookies(context));
        }

        return request;
    }

    /**
     * 解析请求参数
     *
     * @param request
     * @param params
     * @return
     */
    private IRequest parseRequestParams(IRequest request,
                                        Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> item = it.next();
            String key = item.getKey();
            Object value = item.getValue();
            if (value == null) {
                value = "";
            }

            if (value instanceof File) {
                request.getDefaultRequestBody().getFileParams()
                        .put(key, (File) value);
            }

            if (value instanceof String) {
                request.getDefaultRequestBody().getParams()
                        .put(key, value.toString());
            }

        }

        return request;
    }

    /**
     * 执行请求(无参重载)
     *
     * @param activity
     * @param cacheConfig
     * @param requestUrl
     * @param onRequestListener
     * @return
     */
    private IRequest performRequest(Activity activity, CacheConfig cacheConfig,
                                    String requestUrl, OnRequestListener onRequestListener) {
        return performRequest(activity, cacheConfig, requestUrl, null,
                onRequestListener);
    }

    /**
     * 执行请求(无参重载)
     *
     * @param context
     * @param cacheConfig
     * @param requestUrl
     * @param onRequestListener
     * @return
     */
    private IRequest performRequest(Context context, CacheConfig cacheConfig,
                                    String requestUrl, OnRequestListener onRequestListener) {
        return performRequest(context, cacheConfig, requestUrl, null,
                onRequestListener);
    }

    /**
     * 执行请求
     *
     * @param activity
     * @param cacheConfig
     * @param requestUrl
     * @param params
     * @param onRequestListener
     * @return
     */
    private IRequest performRequest(Activity activity, CacheConfig cacheConfig,
                                    String requestUrl, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        IRequest request = getRequest(activity);
        request.setCacheConfig(cacheConfig);
        request.setUrl(requestUrl);
        request.setOnRequestListener(onRequestListener);
        parseRequestParams(request, params);
        RequestManager.getInstance().getRequestQueue().add(request);
        if (params != null) {
            LogUtils.i("RequestParams:" + params.toString());
        }
        return request;
    }

    /**
     * 执行请求
     *
     * @param context
     * @param cacheConfig
     * @param requestUrl
     * @param params
     * @param onRequestListener
     * @return
     */
    private IRequest performRequest(Context context, CacheConfig cacheConfig,
                                    String requestUrl, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        IRequest request = getRequest(context);
        request.setCacheConfig(cacheConfig);
        request.setUrl(requestUrl);
        request.setOnRequestListener(onRequestListener);
        parseRequestParams(request, params);
        RequestManager.getInstance().getRequestQueue().add(request);
        if (params != null) {
            LogUtils.i("RequestParams:" + params.toString());
        }
        return request;
    }

    /**
     * 执行请求
     *
     * @param context
     * @param cacheConfig
     * @param requestUrl
     * @param params
     * @param onRequestListener
     * @return
     */
    private IRequest performRequestOne(Context context, CacheConfig cacheConfig,
                                       String requestUrl, Map<String, Object> params,
                                       OnRequestListener onRequestListener) {
        IRequest request = getRequest(context);
        request.setCacheConfig(cacheConfig);
        request.setUrl(requestUrl);
        request.setOnRequestListener(onRequestListener);
        parseRequestParams(request, params);
        RequestManager.getInstance().getRequestQueue().add(request);
        if (params != null) {
            LogUtils.i("RequestParams:" + params.toString());
        }
        return request;
    }

    /**
     * 登录
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doLogin(BaseActivity activity, Map<String, Object> params,
                            OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.LOGIN, params, onRequestListener);
    }

    /**
     * 注册
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doRegister(BaseActivity activity, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.REGISTER, params, onRequestListener);
    }

    /**
     * 找回密码
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doFindPwd(BaseActivity activity, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.FIND_PWD, params, onRequestListener);
    }

    /**
     * 找回密码，发送验证码
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doSendCode(BaseActivity activity, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SEND_CODE, params, onRequestListener);
    }

    /**
     * 注册，找回密码
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doSendRegisterCode(BaseActivity activity, Map<String, Object> params,
                                       OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SEND_CODE_REGISTER, params, onRequestListener);
    }

    /**
     * 上传证件照片
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doUploadCard(BaseActivity activity, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.UPLOAD_CARD, params, onRequestListener);
    }

    /**
     * 首页信息
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getIndex(Activity activity, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.INDEX, params, onRequestListener);
    }

    /**
     * 签到
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doSign(Activity activity, Map<String, Object> params,
                           OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SIGN, params, onRequestListener);
    }

    /**
     * 修改店铺名称
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doUpdateShopName(BaseActivity activity, Map<String, Object> params,
                                     OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SAVE_SHOP_NAME, params, onRequestListener);
    }

    /**
     * 获取店铺名称
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getShopName(BaseActivity activity, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.GET_SHOP_NAME, params, onRequestListener);
    }

    /**
     * 修改密码
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doUpdatePwd(BaseActivity activity, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.UPDATE_PWD, params, onRequestListener);
    }

    /**
     * 发送短信,个人中心
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doSendMessage(BaseActivity activity, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SEND_MESSAGE_ACCOUNT, params, onRequestListener);
    }

    /**
     * 获取账户信息
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getBankInfo(BaseActivity activity, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.USER_BANK, params, onRequestListener);
    }

    /**
     * 提交账户信息
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest commitBankInfo(BaseActivity activity, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.COMMIT_USER_BANK, params, onRequestListener);
    }

    /**
     * 收支明细
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getUserIncome(BaseActivity activity, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.USER_INCOME, params, onRequestListener);
    }

    /**
     * 收支明细
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getWithwordsRecord(BaseActivity activity, Map<String, Object> params,
                                       OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.WITHWORDS, params, onRequestListener);
    }

    /**
     * 获取证件
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getCardGuide(BaseActivity activity, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.SHOW_CARD_MANGERE, params, onRequestListener);
    }

    /**
     * 上传证件
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest uploadCardGuide(BaseActivity activity, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.UPLOAD_GUIDE_IMG, params, onRequestListener);
    }

    /**
     * 订单列表
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getOrderList(BaseActivity activity, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.ORDER_LIST, params, onRequestListener);
    }

    /**
     * 订单详情
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getOrderDetial(BaseActivity activity, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.ORDER_DETIAL, params, onRequestListener);
    }

    /**
     * 爆品速推
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getSpeedGoods(BaseActivity activity, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.PUSH_SPEED_LIST, params, onRequestListener);
    }

    /**
     * 爆品速推
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getMyGroupers(BaseActivity activity, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.MY_GROUPERS, params, onRequestListener);
    }

    /**
     * 导游学院列表
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getGuideList(BaseActivity activity, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.GUIDES_LIST, params, onRequestListener);
    }

    /**
     * 获取点赞评价信息
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getPriaseCommit(BaseActivity activity, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.GET_PRIASE_COMMIT, params, onRequestListener);
    }

    /**
     * 评论操作
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doCommit(BaseActivity activity, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.DO_COMMIT, params, onRequestListener);
    }

    /**
     * 点赞操作
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doPraise(BaseActivity activity, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.DO_PRIASE, params, onRequestListener);
    }

    /**
     * 我的任务
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest getTaskList(BaseActivity activity, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.TASK_LIST, params, onRequestListener);
    }

    /**
     * 操作任务
     *
     * @param activity
     * @param params
     * @param onRequestListener
     */
    public IRequest doTaskOperate(BaseActivity activity, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(activity, getRequestConfig(), Url.TASK_OPERATE, params, onRequestListener);
    }

    /**
     * 商品管理列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getGoodsList(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GOODS_LIST, params, onRequestListener);
    }

    /**
     * 国内市场
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getGoodListIn(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GOODS_LIST_IN, params, onRequestListener);
    }

    /**
     * 移除操作
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doRemove(Context context, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REMOVE_GOODS, params, onRequestListener);
    }

    /**
     * 活动列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getActionList(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ACTION_LIST, params, onRequestListener);
    }

    /**
     * 加载更多
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doLoadMore(Context context, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.LOAD_MORE, params, onRequestListener);
    }

    /**
     * 移入货架
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doRemoveGoods(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REMOVE_SHELVES, params, onRequestListener);
    }

    /**
     * 获取排序列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getSortList(BaseActivity context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SORT_LIST, params, onRequestListener);
    }

    /**
     * 保存排序列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doSaveSort(BaseActivity context, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SAVE_SORT, params, onRequestListener);
    }

    /**
     * 获取海外商品
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getSeaOutGoods(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SEA_OUT_GOOD, params, onRequestListener);
    }

    /**
     * 团币明细
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getTuanbiDetial(Context context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.TUANBI_DETIAL, params, onRequestListener);
    }

    /**
     * 兑换记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getExchangeList(Context context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.EXCHANGE_LIST, params, onRequestListener);
    }

    /**
     * 兑换 验证码
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getCodeExchange(Context context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.EXCHANGE_CODE, params, onRequestListener);
    }

    /**
     * 兑换 验证码
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doExchangeRed(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DO_EXCHANGE, params, onRequestListener);
    }

    /**
     * 我的收益
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getMyProfit(BaseActivity context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.MYPROFIT, params, onRequestListener);
    }

    /**
     * 收支提现
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getIncomeInfo(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.INCOME_INRO, params, onRequestListener);
    }

    /**
     * 提现
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getWithdraws(BaseActivity context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.WITHDEAELOAD, params, onRequestListener);
    }

    /**
     * 入团邀请
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doJoinGroup(BaseActivity context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.JION_GROUP, params, onRequestListener);
    }

    /**
     * 邀请列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getInviteList(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.INVITE_LIST, params, onRequestListener);
    }

    /**
     * 领取经验
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doInviteOpreation(BaseActivity context, Map<String, Object> params,
                                      OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DO_INVITE_OPREATE, params, onRequestListener);
    }

    /**
     * 邀请导游
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest invisteGuiderInfo(BaseActivity context, Map<String, Object> params,
                                      OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GUIDER_INFO, params, onRequestListener);
    }

    /**
     * 我的店铺
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getMyShopInfo(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.MY_SHOP_INFO, params, onRequestListener);
    }

    /**
     * 上传头像
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doUploadHead(Activity context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.UPLOAD_HEAD, params, onRequestListener);
    }

    /**
     * 保存头像
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest savePicurl(BaseActivity context, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SAVE_IMAGE, params, onRequestListener);
    }

    /**
     * 收支详情
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getIncomeDetial(BaseActivity context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.INCOME_DETIAL, params, onRequestListener);
    }

    /**
     * 查看详情
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest seekIncomeDetial(BaseActivity context, Map<String, Object> params,
                                     OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SEEK_DETIAL, params, onRequestListener);
    }

    /**
     * 短信模版记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest smsModelList(BaseActivity context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SMS_MODEL_LIST, params, onRequestListener);
    }

    /**
     * 单个短信模版记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest oneSmsModelList(BaseActivity context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ONE_MODEL_LIST, params, onRequestListener);
    }

    /**
     * 新增编辑模版
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest editAddSmsMode(BaseActivity context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.EDIT_ADD_MODEL, params, onRequestListener);
    }

    /**
     * 删除模版
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest deleteSmsMode(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DELETE_SMS_MODEL, params, onRequestListener);
    }

    /**
     * 分组列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest groupList(BaseActivity context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GROUP_LIST, params, onRequestListener);
    }

    /**
     * 单个分组列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest oneGroupList(BaseActivity context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ONE_GROUP_LIST, params, onRequestListener);
    }

    /**
     * 编辑分组
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest editGroup(BaseActivity context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.EDIT_GROUP, params, onRequestListener);
    }

    /**
     * 删除分组
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest deleteGroup(BaseActivity context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DELETE_GROUP, params, onRequestListener);
    }

    /**
     * 发送记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest smsSendRecord(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SMS_SEND_RECORD, params, onRequestListener);
    }

    /**
     * 发送记录详情
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest oneSmsSendRecord(BaseActivity context, Map<String, Object> params,
                                     OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ONE_SMS_SEND_RECORD, params, onRequestListener);
    }

    /**
     * 群发短信
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest sendSmsQun(BaseActivity context, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SEND_SMS_QUN, params, onRequestListener);
    }

    /**
     * 阅读公告
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest readNotice(BaseActivity context, Map<String, Object> params,
                               OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ACTIVITY_READ, params, onRequestListener);
    }

    /**
     * 选择应用列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getApplicationList(Context context, Map<String, Object> params,
                                       OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.APPLICATION_LIST, params, onRequestListener);
    }

    /**
     * 说明
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest inviteFriends(BaseActivity context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.INVITE_FROENDS, params, onRequestListener);
    }

    /**
     * 选择应用列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getTiXian(Context context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.TIANXIAN_RECODER, params, onRequestListener);
    }

    /**
     * 推广收益列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getProfit(Context context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.PROFIT_TUIGUANG, params, onRequestListener);
    }


    /**
     * 去申请
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doSupply(Context context, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GO_SUPPLY, params, onRequestListener);
    }

    /**
     * 推广提现
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doTixian(Context context, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.TIXIAN_TUIGUANG, params, onRequestListener);
    }

    /**
     * 更新
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doUpdate(Context context, Map<String, Object> params,
                             OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.UPDATE_VERSION, params, onRequestListener);
    }

    /**
     * 名师讲堂
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getFamousList(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.FAMOUS_TEACHER_LIST, params, onRequestListener);
    }

    /**
     * 名师讲堂详情
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getFamousDetial(Context context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.FAMOUS_TEACHER_DETIAL, params, onRequestListener);
    }

    /**
     * 名师讲堂报名
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doFamousSignUp(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.FAMOUS_SIGN_UP, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doFamousSeek(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.FAMOUS_SEEK, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doFamousSeekApplyers(Context context, Map<String, Object> params,
                                         OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.FAMOUS_SEEK_APPLYERS, params, onRequestListener);
    }
    /**
     * 活动的查看报名人员
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doActSeekApplyers(Context context, Map<String, Object> params,
                                         OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.Act_SEEK_APPLYERS, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getPeronalInfo(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.USER_CENTRAL, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getRankList(Context context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.RANK_LIST, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doUpdateTime(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.UPDATE_TIME, params, onRequestListener);
    }

    /**
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getNickName(Context context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GET_NICK_NAME, params, onRequestListener);
    }

    /**
     * 名师讲堂查看
     *
     * @param context
     * @param onRequestListener
     */
    public IRequest gethChatNum(Context context, String token, String roomId, OnRequestListener onRequestListener) {
        IRequest iRequest = performRequest(context, getRequestConfig(), Url.GET_CHATROOM_PEOPLE + roomId, onRequestListener);
        iRequest.getDefaultRequestBody().getHeaders().put("Authorization", "Bearer " + token);
        return iRequest;
    }

    /**
     * 报备列表
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getReportList(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_LIST, params, onRequestListener);
    }

    /**
     * 报备记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getReporSearchHistory(Context context, Map<String, Object> params,
                                          OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SEARCH_HISTORY, params, onRequestListener);
    }

    /**
     * 报备记录
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getReporSearchList(Context context, Map<String, Object> params,
                                       OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_SEARCH_LIST, params, onRequestListener);
    }

    /**
     * 报备操作
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doReportShop(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_SHOP, params, onRequestListener);
    }

    /**
     * 报备上传图片
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doReportUploadImage(Context context, Map<String, Object> params,
                                        OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.UPLOAD_TUPIAN, params, onRequestListener);
    }

    /**
     * 报备上传图片
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getReportDetial(Context context, Map<String, Object> params,
                                    OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_DETIAL, params, onRequestListener);
    }

    /**
     * 删除上传图片
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doDeleteImage(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DELETE_IMG, params, onRequestListener);
    }

    /**
     * 课程分享成功回调
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getShareTuanBi(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SHARE_COMPLETE, params, onRequestListener);
    }
    /**
     * 蜂圈分享成功回调
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getShareFengTuanBi(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SHARE_FENG_COMPLETE, params, onRequestListener);
    }
    /**
     * 签到
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest qianDao(Context context, Map<String, Object> params,
                            OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.MALL_QIANDAO, params, onRequestListener);
    }

    /**
     * 认证上传
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest uploadRenZheng(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.UPLOAD_RENZHENG, params, onRequestListener);
    }

    /**
     * 认证信息保存
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest saveRenZheng(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.SAVE_INFO_RENZHENG, params, onRequestListener);
    }

    /**
     * 认证信息加载
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest loadRenZheng(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.LOAD_INFO_RENZHZNEG, params, onRequestListener);
    }

    /**
     * 评论消息加载
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getCommenList(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.GET_COMMENT_LIST, params, onRequestListener);
    }

    /**
     * 评论消息加载
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest commitCommen(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.DO_COMMIT_COMMENT, params, onRequestListener);
    }

    /**
     * 学院导游
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getOnLineSign(Context context, Map<String, Object> params,
                                  OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.NEW_GUIDER, params, onRequestListener);
    }

    /**
     * 课程回顾
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getClassReview(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.CLASS_REVIEW, params, onRequestListener);
    }

    /**
     * 热门标签
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getHotTag(Context context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.HOT_TAGR, params, onRequestListener);
    }

    /**
     * 客座讲师
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest getTeacherSeat(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.TEACHER_SEAT, params, onRequestListener);
    }

    /**
     * 客座讲师
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doPlayTimes(Context context, Map<String, Object> params,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.VIDEO_PALY_TIMES, params, onRequestListener);
    }

    /**
     * 选择购物店
     *
     * @param
     */
    public IRequest getReport_shop(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_SHOP_NEW, params, onRequestListener);
    }

    /**
     * 搜索购物店结果
     *
     * @param
     */
    public IRequest getReport_shop_result(Context context, Map<String, Object> params,
                                          OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.REPORT_SHOP_SEARCH_SULT, params, onRequestListener);
    }

    /**
     * 评分
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doEvalute(Context context, Map<String, Object> params,
                              OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.EVALUTE_POTIONS, params, onRequestListener);
    }

    /**
     * 获取群历史消息
     *
     * @param
     */
    public IRequest obtain_history_message(Context context, Map<String, Object> params,
                                           OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.HISTORY_MESSAGE, params, onRequestListener);
    }

    /**
     * 获取群历史消息
     *
     * @param
     */
    public IRequest doCancleReport(Context context, Map<String, Object> params,
                                   OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.CANCLE_REPORT, params, onRequestListener);
    }

    /**
     * 定位查询报备店铺
     *
     * @param
     */
    public IRequest getLocalData(Context context, Map<String, Object> params,
                                 OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.LOCAL_SEARCH_DATA, params, onRequestListener);
    }

    /**
     * 报备上传图片
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doCircleUploadImage(Context context, Map<String, Object> params,
                                        OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.CIRCLE_URL + "/circle/publishtalkimage", params, onRequestListener);
    }


    /**
     * 活动发布-图片上传
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doActivityUploadImage(Context context, Map<String, Object> params,
                                          OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ROOT + "/activity/uploadactivityimg", params, onRequestListener);
    }

    /**
     * 我的-实名认证上传资质照片
     *
     * @param context
     * @param params
     * @param onRequestListener
     */
    public IRequest doUploadIDCardImg(Context context, Map<String, Object> params,
                                      OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), Url.ROOT + "/User/UploadIDCardImg", params, onRequestListener);
    }

    /**
     * 天气获取城市id
     *
     * @param context
     * @param onRequestListener
     */
    public IRequest moJiWeather(Context context, String url,
                                OnRequestListener onRequestListener) {
        return performRequest(context, getRequestConfig(), url, new HashMap<String, Object>(), onRequestListener);
    }


}
