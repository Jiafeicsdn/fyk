package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/5/31 0031.
 * 账户修改
 */
public class AccountUpdateActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    @ViewInject(R.id.tv_kahao)
    private TextView tv_kahao;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_account_one)
    private TextView tv_account_one;
    @ViewInject(R.id.tv_name_one)
    private TextView tv_name_one;
    private String distributorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);
        ViewUtils.inject(this);
        tv_title.setText("账户提现");
        rl_publish.setVisibility(View.VISIBLE);
        tv_right.setText("修改");

    }

    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                openActivity(CashReceiveActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        distributorid = PreferenceHelper.readString(AccountUpdateActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign = TGmd5.getMD5(distributorid);
            if (checkNet()) {
                getData(distributorid, sign);
            }
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 获取账户信息
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getBankInfo(AccountUpdateActivity.this, maps, new OnAccountRequestListener());
    }

    private class OnAccountRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    /**************获取账户信息*************/
                    String info_ = array.get(1).toString();
                    JSONObject jsonObject_info = new JSONObject(info_);
                    String bank_name = jsonObject_info.getString("BankTypeName");
                    String cardNum = jsonObject_info.getString("CardNO");
                    String cardName = jsonObject_info.getString("CardName");
                    String phone = array.get(2).toString();
                    if (bank_name.equals("支付宝")) {
                        tv_account_one.setText("支付宝账户");
                        tv_name_one.setText("真实姓名");
                    } else {
                        tv_account_one.setText("卡号/账户");
                        tv_name_one.setText("开户姓名");
                    }
                    tv_type.setText(bank_name);
                    tv_kahao.setText(cardNum);
                    tv_name.setText(cardName);
                    tv_phone.setText(phone);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
