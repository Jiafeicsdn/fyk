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
import com.lvgou.distribution.entity.MyGroupEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshGridView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.MyGroupersViewHolder;
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
 * Created by Snow on 2016/3/21 0021.
 * 我的团员
 */
public class MyGroupersActivity extends BaseActivity {

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

    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<MyGroupEntity> myGroupEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        ViewUtils.inject(this);
        tv_title.setText("所有团员");

        distributorid = PreferenceHelper.readString(MyGroupersActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        gridView = mPullRefreshGridView.getRefreshableView();

        initViewHolder();
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid + pageIndex);
                getData(distributorid, pageIndex + "", sign);
            }
        }
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = false;
                pageIndex = 1;
                String sign = TGmd5.getMD5(distributorid + pageIndex);
                getData(distributorid, pageIndex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = true;
                pageIndex += 1;
                if (pageIndex <= total_page) {
                    String sign = TGmd5.getMD5(distributorid + pageIndex);
                    getData(distributorid, pageIndex + "", sign);
                } else {
                    MyToast.show(MyGroupersActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_gone.setVisibility(View.GONE);
    }

    /**
     * 初始化ViewHolder
     */
    public void initViewHolder() {
        myGroupEntityListViewDataAdapter = new ListViewDataAdapter<MyGroupEntity>();
        myGroupEntityListViewDataAdapter.setViewHolderClass(this, MyGroupersViewHolder.class);
        gridView.setAdapter(myGroupEntityListViewDataAdapter);
    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public void getData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getMyGroupers(MyGroupersActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String item = array.get(0).toString();
                        JSONObject json_result = new JSONObject(item);
                        String date = json_result.getString("Items");
                        total_page = json_result.getInt("TotalPages");
                        JSONArray array_data = new JSONArray(date);

                        if (mIsUp == false) {
                            myGroupEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载 不做清空myGroupEntityListViewDataAdapter
                        }
                        if (array_data != null && array_data.length() > 0) {
                            showRoGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject json_data = array_data.getJSONObject(i);
                                String id = json_data.getString("ID");
                                String name = json_data.getString("RealName");
                                String phone = json_data.getString("Mobile");
                                String time = json_data.getString("CreateTime");
                                MyGroupEntity myGroupEntity = new MyGroupEntity(id, time, phone, name);
                                myGroupEntityListViewDataAdapter.append(myGroupEntity);
                            }
                        } else {
                            showRoGone();
                            rl_gone.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (status.equals("0")) {
                    MyToast.show(MyGroupersActivity.this, jsonObject.getString("message"));
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
            showLoadingProgressDialog(MyGroupersActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            mPullRefreshGridView.onRefreshComplete();
            closeLoadingProgressDialog();
        }
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
