package com.lvgou.distribution.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.adapter.ActDiscussListAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ActCommentListPresenter;
import com.lvgou.distribution.presenter.DelActCommentPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CommentListView;
import com.lvgou.distribution.view.DelActCommentView;
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
 * Created by Administrator on 2017/3/21.
 * 讨论区
 */

public class DiscussFragment extends BaseFragment implements XListView.IXListViewListener, CommentListView, DelActCommentView {
    private View view;
    private String activityid;
    private ActCommentListPresenter actCommentListPresenter;
    private DelActCommentPresenter delActCommentPresenter;
    private String distributorid;
    private RelativeLayout rl_none;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_act_discuss, container, false);
        actCommentListPresenter = new ActCommentListPresenter(this);
        delActCommentPresenter = new DelActCommentPresenter(this);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        TextView rl_none_title=(TextView)view.findViewById(R.id.nono_data_title);
        rl_none_title.setText("快来抢沙发！");
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            activityid = bundle.getString("activityid");
        }
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private RelativeLayout rl_scroll_button;
    private XListView mListView;
    private ActDiscussListAdapter discussListAdapter;

    private void initView() {
        mListView = (XListView) view.findViewById(R.id.list_view);
        discussListAdapter = new ActDiscussListAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((ActDetailActivity) getActivity()).getTime());
        mListView.setDivider(null);
        discussListAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(discussListAdapter);
    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (distributorid.equals(dataList.get(position - 1).get("DistributorID").toString())) {
                    showpopUpWindow(dataList.get(position - 1).get("ID").toString());
                }

            }
        });
    }

    public void showpopUpWindow(final String talkcommentId) {
        View contentview = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_comment_view, null);
        LinearLayout ll_del_view = (LinearLayout) contentview.findViewById(R.id.ll_del_view);
        final PopupWindow popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView txt_confirm_del = (TextView) contentview.findViewById(R.id.txt_confirm_del);
        TextView txt_cancel_del = (TextView) contentview.findViewById(R.id.txt_cancel_del);
        txt_confirm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新界面
                ((ActDetailActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                String sign = TGmd5.getMD5(distributorid + talkcommentId);
                delActCommentPresenter.delActComment(distributorid, talkcommentId, sign);
                popupWindow.dismiss();
            }
        });
        txt_cancel_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_del_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    private boolean isRefresh = false;
    private int page = 1;

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + activityid + page);
        if (isRefresh) {
            ((ActDetailActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        isRefresh = true;
        actCommentListPresenter.commentList(distributorid, activityid, page, sign);
    }


    @Override
    public void OnCommentListSuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((ActDetailActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            String totalItems = jsonObject1.get("TotalItems").toString();
            if(jsonArray1.length()>0) {
                mListView.setVisibility(View.VISIBLE);
                rl_none.setVisibility(View.GONE);
                ((ActDetailActivity) getActivity()).notifyTitle(totalItems);
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
                if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) && Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) != 0) {
                    mHandler.sendEmptyMessage(2);
                } else {
                    mHandler.sendEmptyMessage(1);
                }
            }else{
                mListView.setVisibility(View.GONE);
                rl_none.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<DiscussFragment> mActivity;

        public mainHandler(DiscussFragment activity) {
            mActivity = new WeakReference<DiscussFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DiscussFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                discussListAdapter.setData(dataList);
                discussListAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            discussListAdapter.setData(dataList);
            discussListAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }

    @Override
    public void OnCommentListFialCallBack(String state, String respanse) {
        if (isRefresh) {
            ((ActDetailActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeCommentListProgress() {

    }

    @Override
    public void OnDelActCommentSuccCallBack(String state, String respanse) {
        ((ActDetailActivity) getActivity()).closeLoadingProgressDialog();
        onRefresh();
    }

    @Override
    public void OnDelActCommentFialCallBack(String state, String respanse) {
        ((ActDetailActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeDelActCommentProgress() {

    }

}