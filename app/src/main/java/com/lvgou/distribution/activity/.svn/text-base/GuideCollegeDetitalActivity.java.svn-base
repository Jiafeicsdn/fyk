package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/22 0022.
 * 导游学院详情 老版 可删
 */
public class GuideCollegeDetitalActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.tv_comment)
    private TextView tv_comment;
    @ViewInject(R.id.tv_comment_num)
    private TextView tv_conmment_num;
    @ViewInject(R.id.tv_priase_num)
    private TextView tv_priase;
    @ViewInject(R.id.img_share_bottom)
    private ImageView img_priase;
    @ViewInject(R.id.img_priase)
    private ImageView img_prise;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.btn_commit)
    private Button btn_publish;
    @ViewInject(R.id.rl_dialog_ios_7_root)
    private RelativeLayout mDialogRootRelativeLayout;
    @ViewInject(R.id.ll_dialog_ios_7_cotent)
    private LinearLayout mDialogCotentLinearLayout;
    @ViewInject(R.id.rl_dialog_share_root)
    private RelativeLayout rl_dialog_share_root;
    @ViewInject(R.id.ll_dialog_share_cotent)
    private LinearLayout ll_dialog_share_cotent;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_qzone)
    private RelativeLayout rl_qzone;
    @ViewInject(R.id.rl_weixin)
    private RelativeLayout rl_weixin;
    @ViewInject(R.id.rl_pengyou)
    private RelativeLayout rl_pengyou;
    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;
    private String priase_statue;// 1  可以点赞  2 不可点赞
    private String priase_num;// 点赞数据，本地加一
    @ViewInject(R.id.rl_comment)
    private RelativeLayout rl_comment;
    @ViewInject(R.id.rl_share_bottom)
    private RelativeLayout rl_share_bottom;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.rl_priase)
    private RelativeLayout rl_priase;


    private String distributorid;
    private String stuyid;
    private String share_content;
    private String share_title;

    private String root_path = Url.XIANSHANG_ROOT + "/study/studydetail/";
    private String share_url = Url.XIANSHANG_ROOT + "/study/detail/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_college_detial);
        ViewUtils.inject(this);
        tv_title.setText("蜂优学院");
        distributorid = PreferenceHelper.readString(GuideCollegeDetitalActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        stuyid = getTextFromBundle("stuyid");
        share_content = getTextFromBundle("intro_");
        share_title = getTextFromBundle("title_");
        if (checkNet()) {
            loadWeb(root_path + stuyid);
            String sign = TGmd5.getMD5(distributorid + stuyid);
            getInfo(distributorid, stuyid, sign);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_share, R.id.tv_comment, R.id.rl_priase, R.id.rl_share_bottom, R.id.btn_commit, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.tv_cancel, R.id.rl_comment, R.id.webView, R.id.rl_dialog_ios_7_root})
    public void OnClick(View view) {
        UMImage image = new UMImage(GuideCollegeDetitalActivity.this, "http://m.quygt.com/Content/images2/onerror/lgtlogo.png");
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_share:
                openDialogShare();
                break;
            case R.id.tv_comment:
                openDialog();
                break;
            case R.id.rl_priase:
                rl_priase.setClickable(false);
                if (priase_statue.equals("2")) {
                    MyToast.show(GuideCollegeDetitalActivity.this, "您已点赞！");
                    rl_priase.setClickable(true);
                } else if (priase_statue.equals("1")) {
                    String sign = TGmd5.getMD5(distributorid + stuyid);
                    doPriase(distributorid, stuyid, sign);
                }
                break;
            case R.id.rl_share_bottom:
                openDialogShare();
                break;
            case R.id.btn_commit:
                String content = et_content.getText().toString().trim();
                if (!StringUtils.isEmpty(content)) {
                    String sign = TGmd5.getMD5(distributorid + stuyid + content);
                    doComment(distributorid, stuyid, content, sign);
                }
                closeDialog();
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(GuideCollegeDetitalActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                loadWeb(root_path + stuyid + "#comment");
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(share_url + stuyid);
                web3.setTitle(share_content);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_title);//描述
                new ShareAction(GuideCollegeDetitalActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + stuyid)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(share_url + stuyid);
                web2.setTitle(share_content);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_title);//描述
                new ShareAction(GuideCollegeDetitalActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withText(share_content)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + stuyid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(share_url + stuyid);
                web1.setTitle(share_content);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_title);//描述
                new ShareAction(GuideCollegeDetitalActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + stuyid)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(share_url + stuyid);
                web.setTitle(share_content);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_title);//描述
                new ShareAction(GuideCollegeDetitalActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + stuyid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.tv_cancel:
                closeDialogShare();
                break;
            case R.id.rl_comment:
                loadWeb(root_path + stuyid + "#comment");
                break;
            case R.id.webView:
                closeDialog();
                break;
            case R.id.rl_dialog_ios_7_root:
                closeDialog();
                break;
        }
    }

    /**
     * 获取点赞评论信息
     *
     * @param distributorid
     * @param studyid
     * @param sign
     */
    public void getInfo(String distributorid, String studyid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("studyid", studyid);
        maps.put("sign", sign);
        RequestTask.getInstance().getPriaseCommit(GuideCollegeDetitalActivity.this, maps, new OnGetInfoRequestListener());
    }

    private class OnGetInfoRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String comment_num = array.get(0).toString();
                    String priase = array.get(1).toString();
                    tv_conmment_num.setText(comment_num);
                    tv_priase.setText(priase);
                    priase_statue = array.get(2).toString();
                    if (priase_statue.equals("1")) {
                        //  点赞表示  未点赞咋状态
                        img_prise.setBackgroundResource(R.mipmap.college_prise);
                    } else if (priase_statue.equals("2")) {
                        //  点赞表示  已点赞点赞咋状态不可点赞
                        img_prise.setBackgroundResource(R.mipmap.college_prise_yet);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行点赞功能
     *
     * @param distributorid
     * @param studyid
     * @param sign
     */
    public void doPriase(String distributorid, String studyid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("studyid", studyid);
        maps.put("sign", sign);
        RequestTask.getInstance().doPraise(GuideCollegeDetitalActivity.this, maps, new OnPriaseRequestListener());
    }

    private class OnPriaseRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    rl_priase.setClickable(true);
                    MyToast.show(GuideCollegeDetitalActivity.this, "点赞成功!");
                    img_prise.setBackgroundResource(R.mipmap.college_prise_yet);
                    //重新获取数据
                    String sign = TGmd5.getMD5(distributorid + stuyid);
                    getInfo(distributorid, stuyid, sign);
                } else {
                    rl_priase.setClickable(true);
                    MyToast.show(GuideCollegeDetitalActivity.this, "点赞失败!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发表评论
     *
     * @param distributorid
     * @param studyid
     * @param content
     * @param sign
     */

    public void doComment(String distributorid, String studyid, String content, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("studyid", studyid);
        maps.put("content", content);
        maps.put("sign", sign);
        RequestTask.getInstance().doCommit(GuideCollegeDetitalActivity.this, maps, new OnCommitRequestListener());

    }

    private class OnCommitRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(GuideCollegeDetitalActivity.this, "评论成功!");
                    et_content.setText("");
                    //重新获取数据
                    String sign = TGmd5.getMD5(distributorid + stuyid);
                    getInfo(distributorid, stuyid, sign);

                    loadWeb(root_path + stuyid + "#comment");
                } else {
                    MyToast.show(GuideCollegeDetitalActivity.this, "评论失败!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 弹出拍照对话框
    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    // 关闭拍照对话框
    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);

    }

    // 弹出拍照对话框
    private void openDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭拍照对话框
    private void closeDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);

    }

    /**
     * 加载网页
     *
     * @param url
     */
    public void loadWeb(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        showProgressDialog("加载中...");
        webView.loadUrl(url);
        dismissProgressDialog();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                dismissProgressDialog();
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                dismissProgressDialog();
            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
