package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportShopEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ReportShopViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/7/2 0002.
 */
public class ReportSearchActivity extends BaseActivity implements OnListItemClickListener<ReportShopEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.img_search)
    private ImageView img_serach;
    private ListViewDataAdapter<ReportShopEntity> reportShopEntityListViewDataAdapter;
    private String distributorid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        distributorid = PreferenceHelper.readString(ReportSearchActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                getData(distributorid, sign);
            }
        }
        serachData();
    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 搜索操作
     */
    public void serachData() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Bundle pBundle = new Bundle();
                pBundle.putString("key", et_search.getText().toString().trim());
                openActivity(ReportSearchHistoryActivity.class, pBundle);
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return true;
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search.getText().length() == 0) {
                    img_serach.setVisibility(View.VISIBLE);
                } else {
                    img_serach.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getReporSearchHistory(ReportSearchActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array_ = new JSONArray(result);
                    String data = array_.get(0).toString();
                    JSONArray array = new JSONArray(data);
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject_ = array.getJSONObject(i);
                            String id = jsonObject_.getString("ID");
                            String name = jsonObject_.getString("ShopName");
                            String reportSellerId = jsonObject_.getString("ReportSellerID");
                            ReportShopEntity reportShopEntity = new ReportShopEntity(name, id, reportSellerId);
                            reportShopEntityListViewDataAdapter.append(reportShopEntity);
                        }
                    }
                } else if (status.equals("0")) {
                    MyToast.show(ReportSearchActivity.this, jsonObject.getString("message"));
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

    @Override
    public void onListItemClick(ReportShopEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("index", "0");
        bundle.putString("reportId", "0");
        bundle.putString("shop_name", itemData.getName());
        bundle.putString("reportSellerId", itemData.getReportSellerID());
        openActivity(ReportShopActivity.class, bundle);
    }


}
