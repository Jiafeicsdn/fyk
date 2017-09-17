package com.lvgou.distribution.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ExchageRecordActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.AnimationUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
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
 * Created by Snow on 2016/3/29 0029.
 * 团币兑换  老版  可删
 */
public class FragmentExchange extends Fragment implements View.OnClickListener {

    private TextView tv_exchange_record;
    private TextView tv_exchange;
    private TextView tv_exchange_1;
    private TextView tv_exchange_2;
    private TextView tv_exchange_3;
    private TextView tv_exchange_4;
    private TextView tv_exchange_5;
    private TextView tv_exchange_6;
    private TextView tv_exchange_7;
    private TextView tv_exchange_8;
    private TextView tv_tuanbi;

    private RelativeLayout rl_five;
    private RelativeLayout rl_ten;
    private RelativeLayout rl_twenty;
    private RelativeLayout rl_fifty;
    private RelativeLayout rl_one_hundred;
    private RelativeLayout rl_hundred_fifty;
    private RelativeLayout rl_two_hundred;
    private RelativeLayout rl_five_hundred;
    private RelativeLayout rl_thousand;

    private RelativeLayout rl_dialog;
    private LinearLayout ll_dialog_root;
    private ImageView img_delete;
    private EditText et_phone;
    private TextView tv_moeny;
    private TextView tv_test;
    private EditText et_code;
    private Button sure;

    private String isChange = "1";
    private Dialog dialog;


    private String distributorid = "";
    private String code_result = "";

