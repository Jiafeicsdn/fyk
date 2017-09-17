package com.lvgou.distribution.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MytalklistAdapter;
import com.lvgou.distribution.adapter.SystemMsglistAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.SystemMessageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.SystemMessagePresenter;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.MytalklistView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 * 我的动态列表
 */
public class SystemMessageAcitivity extends BaseActivity implements BaseView {

    private PullToRefreshListView pull_refresh_list;
    public static ListView listView;
    SystemMessagePresenter systemMessagePresenter;
    private SystemMsglistAdapter systemMsglistAdapter;
    @ViewInject(R.id.lr_listview)
    private LinearLayout lr_listview;
    @ViewInject(R.id.empty_view)
    private RelativeLayout empty_view;
    @ViewInject(R.id.txt_tltle)
    private TextView txt_tltle;
    private String system_sign;
    private int currPage = 1;
    private int dataPageCount = 0;
    private String distributorid;
    private List<SystemMessageBean> systemMessageBeanList;
    private JSONArray jsonArray1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        ViewUtils.inject(this);
        txt_tltle.setText("系统通知");
        empty_view.setVisibility(View.GONE);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        listView = pull_refresh_list.getRefreshableView();
        systemMessagePresenter = new SystemMessagePresenter(this);
        systemMessagePresenter.attach(this);
        init();
        system_sign = TGmd5.getMD5(distributorid + currPage);
        systemMessagePresenter.sysmsglist(distributorid, currPage, system_sign);

    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage = 1;
                system_sign = TGmd5.getMD5(distributorid + currPage);
                list.clear();
                systemMessagePresenter.sysmsglist(distributorid, currPage, system_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    system_sign = TGmd5.getMD5(distributorid + currPage);
                    systemMessagePresenter.sysmsglist(distributorid, currPage, system_sign);
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
        systemMsglistAdapter = new SystemMsglistAdapter(this);
        listView.setAdapter(systemMsglistAdapter);
        pull_refresh_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*
                来源(Source)：0=通知信息（没有ObjectID，系统消息点击不需要跳转），
                1=学院（点击根据ObjectID跳转到课程详情），2=蜂文（跳转到蜂文详情），
                3=团币兑换商品（跳转到兑换详情），
                4=粉丝（不需要ObjectID，直接跳转到我的粉丝列表）
                对象ID(ObjectID)：来源为0时ObjectID为空，来源为1时保存学院ID
                ，来源为2时保存蜂文ID，为3时保存商品兑换ID，为4时ObjectID为空
                 */

                if (list != null) {
                    try {
                        JSONObject jso = new JSONObject(list.get(position - 1));
//                        JSONObject jso = (JSONObject) jsonArray1.get(position - 1);
                        if (jso.get("Source").toString().equals("1")) {
                            //学院
                           /* Bundle bundle = new Bundle();
                            bundle.putString("id", jso.get("ObjectID").toString());
                            bundle.putString("index", "0");
                            Intent intent = new Intent(SystemMessageAcitivity.this, FamousTeacherDetialActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);*/
                            Intent intent1 = new Intent(SystemMessageAcitivity.this, CourseIntrActivity.class);
                            intent1.putExtra("id", jso.get("ObjectID").toString());
                            startActivity(intent1);
                        } else if (jso.get("Source").toString().equals("2")) {
                            //蜂文详情
                            Intent intent = new Intent();
                            intent.setClass(SystemMessageAcitivity.this, NewDynamicDetailsActivity.class);
                            intent.putExtra("position", 0);
                            intent.putExtra("talkId", jso.get("ObjectID").toString());
                            startActivity(intent);
                        } else if (jso.get("Source").toString().equals("3")) {
                            //3=团币兑换商品（跳转到兑换详情）
                            Bundle bundle = new Bundle();
                            bundle.putString("productId", jso.get("ObjectID").toString());
                            openActivity(ExchangeTuanbiRecordDetialActivity.class, bundle);

                        } else if (jso.get("Source").toString().equals("4")) {
                            //4=粉丝（不需要ObjectID，直接跳转到我的粉丝列表）

                           /* Bundle bundle = new Bundle();
                            bundle.putString("seeDistributorId", distributorid);
                            openActivity(MyFansListActivity.class, bundle);*/
                            Intent intent2 = new Intent(SystemMessageAcitivity.this, MyFansActivity.class);
                            intent2.putExtra("seeDistributorId", distributorid);
                            intent2.putExtra("realName", "我");
                            startActivity(intent2);

                        } else if (jso.get("Source").toString().equals("5")) {
                            //5=导游证上传
                            String isover = PreferenceHelper.readString(SystemMessageAcitivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                            String state = PreferenceHelper.readString(SystemMessageAcitivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                            if (isover.equals("true") && state.equals("5")) {
                                openActivity(SeeCertificationActivity.class);
                            } else {
                                openActivity(CertificationActivity.class);
                            }
                        /*    Bundle bundle = new Bundle();
                            bundle.putString("state", "3");//审核不通过
                            Intent intent = new Intent(SystemMessageAcitivity.this, GuideCradMnagerActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);*/

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

    @Override
    public void excuteSuccessCallBack(String resonpse) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
            jsonArray1 = jsonObject1.getJSONArray("Items");

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                list.add(map1);
            }
            Gson gson = new Gson();
            systemMessageBeanList = gson.fromJson(jsonArray1.toString(), new TypeToken<List<SystemMessageBean>>() {
            }.getType());
            if (currPage == 1) {
                systemMsglistAdapter.getListData().clear();
            }
            if (systemMessageBeanList != null && systemMessageBeanList.size() > 0) {
                lr_listview.setVisibility(View.VISIBLE);
                empty_view.setVisibility(View.GONE);
                systemMsglistAdapter.setListData(systemMessageBeanList);
            } else {
                if (currPage == 1) {
                    lr_listview.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }
            systemMsglistAdapter.notifyDataSetChanged();
            dataPageCount = jsonObject1.getInt("ItemsPerPage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String s) {
        if (currPage == 1) {
            lr_listview.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        } else {
            lr_listview.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
        pull_refresh_list.onRefreshComplete();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (systemMessagePresenter != null)
            systemMessagePresenter.dettach();
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
