package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleCommentZanViewHolder;
import com.lvgou.distribution.viewholder.CircleCommentZanViewOneHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/11/7.
 * 关注，未读消息列表
 */
public class MessageListActivity extends BaseActivity implements GroupView, OnClassifyPostionClickListener<CircleCommZanEntity>,DistributorHomeView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.rl_dialog_zhuangfa_root)
    private RelativeLayout rl_dialog_zhuangfa_root;
    @ViewInject(R.id.ll_dialog_zhuangfa_cotent)
    private LinearLayout ll_dialog_zhuangfa_cotent;
    @ViewInject(R.id.rl_dimiss_one)
    private RelativeLayout rl_zhuanfa_dimiss;
    @ViewInject(R.id.rl_fabu)
    private RelativeLayout rl_fabu;
    @ViewInject(R.id.tv_title_one)
    private TextView tv_title_one;
    @ViewInject(R.id.et_content)
    private EditText et_content;


    private String talkeId = "";
    private String commentId = "";
    private String fenwenId = "";
    private int tuanbi = 0;
    private ListViewDataAdapter<CircleCommZanEntity> circleCommZanEntityListViewDataAdapter;

    private PersonalCircleListPresenter personalCircleListPresenter;
    private DistributorHomePresenter distributorHomePresenter;

    String distributorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_unmessage_list);
        ViewUtils.inject(this);
        tv_title.setText("消息列表");
        distributorid = PreferenceHelper.readString(MessageListActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        personalCircleListPresenter = new PersonalCircleListPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);

        initViewHolder();


        if (checkNet()) {
            showLoadingProgressDialog(MessageListActivity.this, "");
            String sign = TGmd5.getMD5(distributorid);
            personalCircleListPresenter.getReadUnreadMessageList(distributorid, sign);

        }
    }


    @OnClick({R.id.rl_back, R.id.rl_fabu, R.id.rl_dimiss_one, R.id.rl_dialog_zhuangfa_root})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_fabu:
                String content_ = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(content_)) {
                    MyToast.show(MessageListActivity.this, "请输入内容");
                    return;
                } else {
                    String sign_ = TGmd5.getMD5(distributorid + talkeId + commentId + content_ + tuanbi);
                    personalCircleListPresenter.doCommentReplay(distributorid, talkeId, commentId, content_, tuanbi, sign_);
                }
                break;
            case R.id.rl_dimiss_one:
                closeDialog();
                break;
            case R.id.rl_dialog_zhuangfa_root:
                closeDialog();
                break;

        }
    }

    public void initViewHolder() {
        circleCommZanEntityListViewDataAdapter = new ListViewDataAdapter<CircleCommZanEntity>();
        circleCommZanEntityListViewDataAdapter.setViewHolderClass(this, CircleCommentZanViewOneHolder.class);
        CircleCommentZanViewOneHolder.setCircleCommZanEntityOnClassifyPostionClickListener(this);
        lv_list.setAdapter(circleCommZanEntityListViewDataAdapter);
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
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        closeDialog();
                        MyToast.show(MessageListActivity.this, "回复成功");
                        String sign = TGmd5.getMD5(distributorid);
                        personalCircleListPresenter.getReadUnreadMessageList(distributorid, sign);
                        lv_list.setSelection(0);
                    } else {
                        closeDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");

                    String sign_one = TGmd5.getMD5(distributorid + "3");
                    personalCircleListPresenter.seekReadUnreadMessageList(distributorid, "3", sign_one);

                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONArray array_data = new JSONArray(data);
                        if (array_data != null && array_data.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject jsonObject_item = array_data.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                String fengwenID = jsonObject_item.getString("FengwenID");
                                String distributorID = jsonObject_item.getString("DistributorID");
                                String name = jsonObject_item.getString("DistributorName");
                                String userType = jsonObject_item.getString("UserType");
                                String messageType = jsonObject_item.getString("MessageType");
                                String IsRZ = jsonObject_item.getString("IsRZ");
                                String title = jsonObject_item.getString("Content");
                                String content = jsonObject_item.getString("FengwenTitle");
                                String time = jsonObject_item.getString("CreateTime");
                                String replayName = jsonObject_item.getString("ReplyDistributorName");
                                String replyDistributorID = jsonObject_item.getString("ReplyDistributorID");
                                if (messageType.equals("1")) {
                                    CircleCommZanEntity circleCommZanEntity = new CircleCommZanEntity(id, fengwenID, distributorID, name, userType, IsRZ, time, "1", messageType, title, distributorid, content, replayName, "", replyDistributorID);
                                    circleCommZanEntityListViewDataAdapter.append(circleCommZanEntity);
                                } else if (messageType.equals("2")) {
                                    CircleCommZanEntity circleCommZanEntity = new CircleCommZanEntity(id, fengwenID, distributorID, name, userType, IsRZ, time, "2", messageType, title, distributorid, content, replayName, "", replyDistributorID);
                                    circleCommZanEntityListViewDataAdapter.append(circleCommZanEntity);
                                }
                            }
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
            case 6:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String one_data = array.get(0).toString();
                        JSONObject jsonObject_ = new JSONObject(one_data);
                        String id = jsonObject_.getString("ID");
                        String user_type = jsonObject_.getString("UserType");
                        String content = jsonObject_.getString("Title");
                        String img_path = jsonObject_.getString("PicUrl");
                        String userType = jsonObject_.getString("UserType");
                        String distributorID = jsonObject_.getString("DistributorID");
                        String name = jsonObject_.getString("SourceDistributorName");
                        String ZanCount = jsonObject_.getString("ZanCount");
                        String CommentCount = jsonObject_.getString("CommentCount");
                        String SourceDistributorID = jsonObject_.getString("SourceDistributorID");
                        String Zaned = jsonObject_.getString("Zaned");
                        if (user_type.equals("1")) {
                            Intent intent = new Intent(MessageListActivity.this, NewRecomFengWenDetailsActivity.class);
                            intent.putExtra("position", "0");
                            intent.putExtra("talkId", fenwenId);
                            startActivityForResult(intent, 0);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("talkId", fenwenId);
                            openActivity(NewDynamicDetailsActivity.class, bundle);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
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
            case 4:
                MyToast.show(MessageListActivity.this, respanse);
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
            case 6://{"status":0,"message":"动态已删除","result":null}
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    final String message = jsonObject.getString("message");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.show(MessageListActivity.this, message);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        personalCircleListPresenter.attach(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personalCircleListPresenter.dettach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    // 弹出评论弹框
    private void openDialog() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, true);
    }

    // 关闭评论弹框
    private void closeDialog() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, false);

    }

    /**
     * item 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(CircleCommZanEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                fenwenId = itemData.getFenwenId();
                String sign_ = TGmd5.getMD5(distributorid + itemData.getFenwenId());
                personalCircleListPresenter.getUserType(distributorid, itemData.getFenwenId(), sign_);
                break;
            case 2:
                String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + itemData.getUserId());
                showLoadingProgressDialog(this, "");
                distributorHomePresenter.distributorHome(distributorid, itemData.getUserId(), sign);
                break;
            case 3:
                String distributorid2 = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign2 = TGmd5.getMD5("" + distributorid2 + itemData.getUserId());
                showLoadingProgressDialog(this, "");
                distributorHomePresenter.distributorHome(distributorid2, itemData.getUserId(), sign2);

                break;
            case 4:
                openDialog();
                talkeId = itemData.getFenwenId();
                commentId = itemData.getId();
                break;
        }
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
                Intent intent1 = new Intent(MessageListActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(MessageListActivity.this, DistributorHomeActivity.class);
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
}
