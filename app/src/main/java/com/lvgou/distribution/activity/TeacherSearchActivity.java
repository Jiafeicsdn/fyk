package com.lvgou.distribution.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.SearchResultAdapter;
import com.lvgou.distribution.adapter.TeacherResultAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.RecommendFragment;
import com.lvgou.distribution.fragment.ShowCardFragment;
import com.lvgou.distribution.presenter.TeacherSearchPresenter;
import com.lvgou.distribution.utils.AdViewpagerUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TeacherSearchView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/15.
 * 全局搜索-讲师搜索
 */

public class TeacherSearchActivity extends BaseActivity implements TeacherSearchView, View.OnClickListener, XListView.IXListViewListener {
    private TeacherSearchPresenter teacherSearchPresenter;
    private String searchword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search);
        teacherSearchPresenter = new TeacherSearchPresenter(this);
        searchword = getIntent().getStringExtra("searchword");
        initView();
        initClick();
        serachData();
        dataList.clear();
        searchResult();
    }


    private TextView search_cancel;//取消
    private EditText et_title_search;//搜索编辑框
    private RelativeLayout et_search_delete;//删除搜索内容
    private XListView mListView;
    private TeacherResultAdapter teacherResultAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) findViewById(R.id.rl_none_one);
        search_cancel = (TextView) findViewById(R.id.search_cancel);
        et_title_search = (EditText) findViewById(R.id.et_title_search);
        et_title_search.setText(searchword);
        et_search_delete = (RelativeLayout) findViewById(R.id.et_search_delete);
        et_title_search.addTextChangedListener(new TextWatcher() {
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

        mListView = (XListView) findViewById(R.id.lv_search_result);
        teacherResultAdapter = new TeacherResultAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        teacherResultAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(teacherResultAdapter);
    }

    private void initClick() {
        search_cancel.setOnClickListener(this);
        et_search_delete.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position - 1).get("UserType").toString().equals("3")) {
                    Intent intent1 = new Intent(TeacherSearchActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position-1).get("ID").toString());
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(TeacherSearchActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position-1).get("ID").toString());
                    startActivity(intent1);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:
                finish();
                break;
            case R.id.et_search_delete:
                et_title_search.setText("");
                et_search_delete.setVisibility(View.GONE);
                break;
        }
    }


    //搜索编辑框
    private void serachData() {
        et_title_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (!v.getText().toString().trim().equals("")) {
                    searchword = v.getText().toString().trim();
                    dataList.clear();
                    searchResult();
                }
                return true;
            }
        });
    }

    private int page = 1;

    //搜索
    private void searchResult() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + searchword + page);
        showLoadingProgressDialog(this, "");
        teacherSearchPresenter.teacherSearchDatas(distributorid, searchword, page, sign);
    }

    @Override
    public void OnTeacherSearchSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            dataListTmp.clear();
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataListTmp.add(map1);
            }
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())&&Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())!=0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTeacherSearchFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeTeacherSearchProgress() {

    }


    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<TeacherSearchActivity> mActivity;

        public mainHandler(TeacherSearchActivity activity) {
            mActivity = new WeakReference<TeacherSearchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TeacherSearchActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                teacherResultAdapter.setData(dataList);
                teacherResultAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            teacherResultAdapter.setData(dataList);
            teacherResultAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }


    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        searchResult();
    }

    @Override
    public void onLoadMore() {
        page++;
        searchResult();
    }
}
