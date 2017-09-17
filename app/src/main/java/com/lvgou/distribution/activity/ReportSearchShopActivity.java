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
import android.util.Log;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportInfoEntitiy;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.ReportShopPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ReportShopView;
import com.lvgou.distribution.viewholder.ReportSearchHistoryViewHolder;
import com.lvgou.distribution.widget.FlowLayout;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
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

import static android.text.TextUtils.TruncateAt.END;

/**
 * Created by Snow on 2016/9/14.
 * 报备搜索
 */
public class ReportSearchShopActivity extends BaseActivity implements OnClassifyPostionClickListener<ReportInfoEntitiy>, ReportShopView {
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search02)
    private EditText et_search02;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pull_refresh_list;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
    @ViewInject(R.id.ll_item_report1)
    private LinearLayout ll_item_report1;
    @ViewInject(R.id.tv_tittle_report1)
    private TextView tv_tittle_report1;
    @ViewInject(R.id.iv_item_report1)
    private ImageView iv_item_report1;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visiable;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int totalPages;
    public ListView listView;


    private ListViewDataAdapter<ReportInfoEntitiy> reportInfoEntitiyListViewDataAdapter;


    private ReportShopPresenter reportShopPresenter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private View headSearchView;
    private FlowLayout flowlayout;
    private RelativeLayout rl_search_before;
    private ImageView delete_history;//清空历史搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_shop);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        distributorid = PreferenceHelper.readString(ReportSearchShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pull_refresh_list.getRefreshableView();
        headSearchView = LayoutInflater.from(this).inflate(R.layout.search_history_header, null);
        flowlayout = (FlowLayout) headSearchView.findViewById(R.id.flowlayout);
        rl_search_before = (RelativeLayout) headSearchView.findViewById(R.id.rl_search_before);
        delete_history = (ImageView) headSearchView.findViewById(R.id.delete_history);
        listView.addHeaderView(headSearchView);
        reportShopPresenter = new ReportShopPresenter(this);


        serachData();
        initCreateView();
        initViewHolder();
        String sign = TGmd5.getMD5(distributorid + pageindex);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                reportShopPresenter.getReportShopData(distributorid, pageindex + "", sign);
            }
        }
        delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != searchList) {

                    showDialog();
                }
            }
        });
    }

    public void showDialog() {

        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定删除历史搜索记录吗?");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        yes.setText("删除");
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("不删除");
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
                mAlertDialog.dismiss();
                mcache.remove("report_search_list");
                searchList.clear();
                rl_search_before.setVisibility(View.GONE);
                initFlowView();
            }
        });
    }

   /* private Dialog dialog_quit;

    public void showDialog() {
        dialog_quit = new Dialog(ReportSearchShopActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ReportSearchShopActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView title = (TextView) view1.findViewById(R.id.tv_title);
        title.setText("确定删除历史搜索记录吗?");
        cancle.setText("不删除");
        sure.setText("删除");

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcache.remove("report_search_list");
                searchList.clear();
                rl_search_before.setVisibility(View.GONE);
                initFlowView();
                dialog_quit.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        reportShopPresenter.attach(this);
        initFlowDatas();
    }

    private ArrayList<String> searchList = new ArrayList<>();

    private void initFlowDatas() {
        searchList = (ArrayList<String>) mcache.getAsObject("report_search_list");
        if (searchList != null) {
            if (searchList.size() == 0) {
                rl_search_before.setVisibility(View.GONE);
            } else {
                initFlowView();
            }
        } else {
            rl_search_before.setVisibility(View.GONE);
        }

    }

    private void initFlowView() {
//        rl_search_before.setVisibility(View.VISIBLE);
        flowlayout.relayoutToCompress();
        flowlayout.removeAllViews();
        for (int i = searchList.size() - 1; i >= 0; i--) {
            int padding1 = DensityUtil.dip2px(ReportSearchShopActivity.this, 8);
            int padding4 = DensityUtil.dip2px(ReportSearchShopActivity.this, 8);
            int padding5 = DensityUtil.dip2px(ReportSearchShopActivity.this, 16);
            int padding6 = DensityUtil.dip2px(ReportSearchShopActivity.this, 16);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(padding4, 0, padding4, padding6);
            final TextView tv = new TextView(ReportSearchShopActivity.this);
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
                    mcache.put("report_search_list", searchList);
                    String searchword = tv.getText().toString().trim();
                    et_search02.setText(searchword);
                    Bundle pBundle = new Bundle();
                    pBundle.putString("key", searchword);
                    openActivity(Report_SearchShop_Result.class, pBundle);

                }
            });
            flowlayout.addView(tv, lp);
        }
    }

    /**
     * 初始化viewHolder
     */
    public void initViewHolder() {
        reportInfoEntitiyListViewDataAdapter = new ListViewDataAdapter<ReportInfoEntitiy>();
        reportInfoEntitiyListViewDataAdapter.setViewHolderClass(this, ReportSearchHistoryViewHolder.class);
        ReportSearchHistoryViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(reportInfoEntitiyListViewDataAdapter);
    }


    /**
     * 空数据显示隐藏
     */
    public void showOrgone() {
        ll_visiable.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    public void showOneDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("请输入关键字");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    /**
     * 搜索操作
     */
    public void serachData() {
        et_search02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
                    rl_search_before.setVisibility(View.VISIBLE);
                    initFlowView();
                    mcache.put("report_search_list", searchList);


                    Bundle pBundle = new Bundle();
                    pBundle.putString("key", et_search02.getText().toString().trim());
                    openActivity(Report_SearchShop_Result.class, pBundle);
                } else {
                    showOneDialog();
                }

                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                // 执行获取数据操作
                return true;
            }
        });

        et_search02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search02.getText().length() == 0) {
                    img_search.setVisibility(View.VISIBLE);
                } else {
                    img_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initCreateView() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ReportSearchShopActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pull_refresh_list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                reportShopPresenter.getReportShopData(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < totalPages) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    reportShopPresenter.getReportShopData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(ReportSearchShopActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 成功回调
     *
     * @param response
     */
    @Override
    public void applcationSuccCallBck(String response) {
        Log.e("kjhskajdf", "**********" + response);
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                String result = jsonObject.getString("result");
                if (mIsUp == false) {
                    reportInfoEntitiyListViewDataAdapter.removeAll();
                } else {

                }
                JSONArray jsonArray = new JSONArray(result);
               /* String stringArr = jsonArray.get(0).toString();
                JSONArray js = new JSONArray(stringArr);
                if (pageindex == 1) {
                    for (int j = 0; j < js.length(); j++) {
                        JSONObject js6 = js.getJSONObject(j);
                        String id = js6.getString("ReportSellerID");
                        String shopName = js6.getString("ShopName");
                        String adderss = js6.getString("Adderss");
                        String business = js6.getString("Business");
                        String latitude = js6.getString("Latitude");
                        String longitude = js6.getString("Longitude");
                        String img_path = js6.getString("Logo");
                        ReportInfoEntitiy reportInfoEntitiy = new ReportInfoEntitiy(id, shopName, adderss, latitude, longitude, Constants.Latitude, Constants.Longitude, business, js.length() + "", "0");
                        reportInfoEntitiy.setImg_path(img_path);
                        reportInfoEntitiyListViewDataAdapter.append(reportInfoEntitiy);
                    }
                }*/
                JSONObject js3 = new JSONObject(jsonArray.get(0).toString());

                totalPages = js3.getInt("TotalPages");
                String items1 = js3.getString("Items");
                JSONArray jsonArray1 = new JSONArray(items1);
                if (jsonArray1 != null && jsonArray1.length() > 0) {
                    for (int k = 0; k < jsonArray1.length(); k++) {
                        JSONObject jsonObject3 = jsonArray1.getJSONObject(k);
//                        int size = js.length();
                        String id = jsonObject3.getString("ID");
                        String shopName = jsonObject3.getString("ShopName");
                        String adderss = jsonObject3.getString("Adderss");
                        String business = jsonObject3.getString("Business");
                        String latitude = jsonObject3.getString("Latitude");
                        String longitude = jsonObject3.getString("Longitude");
                        String img_path = jsonObject3.getString("Logo");
                        ReportInfoEntitiy reportInfoEntitiy = new ReportInfoEntitiy(id, shopName, adderss, latitude, longitude, Constants.Latitude, Constants.Longitude, business, 0 + "", "1");
                        reportInfoEntitiy.setImg_path(img_path);
                        reportInfoEntitiyListViewDataAdapter.append(reportInfoEntitiy);
                    }
                }
                /**
                 * 只有两个数组都为空的时候，才显示空数据js.length() == 0 &&
                 */
                if (jsonArray1.length() == 0) {
                    showOrgone();
                    rl_none.setVisibility(View.VISIBLE);
                } else {
                    showOrgone();
                    ll_visiable.setVisibility(View.VISIBLE);
                }
            } else {
                MyToast.show(ReportSearchShopActivity.this, jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 失败回调
     *
     * @param response
     */
    @Override
    public void applcationFailCallBck(String response) {
        pull_refresh_list.onRefreshComplete();
        MyToast.show(ReportSearchShopActivity.this, "请求失败");
    }

    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }

    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                Constants.IS_SHOW_ADD = "0";
                finish();
                break;
            case R.id.rl_publish:
                bundle.putString("index", "0");
                openActivity(Report_Shop_Location_Activity.class, bundle);
                break;
            default:
                break;
        }
    }

    /**
     * item 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ReportInfoEntitiy itemData, int postion) {
        Log.e("jghsajdfs", "----------" + postion);
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                /*bundle.putString("index", "1");
                bundle.putString("reportId", "0");
                bundle.putString("shop_name", itemData.getShopname());
                bundle.putString("reportSellerId", itemData.getId());
                openActivity(ReportShopActivity.class, bundle);*/
                Intent intent = new Intent(ReportSearchShopActivity.this, MerchantCenterActivity.class);
                intent.putExtra("reportid", itemData.getId());
                startActivity(intent);
                break;
            case 2:
                try {
                    bundle.putString("index", "1");
                    bundle.putString("name", itemData.getShopname());
                    bundle.putString("lat", itemData.getLatitude());
                    bundle.putString("lon", itemData.getLongitude());
                    bundle.putString("address", itemData.getAddress());
                    openActivity(Report_Shop_Location_Activity.class, bundle);
                } catch (Exception e) {
                }
                break;
        }
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
            pull_refresh_list.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        reportShopPresenter.dettach();
    }


    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.MAP_REFRESH) {
            reportInfoEntitiyListViewDataAdapter.notifyDataSetChanged();
        }
    }
}
