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
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DoggerelActivity;
import com.lvgou.distribution.adapter.DoggerelAdapter;
import com.lvgou.distribution.adapter.ListenClassAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.JokeListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.JokeListView;
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
 * Created by Administrator on 2017/3/31.
 */

public class HottestDoggerelFragment extends Fragment implements XListView.IXListViewListener, JokeListView {
    private View view;
    private JokeListPresenter jokeListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doggerel, container, false);
        jokeListPresenter = new JokeListPresenter(this);
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private DoggerelAdapter doggerelAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        doggerelAdapter = new DoggerelAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((DoggerelActivity) getActivity()).getTime());
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

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private boolean isFirst = false;

    /*
    distributorid 	是 	long 	导游ID
type 	是 	int 	类型 1=笑话 2=顺口溜
order 	是 	int 	排序 笑话：1=最新 2=图文 3=纯文；顺口溜：1=最热 2=最新
pageindex 	是 	int 	当前页码
sign 	是 	string 	签名
     */
    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid + 2 + 1 + page);
        if (isFirst) {
            ((DoggerelActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        jokeListPresenter.jokeList(distributorid, 2, 1, page, sign);
    }

    @Override
    public void OnJokeListSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((DoggerelActivity) getActivity()).closeLoadingProgressDialog();
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
    public void OnJokeListFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((DoggerelActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeJokeListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<HottestDoggerelFragment> mActivity;

        public mainHandler(HottestDoggerelFragment activity) {
            mActivity = new WeakReference<HottestDoggerelFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HottestDoggerelFragment activity = mActivity.get();
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