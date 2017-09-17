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
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CarryGroupRecordEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.CarryGroupRecordPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CarryGroupRecordViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/14.
 * 带团记录
 */
public class CarryGroupRecordActivity extends BaseActivity implements GroupView, OnListItemClickListener<CarryGroupRecordEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;


    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<CarryGroupRecordEntity> carryGroupRecordEntityListViewDataAdapter;

    private CarryGroupRecordPresenter carryGroupRecordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        ViewUtils.inject(this);
        tv_title.setText("接团记录");

        distributorid = getTextFromBundle("distributorid");

        carryGroupRecordPresenter = new CarryGroupRecordPresenter(this);

        listView = pullToRefreshListView.getRefreshableView();

        initViewHolder();
        initCreateView();
        showLoadingProgressDialog(CarryGroupRecordActivity.this, "");
        String sign = TGmd5.getMD5(distributorid + pageindex);
        carryGroupRecordPresenter.getCarryGroupRecord(distributorid, pageindex + "", sign);

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
        carryGroupRecordEntityListViewDataAdapter = new ListViewDataAdapter<CarryGroupRecordEntity>();
        carryGroupRecordEntityListViewDataAdapter.setViewHolderClass(this, CarryGroupRecordViewHolder.class);
        CarryGroupRecordViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(carryGroupRecordEntityListViewDataAdapter);
    }


    /**
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CarryGroupRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                carryGroupRecordPresenter.getCarryGroupRecord(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    carryGroupRecordPresenter.getCarryGroupRecord(distributorid, pageindex + "", sign);

                } else {
                    MyToast.show(CarryGroupRecordActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
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
                        String data = array.get(0).toString();

                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("TotalPages");
                        String items = jsonObject_data.getString("Items");
                        if (mIsUp == false) {
                            carryGroupRecordEntityListViewDataAdapter.removeAll();
                        } else {

                        }
                        JSONArray array_items = new JSONArray(items);
                        if (array_items != null && array_items.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_items.length(); i++) {
                                JSONObject jsonObject_items = array_items.getJSONObject(i);
                                String id = jsonObject_items.getString("SeekID");
                                String title = jsonObject_items.getString("Title");
                                String satr = jsonObject_items.getString("Grade");
                                String content = jsonObject_items.getString("Comment");
                                String time = jsonObject_items.getString("GradeTime");
                                CarryGroupRecordEntity carryGroupRecordEntity = new CarryGroupRecordEntity(id, title, satr, time, content);
                                carryGroupRecordEntityListViewDataAdapter.append(carryGroupRecordEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(CarryGroupRecordActivity.this, "请求失败");
                break;
        }
    }


    @Override
    public void closeProgress(){
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    /**
     * item 点击回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(CarryGroupRecordEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("seekid", itemData.getId());
        bundle.putString("groupType", "2");
        openActivity(PublishGroupDetialActivity.class, bundle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carryGroupRecordPresenter.attach(this);
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        carryGroupRecordPresenter.dettach();

    }

    @Override
    protected void onPause() {
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
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

}
