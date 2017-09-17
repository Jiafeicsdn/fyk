package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
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
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.entity.OutSeaEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.GridClassifyViewHolder;
import com.lvgou.distribution.viewholder.OutSeaViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow on 2016/5/21
 * 国内商品
 */
public class GuoNeiActivity extends BaseActivity implements OnClassifyPostionClickListener<ClassifyEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
    @ViewInject(R.id.grid_view)
    private GridView grid_view;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none_haiwai)
    private RelativeLayout rl_none_haiwai;
    @ViewInject(R.id.pull_refresh_list_haiwai)
    private PullToRefreshListView pull_refresh_list_haiwai;

    private ListView lv_list_sea_out;

    private String depth = "0";
    private String category_out = "0";
    private String keyword_out = "";
    private int pageindex_out = 1;
    private String sign_out = "";
    boolean mIsUp;// 是否上拉加载
    private int total_out_pages;

    private String shop_name;
    private String index = "";

    private ListViewDataAdapter<OutSeaEntity> outSeaEntityListViewDataAdapter;
    private ListViewDataAdapter<ClassifyEntity> classifyGridViewEntityListViewDataAdapter;// 一级分类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaout);
        ViewUtils.inject(this);
        tv_title.setText("海淘商品");
        index = getTextFromBundle("index");
        lv_list_sea_out = pull_refresh_list_haiwai.getRefreshableView();
        initViewHolder();
        if (checkNet()) {
            sign_out = TGmd5.getMD5(depth + category_out + keyword_out + pageindex_out);
            getSeaOutGoods(depth, category_out, keyword_out, pageindex_out + "", sign_out);
        }
        initCreateView();

    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * main 代码抽取
     */
    public void initCreateView() {
        pull_refresh_list_haiwai.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list_haiwai.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list_haiwai.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                keyword_out = et_search.getText().toString().trim();
                depth = "1";
                category_out = "0";
                pageindex_out = 1;
                Constants.SELECTE_POSITION04 = "0";
                sign_out = TGmd5.getMD5(depth + category_out + keyword_out + pageindex_out);
                getSeaOutGoods(depth, category_out, keyword_out, pageindex_out + "", sign_out);
                // 隐藏软键盘
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputMethodManager.isActive()) {
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 1);
//                }
                return true;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 if (et_search.getText().length() == 0) {
                                                     img_search.setVisibility(View.VISIBLE);
                                                 } else {
                                                     img_search.setVisibility(View.GONE);
                                                 }
                                             }

                                             @Override
                                             public void afterTextChanged(Editable s) {

                                             }
                                         }

        );

        pull_refresh_list_haiwai.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(GuoNeiActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pull_refresh_list_haiwai.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex_out = 1;
                sign_out = TGmd5.getMD5(depth + category_out + keyword_out + pageindex_out);
                getSeaOutGoods(depth, category_out, keyword_out, pageindex_out + "", sign_out);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex_out < total_out_pages) {
                    pageindex_out += 1;
                    sign_out = TGmd5.getMD5(depth + category_out + keyword_out + pageindex_out);
                    getSeaOutGoods(depth, category_out, keyword_out, pageindex_out + "", sign_out);
                } else {
                    MyToast.show(GuoNeiActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void initViewHolder() {
        outSeaEntityListViewDataAdapter = new ListViewDataAdapter<OutSeaEntity>();
        outSeaEntityListViewDataAdapter.setViewHolderClass(this, OutSeaViewHolder.class);
        lv_list_sea_out.setAdapter(outSeaEntityListViewDataAdapter);

        classifyGridViewEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyGridViewEntityListViewDataAdapter.setViewHolderClass(this, GridClassifyViewHolder.class);
        GridClassifyViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);
        grid_view.setAdapter(classifyGridViewEntityListViewDataAdapter);
    }


    /**
     * 海外商品有无数据页面显示
     */
    public void showOrGoneOne() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none_haiwai.setVisibility(View.GONE);
    }


    /**
     * 获取海外商品
     *
     * @param depth
     * @param category
     * @param keyword
     * @param pageindex
     * @param sign
     */
    public void getSeaOutGoods(String depth, String category, String keyword, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("depth", depth);
        maps.put("category", category);
        maps.put("keyword", keyword);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getSeaOutGoods(GuoNeiActivity.this, maps, new OnSeaOutGoodsRequestListener());
    }


    private class OnSeaOutGoodsRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    /******解析分类数据********/
                    classifyGridViewEntityListViewDataAdapter.removeAll();
                    String classify_data = array.get(0).toString();
                    JSONArray jsonArray_classify = new JSONArray(classify_data);
                    ClassifyEntity classifyEntity_ = new ClassifyEntity("0", "全部", "");
                    classifyGridViewEntityListViewDataAdapter.append(classifyEntity_);
                    if (jsonArray_classify != null) {
                        for (int i = 0; i < jsonArray_classify.length(); i++) {
                            JSONObject json_classify = jsonArray_classify.getJSONObject(i);
                            String id_ = json_classify.getString("ID");
                            String name_ = json_classify.getString("CategoryName");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id_, name_, "");
                            classifyGridViewEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }

                    /******解析列表数据********/
                    String items = array.get(1).toString();
                    JSONObject json_ = new JSONObject(items);

                    String item_data = json_.getString("Items");
                    total_out_pages = json_.getInt("TotalPages");
                    if (mIsUp == false) {
                        outSeaEntityListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    JSONArray array_data = new JSONArray(item_data);
                    if (array_data != null && array_data.length() > 0) {
                        showOrGoneOne();
                        ll_visibilty.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_data.length(); i++) {
                            JSONObject object = array_data.getJSONObject(i);
                            String id = object.getString("ID");
                            String img_path = object.getString("PicUrl");
                            String name = object.getString("ProductName");
                            String price_Market = object.getString("Price_Market");
                            String price_Sell = object.getString("Price_Min");
                            String price_Distributor = object.getString("Price_Distributor");
                            OutSeaEntity outSeaEntity = new OutSeaEntity(id, img_path, price_Market, name, price_Sell, price_Distributor);
                            outSeaEntityListViewDataAdapter.append(outSeaEntity);
                        }
                    } else {
                        showOrGoneOne();
                        rl_none_haiwai.setVisibility(View.VISIBLE);
                    }
                }else if (status.equals("0")) {
                    MyToast.show(GuoNeiActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog();

        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pull_refresh_list_haiwai.onRefreshComplete();
            dismissProgressDialog();

        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pull_refresh_list_haiwai.onRefreshComplete();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        switch (postion) {
            case 4:
                classifyGridViewEntityListViewDataAdapter.notifyDataSetChanged();
                if (itemData.getId().equals("0")) {
                    depth = "1";
                    category_out = "0";
                } else {
                    depth = "2";
                    category_out = itemData.getId();
                }
                sign_out = TGmd5.getMD5(depth + category_out + keyword_out + pageindex_out);
                getSeaOutGoods(depth, category_out, keyword_out, pageindex_out + "", sign_out);
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pull_refresh_list_haiwai.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
