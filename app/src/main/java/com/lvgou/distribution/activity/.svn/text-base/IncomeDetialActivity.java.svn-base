package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/13
 * 收支明细详情
 */
public class IncomeDetialActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_totle)
    private TextView tv_total;
    @ViewInject(R.id.tv_yongjin)
    private TextView tv_comssion;
    @ViewInject(R.id.tv_status)
    private TextView tv_status;
    @ViewInject(R.id.tv_order_num)
    private TextView tv_order_num;
    @ViewInject(R.id.tv_create_time)
    private TextView tv_create_time;
    @ViewInject(R.id.tv_tixian_time)
    private TextView tv_tixian_time;
    @ViewInject(R.id.rl_tixian)
    private RelativeLayout rl_tixian;
    @ViewInject(R.id.btn_seek)
    private Button btn_seek;

    private String distributorid;
    private String incomeid;
    private String sellerID;
    private String order_num;
    private String detial_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detial);
        ViewUtils.inject(this);
        tv_title.setText("收支明细");
        distributorid = PreferenceHelper.readString(IncomeDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        incomeid = getTextFromBundle("incomeid");
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid + incomeid);
            getData(distributorid, incomeid, sign);
        }
    }

    @OnClick({R.id.rl_back, R.id.btn_seek})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_seek:
                Bundle bundle = new Bundle();
                bundle.putString("detial_list", detial_data);
                openActivity(OrderCentralDetialActivity.class, bundle);
                break;
        }
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param incomeid
     * @param sign
     */
    public void getData(String distributorid, String incomeid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("incomeid", incomeid);
        maps.put("sign", sign);
        RequestTask.getInstance().getIncomeDetial(IncomeDetialActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    String item = array.get(0).toString();
                    String status_ = array.get(1).toString();
                    JSONObject object = new JSONObject(item);
                    order_num = object.getString("OrderNO");
                    String price_total = object.getString("Price");
                    String createTime = object.getString("CreateTime");
                    sellerID = object.getString("SellerID");
                    String[] str = createTime.split("T");
                    String[] str_ = str[1].split(":");
                    tv_create_time.setText(str[0] + " " + str_[0] + ":" + str_[1]);
                    tv_order_num.setText(order_num);
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    tv_total.setText("¥" + decimalFormat.format(Float.parseFloat(price_total)));
                    // 0=可提现，1=申请中，2=已结算，3=锁定中，4=已取消
                    if (status_.equals("0")) {// 旅行社
                        String lxs_price = object.getString("PriceLXS");
                        String lxs_state = object.getString("StateLXS");
                        String out_lxs = object.getString("OutTimeLXS");
                        tv_comssion.setText("¥" + decimalFormat.format(Float.parseFloat(lxs_price)));
                        if (lxs_state.equals("2")) {
                            String[] str1 = out_lxs.split("T");
                            String[] str_1 = str1[1].split(":");
                            rl_tixian.setVisibility(View.VISIBLE);
                            tv_status.setText("已结算");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                            tv_tixian_time.setText(str1[0] + " " + str_1[0] + ":" + str_1[1]);
                        } else if (lxs_state.equals("0")) {
                            tv_status.setText("可提现");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_blue_cose));
                        } else if (lxs_state.equals("1")) {
                            tv_status.setText("申请中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (lxs_state.equals("3")) {
                            tv_status.setText("锁定中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (lxs_state.equals("4")) {
                            tv_status.setText("已取消");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                        }
                    } else {
                        String dy_price_ = object.getString("PriceDY");
                        String dy_state_ = object.getString("StateDY");
                        String out_dy = object.getString("OutTimeDY");
                        tv_comssion.setText("¥" + decimalFormat.format(Float.parseFloat(dy_price_)));
                        if (dy_state_.equals("2")) {
                            String[] str1 = out_dy.split("T");
                            String[] str_1 = str1[1].split(":");
                            rl_tixian.setVisibility(View.VISIBLE);
                            tv_status.setText("已结算");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                            tv_tixian_time.setText(str1[0] + " " + str_1[0] + ":" + str_1[1]);
                        } else if (dy_state_.equals("0")) {
                            tv_status.setText("可提现");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_blue_cose));
                        } else if (dy_state_.equals("1")) {
                            tv_status.setText("申请中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (dy_state_.equals("3")) {
                            tv_status.setText("锁定中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (dy_state_.equals("4")) {
                            tv_status.setText("已取消");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                        }
                    }
                    // 获取详情数据
                    String sign_ = TGmd5.getMD5(distributorid + order_num + sellerID);
                    getSeekDetial(distributorid, order_num, sellerID, sign_);
                } else if (status.equals("0")) {
                    MyToast.show(IncomeDetialActivity.this, jsonObject.getString("message"));
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

    public void getSeekDetial(String distributorid, String orderno, String sellerid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("orderno", orderno);
        maps.put("sellerid", sellerid);
        maps.put("sign", sign);
        RequestTask.getInstance().seekIncomeDetial(IncomeDetialActivity.this, maps, new OnSeekRequestListener());

    }

    private class OnSeekRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    detial_data = array.get(0).toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
