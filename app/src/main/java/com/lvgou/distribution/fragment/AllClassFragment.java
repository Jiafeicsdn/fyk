package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.MyAcActivity;
import com.lvgou.distribution.adapter.ApplyActivityAdapter;
import com.lvgou.distribution.adapter.MyActivityAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ApplyForMePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ApplyForMeView;
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
 * Created by Administrator on 2017/3/29.
 */

public class AllClassFragment extends Fragment implements ApplyForMeView, XListView.IXListViewListener {
    private ApplyForMePresenter applyForMePresenter;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allclass, container, false);
        applyForMePresenter = new ApplyForMePresenter(this);

        initView();
        onRefresh();
        initClick();

        return view;
    }

    private XListView mListView;
    private ApplyActivityAdapter myActivityAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        myActivityAdapter = new ApplyActivityAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyAcActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(myActivityAdapter);

    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                intent.putExtra("activityid", dataList.get(position - 1).get("ID").toString());
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

    private boolean isFirst = false;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + 0 + "" + page);
        if (isFirst) {
            ((MyAcActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        applyForMePresenter.applyForMeDatas(distributorid, 0, page, sign);

    }

    @Override
    public void OnApplyForMeSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
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
    public void OnApplyForMeFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeApplyForMeProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<AllClassFragment> mActivity;

        public mainHandler(AllClassFragment activity) {
            mActivity = new WeakReference<AllClassFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AllClassFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                myActivityAdapter.setData(dataList);
                myActivityAdapter.notifyDataSetChanged();
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
            myActivityAdapter.setData(dataList);
            myActivityAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
        }
    }


}
