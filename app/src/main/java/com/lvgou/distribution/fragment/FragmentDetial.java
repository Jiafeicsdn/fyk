package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.DialogInterface;
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

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.LoginActivity;
import com.lvgou.distribution.adapter.ExchangeRecordAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TaskDetialEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
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
 * Created by Snow on 2016/3/29
 */
public class FragmentDetial extends Fragment {

    private CustomProgressDialog progressDialog;
    private ListView lv_list;
    private PullToRefreshListView pullToRefreshListView;
    private LinearLayout ll_visibility;
    private RelativeLayout rl_none;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    //    private ListViewDataAdapter<TaskDetialEntity> listViewDataAdapter;
    private List<TaskDetialEntity> list;
    private ExchangeRecordAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exchange_record, container, false);

        initView(view);

        if (checkNet(getActivity())) {
            String sign = TGmd5.getMD5(distributorid + pageindex + "");
            getData(distributorid, pageindex + "", sign);
        }

        initCreateView();
        return view;
    }

    public void initView(View view) {
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        lv_list = pullToRefreshListView.getRefreshableView();
    }

    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
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

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

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

        RequestTask.getInstance().getExchangeList(getActivity(), maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    list = new ArrayList<TaskDetialEntity>();
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String items_ = array.get(0).toString();
                    JSONObject json_ = new JSONObject(items_);
                    total_page = json_.getInt("TotalPages");
                    String data = json_.getString("Items");
                    if (mIsUp == false) {
                        list.clear();
                    } else if (mIsUp == true) {
                        // 上拉加载，不清空 listViewDataAdapter
                    }
                    showOrGone();
                    JSONArray array_ = new JSONArray(data);
                    if (array_ != null && array_.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject json_item = array_.getJSONObject(i);
                            String id = json_item.getString("ID");
                            String name = json_item.getString("CouponName");
                            String time = json_item.getString("CreateTime");
                            String poiont = json_item.getString("TuanBi");
                            TaskDetialEntity taskDetialEntity = new TaskDetialEntity(id, name, time, poiont, "");
                            list.add(taskDetialEntity);
                        }
                        adapter = new ExchangeRecordAdapter(getActivity(), list);
                        lv_list.setAdapter(adapter);
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(getActivity(), jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }
    }


    /**
     * 销毁进度条对话框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度条对话框
     */
    public void showProgressDialog() {
        showProgressDialog("加载中...", true, null);
    }

    /**
     * 显示进度条对话框
     *
     * @param message
     * @param cancel
     */
    public void showProgressDialog(String message, boolean cancel, DialogInterface.OnCancelListener cancelListener) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancel);
        progressDialog.setCanceledOnTouchOutside(false);
        if (cancelListener != null) {
            progressDialog.setOnCancelListener(cancelListener);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
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
