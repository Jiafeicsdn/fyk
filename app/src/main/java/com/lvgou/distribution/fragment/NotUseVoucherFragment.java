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
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.GiftVoucherActivity;
import com.lvgou.distribution.activity.HomeActivity;
import com.lvgou.distribution.adapter.ListenClassAdapter;
import com.lvgou.distribution.adapter.NotUseVoucherAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.CouponListPresenter;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CouponListView;
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
 * 听课券未使用
 */

public class NotUseVoucherFragment extends Fragment implements XListView.IXListViewListener, CouponListView {
    private View view;
    private CouponListPresenter couponListPresenter;
    public ACache mcache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notuse_voucher_fragment, container, false);
        couponListPresenter = new CouponListPresenter(this);
        mcache = ACache.get(getActivity());
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private NotUseVoucherAdapter notUseVoucherAdapter;
    private View nouseHeader;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        notUseVoucherAdapter = new NotUseVoucherAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((GiftVoucherActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(notUseVoucherAdapter);
        nouseHeader = LayoutInflater.from(getActivity()).inflate(R.layout.nouse_fragment_header, null);
        mListView.addHeaderView(nouseHeader);
    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("kashdkfhsajsd", "---------" + position);
                if (dataList.get(position - 2).get("Type").toString().equals("4")) {
                    //全局券
                    mcache.put("collegecurrentPage", "4");
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "0");
                    ((GiftVoucherActivity) getActivity()).openActivity(HomeActivity.class, bundle);
                    /*Intent intent1 = new Intent(getActivity(), CollegeManagerActivity.class);
                    intent1.putExtra("currentPage", 4);
                    startActivity(intent1);*/
                } else {
                    if (Integer.parseInt(dataList.get(position - 2).get("TeacherID").toString()) > 0) {
                        //进入课程详情
                        Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                        intent1.putExtra("id", dataList.get(position - 2).get("TeacherID").toString());
                        startActivity(intent1);
                    } else {
                        //进入分类
                        mcache.put("collegecurrentPage", "4");
                        Bundle bundle = new Bundle();
                        bundle.putString("selection_postion", "0");
                        ((GiftVoucherActivity) getActivity()).openActivity(HomeActivity.class, bundle);
                        /*Intent intent1 = new Intent(getActivity(), CollegeManagerActivity.class);
                        intent1.putExtra("currentPage", 4);
                        intent1.putExtra("linkUrl", "1");
                        startActivity(intent1);*/
                    }
                }
            }
        });
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

    private int page = 1;
    private boolean isRefresh = false;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + 1 + "" + page);
        if (isRefresh) {
            ((GiftVoucherActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        couponListPresenter.couponListDatas(distributorid, 1, page, sign);

    }

    @Override
    public void OnCouponListSuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((GiftVoucherActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isRefresh = true;
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
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) && Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) != 0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCouponListFialCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeCouponListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<NotUseVoucherFragment> mActivity;

        public mainHandler(NotUseVoucherFragment activity) {
            mActivity = new WeakReference<NotUseVoucherFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            NotUseVoucherFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                notUseVoucherAdapter.setData(dataList);
                notUseVoucherAdapter.notifyDataSetChanged();
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
            notUseVoucherAdapter.setData(dataList);
            notUseVoucherAdapter.notifyDataSetChanged();
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
