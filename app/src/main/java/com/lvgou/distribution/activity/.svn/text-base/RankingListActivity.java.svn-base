package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.RankEntitiy;
import com.lvgou.distribution.presenter.RankListPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.RankListView;
import com.lvgou.distribution.viewholder.RankViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
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
 * Created by Snow on 2016/6/8 0008.
 * 排行榜列表
 */
public class RankingListActivity extends BaseActivity implements RankListView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_day)
    private RelativeLayout rl_day;
    @ViewInject(R.id.rl_week)
    private RelativeLayout rl_week;
    @ViewInject(R.id.rl_month)
    private RelativeLayout rl_month;
    @ViewInject(R.id.tv_day)
    private TextView tv_day;
    @ViewInject(R.id.tv_week)
    private TextView tv_week;
    @ViewInject(R.id.tv_month)
    private TextView tv_month;
    @ViewInject(R.id.view_day)
    private View view_day;
    @ViewInject(R.id.tv_rank_num)
    private TextView tv_rank_num;
    @ViewInject(R.id.view_week)
    private View view_week;
    @ViewInject(R.id.view_month)
    private View view_month;
    @ViewInject(R.id.tv_money)
    private TextView tv_money;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.tv_003)
    private TextView tv_003;
    private String distributorid = "";

    private String type = "1";
    private ListViewDataAdapter<RankEntitiy> rankEntitiyListViewDataAdapter;

    private RankListPresenter rankListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        ViewUtils.inject(this);
        tv_title.setText("排行榜");
        rankListPresenter = new RankListPresenter(this);
        tv_day.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_day.setVisibility(View.VISIBLE);
        distributorid = PreferenceHelper.readString(RankingListActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initViewHolder();
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid + type);
                rankListPresenter.getDataList(distributorid, type, sign);
            }
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_day, R.id.rl_week, R.id.rl_month})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_day:
                type = "1";
                initSelected();
                tv_day.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_day.setVisibility(View.VISIBLE);
                tv_003.setText("今日获得佣金");
                String sign = TGmd5.getMD5(distributorid + type);
                rankListPresenter.getDataList(distributorid, type, sign);
                lv_list.setSelection(0);
                break;
            case R.id.rl_week:
                type = "2";
                initSelected();
                tv_week.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_week.setVisibility(View.VISIBLE);
                tv_003.setText("近一周获得佣金");
                String sign_ = TGmd5.getMD5(distributorid + type);
                rankListPresenter.getDataList(distributorid, type, sign_);
                lv_list.setSelection(0);
                break;
            case R.id.rl_month:
                initSelected();
                type = "3";
                tv_month.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_month.setVisibility(View.VISIBLE);
                tv_003.setText("近一月获得佣金");
                String sign_one = TGmd5.getMD5(distributorid + type);
                rankListPresenter.getDataList(distributorid, type, sign_one);
                lv_list.setSelection(0);
                break;
        }
    }

    public void initSelected() {
        tv_day.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_week.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_month.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_day.setVisibility(View.GONE);
        view_week.setVisibility(View.GONE);
        view_month.setVisibility(View.GONE);
    }

    public void initViewHolder() {
        rankEntitiyListViewDataAdapter = new ListViewDataAdapter<RankEntitiy>();
        rankEntitiyListViewDataAdapter.setViewHolderClass(this, RankViewHolder.class);
        lv_list.setAdapter(rankEntitiyListViewDataAdapter);
    }

    
    /**
     * 数据回调解析
     * @param s
     */
    @Override
    public void excuteSuccessCallBack(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String money = array.get(0).toString();
                tv_money.setText(money);
                String times = array.get(2).toString();
                tv_rank_num.setText(times);
                Constants.MY_RANK_NUM = times;
                rankEntitiyListViewDataAdapter.removeAll();
                String one_array = array.get(1).toString();
                JSONArray array_one = new JSONArray(one_array);
                if (array_one != null && array_one.length() > 0) {
                    for (int i = 0; i < array_one.length(); i++) {
                        JSONObject jsonObject_one = array_one.getJSONObject(i);
                        String name_one = jsonObject_one.getString("DistributorName");
                        String money_one = jsonObject_one.getString("Price_Distributor");
                        RankEntitiy rankEntitiy = new RankEntitiy("", money_one, name_one, i + "");
                        rankEntitiyListViewDataAdapter.append(rankEntitiy);
                    }
                }
            } else if (status.equals("0")) {
                MyToast.show(RankingListActivity.this, jsonObject.getString("message"));
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
    public void excuteFailedCallBack(String s) {
        MyToast.show(RankingListActivity.this, s);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        rankListPresenter.attach(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rankListPresenter.dettach();
    }
}
