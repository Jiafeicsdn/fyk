package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.AddEditSmsBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.SmsAddEditPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.AddEditSmsView;
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
 * Created by Snow on 2016/4/18 0018.
 * 短信模板，编辑增加
 */
public class SmsEditAddActivity extends BaseActivity implements AddEditSmsView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_title)
    private EditText et_name;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.tv_send)
    private TextView tv_save;

    private String type;
    private String tmpid_ = "";
    private String distributorid = "";
    private String name = "";
    private String content = "";


    private SmsAddEditPresenter smsAddEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_add);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(SmsEditAddActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        type = getTextFromBundle("type");
        tmpid_ = getTextFromBundle("tmpid");

        smsAddEditPresenter = new SmsAddEditPresenter(this);

        if (type.equals("1")) {
            tv_title.setText("编辑模版");
            name = getTextFromBundle("name");
            content = getTextFromBundle("content");
            et_name.setText(name);
            et_content.setText(content);
        } else if (type.equals("2")) {
            tv_title.setText("新增模版");
        }
    }

    @OnClick({R.id.rl_back, R.id.tv_send})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_send:
                String name = et_name.getText().toString().trim();
                String content = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(name)) {
                    MyToast.show(SmsEditAddActivity.this, "请输入短信名称");
                    return;
                } else if (StringUtils.isEmpty(content)) {
                    MyToast.show(SmsEditAddActivity.this, "请输入短信内容");
                    return;
                } else if (content.length() > 200) {
                    MyToast.show(SmsEditAddActivity.this, "内容字数不能超过200字");
                    return;
                } else {
                    String name_ = et_name.getText().toString().trim();
                    String content_ = et_content.getText().toString().trim();
                    String sign = TGmd5.getMD5(distributorid + tmpid_ + name_ + content_);
                    AddEditSmsBean addEditSmsBean = new AddEditSmsBean(distributorid, tmpid_, name_, content_, sign);
                    smsAddEditPresenter.addEditModel(addEditSmsBean);
                }
                break;
        }
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                MyToast.show(SmsEditAddActivity.this, jsonObject.getString("message"));
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String id_ = array.get(0).toString().trim();
                finish();
            } else if (status.equals("0")) {
                MyToast.show(SmsEditAddActivity.this, jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String s) {
        MyToast.show(SmsEditAddActivity.this, "请求失败");
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        smsAddEditPresenter.attach(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsAddEditPresenter.attach(this);
    }
}
