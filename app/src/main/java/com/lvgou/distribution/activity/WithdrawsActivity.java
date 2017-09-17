package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/1 0001.
 * 提现页面
 */
public class WithdrawsActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_total_money)
    private TextView tv_total;
    @ViewInject(R.id.tv_lock_money)
    private TextView tv_lock;
    @ViewInject(R.id.tv_tixian_money)
    private TextView tv_withdraws;
    @ViewInject(R.id.tv_account)
    private TextView tv_accont;
    @ViewInject(R.id.btn_tixian)
    private Button btn_tixian;
    @ViewInject(R.id.rl_account)
    private RelativeLayout rl_account;
    @ViewInject(R.id.img)
    private ImageView img;

    private String accont_name = "";
    private String account_num = "";
    private String name = "";
    private String time = "";
    private String tixian = "";
    private String total = "";
    private String account = "";


    private String distributorid = "";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraws);
        ViewUtils.inject(this);
        tv_title.setText("申请提现");
        distributorid = PreferenceHelper.readString(WithdrawsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            getData(distributorid, sign);
        }
    }

    @OnClick({R.id.rl_back, R.id.btn_tixian, R.id.rl_account})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_tixian:
                showDialog();
                break;
            case R.id.rl_account:
                if (account.equals("")) {
                    openActivity(CashReceiveActivity.class);
                } else {
                    openActivity(AccountUpdateActivity.class);
                }
                break;
        }
    }

    /**
     * 收支提现
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getIncomeInfo(WithdrawsActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                if (status.equals("1")) {
                    String result = json.getString("result");
                    JSONArray array = new JSONArray(result);
                    total = array.get(2).toString();
                    String local = array.get(1).toString();
                    tixian = array.get(0).toString();

                    account = array.get(3).toString();

                    accont_name = array.get(4).toString();
                    account_num = array.get(5).toString();
                    name = array.get(6).toString();
                    time = array.get(7).toString();

                    tv_total.setText("¥" + total);
                    tv_lock.setText("¥" + local);
                    tv_withdraws.setText("¥" + tixian);
                    if (account.equals("")) {
                        tv_accont.setText("设置提现账户");
                        tv_accont.setTextColor(getResources().getColor(R.color.bg_code_number));
                        img.setVisibility(View.INVISIBLE);
                    } else {
                        tv_accont.setText(account);
                        img.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(WithdrawsActivity.this, json.getString("message"));
                    if (json.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提现操作
     *
     * @param distributorid
     * @param price
     * @param cardno
     * @param outtime
     * @param sign
     */
    public void withDraws(String distributorid, String price, String cardno, String outtime, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("price", price);
        maps.put("cardno", cardno);
        maps.put("outtime", outtime);
        maps.put("sign", sign);
        RequestTask.getInstance().getWithdraws(WithdrawsActivity.this, maps, new OnWithRequestListener());
    }

    private class OnWithRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                if (status.equals("1")) {
                    MyToast.show(WithdrawsActivity.this, "提现成功");
                } else if (status.equals("0")) {
                    MyToast.show(WithdrawsActivity.this, json.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showDialog() {
        dialog = new Dialog(WithdrawsActivity.this,
                R.style.Mydialog);
        View view1 = View.inflate(WithdrawsActivity.this,
                R.layout.dialog_withwards_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_money = (TextView) view1.findViewById(R.id.tv_money);
        TextView tv_name = (TextView) view1.findViewById(R.id.tv_name);
        TextView tv_account = (TextView) view1.findViewById(R.id.tv_account);
        TextView tv_account_num = (TextView) view1.findViewById(R.id.tv_account_num);
        tv_money.setText("¥" + tixian);
        tv_name.setText(name);
        tv_account.setText(accont_name);
        tv_account_num.setText(account_num);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5(distributorid + tixian + account_num + time);
                withDraws(distributorid, tixian, account_num, time, sign);
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view1);
        dialog.show();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
