package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.HomePageAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnFengWenPostionClickListener;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.FengwenSearchPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleUserViewHolder;
import com.lvgou.distribution.viewholder.FengWenSearcherViewHolder;
import com.lvgou.distribution.widget.FlowLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.animation.FoldLayout;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.TruncateAt.END;

/**
 * Created by Administrator on 2016/10/19.
 * 蜂文搜索
 */
public class FenwenSearchActivity extends BaseActivity implements GroupView, OnClassifyPostionClickListener<CircleUserEntity>, OnFengWenPostionClickListener<FengCircleDynamicBean>,DistributorHomeView {

    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.rl_back)
    private TextView rl_back;
    @ViewInject(R.id.rl_fengwen)
    private RelativeLayout rl_fengwen;
    @ViewInject(R.id.rl_contacts)
    private RelativeLayout rl_contacts;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;//
    @ViewInject(R.id.rl_search_before)
    private RelativeLayout rl_search_before;
    @ViewInject(R.id.flowlayout)
    private FlowLayout flowlayout;
    @ViewInject(R.id.delete_history)
    private ImageView delete_history;
    @ViewInject(R.id.et_search_delete)
    private RelativeLayout et_search_delete;//删除搜索
    @ViewInject(R.id.rl_seaech_history)
    private RelativeLayout rl_seaech_history;//删除搜索

    private Dialog dialog_del_can;// 取消，删除弹框
    private DistributorHomePresenter distributorHomePresenter;
    private String type = "";

    private String distributorid = "";
    private ListView listView;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String keyword = "";

    private ListViewDataAdapter<CircleUserEntity> circleUserEntityListViewDataAdapter;
//    private ListViewDataAdapter<FengCircleDynamicBean> fengCircleDynamicBeanListViewDataAdapter;

    private FengwenSearchPresenter fengwenSearchPresenter;


    private CircleUserEntity circleUserEntity = null;
    //    private FengCircleDynamicBean fengCircleDynamicBean = null;
    View fragment_mayknowperson;
    private Context mContext;
    private boolean isAddView = false;
    private View empty_view;
    private boolean isflag = false;

    private int userNumber;
    private int fengwenNumber;
    private HomePageAdapter homePageAdapter;
    private FengCircleDynamicBean adapterDynamicBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_fenwen);
        ViewUtils.inject(this);

        mContext = this;
        distributorid = PreferenceHelper.readString(FenwenSearchActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        fengwenSearchPresenter = new FengwenSearchPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        listView = pullToRefreshListView.getRefreshableView();
        empty_view = LayoutInflater.from(this).inflate(R.layout.none_data_layout, null);
        type = getTextFromBundle("type");
        if (type.equals("1")) {
//            et_search.setHint("搜索文章...");
            showOrGone();
            rl_contacts.setVisibility(View.GONE);
            rl_search_before.setVisibility(View.VISIBLE);
            flowlayout.setVisibility(View.VISIBLE);
            intiViewFengWenViewHolder();
        }
//        else if (type.equals("2")) {
//            showOrGone();
//            et_search.setHint("搜索用户...");
//            rl_fengwen.setVisibility(View.GONE);
//            rl_contacts.setVisibility(View.VISIBLE);
//            initViewHolderUser();
//        }

        initCreateView();
        serachData();
        initFlowDatas();
        et_search_delete.setVisibility(View.GONE);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    et_search_delete.setVisibility(View.GONE);
                } else {
                    et_search_delete.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initFlowDatas() {

        searchList = (ArrayList<String>) mcache.getAsObject("fengwen_search_list");
        if (searchList == null) {
            searchList = new ArrayList<>();
        }
        initFlowView();
        if (searchList.size() == 0) {
            rl_seaech_history.setVisibility(View.GONE);
            rl_fengwen.setVisibility(View.VISIBLE);
        } else {
            rl_seaech_history.setVisibility(View.VISIBLE);
            rl_fengwen.setVisibility(View.GONE);
        }

    }

    private void initFlowView() {
        flowlayout.relayoutToCompress();
        flowlayout.removeAllViews();
        for (int i = searchList.size() - 1; i >= 0; i--) {
            int padding1 = DensityUtil.dip2px(FenwenSearchActivity.this, 8);
            int padding4 = DensityUtil.dip2px(FenwenSearchActivity.this, 8);
            int padding5 = DensityUtil.dip2px(FenwenSearchActivity.this, 16);
            int padding6 = DensityUtil.dip2px(FenwenSearchActivity.this, 16);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(padding4, 0, padding4, padding6);
            final TextView tv = new TextView(FenwenSearchActivity.this);
            tv.setPadding(padding5, padding1, padding5, padding1);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            tv.setText(searchList.get(i));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setLines(1);
            tv.setMaxEms(10);
            tv.setEllipsize(END);
            tv.setBackgroundResource(R.drawable.bg_search_history_item);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchList.remove(tv.getText().toString().trim());
                    searchList.add(tv.getText().toString().trim());
                    mcache.put("fengwen_search_list", searchList);
                    rl_fengwen.setVisibility(View.GONE);
//                    isFirst = false;
                    et_search.setText(tv.getText().toString().trim());
                    searchword = tv.getText().toString().trim();
                    pageindex = 1;
                    searchResult(tv.getText().toString().trim());

                }
            });
            flowlayout.addView(tv, lp);
        }
    }

    @OnClick({R.id.rl_back, R.id.delete_history, R.id.et_search_delete, R.id.et_search_delete})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.delete_history://清空历史搜索
                showDelDialog();

                break;
            case R.id.et_search_delete:
                et_search.setText("");
                et_search_delete.setVisibility(View.GONE);
                break;
        }
    }

    public void showDelDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定删除历史搜索记录吗？ ");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("留着");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcache.remove("fengwen_search_list");
                if (null != searchList) {
                    searchList.clear();
                }
                rl_fengwen.setVisibility(View.VISIBLE);
                initFlowView();
                rl_search_before.setVisibility(View.GONE);
                mAlertDialog.dismiss();
            }
        });
    }

