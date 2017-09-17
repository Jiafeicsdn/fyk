package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * Created by Snpw on 2016/3/9
 * 修改店铺名称
 */
public class UpdateShopNameActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_shop_name)
    private EditText et_shop_name;
    @ViewInject(R.id.btn_save)
    private Button btn_save;

    private String shop_name = "";
    private String distributorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shopname);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(UpdateShopNameActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        tv_title.setText("店铺名称");
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            getName(distributorid, sign);
        }
    }

    @OnClick({R.id.rl_back, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_save:
                String shop_name = et_shop_name.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + shop_name);
                if (StringUtils.isEmpty(shop_name)) {
                    MyToast.show(UpdateShopNameActivity.this, "请输入店铺名称");
                    return;
                } else {
                    updateName(distributorid, shop_name, sign);
                }
                break;
        }
    }

    //修改店铺名称
    public void updateName(String distributorid, String shopname, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("shopname", shopname);
        maps.put("sign", sign);
        RequestTask.getInstance().doUpdateShopName(UpdateShopNameActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(UpdateShopNameActivity.this, "修改成功");
                    finish();
                } else if (status.equals("0")) {
                    MyToast.show(UpdateShopNameActivity.this, "修改失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //获取店铺名称
    public void getName(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getShopName(UpdateShopNameActivity.this, maps, new OngetNmmeRequestListener());
    }

    private class OngetNmmeRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String name = array.get(0).toString();
                    et_shop_name.setText(name);
                    if (name.length() > 0) {
                        et_shop_name.setSelection(name.length());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
