package com.lvgou.distribution.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MyCollectionActivity;
import com.lvgou.distribution.adapter.CollecteDoggerelAdapter;
import com.lvgou.distribution.adapter.DoggerelAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.CollectionListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CollectionListView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CollecteDoggerelFragment extends Fragment implements XListView.IXListViewListener, CollectionListView {
    private View view;
    private CollectionListPresenter collectionListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doggerel, container, false);
        collectionListPresenter = new CollectionListPresenter(this);
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private CollecteDoggerelAdapter doggerelAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        doggerelAdapter = new CollecteDoggerelAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyCollectionActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(doggerelAdapter);
    }

    private void initClick() {
        doggerelAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        Log.e("askhskjfd", "doSomeThing: ");
                        dataList.get(i).put("State", info.get("State"));
                        dataList.get(i).put("Ding", info.get("Ding"));
                        dataList.get(i).put("Cai", info.get("Cai"));
                        dataList.get(i).put("Hits", info.get("Hits"));
                        break;
                    }
                }
            }
        });
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();
    }

    private boolean isFirst = false;

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid + 2 + page);
        if (isFirst) {
            ((MyCollectionActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        collectionListPresenter.collectionList(distributorid, 2, page, sign);
    }

    private final mainHandler mHandler = new mainHandler(this);

    @Override
    public void OnCollectionListSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyCollectionActivity) getActivity()).closeLoadingProgressDialog();
        }
        isFirst = true;
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataListTmp.add(map1);
            }
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())&&Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())!=0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCollectionListFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyCollectionActivity) getActivity()).closeLoadingProgressDialog();
        }
        isFirst = true;
        mListView.stopRefresh();

    }

    @Override
    public void closeCollectionListProgress() {

    }

    private static class mainHandler extends Handler {
        private final WeakReference<CollecteDoggerelFragment> mActivity;

        public mainHandler(CollecteDoggerelFragment activity) {
            mActivity = new WeakReference<CollecteDoggerelFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CollecteDoggerelFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                doggerelAdapter.setData(dataList);
                doggerelAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            doggerelAdapter.setData(dataList);
            doggerelAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }
}
