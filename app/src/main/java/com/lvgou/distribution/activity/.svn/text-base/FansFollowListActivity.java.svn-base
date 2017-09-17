package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
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
import com.lvgou.distribution.adapter.Concern_FansAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.presenter.FansFollowListPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 * 粉丝，关注 列表
 */
public class FansFollowListActivity extends BaseActivity implements GroupView,Concern_FansAdapter.OnClassifyPostionClickListener{

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private Dialog dialog_del_can;// 取消，删除弹框


    private String distributorid = "";
    private ListView listView;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private Concern_FansAdapter concern_fansAdapter;
//    private ListViewDataAdapter<CircleUserEntity> circleUserEntityListViewDataAdapter;

    private FansFollowListPresenter fansFollowListPresenter;

    private CircleUserEntity circleUserEntity = null;

    private String type = "";
    private String seeDistributorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_list);
        ViewUtils.inject(this);
        tv_title.setText("我的关注");

        distributorid = PreferenceHelper.readString(FansFollowListActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();

        fansFollowListPresenter = new FansFollowListPresenter(this);

        seeDistributorId = getTextFromBundle("seeDistributorId");

        initCreateView();

        initViewHolderUser();

        showLoadingProgressDialog(FansFollowListActivity.this, "");
        String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
        fansFollowListPresenter.getFollowList(distributorid, seeDistributorId, pageindex + "", sign);

    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public void initViewHolderUser() {
//        circleUserEntityListViewDataAdapter = new ListViewDataAdapter<CircleUserEntity>();
//        circleUserEntityListViewDataAdapter.setViewHolderClass(this, CircleUserViewHolder.class);
//        CircleUserViewHolder.setCircleUserEntityOnClassifyPostionClickListener(this);
//        listView.setAdapter(circleUserEntityListViewDataAdapter);

        concern_fansAdapter = new Concern_FansAdapter(this);
        concern_fansAdapter.setOnClassifyPostionClick(this);
        listView.setAdapter(concern_fansAdapter);


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
                String label = DateUtils.formatDateTime(FansFollowListActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                fansFollowListPresenter.getFollowList(distributorid, seeDistributorId, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;

                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign_two = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                    fansFollowListPresenter.getFollowList(distributorid, seeDistributorId, pageindex + "", sign_two);

                } else {
                    MyToast.show(FansFollowListActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(FansFollowListActivity.this, R.style.Mydialog);
        View view1 = View.inflate(FansFollowListActivity.this, R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                String sign = TGmd5.getMD5(distributorid + id);
                fansFollowListPresenter.cancleFollow(distributorid, id, sign);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
            }
        });
        dialog_del_can.setContentView(view1);
        dialog_del_can.show();
    }


    /**
     * item 点击事件
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(CircleUserEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getID()));
                openActivity(UserPersonalCenterActivity.class, bundle);
                break;
            case 2:
                circleUserEntity = itemData;
                if (itemData.getTuanBi().equals("1")) {//加关注
                    String sign = TGmd5.getMD5(distributorid + itemData.getID());
                    fansFollowListPresenter.doFollow(distributorid, itemData.getID(), sign);
                } else {// 取消关注
                    showQuitDialog(itemData.getID());
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
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1://列表
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonOb_data = new JSONObject(data);
                        total_page = jsonOb_data.getInt("DataPageCount");
                        if (pageindex == 1) {
                            concern_fansAdapter.getFengcircleData().clear();
                        }
                        String data_one = jsonOb_data.getString("Data");
                        Gson gson = new Gson();
                        List<CircleUserEntity> circleUserEntityList = gson.fromJson(data_one, new TypeToken<List<CircleUserEntity>>() {
                        }.getType());
                        if (circleUserEntityList != null && circleUserEntityList.size() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            concern_fansAdapter.setFengcircleData(circleUserEntityList);
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
            case 3://关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "关注成功");
                        circleUserEntity.setTuanBi("2");
                        concern_fansAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 4://取消关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消关注");
                        circleUserEntity.setTuanBi("1");
                        concern_fansAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(this, "请求失败");
    }


    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

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
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}