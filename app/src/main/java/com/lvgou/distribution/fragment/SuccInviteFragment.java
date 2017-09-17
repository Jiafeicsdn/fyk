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
import com.lvgou.distribution.activity.InviteRecordActivity;
import com.lvgou.distribution.adapter.InviteAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.MyInviteListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyInviteListView;
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
 * Created by Administrator on 2017/4/12.
 */

public class SuccInviteFragment extends Fragment implements XListView.IXListViewListener, MyInviteListView {
    private View view;
    MyInviteListPresenter myInviteListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_invite, container, false);
        myInviteListPresenter = new MyInviteListPresenter(this);
        initView();
        onRefresh();
        return view;
    }

    private XListView mListView;
    private InviteAdapter inviteAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        inviteAdapter = new InviteAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((InviteRecordActivity) getActivity()).getTime());
        mListView.setDivider(null);
        inviteAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(inviteAdapter);
    }

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

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    private boolean isRefresh = false;
    private int page = 1;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + 2 + page);
        if (isRefresh) {
            ((InviteRecordActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        isRefresh = true;
        myInviteListPresenter.myInviteList(distributorid, 2, page, sign);
    }

    @Override
    public void OnMyInviteListSuccCallBack(String state, String respanse) {
        Log.e("kjhsakdfa", "========"+respanse );
        if (isRefresh) {
            ((InviteRecordActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            String totalItems = jsonObject1.get("TotalItems").toString();
            ((InviteRecordActivity) getActivity()).notifyTitle("", totalItems);
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
    public void OnMyInviteListFialCallBack(String state, String respanse) {
        Log.e("kjhsakdfa", "********"+respanse );
        if (isRefresh) {
            ((InviteRecordActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeMyInviteListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<SuccInviteFragment> mActivity;

        public mainHandler(SuccInviteFragment activity) {
            mActivity = new WeakReference<SuccInviteFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SuccInviteFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                inviteAdapter.setData(dataList);
                inviteAdapter.notifyDataSetChanged();
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
            inviteAdapter.setData(dataList);
            inviteAdapter.notifyDataSetChanged();
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
