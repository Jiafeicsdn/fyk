package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.PreferenceInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.PersonalPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.PersonalView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Swow on 2016/10/12.
 * 消息这中心，管理
 */
public class MyMessageActivity extends BaseActivity implements PersonalView {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_fengyou_group)
    private RelativeLayout rl_fengyou_group;
    @ViewInject(R.id.tv_fengyou_num)
    private TextView tv_fengyou_num;
    @ViewInject(R.id.rl_my_comment)
    private RelativeLayout rl_comment;
    @ViewInject(R.id.tv_comment_num)
    private TextView tv_comment_num;
    @ViewInject(R.id.rl_my_zan)
    private RelativeLayout rl_zan;
    @ViewInject(R.id.tv_zan_num)
    private TextView tv_zan_num;
    @ViewInject(R.id.tv_system_num)
    private TextView tv_system_num;
    private String distributorid;
    private String getNewestDistributorId = "1";
    private PersonalPresenter personalPresenter;
    private String usetisover;
    private String userstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ViewUtils.inject(this);
        tv_title.setText("我的消息");
        personalPresenter = new PersonalPresenter(this);
        usetisover = PreferenceHelper.readString(MyMessageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        userstate = PreferenceHelper.readString(MyMessageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");

        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
    }

    @OnClick({R.id.rl_back, R.id.rl_system_group, R.id.rl_fengyou_group, R.id.rl_my_comment, R.id.rl_my_zan})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_system_group:
                openActivity(SystemMessageAcitivity.class);
                break;
            case R.id.rl_fengyou_group:
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(NoticeActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }
                break;
            case R.id.rl_my_comment:
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(MyCommentListActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }
                break;
            case R.id.rl_my_zan:
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(MyZanListActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }
                break;
        }
    }

    private boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        String getnews_sign = TGmd5.getMD5(distributorid + getNewestDistributorId);
        personalPresenter.getMessageNum(distributorid, getNewestDistributorId, getnews_sign);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void OnRequestSuccCallBack(String state, String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            closeLoadingProgressDialog();
            if (status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                int uncomment = (int) jsonArray.get(0);
                int dianzanNum = (int) jsonArray.get(1);
                int classNum = (int) jsonArray.get(3);
                int systemNum = (int) jsonArray.get(4);
                int fengyouNum = (int) jsonArray.get(5);
                if (uncomment > 0) {
                    tv_comment_num.setVisibility(View.VISIBLE);
                    tv_comment_num.setText(String.valueOf(uncomment));
                } else {
                    tv_comment_num.setVisibility(View.GONE);
                }

                if (dianzanNum > 0) {
                    tv_zan_num.setVisibility(View.VISIBLE);
                    tv_zan_num.setText(String.valueOf(dianzanNum));
                } else {
                    tv_zan_num.setVisibility(View.GONE);
                }

                if (fengyouNum > 0) {
                    tv_fengyou_num.setVisibility(View.VISIBLE);
                    tv_fengyou_num.setText(String.valueOf(fengyouNum));
                } else {
                    tv_fengyou_num.setVisibility(View.GONE);
                }
                if (systemNum > 0) {
                    tv_system_num.setVisibility(View.VISIBLE);
                    tv_system_num.setText(String.valueOf(systemNum));
                } else {
                    tv_system_num.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {

    }
}
