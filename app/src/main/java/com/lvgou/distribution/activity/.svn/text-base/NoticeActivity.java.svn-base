package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.NoticeEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.ReadAllMessagePresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.NoticeViewHolder;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/28 0028.
 * 蜂优团队
 */
public class NoticeActivity extends BaseActivity implements OnListItemClickListener<NoticeEntity>, GroupView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private String distributorid = "";


    private ListViewDataAdapter<NoticeEntity> noticeEntityListViewDataAdapter;

    private ReadAllMessagePresenter readAllMessagePresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    MyToast.show(NoticeActivity.this, "没有未读消息！");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(NoticeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        readAllMessagePresenter = new ReadAllMessagePresenter(this);

        initViewHolder();


    }

    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                String sign = TGmd5.getMD5(distributorid);
                readAllMessagePresenter.doAllActivityRead(distributorid, sign);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllMessagePresenter.attach(this);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid);
                getData(distributorid, sign);
            }
        }
        MobclickAgent.onResume(this);
    }

    /**
     * 初始化viewHolder
     */
    public void initViewHolder() {
        noticeEntityListViewDataAdapter = new ListViewDataAdapter<NoticeEntity>();
        noticeEntityListViewDataAdapter.setViewHolderClass(this, NoticeViewHolder.class);
        NoticeViewHolder.setNoticeEntityOnListItemClickListener(this);
        lv_list.setAdapter(noticeEntityListViewDataAdapter);
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 活动列表
     *
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);

        RequestTask.getInstance().getActionList(NoticeActivity.this, maps, new OnRequestListener());
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
                    JSONArray array = new JSONArray(result);
                    String data = array.get(0).toString();
                    noticeEntityListViewDataAdapter.removeAll();
                    showOrGone();
                    JSONArray array_data = new JSONArray(data);
                    if (array_data != null && array_data.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_data.length(); i++) {
                            JSONObject json_ = array_data.getJSONObject(i);
                            String id = json_.getString("ID");
                            String title = json_.getString("Title");
                            String content = json_.getString("Content");
                            String iconType = json_.getString("IconType");
                            String time = json_.getString("CreateTime");
                            String userId = json_.getString("UserID");
                            NoticeEntity noticeEntity = new NoticeEntity(id, iconType, time, content, title, userId);
                           noticeEntity.setIconType(json_.getInt("IconType"));
                            noticeEntityListViewDataAdapter.append(noticeEntity);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(NoticeActivity.this, jsonObject.getString("message"));
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
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(NoticeActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
        }
    }


    /**
     * 标记已读成功回调
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
                        PreferenceHelper.write(NoticeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, "0");
                        EventFactory.updateCornerIndex("0");
                        String sign = TGmd5.getMD5(distributorid);
                        getData(distributorid, sign);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 标记已读失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 2:
                Message message = new Message();
                message.what = 1001;
                mHandler.sendMessage(message);
                break;
        }
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }

    /**
     * 点击回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(NoticeEntity itemData) {
        long between = 0;
        long day1 = 0;
        if (itemData.getTime() != null && itemData.getTime().length() > 0) {
            String[] str = itemData.getTime().split("T");
            //2016-04-22 16:32:50
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_e = dfs.format(new Date());
            String date_b = str[0] + " " + str[1];
            try {
                Date begin = dfs.parse(date_b);
                Date end = dfs.parse(date_e);
                between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                day1 = between / (24 * 3600);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ((Integer.parseInt(itemData.getUser_id()) == 0) && day1 < 7) {
            String num_a = PreferenceHelper.readString(NoticeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, "0");
            if (Integer.parseInt(num_a) > 0) {
                int a_ = Integer.parseInt(num_a) - 1;
                PreferenceHelper.write(NoticeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, a_ + "");
                EventFactory.updateCornerIndex("0");
            }
        }
        Bundle pBundle = new Bundle();
        pBundle.putString("index", "5");
        pBundle.putString("id", itemData.getId());
        openActivity(NoticeDetialActivity.class, pBundle);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readAllMessagePresenter.dettach();
    }
}