    private String type = "1";
    private String type1 = "1";
    private String type2 = "1";
    private String type3 = "1";
    private String type4 = "1";
    private String type5 = "1";
    private String type6 = "1";
    private String type7 = "1";
    private String type8 = "1";
    private String price;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    try {
                        tv_test.setText("剩余" + msg.arg1 + "秒");
                        tv_test.setTextColor(getActivity().getResources().getColor(R.color.bg_baoming_yellow));
                        if (msg.arg1 == 0) {
                            closeTimer();
                            tv_test.setClickable(true);
                            sure.setEnabled(true);
                        }
                    } catch (Exception c) {

                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    tv_test.setText("获取验证码");
                    tv_test.setClickable(true);
                    sure.setEnabled(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        initView(view);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FragmentExchange");
        String tuanbi = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        tv_tuanbi.setText(tuanbi);
        initButton(tuanbi);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FragmentExchange");
    }

    public void initButton(String tuanbi) {
        int tuanbi_ = Integer.parseInt(tuanbi);
        if (tuanbi_ > 50) {
            type = "1";
            tv_exchange.setBackgroundResource(R.drawable.bg_task_orange_shape);
            tv_exchange.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
            rl_five.setBackgroundResource(R.mipmap.yellow_five);
        } else {
            type = "2";
            tv_exchange.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
            rl_five.setBackgroundResource(R.mipmap.gray_five);
        }
        if (tuanbi_ > 100) {
            type1 = "1";
            rl_ten.setBackgroundResource(R.mipmap.yellow_ten);
            tv_exchange_1.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_1.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type1 = "2";
            rl_ten.setBackgroundResource(R.mipmap.gray_ten);
            tv_exchange_1.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_1.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 200) {
            type2 = "1";
            rl_twenty.setBackgroundResource(R.mipmap.yellow_twenty);
            tv_exchange_2.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_2.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type2 = "2";
            rl_twenty.setBackgroundResource(R.mipmap.gray_twenty);
            tv_exchange_2.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_2.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 500) {
            type3 = "1";
            rl_fifty.setBackgroundResource(R.mipmap.yellow_fifty);
            tv_exchange_3.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_3.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type3 = "2";
            rl_fifty.setBackgroundResource(R.mipmap.gray_fifty);
            tv_exchange_3.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_3.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 1000) {
            type4 = "1";
            rl_one_hundred.setBackgroundResource(R.mipmap.yellow_one_hundred);
            tv_exchange_4.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_4.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type4 = "2";
            rl_one_hundred.setBackgroundResource(R.mipmap.gray_one_hundred);
            tv_exchange_4.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_4.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 1500) {
            type5 = "1";
            rl_hundred_fifty.setBackgroundResource(R.mipmap.yellow_hundred_fifty);
            tv_exchange_5.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_5.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type5 = "2";
            rl_hundred_fifty.setBackgroundResource(R.mipmap.gray_hundred_fifty);
            tv_exchange_5.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_5.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 2000) {
            type6 = "1";
            rl_two_hundred.setBackgroundResource(R.mipmap.yellow_two_hundred);
            tv_exchange_6.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_6.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type6 = "2";
            rl_two_hundred.setBackgroundResource(R.mipmap.gray_two_hundred);
            tv_exchange_6.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_6.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 5000) {
            type7 = "1";
            rl_five_hundred.setBackgroundResource(R.mipmap.yellow_five_hundred);
            tv_exchange_7.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_7.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type7 = "2";
            rl_five_hundred.setBackgroundResource(R.mipmap.gray_five_hundred);
            tv_exchange_7.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_7.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
        if (tuanbi_ > 10000) {
            type8 = "1";
            rl_thousand.setBackgroundResource(R.mipmap.yellow_thousand);
            tv_exchange_8.setBackgroundResource((R.drawable.bg_task_orange_shape));
            tv_exchange_8.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        } else {
            type8 = "2";
            rl_thousand.setBackgroundResource(R.mipmap.gray_thousand);
            tv_exchange_8.setBackgroundResource((R.drawable.bg_grey_shape));
            tv_exchange_8.setTextColor(getActivity().getResources().getColor(R.color.bg_white));
        }
    }

    public void initView(final View view) {
        tv_exchange_record = (TextView) view.findViewById(R.id.tv_exchange_record);
        tv_exchange = (TextView) view.findViewById(R.id.tv_exchange);
        tv_exchange_1 = (TextView) view.findViewById(R.id.tv_exchange_1);
        tv_exchange_2 = (TextView) view.findViewById(R.id.tv_exchange_2);
        tv_exchange_3 = (TextView) view.findViewById(R.id.tv_exchange_3);
        tv_exchange_4 = (TextView) view.findViewById(R.id.tv_exchange_5);
        tv_exchange_5 = (TextView) view.findViewById(R.id.tv_exchange_6);
        tv_exchange_6 = (TextView) view.findViewById(R.id.tv_exchange_7);
        tv_exchange_7 = (TextView) view.findViewById(R.id.tv_exchange_8);
        tv_exchange_8 = (TextView) view.findViewById(R.id.tv_exchange_9);
        rl_five = (RelativeLayout) view.findViewById(R.id.rl_five);
        rl_ten = (RelativeLayout) view.findViewById(R.id.rl_ten);
        rl_twenty = (RelativeLayout) view.findViewById(R.id.rl_twenty);
        rl_fifty = (RelativeLayout) view.findViewById(R.id.rl_fifty);
        rl_one_hundred = (RelativeLayout) view.findViewById(R.id.rl_one_hundred);
        rl_hundred_fifty = (RelativeLayout) view.findViewById(R.id.rl_hundred_fifty);
        rl_two_hundred = (RelativeLayout) view.findViewById(R.id.rl_two_hundred);
        rl_five_hundred = (RelativeLayout) view.findViewById(R.id.rl_five_hundred);
        rl_thousand = (RelativeLayout) view.findViewById(R.id.rl_thousand);
        tv_tuanbi = (TextView) view.findViewById(R.id.tv_tuanbi);
        rl_dialog = (RelativeLayout) view.findViewById(R.id.rl_dialog);
        img_delete = (ImageView) view.findViewById(R.id.img_dismiss);
        ll_dialog_root = (LinearLayout) view.findViewById(R.id.rl_dialog_root);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        tv_moeny = (TextView) view.findViewById(R.id.tv_money_red);
        tv_test = (TextView) view.findViewById(R.id.tv_test);
        et_code = (EditText) view.findViewById(R.id.et_code);
        sure = (Button) view.findViewById(R.id.btn_exchange);

        tv_exchange_record.setOnClickListener(this);
        tv_exchange.setOnClickListener(this);
        tv_exchange_1.setOnClickListener(this);
        tv_exchange_2.setOnClickListener(this);
        tv_exchange_3.setOnClickListener(this);
        tv_exchange_4.setOnClickListener(this);
        tv_exchange_5.setOnClickListener(this);
        tv_exchange_6.setOnClickListener(this);
        tv_exchange_7.setOnClickListener(this);
        tv_exchange_8.setOnClickListener(this);
        img_delete.setOnClickListener(this);
        sure.setOnClickListener(this);
        tv_test.setOnClickListener(this);

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isChange = "2";
                if (StringUtils.isPhone(et_phone.getText())) {
                    hideSoftInput(view);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exchange_record:
                Intent intent = new Intent(getActivity(), ExchageRecordActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_exchange:
                if (type.equals("1")) {
                    price = "5";
                    tv_moeny.setText("5");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_1:
                if (type1.equals("1")) {
                    price = "10";
                    tv_moeny.setText("10");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_2:
                if (type2.equals("1")) {
                    price = "20";
                    tv_moeny.setText("20");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_3:
                if (type3.equals("1")) {
                    price = "50";
                    tv_moeny.setText("50");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_5:
                if (type4.equals("1")) {
                    price = "100";
                    tv_moeny.setText("100");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_6:
                if (type5.equals("1")) {
                    price = "150";
                    tv_moeny.setText("150");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_7:
                if (type6.equals("1")) {
                    price = "200";
                    tv_moeny.setText("200");
                    et_phone.setText("");
                    et_code.setText("");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_8:
                if (type7.equals("1")) {
                    price = "500";
                    tv_moeny.setText("500");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.tv_exchange_9:
                if (type8.equals("1")) {
                    price = "1000";
                    tv_moeny.setText("1000");
                    closeTimer();
                    openDialog();
                }
                break;
            case R.id.img_dismiss:
                closeDialog();
                //隐藏软键盘
                if (isChange.equals("2")) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                }
                et_phone.setText("");
                et_code.setText("");
                break;
            case R.id.btn_exchange:
                String price = tv_moeny.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                if (StringUtils.isEmpty(phone)) {
                    MyToast.show(getActivity(), "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(getActivity(), "请输入合法手机号");
                    return;
                } else if (StringUtils.isEmpty(code)) {
                    MyToast.show(getActivity(), "请输入验证码");
                    return;
                } else if (!code.equals(code_result)) {
                    MyToast.show(getActivity(), "验证码不正确");
                    return;
                } else {
                    String sign = TGmd5.getMD5(distributorid + price + phone);
                    doExchangeRed(distributorid, price, phone, sign);
                }

                break;
            case R.id.tv_test:
                String price_ = tv_moeny.getText().toString().trim();
                String phone_ = et_phone.getText().toString().trim();
                if (StringUtils.isEmpty(phone_)) {
                    MyToast.show(getActivity(), "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone_)) {
                    MyToast.show(getActivity(), "请输入合法手机号");
                    return;
                } else {
                    if (checkNet(getActivity())) {
                        String sign_ = TGmd5.getMD5(distributorid + price_ + phone_);
                        getCode(distributorid, price_, phone_, sign_);
                        tv_test.setClickable(false);
                        sure.setEnabled(false);
                        startTimer();
                    }
                }
                break;
        }
    }


    /**
     * 发送验证码
     *
     * @param distributorid
     * @param price
     * @param mobile
     * @param sign
     */
    public void getCode(String distributorid, String price, String mobile, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("price", price);
        maps.put("mobile", mobile);
        maps.put("sign", sign);
        RequestTask.getInstance().getCodeExchange(getActivity(), maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "发送成功");
                    String result = jsonObject.getString("result");
                    sure.setEnabled(true);
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        code_result = array.get(0).toString();
                    }
                } else if (status.equals("0")) {
                    closeTimer();
                    MyToast.show(getActivity(), jsonObject.getString("message"));
                    tv_test.setText("获取验证码");
                    tv_test.setClickable(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 兑换红包
     *
     * @param distributorid
     * @param price
     * @param mobile
     * @param sign
     */
    public void doExchangeRed(String distributorid, String price, String mobile, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("price", price);
        maps.put("mobile", mobile);
        maps.put("sign", sign);
        RequestTask.getInstance().doExchangeRed(getActivity(), maps, new OnExchangeRedRequestListener());
    }

    private class OnExchangeRedRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "兑换成功");
                    closeTimer();
                    et_phone.setText("");
                    et_code.setText("");
                    tv_test.setText("获取验证码");
                    sure.setEnabled(true);
                    tv_test.setClickable(true);
                    tv_test.setBackgroundResource(R.color.bg_white);
                    tv_test.setTextColor(getActivity().getResources().getColor(R.color.bg_baoming_yellow));
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    tv_tuanbi.setText(array.get(0).toString());
                    PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, array.get(0).toString());
                    closeDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

    // 弹出拍照对话框
    private void openDialog() {
        performDialogAnimation(ll_dialog_root,
                rl_dialog, true);
    }

    // 关闭拍照对话框
    private void closeDialog() {
        performDialogAnimation(ll_dialog_root,
                rl_dialog, false);

    }

    /**
     * 执行对话框动画
     *
     * @param rootView    背景View
     * @param contentView 内容View
     * @param isShow      true 执行显示动画 false 执行隐藏动画
     */
    protected void performDialogAnimation(final LinearLayout rootView,
                                          final RelativeLayout contentView, final Boolean isShow) {
        float[] floats = null;
        if (isShow) {
            rootView.setVisibility(View.VISIBLE);
            floats = new float[]{0.0f, 1.0f};
        } else {
            floats = new float[]{1.0f, 0.0f};
        }
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rootView, "alpha", floats[0], floats[1]);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow) {
                    rootView.setVisibility(View.VISIBLE);
                } else {
                    rootView.setVisibility(View.GONE);
                }
            }
        });
        if (isShow) {
            AnimationUtils.expandingAnimation(contentView);
        } else {
            AnimationUtils.collapsingAnimation(contentView);
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public void hideSoftInput(View view) {
        if (getActivity() == null || view == null) {
            return;
        }
        int times = 0;
        boolean isClosed = false;
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        while (!isClosed && times <= 3) {
            times++;
            isClosed = manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