//    public void initViewHolderUser() {
//        circleUserEntityListViewDataAdapter = new ListViewDataAdapter<CircleUserEntity>();
//        circleUserEntityListViewDataAdapter.setViewHolderClass(this, CircleUserViewHolder.class);
//        CircleUserViewHolder.setCircleUserEntityOnClassifyPostionClickListener(this);
//        listView.setAdapter(circleUserEntityListViewDataAdapter);
//    }

    public void intiViewFengWenViewHolder() {
        homePageAdapter = new HomePageAdapter(this, fengwenSearchPresenter);
        homePageAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        homePageAdapter.setmAdapterListener(adapterCallBack);
        listView.setAdapter(homePageAdapter);

    }

    public HomePageAdapter.AdapterCallBack adapterCallBack = new HomePageAdapter.AdapterCallBack() {
        @Override
        public void getItemData(FengCircleDynamicBean circleDynamicBean) {
            adapterDynamicBean = circleDynamicBean;
        }
    };

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
                if (searchList == null) {
                    searchList = new ArrayList<String>();
                }
                if (!v.getText().toString().trim().equals("")) {

                    if (searchList.contains(v.getText().toString().trim())) {
                        searchList.remove(v.getText().toString().trim());
                    } else if (searchList.size() == 15) {
                        searchList.remove(0);
                    }
                    searchList.add(v.getText().toString().trim());
//                    initFlowView();
                    mcache.put("fengwen_search_list", searchList);
                    rl_fengwen.setVisibility(View.VISIBLE);
                    searchword = v.getText().toString().trim();
                    searchResult(v.getText().toString().trim());
                }
                // 执行获取数据操作
               /* if (type.equals("1")) {
                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                    fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
                }*/
                return true;
            }
        });
    }

    private String searchword = "";
    private ArrayList<String> searchList = new ArrayList<>();

    private void searchResult(String searchword) {
        String sign = TGmd5.getMD5(distributorid + searchword + pageindex);
        fengwenSearchPresenter.getUserList(distributorid, searchword, pageindex + "", sign);
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
                String label = DateUtils.formatDateTime(FenwenSearchActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                if (type.equals("1")) {
                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                    fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
                }
//                else if (type.equals("2")) {
//                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
//                    fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
//                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    if (type.equals("1")) {
                        String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                        fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
                    }
//                    else if (type.equals("2")) {
//                        String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
//                        fengwenSearchPresenter.getUserList(distributorid, keyword, pageindex + "", sign);
//                    }
                } else {
                    MyToast.show(FenwenSearchActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
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
    LinearLayout hsview_mayknowperson;

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    showOrGone();
                    JSONObject jsonObject = new JSONObject(respanse);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                    total_page = jsonObject1.getInt("DataPageCount");
                    if (pageindex == 1) {
                        homePageAdapter.getFengcircleData().clear();
                    }
                    if (isflag) {
                        listView.removeHeaderView(empty_view);
                    }
                    listView.addHeaderView(empty_view);
                    isflag = true;
                    List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
                    if (jsonArray1 != null && jsonArray1.length() > 0) {
                        isflag = false;
                        listView.removeHeaderView(empty_view);
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            FengCircleDynamicBean fengCircleDynamicBean = new FengCircleDynamicBean();
                            fengCircleDynamicBean.setID(((JSONObject) jsonArray1.get(i)).getString("ID"));
                            fengCircleDynamicBean.setDistributorID(((JSONObject) jsonArray1.get(i)).getInt("DistributorID"));
                            fengCircleDynamicBean.setDistributorName(((JSONObject) jsonArray1.get(i)).getString("DistributorName"));
                            fengCircleDynamicBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
                            fengCircleDynamicBean.setIsRZ(((JSONObject) jsonArray1.get(i)).getInt("IsRZ"));
                            fengCircleDynamicBean.setTopicID(((JSONObject) jsonArray1.get(i)).getString("TopicID"));
                            fengCircleDynamicBean.setTopicTitle(((JSONObject) jsonArray1.get(i)).getString("TopicTitle"));
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
                            circleDynamicBeans.add(fengCircleDynamicBean);
                        }
                        homePageAdapter.setFengcircleData(circleDynamicBeans);
                    } else {
                        if (pageindex == 1 && userNumber <= 0) {
                            rl_none.setVisibility(View.VISIBLE);
                        } else {
                            ll_visibility.setVisibility(View.VISIBLE);
                        }
                    }
                    homePageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    userNumber = 0;
                    String sign = TGmd5.getMD5(distributorid + keyword + pageindex);
                    fengwenSearchPresenter.getFengwenList(distributorid, keyword, "", pageindex + "", sign);
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonOb_data = new JSONObject(data);
//                        total_page = jsonOb_data.getInt("DataPageCount");
//                        if (mIsUp == false) {
//                            circleUserEntityListViewDataAdapter.removeAll();
//                        }
                        String data_one = jsonOb_data.getString("Data");
                        JSONArray array_one = new JSONArray(data_one);
                        if (array_one != null && array_one.length() > 0) {
                            userNumber = 1;
                            if (!isAddView) {
                                isAddView = true;
                                fragment_mayknowperson = LayoutInflater.from(mContext).inflate(R.layout.fragment_mayknowperson, null);
                                RelativeLayout rl_hint_title_view = (RelativeLayout) fragment_mayknowperson.findViewById(R.id.rl_hint_title_view);
                                rl_hint_title_view.setVisibility(View.GONE);
                                LinearLayout ll_view = (LinearLayout) fragment_mayknowperson.findViewById(R.id.ll_view);
                                ll_view.setVisibility(View.VISIBLE);

                                hsview_mayknowperson = (LinearLayout) fragment_mayknowperson.findViewById(R.id.hsview_mayknowperson);
                                listView.addHeaderView(fragment_mayknowperson);
                            }
                            if (hsview_mayknowperson != null) {
                                hsview_mayknowperson.removeAllViews();
                            }
//                            showOrGone();
//                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < 5; i++) {
                                JSONObject jsonObject_item = array_one.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                String realName = jsonObject_item.getString("RealName");
                                String picUrl = jsonObject_item.getString("PicUrl");
                                String TuanBi = jsonObject_item.getString("TuanBi");
                                String sex = jsonObject_item.getString("Attr");
                                String FengwenCount = jsonObject_item.getString("FengwenCount");
                                String FansCount = jsonObject_item.getString("FansCount");
//                                CircleUserEntity circleUserEntity = new CircleUserEntity(id, realName, picUrl, FengwenCount, FansCount, TuanBi, sex);
//                                circleUserEntityListViewDataAdapter.append(circleUserEntity);

                                View layout_mayknowperson = LayoutInflater.from(mContext).inflate(R.layout.layout_mayknowperson, null);
                                CircleImageView circleImageView = (CircleImageView) layout_mayknowperson.findViewById(R.id.circleImageview);
                                TextView txt_recom_course_title = (TextView) layout_mayknowperson.findViewById(R.id.txt_user_name);
                                txt_recom_course_title.setText(realName);
                                layout_mayknowperson.setTag(id);
                                DisplayImageOptions options = new DisplayImageOptions.Builder()
                                        .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                                        .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                                        .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
                                String path = ImageUtils.getInstance().getPath(id);
                                ImageLoader.getInstance().displayImage(path, circleImageView, options);
                                layout_mayknowperson.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String distributorid = PreferenceHelper.readString(FenwenSearchActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                                        String sign = TGmd5.getMD5("" + distributorid + v.getTag());
                                        showLoadingProgressDialog(FenwenSearchActivity.this, "");
                                        distributorHomePresenter.distributorHome(distributorid, v.getTag()+"", sign);

                                        /*Intent intent = new Intent(mContext, UserPersonalCenterActivity.class);
                                        intent.putExtra("distributorid", Integer.valueOf((String) v.getTag()));
                                        startActivity(intent);*/
                                    }
                                });
                                hsview_mayknowperson.addView(layout_mayknowperson);
                            }

                            if (array_one.length() > 5) {
                                View layout_more = LayoutInflater.from(mContext).inflate(R.layout.layout_more, null);
                                ImageView img_more = (ImageView) layout_more.findViewById(R.id.img_topic_more);
                                img_more.setImageResource(R.mipmap.icon_friend_more);
                                img_more.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //跳转更多认识的人
                                        Intent intent = new Intent(mContext, UserSearchActivity.class);
                                        intent.putExtra("keyword", keyword);
                                        startActivity(intent);
                                    }
                                });
                                hsview_mayknowperson.addView(layout_more);
                            }
                        } else {
//                            showOrGone();
//                            rl_none.setVisibility(View.VISIBLE);
                            if (isAddView == true) {
                                listView.removeHeaderView(fragment_mayknowperson);
                                isAddView = false;
                            }

                        }
                    } else {
//                        showOrGone();
//                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3://点赞
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "点赞成功");
                        int zan = adapterDynamicBean.getZanCount() + 1;
                        adapterDynamicBean.setZanCount(zan);
                        adapterDynamicBean.setZaned(1);
                        homePageAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4://取消点赞
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消成功");
                        int zan = adapterDynamicBean.getZanCount() - 1;
                        adapterDynamicBean.setZanCount(zan);
                        adapterDynamicBean.setZaned(0);
                        homePageAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5://关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "关注成功");
                        if (type.equals("1")) {
                            adapterDynamicBean.setFollowed("1");
                            homePageAdapter.notifyDataSetChanged();
                        } else {
                            circleUserEntity.setTuanBi("2");
                            circleUserEntityListViewDataAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6://取消关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消关注");
                        if (type.equals("1")) {
                            adapterDynamicBean.setFollowed("2");
                            homePageAdapter.notifyDataSetChanged();
                        } else {
                            circleUserEntity.setTuanBi("1");
                            circleUserEntityListViewDataAdapter.notifyDataSetChanged();
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
        rl_search_before.setVisibility(View.GONE);
        flowlayout.setVisibility(View.GONE);
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
                if (pageindex == 1 && userNumber <= 0) {
                    showOrGone();
                } else {
                    ll_visibility.setVisibility(View.VISIBLE);
                }
//                rl_none.setVisibility(View.VISIBLE);
                break;
            case 2:
                if (pageindex == 1 && userNumber <= 0) {
                    showOrGone();
                } else {
                    ll_visibility.setVisibility(View.VISIBLE);
                }
//                rl_none.setVisibility(View.VISIBLE);
                break;
            default:
                showHintDialog(respanse);
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
        dialog_del_can = new Dialog(FenwenSearchActivity.this, R.style.Mydialog);
        View view1 = View.inflate(FenwenSearchActivity.this,
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
                adapterDynamicBean = itemData;
                if (itemData.getFollowed().equals("0")) {//加关注
                    String sign_one = TGmd5.getMD5(distributorid + itemData.getDistributorID());
                    fengwenSearchPresenter.doFollow(distributorid, itemData.getDistributorID() + "", sign_one);
                } else {// 取消关注
                    showQuitDialog(itemData.getDistributorID() + "");
                }
                break;
            case 3:
                adapterDynamicBean = itemData;
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

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(FenwenSearchActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(FenwenSearchActivity.this, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
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
}
