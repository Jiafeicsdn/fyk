package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.lvgou.distribution.entity.FamousTeacherEntity;
import com.lvgou.distribution.entity.GuiderCollegeEntity;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.lvgou.distribution.inter.OnClassifyClickListener;
import com.lvgou.distribution.inter.OnImageItemListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.CollegeIconViewHolder;
import com.lvgou.distribution.viewholder.FamousTeacherViewHolder;
import com.lvgou.distribution.viewholder.GuideCollegeViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/21 0021.
 * 导游学院 老版 可删
 */
public class GuideCollegeActivity extends BaseActivity implements OnListItemClickListener<GuiderCollegeEntity>, OnImageItemListener<ImageViewEntity>, OnClassifyClickListener<FamousTeacherEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
    @ViewInject(R.id.rl_purchase_case)
    private RelativeLayout rl_purchase_case;
    @ViewInject(R.id.rl_teacher_lecturing)
    private RelativeLayout rl_teacher_lecturing;
    @ViewInject(R.id.rl_redskins_museum)
    private RelativeLayout rl_redskins_museum;
    @ViewInject(R.id.img_purchase_case)
    private ImageView img_purchase_case;
    @ViewInject(R.id.img_teacher_lecturing)
    private ImageView img_teacher_lecturing;
    @ViewInject(R.id.img_redskins_museum)
    private ImageView img_redskins_museum;
    @ViewInject(R.id.tv_purchase_case)
    private TextView tv_purchase_case;
    @ViewInject(R.id.tv_teacher_lecturing)
    private TextView tv_teacher_lecturing;
    @ViewInject(R.id.tv_redskins_museum)
    private TextView tv_redskins_museum;
    @ViewInject(R.id.rl_title)
    private RelativeLayout rl_title;
    @ViewInject(R.id.rl_search)
    private RelativeLayout rl_search;
    @ViewInject(R.id.rl_delete)
    private RelativeLayout rl_delete;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.ll_all)
    private LinearLayout ll_all;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_gone;
    private ListView lv_list;

    private ListViewDataAdapter<ImageViewEntity> imageViewEntityListViewDataAdapter;
    private ListViewDataAdapter<GuiderCollegeEntity> guiderCollegeEntityListViewDataAdapter;
    private ListViewDataAdapter<FamousTeacherEntity> famousTeacherEntityListViewDataAdapter;
    private String type = "8";
    private String keywords = "";
    private int pageIndex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String selected_type = "3";// 1 商品解读  2 其他
    private int famous_total_page = 0;
    private int total_page_imgage = 0;
    private String distributorid = "";
    private String index = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_college);
        ViewUtils.inject(this);
        tv_title.setText("蜂优学院");
        distributorid = PreferenceHelper.readString(GuideCollegeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        index = getTextFromBundle("index");
        initStatus();
        initCollegeViewHolder();
        lv_list = pullToRefreshListView.getRefreshableView();


        img_teacher_lecturing.setBackgroundResource(R.mipmap.bg_yellow_selected_teacher);
        tv_teacher_lecturing.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        if (checkNet()) {
            String sign_ = TGmd5.getMD5(distributorid + pageIndex + "");
            getFamousData(distributorid, pageIndex + "", sign_);
            img_search.setVisibility(View.GONE);
        }

        initCreateView();

    }


    @OnClick({R.id.rl_back, R.id.img_search, R.id.rl_purchase_case, R.id.rl_teacher_lecturing, R.id.rl_redskins_museum, R.id.rl_delete})
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
            case R.id.img_search:
                rl_title.setVisibility(View.GONE);
                rl_search.setVisibility(View.VISIBLE);
                ll_all.setVisibility(View.GONE);
                break;
            case R.id.rl_purchase_case:// 商游购案例    商品解读
                img_search.setVisibility(View.VISIBLE);
                selected_type = "1";
                initStatus();
                keywords = "";
                type = "8";
                pageIndex = 1;
                img_purchase_case.setBackgroundResource(R.mipmap.bg_yellow_selected_goods);
                tv_purchase_case.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign = TGmd5.getMD5(type + pageIndex + "" + keywords);
                getImageData(type, pageIndex + "", keywords, sign);
                break;
            case R.id.rl_teacher_lecturing:// 名师开讲
                img_search.setVisibility(View.GONE);
                selected_type = "3";
                initStatus();
                img_teacher_lecturing.setBackgroundResource(R.mipmap.bg_yellow_selected_teacher);
                pageIndex = 1;
                tv_teacher_lecturing.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign_3 = TGmd5.getMD5(distributorid + pageIndex);
                getFamousData(distributorid, pageIndex + "", sign_3);
                break;
            case R.id.rl_redskins_museum:// 红人馆
                img_search.setVisibility(View.VISIBLE);
                selected_type = "2";
                initStatus();
                keywords = "";
                img_redskins_museum.setBackgroundResource(R.mipmap.bg_yellow_selected_guan);
                type = "3";
                pageIndex = 1;
                tv_redskins_museum.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign1 = TGmd5.getMD5(type + pageIndex + "" + keywords);
                getData(type, pageIndex + "", keywords, sign1);
                break;
            case R.id.rl_delete:
                rl_title.setVisibility(View.VISIBLE);
                rl_search.setVisibility(View.GONE);
                ll_all.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * main 代码抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(GuideCollegeActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageIndex = 1;
                String sign_2 = TGmd5.getMD5(type + pageIndex + keywords);
                if (selected_type.equals("1")) {
                    getImageData(type, pageIndex + "", keywords, sign_2);
                } else if (selected_type.equals("2")) {
                    getData(type, pageIndex + "", keywords, sign_2);
                } else if (selected_type.equals("3")) {
                    String sign_ = TGmd5.getMD5(distributorid + pageIndex);
                    getFamousData(distributorid, pageIndex + "", sign_);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (selected_type.equals("1")) {
                    if (pageIndex < total_page_imgage) {
                        pageIndex += 1;
                        String sign_1 = TGmd5.getMD5(type + pageIndex + "" + keywords);
                        getImageData(type, pageIndex + "", keywords, sign_1);
                    } else {
                        MyToast.show(GuideCollegeActivity.this, "没有更多数据");
                        new CancleRefreshTask().execute();
                    }
                } else if (selected_type.equals("2")) {
                    if (pageIndex < total_page) {
                        pageIndex += 1;
                        String sign_2 = TGmd5.getMD5(type + pageIndex + "" + keywords);
                        getData(type, pageIndex + "", keywords, sign_2);
                    } else {
                        MyToast.show(GuideCollegeActivity.this, "没有更多数据");
                        new CancleRefreshTask().execute();
                    }
                } else if (selected_type.equals("3")) {
                    if (pageIndex < famous_total_page) {
                        pageIndex += 1;
                        String sign_3 = TGmd5.getMD5(distributorid + pageIndex);
                        getFamousData(distributorid, pageIndex + "", sign_3);
                    } else {
                        MyToast.show(GuideCollegeActivity.this, "没有更多数据");
                        new CancleRefreshTask().execute();
                    }
                }
            }
        });

        //搜索功能
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    keywords = et_search.getText().toString();
                    pageIndex = 1;
                    String sign = TGmd5.getMD5(type + pageIndex + keywords);
                    getData(type, pageIndex + "", keywords, sign);
                    rl_title.setVisibility(View.VISIBLE);
                    rl_search.setVisibility(View.GONE);
                    ll_all.setVisibility(View.VISIBLE);
                    // 隐藏软键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(GuideCollegeActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 初始化CollegeViewHolder
     */
    public void initCollegeViewHolder() {
        guiderCollegeEntityListViewDataAdapter = new ListViewDataAdapter<GuiderCollegeEntity>();
        guiderCollegeEntityListViewDataAdapter.setViewHolderClass(this, GuideCollegeViewHolder.class);
        imageViewEntityListViewDataAdapter = new ListViewDataAdapter<ImageViewEntity>();
        imageViewEntityListViewDataAdapter.setViewHolderClass(this, CollegeIconViewHolder.class);
        famousTeacherEntityListViewDataAdapter = new ListViewDataAdapter<FamousTeacherEntity>();
        famousTeacherEntityListViewDataAdapter.setViewHolderClass(this, FamousTeacherViewHolder.class);
        CollegeIconViewHolder.setOnImageItemListener(this);
        GuideCollegeViewHolder.setOnListItemClickListener(this);
        FamousTeacherViewHolder.setOnListItemClickListener(this);

    }

    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_gone.setVisibility(View.GONE);
    }

    /**
     * 初始化所有选中状态
     */
    public void initStatus() {
        img_purchase_case.setBackgroundResource(R.mipmap.bg_yellow_default_goods);
        img_redskins_museum.setBackgroundResource(R.mipmap.bg_yellow_default_guan);
        img_teacher_lecturing.setBackgroundResource(R.mipmap.bg_yellow_default_teacher);
        tv_purchase_case.setTextColor(getResources().getColor(R.color.bg_main_clissfy_text));
        tv_redskins_museum.setTextColor(getResources().getColor(R.color.bg_main_clissfy_text));
        tv_teacher_lecturing.setTextColor(getResources().getColor(R.color.bg_main_clissfy_text));
    }


    /**
     * 获取数据
     *
     * @param categoryid
     * @param pageindex
     * @param keyword    搜索关键字
     * @param sign
     */
    public void getData(String categoryid, String pageindex, String keyword, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("categoryid", categoryid);
        maps.put("pageindex", pageindex);
        maps.put("keyword", keyword);
        maps.put("sign", sign);
        RequestTask.getInstance().getGuideList(GuideCollegeActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {

                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String postion_id = array.get(0).toString();
                        String data = array.get(2).toString();
                        JSONObject json_ = new JSONObject(data);
                        String items = json_.getString("Items");
                        total_page = json_.getInt("TotalPages");
                        JSONArray array_data = new JSONArray(items);
                        if (mIsUp == false) {
                            guiderCollegeEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 不做处理
                        }
                        if (array_data != null && array_data.length() > 0) {
                            showRoGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject json_item = array_data.getJSONObject(i);
                                String id = json_item.getString("ID");
                                String title = json_item.getString("Title");
                                String date = json_item.getString("CreateTime");
                                String name = json_item.getString("Source");
                                String img_path = json_item.getString("PicUrl");
                                String content = json_item.getString("Intro");
                                GuiderCollegeEntity guiderCollegeEntity = new GuiderCollegeEntity(id, content, img_path, name, date, title);
                                guiderCollegeEntityListViewDataAdapter.append(guiderCollegeEntity);
                            }
                            lv_list.setAdapter(guiderCollegeEntityListViewDataAdapter);
                        } else {
                            showRoGone();
                            rl_gone.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("加载中....");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    /**
     * 获取数据
     *
     * @param categoryid
     * @param pageindex
     * @param keyword    搜索关键字
     * @param sign
     */
    public void getImageData(String categoryid, String pageindex, String keyword, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("categoryid", categoryid);
        maps.put("pageindex", pageindex);
        maps.put("keyword", keyword);
        maps.put("sign", sign);
        RequestTask.getInstance().getGuideList(GuideCollegeActivity.this, maps, new OnImageRequestListener());
    }

    private class OnImageRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String postion_id = array.get(0).toString();
                        String data = array.get(2).toString();
                        JSONObject json_ = new JSONObject(data);
                        total_page_imgage = json_.getInt("TotalPages");
                        String items = json_.getString("Items");
                        JSONArray array_data = new JSONArray(items);
                        if (mIsUp == false) {
                            imageViewEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 不做处理
                        }
                        if (array_data != null && array_data.length() > 0) {
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject json_item = array_data.getJSONObject(i);
                                String id = json_item.getString("ID");
                                String img_path = json_item.getString("PicUrl");
                                String title = json_item.getString("Title");
                                String intro_ = json_item.getString("Intro");
                                ImageViewEntity imageViewEntity = new ImageViewEntity(id, img_path, intro_, title);
                                imageViewEntityListViewDataAdapter.append(imageViewEntity);

                            }
                        } else {
                            rl_gone.setVisibility(View.VISIBLE);
                        }
                        lv_list.setAdapter(imageViewEntityListViewDataAdapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("加载中....");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }
    }


    /**
     * 获取 名人堂数据
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getFamousData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getFamousList(GuideCollegeActivity.this, maps, new OnFamousRequestListener());
    }

    private class OnFamousRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("加载中...");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String data = array.get(0).toString();
                    JSONObject json_data = new JSONObject(data);
                    famous_total_page = json_data.getInt("TotalPages");
                    String items_ = json_data.getString("Items");
                    JSONArray array_items = new JSONArray(items_);
                    if (mIsUp == false) {
                        famousTeacherEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        // 不做处理
                    }
                    if (array_items != null && array_items.length() > 0) {
                        showRoGone();
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject jsonObject_ = array_items.getJSONObject(i);
                            String id = jsonObject_.getString("ID");
                            String img_head = jsonObject_.getString("PicUrl");
                            String name = jsonObject_.getString("TeacherName");
                            String fee = jsonObject_.getString("BMTuanBi");
                            String people_num = jsonObject_.getString("People_Apply");
                            String theme = jsonObject_.getString("Theme");
                            String time = jsonObject_.getString("StartTime");
                            String states = jsonObject_.getString("State");
                            String fit_people = jsonObject_.getString("Crowd");
                            FamousTeacherEntity famousTeacherEntity = new FamousTeacherEntity(id, time, theme, people_num, fee, name, img_head, states, fit_people);
                            famousTeacherEntityListViewDataAdapter.append(famousTeacherEntity);
                        }
                        lv_list.setAdapter(famousTeacherEntityListViewDataAdapter);
                    } else {
                        showRoGone();
                        rl_gone.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查看详情操作
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(GuiderCollegeEntity itemData) {
        Bundle pBundle = new Bundle();
        pBundle.putString("stuyid", itemData.getId());
        pBundle.putString("intro_", itemData.getContent());
        pBundle.putString("title_", itemData.getTitle());
        openActivity(GuideCollegeDetitalActivity.class, pBundle);
    }

    @Override
    public void onImageItemClick(ImageViewEntity itemData) {
        Bundle pBundle = new Bundle();
        pBundle.putString("stuyid", itemData.getId());
        pBundle.putString("intro_", itemData.getIntro());
        pBundle.putString("title_", itemData.getTitle());
        openActivity(GuideCollegeDetitalActivity.class, pBundle);
    }

    @Override
    public void onClassifyClick(FamousTeacherEntity itemData) {
        Bundle pBundle = new Bundle();
        pBundle.putString("id", itemData.getId());
        pBundle.putString("index", "0");
        openActivity(FamousTeacherDetialActivity.class, pBundle);
    }


    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
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
}
