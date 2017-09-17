package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.ExchangeBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/2.
 * 礼品兑换
 */
public class ExchangePresnetActivity extends BaseActivity implements GroupView {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_address)
    private EditText et_address;
    @ViewInject(R.id.img_icon)
    private ImageView img_icon;
    @ViewInject(R.id.tv_title_one)
    private TextView tv_title_goods;
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_tuanbi_goods;
    @ViewInject(R.id.tv_price)
    private TextView tv_price_goods;
    @ViewInject(R.id.tv_num_people)
    private TextView tv_people_num;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    @ViewInject(R.id.rl_jianhao)
    private RelativeLayout rl_jianhao;
    @ViewInject(R.id.tv_goods_num)
    private TextView tv_num;// 商品数量
    @ViewInject(R.id.rl_jihao)
    private RelativeLayout rl_jiahao;
    @ViewInject(R.id.tv_tuanbi_total)
    private TextView tv_totle_tuanbi;
    @ViewInject(R.id.tv_num)
    private TextView tv_goos_num;
    @ViewInject(R.id.tv_tuanbi_pay)
    private TextView tv_pay_tuanbi;
    @ViewInject(R.id.tv_tuanbi_shengyu)
    private TextView tv_shengyu_tuanbi;
    @ViewInject(R.id.jiahao)
    private ImageView img_jiahao;
    @ViewInject(R.id.jianhao)
    private ImageView img_jianhao;
    @ViewInject(R.id.tv_sure)
    private TextView tv_sure_exchange;

    DisplayImageOptions options;


    private String distributorid = "";

    private String img_path = "";
    private String title = "";
    private String price = "0";
    private String people = "0";
    private String kucun = "0";
    private String tuanbi = "0";
    private String productId = "";

    private String tuanbi_pay = "0";
    private TuanbiExchangePresenter tuanbiExchangePresenter;

    private Dialog dialog_exchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_present);
        ViewUtils.inject(this);
        tv_title.setText("确认兑换");
        distributorid = PreferenceHelper.readString(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);

        productId = getTextFromBundle("productid");
        img_path = getTextFromBundle("img_path");
        title = getTextFromBundle("title");
        price = getTextFromBundle("price");
        people = getTextFromBundle("people");
        tuanbi = getTextFromBundle("tuanbi");
        kucun = getTextFromBundle("kucun");
        tuanbi_pay = tuanbi;

        distributorid = PreferenceHelper.readString(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        et_address.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_address.setSingleLine(false);
        //水平滚动设置为False
        et_address.setHorizontallyScrolling(false);


        String sign = TGmd5.getMD5(distributorid);

        tuanbiExchangePresenter.exchangeTuanbi(distributorid, sign);


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_order)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_order)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_order)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                        // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_icon, options);
        tv_title_goods.setText(title);
        tv_tuanbi_goods.setText(tuanbi + "团币");
        tv_price_goods.setText("¥" + price);
        tv_price_goods.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 中划线
        tv_people_num.setText(people + "人兑换");
        tv_kucun.setText(kucun + "库存");

    }


    @OnClick({R.id.rl_back, R.id.rl_jianhao, R.id.rl_jihao, R.id.tv_sure})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_jianhao:
                String tuanbi_b = PreferenceHelper.readString(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
                rl_jiahao.setEnabled(true);
                img_jiahao.setBackgroundResource(R.mipmap.jiahao);
                if (Integer.parseInt(tv_num.getText().toString()) == 1) {
                    rl_jianhao.setEnabled(false);
                    img_jianhao.setBackgroundResource(R.mipmap.jianhao);
                } else {
                    int a = Integer.parseInt(tv_num.getText().toString()) - 1;
                    int fu_tuanbi = a * Integer.parseInt(tuanbi);
                    int shengyu_tuanbi_a = Integer.parseInt(tuanbi_b) - fu_tuanbi;
                    tv_num.setText(a + "");
                    tv_shengyu_tuanbi.setText(shengyu_tuanbi_a + "");
                    tv_goos_num.setText(tuanbi + "x" + a);
                    tv_pay_tuanbi.setText("-" + fu_tuanbi);
                }
                break;
            case R.id.rl_jihao:
                String tuanbi_a = PreferenceHelper.readString(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
                rl_jianhao.setEnabled(true);
                img_jianhao.setBackgroundResource(R.mipmap.jianhao_black);
                if (Integer.parseInt(tv_num.getText().toString()) >= (Integer.parseInt(kucun))) {
                    rl_jiahao.setEnabled(false);
                    MyToast.show(ExchangePresnetActivity.this, "达到库存最大值");
                    img_jiahao.setBackgroundResource(R.mipmap.jiahao_gray);
                } else {
                    int b = Integer.parseInt(tv_num.getText().toString()) + 1;
                    int fu_tuanbi = b * Integer.parseInt(tuanbi);
                    int pay_d = Integer.parseInt(tuanbi_a) - fu_tuanbi;
                    tv_num.setText(b + "");
                    tv_goos_num.setText(tuanbi + "x" + b);
                    tv_shengyu_tuanbi.setText(pay_d + "");
                    tv_pay_tuanbi.setText("-" + fu_tuanbi);
                    if (pay_d < Integer.parseInt(tuanbi)) {
                        rl_jiahao.setEnabled(false);
                        img_jiahao.setBackgroundResource(R.mipmap.jiahao_gray);
//                        MyToast.show(ExchangePresnetActivity.this, "团币不足");
                    }
                }
                break;
            case R.id.tv_sure:
                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                String address = et_address.getText().toString();
                String num_str = tv_num.getText().toString();
                if (StringUtils.isEmpty(name)) {
                    MyToast.show(ExchangePresnetActivity.this, "请输入姓名");
                    return;
                } else if (StringUtils.isEmpty(phone)) {
                    MyToast.show(ExchangePresnetActivity.this, "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(ExchangePresnetActivity.this, "手机号不合法");
                    return;
                } else if (StringUtils.isEmpty(address)) {
                    MyToast.show(ExchangePresnetActivity.this, "请输入详细地址");
                    return;
                } else {
                    showQuitDialog(distributorid, productId, num_str, name, phone, address);
                }
                break;
        }
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 6:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String tuanbi_str = array.get(0).toString();
                        PreferenceHelper.write(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_str);
                        String data = array.get(1).toString();
                        JSONObject jsonObject_one = new JSONObject(data);
                        String name = jsonObject_one.getString("ReceiveName");
                        String phoen = jsonObject_one.getString("Mobile");
                        String address = jsonObject_one.getString("FullAddress");

                        et_name.setText(name);
                        et_phone.setText(phoen);
                        et_address.setText(address);

                        if (name.length() > 0) {
                            et_name.setSelection(name.length());
                        }
                        if (phoen.length() > 0) {
                            et_phone.setSelection(phoen.length());
                        }

                        if (address.length() > 0) {
                            et_address.setSelection(address.length());
                        }

                        tv_totle_tuanbi.setText(tuanbi_str);

                        tv_goos_num.setText(tuanbi + "x1");
                        tv_pay_tuanbi.setText("-" + tuanbi);

                        int a = Integer.parseInt(tuanbi_str) - Integer.parseInt(tuanbi);
                        tv_shengyu_tuanbi.setText(a + "");

                        rl_jianhao.setEnabled(false);
                        img_jianhao.setBackgroundResource(R.mipmap.jiahao_gray);

                        if (a >= Integer.parseInt(tuanbi)) {
                            rl_jiahao.setEnabled(true);
                            img_jiahao.setBackgroundResource(R.mipmap.jiahao);
                        } else {
                            rl_jiahao.setEnabled(false);
                            img_jiahao.setBackgroundResource(R.mipmap.jiahao_gray);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(ExchangePresnetActivity.this, "兑换成功");
                        String shengyu_tuanbi = tv_shengyu_tuanbi.getText().toString();
                        PreferenceHelper.write(ExchangePresnetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, shengyu_tuanbi);
                        Intent intent = new Intent();
                        intent.setAction("com.distribution.tugou.update");
                        sendBroadcast(intent);
                        finish();
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

    }

    @Override
    public void showProgress() {

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


    //退出登录
    public void showQuitDialog(final String distributorid, final String productId, final String num_str, final String name, final String phone, final String address) {
        dialog_exchange = new Dialog(ExchangePresnetActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ExchangePresnetActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("确定兑换吗？");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exchange.dismiss();
                String sign = TGmd5.getMD5(distributorid + productId + num_str + name + phone + address);
                ExchangeBean exchangeBean = new ExchangeBean(distributorid, productId, num_str, name, phone, address, sign);
                tuanbiExchangePresenter.doExchangeCommit(exchangeBean);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exchange.dismiss();
            }
        });
        dialog_exchange.setContentView(view1);
        dialog_exchange.show();
    }
}
