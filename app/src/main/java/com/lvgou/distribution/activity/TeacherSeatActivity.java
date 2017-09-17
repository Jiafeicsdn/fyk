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
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TeacherSeatEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.TeacherSeatViewHolder;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/7/27 0027.
 * 客座讲师
 */
public class TeacherSeatActivity extends BaseActivity implements OnClassifyPostionClickListener<TeacherSeatEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout ll_none;

    private ListView lv_list;
    private String distributorid = "";
    private int pageindex = 1;
    private String sign = "";
    boolean mIsUp = false;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<TeacherSeatEntity> teacherSeatEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_seat);
        ViewUtils.inject(this);
        tv_title.setText("客座讲师");
        distributorid = PreferenceHelper.readString(TeacherSeatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_list = pullToRefreshListView.getRefreshableView();

//        initViewHolder();
        String sign = TGmd5.getMD5(pageindex + "");
        if (checkNet()) {
            getData(pageindex + "", sign);
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

    /**
     * main 代码抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(TeacherSeatActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(pageindex + "");
                getData(pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(pageindex + "");
                    getData(pageindex + "", sign);
                } else {
                    MyToast.show(TeacherSeatActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

//    public void initViewHolder() {
//        teacherSeatEntityListViewDataAdapter = new ListViewDataAdapter<TeacherSeatEntity>();
//        teacherSeatEntityListViewDataAdapter.setViewHolderClass(this, TeacherSeatViewHolder.class);
//        TeacherSeatViewHolder.setOnClassifyPostionClickListener(this);
//        lv_list.setAdapter(teacherSeatEntityListViewDataAdapter);
//    }

    public void getData(String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getTeacherSeat(TeacherSeatActivity.this, maps, new OnRequestListener());
    }


    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(TeacherSeatActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
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
                String satus = jsonObject.getString("status");
                if (satus.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String data = array.get(0).toString();
                    JSONObject jsonData = new JSONObject(data);
                    total_page = jsonData.getInt("TotalPages");
                    String items_ = jsonData.getString("Items");
                    if (mIsUp == false) {
                        teacherSeatEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                    }
                    JSONArray array_one = new JSONArray(items_);
                    if (array_one != null && array_one.length() > 0) {
                        showOrGone();
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_one.length(); i++) {
                            JSONObject jsonObject_ = array_one.getJSONObject(i);
                            String id = jsonObject_.getString("LinkUrl");
                            String imgpath = jsonObject_.getString("PicUrl");
                            String name = jsonObject_.getString("Title");
                            String num = jsonObject_.getString("Other");
                            String content = jsonObject_.getString("Intro");
//                            TeacherSeatEntity teacherSeatEntity = new TeacherSeatEntity(id, imgpath, name, num, content);
//                            teacherSeatEntityListViewDataAdapter.append(teacherSeatEntity);
                        }
                    } else {
                        showOrGone();
                        ll_none.setVisibility(View.VISIBLE);
                    }
                } else if (satus.equals("0")) {
                    MyToast.show(TeacherSeatActivity.this, jsonObject.getString("message"));
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
     * 隐藏
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        ll_none.setVisibility(View.GONE);
    }

    /**
     * 点击事件处理
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(TeacherSeatEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 2:// 往期回顾
                Constants.SELECTE_POSITION06 = "全部";
                bundle.putString("id", itemData.getId());
                bundle.putString("name", itemData.getName());
                openActivity(ClassessBackActivity.class, bundle);
                break;
        }
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
