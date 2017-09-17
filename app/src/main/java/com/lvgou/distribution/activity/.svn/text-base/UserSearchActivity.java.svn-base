package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.Concern_FansAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnFengWenPostionClickListener;
import com.lvgou.distribution.presenter.FengwenSearchPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleUserViewHolder1;
import com.lvgou.distribution.viewholder.FengWenSearcherViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 * 蜂文搜索
 */
public class UserSearchActivity extends BaseActivity implements GroupView, Concern_FansAdapter.OnClassifyPostionClickListener, OnFengWenPostionClickListener<FengCircleDynamicBean> {

    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_fengwen)
    private RelativeLayout rl_fengwen;
    @ViewInject(R.id.rl_contacts)
    private RelativeLayout rl_contacts;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private Dialog dialog_del_can;// 取消，删除弹框

    private String type = "";

    private String distributorid = "";
    private ListView listView;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String keyword = "";

    private Concern_FansAdapter concern_fansAdapter;
    //    private ListViewDataAdapter<CircleUserEntity> circleUserEntityListViewDataAdapter;
    private ListViewDataAdapter<FengCircleDynamicBean> fengCircleDynamicBeanListViewDataAdapter;

    private FengwenSearchPresenter fengwenSearchPresenter;


    private CircleUserEntity circleUserEntity = null;
    private FengCircleDynamicBean fengCircleDynamicBean = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_fenwen);
        ViewUtils.inject(this);
        RelativeLayout rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.setVisibility(View.GONE);
        RelativeLayout rl_search_before = (RelativeLayout) findViewById(R.id.rl_search_before);
        rl_search_before.setVisibility(View.GONE);
        RelativeLayout rl_title22 = (RelativeLayout) findViewById(R.id.rl_title22);
        rl_title22.setVisibility(View.VISIBLE);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("更多用户");
        RelativeLayout rl_back22 = (RelativeLayout) findViewById(R.id.rl_back22);
        rl_back22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        fengwenSearchPresenter = new FengwenSearchPresenter(this);

        listView = pullToRefreshListView.getRefreshableView();
        keyword = getIntent().getStringExtra("keyword");
        et_search.setText(keyword);
//        type = getTextFromBundle("type");
//        if (type.equals("1")) {
//            et_search.setHint("搜索文章...");
//            showOrGone();
//            rl_fengwen.setVisibility(View.VISIBLE);
//            rl_contacts.setVisibility(View.GONE);
//            intiViewFengWenViewHolder();
//        } else if (type.equals("2")) {
        showOrGone();
        et_search.setHint("搜索用户...");
        rl_fengwen.setVisibility(View.GONE);
        rl_contacts.setVisibility(View.VISIBLE);
        initViewHolderUser();
//        }

        initCreateView();
        serachData();
        String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
        fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
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
        concern_fansAdapter = new Concern_FansAdapter(this);
        concern_fansAdapter.setOnClassifyPostionClick(this);
        listView.setAdapter(concern_fansAdapter);
    }

    public void intiViewFengWenViewHolder() {
        fengCircleDynamicBeanListViewDataAdapter = new ListViewDataAdapter<FengCircleDynamicBean>();
        fengCircleDynamicBeanListViewDataAdapter.setViewHolderClass(this, FengWenSearcherViewHolder.class);
        FengWenSearcherViewHolder.setOnFengWenPostionClickListener(this);
        listView.setAdapter(fengCircleDynamicBeanListViewDataAdapter);

    }

    /**
     * 搜索操作
     */
    public void serachData() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                keyword = et_search.getText().toString();
                pageindex = 1;
                // 执行获取数据操作
//                if (type.equals("1")) {
//                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
//                    fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
//                } else if (type.equals("2")) {
                String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
