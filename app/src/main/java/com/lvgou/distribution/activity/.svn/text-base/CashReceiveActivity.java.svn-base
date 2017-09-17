package com.lvgou.distribution.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.BankEntity;
import com.lvgou.distribution.inter.OnClassifyClickListener;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ClassifyingOneListviewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Snow on 2016/3/10
 * 收款账户
 */
public class CashReceiveActivity extends BaseActivity implements OnClassifyClickListener<BankEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_bank_account)
    private TextView et_bank_account;// 开户银行
    @ViewInject(R.id.select_account)
    private RadioGroup select_account;
    @ViewInject(R.id.person)
    private RadioButton radioBtn_pesrson;//个人张户
    @ViewInject(R.id.company)
    private RadioButton radioBtn_company;//企业账户
    @ViewInject(R.id.et_account_num)
    private EditText et_account_num;// 银行卡号
    @ViewInject(R.id.et_card_name)
    private EditText et_card_name;// 开卡人姓名
    @ViewInject(R.id.tv_acount_address)
    private TextView tv_acount_address;// 开户地区
    @ViewInject(R.id.et_child_bank)
    private EditText et_child_bank;// 开户支行
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_second)
    private TextView tv_second;
    @ViewInject(R.id.et_code)
    private EditText et_code;// 验证码
    @ViewInject(R.id.btn_commit)
    private Button btn_commit;
    @ViewInject(R.id.rl_get_code)
    private RelativeLayout rl_get_code;
    @ViewInject(R.id.tv_account_one)
    private TextView tv_account_one;
    @ViewInject(R.id.tv_name_one)
    private TextView tv_name_one;

    private ListView mFirstClassifyingListView;
    private String distributorid = "";
    private String code_result = "";//验证后台获取

    private String bankType = "";// 银行类别.id
    private String accountType = "";//账户类型  1 个人账户  2 企业账户
    private String countryPath = "";// 地址节点

    private PopupWindow popupwindow;

    private ListViewDataAdapter<BankEntity> bankEntityListViewDataAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    tv_second.setText("剩余" + msg.arg1 + "秒");
                    if (msg.arg1 == 0) {
                        closeTimer();
                        rl_get_code.setEnabled(true);
                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    rl_get_code.setEnabled(true);
                    tv_second.setText("获取验证码");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_receive);
        ViewUtils.inject(this);
        tv_title.setText("收款账户");
        distributorid = PreferenceHelper.readString(CashReceiveActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign = TGmd5.getMD5(distributorid);
            if (checkNet()) {
                getData(distributorid, sign);
            }
        }
        initViewHolder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public boolean checkForm() {
        String bank_account = et_bank_account.getText().toString().trim();
        String account_num = et_account_num.getText().toString().trim();
        String card_name = et_card_name.getText().toString().trim();
        String acount_address = tv_acount_address.getText().toString().trim();
        String child_bank = et_child_bank.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (bank_account.equals("请选择开户银行")) {
            MyToast.show(CashReceiveActivity.this, "请选择开户银行");
            return false;
        } else if (StringUtils.isEmpty(account_num)) {
            MyToast.show(CashReceiveActivity.this, "请输入银行卡号");
            return false;
        } else if (StringUtils.isEmpty(card_name)) {
            MyToast.show(CashReceiveActivity.this, "请输入开户人姓名");
            return false;
        } else if (StringUtils.isEmpty(code)) {
            MyToast.show(CashReceiveActivity.this, "请输入验证码");
            return false;
        } else if (!code.equals(code_result)) {
            MyToast.show(CashReceiveActivity.this, "请输入正确的验证码");
            return false;
        }
        return true;
    }

    @OnClick({R.id.rl_back, R.id.rl_get_code, R.id.btn_commit, R.id.et_bank_account, R.id.tv_acount_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_get_code:
                String bank_account = et_bank_account.getText().toString().trim();
                String account_num = et_account_num.getText().toString().trim();
                String card_name = et_card_name.getText().toString().trim();
                if (bank_account.equals("请选择开户银行")) {
                    MyToast.show(CashReceiveActivity.this, "请选择开户银行");
                    return;
                } else if (StringUtils.isEmpty(account_num)) {
                    MyToast.show(CashReceiveActivity.this, "请输入银行卡号");
                    return;
                } else if (StringUtils.isEmpty(card_name)) {
                    MyToast.show(CashReceiveActivity.this, "请输入开户人姓名");
                    return;
                } else {
                    if (checkNet()) {
                        rl_get_code.setEnabled(false);
                        String sign = TGmd5.getMD5(distributorid);
                        getCode(distributorid, sign);
                        startTimer();
                    }
                }
                break;
            case R.id.btn_commit:
                String account_num_ = et_account_num.getText().toString().trim();
                String card_name_ = et_card_name.getText().toString().trim();
                String sign_ = TGmd5.getMD5(distributorid + bankType + "0" + account_num_ + card_name_ + "" + "");
                if (checkForm()) {
                    commitBankInfo(distributorid, bankType, "0", account_num_, card_name_, "", "", sign_);
                }
                break;
            case R.id.et_bank_account:
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView();
                    popupwindow.showAsDropDown(view, 0, 5);
                }
                break;
            case R.id.tv_acount_address:
                openActivity(SelectProvinceActivity.class);
                break;

        }
    }


    public void initViewHolder() {
        bankEntityListViewDataAdapter = new ListViewDataAdapter<BankEntity>();
        bankEntityListViewDataAdapter.setViewHolderClass(this, ClassifyingOneListviewHolder.class);
        ClassifyingOneListviewHolder.setOnFirstClassifyingItemClickListener(this);

    }

    // =======================倒计时模块================================//

    private int mTimerId = 120;

    private TimerTask timerTask;

    private Timer timer;

    /**
     * 开始倒计时
     */
    private void startTimer() {

        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = Constants.EXECUTE_LOADING;
                    msg.arg1 = (int) (--mTimerId);
                    handler.sendMessage(msg);
                }
            };
            timer = new Timer();
            // schedule(TimerTask task, long delay, long period)
            // 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
            // task - 所要安排的任务。
            // delay - 执行任务前的延迟时间，单位是毫秒。
            // period - 执行各后续任务之间的时间间隔，单位是毫秒。
            timer.schedule(timerTask, 100, 1000);
        }
    }

    /**
     * 结束计时
     */
    private void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        mTimerId = 120;
        handler.sendEmptyMessage(Constants.EXECUTE_FINISH);
    }

    private void initmPopupWindowView() {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popwindow_one, null, false);
