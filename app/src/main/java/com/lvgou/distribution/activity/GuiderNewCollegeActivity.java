package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.OnlineSignEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomerViewPage;
import com.lvgou.distribution.view.ImageCycleView;
import com.lvgou.distribution.viewholder.OnlineSignViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/7/27 0027.
 * 新手指南  老版  可删
 */
public class GuiderNewCollegeActivity extends BaseActivity implements OnListItemClickListener<OnlineSignEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none_one)
    private RelativeLayout rl_gone_one;
    private CustomerViewPage viewPage;
    private RelativeLayout rl_baoming;
    private RelativeLayout rl_classes_back;
    private RelativeLayout rl_teacher_seat;
    DisplayImageOptions options_head;
    private ListView lv_list;
    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String index = "0";

    private ImageCycleView mAdView;
    private View headview;
    private ArrayList<String> list_urls;
    private ArrayList<String> ids_list;

    private ListViewDataAdapter<OnlineSignEntity> onlineSignEntityListViewDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_new);
        ViewUtils.inject(this);
        tv_title.setText("蜂优学院");
        distributorid = PreferenceHelper.readString(GuiderNewCollegeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        index = getTextFromBundle("index");

        initCreateView();
        initHeadView(headview);

        String sign = TGmd5.getMD5(distributorid + pageindex);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                getFamousData(distributorid, pageindex + "", sign);
            }
        }
        initViewHolder();
    }

    public void initHeadView(View view) {
        mAdView = (ImageCycleView) view.findViewById(R.id.ad_view);
        rl_baoming = (RelativeLayout) view.findViewById(R.id.rl_baoming);
        rl_classes_back = (RelativeLayout) view.findViewById(R.id.rl_classes_back);
        rl_teacher_seat = (RelativeLayout) view.findViewById(R.id.rl_teacher_seat);

        rl_classes_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SELECTE_POSITION06 = "全部";
                Bundle bundle = new Bundle();
                bundle.putString("id", "0");
                openActivity(ClassessBackActivity.class, bundle);
            }
        });

        rl_teacher_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TeacherSeatActivity.class);
            }
        });

    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                } else {
                    finish();
                }
                break;
        }
    }

    public void initCreateView() {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headview = getLayoutInflater().inflate(R.layout.head_online_view, pullToRefreshListView, false);
        headview.setLayoutParams(layoutParams);


        lv_list = pullToRefreshListView.getRefreshableView();
        lv_list.addHeaderView(headview);

        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(GuiderNewCollegeActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                getFamousData(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getFamousData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(GuiderNewCollegeActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.startImageCycle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pushImageCycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.pushImageCycle();
    }

    public void initViewHolder() {
        onlineSignEntityListViewDataAdapter = new ListViewDataAdapter<OnlineSignEntity>();
        onlineSignEntityListViewDataAdapter.setViewHolderClass(this, OnlineSignViewHolder.class);
        OnlineSignViewHolder.setOnlineSignEntityOnListItemClickListener(this);
        lv_list.setAdapter(onlineSignEntityListViewDataAdapter);
    }

    public void getFamousData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getOnLineSign(GuiderNewCollegeActivity.this, maps, new OnFamousRequestListener());
    }

    private class OnFamousRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(GuiderNewCollegeActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
        }

        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                list_urls = new ArrayList<String>();
                ids_list = new ArrayList<String>();
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);

                    /*******************Binner*********************/
                    String url_path = array.get(1).toString();
                    JSONArray patt_array = new JSONArray(url_path);
                    list_urls.clear();
                    ids_list.clear();
                    if (patt_array != null && patt_array.length() > 0) {
                        for (int j = 0; j < patt_array.length(); j++) {
                            JSONObject img_json = patt_array.getJSONObject(j);
                            String url = img_json.getString("PicUrl");
                            String ids = img_json.getString("LinkUrl");
                            list_urls.add(Url.ROOT + url);
                            ids_list.add(ids);
                        }
                    }
                    mAdView.setImageResources(list_urls, mAdCycleViewListener);

                    /*******************列表数据*********************/
                    String data = array.get(0).toString();
                    JSONObject json_data = new JSONObject(data);
                    total_page = json_data.getInt("TotalPages");
                    String items_ = json_data.getString("Items");
                    JSONArray array_items = new JSONArray(items_);
                    if (mIsUp == false) {
                        onlineSignEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        // 不做处理
                    }

                    if (array_items != null && array_items.length() > 0) {
                        rl_gone_one.setVisibility(View.GONE);
                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject json = array_items.getJSONObject(i);
                            String id = json.getString("ID");
                            String time = json.getString("ZBTime");
                            String path = json.getString("Banner1");
                            String content = json.getString("ThemeInfo");
                            String BMTuanBi = json.getString("BMTuanBi");
                            String state = json.getString("State");
                            String People_Apply = json.getString("People_Apply");
                            String People_All = json.getString("People_All");
                            OnlineSignEntity onlineSignEntity = new OnlineSignEntity(id, People_Apply, People_All, BMTuanBi, state, content, path, time);
                            onlineSignEntityListViewDataAdapter.append(onlineSignEntity);
                        }
                    } else {
                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        rl_gone_one.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(GuiderNewCollegeActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * item 点击事件回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(OnlineSignEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        openActivity(FamousTeacherDetialActivity.class, bundle);
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
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

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            Bundle bundle = new Bundle();
            bundle.putString("id", ids_list.get(position));
            bundle.putString("index", "0");
            openActivity(FamousTeacherDetialActivity.class, bundle);
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader.getInstance().displayImage(imageURL, imageView);// 此处本人使用了ImageLoader对图片进行加装！
        }
    };

}
