package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.GamesAdapter;
import com.lvgou.distribution.adapter.JokeAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.TugouGamePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TugouGameView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/1.
 * 小游戏
 */

public class GamesActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, TugouGameView {
    private TugouGamePresenter tugouGamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        tugouGamePresenter = new TugouGamePresenter(this);
        initView();

        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XListView mListView;
    private GamesAdapter gamesAdapter;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("小游戏");

        mListView = (XListView) findViewById(R.id.list_view);
        gamesAdapter = new GamesAdapter(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(gamesAdapter);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        tugouGamePresenter.tugouGame(distributorid, sign);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

    @Override
    public void OnTugouGameSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            if (jsa != null && !jsa.equals("")) {
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
                    dataList.add(map1);
                }
            }
            gamesAdapter.setDatas(dataList);
            gamesAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTugouGameFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeTugouGameProgress() {

    }
}
