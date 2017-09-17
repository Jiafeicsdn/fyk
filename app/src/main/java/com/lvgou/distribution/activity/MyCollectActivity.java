package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.CollectAdapter;
import com.lvgou.distribution.adapter.MytalklistAdapter;
import com.lvgou.distribution.bean.CollectListBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.ZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.CollectListPresenter;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyCollectView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class MyCollectActivity extends BaseActivity implements MyCollectView{
    @ViewInject(R.id.layout_filter)
    private RelativeLayout layout_filter;
    @ViewInject(R.id.data_view)
    private LinearLayout data_view;
    @ViewInject(R.id.empty_view)
    private RelativeLayout empty_view;
    private int currPage = 1;
    private String distributorid;
    private PullToRefreshListView pull_refresh_list;
    public ListView listView;

    private CollectListPresenter collectListPresenter;
    private String collect_sign;
    private String prePageLastDataObjectId="";
    private int dataPageCount;
    private CollectAdapter collectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        ViewUtils.inject(this);
        layout_filter.setVisibility(View.GONE);
        data_view.setVisibility(View.GONE);
        empty_view.setVisibility(View.GONE);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        listView = pull_refresh_list.getRefreshableView();
        collectListPresenter = new CollectListPresenter(this);
        collectListPresenter.attach(this);
        init();
        collect_sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + currPage);
        collectListPresenter.talkcollectionlist(distributorid, prePageLastDataObjectId, currPage, collect_sign);
    }
    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage = 1;
                collect_sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + currPage);
                collectListPresenter.talkcollectionlist(distributorid, prePageLastDataObjectId, currPage, collect_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    collect_sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + currPage);
                    collectListPresenter.talkcollectionlist(distributorid, prePageLastDataObjectId, currPage, collect_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 500);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        collectAdapter = new CollectAdapter(this,new ArrayList<CollectListBean>());
        listView.setAdapter(collectAdapter);
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
    public void excuteSuccessCallBack(String type, String s) {
        switch (type){
            case "collect":
                talkCollectResponse(s);
                break;
        }
    }
private void talkCollectResponse(String response){
    pull_refresh_list.onRefreshComplete();
    try {
        if (currPage == 1) {
            collectAdapter.getCollectList().clear();
        }
        JSONObject jsonObject = new JSONObject(response);
        int status = jsonObject.getInt("status");
        if (status == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
            if (jsonArray != null && jsonArray.length() > 0) {
                Gson gson = new Gson();
//                gson.fromJson(jsonObject1.toString(),);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                List<CollectListBean> collectListBeans = gson.fromJson(jsonArray1.toString(), new TypeToken<List<CollectListBean>>() {
                }.getType());
                if (collectListBeans != null && collectListBeans.size() > 0) {
                    prePageLastDataObjectId = collectListBeans.get(collectListBeans.size() - 1).getID();
                    data_view.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                } else {
                    data_view.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
                collectAdapter.setListData(collectListBeans);
                dataPageCount = jsonObject1.getInt("DataPageCount");
            }
        }
        collectAdapter.notifyDataSetChanged();
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {

    }

    @Override
    public void excuteFailedCallBack(String s) {
        pull_refresh_list.onRefreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(collectListPresenter!=null){
            collectListPresenter.dettach();
        }
    }
}
