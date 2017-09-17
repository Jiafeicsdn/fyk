package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MyProfitActivity;
import com.lvgou.distribution.adapter.ExtensionProfitAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ProfitEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snow on 2016/4/27
 * 推广收益
 */
public class ProfitFragment extends Fragment implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;
    private RelativeLayout rl_withdrawals;
    private TextView tv_withdrawals_money;
    private ListView listView;
    private TextView tv_ketixian;
    private List<ProfitEntity> profitEntityLists;

    private ExtensionProfitAdapter extensionProfitAdapter;
    private String distributorid = "";
    private String sign = "";

    private String tixian_price;
    private String name;
    private String account_name;
    private String account_num;
    private Dialog dialog;

    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载

    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit, container, false);
        initView(view);
        listView = pullToRefreshListView.getRefreshableView();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (checkNet(getActivity())) {
            sign = TGmd5.getMD5(distributorid + "1");
            getData(distributorid, "1", sign);
        }

        initCreateView();

        return view;
    }

    public void initView(View view) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        rl_withdrawals = (RelativeLayout) view.findViewById(R.id.rl_withdrawals);
        tv_withdrawals_money = (TextView) view.findViewById(R.id.tv_withdrawals_money);
        tv_ketixian = (TextView) view.findViewById(R.id.tv_ketixian);
        rl_withdrawals.setOnClickListener(this);
        profitEntityLists = new ArrayList<ProfitEntity>();

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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FragmentProfit");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FragmentProfit");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_withdrawals:
//                if (!tixian_price.equals("0")) {
//                    showDialog();
//                }
                Intent intent = new Intent(getActivity(), MyProfitActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    /**
     * 有无数据页面显示
     */
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

        RequestTask.getInstance().getProfit(getActivity(), maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
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
                    JSONArray array_ = new JSONArray(result);
                    String data = array_.get(0).toString();
                    JSONObject json_data = new JSONObject(data);
                    total_page = json_data.getInt("TotalPages");
                    String items = json_data.getString("Items");
                    if (mIsUp == false) {
                        profitEntityLists.clear();
                    } else if (mIsUp == true) {
                        // 上拉加载，不清空 listViewDataAdapter
                    }
                    showOrGone();
                    JSONArray array_items = new JSONArray(items);
                    if (array_items != null && array_items.length() > 0) {
                        ll_visibilty.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject json_items = array_items.getJSONObject(i);
                            String id = json_items.getString("ID");
                            String tiem_ = json_items.getString("CreateTime");
                            String supply_name = json_items.getString("SupplyName");
                            String weixin = json_items.getString("WeiXin");
                            String user_type = json_items.getString("Sate_TG");
                            String state = json_items.getString("State_Pay");
                            String price = json_items.getString("Price_Distributor");
                            ProfitEntity profitEntity = new ProfitEntity(id, state, price, user_type, weixin, supply_name, tiem_);
                            profitEntityLists.add(profitEntity);
                        }
                        extensionProfitAdapter = new ExtensionProfitAdapter(getActivity(), profitEntityLists);
                        listView.setAdapter(extensionProfitAdapter);
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                    // 提现数据解析
                    tixian_price = array_.get(1).toString();
                    account_name = array_.get(2).toString();
                    account_num = array_.get(3).toString();
                    name = array_.get(4).toString();
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    if (!tixian_price.equals("0")) {
                        tv_ketixian.setVisibility(View.VISIBLE);
                        tv_withdrawals_money.setText("¥" + decimalFormat.format(Float.parseFloat(tixian_price)));
                        tv_withdrawals_money.setTextColor(getActivity().getResources().getColor(R.color.bg_code_number));
//                        rl_withdrawals.setBackgroundResource(R.color.bg_code_number);
                    } else {
                        tv_withdrawals_money.setText("无可提现金额");
                        tv_ketixian.setVisibility(View.INVISIBLE);
                        tv_withdrawals_money.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
//                        rl_withdrawals.setBackgroundResource(R.color.bg_main_gray);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提现操作
     *
     * @param distributorid
     * @param price
     * @param cardno
     * @param sign
     */
    public void withdraw(String distributorid, String price, String cardno, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("price", price);
        maps.put("cardno", cardno);
        maps.put("sign", sign);

        RequestTask.getInstance().doTixian(getActivity(), maps, new OnTixianRequestListener());
    }

    public class OnTixianRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "提现成功");
                } else if (status.equals("0")) {
                    MyToast.show(getActivity(), json.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }
    }

    public void showDialog() {
        dialog = new Dialog(getActivity(),
                R.style.Mydialog);
        View view1 = View.inflate(getActivity(),
                R.layout.dialog_withwards_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_money = (TextView) view1.findViewById(R.id.tv_money);
        TextView tv_name = (TextView) view1.findViewById(R.id.tv_name);
        TextView tv_account = (TextView) view1.findViewById(R.id.tv_account);
        TextView tv_account_num = (TextView) view1.findViewById(R.id.tv_account_num);
        tv_money.setText("¥" + tixian_price);
        tv_name.setText(name);
        tv_account.setText(account_name);
        tv_account_num.setText(account_num);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5(distributorid + tixian_price + account_num);
                withdraw(distributorid, tixian_price, account_num, sign);
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view1);
        dialog.show();
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
