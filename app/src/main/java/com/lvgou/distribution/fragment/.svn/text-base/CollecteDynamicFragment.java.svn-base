package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.MyAcActivity;
import com.lvgou.distribution.activity.MyCollectionActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.adapter.ApplyActivityAdapter;
import com.lvgou.distribution.adapter.CollecteDynamicAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ApplyForMePresenter;
import com.lvgou.distribution.presenter.TalkCollectionListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TalkCollectionListView;
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

public class CollecteDynamicFragment extends Fragment implements XListView.IXListViewListener, TalkCollectionListView {
    private View view;
    private TalkCollectionListPresenter talkCollectionListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        talkCollectionListPresenter = new TalkCollectionListPresenter(this);
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private CollecteDynamicAdapter collecteDynamicAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        collecteDynamicAdapter = new CollecteDynamicAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyCollectionActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(collecteDynamicAdapter);
    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewDynamicDetailsActivity.class);
                intent.putExtra("position", 0);
                intent.putExtra("talkId", dataList.get(position - 1).get("FengwenID").toString());
                getActivity().startActivity(intent);
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

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private String prePageLastDataObjectId = "";

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + page);
        ((MyCollectionActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        talkCollectionListPresenter.talkCollectionList(distributorid, prePageLastDataObjectId, page, sign);
    }


    @Override
    public void OnTalkCollectionListSuccCallBack(String state, String respanse) {
        ((MyCollectionActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Data"));

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
            if (dataListTmp.size() > 0) {
                prePageLastDataObjectId = dataListTmp.get(dataListTmp.size() - 1).get("ID").toString();
            }
           /* if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("DataCount").toString())) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }*/

            if (page == Integer.parseInt(jsonObject1.get("DataPageCount").toString())) {
                //如果当前页等于总页数，就没有下一页
                mHandler.sendEmptyMessage(2);
            } else {
                //有下一页
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnTalkCollectionListFialCallBack(String state, String respanse) {
        ((MyCollectionActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeTalkCollectionListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<CollecteDynamicFragment> mActivity;

        public mainHandler(CollecteDynamicFragment activity) {
            mActivity = new WeakReference<CollecteDynamicFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CollecteDynamicFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }

    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                collecteDynamicAdapter.setData(dataList);
                collecteDynamicAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                if (page==1){
                    mListView.setPullLoadEnable(false);
                }
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            collecteDynamicAdapter.setData(dataList);
            collecteDynamicAdapter.notifyDataSetChanged();
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
