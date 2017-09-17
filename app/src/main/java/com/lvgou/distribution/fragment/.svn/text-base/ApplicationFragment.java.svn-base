package com.lvgou.distribution.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BindCodeActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ApplicationEntity;
import com.lvgou.distribution.inter.OnApplicationClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ApplicationViewHolder;
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
 * Created by Snow on 2016/4/27
 * 选择应用
 */
public class ApplicationFragment extends Fragment implements OnApplicationClickListener<ApplicationEntity> {

    private LinearLayout ll_visibility;
    private PullToRefreshListView pullToRefreshListView;
    private RelativeLayout rl_none;
    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private ListViewDataAdapter<ApplicationEntity> applicationEntityListViewDataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        initView(view);
        listView = pullToRefreshListView.getRefreshableView();
        initViewHolder();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        if (checkNet(getActivity())) {
            String sign = TGmd5.getMD5(distributorid + pageindex);
            getData(distributorid, pageindex + "", sign);
        }
        initCreateView();
        return view;
    }

    public void initView(View view) {
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
    }

    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
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
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void initViewHolder() {
        applicationEntityListViewDataAdapter = new ListViewDataAdapter<ApplicationEntity>();
        applicationEntityListViewDataAdapter.setViewHolderClass(this, ApplicationViewHolder.class);
        ApplicationViewHolder.setOnApplicationClickListener(this);
        listView.setAdapter(applicationEntityListViewDataAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("FragmentProfit");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("FragmentProfit");
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
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
        RequestTask.getInstance().getApplicationList(getActivity(), maps, new OnRequestListener());
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
                    JSONArray jsonArray = new JSONArray(result);
                    String data = jsonArray.get(0).toString();
                    JSONObject json_data = new JSONObject(data);
                    total_page = json_data.getInt("TotalPages");
                    String items = json_data.getString("Items");
                    if (mIsUp == false) {
                        applicationEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        // 上拉加载，不清空 listViewDataAdapter

                    }
                    showOrGone();
                    JSONArray array_items = new JSONArray(items);
                    if (array_items != null && array_items.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject json = array_items.getJSONObject(i);
                            String id = json.getString("ID");
                            String supplyName = json.getString("SupplyName");
                            String img_path = json.getString("Logo");
                            String state = json.getString("State");
                            String downUrl = json.getString("DownUrl");
                            String Star = json.getString("Star");
                            String pregress = json.getString("ParentID");
                            String total = json.getString("Price_Total");
                            String content = json.getString("SupplyType");
                            String price = json.getString("Price_Distributor");
                            ApplicationEntity applicationEntity = new ApplicationEntity(id, downUrl, state, price, content, supplyName, img_path, total, Star, pregress);
                            applicationEntityListViewDataAdapter.append(applicationEntity);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
        }
    }

    public void goSupply(String distributorid, String supplyId, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("supplyId", supplyId);
        maps.put("sign", sign);

        RequestTask.getInstance().doSupply(getActivity(), maps, new OnSupplyRequestListener());

    }

    private class OnSupplyRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "申请成功");
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getData(distributorid, pageindex + "", sign);
                } else if (status.equals("0")) {
                    MyToast.show(getActivity(), jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onApplicationClickListener(ApplicationEntity itemdata, TextView textView, int num) {
        Bundle bundle = new Bundle();
        switch (num) {
            case 1:
                bundle.putString("supplyId", itemdata.getId());
                bundle.putString("index", "1");
                Intent intentone = new Intent(getActivity(), BindCodeActivity.class);
                intentone.putExtras(bundle);
                getActivity().startActivity(intentone);
                break;
            case 2:
                if (itemdata.getState().equals("1") && itemdata.getDownload().equals("2")) {
                    bundle.putString("supplyId", itemdata.getId());
                    bundle.putString("index", "2");
                    Intent intent = new Intent(getActivity(), BindCodeActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else if (itemdata.getState().equals("2")) {
                    MyToast.show(getActivity(), "已结束");
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("0")) {
                    String sign_ = TGmd5.getMD5(distributorid + itemdata.getId());
                    goSupply(distributorid, itemdata.getId(), sign_);
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("1")) {
                    MyToast.show(getActivity(), "待审核");
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("3")) {
                    MyToast.show(getActivity(), "审核失败请联系客服");
                }
                break;
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
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

