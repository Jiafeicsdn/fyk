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
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.GroupEntity;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.SelectGroupViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.TimeUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.request.extension.interfaces.OnRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/18 0018.
 * 选择分组
 */
public class SelectGroupActivity extends BaseActivity implements OnPushSpeedClickListener<GroupEntity> {

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


    private ListViewDataAdapter<GroupEntity> groupEntityListViewDataAdapter;
    private String distributorid;

    private Dialog dialog;

    public static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    public static final String KEY_GROUP_NAME = "KEY_GROUP_NAME";
    public static final String KEY_GROUP_MOBILES = "KEY_GROUP_MOBILES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        ViewUtils.inject(this);
        tv_title.setText("选择分组");
        rl_publish.setVisibility(View.VISIBLE);
        tv_right.setText("新增");
        distributorid = PreferenceHelper.readString(SelectGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

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
                bundle.putString("group_id", "0");
                bundle.putString("type", "2");
                openActivity(EditAddGroupActivity.class, bundle);
                break;
        }
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    public void initViewHolder() {
        groupEntityListViewDataAdapter = new ListViewDataAdapter<GroupEntity>();
        groupEntityListViewDataAdapter.setViewHolderClass(this, SelectGroupViewHolder.class);
        SelectGroupViewHolder.setGroupEntityOnPushSpeedClickListener(this);
        lv_list.setAdapter(groupEntityListViewDataAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            getData(distributorid, sign);
        }
    }

    /**
     * 删除分组
     *
     * @param distributorid
     * @param groupid
     * @param sign
     */
    public void deleteGroup(String distributorid, String groupid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("groupid", groupid);
        maps.put("sign", sign);
        RequestTask.getInstance().deleteGroup(SelectGroupActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(SelectGroupActivity.this, jsonObject.getString("message"));
                    String sign = TGmd5.getMD5("1");
                    getData("1", sign);
                } else if (status.equals("0")) {
                    MyToast.show(SelectGroupActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取分组列表
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().groupList(SelectGroupActivity.this, maps, new OnGroupRequestListener());
    }

    private class OnGroupRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String data_ = array.get(0).toString();
                    JSONArray array_ = new JSONArray(data_);
                    showOrGone();
                    groupEntityListViewDataAdapter.removeAll();
                    if (array_ != null && array_.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject json_group = array_.getJSONObject(i);
                            String id = json_group.getString("ID");
                            String name = json_group.getString("GroupName");
                            String phones = json_group.getString("Mobiles");
                            GroupEntity groupEntity = new GroupEntity(id, name, phones);
                            groupEntityListViewDataAdapter.append(groupEntity);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(SelectGroupActivity.this, jsonObject.getString("message"));
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

    /**
     * 接口回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onPushSpeedListener(final GroupEntity itemData, int postion) {
        switch (postion) {
            case 1:
                Constants.SELECTED_GROUP_ID = itemData.getId();
                Intent intent = new Intent();
                intent.putExtra(KEY_GROUP_ID, itemData.getId());
                intent.putExtra(KEY_GROUP_NAME, itemData.getName());
                intent.putExtra(KEY_GROUP_MOBILES, itemData.getPhones());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putString("group_id", itemData.getId());
                bundle.putString("type", "1");
                bundle.putString("group_name", itemData.getName());
                bundle.putString("Mobiles", itemData.getPhones());
                openActivity(EditAddGroupActivity.class, bundle);
                break;
            case 3:
                final String sign = TGmd5.getMD5(distributorid + itemData.getId());
                dialog = new Dialog(SelectGroupActivity.this,
                        R.style.Mydialog);
                View view1 = View.inflate(SelectGroupActivity.this,
                        R.layout.dialog_delete_group, null);
                Button sure = (Button) view1.findViewById(R.id.sure);
                Button cancle = (Button) view1.findViewById(R.id.cancle);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGroup(distributorid, itemData.getId(), sign);
//                        groupEntityListViewDataAdapter.remove(itemData);
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
