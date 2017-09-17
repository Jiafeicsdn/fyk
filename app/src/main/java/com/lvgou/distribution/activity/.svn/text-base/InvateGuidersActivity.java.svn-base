package com.lvgou.distribution.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.request.extension.interfaces.OnRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Snow on 2016/4/6
 * 邀请导游
 */
public class InvateGuidersActivity extends BaseActivity {

    @ViewInject(R.id.img_code)
    private ImageView img_icon;
    @ViewInject(R.id.tv_success_num)
    private TextView tv_invition_num;// 邀请人数
    @ViewInject(R.id.tv_tuanbi_num)
    private TextView tv_integral; // 团币数
    @ViewInject(R.id.tv_prepare_num)
    private TextView tv_prepare_num;
    @ViewInject(R.id.tv_copy)
    private TextView tv_copy;//复制链接
    @ViewInject(R.id.tv_share)
    private TextView tv_share;// 分享链接
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_prepare)
    private RelativeLayout rl_prepare;
    @ViewInject(R.id.rl_success)
    private RelativeLayout rl_success;
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
    @ViewInject(R.id.tv_invition_num)
    private TextView tv_invition_numl;
    private String distributorid = "";
    private String index = "";
    private String addres_ = "";
    private String img_path;
    DisplayImageOptions options;
private String share_content="蜂优客，国内领先的导游移动工作平台";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_guider_one);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
        tv_title.setText("推荐有奖");
        distributorid = PreferenceHelper.readString(InvateGuidersActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign_ = TGmd5.getMD5(distributorid);
            if (checkNet()) {
                getData(distributorid, sign_);
            }
        }
    }

    @OnClick({R.id.rl_back, R.id.tv_copy, R.id.img_code, R.id.tv_share, R.id.rl_prepare, R.id.rl_success, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        UMImage image = new UMImage(InvateGuidersActivity.this, "http://m.quygt.com/Content/images2/onerror/lgtlogo.png");
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.tv_copy:
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", addres_);
                myClipboard.setPrimaryClip(clipData);
                MyToast.show(InvateGuidersActivity.this, "复制成功");
                break;
            case R.id.img_code:
                List<String> urls = new ArrayList<String>();
                String img_path_ = Url.ROOT + img_path;
                urls.add(img_path_);
                if (urls.size() >= 1) {
                    MyToast.show(InvateGuidersActivity.this, "正在保存");
                    downimage(urls);
                } else {
                    MyToast.show(InvateGuidersActivity.this, "没有图片");
                }
                break;
            case R.id.tv_share:
                openDialog();
                break;
            case R.id.rl_prepare:
                pBundle.putString("type", "3");
                openActivity(InvaterRecordActivity.class, pBundle);
                break;
            case R.id.rl_success:
                pBundle.putString("type", "2");
                openActivity(InvaterRecordActivity.class, pBundle);
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(addres_);
                web3.setTitle("欢迎加入蜂优客平台");//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(InvateGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle("欢迎加入蜂优客平台")
                        .withText(share_content)
                        .withTargetUrl(addres_)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(addres_);
                web2.setTitle("欢迎加入蜂优客平台");//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(InvateGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle("欢迎加入蜂优客平台")
                        .withText(share_content)
                        .withTargetUrl(addres_)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(addres_);
                web1.setTitle("欢迎加入蜂优客平台");//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(InvateGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .setCallback(umShareListener)
                        .withTitle("欢迎加入蜂优客平台")
                        .withText(share_content)
                        .withTargetUrl(addres_)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(addres_);
                web.setTitle("欢迎加入蜂优客平台");//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(InvateGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle("欢迎加入蜂优客平台")
                        .withText(share_content)
                        .withTargetUrl(addres_)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_dismiss:
                closeDialog();
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


    /**
     * 邀请导游
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().invisteGuiderInfo(InvateGuidersActivity.this, maps, new OnRequestListener());
    }


    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    tv_invition_numl.setText(distributorid);
                    img_path = array.get(0).toString();
                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                            .build();
                    ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_icon, options);
                    addres_ = array.get(1).toString();
                    String a = array.get(2).toString();
                    tv_invition_num.setText(a + "人");
                    String b = array.get(3).toString();
                    tv_integral.setText(b);
                    String c = array.get(4).toString();
                    tv_prepare_num.setText(c + "人");
                }else if (status.equals("0")) {
                    MyToast.show(InvateGuidersActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
