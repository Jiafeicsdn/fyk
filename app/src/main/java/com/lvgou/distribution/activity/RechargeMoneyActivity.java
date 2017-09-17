package com.lvgou.distribution.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.alipay.AuthResult;
import com.lvgou.distribution.alipay.PayDemoActivity;
import com.lvgou.distribution.alipay.PayResult;
import com.lvgou.distribution.alipay.util.OrderInfoUtil2_0;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.GetRechargePresenter;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.GetPrepayIdTask;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GetRechargeView;
import com.lvgou.distribution.view.GroupView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/11/1.
 * 团币充值
 */
public class RechargeMoneyActivity extends BaseActivity implements GroupView, GetRechargeView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_recoder;
    @ViewInject(R.id.img_teacher_head)
    private ImageView img_head;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_tuanbi_num;
    @ViewInject(R.id.rl_ten_yuan)
    private RelativeLayout rl_ten_yuan;
    @ViewInject(R.id.tv_ten_zero_zero)
    private TextView tv_ten_zero_zero;
    @ViewInject(R.id.tv_ten_money)
    private TextView tv_ten_money;
    @ViewInject(R.id.rl_twenty_yuan)
    private RelativeLayout rl_twenty_yuan;
    @ViewInject(R.id.tv_two_zero_five)
    private TextView tv_two_zero_five;
    @ViewInject(R.id.tv_twenty_money)
    private TextView tv_twenty_money;
    @ViewInject(R.id.rl_thirty_yuan)
    private RelativeLayout rl_thirty_yuan;
    @ViewInject(R.id.tv_three_one_zero)
    private TextView tv_three_one_zero;
    @ViewInject(R.id.tv_thirty_money)
    private TextView tv_thirty_money;
    @ViewInject(R.id.rl_fifty_yuan)
    private RelativeLayout rl_fifty_yuan;
    @ViewInject(R.id.tv_five_two_zero)
    private TextView tv_five_two_zero;
    @ViewInject(R.id.tv_fifty_money)
    private TextView tv_fifty_money;
    @ViewInject(R.id.rl_eighty_yuan)
    private RelativeLayout rl_eighty_yuan;
    @ViewInject(R.id.tv_eight_five_zero)
    private TextView tv_eight_five_zero;
    @ViewInject(R.id.tv_eighty_money)
    private TextView tv_eighty_money;
    @ViewInject(R.id.rl_handred_yuan)
    private RelativeLayout rl_handred_yuan;
    @ViewInject(R.id.tv_one_one_zero_zero)
    private TextView tv_one_one_zero_zero;
    @ViewInject(R.id.tv_hundred_money)
    private TextView tv_hundred_money;
    @ViewInject(R.id.tv_sure_recharge)
    private TextView tv_sure_recharge;
    @ViewInject(R.id.rl_alipay)
    private RelativeLayout rl_alipay;
    @ViewInject(R.id.im_alipay)
    private ImageView im_alipay;
    @ViewInject(R.id.rl_weixin)
    private ImageView rl_weixin;
    @ViewInject(R.id.im_weixin)
    private ImageView im_weixin;
    @ViewInject(R.id.im_star)
    private ImageView im_star;
    @ViewInject(R.id.tv_messagetext)
    private TextView tv_messagetext;
    @ViewInject(R.id.rl_message)
    private RelativeLayout rl_message;
    private String recharge_money = "0";
    private String tuanbi_info = "";
    private String tuanbi_num_send = "";
    private String name = "";
    private String tuanbi_num = "";
    private String distributorid = "";
    private String star = "";
    private String payCheck = "alipay";


    private TuanbiExchangePresenter tuanbiExchangePresenter;
    private GetRechargePresenter getRechargePresenter;

    DisplayImageOptions options;

    private String logid = "";
    private String pay_state = "";

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016080901724287";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088021297116162";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "zhouleijian@quygt.com";

    /**
     * 微信支付业务：入参app_id
     */
    public static final String WXAPPID = "wx384a0e20d6d4b6bd";


    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANYlUM+Iv46plRjBOOZ5MCdpQDLorjrBH7GNpqjMGLYryyON4eIY+AYD+JMTAlAIFxOzon2AJF5HeRnyA4XTXJmw96yM2EOJXjOW+w6ocnGWACDQTH4rn1vgjvUlVF6T3D31oylTwoS/37xDg9TkxbSbmI4axHPJUP6c2WhwuNGlAgMBAAECgYEAj1dTDFfgwUHKR1OvHraoAPl2u5z8Yt+6s0K59+sF74rI4vep54oHGx+1V901gxSnPczUS2Vm8qSs7y0MJpwgMpZKhjXsAI31sRqzbpbWNWBFuWaajCtTttzsGhgA8B9tbsc2Hjjm7UC7YSPlEn0jWwQoVnd+SO692rI2cjxGmIECQQD3vRy89JsHmEHEPUEqUgl7CBIexKthWVIQlbf7PNpW/lnjOClOkMfrUhlHPfFxs7tDj5msnXLQfwKDW+aNqCFJAkEA3UltnMF4UOdag193k/59B4GhPMU41W6eXKqaWRwdRU7JdRsc4K30hzESW6N6oKvTQFiJzTMdv2F81aBPDc0JfQJAbxVC748WfJ9OzflRYPKMAbiqt1UkK3BrlbgsWOD+XgeKspGaI/pTSjbz0rf5rSwUCcU3+OhYdRiePdxVUqtS0QJBAKKuPLslMIKp0s0J/ir6yIggMJ0wkJu3+ww9D8O6+3ncdhZ1nEFBIafR16EvChPcvi1r6cLFdXUhAlk6xWNr/TECQQDj2HZYrGeGo1xy/BvC1E4xrjyIb6mrXsWKug1swVxFsiD578U67nJNbFgDOgURUlI4vVRKrLXzcnLcbSp0rf5i";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String memo = payResult.getMemo();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showHintDialog("支付成功");
                        String origin = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
                        int a = Integer.parseInt(origin) + Integer.parseInt(tuanbi_num_send);
                        PreferenceHelper.write(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, a + "");
                        tv_tuanbi_num.setText("剩余团币:　" + a);
                        pay_state = "1";
                        openActivity(RechargeRecordListActivity.class);
                    } else {
                        pay_state = "0";
                    }

                    String sign = TGmd5.getMD5(logid + pay_state + memo);
                    tuanbiExchangePresenter.doAlipay(logid, pay_state, memo, sign);

                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        MyToast.makeText(RechargeMoneyActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        MyToast.makeText(RechargeMoneyActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private int discount;
    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("rechargesuccess".equals(action)) {
            String origin = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
            int a = Integer.parseInt(origin) + Integer.parseInt(tuanbi_num_send);
            PreferenceHelper.write(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, a + "");
            tv_tuanbi_num.setText("剩余团币:　" + a);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_money);
        ViewUtils.inject(this);
        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);
        getRechargePresenter = new GetRechargePresenter(this);
        EventBus.getDefault().register(this);
        name = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        distributorid = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        tuanbi_num = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        star = PreferenceHelper.readString(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STAR);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), img_head, options);
        tv_name.setText(name);
        tv_tuanbi_num.setText("剩余团币: " + tuanbi_num);
        if (star.equals("1")) {
            im_star.setBackgroundResource(R.mipmap.onelevel_light_icon);
        } else if (star.equals("2")) {
            im_star.setBackgroundResource(R.mipmap.twolevel_light_icon);
        } else if (star.equals("3")) {
            im_star.setBackgroundResource(R.mipmap.threelevel_light_icon);
        } else if (star.equals("4")) {
            im_star.setBackgroundResource(R.mipmap.fourlevel_light_icon);
        } else if (star.equals("5")) {
            im_star.setBackgroundResource(R.mipmap.fivelevel_light_icon);
        } else if (star.equals("6")) {
            im_star.setBackgroundResource(R.mipmap.sixlevel_light_icon);
        } else if (star.equals("7")) {
            im_star.setBackgroundResource(R.mipmap.sevenlevel_light_icon);
        } else if (star.equals("8")) {
            im_star.setBackgroundResource(R.mipmap.eightlevel_light_icon);
        } else if (star.equals("9")) {
            im_star.setBackgroundResource(R.mipmap.ninelevel_light_icon);
        } else if (star.equals("10")) {
            im_star.setBackgroundResource(R.mipmap.tenlevel_light_icon);
        }
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        getRechargePresenter.getRecharge(distributorid, sign);
    }


    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_ten_yuan, R.id.rl_twenty_yuan, R.id.rl_thirty_yuan, R.id.rl_fifty_yuan, R.id.rl_eighty_yuan, R.id.rl_handred_yuan, R.id.tv_sure_recharge, R.id.rl_alipay, R.id.rl_weixin})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                openActivity(RechargeRecordListActivity.class);
                break;
            case R.id.rl_ten_yuan:
                recharge_money = "10";
                tuanbi_num_send = "100";
                tuanbi_info = "充值100团币";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_ten_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_ten_zero_zero.setTextColor(getResources().getColor(R.color.bg_white));
                tv_ten_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.rl_twenty_yuan:
                recharge_money = "20";
                tuanbi_num_send = "205";
                tuanbi_info = "充值205团币";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_twenty_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_two_zero_five.setTextColor(getResources().getColor(R.color.bg_white));
                tv_twenty_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.rl_thirty_yuan:
                recharge_money = "30";
                tuanbi_num_send = "310";
                tuanbi_info = "充值310团币";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_thirty_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_three_one_zero.setTextColor(getResources().getColor(R.color.bg_white));
                tv_thirty_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.rl_fifty_yuan:
                recharge_money = "50";
                tuanbi_info = "充值520团币";
                tuanbi_num_send = "520";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_fifty_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_five_two_zero.setTextColor(getResources().getColor(R.color.bg_white));
                tv_fifty_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.rl_eighty_yuan:
                recharge_money = "80";
                tuanbi_num_send = "850";
                tuanbi_info = "充值850团币";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_eighty_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_eight_five_zero.setTextColor(getResources().getColor(R.color.bg_white));
                tv_eighty_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.rl_handred_yuan:
                recharge_money = "100";
                tuanbi_num_send = "1100";
                tuanbi_info = "充值1100团币";
                recharge_money = Double.parseDouble(recharge_money) * discount / 100 + "";
                initSelect();
                rl_handred_yuan.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_one_one_zero_zero.setTextColor(getResources().getColor(R.color.bg_white));
                tv_hundred_money.setTextColor(getResources().getColor(R.color.bg_white));
                break;
            case R.id.tv_sure_recharge:
                if (recharge_money.equals("0")) {
                    MyToast.show(RechargeMoneyActivity.this, "请选择充值团币");
                    return;
                } else {
//                    showLoadingProgressDialog(this, "");

                    if (payCheck.equals("alipay")) {
                        String sign = TGmd5.getMD5(distributorid + 2 + tuanbi_num_send + recharge_money);
                        tuanbiExchangePresenter.addRechargeRecord(distributorid, 2, tuanbi_num_send, recharge_money, sign);
                    } else if (payCheck.equals("weixinpay")) {

                        String sign = TGmd5.getMD5(distributorid + 1 + tuanbi_num_send + recharge_money);
                        tuanbiExchangePresenter.addRechargeRecord(distributorid, 1, tuanbi_num_send, recharge_money, sign);
                    }
                }
                break;
            case R.id.rl_alipay://选中支付宝支付
                im_alipay.setBackgroundResource(R.mipmap.checkbox_check_icon);
                im_weixin.setBackgroundResource(R.mipmap.checkbox_default_icon);
                payCheck = "alipay";
                break;
            case R.id.rl_weixin://选中微信支付
                im_weixin.setBackgroundResource(R.mipmap.checkbox_check_icon);
                im_alipay.setBackgroundResource(R.mipmap.checkbox_default_icon);
                payCheck = "weixinpay";
                break;
        }
    }

    public void initSelect() {
        rl_ten_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_ten_zero_zero.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_ten_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        rl_twenty_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_two_zero_five.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_twenty_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        rl_thirty_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_three_one_zero.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_thirty_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        rl_fifty_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_five_two_zero.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_fifty_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        rl_eighty_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_eight_five_zero.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_eighty_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        rl_handred_yuan.setBackgroundResource(R.drawable.recharge_bg_text);
        tv_one_one_zero_zero.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        tv_hundred_money.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
    }


    /**
     * 支付宝支付业务
     */
    public void payV2(String price, String product_info, String time, String out_trade_no) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, price, product_info, time, out_trade_no);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeMoneyActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 支付宝账户授权业务
     * 暂时不需要，
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_PRIVATE);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(RechargeMoneyActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        MyToast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        tuanbiExchangePresenter.attach(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tuanbiExchangePresenter.dettach();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 成功回调接口
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 5:// 添加 充值记录
                try {
                    closeLoadingProgressDialog();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        logid = array.get(0).toString();
                    }
                    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date_e = dfs.format(new Date());
                    /*Log.e("kjhsakjs", "****1***" + recharge_money);
                    Log.e("kjhsakjs", "****2***" + tuanbi_info);
                    Log.e("kjhsakjs", "****3***" + date_e);
                    Log.e("kjhsakjs", "****4***" + logid);*/
                    if (payCheck.equals("alipay")) {//调用支付宝支付
                        payV2(recharge_money, tuanbi_info, date_e, logid);
                    } else if (payCheck.equals("weixinpay")) {
                        //调用微信支付
                        MyToast.makeText(this, "微信支付开始", Toast.LENGTH_SHORT).show();
//                        int price = (int) (Double.parseDouble(recharge_money) * 100);
                        int price = (int) (Float.parseFloat(recharge_money) * 100);
                        GetPrepayIdTask getPrepayIdTask = new GetPrepayIdTask(this, price + "", "蜂优客团币充值-" + tuanbi_info, logid);
                        getPrepayIdTask.execute();
                        mcache.put("wxlogid", logid);
//                        getName(logid, recharge_money);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 9:// 支付宝回调
                try {
                    JSONObject jsonObject_ = new JSONObject(respanse);
                    String status_ = jsonObject_.getString("status");
                    if (status_.equals("1")) {
                        String result = jsonObject_.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonObject_person = new JSONObject(data);
                        String tuanbi = jsonObject_person.getString("TuanBi");
                        PreferenceHelper.write(RechargeMoneyActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    /**
     * 失败回调接口
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    /**
     * 取消弹框
     */
    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void OnGetRechargeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            discount = Integer.parseInt(jsa.get(0).toString());
//            discount = Integer.parseInt("92");
            if (discount == 100) {
//                tv_messagetext.setVisibility(View.GONE);
                rl_message.setVisibility(View.GONE);
            }
            double discount2 = discount / 10.0;
            tv_messagetext.setText("目前您是最贵的lv" + star + "用户，享受" + discount2 + "折充值优惠");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void OnGetRechargeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeGetRechargeProgress() {

    }
}
