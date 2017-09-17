package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.UserInvitePresenter;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.UserInviteView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class InviteFrendsActivity extends BaseActivity implements View.OnClickListener, UserInviteView {
    private UserInvitePresenter userInvitePresenter;
    private String shareUrl;
    private String share_content = "蜂优客，国内领先的导游移动工作平台";
    private String distributorid;
    private String pathUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_frends);
        userInvitePresenter = new UserInvitePresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        initClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initDatas();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initDatas() {
        String sign = TGmd5.getMD5("" + distributorid);
        showLoadingProgressDialog(this, "");
        userInvitePresenter.userInvite(distributorid, sign);
    }

    private RelativeLayout rl_record;
    private TextView record_num;//邀请数量
    private RelativeLayout rl_back;//返回
    private TextView tv_title;
    private ImageView im_code;//二维码
    private TextView tv_myget;//邀请好友我获得团币数
    private TextView tv_otherget;//邀请好友好友获得团币数
    private TextView tv_totalget;//已获得团币数
    private TextView tv_share;//分享
    private RelativeLayout rl_dialog_share_root;
    private LinearLayout ll_dialog_share_cotent;
    private RelativeLayout rl_qq;
    private RelativeLayout rl_qzone;
    private RelativeLayout rl_weixin;
    private RelativeLayout rl_pengyou;
    private RelativeLayout rl_dismiss;
    private TextView tv_invite_num;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("邀请好友");
        rl_record = (RelativeLayout) findViewById(R.id.rl_record);
        record_num = (TextView) findViewById(R.id.record_num);
        im_code = (ImageView) findViewById(R.id.im_code);
        tv_myget = (TextView) findViewById(R.id.tv_myget);
        tv_otherget = (TextView) findViewById(R.id.tv_otherget);
        tv_totalget = (TextView) findViewById(R.id.tv_totalget);
        tv_share = (TextView) findViewById(R.id.tv_share);
        rl_dialog_share_root = (RelativeLayout) findViewById(R.id.rl_dialog_share_root);
        ll_dialog_share_cotent = (LinearLayout) findViewById(R.id.ll_dialog_share_cotent);
        rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
        rl_qzone = (RelativeLayout) findViewById(R.id.rl_qzone);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        rl_pengyou = (RelativeLayout) findViewById(R.id.rl_pengyou);
        rl_dismiss = (RelativeLayout) findViewById(R.id.rl_dismiss);
        tv_invite_num = (TextView) findViewById(R.id.tv_invite_num);
        tv_invite_num.setText(distributorid);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_record.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
        rl_qzone.setOnClickListener(this);
        rl_weixin.setOnClickListener(this);
        rl_pengyou.setOnClickListener(this);
        rl_dismiss.setOnClickListener(this);
        im_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UMImage image = new UMImage(InviteFrendsActivity.this, "http://m.quygt.com/Content/images2/onerror/lgtlogo.png");
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_record://邀请记录
                Intent intent = new Intent(InviteFrendsActivity.this, InviteRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_share://立即分享
                openDialog();
                break;
            case R.id.rl_qq://QQ分享
                UMWeb web3 = new UMWeb(shareUrl);
                web3.setTitle("欢迎加入蜂优客平台");//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(InviteFrendsActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                closeDialog();
                break;
            case R.id.rl_qzone://QQ空间分享
                UMWeb web2 = new UMWeb(shareUrl);
                web2.setTitle("欢迎加入蜂优客平台");//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(InviteFrendsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                closeDialog();
                break;
            case R.id.rl_weixin://微信分享
                UMWeb web1 = new UMWeb(shareUrl);
                web1.setTitle("欢迎加入蜂优客平台");//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(InviteFrendsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                closeDialog();
                break;
            case R.id.rl_pengyou://朋友圈分享
                UMWeb web = new UMWeb(shareUrl);
                web.setTitle("欢迎加入蜂优客平台");//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(InviteFrendsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                closeDialog();
                break;
            case R.id.rl_dismiss://关闭分享
                closeDialog();
                break;
            case R.id.im_code:
                List<String> urls = new ArrayList<String>();
                String img_path_ = Url.ROOT + pathUrl;
                urls.add(img_path_);
                if (urls.size() >= 1) {
                    MyToast.show(InviteFrendsActivity.this, "正在保存");
                    downimage(urls);
                } else {
                    MyToast.show(InviteFrendsActivity.this, "没有图片");
                }
                break;

        }
    }

    // 弹出对话框
    private void openDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭对话框
    private void closeDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);
    }


    @Override
    public void OnUserInviteSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("askjhsdks", "-------" + respanse);
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            pathUrl = jsa.get(2).toString();
            shareUrl = jsa.get(3).toString();
            Glide.with(this).load(Url.ROOT + pathUrl).into(im_code);
            tv_myget.setText(jsa.get(0).toString());
            tv_otherget.setText(jsa.get(1).toString());
            if (jsa.get(4).toString().equals("0")) {
                record_num.setVisibility(View.GONE);
            } else {
                record_num.setVisibility(View.VISIBLE);
            }
            record_num.setText(jsa.get(4).toString());
            tv_totalget.setText(jsa.get(5).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnUserInviteFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("askjhsdks", "++++++++" + respanse);
    }

    @Override
    public void closeUserInviteProgress() {

    }
}
