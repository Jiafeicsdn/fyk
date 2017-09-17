package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.PhoneEntity;
import com.lvgou.distribution.presenter.SendRecordDetialPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.view.SendSmsRecordDetialView;
import com.lvgou.distribution.viewholder.GridPhoneViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Snow on 2016/4/18 0018.
 * 短信记录详情
 */
public class SendRecordDetialActivity extends BaseActivity implements SendSmsRecordDetialView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_model)
    private TextView tv_model;
    @ViewInject(R.id.tv_group)
    private TextView tv_group;
    @ViewInject(R.id.tv_content)
    private TextView tv_content;
    @ViewInject(R.id.grid_view)
    private MyGridView gridView;
    @ViewInject(R.id.tv_send)
    private TextView tv_again;
    private ListViewDataAdapter<PhoneEntity> phoneEntityListViewDataAdapter;

    private List<String> phones;
    private String distributorid = "";
    private String msgId = "";
    private String TmpTitle = "";
    private String content = "";
    private String tmpId = "";

    private SendRecordDetialPresenter sendRecordDetialPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detial);
        ViewUtils.inject(this);
        tv_title.setText("发送详情");

        sendRecordDetialPresenter = new SendRecordDetialPresenter(this);

        distributorid = PreferenceHelper.readString(SendRecordDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initViewHolder();
        msgId = getTextFromBundle("msgId");


        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid + msgId);
                sendRecordDetialPresenter.getDetial(distributorid, msgId, sign);
            }
        }
    }

    @OnClick({R.id.rl_back, R.id.tv_send})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_send:
                Bundle bundle = new Bundle();
                bundle.putString("state", "1");
                bundle.putString("name", TmpTitle);
                bundle.putString("content", content);
                bundle.putString("id", tmpId);
                bundle.putString("phones", phones.toString());
                openActivity(NewFreeSmsActivity.class, bundle);
                finish();
                break;
        }
    }

    public void initViewHolder() {
        phoneEntityListViewDataAdapter = new ListViewDataAdapter<PhoneEntity>();
        phoneEntityListViewDataAdapter.setViewHolderClass(this, GridPhoneViewHolder.class);
        gridView.setAdapter(phoneEntityListViewDataAdapter);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }

    @Override
    public void excuteSuccessCallBack(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                phones = new ArrayList<String>();
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String data = array.get(0).toString();
                JSONObject json_data = new JSONObject(data);
                String time = json_data.getString("CreateTime");
                tmpId = json_data.getString("TemplateID");
                TmpTitle = json_data.getString("TmpTitle");
                content = json_data.getString("Content");
                String Mobiles = json_data.getString("Mobiles");
                phoneEntityListViewDataAdapter.removeAll();
                phones.clear();
                if (Mobiles.contains(",")) {
                    String[] strPhone = Mobiles.split(",");
                    for (int i = 0; i < strPhone.length; i++) {
                        PhoneEntity phoneEntity = new PhoneEntity(i + "", strPhone[i]);
                        phoneEntityListViewDataAdapter.append(phoneEntity);
                        phones.add(strPhone[i]);
                    }
                } else {
                    PhoneEntity phoneEntity = new PhoneEntity("", Mobiles);
                    phoneEntityListViewDataAdapter.append(phoneEntity);
                    phones.add(Mobiles);
                }
                tv_model.setText(TmpTitle);
                String[] str = time.split("T");
                tv_time.setText(str[1].substring(0, 5) + "发送");
                tv_content.setText(content);
                String gorup_name = array.get(1).toString();
                tv_group.setText(gorup_name);
            } else if (status.equals("0")) {
                MyToast.show(SendRecordDetialActivity.this, jsonObject.getString("message"));
                if (jsonObject.getString("message").equals("请登录")) {
                    openActivity(LoginActivity.class);
                    finish();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void excuteFailedCallBack(String s) {
        MyToast.show(SendRecordDetialActivity.this, "请求错误");
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        sendRecordDetialPresenter.attach(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendRecordDetialPresenter.dettach();
    }
}
