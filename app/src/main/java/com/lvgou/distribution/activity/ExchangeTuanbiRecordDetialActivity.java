package com.lvgou.distribution.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snwo on 2016/11/3.
 * 团币兑换详情
 */
public class ExchangeTuanbiRecordDetialActivity extends BaseActivity implements GroupView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.tv_company_name)
    private TextView tv_kuaidi;
    @ViewInject(R.id.tv_order_num)
    private TextView tv_order_num;
    @ViewInject(R.id.img_kuaidi)
    private ImageView img_copy_num;
    @ViewInject(R.id.img_icon)
    private ImageView img_icon;
    @ViewInject(R.id.tv_title_goods)
    private TextView tv_goods_title;
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_goods_tuanbi;
    @ViewInject(R.id.tv_price)
    private TextView tv_goods_price;
    @ViewInject(R.id.tv_num)
    private TextView tv_goods_people;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_goods_kucun;
    @ViewInject(R.id.tv_duihuan_num)
    private TextView tv_num;
    @ViewInject(R.id.tv_duihuan_price)
    private TextView tv_sigle_price;
    @ViewInject(R.id.tv_pay_price)
    private TextView tv_pay_tuanbi;


    DisplayImageOptions options;

    private String distributorid = "";
    private String productId = "";

    private TuanbiExchangePresenter tuanbiExchangePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangelog_detial);
        ViewUtils.inject(this);
        tv_title.setText("兑换详情");

        productId = getTextFromBundle("productId");

        distributorid = PreferenceHelper.readString(ExchangeTuanbiRecordDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);

        showLoadingProgressDialog(ExchangeTuanbiRecordDetialActivity.this, "");
        String sign = TGmd5.getMD5(distributorid + productId);
        tuanbiExchangePresenter.getExchangelogdetail(distributorid, productId, sign);

    }


    @OnClick({R.id.rl_back, R.id.img_kuaidi})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.img_kuaidi:
                String text = tv_order_num.getText().toString().trim();
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(clipData);
                MyToast.show(ExchangeTuanbiRecordDetialActivity.this, "复制成功");
                break;
        }
    }

    /***
     * 成功会回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 8:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);

                        /************个人信息***********/
                        String info = jsonArray.get(0).toString();
                        JSONObject jsonObject_person = new JSONObject(info);
                        String name = jsonObject_person.getString("ReceiveName");
                        String phone = jsonObject_person.getString("Mobile");
                        String FullAddress = jsonObject_person.getString("FullAddress");
                        String state_ = jsonObject_person.getString("State");
                        String amount = jsonObject_person.getString("Amount");
                        String pay_tuanbi = jsonObject_person.getString("TuanBi");
                        String kuaidi_name = jsonObject_person.getString("LogisticsName");
                        String order_num = jsonObject_person.getString("LogisticsNO");


                        tv_name.setText(name);
                        tv_phone.setText(phone);
                        tv_address.setText(FullAddress);
                        tv_num.setText(amount);
                        tv_pay_tuanbi.setText(pay_tuanbi);


                        if (state_.equals("1")) {
                            tv_kuaidi.setText("等待发货");
                            tv_order_num.setText("暂无");
                            tv_kuaidi.setTextColor(getResources().getColor(R.color.bg_code_number));
                            tv_order_num.setTextColor(getResources().getColor(R.color.bg_gray_two));
                        } else if (state_.equals("2")) {
                            tv_kuaidi.setText(kuaidi_name);
                            tv_order_num.setText(order_num);
                            tv_kuaidi.setTextColor(getResources().getColor(R.color.bg_balck_three));
                            tv_order_num.setTextColor(getResources().getColor(R.color.bg_balck_three));
                            img_copy_num.setVisibility(View.VISIBLE);
                        }


                        /************商品信息***********/
                        String goods_info = jsonArray.get(1).toString();
                        JSONObject jsonObject_goods = new JSONObject(goods_info);
                        String PicUrl = jsonObject_goods.getString("PicUrl");
                        String SubTitle = jsonObject_goods.getString("SubTitle");
                        String TuanBi = jsonObject_goods.getString("TuanBi");
                        String Price_Market = jsonObject_goods.getString("Price_Market");
                        String Amount_Stock = jsonObject_goods.getString("Amount_Stock");
                        String Amount_Sell = jsonObject_goods.getString("Amount_Sell");

                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + PicUrl, img_icon, options);

                        tv_goods_title.setText(SubTitle);
                        tv_goods_tuanbi.setText(TuanBi + "团币");
                        tv_goods_price.setText("¥" + Price_Market);
                        tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 中划线
                        tv_goods_kucun.setText(Amount_Stock + "库存");
                        tv_goods_people.setText(Amount_Sell + "人兑换");
                        tv_sigle_price.setText(TuanBi);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        tuanbiExchangePresenter.attach(this);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tuanbiExchangePresenter.dettach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
