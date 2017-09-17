package com.lvgou.distribution.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.MyZanListActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.adapter.MyZanListAdapter;
import com.lvgou.distribution.bean.MyZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class MyZanListFragment extends Fragment implements GroupView {
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;

    PersonalCircleListPresenter personalCircleListPresenter;
    private String distributorid;
    private Activity mContext;
    private String prePageLastDataObjectId = "";
    private int pageindex = 1;

    private int total_page;
    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;
    private MyZanListAdapter myZanListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_praised_comment, container, false);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        mContext=getActivity();
        personalCircleListPresenter = new PersonalCircleListPresenter(this);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();
        ((MyZanListActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
        initCreateView();
        initViewHolder();
        personalCircleListPresenter.getMyZanList1(distributorid, prePageLastDataObjectId, pageindex, sign);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initViewHolder() {
        myZanListAdapter = new MyZanListAdapter(mContext, new ArrayList());
        myZanListAdapter.setPersonal(personalCircleListPresenter);
        listView.setAdapter(myZanListAdapter);
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
                pageindex = 1;
                prePageLastDataObjectId = "";
                String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                personalCircleListPresenter.getMyZanList1(distributorid, prePageLastDataObjectId, pageindex, sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                    personalCircleListPresenter.getMyZanList1(distributorid, prePageLastDataObjectId, pageindex, sign);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.onRefreshComplete();
                        }
                    }, 500);
                    MyToast.show(mContext, "没有更多数据");
                }
            }
        });
    }

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (state) {
            case "6"://获取蜂文详情（得到蜂文类型）
                try {
                    JSONObject jsonObject=new JSONObject(respanse);
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String data = array.get(0).toString();
                    JSONObject jsonObject_data = new JSONObject(data);
                    int userType=jsonObject_data.getInt("UserType");
                    String fengwenId= jsonObject_data.getString("ID");
                    Intent intent = new Intent();
                    if (userType == 1) {
                        intent.setClass(mContext, NewRecomFengWenDetailsActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("talkId",fengwenId);
                    } else {
                        intent.setClass(mContext, NewDynamicDetailsActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("talkId",fengwenId);
                    }
                    mContext.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");

//                    String sign_oen = TGmd5.getMD5(distributorid + "2");
//                    personalCircleListPresenter.seekReadUnreadMessageList(distributorid, "2", sign_oen);

                    if (status.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                        Gson gson = new Gson();
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                        List<MyZanListBean> myZanListBeanList = gson.fromJson(jsonArray1.toString(), new TypeToken<List<MyZanListBean>>() {
                        }.getType());
                        if (pageindex == 1) {
                            total_page = jsonObject1.getInt("DataPageCount");
                        }
                        if (pageindex == 1) {
                            myZanListAdapter.getMyzanList().clear();
                        }
                        if (myZanListBeanList != null && myZanListBeanList.size() > 0) {
                            prePageLastDataObjectId = myZanListBeanList.get(myZanListBeanList.size() - 1).getID();
                            myZanListAdapter.setDataList(myZanListBeanList);
                            myZanListAdapter.notifyDataSetChanged();
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (state) {
            case "2":
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
            case "6"://{"status":0,"message":"动态已删除","result":null}
                ((BaseActivity)mContext).showHintDialog(respanse);
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

    @Override
    public void closeProgress() {
        ((MyZanListActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {
        ((MyZanListActivity) getActivity()).showLoadingProgressDialog(mContext,"");
    }
}
