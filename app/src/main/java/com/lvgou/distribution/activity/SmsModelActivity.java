package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.SmsModelEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.lvgou.distribution.presenter.SendSmsModelPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.SmsModelView;
import com.lvgou.distribution.viewholder.SmsModelViewHolder;
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

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/18
 * 短信模版
 */
public class SmsModelActivity extends BaseActivity implements OnPushSpeedClickListener<SmsModelEntity>, SmsModelView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private String distributorid;
    private ListViewDataAdapter<SmsModelEntity> modelEntityListViewDataAdapter;

    private String sms_id;
    private Dialog dialog;

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_CONTENT = "KEY_CONTENT";

    private SendSmsModelPresenter sendSmsModelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_template);
        ViewUtils.inject(this);
        tv_title.setText("短信模版");
        tv_right.setText("新增");

        sendSmsModelPresenter = new SendSmsModelPresenter(this);

        rl_publish.setVisibility(View.VISIBLE);
        distributorid = PreferenceHelper.readString(SmsModelActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initViewHolder();

    }


    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                Bundle bundle = new Bundle();
                bundle.putString("tmpid", "0");
                bundle.putString("type", "2");
                openActivity(SmsEditAddActivity.class, bundle);
                break;
        }
    }

    public void initViewHolder() {
        modelEntityListViewDataAdapter = new ListViewDataAdapter<SmsModelEntity>();
        modelEntityListViewDataAdapter.setViewHolderClass(this, SmsModelViewHolder.class);
        SmsModelViewHolder.setSmsModelEntityOnPushSpeedClickListener(this);
        lv_list.setAdapter(modelEntityListViewDataAdapter);
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        sendSmsModelPresenter.attach(this);
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            sendSmsModelPresenter.getModelList(distributorid, sign);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendSmsModelPresenter.dettach();
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String datas = array.get(0).toString();
                        JSONArray array_ = new JSONArray(datas);
                        showOrGone();
                        modelEntityListViewDataAdapter.removeAll();
                        if (array_ != null && array_.length() > 0) {
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_.length(); i++) {
                                JSONObject jsonObject_ = array_.getJSONObject(i);
                                String id = jsonObject_.getString("ID");
                                String name = jsonObject_.getString("TmpTitle");
                                String content = jsonObject_.getString("TmpContent");
                                String type = jsonObject_.getString("TmpType");
                                SmsModelEntity smsModelEntity = new SmsModelEntity(id, name, content, type);
                                modelEntityListViewDataAdapter.append(smsModelEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else if (status.equals("0")) {
                        MyToast.show(SmsModelActivity.this, jsonObject.getString("message"));
                        if (jsonObject.getString("message").equals("请登录")) {
                            openActivity(LoginActivity.class);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(SmsModelActivity.this, jsonObject.getString("message"));
                        EventFactory.sendSms(Integer.parseInt(sms_id));
                    } else if (status.equals("0")) {
                        MyToast.show(SmsModelActivity.this, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(SmsModelActivity.this, "请求失败");
                break;
            case 2:
                MyToast.show(SmsModelActivity.this, "请求失败");
                break;
        }
    }


    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }


    /**
     * 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onPushSpeedListener(final SmsModelEntity itemData, int postion) {
        switch (postion) {
            case 1:// item  点击
                modelEntityListViewDataAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra(KEY_ID, itemData.getId());
                intent.putExtra(KEY_NAME, itemData.getName());
                intent.putExtra(KEY_CONTENT, itemData.getContent());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case 2:// 编辑
                Bundle bundle = new Bundle();
                bundle.putString("tmpid", itemData.getId());
                bundle.putString("type", "1");
                bundle.putString("name", itemData.getName());
                bundle.putString("content", itemData.getContent());
                openActivity(SmsEditAddActivity.class, bundle);
                break;
            case 3:// 删除
                sms_id = itemData.getId();
                final String sign = TGmd5.getMD5(distributorid + itemData.getId());
                dialog = new Dialog(SmsModelActivity.this,
                        R.style.Mydialog);
                View view1 = View.inflate(SmsModelActivity.this,
                        R.layout.dialog_delete_show, null);
                Button sure = (Button) view1.findViewById(R.id.sure);
                Button cancle = (Button) view1.findViewById(R.id.cancle);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendSmsModelPresenter.deleteModel(distributorid, itemData.getId(), sign);
                        modelEntityListViewDataAdapter.remove(itemData);
                        dialog.dismiss();
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
                break;
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
