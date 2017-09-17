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
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.AllClassPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.AllClassesView;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/9.
 * 个人课程列表
 */
public class PersonalClassesActivity extends BaseActivity implements OnListItemClickListener<ClassesBackEntity>, AllClassesView {


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


    private String distributorid = "";
    private ListView listView;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private AllClassPresenter allClassPresenter;

    private ListViewDataAdapter<ClassesBackEntity> classesBackEntityListViewDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_list);
        ViewUtils.inject(this);
        tv_title.setText("我的课程");

        listView = pullToRefreshListView.getRefreshableView();

        distributorid = PreferenceHelper.readString(PersonalClassesActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        allClassPresenter = new AllClassPresenter(this);

        initCreateView();
        initViewHolder();

        if (checkNet()) {
            showLoadingProgressDialog(PersonalClassesActivity.this, "");
            String sign = TGmd5.getMD5(distributorid + pageindex);
            allClassPresenter.getMyClass(distributorid, pageindex + "", sign);
        }

    }


    public void initViewHolder() {
        classesBackEntityListViewDataAdapter = new ListViewDataAdapter<ClassesBackEntity>();
        classesBackEntityListViewDataAdapter.setViewHolderClass(this, ClassesBackViewHolder.class);
        ClassesBackViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(classesBackEntityListViewDataAdapter);

    }


    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(PersonalClassesActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                allClassPresenter.getMyClass(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    allClassPresenter.getMyClass(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(PersonalClassesActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
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
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 3:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_one = new JSONObject(data);
                        total_page = jsonObject_one.getInt("DataPageCount");
                        if (mIsUp == false) {
                            classesBackEntityListViewDataAdapter.removeAll();
                        } else {
                        }

                        String items = jsonObject_one.getString("Data");
                        JSONArray array = new JSONArray(items);
                        if (array != null && array.length() > 0) {
                            showRoGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json_ = array.getJSONObject(i);
                                String id_ = json_.getString("ID");
                                String img_path = json_.getString("Banner2");
                                String name = json_.getString("TeacherName");
                                String title = json_.getString("Theme");
                                String sign_num = json_.getString("People_Apply");
                                String tuanbi = json_.getString("BMTuanBi");
                                ClassesBackEntity classesBackEntity = new ClassesBackEntity(id_, tuanbi, sign_num, title, name, img_path);
                                classesBackEntityListViewDataAdapter.append(classesBackEntity);
                            }
                        } else {
                            showRoGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showRoGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 3:
                MyToast.show(PersonalClassesActivity.this, "请求失败");
                break;
        }
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }


    /**
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(ClassesBackEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        openActivity(FamousTeacherDetialActivity.class, bundle);
    }


    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
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


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
