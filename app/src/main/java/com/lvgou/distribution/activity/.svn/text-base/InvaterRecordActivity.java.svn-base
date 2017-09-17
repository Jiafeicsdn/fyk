package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.InviteRecordEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshGridView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.InviteRecordViewHoder;
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
 * Created by Snow on 2016/4/6
 * 邀请记录
 */
public class InvaterRecordActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private GridView gridView;
    @ViewInject(R.id.pull_refresh_grid)
    private PullToRefreshGridView mPullRefreshGridView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_gone;

    private String distributorid = "";
    private int pageIndex = 1;
    boolean mIsUp;// 是否上拉加载
    private ListViewDataAdapter<InviteRecordEntity> listViewDataAdapter;

    private String type = "1";
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_record);
        ViewUtils.inject(this);
        tv_title.setText("邀请记录");
        type = getTextFromBundle("type");// 2 已完成  3  未完成
        distributorid = PreferenceHelper.readString(InvaterRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initViewHolder();
        if (distributorid.equals("") || distributorid.equals("nulll")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid + type + pageIndex);
                getData(distributorid, type, pageIndex + "", sign);
            }
        }
        setmPullRefreshGridView();
    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    /**
     * 初始化ViewHolder
     */
    public void initViewHolder() {
        gridView = mPullRefreshGridView.getRefreshableView();
        listViewDataAdapter = new ListViewDataAdapter<InviteRecordEntity>();
        listViewDataAdapter.setViewHolderClass(this, InviteRecordViewHoder.class);
        gridView.setAdapter(listViewDataAdapter);
    }

    public void setmPullRefreshGridView() {
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = false;
                pageIndex = 1;
                String sign = TGmd5.getMD5(distributorid + type + pageIndex);
                getData(distributorid, type, pageIndex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = true;
                if (pageIndex < total_page) {
                    pageIndex += 1;
                    String sign = TGmd5.getMD5(distributorid + type + pageIndex);
                    getData(distributorid, type, pageIndex + "", sign);
                } else {
                    MyToast.show(InvaterRecordActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_gone.setVisibility(View.GONE);
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param type
     * @param pageindex
     * @param sign
     */
    public void getData(String distributorid, String type, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("type", type);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getInviteList(InvaterRecordActivity.this, maps, new OnRequestListener());
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
                    JSONObject json_data = new JSONObject(data);
                    total_page = json_data.getInt("TotalPages");
                    String items = json_data.getString("Items");
                    if (mIsUp == false) {
                        listViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        // 不做任何处理
                    }
                    showRoGone();
                    JSONArray json_items = new JSONArray(items);
                    if (json_items != null && json_items.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < json_items.length(); i++) {
                            JSONObject object = json_items.getJSONObject(i);
                            String id = object.getString("ID");
                            String real_name = object.getString("RealName");
                            String IsGet = object.getString("IsGet");
                            String State = object.getString("State");
                            String ExperienceBuy = object.getString("ExperienceBuy");
                            String tiem = object.getString("CreateTime");
                            InviteRecordEntity inviteRecordEntity = new InviteRecordEntity(ExperienceBuy, State, IsGet, tiem, real_name, id);
                            listViewDataAdapter.append(inviteRecordEntity);
                        }
                    } else {
                        showRoGone();
                        rl_gone.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(InvaterRecordActivity.this, jsonObject.getString("message"));
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
            showLoadingProgressDialog(InvaterRecordActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
            mPullRefreshGridView.onRefreshComplete();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
            mPullRefreshGridView.onRefreshComplete();
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mPullRefreshGridView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}
