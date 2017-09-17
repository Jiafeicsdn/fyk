package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.TuanbiDetialAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TaskDetialEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.TaskDetialViewHolder;
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
 * Created by Snow on 2016/5/25 0025.
 * 团币明细
 */
public class TuanBiDetialActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pull_refresh_list;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;


    private ListView lv_list;

    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private ListViewDataAdapter<TaskDetialEntity> entityListViewDataAdapter;
    private TuanbiDetialAdapter adapter;
    private List<TaskDetialEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detial);
        ViewUtils.inject(this);
        tv_title.setText("团币明细");
        distributorid = PreferenceHelper.readString(TuanBiDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_list = pull_refresh_list.getRefreshableView();
        list = new ArrayList<TaskDetialEntity>();
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid + pageindex + "");
                getData(distributorid, pageindex + "", sign);
            }
        }
        initCreateView();

    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    public void initViewHolder() {
        entityListViewDataAdapter = new ListViewDataAdapter<TaskDetialEntity>();
        entityListViewDataAdapter.setViewHolderClass(this, TaskDetialViewHolder.class);
        lv_list.setAdapter(entityListViewDataAdapter);
    }

    public void initCreateView() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(TuanBiDetialActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pull_refresh_list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                getData(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(TuanBiDetialActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 获取数据
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);

        RequestTask.getInstance().getTuanbiDetial(this, maps, new OnRequestListener());
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
                    String items_ = array.get(0).toString();
                    JSONObject json_ = new JSONObject(items_);
                    total_page = json_.getInt("TotalPages");
                    String data = json_.getString("Items");
                    showOrGone();
                    if (mIsUp == false) {
                        list.clear();
                        JSONArray array_ = new JSONArray(data);
                        if (array_ != null && array_.length() > 0) {
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_.length(); i++) {
                                JSONObject json_item = array_.getJSONObject(i);
                                String id = json_item.getString("ID");
                                String name = json_item.getString("Intro");
                                String time = json_item.getString("CreateTime");
                                String status_ = json_item.getString("IsOut");
                                String poiont = json_item.getString("TuanBi");
                                TaskDetialEntity taskDetialEntity = new TaskDetialEntity(id, name, time, poiont, status_);
                                list.add(taskDetialEntity);
                            }
                            adapter = new TuanbiDetialAdapter(TuanBiDetialActivity.this);
                            lv_list.setAdapter(adapter);
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else if (mIsUp == true) {
                        JSONArray array_ = new JSONArray(data);
                        if (array_ != null && array_.length() > 0) {
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_.length(); i++) {
                                JSONObject json_item = array_.getJSONObject(i);
                                String id = json_item.getString("ID");
                                String name = json_item.getString("Intro");
                                String time = json_item.getString("CreateTime");
                                String status_ = json_item.getString("IsOut");
                                String poiont = json_item.getString("TuanBi");
                                TaskDetialEntity taskDetialEntity = new TaskDetialEntity(id, name, time, poiont, status_);
                                list.add(taskDetialEntity);
                            }
                            adapter = new TuanbiDetialAdapter(TuanBiDetialActivity.this);
                            lv_list.setAdapter(adapter);
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (status.equals("0")) {
                    MyToast.show(TuanBiDetialActivity.this, jsonObject.getString("message"));
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
            showLoadingProgressDialog(TuanBiDetialActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
            pull_refresh_list.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
            pull_refresh_list.onRefreshComplete();
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
            pull_refresh_list.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}
