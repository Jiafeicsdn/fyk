package com.lvgou.distribution.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.MyCommentListFragment;
import com.lvgou.distribution.fragment.OtherCommentFragment;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

/**
 * Created by Snow on 2016/10/18.
 * 我的评论列表
 */
public class MyCommentListActivity extends BaseActivity{


    @ViewInject(R.id.txt_myzan)
    private TextView txt_myzan;
    @ViewInject(R.id.view01)
    private View view01;
    @ViewInject(R.id.txt_praised)
    private TextView txt_praised;
    @ViewInject(R.id.view02)
    private View view02;
    @ViewInject(R.id.fm_content)
    private FrameLayout fm_content;
    @ViewInject(R.id.rl_dialog_zhuangfa_root)
    private RelativeLayout rl_dialog_zhuangfa_root;
    @ViewInject(R.id.ll_dialog_zhuangfa_cotent)
    private LinearLayout ll_dialog_zhuangfa_cotent;
    @ViewInject(R.id.rl_dimiss_one)
    private RelativeLayout rl_zhuanfa_dimiss;
    @ViewInject(R.id.rl_fabu)
    private RelativeLayout rl_fabu;
    @ViewInject(R.id.et_content)
    private EditText et_content;

    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载

    private String talkeId = "";
    private String commentId = "";
    private int tuanbi=0;
    private String prePageLastDataObjectId = "";
    private String fenwenId = "";
    private String postion = "";

private MyCommentListFragment myCommentListFragment;
    private OtherCommentFragment otherCommentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
        ViewUtils.inject(this);
        txt_myzan.setText("我评论的");
        txt_praised.setText("被评论的");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (savedInstanceState == null) {
            otherCommentFragment = new OtherCommentFragment();
//            myCommentListFragment = new MyCommentListFragment();
//            otherCommentFragment = new OtherCommentFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.fm_content, otherCommentFragment);
//            tx.add(R.id.fm_content, otherCommentFragment).hide(otherCommentFragment);
            tx.commit();
        }

        et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_content.setSingleLine(false);
        //水平滚动设置为False
        et_content.setHorizontallyScrolling(false);
    }

    public void transmitData(String talkeId,String commentId,String userName) {
        this.talkeId=talkeId;
        this.commentId=commentId;
        et_content.setHint("回复:" + userName);
    }
public EditText getEditView(){
    return et_content;
}
    @OnClick({R.id.img_back,R.id.rl_fabu,R.id.rl_title1,R.id.rl_title2,R.id.rl_dimiss_one})
    public void OnClick(View view) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_fabu:
                String content = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    MyToast.show(MyCommentListActivity.this, "请输入内容");
                    return;
                } else {
                    String sign_ = TGmd5.getMD5(distributorid + talkeId + commentId + content+tuanbi);
                    otherCommentFragment.personalCircleListPresenter.doCommentReplay(distributorid, talkeId, commentId, content, tuanbi,sign_);
                }
                break;
            case R.id.rl_dimiss_one:
                closeDialog();
                break;
            case R.id.rl_dialog_zhuangfa_root:
                closeDialog();
                break;
            case R.id.rl_title1:
                hideFragments(tx);
                changeView();
                txt_myzan.setTextColor(getResources().getColor(R.color.text_color_333333));
                view01.setVisibility(View.VISIBLE);
                if (myCommentListFragment == null) {
                    myCommentListFragment = new MyCommentListFragment();
                    tx.add(R.id.fm_content, myCommentListFragment);
                } else {
                    tx.show(myCommentListFragment);
                }
                tx.commit();
                break;
            case R.id.rl_title2:
                hideFragments(tx);
                if (otherCommentFragment == null) {
                    otherCommentFragment = new OtherCommentFragment();
                    tx.add(R.id.fm_content, otherCommentFragment);
                } else {
                    tx.show(otherCommentFragment);
                }
                tx.commit();
                changeView();
                txt_praised.setTextColor(getResources().getColor(R.color.text_color_333333));
                view02.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void hideFragments(FragmentTransaction transaction) {
        if (myCommentListFragment != null) {
            transaction.hide(myCommentListFragment);
        }
        if (otherCommentFragment != null) {
            transaction.hide(otherCommentFragment);
        }
    }
    private void changeView() {
        txt_myzan.setTextColor(getResources().getColor(R.color.text_color_999999));
        txt_praised.setTextColor(getResources().getColor(R.color.text_color_999999));
        view01.setVisibility(View.INVISIBLE);
        view02.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 弹出评论弹框
    public void openDialog() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, true);
    }

    // 关闭评论弹框
    public void closeDialog() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, false);

    }
}
