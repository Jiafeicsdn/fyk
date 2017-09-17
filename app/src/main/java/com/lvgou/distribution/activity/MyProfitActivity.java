package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyChartView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/21 0021.
 * 我的收益
 */
public class MyProfitActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_table)
    private LinearLayout ll_table;
    @ViewInject(R.id.tv_withdrawals_money)
    private TextView tv_withdrawals_money;//底部提现金额
    @ViewInject(R.id.rl_withdrawals)
    private RelativeLayout rl_withdrawals;// 提现按钮
    @ViewInject(R.id.tv_today_profit)
    private TextView tv_today_profit;
    @ViewInject(R.id.tv_cumulative_profit)
    private TextView tv_cumulative_profit;
    @ViewInject(R.id.tv_total_profit_yet)
    private TextView tv_total_profit_yet;
    @ViewInject(R.id.tv_seek_order_record)
    private TextView tv_seek_order_record;
    @ViewInject(R.id.tv_tixian_total_profit)
    private TextView tv_tixian_total_profit;
    @ViewInject(R.id.img_indicator)
    private ImageView img_indicator;
    @ViewInject(R.id.rl_child_profit)
    private RelativeLayout rl_child_profit;
    @ViewInject(R.id.tv_notcan_profit)
    private TextView tv_notcan_profit;
    @ViewInject(R.id.tv_can_profit)
    private TextView tv_can_profit;
    @ViewInject(R.id.tv_seek_tuiguang_record)
    private TextView tv_seek_tuiguang_record;
    @ViewInject(R.id.tv_make_total_profit)
    private TextView tv_make_total_profit;
    @ViewInject(R.id.rl_open)
    private RelativeLayout rl_open;
    @ViewInject(R.id.ll_bottom)
    private LinearLayout ll_bottom;
    @ViewInject(R.id.tv_ketixian)
    private TextView tv_ketixian;
    @ViewInject(R.id.tv_withdrawals_no_money)
    private TextView tv_withdrawals_no_money;
    private String str_8 = "";// 可提现金额
    private String index = "";


    private String is_open = "1";// 1  展开  2：收起

    private String distributorid = "";
    private HashMap<String, Double> map;
    private double max = 0.00;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x1111:
                    try {
                        initChartView(max);
                    } catch (Exception c) {

                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profit);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
        tv_title.setText("我的收益");

    }

    @OnClick({R.id.rl_back, R.id.rl_withdrawals, R.id.rl_open, R.id.tv_seek_order_record, R.id.tv_seek_tuiguang_record})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.rl_withdrawals:
                if (!str_8.equals("0")) {
                    openActivity(WithdrawsActivity.class);
                } else {
                    return;
                }
                break;
            case R.id.rl_open:
                if (is_open.equals("1")) {
                    is_open = "2";
                    img_indicator.setBackgroundResource(R.mipmap.bg_profit_up);
                    rl_child_profit.setVisibility(View.VISIBLE);
                } else if (is_open.equals("2")) {
                    is_open = "1";
                    img_indicator.setBackgroundResource(R.mipmap.bg_profit_down);
                    rl_child_profit.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_seek_tuiguang_record:
                bundle.putString("selection_postion", "1");
                openActivity(PayDetailActivity.class, bundle);
                break;
            case R.id.tv_seek_order_record:
                bundle.putString("selection_postion", "0");
                openActivity(PayDetailActivity.class, bundle);
                break;

        }
    }

    public void initChartView(double max_values) {
        MyChartView chart_view = new MyChartView(MyProfitActivity.this);
        if (max_values > 5) {
            int max = (int) max_values;
            int i = (int) max_values / 5;
            int data = (i + 1) * 5;
            int item = data / 5;
            chart_view.SetTuView(map, data, i, "", "", true);
        } else {
            chart_view.SetTuView(map, 5, 1, "", "", true);
        }
        chart_view.setMap(map);
        chart_view.setMargint(20);
        chart_view.setMarginb(50);
        chart_view.setMstyle(MyChartView.Mstyle.Line);
        ll_table.addView(chart_view);
    }

    /**
     * 获取收益
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getMyProfit(MyProfitActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject json_ = new JSONObject(response);
                String status = json_.getString("status");
                if (status.equals("1")) {
                    String result = json_.getString("result");
                    JSONArray array = new JSONArray(result);
                    String str_1 = array.get(0).toString();
                    String str_2 = array.get(1).toString();
                    String str_3 = array.get(2).toString();
                    String str_4 = array.get(3).toString();
                    String str_5 = array.get(4).toString();
                    String str_6 = array.get(5).toString();
                    String str_7 = array.get(6).toString();
                    str_8 = array.get(7).toString();
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    tv_today_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_1)));
                    tv_cumulative_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_2)));
                    tv_total_profit_yet.setText("+" + decimalFormat.format(Float.parseFloat(str_3)));
                    tv_tixian_total_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_4)));
                    tv_notcan_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_5)));
                    tv_can_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_6)));
                    tv_make_total_profit.setText("+" + decimalFormat.format(Float.parseFloat(str_7)));

                    if (Float.parseFloat(str_8) > 0) {
                        tv_withdrawals_money.setText("¥" + decimalFormat.format(Float.parseFloat(str_8)));
                        tv_withdrawals_money.setTextColor(Color.parseColor("#fc4d30"));
                        ll_bottom.setBackgroundResource(R.mipmap.bg_profit_bottom);
                        tv_ketixian.setVisibility(View.VISIBLE);
                        tv_withdrawals_money.setVisibility(View.VISIBLE);
                        tv_withdrawals_no_money.setVisibility(View.GONE);
                    } else {
                        tv_withdrawals_money.setVisibility(View.GONE);
                        tv_withdrawals_money.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
                        tv_ketixian.setVisibility(View.GONE);
                        tv_withdrawals_no_money.setVisibility(View.VISIBLE);
                        ll_bottom.setBackgroundResource(R.mipmap.bg_profit_bottom_gray);
                    }

                    /***********日期key值*********/
                    String str_9 = array.get(8).toString();
                    JSONArray array_key = new JSONArray(str_9);
                    /***********日期key值*********/
                    String str_10 = array.get(9).toString();
                    JSONArray array_value = new JSONArray(str_10);
                    max = 0.00;
                    map = new HashMap<String, Double>();
                    for (int i = 0; i < 7; i++) {
                        map.put(array_key.get(i).toString(), Double.parseDouble(array_value.get(i).toString()));
                        if (max < Double.parseDouble(array_value.get(i).toString())) {
                            max = Double.parseDouble(array_value.get(i).toString());
                        }
                    }
                    Message message = new Message();
                    message.what = 0x1111;
                    handler.sendMessage(message);
                } else if (status.equals("0")) {
                    MyToast.show(MyProfitActivity.this, json_.getString("message"));
                    if (json_.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        distributorid = PreferenceHelper.readString(MyProfitActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid);
                getData(distributorid, sign);
            }
        }

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
