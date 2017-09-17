package com.lvgou.distribution.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.RechargeRecordListActivity;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/4/11.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, GroupView {
    private TuanbiExchangePresenter tuanbiExchangePresenter;
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    /**
     * 微信支付业务：入参app_id
     */
    public static final String WXAPPID = "wx384a0e20d6d4b6bd";

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);
        api = WXAPIFactory.createWXAPI(this, WXAPPID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
    }


    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        if (errCode == 0) {
            //0 成功 展示成功页面
//            MyToast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(Constant.ACTION_NAME);
            sendBroadcast(intent);*/
            showLoadingProgressDialog(this, "");
            String logid = mcache.getAsString("wxlogid");
            String sign = TGmd5.getMD5(logid + "1" + "-");
            tuanbiExchangePresenter.doAlipay(logid, "1", "-", sign);

        } else if (errCode == -1) {
            //-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
            MyToast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            showLoadingProgressDialog(this, "");
            String logid = mcache.getAsString("wxlogid");
            String sign = TGmd5.getMD5(logid + "0" + resp.errStr);
            tuanbiExchangePresenter.doAlipay(logid, "0", resp.errStr, sign);
        } else if (errCode == -2) {
            //-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
            MyToast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
//        MyToast.makeText(this, "=" + respanse, Toast.LENGTH_SHORT).show();
//        Log.e("kjhaskfhs", "===========" + respanse);
        openActivity(RechargeRecordListActivity.class);
        EventBus.getDefault().post("rechargesuccess");
        finish();
//        finish();
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
//        Log.e("kjhaskfhs", "**********" + respanse);
//        MyToast.makeText(this, "*" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }
}