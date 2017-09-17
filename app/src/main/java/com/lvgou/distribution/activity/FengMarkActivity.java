package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.FengWenClassifyAdapter;
import com.lvgou.distribution.adapter.GamesAdapter;
import com.lvgou.distribution.adapter.MarkAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.FindTagandTopicPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.FindTagandTopicView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/17.
 */

public class FengMarkActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, FindTagandTopicView {
    private FindTagandTopicPresenter findTagandTopicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feng_mark);
        findTagandTopicPresenter = new FindTagandTopicPresenter(this);
        checkData = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("fengmarklist");
        if (null == checkData) {
            checkData = new ArrayList<>();
        }
        initView();
        onRefresh();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XListView mListView;
    private MarkAdapter markAdapter;
    private TextView tv_sure;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("标签");
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        mListView = (XListView) findViewById(R.id.list_view);
        markAdapter = new MarkAdapter(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(markAdapter);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    public ArrayList<HashMap<String, Object>> checkData = new ArrayList<HashMap<String, Object>>();

    public void setCheckDatas(ArrayList<HashMap<String, Object>> checkData) {
        this.checkData = checkData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sure:
                mcache.put("fengmarklist", checkData);
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();

    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        findTagandTopicPresenter.findTagandTopic(distributorid, sign);

    }

    @Override
    public void OnFindTagandTopicSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            dataList.clear();
            JSONArray jsonArray = jsa.getJSONArray(0);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                for (HashMap<String, Object> stringObjectHashMap : checkData) {
                    if (stringObjectHashMap.get("ID").toString().equals(map1.get("ID").toString())) {
                        map1.put("State", "1");
                    }
                }
                dataList.add(map1);
            }
            Log.e("lkhasdfks", "--------" + dataList);
            markAdapter.setData(dataList, checkData);
            markAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnFindTagandTopicFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();

    }

    @Override
    public void closeFindTagandTopicProgress() {

    }
}