//        int width = et_bank_account.getWidth();
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupwindow.setFocusable(true);
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }
                return false;
            }
        });
        mFirstClassifyingListView = (ListView) customView.findViewById(R.id.lv_first_classifying);
        mFirstClassifyingListView.setAdapter(bankEntityListViewDataAdapter);

        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.ll_list);
        ImageView bg = (ImageView) customView.findViewById(R.id.bg);
        bg.setVisibility(View.VISIBLE);

        Animation animation1 = AnimationUtils.loadAnimation(CashReceiveActivity.this, R.anim.translate_in);
        Animation animation2 = AnimationUtils.loadAnimation(CashReceiveActivity.this, R.anim.alpha_in);
        linearLayout.startAnimation(animation1);

        bg.startAnimation(animation2);

    }

    /**
     * 获取验证码
     */
    public void getCode(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().doSendMessage(CashReceiveActivity.this, maps, new OnCodeRequestListener());
    }

    private class OnCodeRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    MyToast.show(CashReceiveActivity.this, "发送失败！");
                    tv_second.setText("获取验证码");
                    tv_second.setClickable(true);
                    closeTimer();
                } else if (status.equals("1")) {
                    MyToast.show(CashReceiveActivity.this, "发送成功！");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        code_result = array.get(0).toString();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取账户信息
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getBankInfo(CashReceiveActivity.this, maps, new OnAccountRequestListener());
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

                    /**************获取银行列表*************/
                    String bank_list = array.get(0).toString();
                    JSONArray array_bank = new JSONArray(bank_list);
                    bankEntityListViewDataAdapter.removeAll();
                    BankEntity bankEntity1 = new BankEntity("0", "请选择开户银行");
                    bankEntityListViewDataAdapter.append(bankEntity1);
                    if (array_bank != null && array_bank.length() > 0) {
                        for (int i = 0; i < array_bank.length(); i++) {
                            JSONObject jsonObject_bank = array_bank.getJSONObject(i);
                            String id = jsonObject_bank.getString("ID");
                            String name = jsonObject_bank.getString("String1");
                            BankEntity bankEntity = new BankEntity(id, name);
                            bankEntityListViewDataAdapter.append(bankEntity);
                        }
                    }
                    /**************获取账户信息*************/
                    String info_ = array.get(1).toString();
                    JSONObject jsonObject_info = new JSONObject(info_);
                    accountType = jsonObject_info.getString("Account");
                    String bank_name = jsonObject_info.getString("BankTypeName");
                    String cardNum = jsonObject_info.getString("CardNO");
                    String cardName = jsonObject_info.getString("CardName");
                    String child_bank = jsonObject_info.getString("Branch");
                    bankType = jsonObject_info.getString("BankType");
                    String phone = array.get(2).toString();
                    String address = array.get(3).toString();
                    Constants.TOTAL_ADDRESS = array.get(3).toString();
                    if (bank_name.equals("")) {
                        et_bank_account.setText("请选择开户银行");
                        tv_account_one.setText("支付宝账户");
                        tv_name_one.setText("真实姓名");
                    } else {
                        et_bank_account.setText(bank_name);
                        if (bank_name.equals("支付宝")) {
                            tv_account_one.setText("支付宝账户");
                            tv_name_one.setText("真实姓名");
                        } else {
                            tv_account_one.setText("卡号/账户");
                            tv_name_one.setText("开户姓名");
                        }
                    }
                    // 1 个人账户  2 企业账户
                    if (accountType.equals("2") || accountType.equals("0")) {
                        radioBtn_company.setChecked(true);
                    } else if (accountType.equals("1")) {
                        radioBtn_pesrson.setChecked(true);
                    }
                    et_account_num.setText(cardNum);
                    et_card_name.setText(cardName);
                    tv_acount_address.setText(address);
                    et_child_bank.setText(child_bank);
                    tv_phone.setText(phone);

                } else if (status.equals("0")) {
                    MyToast.show(CashReceiveActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
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
     * 提交信息
     */
    public void commitBankInfo(String distributorid, String bankType, String accountType, String cardNO, String cardName, String branch, String path, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("bankType", bankType);
        maps.put("accountType", "0");
        maps.put("cardNO", cardNO);
        maps.put("cardName", cardName);
        maps.put("branch", "");
        maps.put("path", "");
        maps.put("sign", sign);

        RequestTask.getInstance().commitBankInfo(CashReceiveActivity.this, maps, new OnCommitRequestListener());

    }

    private class OnCommitRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(CashReceiveActivity.this, "修改成功");
                    finish();
                } else if (status.equals("0")) {
                    MyToast.show(CashReceiveActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 点击事件回调
     */
    @Override
    public void onClassifyClick(BankEntity itemData) {
        if (itemData.getName().equals("支付宝")) {
            tv_account_one.setText("支付宝账户");
            tv_name_one.setText("真实姓名");
        } else {
            tv_account_one.setText("卡号/账户");
            tv_name_one.setText("开户姓名");
        }
        et_bank_account.setText(itemData.getName());
        bankType = itemData.getId();
        popupwindow.dismiss();
    }
}

