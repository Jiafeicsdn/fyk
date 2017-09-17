package com.lvgou.distribution.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TuanYuanEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshGridView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.viewholder.FamousTuanYuanViewHolde;
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
 * Created by Snow on 2016/5/16 0016.
 * 我的团员
 */
public class TuanYuanActivity extends BaseActivity implements OnListItemClickListener<TuanYuanEntity>, DistributorHomeView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.rl_search)
    private RelativeLayout rl_search;
    @ViewInject(R.id.rl_delete)
    private RelativeLayout rl_detlete;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.pull_refresh_grid)
    private PullToRefreshGridView pullToRefreshGridView;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    private GridView gridView;

    private ListViewDataAdapter<TuanYuanEntity> tuanYuanEntityListViewDataAdapter;

    private int pageIndex = 1;
    boolean mIsUp;// 是否上拉加载

    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private String id = "";
    private String keywords = "";
    private DistributorHomePresenter distributorHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuanyuan);
        distributorHomePresenter = new DistributorHomePresenter(this);
        ViewUtils.inject(this);
        tv_title.setText("报名人员");
        id = getTextFromBundle("id");
        gridView = pullToRefreshGridView.getRefreshableView();

        initViewHolder();
        if (checkNet()) {
            String sign = TGmd5.getMD5(id + "1");
            getData(id, "1", keywords, sign);
        }

        initCreateView();

    }

    @OnClick({R.id.rl_back, R.id.rl_delete, R.id.rl_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                rl_all.setVisibility(View.GONE);
                rl_search.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_delete:
                rl_all.setVisibility(View.VISIBLE);
                rl_search.setVisibility(View.GONE);
                break;

        }
    }

    public void initViewHolder() {
        tuanYuanEntityListViewDataAdapter = new ListViewDataAdapter<TuanYuanEntity>();
        tuanYuanEntityListViewDataAdapter.setViewHolderClass(this, FamousTuanYuanViewHolde.class);
        FamousTuanYuanViewHolde.setTuanYuanEntityOnListItemClickListener(this);
        gridView.setAdapter(tuanYuanEntityListViewDataAdapter);
    }

    public void showOrNone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    public void initCreateView() {
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = false;
                pageIndex = 1;
                String sign = TGmd5.getMD5(id + pageIndex);
                getData(id, pageIndex + "", keywords, sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = true;
                if (pageIndex < total_page) {
                    pageIndex += 1;
                    String sign = TGmd5.getMD5(id + pageIndex);
                    getData(id, pageIndex + "", keywords, sign);
                } else {
                    MyToast.show(TuanYuanActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });

        //搜索功能
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    keywords = et_search.getText().toString();
                    pageIndex = 1;
                    String sign = TGmd5.getMD5(id + pageIndex + keywords);
                    getData(id, pageIndex + "", keywords, sign);
                    rl_all.setVisibility(View.VISIBLE);
                    rl_search.setVisibility(View.GONE);
                    // 隐藏软键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(TuanYuanActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 获取数据
     *
     * @param id
     * @param pageindex
     * @param keyword
     * @param sign
     */
    public void getData(String id, String pageindex, String keyword, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("id", id);
        maps.put("pageindex", pageindex);
        maps.put("keyword", keyword);
        maps.put("sign", sign);
        RequestTask.getInstance().doFamousSeekApplyers(TuanYuanActivity.this, maps, new OnRequestListener());
    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
            try {
                JSONObject jsb = new JSONObject(respanse);
                JSONArray jsa = new JSONArray(jsb.getString("result"));
                JSONObject distributorModeljsa = jsa.getJSONObject(0);
                if (distributorModeljsa.get("UserType").toString().equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(TuanYuanActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                    startActivity(intent1);
                } else {
                    //普通导游
                    Intent intent1 = new Intent(TuanYuanActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                    startActivity(intent1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void OnDistributorHomeFialCallBack (String state, String respanse){
            closeLoadingProgressDialog();
        }

        @Override
        public void closeDistributorHomeProgress () {

        }


        private class OnRequestListener extends OnRequestListenerAdapter<Object> {
            @Override
            public void onRequestPrepare() {
                super.onRequestPrepare();
                showLoadingProgressDialog(TuanYuanActivity.this, "");
            }

            @Override
            public void onRequestFinish(String data) {
                super.onRequestFinish(data);
                closeLoadingProgressDialog();
                pullToRefreshGridView.onRefreshComplete();
            }

            @Override
            public void onRequestFailed(String error) {
                super.onRequestFailed(error);
                closeLoadingProgressDialog();
                pullToRefreshGridView.onRefreshComplete();
            }

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
                        JSONObject json_ = new JSONObject(data);
                        total_page = json_.getInt("TotalPages");
                        String items = json_.getString("Items");
                        JSONArray jsonArray = new JSONArray(items);
                        if (mIsUp == false) {
                            tuanYuanEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 不做处理
                        }
                        if (jsonArray != null && jsonArray.length() > 0) {
                            showOrNone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject_ = jsonArray.getJSONObject(i);
                                String id = jsonObject_.getString("DistributorID");
                                String DistributorName = jsonObject_.getString("DistributorName");
//                            String[] str = DistributorName.split("▓");
//                            String name = str[0].toString();
                                String img_path = jsonObject_.getString("Banner2");
                                TuanYuanEntity tuanYuanEntity = new TuanYuanEntity(id, DistributorName, img_path);
                                tuanYuanEntityListViewDataAdapter.append(tuanYuanEntity);
                            }
                        } else {
                            showOrNone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else if (status.equals("0")) {
                        MyToast.show(TuanYuanActivity.this, jsonObject.getString("message"));
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


        @Override
        public void onListItemClick (TuanYuanEntity itemData){
       /* Bundle bundle = new Bundle();
        bundle.putInt("distributorid", Integer.parseInt(itemData.getId()));
        openActivity(UserPersonalCenterActivity.class, bundle);*/
            String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
            String sign = TGmd5.getMD5("" + distributorid + itemData.getId());
            showLoadingProgressDialog(this, "");
            distributorHomePresenter.distributorHome(distributorid, itemData.getId(), sign);
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
                pullToRefreshGridView.onRefreshComplete();
                super.onPostExecute(aVoid);
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
}
