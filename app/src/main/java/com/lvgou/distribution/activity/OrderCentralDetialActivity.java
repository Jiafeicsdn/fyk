package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.OrderCentralChildEntity;
import com.lvgou.distribution.view.MyListView;
import com.lvgou.distribution.viewholder.OrderCentralDetialViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Snow on 2016/3/14 0014.
 * 订单详情
 */
public class OrderCentralDetialActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_order_time_one)
    private TextView tv_order_time_one;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;//创建时间
    @ViewInject(R.id.tv_name)
    private TextView tv_name;// 姓名
    @ViewInject(R.id.tv_order_num)
    private TextView tv_order_num;// 订单号
    @ViewInject(R.id.tv_status)
    private TextView tv_status;// 订单状态
    @ViewInject(R.id.tv_goods_name)
    private TextView tv_goods_name;// 店铺名
    @ViewInject(R.id.tv_goods_num)
    private TextView tv_goods_num;//商品数量
    @ViewInject(R.id.lv_list)
    private MyListView lv_list;// 商品列表
    @ViewInject(R.id.tv_message)
    private TextView tv_message;// 买家留言s
    @ViewInject(R.id.tv_order_total)
    private TextView tv_order_total;// 订单合计
    @ViewInject(R.id.tv_order_pay)
    private TextView tv_order_pay;//实际支付
    @ViewInject(R.id.tv_sale_name)
    private TextView tv_sale_name;
    @ViewInject(R.id.tv_sale_phone)
    private TextView tv_sale_phone;
    @ViewInject(R.id.tv_adress)
    private TextView tv_adress;
    @ViewInject(R.id.tv_ems)
    private TextView tv_ems;
    @ViewInject(R.id.tv_cost_fact)
    private TextView tv_cost_fact;
    @ViewInject(R.id.tv_youjin)
    private TextView tv_comm;
    @ViewInject(R.id.tv_create_time)
    private TextView tv_create_time;
    @ViewInject(R.id.tv_pay_time)
    private TextView tv_pay_time;
    @ViewInject(R.id.tv_send_time)
    private TextView tv_send_time;
    @ViewInject(R.id.tv_sign_time)
    private TextView tv_sign_time;
    @ViewInject(R.id.tv_close_time)
    private TextView tv_close_time;
    @ViewInject(R.id.tv_goods_status)
    private TextView tv_goods_status;// 物流状态
    @ViewInject(R.id.tv_seek_goods)
    private TextView tv_seek_goods;// 查看物流  只有已签收 状态才显示
    @ViewInject(R.id.tv_shop_total)
    private TextView tv_shop_total;
    @ViewInject(R.id.tv_post_fee)
    private TextView tv_post_fee;
    @ViewInject(R.id.tv_0002)
    private TextView tv_0002;
    @ViewInject(R.id.tv_conpany)
    private TextView tv_conpany;
    @ViewInject(R.id.tv_0003)
    private TextView tv_0003;
    @ViewInject(R.id.tv_sms_order)
    private TextView tv_sms_order;
    @ViewInject(R.id.tv_0004)
    private TextView tv_0004;
    @ViewInject(R.id.tv_sms_time)
    private TextView tv_sms_time;
    @ViewInject(R.id.rl_seek_goods)
    private RelativeLayout rl_seek_goods;
    private String detial; //订单详情
    private String LogisticsNO = "";

    private ListViewDataAdapter<OrderCentralChildEntity> orderCentralEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_centra_detial);
        ViewUtils.inject(this);
        detial = getTextFromBundle("detial_list");
        tv_title.setText("订单中心");
        initViewHolder();
        getData(detial);

    }

    @OnClick({R.id.rl_back, R.id.rl_seek_goods})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_seek_goods:
                Bundle bundle = new Bundle();
                bundle.putString("LogisticsNO", LogisticsNO);
                openActivity(SeekGoodsActivity.class, bundle);
                break;
        }
    }

    // 初始化ViewHolder
    public void initViewHolder() {
        orderCentralEntityListViewDataAdapter = new ListViewDataAdapter<OrderCentralChildEntity>();
        orderCentralEntityListViewDataAdapter.setViewHolderClass(this, OrderCentralDetialViewHolder.class);
        lv_list.setAdapter(orderCentralEntityListViewDataAdapter);
    }

    // 初始化ViewHolder
    public void hideTime() {
        tv_create_time.setVisibility(View.GONE);
        tv_pay_time.setVisibility(View.GONE);
        tv_send_time.setVisibility(View.GONE);
        tv_sign_time.setVisibility(View.GONE);
        tv_close_time.setVisibility(View.GONE);
        tv_0002.setVisibility(View.GONE);
        tv_0003.setVisibility(View.GONE);
        tv_0004.setVisibility(View.GONE);
        tv_conpany.setVisibility(View.GONE);
        tv_sms_order.setVisibility(View.GONE);
        tv_sms_time.setVisibility(View.GONE);
    }

    /**
     * 获取详情信息
     *
     * @param detial
     */
    public void getData(String detial) {
        try {
            JSONArray array = new JSONArray(detial);
            if (array != null && array.length() > 0) {
                JSONObject json_detial = new JSONObject(array.get(0).toString());
                String CreateTime = json_detial.getString("CreateTime");
                String agentRealName = json_detial.getString("AgentRealName");
                if (CreateTime != null && CreateTime.length() > 0) {
                    String[] str = CreateTime.split("T");
                    if (!str[0].equals("1900-01-01")) {
                        tv_time.setText(str[0]);
                    } else {
                        tv_time.setText("");
                        tv_order_time_one.setText("");
                    }
                }
                if (!agentRealName.equals("")) {
                    tv_name.setText(agentRealName);
                } else {
                    tv_name.setText("");
                }
                String ReceiveName = json_detial.getString("ReceiveName");
                String FullAddress = json_detial.getString("FullAddress");
                String phone = json_detial.getString("AddressMobile");
                tv_sale_name.setText(ReceiveName);
                tv_sale_phone.setText(phone);
                tv_adress.setText(FullAddress);

                LogisticsNO = json_detial.getString("LogisticsNO");
                String LogisticsID = json_detial.getString("LogisticsID");
                String LogisticsName = json_detial.getString("LogisticsName");


                String orderNo = json_detial.getString("OrderNO");
                String OrderState = json_detial.getString("OrderState");
                String CreateTime_ = json_detial.getString("CreateTime");
                String ModifiedTime = json_detial.getString("ModifiedTime");
                String PayTime = json_detial.getString("PayTime");
                String ReceiveTime = json_detial.getString("ReceiveTime");
                String SendTime = json_detial.getString("SendTime");


                tv_order_num.setText(orderNo);
                if (OrderState.equals("1")) {
                    tv_status.setText("待付款");
                    hideTime();
                    if (!CreateTime_.split("T")[0].equals("1900-01-01")) {
                        tv_create_time.setText("创建时间 " + getTimeStr(CreateTime_));
                        tv_create_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_create_time.setVisibility(View.GONE);
                    }
                    tv_goods_status.setText("待付款");
                    tv_goods_status.setTextColor(getResources().getColor(R.color.bg_button));
                    rl_seek_goods.setVisibility(View.GONE);
                } else if (OrderState.equals("2")) {
                    tv_status.setText("待发货");
                    hideTime();
                    if (!CreateTime_.split("T")[0].equals("1900-01-01")) {
                        tv_create_time.setText("创建时间 " + getTimeStr(CreateTime_));
                        tv_create_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_create_time.setVisibility(View.GONE);
                    }
                    if (!PayTime.split("T")[0].equals("1900-01-01")) {
                        tv_pay_time.setText("付款时间 " + getTimeStr(PayTime));
                        tv_pay_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_pay_time.setVisibility(View.GONE);
                    }
                    tv_goods_status.setText("待发货");
                    tv_goods_status.setTextColor(getResources().getColor(R.color.bg_button));
                    rl_seek_goods.setVisibility(View.GONE);
                } else if (OrderState.equals("3")) {
                    tv_status.setText("待收货");
                    hideTime();
                    if (!CreateTime_.split("T")[0].equals("1900-01-01")) {
                        tv_create_time.setText("创建时间 " + getTimeStr(CreateTime_));
                        tv_create_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_create_time.setVisibility(View.GONE);
                    }
                    if (!PayTime.split("T")[0].equals("1900-01-01")) {
                        tv_pay_time.setText("付款时间 " + getTimeStr(PayTime));
                        tv_pay_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_pay_time.setVisibility(View.GONE);
                    }
                    if (!SendTime.split("T")[0].equals("1900-01-01")) {
                        tv_send_time.setVisibility(View.VISIBLE);
                        tv_send_time.setText("发货时间 " + getTimeStr(SendTime));
                    } else {
                        tv_send_time.setVisibility(View.GONE);
                    }
                    tv_goods_status.setText("待收货");
                    tv_goods_status.setTextColor(getResources().getColor(R.color.bg_button));
                    if (Integer.parseInt(LogisticsID) > 0) {
                        if (!LogisticsNO.equals("null") && LogisticsNO.length() > 0) {
                            rl_seek_goods.setVisibility(View.VISIBLE);
                            tv_0002.setVisibility(View.VISIBLE);
                            tv_0003.setVisibility(View.VISIBLE);
                            tv_conpany.setVisibility(View.VISIBLE);
                            tv_sms_order.setVisibility(View.VISIBLE);
                            tv_sms_time.setVisibility(View.VISIBLE);
                            tv_conpany.setText(LogisticsName);
                            tv_sms_order.setText(LogisticsNO);
                            if (!SendTime.split("T")[0].equals("1900-01-01")) {
                                tv_0004.setVisibility(View.VISIBLE);
                                tv_sms_time.setText(getTimeStrM(SendTime));
                            } else {
                                tv_0004.setVisibility(View.GONE);
                                tv_sms_time.setVisibility(View.GONE);
                            }
                        }
                    }
                } else if (OrderState.equals("4")) {
                    tv_status.setText("交易成功");
                    hideTime();
                    if (!CreateTime_.split("T")[0].equals("1900-01-01")) {
                        tv_create_time.setText("创建时间 " + getTimeStr(CreateTime_));
                        tv_create_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_create_time.setVisibility(View.GONE);
                    }
                    if (!PayTime.split("T")[0].equals("1900-01-01")) {
                        tv_pay_time.setText("付款时间 " + getTimeStr(PayTime));
                        tv_pay_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_pay_time.setVisibility(View.GONE);
                    }
                    if (!SendTime.split("T")[0].equals("1900-01-01")) {
                        tv_send_time.setVisibility(View.VISIBLE);
                        tv_send_time.setText("发货时间 " + getTimeStr(SendTime));
                    } else {
                        tv_send_time.setVisibility(View.GONE);
                    }
                    if (!ReceiveTime.split("T")[0].equals("1900-01-01")) {
                        tv_sign_time.setVisibility(View.VISIBLE);
                        tv_sign_time.setText("签收时间 " + getTimeStr(ReceiveTime));
                    } else {
                        tv_sign_time.setVisibility(View.GONE);
                    }
                    tv_goods_status.setText("交易成功");
                    tv_goods_status.setTextColor(getResources().getColor(R.color.bg_blue_order));
                    if (Integer.parseInt(LogisticsID) > 0) {
                        if (!LogisticsNO.equals("null") && LogisticsNO.length() > 0) {
                            rl_seek_goods.setVisibility(View.VISIBLE);
                            tv_0002.setVisibility(View.VISIBLE);
                            tv_0003.setVisibility(View.VISIBLE);
                            tv_conpany.setVisibility(View.VISIBLE);
                            tv_sms_order.setVisibility(View.VISIBLE);
                            tv_sms_time.setVisibility(View.VISIBLE);
                            tv_conpany.setText(LogisticsName);
                            tv_sms_order.setText(LogisticsNO);
                            if (!SendTime.split("T")[0].equals("1900-01-01")) {
                                tv_0004.setVisibility(View.VISIBLE);
                                tv_sms_time.setText(getTimeStrM(SendTime));
                            } else {
                                tv_0004.setVisibility(View.GONE);
                                tv_sms_time.setVisibility(View.GONE);
                            }
                        }
                    }
                } else if (OrderState.equals("5")) {
                    tv_status.setText("交易关闭");
                    hideTime();
                    if (!CreateTime_.split("T")[0].equals("1900-01-01")) {
                        tv_create_time.setText("创建时间 " + getTimeStr(CreateTime_));
                        tv_create_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_create_time.setVisibility(View.GONE);
                    }
                    if (!PayTime.split("T")[0].equals("1900-01-01")) {
                        tv_pay_time.setText("付款时间 " + getTimeStr(PayTime));
                        tv_pay_time.setVisibility(View.VISIBLE);
                    } else {
                        tv_pay_time.setVisibility(View.GONE);
                    }
                    if (!SendTime.split("T")[0].equals("1900-01-01")) {
                        tv_send_time.setVisibility(View.VISIBLE);
                        tv_send_time.setText("发货时间 " + getTimeStr(SendTime));
                    } else {
                        tv_send_time.setVisibility(View.GONE);
                    }
                    if (!ModifiedTime.split("T")[0].equals("1900-01-01")) {
                        tv_close_time.setVisibility(View.VISIBLE);
                        tv_close_time.setText("关闭时间 " + getTimeStr(ModifiedTime));
                    } else {
                        tv_close_time.setVisibility(View.GONE);
                    }

                    tv_goods_status.setText("交易关闭");
                    tv_goods_status.setTextColor(getResources().getColor(R.color.bg_button));
                    rl_seek_goods.setVisibility(View.GONE);
                }
                /************** 订单列表 ***************/
                int num = 0;
                int commission = 0;//佣金
                int price_total = 0;// 合计
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json_item = array.getJSONObject(i);
                    String id = json_item.getString("ID");
                    String picUrl = json_item.getString("PicUrl");
                    String productName = json_item.getString("ProductName");
                    String sKUName = json_item.getString("SKUName");
                    String price_Real = json_item.getString("Price_Real");
                    int Price_Real = json_detial.getInt("Price_Real");
                    int Price_Agent = json_detial.getInt("Price_Agent");
                    int Price_Total = json_detial.getInt("Price_Total");
                    int amount = json_item.getInt("Amount");
                    int commission_ = (Price_Real - Price_Agent) * amount;
                    commission = commission + commission_;
                    num = num + amount;
                    price_total = price_total + Price_Total;
                    OrderCentralChildEntity orderCentralChildEntity = new OrderCentralChildEntity(id, picUrl, productName, sKUName, price_Real, amount + "");
                    orderCentralEntityListViewDataAdapter.append(orderCentralChildEntity);
                }
                tv_goods_num.setText("共" + num + "件商品");
                String shop_name = json_detial.getString("ShopName");
                tv_goods_name.setText(shop_name);
                String Remark = json_detial.getString("Message");
                tv_message.setText(Remark);
                String price_Postage = json_detial.getString("Price_Postage");
                String price_Postage_Real = json_detial.getString("Price_Postage_Real");
                String price_Total_Real = json_detial.getString("Price_Total_Real");
                tv_post_fee.setText("¥" + price_Postage);
                tv_cost_fact.setText("¥" + price_Postage_Real);
                tv_order_pay.setText("¥" + price_Total_Real);
                tv_shop_total.setText("¥" + price_Total_Real);
                tv_comm.setText("¥" + commission);
                tv_order_total.setText("¥" + price_total);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTimeStr(String str) {
        if (str != null && str.length() > 0) {
            String[] strTime = str.split("T");
            String date_ = strTime[0];
            String[] time_ = strTime[1].split(":");
            return date_ + " " + time_[0] + ":" + time_[1] + ":" + time_[2].substring(0, 2);
        }
        return null;
    }

    public String getTimeStrM(String str) {
        if (str != null && str.length() > 0) {
            String[] strTime = str.split("T");
            String date_ = strTime[0];
            String[] time_ = strTime[1].split(":");
            return date_ + " " + time_[0] + ":" + time_[1];
        }
        return null;
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
