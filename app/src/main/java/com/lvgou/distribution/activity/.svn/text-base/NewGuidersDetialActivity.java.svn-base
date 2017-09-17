package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
 * Created by Snow on 2016/3/21 0021.
 * 新手指南详情页
 */
public class NewGuidersDetialActivity extends BaseActivity {

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
    @ViewInject(R.id.rl_share_bottom)
    private RelativeLayout rl_share_bottom;
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
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout rl_dismiss;
    @ViewInject(R.id.rl_fengquan)
    private RelativeLayout rl_fengquan;
    @ViewInject(R.id.view_line)
    private View view_line;
    @ViewInject(R.id.rl_comment)
    private RelativeLayout rl_comment;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.rl_priase)
    private RelativeLayout rl_priase;
    private String priase_statue;// 1  可以点赞  2 不可点赞
    private String priase_num;// 点赞数据，本地加一

    private String page_id;
    private String root_path = Url.XIANSHANG_ROOT + "/study/studydetail/";
    private String distributorid;

    private String share_content = "";
    private String share_url = Url.XIANSHANG_ROOT + "/study/studydetail/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guiders_detial);
        ViewUtils.inject(this);
        tv_title.setText("操作指南");
        page_id = getTextFromBundle("page_id");
        share_content = getTextFromBundle("name");
        distributorid = PreferenceHelper.readString(NewGuidersDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                loadWeb(root_path + page_id);
            }
        }
    }


    @OnClick({R.id.rl_back, R.id.rl_share, R.id.tv_comment, R.id.rl_priase, R.id.rl_share_bottom, R.id.btn_commit, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_comment, R.id.webView, R.id.rl_dialog_ios_7_root})
    public void OnClick(View view) {
        UMImage image = new UMImage(NewGuidersDetialActivity.this, R.mipmap.fenxiangon);
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
                    MyToast.show(NewGuidersDetialActivity.this, "您已点赞！");
                    rl_priase.setClickable(true);
                } else if (priase_statue.equals("1")) {
                    String sign = TGmd5.getMD5(distributorid + page_id);
                    doPriase(distributorid, page_id, sign);
                }
                break;
            case R.id.rl_share_bottom:
                openDialogShare();
                break;
            case R.id.btn_commit:
                String content = et_content.getText().toString().trim();
                if (!StringUtils.isEmpty(content)) {
                    String sign = TGmd5.getMD5(distributorid + page_id + content);
                    doComment(distributorid, page_id, content, sign);
                }
                closeDialog();
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(NewGuidersDetialActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                loadWeb(root_path + page_id + "#comment");
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + page_id);
                web.setTitle("操作指南--蜂优客");//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(NewGuidersDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle("操作指南--蜂优客")
                        .withText(share_content)
                        .withTargetUrl(share_url + page_id)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url + page_id);
                web1.setTitle("操作指南--蜂优客");//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(NewGuidersDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle("操作指南--蜂优客")
                        .withText(share_content)
                        .withTargetUrl(share_url + page_id)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url + page_id);
                web2.setTitle("操作指南--蜂优客");//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(NewGuidersDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle("操作指南--蜂优客")
                        .withText(share_content)
                        .withTargetUrl(share_url + page_id)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url + page_id);
                web3.setTitle("操作指南--蜂优客");//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(NewGuidersDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle("操作指南--蜂优客")
                        .withText(share_content)
                        .withTargetUrl(share_url + page_id)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.rl_comment:
                loadWeb(root_path + page_id + "#comment");
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
        RequestTask.getInstance().getPriaseCommit(NewGuidersDetialActivity.this, maps, new OnGetInfoRequestListener());
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
        RequestTask.getInstance().doPraise(NewGuidersDetialActivity.this, maps, new OnPriaseRequestListener());
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
                    MyToast.show(NewGuidersDetialActivity.this, "点赞成功!");
                    img_prise.setBackgroundResource(R.mipmap.college_prise_yet);
                    //重新获取数据
                    String sign = TGmd5.getMD5(distributorid + page_id);
                    getInfo(distributorid, page_id, sign);
                } else {
                    rl_priase.setClickable(true);
                    MyToast.show(NewGuidersDetialActivity.this, "点赞失败!");
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
        RequestTask.getInstance().doCommit(NewGuidersDetialActivity.this, maps, new OnCommitRequestListener());

    }

    private class OnCommitRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(NewGuidersDetialActivity.this, "评论成功!");
                    et_content.setText("");
                    //重新获取数据
                    String sign = TGmd5.getMD5(distributorid + page_id);
                    getInfo(distributorid, page_id, sign);

                    loadWeb(root_path + page_id + "#comment");

                } else {
                    MyToast.show(NewGuidersDetialActivity.this, "评论失败!");
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
        showLoadingProgressDialog(NewGuidersDetialActivity.this, "");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
       /*
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }*/

        webView.setVisibility(View.VISIBLE);
        //webview的缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        //设置默认的字符编码
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl(url);
        dismissProgressDialog();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                closeLoadingProgressDialog();
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                closeLoadingProgressDialog();
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        webView.onPause();
    }
}