package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.SearchResultEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ReportSearchHistoryViewHolder;
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
 * Created by Snow on 2016/7/2
 */
public class ReportSearchHistoryActivity extends BaseActivity implements OnListItemClickListener<SearchResultEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.img_search)
    private ImageView img_serach;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    private ListView lv_list;
    private String key;
    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String keyword;

    private ListViewDataAdapter<SearchResultEntity> searchResultEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search_history);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(ReportSearchHistoryActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_list = pullToRefreshListView.getRefreshableView();
        keyword = getTextFromBundle("key");
        et_search.setText(keyword);
        createView();
//        initViewHolder();
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
    protected void onResume() {
        super.onResume();
        String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                getData(distributorid, keyword, pageindex + "", sign);
            }
        }
    }

//    public void initViewHolder() {
//        searchResultEntityListViewDataAdapter = new ListViewDataAdapter<SearchResultEntity>();
//        searchResultEntityListViewDataAdapter.setViewHolderClass(this, ReportSearchHistoryViewHolder.class);
//        ReportSearchHistoryViewHolder.setOnListItemClickListener(this);
//        lv_list.setAdapter(searchResultEntityListViewDataAdapter);
//    }

    public void createView() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                keyword = et_search.getText().toString().trim();
                pageindex = 1;
                mIsUp = false;
                String sign_ = TGmd5.getMD5(distributorid + keyword + pageindex);
                getData(distributorid, keyword, pageindex + "", sign_);
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

        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ReportSearchHistoryActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign_ = TGmd5.getMD5(distributorid + keyword + pageindex);
                getData(distributorid, keyword, pageindex + "", sign_);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign_ = TGmd5.getMD5(distributorid + keyword + pageindex);
                    getData(distributorid, keyword, pageindex + "", sign_);
                } else {
                    MyToast.show(ReportSearchHistoryActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void getData(String distributorid, String key, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("key", key);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);

        RequestTask.getInstance().getReporSearchList(ReportSearchHistoryActivity.this, maps, new OnRequestListener());
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
                    String paystr = array.get(0).toString();
                    JSONObject jsonObject_ = new JSONObject(paystr);
                    String item_ = jsonObject_.getString("Items");
                    total_page = jsonObject_.getInt("TotalPages");
                    if (mIsUp == false) {
                        searchResultEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        //
                    }
                    showOrGone();
                    JSONArray array_ = new JSONArray(item_);
                    if (array_ != null && array_.length() > 0) {
                        ll_visibilty.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject jsonObjectone = array_.getJSONObject(i);
                            String id = jsonObjectone.getString("ID");
                            String name = jsonObjectone.getString("ShopName");
                            String address = jsonObjectone.getString("Adderss");
                            SearchResultEntity searchResultEntity = new SearchResultEntity(id, name, address);
                            searchResultEntityListViewDataAdapter.append(searchResultEntity);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(ReportSearchHistoryActivity.this, jsonObject.getString("message"));
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
            showProgressDialog("加载中....");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }
    }


    /**
     * 隐藏
     */
    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * item  点击回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(SearchResultEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("index", "2");
        bundle.putString("reportId", "0");
        bundle.putString("shop_name", itemData.getName());
        bundle.putString("reportSellerId", itemData.getId());
        openActivity(ReportShopActivity.class, bundle);
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}


