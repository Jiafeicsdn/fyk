package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.TuanbiDetialAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TaskDetialEntity;
import com.lvgou.distribution.fragment.FragmentTuanbiDetial;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
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
 * Created by Administrator on 2017/4/24.
 */

public class TuanbiDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_title_list);
        findViewById(R.id.title_line).setVisibility(View.VISIBLE);
        initView();
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_list = pull_refresh_list.getRefreshableView();
        list = new ArrayList<TaskDetialEntity>();
        initCreateView();
        initAdapter();

        String sign = TGmd5.getMD5(distributorid + pageindex);
        getData(distributorid, pageindex + "", sign);
    }


    private LinearLayout ll_visibilty;
    private PullToRefreshListView pull_refresh_list;
    private RelativeLayout rl_none;
    private ListView lv_list;

    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private TuanbiDetialAdapter adapter;
    private List<TaskDetialEntity> list;
    private RelativeLayout rl_back;
    private TextView tv_title;

    private void initView() {
        ll_visibilty = (LinearLayout) findViewById(R.id.ll_visibilty);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("团币明细");
        pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        rl_none = (RelativeLayout) findViewById(R.id.rl_none);
    }

    public void initAdapter() {
        adapter = new TuanbiDetialAdapter(this);
        adapter.setOrderEntityLists(new ArrayList<TaskDetialEntity>());
        lv_list.setAdapter(adapter);
    }


    public void initCreateView() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(TuanbiDetailActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
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
                    MyToast.show(TuanbiDetailActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
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
                    JSONArray array_ = new JSONArray(data);
                    if (pageindex == 1) {
                        adapter.getAllData().clear();
                    }
                    List<TaskDetialEntity> taskDetialEntities = new ArrayList<TaskDetialEntity>();
                    if (array_ != null && array_.length() > 0) {
                        showOrGone();
                        ll_visibilty.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject json_item = array_.getJSONObject(i);
                            String id = json_item.getString("ID");
                            String name = json_item.getString("Intro");
                            String time = json_item.getString("CreateTime");
                            String status_ = json_item.getString("IsOut");
                            String poiont = json_item.getString("TuanBi");
                            TaskDetialEntity taskDetialEntity = new TaskDetialEntity(id, name, time, poiont, status_);
                            taskDetialEntities.add(taskDetialEntity);
                        }
                        adapter.setOrderEntityLists(taskDetialEntities);
                        adapter.notifyDataSetChanged();
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
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pull_refresh_list.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
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
