package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xdroid.functions.request.sender.BaseRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/1.
 * 兑换详情
 */
public class ExchangeDetialActivity extends BaseActivity implements GroupView {

    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.img_icon)
    private ImageView img_icon;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_tuanbi;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    @ViewInject(R.id.web_view)
    private WebView web_view;
    @ViewInject(R.id.tv_exchange)
    private TextView tv_exchange;

    private TuanbiExchangePresenter tuanbiExchangePresenter;
    private String distributorid = "";
    private String productid = "";
    private String type = "";
    private String tuanbi_str = "";

    private String title_ = "";
    private String img_path_ = "";
    private String price_ = "";
    private String people_ = "";
    private String kucun_ = "";
    private String tuanbi_ = "";

    DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_detial);
        ViewUtils.inject(this);

        distributorid = PreferenceHelper.readString(ExchangeDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        productid = getTextFromBundle("productid");
        type = getTextFromBundle("type");

        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);
        showLoadingProgressDialog(ExchangeDetialActivity.this, "");
        String sign = TGmd5.getMD5(distributorid + productid);
        tuanbiExchangePresenter.getExchangeDetial(distributorid, productid, sign);

    }


    @OnClick({R.id.rl_back, R.id.tv_exchange})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_exchange:
                String str = tv_exchange.getText().toString().trim();
                if (str.equals("立即兑换")) {
                    bundle.putString("productid", productid);
                    bundle.putString("img_path", img_path_);
                    bundle.putString("title", title_);
                    bundle.putString("price", price_);
                    bundle.putString("people", people_);
                    bundle.putString("kucun", kucun_);
                    bundle.putString("tuanbi", tuanbi_);
                    openActivity(ExchangePresnetActivity.class, bundle);
                }else if (str.equals("团币不足")){
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view1 = inflater.inflate(R.layout.dialog_lack_tuanbi, null);//自定义的布局文件
                    TextView message = (TextView) view1.findViewById(R.id.message);
                    message.setTextColor(Color.parseColor("#000000"));
                    TextView tv_sure = (TextView) view1.findViewById(R.id.tv_sure);
                    tv_sure.setText("去充值");
                    TextView tv_cancel = (TextView) view1.findViewById(R.id.tv_cancel);
                    tv_cancel.setText("过会去");
                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view1, pm);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到充值页面
                            Intent intent = new Intent(ExchangeDetialActivity.this, RechargeMoneyActivity.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array_one = new JSONArray(result);
                        String data = array_one.get(1).toString();
                        JSONObject jsonObject_one = new JSONObject(data);

                        String title = jsonObject_one.getString("SubTitle");
                        String picurl = jsonObject_one.getString("PicUrl");
                        String TuanBi = jsonObject_one.getString("TuanBi");
                        String Price_Market = jsonObject_one.getString("Price_Market");
                        String Amount_Sell = jsonObject_one.getString("Amount_Sell");
                        String kucun = jsonObject_one.getString("Amount_Stock");
                        String content = jsonObject_one.getString("Content");

                        title_ = jsonObject_one.getString("SubTitle");
                        img_path_ = jsonObject_one.getString("PicUrl");
                        tuanbi_ = jsonObject_one.getString("TuanBi");
                        people_ = jsonObject_one.getString("Amount_Sell");
                        kucun_ = jsonObject_one.getString("Amount_Stock");
                        price_ = jsonObject_one.getString("Price_Market");


                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + picurl, img_icon, options);

                        tv_title.setText(title);
                        tv_tuanbi.setText(TuanBi + "团币");
                        tv_price.setText("¥" + Price_Market);
                        tv_num.setText(Amount_Sell + "人兑换");
                        tv_kucun.setText(kucun + "库存");
                        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 中划线
                        web_view.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

                        tuanbi_str = PreferenceHelper.readString(ExchangeDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
                        if (type.equals("0")) {
                            tv_exchange.setText("抢光了");
                            tv_exchange.setBackgroundColor(Color.parseColor("#ead4af"));
//                            tv_exchange.setBackgroundResource(R.drawable.bg_radius_apply_no);
                        } else {
                            if (Integer.parseInt(TuanBi) > Integer.parseInt(tuanbi_str)) {
                                tv_exchange.setText("团币不足");
                                tv_exchange.setBackgroundColor(Color.parseColor("#ead4af"));
//                                tv_exchange.setBackgroundResource(R.drawable.bg_radius_apply_no);
                            } else {
                                tv_exchange.setText("立即兑换");
                                tv_exchange.setBackgroundColor(Color.parseColor("#d5aa5f"));
//                                tv_exchange.setBackgroundResource(R.drawable.bg_radius_apply);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(ExchangeDetialActivity.this, "请求失败");
    }

    /**
     * 取消弹框
     */
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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tuanbiExchangePresenter.dettach();
        MobclickAgent.onPause(this);
    }
}
