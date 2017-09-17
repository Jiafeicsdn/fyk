package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.lvgou.distribution.inter.OnPhoneClickListener;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.viewholder.PhoneViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/19
 * 编辑分组，新建
 */
public class EditAddGroupActivity extends BaseActivity implements OnPhoneClickListener<PhoneEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_group_name)
    private EditText et_name;
    @ViewInject(R.id.grid_phone)
    private GridView gridView;
    @ViewInject(R.id.btn_save)
    private Button btn_save;

    private String group_name = "";
    private String mobiles = "";
    private String type = "";
    private String group_id = "";

    private String distributorid = "";

    private ListViewDataAdapter<PhoneEntity> phoneEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(EditAddGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        type = getTextFromBundle("type");
        group_id = getTextFromBundle("group_id");
        if (type.equals("1")) {
            tv_title.setText("编辑分组");
            mobiles = getTextFromBundle("Mobiles");
            group_name = getTextFromBundle("group_name");
            et_name.setText(group_name);
        } else if (type.equals("2")) {
            tv_title.setText("新增分组");
        }
        initViewHolder();
        if (checkNet()) {
            getData(mobiles);
        }
    }

    @OnClick({R.id.rl_back, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_save:
                break;
        }
    }

    public void initViewHolder() {
        phoneEntityListViewDataAdapter = new ListViewDataAdapter<PhoneEntity>();
        phoneEntityListViewDataAdapter.setViewHolderClass(this, PhoneViewHolder.class);
        PhoneViewHolder.setPhoneEntityOnPhoneClickListener(this);
        gridView.setAdapter(phoneEntityListViewDataAdapter);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 解析数据
     *
     * @param mobiles
     */
    public void getData(String mobiles) {
        if (mobiles.length() > 11) {
            phoneEntityListViewDataAdapter.removeAll();
            String[] phones = mobiles.split(",");
            for (int i = 0; i < phones.length; i++) {
                PhoneEntity phoneEntity = new PhoneEntity(i + "", phones[i]);
                phoneEntityListViewDataAdapter.append(phoneEntity);
            }
            PhoneEntity phoneEntity1 = new PhoneEntity("", "");
            phoneEntityListViewDataAdapter.append(phoneEntity1);
        } else if (mobiles.length() > 0 && mobiles.length() < 12) {
            phoneEntityListViewDataAdapter.removeAll();
            PhoneEntity phoneEntity2 = new PhoneEntity("", mobiles);
            PhoneEntity phoneEntity1 = new PhoneEntity("", "");
            phoneEntityListViewDataAdapter.append(phoneEntity2);
            phoneEntityListViewDataAdapter.append(phoneEntity1);
        } else if (mobiles.length() == 0) {
            PhoneEntity phoneEntity1 = new PhoneEntity("", "");
            phoneEntityListViewDataAdapter.append(phoneEntity1);
        }
    }

    /**
     * @param distributorid
     * @param groupid
     * @param groupname
     * @param mobiles
     * @param sign
     */
    public void editGroup(String distributorid, String groupid, String groupname, String mobiles, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("groupid", groupid);
        maps.put("groupname", groupname);
        maps.put("mobiles", mobiles);
        maps.put("sign", sign);

        RequestTask.getInstance().editGroup(EditAddGroupActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(EditAddGroupActivity.this, "编辑成功");
                } else if (status.equals("0")) {
                    MyToast.show(EditAddGroupActivity.this, "编辑失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击监听EditText
     *
     * @param phone
     * @param itemData
     */
    @Override
    public void onPhoneClickListener(String phone, PhoneEntity itemData) {
//        if (phone.length() == 11 && StringUtils.isPhone(phone)) {
//            PhoneEntity phoneEntity = new PhoneEntity("", phone);
//            PhoneEntity phoneEntity1 = new PhoneEntity("", "");
//            phoneEntityListViewDataAdapter.append(phoneEntity);
//            phoneEntityListViewDataAdapter.append(phoneEntity1);
//        }
    }
}
