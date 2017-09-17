package com.lvgou.distribution.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ApplicationEntity;
import com.lvgou.distribution.inter.OnApplicationClickListener;
import com.lvgou.distribution.presenter.ApplicationPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.AppliactionView;
import com.lvgou.distribution.viewholder.ApplicationViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/6/1 0001.
 * 随时赚
 */
public class ApplicationActivity extends BaseActivity implements OnApplicationClickListener<ApplicationEntity>, AppliactionView {

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
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_right;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.rl_info)
    private RelativeLayout rl_info;
    @ViewInject(R.id.img_1)
    private ImageView img_star_one;
    @ViewInject(R.id.img_2)
    private ImageView img_star_two;
    @ViewInject(R.id.img_3)
    private ImageView img_star_three;
    @ViewInject(R.id.img_4)
    private ImageView img_star_four;
    @ViewInject(R.id.img_5)
    private ImageView img_star_five;

    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String stars;//导游星级
    private String index = "0";

    private ListViewDataAdapter<ApplicationEntity> applicationEntityListViewDataAdapter;

    private ApplicationPresenter applicationPresenter;
    private String state="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_application);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(ApplicationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        state = PreferenceHelper.readString(ApplicationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        Log.e("ksajdfhkash", "o--------------------"+state );
        initView();
        applicationPresenter = new ApplicationPresenter(this);

        index = getTextFromBundle("index");
        initViewHolder();

        initCreateView();

        /**
         * 数据加载
         */
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                showLoadingProgressDialog(ApplicationActivity.this, "");
                String sign = TGmd5.getMD5(distributorid + pageindex);
                applicationPresenter.getApplicationData(distributorid, pageindex + "", sign);
            }
        }
    }

    /**
     * 基本数据获取
     */
    public void initView() {
        tv_title.setText("选择应用");
//        rl_right.setVisibility(View.VISIBLE);
//        tv_right.setText("排行榜");
//        tv_right.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
//        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.fourteen));
        distributorid = PreferenceHelper.readString(ApplicationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();
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
                String label = DateUtils.formatDateTime(ApplicationActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                applicationPresenter.getApplicationData(distributorid, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    applicationPresenter.getApplicationData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(ApplicationActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_info})
    public void OnClick(View view) {
//        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                openActivity(RankingListActivity.class);
                break;
            case R.id.rl_info:
                openActivity(RankInfoActivity.class);
                break;
        }
    }

    /**
     * viewholder 初始化
     */
    public void initViewHolder() {
        applicationEntityListViewDataAdapter = new ListViewDataAdapter<ApplicationEntity>();
        applicationEntityListViewDataAdapter.setViewHolderClass(this, ApplicationViewHolder.class);
        ApplicationViewHolder.setOnApplicationClickListener(this);
        listView.setAdapter(applicationEntityListViewDataAdapter);
    }


    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 星级判断显示
     *
     * @param star
     */
    public void judgeStarLeve(String star) {
        switch (Integer.parseInt(star)) {
            case 0:
                img_star_one.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_two.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 1:
                img_star_one.setBackgroundResource(R.mipmap.get_star_already);
                img_star_two.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 2:
                img_star_one.setBackgroundResource(R.mipmap.get_star_already);
                img_star_two.setBackgroundResource(R.mipmap.get_star_already);
                img_star_three.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 3:
                img_star_one.setBackgroundResource(R.mipmap.get_star_already);
                img_star_two.setBackgroundResource(R.mipmap.get_star_already);
                img_star_three.setBackgroundResource(R.mipmap.get_star_already);
                img_star_four.setBackgroundResource(R.mipmap.get_star_yet);
                img_star_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 4:
                img_star_one.setBackgroundResource(R.mipmap.get_star_already);
                img_star_two.setBackgroundResource(R.mipmap.get_star_already);
                img_star_three.setBackgroundResource(R.mipmap.get_star_already);
                img_star_four.setBackgroundResource(R.mipmap.get_star_already);
                img_star_five.setBackgroundResource(R.mipmap.get_star_yet);
                break;
            case 5:
                img_star_one.setBackgroundResource(R.mipmap.get_star_already);
                img_star_two.setBackgroundResource(R.mipmap.get_star_already);
                img_star_three.setBackgroundResource(R.mipmap.get_star_already);
                img_star_four.setBackgroundResource(R.mipmap.get_star_already);
                img_star_five.setBackgroundResource(R.mipmap.get_star_already);
                break;
        }
    }

    /**
     * 获取数据成功回调
     *
     * @param response
     */
    @Override
    public void applcationSuccCallBck(String response) {
        closeLoadingProgressDialog();
        pullToRefreshListView.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String result = jsonObject.getString("result");
            if (status.equals("1")) {
                JSONArray jsonArray = new JSONArray(result);
                String data = jsonArray.get(0).toString();
                stars = jsonArray.get(1).toString();
                judgeStarLeve(stars);
                JSONObject json_data = new JSONObject(data);
                total_page = json_data.getInt("TotalPages");
                String items = json_data.getString("Items");
                if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                    applicationEntityListViewDataAdapter.removeAll();
                } else if (mIsUp == true) {
                    // 上拉加载，不清空 listViewDataAdapter
                }
                showOrGone();
                JSONArray array_items = new JSONArray(items);
                if (array_items != null && array_items.length() > 0) {
                    ll_visibility.setVisibility(View.VISIBLE);
                    for (int i = 0; i < array_items.length(); i++) {
                        JSONObject json = array_items.getJSONObject(i);
                        String id = json.getString("ID");
                        String supplyName = json.getString("SupplyName");
                        String img_path = json.getString("Logo");
                        String state = json.getString("State");
                        String star = json.getString("Star");
                        String downUrl = json.getString("DownUrl");
                        String total = json.getString("Price_Total");
                        String pregress = json.getString("FinishPlan");
                        String content = json.getString("SupplyType");
                        String price = json.getString("Price_Distributor");
                        ApplicationEntity applicationEntity = new ApplicationEntity(id, downUrl, state, price, content, supplyName, img_path, total, star, pregress);
                        applicationEntityListViewDataAdapter.append(applicationEntity);
                    }
                } else {
                    showOrGone();
                    rl_none.setVisibility(View.VISIBLE);
                }
            } else if (status.equals("0")) {
                MyToast.show(ApplicationActivity.this, jsonObject.getString("message"));
                if (jsonObject.getString("message").equals("请登录")) {
                    openActivity(LoginActivity.class);
                    finish();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据失败回调
     *
     * @param response
     */
    @Override
    public void applcationFailCallBck(String response) {
        pullToRefreshListView.onRefreshComplete();
    }

    /**
     * 申请成功回调
     *
     * @param response
     */
    @Override
    public void applySuccCallBck(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                MyToast.show(ApplicationActivity.this, "申请成功");
                String sign = TGmd5.getMD5(distributorid + pageindex);
                applicationPresenter.getApplicationData(distributorid, pageindex + "", sign);
            } else if (status.equals("0")) {
                MyToast.show(ApplicationActivity.this, jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求失败回调
     *
     * @param response
     */
    @Override
    public void applyFailCallBck(String response) {

    }

    @Override
    public void showProgress() {
        showLoadingProgressDialog(ApplicationActivity.this, "");
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    /**
     * 点击事件 处理
     *
     * @param itemdata
     * @param textView
     * @param num
     */
    @Override
    public void onApplicationClickListener(ApplicationEntity itemdata, TextView textView, int num) {
        Bundle bundle = new Bundle();
        switch (num) {
            case 1:
                bundle.putString("supplyId", itemdata.getId());
                bundle.putString("index", "1");
                openActivity(BindCodeActivity.class, bundle);
                break;
            case 2:
                if (itemdata.getState().equals("1") && itemdata.getDownload().equals("2")) {
                    bundle.putString("supplyId", itemdata.getId());
                    bundle.putString("index", "2");
                    openActivity(BindCodeActivity.class, bundle);
                } else if (itemdata.getState().equals("2")) {
                    MyToast.show(ApplicationActivity.this, "已结束");
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("0")) {
                    if (Integer.parseInt(stars) >= Integer.parseInt(itemdata.getStar())) {
                        String sign_ = TGmd5.getMD5(distributorid + itemdata.getId());
                        applicationPresenter.doApplyData(distributorid, itemdata.getId(), sign_);
                    } else {
                        MyToast.show(ApplicationActivity.this, "推广星级不足,请参考说明升级");
                    }
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("1")) {
                    MyToast.show(ApplicationActivity.this, "待审核");
                } else if (itemdata.getState().equals("1") && itemdata.getDownload().equals("3")) {
                    MyToast.show(ApplicationActivity.this, "审核失败请联系客服");
                } else if (itemdata.getState().equals("4")) {
                    MyToast.show(ApplicationActivity.this, "即将上线");
                }
                break;
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        applicationPresenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        applicationPresenter.dettach();
    }
}