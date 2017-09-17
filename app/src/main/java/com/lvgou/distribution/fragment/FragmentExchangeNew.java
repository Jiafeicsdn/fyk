package com.lvgou.distribution.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.lvgou.distribution.activity.ExchangeDetialActivity;
import com.lvgou.distribution.activity.ExchangePresnetActivity;
import com.lvgou.distribution.bean.ExchangeBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.entity.ExchangeEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.lvgou.distribution.viewholder.ExchangeViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/22.
 * 礼品兑换
 */
public class FragmentExchangeNew extends Fragment implements OnClassifyPostionClickListener<ExchangeEntity>, GroupView {

    private ListView listView;
    private PullToRefreshListView pullToRefreshListView;
    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;

    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private UpdateReceiver updateReceiver;
    private IntentFilter intentFilter;
    private ListViewDataAdapter<ExchangeEntity> exchangeEntityListViewDataAdapter;

    private TuanbiExchangePresenter tuanbiExchangePresenter;

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mIsUp = false;
            pageindex = 1;
            String sign = TGmd5.getMD5(distributorid + pageindex);
            tuanbiExchangePresenter.getExchangeList(distributorid, pageindex + "", sign);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_pullview, null);

        //注册广播
        updateReceiver = new UpdateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.update");
        getActivity().registerReceiver(updateReceiver, intentFilter);

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        listView = pullToRefreshListView.getRefreshableView();


        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);

        initViewHolder();
        initCreateView();

        String sign = TGmd5.getMD5(distributorid + pageindex);
        tuanbiExchangePresenter.getExchangeList(distributorid, pageindex + "", sign);

        return view;
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
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                tuanbiExchangePresenter.getExchangeList(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    tuanbiExchangePresenter.getExchangeList(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void initViewHolder() {
        exchangeEntityListViewDataAdapter = new ListViewDataAdapter<ExchangeEntity>();
        exchangeEntityListViewDataAdapter.setViewHolderClass(this, ExchangeViewHolder.class);
        ExchangeViewHolder.setOnClassifyPostionClickListener(this);
        listView.setAdapter(exchangeEntityListViewDataAdapter);
    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.VISIBLE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * item 点击事件处理
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ExchangeEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putString("productid", itemData.getId());
                bundle.putString("img_path", itemData.getImg_path());
                bundle.putString("title", itemData.getTitle());
                bundle.putString("price", itemData.getPrice());
                bundle.putString("people", itemData.getPeople());
                bundle.putString("kucun", itemData.getKucun());
                bundle.putString("tuanbi", itemData.getTuanbi());
                Intent intent_one = new Intent(getActivity(), ExchangePresnetActivity.class);
                intent_one.putExtras(bundle);
                getActivity().startActivity(intent_one);
                break;
            case 2:
                bundle.putString("productid", itemData.getId());
                bundle.putString("type", itemData.getState());
                Intent intent = new Intent(getActivity(), ExchangeDetialActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
        }
    }
    // 成功回调
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status_ = jsonObject.getString("status");
                    if (status_.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);

                        /***********团币数量***************/
                        String tuanbi = array.get(0).toString();
                        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);

                        /***********数据*************/

                        String data = array.get(1).toString();
                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("TotalPages");
                        String items = jsonObject_data.getString("Items");
                        if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                            exchangeEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }
                        JSONArray array_items = new JSONArray(items);
                        if (array_items != null && array_items.length() > 0) {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_items.length(); i++) {
                                JSONObject jsonObject_item = array_items.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                String title = jsonObject_item.getString("ProductName");
                                String PicUrl = jsonObject_item.getString("PicUrl");
                                String tuanbi_ = jsonObject_item.getString("TuanBi");
                                String price = jsonObject_item.getString("Price_Market");
                                String sell = jsonObject_item.getString("Amount_Sell");
                                String Amount_Stock = jsonObject_item.getString("Amount_Stock");
                                ExchangeEntity exchangeBean = new ExchangeEntity(id, title, PicUrl, tuanbi_, price, sell, Amount_Stock, Amount_Stock);
                                exchangeEntityListViewDataAdapter.append(exchangeBean);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.GONE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    //失败回调
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(getActivity(), "请求失败");
                break;
        }
    }


    //取消弹框
    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }


    @Override
    public void onResume() {
        super.onResume();
        tuanbiExchangePresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        tuanbiExchangePresenter.dettach();
        if (updateReceiver != null) {
            getActivity().unregisterReceiver(updateReceiver);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
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

//    /**
//     * 接收event事件
//     *
//     * @param event
//     */
//    public void onEventMainThread(Event<Integer> event) {
//        if (event.getEventType() == EventType.UPDATE_EXCHANGE_LIST) {
//            mIsUp = false;
//            pageindex = 1;
//            String sign = TGmd5.getMD5(distributorid + pageindex);
//            tuanbiExchangePresenter.getExchangeList(distributorid, pageindex + "", sign);
//        }
//    }
}
