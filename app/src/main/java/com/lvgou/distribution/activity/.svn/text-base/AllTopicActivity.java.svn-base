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
import com.lvgou.distribution.entity.AllTopicEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.AllTopicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.AllTopicViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/19.
 * 全部话题
 */
public class AllTopicActivity extends BaseActivity implements GroupView, OnListItemClickListener<AllTopicEntity> {


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
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<AllTopicEntity> allTopicEntityListViewDataAdapter;

    private AllTopicPresenter allTopicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        ViewUtils.inject(this);
        tv_title.setText("更多话题");

        allTopicPresenter = new AllTopicPresenter(this);

        listView = pullToRefreshListView.getRefreshableView();


        initViewHolder();

        initCreateView();
        if (checkNet()) {
            showLoadingProgressDialog(AllTopicActivity.this, "");
            String sign = TGmd5.getMD5(pageindex + "");
            allTopicPresenter.getToppicList(pageindex + "", sign);
        }
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
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(AllTopicActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(pageindex + "");
                allTopicPresenter.getToppicList(pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(pageindex + "");
                    allTopicPresenter.getToppicList(pageindex + "", sign);
                } else {
                    MyToast.show(AllTopicActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void initViewHolder() {
        allTopicEntityListViewDataAdapter = new ListViewDataAdapter<AllTopicEntity>();
        allTopicEntityListViewDataAdapter.setViewHolderClass(this, AllTopicViewHolder.class);
        AllTopicViewHolder.setAllTopicEntityOnListItemClickListener(this);
        listView.setAdapter(allTopicEntityListViewDataAdapter);

    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("DataPageCount");
                        String items = jsonObject_data.getString("Data");
                        if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                            allTopicEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }

                        JSONArray array_items = new JSONArray(items);
                        if (array_items != null && array_items.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_items.length(); i++) {
                                JSONObject jsonObject_ = array_items.getJSONObject(i);
                                String id = jsonObject_.getString("ID");
                                String img_path = jsonObject_.getString("PicUrl");
                                String title = jsonObject_.getString("Title");
                                String readnum = jsonObject_.getString("Hits");
                                String commentnum = jsonObject_.getString("CommentCount");
                                AllTopicEntity allTopicEntity = new AllTopicEntity(img_path, id, title, readnum, commentnum);
                                allTopicEntityListViewDataAdapter.append(allTopicEntity);
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

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(AllTopicActivity.this, "请求失败");
                break;
            case 2:
                MyToast.show(AllTopicActivity.this, "请求失败");
                break;
        }
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {
showLoadingProgressDialog(this,"");
    }

    /**
     * item 点击回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(AllTopicEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("topicId", itemData.getId());
        openActivity(TopicDetailsActivity.class, bundle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        allTopicPresenter.attach(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allTopicPresenter.dettach();
        MobclickAgent.onPause(this);
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
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

