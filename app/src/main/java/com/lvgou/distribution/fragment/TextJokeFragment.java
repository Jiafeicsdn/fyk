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
import com.lvgou.distribution.activity.JokeActivity;
import com.lvgou.distribution.adapter.JokeAdapter;
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
 * 纯文笑话
 */

public class TextJokeFragment extends Fragment implements XListView.IXListViewListener, JokeListView {
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
    private JokeAdapter jokeAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        jokeAdapter = new JokeAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((JokeActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(jokeAdapter);
    }

    private void initClick() {
        jokeAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
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


    /*
    distributorid 	是 	long 	导游ID
type 	是 	int 	类型 1=笑话 2=顺口溜
order 	是 	int 	排序 笑话：1=最新 2=图文 3=纯文；顺口溜：1=最热 2=最新
pageindex 	是 	int 	当前页码
sign 	是 	string 	签名
     */
    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid + 1 + 3 + page);
        ((JokeActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        jokeListPresenter.jokeList(distributorid, 1, 3, page, sign);
    }

    @Override
    public void OnJokeListSuccCallBack(String state, String respanse) {
        ((JokeActivity) getActivity()).closeLoadingProgressDialog();
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
    public void OnJokeListFialCallBack(String state, String respanse) {
        ((JokeActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeJokeListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<TextJokeFragment> mActivity;

        public mainHandler(TextJokeFragment activity) {
            mActivity = new WeakReference<TextJokeFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TextJokeFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                jokeAdapter.setData(dataList);
                jokeAdapter.notifyDataSetChanged();
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
            jokeAdapter.setData(dataList);
            jokeAdapter.notifyDataSetChanged();
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
