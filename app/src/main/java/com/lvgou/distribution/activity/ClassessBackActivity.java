package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.storage.OnObbStateChangeListener;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
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
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.entity.OnlineSignEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.lvgou.distribution.viewholder.GridBackViewHolder;
import com.lvgou.distribution.viewholder.GridClassifyViewHolder;
import com.lvgou.distribution.viewholder.OnlineSignViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/7/27 0027
 * 课程回顾
 */
public class ClassessBackActivity extends BaseActivity implements OnListItemClickListener<ClassesBackEntity>, OnClassifyPostionClickListener<ClassifyEntity> {


    @ViewInject(R.id.ll_gridview)
    private LinearLayout ll_gridview;
    @ViewInject(R.id.grid_view)
    private GridView gridView;
    @ViewInject(R.id.tv_classify_name)
    private TextView tv_classify_name;
    @ViewInject(R.id.rl_biaotilan)
    private RelativeLayout rl_biaotilan;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout ll_none;

    private ListView lv_list;
    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String tag = "0";

    private String name = "";
    private String teacher_id = "";


    private ListViewDataAdapter<ClassesBackEntity> classesBackEntityListViewDataAdapter;
    private ListViewDataAdapter<ClassifyEntity> classifyGridViewEntityListViewDataAdapter;// 一级分类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_back);
        ViewUtils.inject(this);

        lv_list = pullToRefreshListView.getRefreshableView();
        distributorid = PreferenceHelper.readString(ClassessBackActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        teacher_id = getTextFromBundle("id");
//        if (teacher_id.equals("0")) {
//            tv_title.setText("课程回顾");
//
//        } else {
//            pageindex = 1;
//            name = getTextFromBundle("name");
//            tv_title.setText(name + "的课程");
//            ll_gridview.setVisibility(View.GONE);
//            rl_biaotilan.setVisibility(View.GONE);
//        }

        String sign1 = TGmd5.getMD5(teacher_id + tag + pageindex);
        String sign = TGmd5.getMD5(distributorid);
        if (checkNet()) {
            getData(teacher_id, tag, pageindex + "", sign1);
            if (distributorid.equals("") || distributorid.equals("null")) {
                openActivity(LoginActivity.class);
                finish();
            } else {
                getHotData(distributorid, sign);
            }
        }
        initCreateView();
        initViewHolder();

        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 2) {
                    ll_gridview.setVisibility(View.GONE);
                    rl_biaotilan.setVisibility(View.VISIBLE);
                    tv_classify_name.setText(Constants.SELECTE_POSITION06);
                }
            }
        });
    }

    @OnClick({R.id.rl_biaotilan})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_biaotilan:
                ll_gridview.setVisibility(View.VISIBLE);
                rl_biaotilan.setVisibility(View.GONE);
                lv_list.setSelection(0);
                break;
        }
    }

    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ClassessBackActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(teacher_id + tag + pageindex);
                getData(teacher_id, tag, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(teacher_id + tag + pageindex);
                    getData(teacher_id, tag, pageindex + "", sign);
                } else {
                    MyToast.show(ClassessBackActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void initViewHolder() {
        classesBackEntityListViewDataAdapter = new ListViewDataAdapter<ClassesBackEntity>();
        classesBackEntityListViewDataAdapter.setViewHolderClass(this, ClassesBackViewHolder.class);
        ClassesBackViewHolder.setOnListItemClickListener(this);
        lv_list.setAdapter(classesBackEntityListViewDataAdapter);

        classifyGridViewEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyGridViewEntityListViewDataAdapter.setViewHolderClass(this, GridBackViewHolder.class);
        GridBackViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);
        gridView.setAdapter(classifyGridViewEntityListViewDataAdapter);
    }

    public void getHotData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);

        RequestTask.getInstance().getHotTag(ClassessBackActivity.this, maps, new OnHotRequestListener());
    }


    private class OnHotRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(ClassessBackActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
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
                    JSONArray jsonArray = new JSONArray(data);
                    ClassifyEntity classifyEntity1 = new ClassifyEntity("0", "全部", "");
                    classifyGridViewEntityListViewDataAdapter.append(classifyEntity1);
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject_ = jsonArray.getJSONObject(i);
                            String id = jsonObject_.getString("ID");
                            String name = jsonObject_.getString("String1");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id, name, "");
                            classifyGridViewEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取列表数据
     *
     * @param teacherId
     * @param tag
     * @param pageindex
     * @param sign
     */
    public void getData(String teacherId, String tag, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("teacherId", teacherId);
        maps.put("tag", tag);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);

        RequestTask.getInstance().getClassReview(ClassessBackActivity.this, maps, new OnClassRequestListener());
    }

    private class OnClassRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(ClassessBackActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
            pullToRefreshListView.onRefreshComplete();
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
                    String datas = array.get(0).toString();
                    JSONObject json_data = new JSONObject(datas);
                    total_page = json_data.getInt("TotalPages");
                    String items_ = json_data.getString("Items");
                    JSONArray array_items = new JSONArray(items_);
                    if (mIsUp == false) {
                        classesBackEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {

                    }
                    if (array_items != null && array_items.length() > 0) {
                        showRoGone();
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject json_ = array_items.getJSONObject(i);
                            String id = json_.getString("ID");
                            String url = json_.getString("Banner2");
                            String name = json_.getString("TeacherName");
                            String title = json_.getString("Theme");
                            String content = json_.getString("ThemeInfo");

//                            ClassesBackEntity classesBackEntity = new ClassesBackEntity(id, url, name, title, content);
//                            classesBackEntityListViewDataAdapter.append(classesBackEntity);
                        }
                    } else {
                        showRoGone();
                        ll_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(ClassessBackActivity.this, jsonObject.getString("message"));
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
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        ll_none.setVisibility(View.GONE);
    }


    /**
     * 列表点击回调
     */
    @Override
    public void onListItemClick(ClassesBackEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        openActivity(FamousTeacherDetialActivity.class, bundle);

    }

    /**
     * 类别点击回调
     */
    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        classifyGridViewEntityListViewDataAdapter.notifyDataSetChanged();
        if (itemData.getName().equals("全部")) {
            tag = "0";
        } else {
            tag = itemData.getId();
        }
        tv_classify_name.setText(itemData.getName());
        ll_gridview.setVisibility(View.GONE);
        rl_biaotilan.setVisibility(View.VISIBLE);
        pageindex = 1;
        mIsUp = false;
        String sign2 = TGmd5.getMD5(teacher_id + tag + pageindex);
        getData(teacher_id, tag, pageindex + "", sign2);

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

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