//                }
                return true;
            }
        });
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
                String label = DateUtils.formatDateTime(UserSearchActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
//                if (type.equals("1")) {
//                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
//                    fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
//                } else if (type.equals("2")) {
                String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
//                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
//                    if (type.equals("1")) {
//                        String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
//                        fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
//                    } else if (type.equals("2")) {
                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                    fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
//                    }
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.show(UserSearchActivity.this, "没有更多数据");
                            pullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
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
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                    total_page = jsonObject1.getInt("DataPageCount");
                    if (mIsUp == false) {
                        fengCircleDynamicBeanListViewDataAdapter.removeAll();
                    }
                    List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
                    if (jsonArray1 != null && jsonArray1.length() > 0) {
                        showOrGone();
                        ll_visibility.setVisibility(View.VISIBLE);
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
                                        if (((String) piclists.get(j)).indexOf("smallPicUrl") != -1) {
                                            circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                                        }
                                        if (((String) piclists.get(j)).indexOf("picUrl") != -1) {
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
                            fengCircleDynamicBean.setFollowed(((JSONObject) jsonArray1.get(i)).getString("Followed"));
                            fengCircleDynamicBean.setZaned(((JSONObject) jsonArray1.get(i)).getInt("Zaned"));
                            fengCircleDynamicBean.setSex(((JSONObject) jsonArray1.get(i)).getString("Sex"));
                            fengCircleDynamicBean.setCurrentLocation(((JSONObject) jsonArray1.get(i)).getString("CurrentLocation"));
                            fengCircleDynamicBeanListViewDataAdapter.append(fengCircleDynamicBean);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
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
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "点赞成功");
                        int zan = fengCircleDynamicBean.getZanCount() + 1;
                        fengCircleDynamicBean.setZanCount(zan);
                        fengCircleDynamicBean.setZaned(1);
                        fengCircleDynamicBeanListViewDataAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消成功");
                        int zan = fengCircleDynamicBean.getZanCount() - 1;
                        fengCircleDynamicBean.setZanCount(zan);
                        fengCircleDynamicBean.setZaned(0);
                        fengCircleDynamicBeanListViewDataAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "关注成功");
                        if (type.equals("1")) {
                            fengCircleDynamicBean.setFollowed("1");
                            fengCircleDynamicBeanListViewDataAdapter.notifyDataSetChanged();
                        } else {
                            circleUserEntity.setTuanBi("2");
                            concern_fansAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消关注");
                        if (type.equals("1")) {
                            fengCircleDynamicBean.setFollowed("2");
                            fengCircleDynamicBeanListViewDataAdapter.notifyDataSetChanged();
                        } else {
                            circleUserEntity.setTuanBi("1");
                            concern_fansAdapter.notifyDataSetChanged();
                        }
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
        rl_fengwen.setVisibility(View.GONE);
        rl_contacts.setVisibility(View.GONE);
    }


    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
            case 2:
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * 取消弹框
     */
    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(this, R.style.Mydialog);
        View view1 = View.inflate(this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                String sign = TGmd5.getMD5(distributorid + id);
                fengwenSearchPresenter.cancleFollow(distributorid, id, sign);
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
     * 好友列表，点击回调
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
                    fengwenSearchPresenter.doFollow(distributorid, itemData.getID(), sign);
                } else {// 取消关注
                    showQuitDialog(itemData.getID());
                }
                break;
        }
    }


    /**
     * 蜂文列表点击事件处理
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onFengWenPostionClick(FengCircleDynamicBean itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putInt("distributorid", itemData.getDistributorID());
                openActivity(UserPersonalCenterActivity.class, bundle);
                break;
            case 2:
                fengCircleDynamicBean = itemData;
                if (itemData.getFollowed().equals("0")) {//加关注
                    String sign_one = TGmd5.getMD5(distributorid + itemData.getDistributorID());
                    fengwenSearchPresenter.doFollow(distributorid, itemData.getDistributorID() + "", sign_one);
                } else {// 取消关注
                    showQuitDialog(itemData.getDistributorID() + "");
                }
                break;
            case 3:
                fengCircleDynamicBean = itemData;
                if (itemData.getZaned() == 1) {//点赞
                    String sign_one = TGmd5.getMD5(distributorid + itemData.getID());
                    fengwenSearchPresenter.cancleZan(distributorid, itemData.getID(), sign_one);
                } else {//
                    String sign = TGmd5.getMD5(distributorid + itemData.getID());
                    fengwenSearchPresenter.doZan(distributorid, itemData.getID(), sign);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fengwenSearchPresenter.attach(this);
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fengwenSearchPresenter.dettach();
        MobclickAgent.onPause(this);
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
}
