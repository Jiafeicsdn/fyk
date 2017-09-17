package com.lvgou.distribution.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MytalklistAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MytalklistView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 * 我的动态列表
 */
public class MyFengwenAcitivity extends BaseActivity implements MytalklistView {

    boolean mIsUp;// 是否上拉加载
    private PullToRefreshListView pull_refresh_list;
    public static ListView listView;
    UserDynamicPresenter userDynamicPresenter;
    private MytalklistAdapter mytalklistAdapter;
    @ViewInject(R.id.layout_filter)
    private RelativeLayout layout_filter;
    @ViewInject(R.id.data_view)
    private LinearLayout data_view;
    @ViewInject(R.id.empty_view)
    private RelativeLayout empty_view;
    private String mytalklist_sign;
    private int currPage = 1;
    private int dataPageCount = 0;
    private String seeDistributorId = "";
    private FengCircleDynamicBean adapterDynamicBean;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;
    private String fengwenId;
    private String distributorid;

    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if ("com.distribution.tugou.state".equals(intent.getAction())) {
                    FengCircleDynamicBean circleDynamicBean = (FengCircleDynamicBean) intent.getSerializableExtra("itemData");
                    try {
                        int position = intent.getIntExtra("position", 0);
                        for (int i = 0; i < mytalklistAdapter.getCount(); i++) {
                            if (circleDynamicBean.getDistributorID() == mytalklistAdapter.getItem(i).getDistributorID()) {
                                mytalklistAdapter.getItem(i).setFollowed(circleDynamicBean.getFollowed());
                            }
                            if (circleDynamicBean.getID().equals(mytalklistAdapter.getItem(i).getID())) {
                                mytalklistAdapter.getItem(i).setCommentCount(circleDynamicBean.getCommentCount());
                                mytalklistAdapter.getItem(i).setZaned(circleDynamicBean.getZaned());
                                mytalklistAdapter.getItem(i).setZanCount(circleDynamicBean.getZanCount());
                            }
                        }
                    } catch (Exception e) {
                    }
                } else if ("com.distribution.tugou.del".equals(intent.getAction())) {
                    String fengwenId = intent.getStringExtra("fengwenId");
                    for (int i = 0; i < mytalklistAdapter.getCount(); i++) {
                        if (fengwenId.equals(mytalklistAdapter.getItem(i).getID())) {
                            mytalklistAdapter.getFengcircleData().remove(i);
                        }
                    }
                }
                mytalklistAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fengwen_acitivity);
        ViewUtils.inject(this);
        layout_filter.setVisibility(View.GONE);
        data_view.setVisibility(View.GONE);
        empty_view.setVisibility(View.GONE);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        listView = pull_refresh_list.getRefreshableView();
        userDynamicPresenter = new UserDynamicPresenter(this);
        seeDistributorId = distributorid;
        init();
        mytalklist_sign = TGmd5.getMD5(distributorid + seeDistributorId + currPage);
        userDynamicPresenter.mytalklist(distributorid, seeDistributorId, currPage, mytalklist_sign);

        //注册广播
        stateReceiver = new StateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.state");
        intentFilter.addAction("com.distribution.tugou.del");
        registerReceiver(stateReceiver, intentFilter);
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage = 1;
                mytalklist_sign = TGmd5.getMD5(distributorid + seeDistributorId + currPage);
                userDynamicPresenter.mytalklist(distributorid, seeDistributorId, currPage, mytalklist_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    mytalklist_sign = TGmd5.getMD5(distributorid + seeDistributorId + currPage);
                    userDynamicPresenter.mytalklist(distributorid, seeDistributorId, currPage, mytalklist_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        mytalklistAdapter = new MytalklistAdapter(this, userDynamicPresenter);
        mytalklistAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        mytalklistAdapter.setmAdapterListener(adapterCallBack);
        mytalklistAdapter.setFengwenDelListener(fengwenDelListener);
        mytalklistAdapter.setPageType(1);
        listView.setAdapter(mytalklistAdapter);
    }

    @OnClick(R.id.img_back)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void mytalklistResponse(String resonpse) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
            JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
            dataPageCount = jsonObject1.getInt("DataPageCount");
            List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
            if (jsonArray1 != null && jsonArray1.length() > 0) {
                data_view.setVisibility(View.VISIBLE);
                empty_view.setVisibility(View.GONE);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    FengCircleDynamicBean fengCircleDynamicBean = new FengCircleDynamicBean();
                    fengCircleDynamicBean.setID(((JSONObject) jsonArray1.get(i)).getString("ID"));
                    fengCircleDynamicBean.setDistributorID(((JSONObject) jsonArray1.get(i)).getInt("DistributorID"));
                    fengCircleDynamicBean.setDistributorName(((JSONObject) jsonArray1.get(i)).getString("DistributorName"));
                    fengCircleDynamicBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
                    fengCircleDynamicBean.setIsRZ(((JSONObject) jsonArray1.get(i)).getInt("IsRZ"));
                    fengCircleDynamicBean.setCategoryIDs(((JSONObject) jsonArray1.get(i)).getString("CategoryIDs"));
                    JSONArray jsonCategory = ((JSONObject) jsonArray1.get(i)).getJSONArray("CategoryNames");
                    List<String> categoryNames = new ArrayList<>();
                    for (int j = 0; j < jsonCategory.length(); j++) {
                        categoryNames.add((String) jsonCategory.get(j));
                    }
                    fengCircleDynamicBean.setCategoryNames(categoryNames);
                    fengCircleDynamicBean.setTitle(((JSONObject) jsonArray1.get(i)).getString("Title"));
                    fengCircleDynamicBean.setContent(((JSONObject) jsonArray1.get(i)).getString("Content"));
                    fengCircleDynamicBean.setPicUrl(((JSONObject) jsonArray1.get(i)).getString("PicUrl"));
                    JSONArray piclists = ((JSONObject) jsonArray1.get(i)).getJSONArray("PicJson");
                    List<FengCircleImageBean> circleImageBeans = new ArrayList<>();
                    if (piclists != null) {
                        for (int j = 0; j < piclists.length(); j++) {
                            FengCircleImageBean circleImageBean = new FengCircleImageBean();
                            if (((String) piclists.get(j)).indexOf("{") != -1) {
                                JSONObject jsonObject2 = new JSONObject((String) piclists.get(j));
                                if(((String) piclists.get(j)).indexOf("smallPicUrl") != -1){
                                    circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                                }
                                if(((String) piclists.get(j)).indexOf("picUrl") != -1){
                                    circleImageBean.setPicUrl(Url.ROOT + jsonObject2.getString("picUrl"));
                                }
                                circleImageBean.setHeight(jsonObject2.getInt("height"));
                                circleImageBean.setWidth(jsonObject2.getInt("width"));
                            }
                            circleImageBeans.add(circleImageBean);
                        }
                    }
                    fengCircleDynamicBean.setmImgUrlList(circleImageBeans);
                    fengCircleDynamicBean.setZanCount(((JSONObject) jsonArray1.get(i)).getInt("ZanCount"));
                    fengCircleDynamicBean.setCommentCount(((JSONObject) jsonArray1.get(i)).getInt("CommentCount"));
                    fengCircleDynamicBean.setSourceDistributorID(((JSONObject) jsonArray1.get(i)).getInt("SourceDistributorID"));
                    fengCircleDynamicBean.setSourceDistributorName(((JSONObject) jsonArray1.get(i)).getString("SourceDistributorName"));
                    fengCircleDynamicBean.setSourceTitle(((JSONObject) jsonArray1.get(i)).getString("SourceTitle"));
                    fengCircleDynamicBean.setCreateTime(((JSONObject) jsonArray1.get(i)).getString("CreateTime"));
                    circleDynamicBeans.add(fengCircleDynamicBean);
                }
                if (currPage == 1) {
                    mytalklistAdapter.getFengcircleData().clear();
                }
                mytalklistAdapter.setFengcircleData(circleDynamicBeans);
                mytalklistAdapter.notifyDataSetChanged();
            } else {
                data_view.setVisibility(View.GONE);
                empty_view.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String s) {
        data_view.setVisibility(View.GONE);
        empty_view.setVisibility(View.VISIBLE);
        pull_refresh_list.onRefreshComplete();
    }

    @Override
    public void zanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = mytalklistAdapter.getFengcircleData();
                list.get(position).setZaned(1);
                list.get(position).setZanCount(list.get(position).getZanCount() + 1);
                mytalklistAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unzanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = mytalklistAdapter.getFengcircleData();
                list.get(position).setZaned(0);
                list.get(position).setZanCount(list.get(position).getZanCount() - 1);
                mytalklistAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void talkdelResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                Intent intent = new Intent();
                intent.putExtra("fengwenId", fengwenId);
                intent.setAction("com.distribution.tugou.del");
                sendBroadcast(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void distributormainResponse(String resonpse) {
        //个人主页头部详情调用
    }

    @Override
    public void followResponse(String resonpse, String position) {

    }

    public MytalklistAdapter.AdapterCallBack adapterCallBack = new MytalklistAdapter.AdapterCallBack() {
        @Override
        public void getItemData(FengCircleDynamicBean circleDynamicBean) {
            adapterDynamicBean = circleDynamicBean;
        }
    };
    public MytalklistAdapter.FengwenDelListener fengwenDelListener = new MytalklistAdapter.FengwenDelListener() {
        @Override
        public void getFengwenId(String Id) {
            fengwenId = Id;
        }
    };

    private void sendBrodCastReciver() {
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.state");
        intent.putExtra("itemData", adapterDynamicBean);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (stateReceiver != null) {
            unregisterReceiver(stateReceiver);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
