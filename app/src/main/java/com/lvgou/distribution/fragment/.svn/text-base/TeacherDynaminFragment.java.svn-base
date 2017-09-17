package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.SystemMessageAcitivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.adapter.OpenClassAdapter;
import com.lvgou.distribution.adapter.TeacherDynaAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.HomeTalklistPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.HomeTalklistView;
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
 * Created by Administrator on 2017/4/1.
 */

public class TeacherDynaminFragment extends BaseFragment implements XListView.IXListViewListener, HomeTalklistView {
    private View view;
    private HomeTalklistPresenter homeTalklistPresenter;
    private String seeDistributorId;
    private String distributorid;
    private boolean isMe = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course, container, false);
        homeTalklistPresenter = new HomeTalklistPresenter(this);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            seeDistributorId = bundle.getString("seeDistributorId");
        }
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        if (seeDistributorId.equals(distributorid)) {
            isMe = true;
        } else {
            isMe = false;
        }
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private TeacherDynaAdapter teacherDynaAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        teacherDynaAdapter = new TeacherDynaAdapter(getActivity(), isMe);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((TeacherHomeActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(teacherDynaAdapter);
    }

    private void initClick() {
        teacherDynaAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        if (info.get("ZanCount").equals("delete")) {
                            dataList.remove(i);
                            teacherDynaAdapter.notifyDataSetChanged();
                            break;
                        } else {
                            dataList.get(i).put("ZanCount", info.get("ZanCount"));
                            dataList.get(i).put("Zaned", info.get("Zaned"));
                        }

                        break;
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("kjashkdfs", "========"+position );
                //蜂文详情
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewDynamicDetailsActivity.class);
                intent.putExtra("position", 0);
                intent.putExtra("talkId", dataList.get(position - 1).get("ID").toString());
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return mListView;
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

    private boolean isFirst = false;
    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + seeDistributorId + page);
        if (isFirst) {
            ((TeacherHomeActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        homeTalklistPresenter.homeTalklist(distributorid, seeDistributorId, page, sign);

    }

    @Override
    public void OnHomeTalklistSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((TeacherHomeActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
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
    public void OnHomeTalklistFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((TeacherHomeActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
    }

    @Override
    public void closeHomeTalklistProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<TeacherDynaminFragment> mActivity;

        public mainHandler(TeacherDynaminFragment activity) {
            mActivity = new WeakReference<TeacherDynaminFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TeacherDynaminFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                teacherDynaAdapter.setData(dataList);
                teacherDynaAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
                mListView.setPullLoadEnable(false);
            } else {
                mListView.setPullLoadEnable(true);
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            teacherDynaAdapter.setData(dataList);
            teacherDynaAdapter.notifyDataSetChanged();
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