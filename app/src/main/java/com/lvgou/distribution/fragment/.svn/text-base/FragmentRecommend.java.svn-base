package com.lvgou.distribution.fragment;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CirclrFengActivity;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.OfficialHomePageActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.adapter.ToutiaoFengwenAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.inter.OnClassifyPostionOneClickListener;
import com.lvgou.distribution.presenter.CircleRecommentPresenter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleRecommentViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 * 蜂圈 头条
 */
public class FragmentRecommend extends Fragment implements GroupView, ToutiaoFengwenAdapter.ItemClickListener, DistributorHomeView {

    private DistributorHomePresenter distributorHomePresenter;
    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;
    private PullToRefreshListView pullToRefreshListView;

    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private String prePageLastDataObjectId = "";

    private CircleRecommentPresenter circleRecommentPresenter;

    //    private ListViewDataAdapter<CircleRecommentEntity> circleRecommentEntityListViewDataAdapter;
    private ToutiaoFengwenAdapter fengwenAdapter;
    private CircleRecommentEntity circleRecommentEntity = null;
    private long startTime;

    private Dialog dialog_progress;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;


    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if ("com.distribution.tugou.top.state".equals(intent.getAction())) {
                    CircleRecommentEntity circleRecommentEntity = (CircleRecommentEntity) intent.getSerializableExtra("itemData");
                    int position = intent.getIntExtra("position", 0);
                    try {
                        for (int i = 0; i < fengwenAdapter.getCount(); i++) {
                            if (circleRecommentEntity.getID().equals(fengwenAdapter.getItem(i).getID())) {
                                fengwenAdapter.getItem(i).setCommentCount(circleRecommentEntity.getCommentCount());
                                fengwenAdapter.getItem(i).setZaned(circleRecommentEntity.getZaned());
                                fengwenAdapter.getItem(i).setZanCount(circleRecommentEntity.getZanCount());
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                fengwenAdapter.notifyDataSetChanged();
            }

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_no_title_list, null);
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        initView(view);
        startTime = System.currentTimeMillis();
        circleRecommentPresenter = new CircleRecommentPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        listView = pullToRefreshListView.getRefreshableView();

        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initViewHolder();
        initCreateView();

        showProgressDialog();
        String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
        circleRecommentPresenter.getRecommendList(distributorid, prePageLastDataObjectId, pageindex + "", sign);

        //注册广播
        stateReceiver = new StateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.top.state");
        getActivity().registerReceiver(stateReceiver, intentFilter);
        return view;
    }


    public void initView(View view) {
        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
    }

    public void showProgressDialog() {
        dialog_progress = createLoadingDialog(getActivity(), "");
        dialog_progress.show();
    }

    public void closeProgressDialog() {
        if (dialog_progress != null && dialog_progress.isShowing()) {
            dialog_progress.dismiss();
        }
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
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                prePageLastDataObjectId = "";
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                circleRecommentPresenter.getRecommendList(distributorid, prePageLastDataObjectId, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                    circleRecommentPresenter.getRecommendList(distributorid, prePageLastDataObjectId, pageindex + "", sign);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0 && resultCode == 1) {
//            if (data != null) {
//                CircleRecommentEntity entity = (CircleRecommentEntity) data.getSerializableExtra("itemData");
//                int position = data.getIntExtra("position", 0);
//                CircleRecommentEntity itemData = circleRecommentEntityListViewDataAdapter.getDataList().get(position);
//                itemData.setCommentCount(entity.getCommentCount());
//                itemData.setZanCount(entity.getZanCount());
//                itemData.setIsZaned(entity.getIsZaned());
//                circleRecommentEntityListViewDataAdapter.notifyDataSetChanged();
//            }
//        }
    }

    public void initViewHolder() {
//        circleRecommentEntityListViewDataAdapter = new ListViewDataAdapter<CircleRecommentEntity>();
//        circleRecommentEntityListViewDataAdapter.setViewHolderClass(this, CircleRecommentViewHolder.class);
//        CircleRecommentViewHolder.setOnClassifyPostionClickListener(this);
//        listView.setAdapter(circleRecommentEntityListViewDataAdapter);

        fengwenAdapter = new ToutiaoFengwenAdapter(getActivity());
        fengwenAdapter.setClickListener(this);
        listView.setAdapter(fengwenAdapter);
    }


    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String status = jsonObject_one.getString("status");
                    if (status.equals("1")) {
                        circleRecommentEntity.setZaned("1");
                        int zan = Integer.parseInt(circleRecommentEntity.getZanCount()) + 1;
                        circleRecommentEntity.setZanCount(zan + "");
//                        fengwenAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
                    JSONObject jsonObject_two = new JSONObject(respanse);
                    String status = jsonObject_two.getString("status");
                    if (status.equals("1")) {
                        circleRecommentEntity.setZaned("0");
                        int zan = Integer.parseInt(circleRecommentEntity.getZanCount()) - 1;
                        circleRecommentEntity.setZanCount(zan + "");
//                        fengwenAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String datas = jsonArray.get(0).toString();

                        JSONObject jsonObject_data = new JSONObject(datas);
                        if (pageindex == 1) {
                            total_page = jsonObject_data.getInt("DataPageCount");
                        }
                        String data_one = jsonObject_data.getString("Data");
                        if (mIsUp == false) {
                            fengwenAdapter.getCircleRecommentEntityList().clear();
                        } else {

                        }
                        Gson gosn = new Gson();
                        List<CircleRecommentEntity> circleRecommentEntityList = gosn.fromJson(data_one, new TypeToken<List<CircleRecommentEntity>>() {
                        }.getType());
                        if (circleRecommentEntityList != null && circleRecommentEntityList.size() > 0) {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            prePageLastDataObjectId = circleRecommentEntityList.get(circleRecommentEntityList.size() - 1).getID();
                            if (pageindex == 1) {
                                listView.setSelection(0);
                            }
                            fengwenAdapter.setFengcircleData(circleRecommentEntityList);
                            fengwenAdapter.notifyDataSetChanged();
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

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        closeProgressDialog();
        MyToast.show(getActivity(), "请求失败");
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }
    @Override
    public void onClassifyPostionClick(CircleRecommentEntity itemData, int postion, String state) {
        switch (Integer.parseInt(state)) {
            case 1:
                Intent intent = new Intent(getActivity(), NewRecomFengWenDetailsActivity.class);
                intent.putExtra("position", postion);
                intent.putExtra("talkId", itemData.getID());
                startActivityForResult(intent, 0);
                break;
            case 2:
                circleRecommentEntity = itemData;
                if (itemData.getZaned().equals("1")) {
                    String sing_one = TGmd5.getMD5(distributorid + itemData.getID());
                    ((CirclrFengActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                    circleRecommentPresenter.cancleZan(distributorid, itemData.getID(), sing_one);
                } else {
                    String sing_two = TGmd5.getMD5(distributorid + itemData.getID());
                    ((CirclrFengActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                    circleRecommentPresenter.doZan(distributorid, itemData.getID(), sing_two);
                }
                break;
            case 3:
                String seeDistributorID = "";
                if (Integer.parseInt(itemData.getSourceDistributorID()) > 0) {
                    seeDistributorID = itemData.getSourceDistributorID();
                } else {
                    seeDistributorID = itemData.getDistributorID();
                }
                String sign2 = TGmd5.getMD5("" + distributorid + seeDistributorID);
                ((CirclrFengActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                distributorHomePresenter.distributorHome(distributorid, seeDistributorID + "", sign2);
                /*if (Integer.parseInt(itemData.getSourceDistributorID()) > 0) {
                    //个人首页
                    Intent intent_one = new Intent(getActivity(), UserPersonalCenterActivity.class);
                    intent_one.putExtra("distributorid", Integer.parseInt(itemData.getSourceDistributorID()));
                    getActivity().startActivity(intent_one);
                } else {
                    // 官方
                    Intent intent_two = new Intent(getActivity(), OfficialHomePageActivity.class);
                    intent_two.putExtra("seeDistributorId", itemData.getDistributorID());
                    getActivity().startActivity(intent_two);
                }*/
                break;
        }
    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(getActivity(), TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(getActivity(), DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }


    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        startTime = System.currentTimeMillis();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == false) {
            startTime = System.currentTimeMillis();
        } else {
            long time = System.currentTimeMillis() - startTime;
            if (time >= 1000 * 60 * Constants.SECOND) {
                mIsUp = false;
                pageindex = 1;
                showProgressDialog();
                String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                circleRecommentPresenter.getRecommendList(distributorid, prePageLastDataObjectId, pageindex + "", sign);
            }
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onDestroy() {
        if (stateReceiver != null) {
            getActivity().unregisterReceiver(stateReceiver);
        }
        super.onDestroy();
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loding, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }
}
