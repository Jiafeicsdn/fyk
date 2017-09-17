package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.DetailsMenuAdapter;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.MenuBean;
import com.lvgou.distribution.bean.ZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.fragment.CommentFragment;
import com.lvgou.distribution.fragment.ZanFragment;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.DynamicDetailsPresenter;
import com.lvgou.distribution.presenter.TalkisnormalPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.DynamicDetailsView;
import com.lvgou.distribution.view.HorizontalListView;
import com.lvgou.distribution.view.TalkisnormalView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wx.goodview.GoodView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.common.utils.ScreenUtils;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 官方蜂文详情
 */
public class NewRecomFengWenDetailsActivity extends BaseCircleActivity implements DynamicDetailsView, ZanFragment.OnArticleSelectedListener, CommentFragment.OnArticleSelectedListener, DistributorHomeView, TalkisnormalView {
    DynamicDetailsPresenter dynamicDetailsPresenter;
    private DistributorHomePresenter distributorHomePresenter;
    private String distributorid, distributorName;
    private int currPage = 1;
    private String talkisnormal_sign = "";
    private String talkcommentlist_sign = "";
    private String talkId = "";
    private int zaned;
    private int zancount;
    private int commentcount;
    private TalkisnormalPresenter talkisnormalPresenter;
    @ViewInject(R.id.txt_zan_numer)
    private TextView txt_zan_numer;
    @ViewInject(R.id.img_zan)
    private ImageView img_zan;
    @ViewInject(R.id.txt_details_title)
    private TextView txt_details_title;
    @ViewInject(R.id.txt_user_name)
    private TextView txt_user_name;
    /*    @ViewInject(R.id.img_sex)
        private ImageView img_sex;*/
    @ViewInject(R.id.img_collect)
    private ImageView img_collect;
    @ViewInject(R.id.et_evaluate)
    private EditText et_evaluate;
    //    @ViewInject(R.id.web_view)
    private WebView web_view;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_horizontalmenu)
    private HorizontalListView menu;

    @ViewInject(R.id.img_head_pic)
    private CircleImageView img_head_pic;
    @ViewInject(R.id.edit_comment)
    private EditText edit_comment;
    @ViewInject(R.id.img_send)
    private ImageView img_send;
    @ViewInject(R.id.img_dismiss)
    private ImageView img_dismiss;
    @ViewInject(R.id.rl_dialog_comment_root)
    private RelativeLayout rl_dialog_comment_root;
    @ViewInject(R.id.ll_dialog_comment_cotent)
    private LinearLayout ll_dialog_comment_cotent;
    @ViewInject(R.id.rl_dialog_zhuangfa_root)
    private RelativeLayout rl_dialog_zhuangfa_root;
    @ViewInject(R.id.ll_dialog_zhuangfa_cotent)
    private LinearLayout ll_dialog_zhuangfa_cotent;
    @ViewInject(R.id.rl_dimiss_one)
    private RelativeLayout rl_zhuanfa_dimiss;
    @ViewInject(R.id.rl_fabu)
    private RelativeLayout rl_fabu;
    @ViewInject(R.id.et_content)
    private EditText et_zhuanfa;
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

    TextView txt_praise;
    @ViewInject(R.id.txt_createTime)
    private TextView txt_createTime;
    @ViewInject(R.id.txt_read_count)
    private TextView txt_read_count;
    @ViewInject(R.id.txt_look_original)
    private TextView txt_look_original;
    @ViewInject(R.id.img_concern)
    ImageView img_concern;
    @ViewInject(R.id.img_share)
    ImageView img_share;
    @ViewInject(R.id.ll_zan)
    LinearLayout ll_zan;
    @ViewInject(R.id.ll_webview)
    RelativeLayout ll_webview;
    @ViewInject(R.id.txt_collect_numer)
    TextView txt_collect_numer;//收藏数量
    private int position;
    //    private FengCircleCommentAdapter fengCircleCommentAdapter;
    private CircleCommentBean circleCommentBean;
    int layer;//层级
    int comment_position;//点击的评论item
    private String comment_content;
    private int dataPageCount = 0;
    private ListView listView;
    private LinearLayout empty_view;

    private String share_content = "蜂圈-小伙伴刚刚分享了自己的带团日常，快来围观吧";

    private String share_title = "";
    private String share_url = "http://agent.quygt.com/circle/detail?talkId=";
    UMImage share_image = null;

    private Dialog dialog_quit;

    private String recommenttalkcontent_sign = "";

    private CircleRecommentEntity itemData;
    private String sex;
    private String prePageLastDataObjectId = "";

    private String push_talkId = "";
    private FengCircleDynamicBean fengCircleDynamicBean;
    private int tuanbi = 0;
    CommentFragment commentFragment;
    ZanFragment zanFragment;
    List<MenuBean> menulist;
    DetailsMenuAdapter menuAdapter;
    private List<Fragment> fragmentList;
    private LinearLayout ll_comment_view;
    private RelativeLayout rl_top_title;

    private int zanListSize = 0;
    private String state;
    private String userType;
    GoodView goodView;
    private LinearLayout ll_collect;
    private LinearLayout ll_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_head_details);
        ViewUtils.inject(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        talkisnormalPresenter = new TalkisnormalPresenter(this);
        showLoadingProgressDialog(this, "");
        goodView = new GoodView(this);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        rl_top_title = (RelativeLayout) findViewById(R.id.rl_top_title);
        ll_comment_view = (LinearLayout) findViewById(R.id.ll_comment_view);
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        ll_comment_view.setVisibility(View.GONE);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        distributorName = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        sex = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        state = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        userType = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        dynamicDetailsPresenter = new DynamicDetailsPresenter(this);
        dynamicDetailsPresenter.attach(this);
        talkId = intent.getStringExtra("talkId");
        String sign_one = TGmd5.getMD5(distributorid + talkId);
        dynamicDetailsPresenter.getTalkDetial(distributorid, talkId, sign_one);
        talkisnormal_sign = TGmd5.getMD5(talkId);
        talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);
        recommenttalkcontent_sign = TGmd5.getMD5(talkId);


//        dynamicDetailsPresenter.talkisnormal(talkId, talkisnormal_sign);
        dynamicDetailsPresenter.recommenttalkcontent(talkId, recommenttalkcontent_sign);
        fragmentList = new ArrayList<>();
        commentFragment = new CommentFragment();
        fragmentList.add(commentFragment);
        zanFragment = new ZanFragment();
        fragmentList.add(zanFragment);
        menulist = new ArrayList();
        menuAdapter = new DetailsMenuAdapter(this);
        menuAdapter.setMenuList(menulist);
        menu.setAdapter(menuAdapter);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {

                return fragmentList.get(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.e("kjashdkfhasd", "-------" + position);
                if (position == 0) {
                    ll_bottom.setVisibility(View.VISIBLE);
                    viewPager.setPadding(0, 0, 0, (int) ScreenUtils.dpToPx(NewRecomFengWenDetailsActivity.this, 80));
                } else {
                    ll_bottom.setVisibility(View.GONE);
                    viewPager.setPadding(0, 0, 0, (int) ScreenUtils.dpToPx(NewRecomFengWenDetailsActivity.this, 40));
                }
                if (position == 1) {
                    rl_fabu.setVisibility(View.GONE);
                } else {
                    rl_fabu.setVisibility(View.VISIBLE);
                }
                ((DetailsMenuAdapter) menu.getAdapter()).setCurOrderItem(position);
            }
        });

    }

    public void showActivityCommentChildView() {
        ll_comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_comment_view.setVisibility(View.GONE);
            }
        });
        ll_comment_view.setVisibility(View.VISIBLE);
        edit_comment.requestFocus();
        edit_comment.setText("");
        if (layer == 1) {
            edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
        } else {
            edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(comment_position).getDistributorName());
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public void requestCommentTalk() {
        if (TextUtils.isEmpty(comment_content)) {
            MyToast.show(NewRecomFengWenDetailsActivity.this, "评论内容不能为空");
            return;
        }
        String commentId = "";
        if (circleCommentBean != null) {
            commentId = circleCommentBean.getID();
        }
        String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content + tuanbi);
        dynamicDetailsPresenter.commenttalk(distributorid, talkId, commentId, comment_content, tuanbi, sign);
    }


//    FengCircleCommentAdapter.CircleCommentCallBack circleCommentCallBack = new FengCircleCommentAdapter.CircleCommentCallBack() {
//        @Override
//        public void showComment(CircleCommentBean circleCommentBean, int layer, int position) {
//            RecomFengWenDetailsActivity.this.layer = layer;
//            RecomFengWenDetailsActivity.this.comment_position = position;
//            edit_comment.setText("");
//            if (layer == 1) {
//                edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
//            } else {
//                edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(position).getDistributorName());
//            }
//            openDialogCommentShare();
//            RecomFengWenDetailsActivity.this.circleCommentBean = circleCommentBean;
//        }
//    };


    @OnClick({R.id.ll_collect, R.id.img_share, R.id.tv_sned, R.id.img_concern, R.id.ll_zan, R.id.txt_look_original, R.id.img_send, R.id.txt_comment, R.id.rl_back, R.id.img_send, R.id.img_dismiss, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_fengquan, R.id.rl_dialog_comment_root, R.id.img_head_pic, R.id.txt_user_name})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_share:
                openDialogShare();
                break;
            case R.id.ll_zan:
                //点赞
                String sign_zan = TGmd5.getMD5(distributorid + talkId);
                if (fengCircleDynamicBean.getZaned() == 1) {
                    dynamicDetailsPresenter.CircleunZan(distributorid, talkId, sign_zan, position);
                    img_zan.setImageResource(R.mipmap.unzan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#a3a3a3"));
                    goodView.setImage(R.mipmap.unzan_icon);
                    goodView.show(ll_zan);
                } else {
                    dynamicDetailsPresenter.CircleZan(distributorid, talkId, sign_zan, position);
                    img_zan.setImageResource(R.mipmap.zan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#d5aa5f"));
                    goodView.setImage(R.mipmap.zan_icon);
                    goodView.show(ll_zan);
                }
               /* img_zan.startAnimation(AnimationUtils.loadAnimation(
                        this, R.anim.dianzan_anim));*/
                break;
            case R.id.img_concern:
                String friendId = String.valueOf(fengCircleDynamicBean.getSourceDistributorID());
                String sign = TGmd5.getMD5(distributorid + friendId);
                if (fengCircleDynamicBean.getFollowed().equals("1")) {
                    showQuitDialog(friendId, sign, position);
                } else {
                    dynamicDetailsPresenter.CircleFollow(distributorid, String.valueOf(friendId), sign, position);
                }
                break;
            case R.id.txt_look_original:
                String sign1 = TGmd5.getMD5(fengCircleDynamicBean.getSourceFengwenID());
                showLoadingProgressDialog(this, "");
                talkisnormalPresenter.talkisnormal(fengCircleDynamicBean.getSourceFengwenID(), sign1);


                break;
            case R.id.rl_back:
                Intent intent = new Intent();
                intent.setAction("com.distribution.tugou.top.state");
                if (itemData == null) {
                    itemData = new CircleRecommentEntity();
                }
                itemData.setID(talkId);
                if (fengCircleDynamicBean != null) {
                    itemData.setZaned(String.valueOf(fengCircleDynamicBean.getZaned()));
                    itemData.setZanCount(String.valueOf(fengCircleDynamicBean.getZanCount()));
                    itemData.setCommentCount(String.valueOf(fengCircleDynamicBean.getCommentCount()));
                }
                intent.putExtra("itemData", itemData);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.ll_collect:
                if (fengCircleDynamicBean.getCollectioned() == 1) {
                    String talkcollect_sign = TGmd5.getMD5(distributorid + talkId);
                    dynamicDetailsPresenter.talkuncollection(distributorid, talkId, talkcollect_sign);
                } else {
                    String talkcollect_sign = TGmd5.getMD5(distributorid + talkId);
                    dynamicDetailsPresenter.talkcollection(distributorid, talkId, talkcollect_sign);
                }
                break;
            case R.id.img_send:
                comment_content = edit_comment.getText().toString();
                requestCommentTalk();
                ll_comment_view.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
                break;
            case R.id.img_dismiss:
                ll_comment_view.setVisibility(View.GONE);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
                break;
            case R.id.tv_sned:
                comment_content = et_evaluate.getText().toString();
                circleCommentBean = null;
                requestCommentTalk();
                break;
//            case R.id.txt_comment:
//                circleCommentBean = null;
//                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
//                edit_comment.setText("");
//                openDialogCommentShare();
//                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(share_url + talkId);
                web3.setTitle(share_title);//标题
                web3.setThumb(share_image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(NewRecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(share_url + talkId);
                web2.setTitle(share_title);//标题
                web2.setThumb(share_image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(NewRecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(share_url + talkId);
                web1.setTitle(share_title);//标题
                web1.setThumb(share_image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(NewRecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(share_url + talkId);
                web.setTitle(share_title);//标题
                web.setThumb(share_image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(NewRecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.rl_dialog_comment_root:
                closeDialogCommentShare();
                break;
            case R.id.img_head_pic:
            case R.id.txt_user_name:
                String seeDistributorID = "";
                if (fengCircleDynamicBean.getSourceDistributorID() > 0) {
                    seeDistributorID = fengCircleDynamicBean.getSourceDistributorID() + "";
                } else {
                    seeDistributorID = fengCircleDynamicBean.getDistributorID() + "";
                }
                String sign2 = TGmd5.getMD5("" + distributorid + seeDistributorID);
                showLoadingProgressDialog(this, "");
                distributorHomePresenter.distributorHome(distributorid, seeDistributorID, sign2);
                break;
        }
    }

    @Override
    public void talkisnormalResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 0) {
                showErrorDialog(resonpse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void talkcommentlistResponse(String resonpse) {

    }


    @Override
    public void commenttalkResponse(String resonpse) {
//        closeDialogCommentShare();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                String comment_id = "";
                JSONArray result = jsonObject.getJSONArray("result");
                if (result != null && result.length() > 0) {
                    comment_id = (String) result.get(0);
                }
                //成功
                if (circleCommentBean != null) {
                    List<CircleCommentBean> list = circleCommentBean.getItem_circleCommentBeans();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    CircleCommentBean itemcircleCommentBean = new CircleCommentBean();
                    itemcircleCommentBean.setContent(comment_content);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    String sqlit[] = str.split(" ");
                    str = sqlit[0] + "T" + sqlit[1];
                    itemcircleCommentBean.setCreateTime(str);
                    itemcircleCommentBean.setID(comment_id);
                    itemcircleCommentBean.setSex(sex);
                    itemcircleCommentBean.setUserType(Integer.valueOf(userType));
                    if ("5".equals(state)) {
                        itemcircleCommentBean.setIsRZ(1);
                    }
                    itemcircleCommentBean.setDistributorID(Integer.valueOf(distributorid));
                    itemcircleCommentBean.setDistributorName(distributorName);
                    if (layer == 2) {
                        itemcircleCommentBean.setReplyDistributorID(circleCommentBean.getItem_circleCommentBeans().get(comment_position).getDistributorID());
                        itemcircleCommentBean.setReplyDistributorName(circleCommentBean.getItem_circleCommentBeans().get(comment_position).getDistributorName());
                    } else {
                        itemcircleCommentBean.setReplyDistributorID(circleCommentBean.getDistributorID());
                        itemcircleCommentBean.setReplyDistributorName(circleCommentBean.getReplyDistributorName());
                    }
                    list.add(itemcircleCommentBean);
                    circleCommentBean.setItem_circleCommentBeans(list);
                } else {
                    CircleCommentBean circleCommentBean = new CircleCommentBean();
                    circleCommentBean.setContent(comment_content);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    String sqlit[] = str.split(" ");
                    str = sqlit[0] + "T" + sqlit[1];
                    circleCommentBean.setCreateTime(str);
                    circleCommentBean.setID(comment_id);
                    circleCommentBean.setSex(sex);
                    circleCommentBean.setUserType(Integer.valueOf(userType));
                    if ("5".equals(state)) {
                        circleCommentBean.setIsRZ(1);
                    }
                    circleCommentBean.setDistributorID(Integer.valueOf(distributorid));
                    circleCommentBean.setDistributorName(distributorName);
                    commentFragment.fengCircleCommentAdapter.addcircleCommentData(circleCommentBean);
                    commentFragment.ChangeUiView(commentFragment.fengCircleCommentAdapter.getcircleCommentData().size());

                }
                int comment_num = menuAdapter.getMenuList().get(0).getNumer();
                comment_num++;
                menuAdapter.getMenuList().get(0).setNumer(comment_num);
                menuAdapter.notifyDataSetChanged();
                commentFragment.fengCircleCommentAdapter.notifyDataSetChanged();
                et_evaluate.setText("");
                et_evaluate.setHint("想说点什么,就写在这里吧");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void zanResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                fengCircleDynamicBean.setZaned(1);
                fengCircleDynamicBean.setZanCount(fengCircleDynamicBean.getZanCount() + 1);
                if (fengCircleDynamicBean.getZanCount() > 0) {
                    txt_zan_numer.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
                } else {
                    txt_zan_numer.setText("");
                }
                if (fengCircleDynamicBean.getZaned() == 1) {
                    img_zan.setImageResource(R.mipmap.zan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#d5aa5f"));
                } else {
                    img_zan.setImageResource(R.mipmap.unzan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#a3a3a3"));
                }
                ZanListBean zanListBean = new ZanListBean();
                zanListBean.setSex(Integer.valueOf(sex));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                String sqlit[] = str.split(" ");
                str = sqlit[0] + "T" + sqlit[1];
                zanListBean.setCreateTime(str);
                zanListBean.setDistributorID(distributorid);
                zanListBean.setDistributorName(distributorName);
                zanFragment.zanListAdapter.addcircleZanData(zanListBean);
                zanFragment.ChangeUiView(zanFragment.zanListAdapter.getZanList().size());
                zanFragment.zanListAdapter.notifyDataSetChanged();
                zanListSize = zanFragment.zanListAdapter.getCount();
                changeZanView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unzanResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                fengCircleDynamicBean.setZaned(0);
                fengCircleDynamicBean.setZanCount(fengCircleDynamicBean.getZanCount() - 1);
                if (fengCircleDynamicBean.getZanCount() > 0) {
                    txt_zan_numer.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
                } else {
                    txt_zan_numer.setText("");
                }
                if (fengCircleDynamicBean.getZaned() == 1) {
                    img_zan.setImageResource(R.mipmap.zan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#d5aa5f"));
                } else {
                    img_zan.setImageResource(R.mipmap.unzan_icon);
                    txt_zan_numer.setTextColor(Color.parseColor("#a3a3a3"));
                }
                for (int i = 0; i < zanFragment.zanListAdapter.getZanList().size(); i++) {
                    if (zanFragment.zanListAdapter.getZanList().get(i).getDistributorID().equals(distributorid)) {
                        zanFragment.zanListAdapter.getZanList().remove(i);
                        break;
                    }
                }
                zanFragment.ChangeUiView(zanFragment.zanListAdapter.getZanList().size());
                zanFragment.zanListAdapter.notifyDataSetChanged();
                zanListSize = zanFragment.zanListAdapter.getCount();
                changeZanView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void changeZanView() {
        menuAdapter.getMenuList().get(1).setNumer(zanListSize);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void followResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                fengCircleDynamicBean.setFollowed(String.valueOf(1));
                if (fengCircleDynamicBean.getFollowed().equals("1")) {
                    img_concern.setImageResource(R.mipmap.circle_follow_already);
                    img_concern.setVisibility(View.GONE);
                } else {
                    img_concern.setImageResource(R.mipmap.circle_add_follow);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unfollowResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                fengCircleDynamicBean.setFollowed(String.valueOf(0));
                if (fengCircleDynamicBean.getFollowed().equals("1")) {
                    img_concern.setImageResource(R.mipmap.circle_follow_already);
                    img_concern.setVisibility(View.GONE);
                } else {
                    img_concern.setImageResource(R.mipmap.circle_add_follow);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转发
     *
     * @param resonpse
     */
    @Override
    public void zhuanfa(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                MyToast.show(NewRecomFengWenDetailsActivity.this, "转发成功");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取详情数据
     *
     * @param resonpse
     */
    @Override
    public void getTalkDetial(String resonpse) {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeLoadingProgressDialog();
                }
            }, 1000);
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String data = array.get(0).toString();
                JSONObject jsonObject_data = new JSONObject(data);
                zaned = jsonObject_data.getInt("Zaned");
                zancount = jsonObject_data.getInt("ZanCount");
                commentcount = jsonObject_data.getInt("CommentCount");
                share_title = jsonObject_data.getString("Title");
                fengCircleDynamicBean = new FengCircleDynamicBean();
                fengCircleDynamicBean.setCollectCount(Integer.parseInt(array.get(3).toString()));
                fengCircleDynamicBean.setID(jsonObject_data.getString("ID"));
                fengCircleDynamicBean.setDistributorID(jsonObject_data.getInt("DistributorID"));
                fengCircleDynamicBean.setDistributorName(jsonObject_data.getString("DistributorName"));
                fengCircleDynamicBean.setUserType(jsonObject_data.getInt("UserType"));
                fengCircleDynamicBean.setIsRZ(jsonObject_data.getInt("IsRZ"));
                fengCircleDynamicBean.setCategoryIDs(jsonObject_data.getString("CategoryIDs"));
                JSONArray jsonCategory = jsonObject_data.getJSONArray("CategoryNames");
                List<String> categoryNames = new ArrayList<>();
                for (int j = 0; j < jsonCategory.length(); j++) {
                    categoryNames.add((String) jsonCategory.get(j));
                }
                fengCircleDynamicBean.setCategoryNames(categoryNames);
                fengCircleDynamicBean.setTitle(jsonObject_data.getString("Title"));
                fengCircleDynamicBean.setContent(jsonObject_data.getString("Content"));
                fengCircleDynamicBean.setPicUrl(jsonObject_data.getString("PicUrl"));
                JSONArray piclists = jsonObject_data.getJSONArray("PicJson");
                List<FengCircleImageBean> circleImageBeans = new ArrayList<>();
                if (piclists != null) {
                    for (int j = 0; j < piclists.length(); j++) {
                        FengCircleImageBean circleImageBean = new FengCircleImageBean();
                        if (((String) piclists.get(j)).indexOf("{") != -1) {
                            JSONObject jsonObject2 = new JSONObject((String) piclists.get(j));
                            if (((String) piclists.get(j)).indexOf("smallPicUrl") != -1) {
                                circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                            }
                            if (((String) piclists.get(j)).indexOf("picUrl") != -1) {
                                circleImageBean.setPicUrl(Url.ROOT + jsonObject2.getString("picUrl"));
                            }
                            circleImageBean.setHeight(jsonObject2.getInt("height"));
                            circleImageBean.setWidth(jsonObject2.getInt("width"));
                        }
                        circleImageBeans.add(circleImageBean);
                    }
                }
                fengCircleDynamicBean.setmImgUrlList(circleImageBeans);
                fengCircleDynamicBean.setZanCount(jsonObject_data.getInt("ZanCount"));
                fengCircleDynamicBean.setCommentCount(jsonObject_data.getInt("CommentCount"));
                fengCircleDynamicBean.setSourceDistributorID(jsonObject_data.getInt("SourceDistributorID"));
                fengCircleDynamicBean.setSourceDistributorName(jsonObject_data.getString("SourceDistributorName"));
                fengCircleDynamicBean.setSourceTitle(jsonObject_data.getString("SourceTitle"));
                fengCircleDynamicBean.setCreateTime(jsonObject_data.getString("CreateTime"));
                if (!distributorid.equals(jsonObject_data.getInt("DistributorID"))) {
                    fengCircleDynamicBean.setFollowed(jsonObject_data.getString("Followed"));
                }
                fengCircleDynamicBean.setTopicTitle(jsonObject_data.getString("TopicTitle"));
                fengCircleDynamicBean.setTopicID(jsonObject_data.getString("TopicID"));
                fengCircleDynamicBean.setZaned(jsonObject_data.getInt("Zaned"));
                fengCircleDynamicBean.setHits(jsonObject_data.getInt("Hits"));
                fengCircleDynamicBean.setCollectioned(jsonObject_data.getInt("Collectioned"));
                fengCircleDynamicBean.setSourceFengwenID(jsonObject_data.getString("SourceFengwenID"));
                initView(fengCircleDynamicBean);
                if (!"null".equals(fengCircleDynamicBean.getPicUrl()) && fengCircleDynamicBean.getPicUrl() != null && !"".equals(fengCircleDynamicBean.getPicUrl())) {
                    share_image = new UMImage(NewRecomFengWenDetailsActivity.this, Url.ROOT + fengCircleDynamicBean.getPicUrl());
                } else {
                    share_image = new UMImage(NewRecomFengWenDetailsActivity.this, R.mipmap.ic_launcher);
                }

                MenuBean menuBean = new MenuBean();
                menuBean.setTitle("评论");
                menuBean.setNumer(fengCircleDynamicBean.getCommentCount());
                menulist.add(menuBean);
                MenuBean menuBean1 = new MenuBean();
                menuBean1.setTitle("赞");
                menuBean1.setNumer(fengCircleDynamicBean.getZanCount());
                menulist.add(menuBean1);
                menuAdapter.setMenuList(menulist);
                menuAdapter.setAdaptercallback(new DetailsMenuAdapter.Adaptercallback() {
                    @Override
                    public void Itemclick(int position) {
                        viewPager.setCurrentItem(position);
                    }
                });
                menuAdapter.notifyDataSetChanged();
//                share_content = fengCircleDynamicBean.getContent();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView(FengCircleDynamicBean fengCircleDynamicBean) {
        if (fengCircleDynamicBean != null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
            String path = "";
            if (fengCircleDynamicBean.getSourceDistributorID() == 0) {
                path = ImageUtils.getInstance().getPath(String.valueOf(fengCircleDynamicBean.getDistributorID()));
                txt_user_name.setText(fengCircleDynamicBean.getDistributorName());
                txt_user_name.setTextColor(Color.parseColor("#d5aa5f"));
            } else {
                path = ImageUtils.getInstance().getPath(String.valueOf(fengCircleDynamicBean.getSourceDistributorID()));
                txt_user_name.setText(fengCircleDynamicBean.getSourceDistributorName());
            }
            ImageLoader.getInstance().displayImage(path, img_head_pic, options);
            if (!fengCircleDynamicBean.getSourceFengwenID().equals("null") && !" ".equals(fengCircleDynamicBean.getSourceFengwenID())) {
                txt_look_original.setVisibility(View.VISIBLE);
            } else {
                txt_look_original.setVisibility(View.GONE);
            }
            if (String.valueOf(fengCircleDynamicBean.getDistributorID()).equals(distributorid)) {
                img_concern.setVisibility(View.GONE);
            } else {
                img_concern.setVisibility(View.VISIBLE);
            }
            if (fengCircleDynamicBean.getFollowed().equals("1")) {
                img_concern.setImageResource(R.mipmap.yiguanzhu);
                img_concern.setVisibility(View.GONE);
            } else {
                img_concern.setImageResource(R.mipmap.circle_add_follow);
            }
            if (fengCircleDynamicBean.getZaned() == 1) {
                img_zan.setImageResource(R.mipmap.zan_icon);
                txt_zan_numer.setTextColor(Color.parseColor("#d5aa5f"));
            } else {
                img_zan.setImageResource(R.mipmap.unzan_icon);
                txt_zan_numer.setTextColor(Color.parseColor("#a3a3a3"));

            }
            if (fengCircleDynamicBean.getSourceFengwenID() == null || fengCircleDynamicBean.getSourceFengwenID().equals("")) {
                txt_look_original.setVisibility(View.GONE);
            } else {
                txt_look_original.setVisibility(View.VISIBLE);
            }
          /*  if (String.valueOf(fengCircleDynamicBean.getSex()).equals("1")) {
                img_sex.setImageResource(R.mipmap.icon_man);
            } else {
                img_sex.setImageResource(R.mipmap.icon_woman);
            }*/
            if (fengCircleDynamicBean.getCollectioned() == 1) {
                txt_collect_numer.setText(fengCircleDynamicBean.getCollectCount() + "");
                txt_collect_numer.setTextColor(Color.parseColor("#d5aa5f"));
                img_collect.setImageResource(R.mipmap.collect_icon);
            } else {
                if (fengCircleDynamicBean.getCollectCount() > 0) {
                    txt_collect_numer.setText(fengCircleDynamicBean.getCollectCount() + "");
                    txt_collect_numer.setTextColor(Color.parseColor("#a3a3a3"));
                } else {
                    txt_collect_numer.setVisibility(View.GONE);
                }
                img_collect.setImageResource(R.mipmap.uncollect_icon);
            }
            if (fengCircleDynamicBean.getZanCount() > 0) {
                txt_zan_numer.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
            } else {
                txt_zan_numer.setText("");
            }
            if (null != fengCircleDynamicBean.getTopicTitle() && !fengCircleDynamicBean.getTopicTitle().equals("") && !fengCircleDynamicBean.getTopicTitle().equals("null")) {
                FaXianParseEmojiMsgUtil.getExpressionString(this, fengCircleDynamicBean.getID(), txt_details_title, fengCircleDynamicBean.getTitle(), fengCircleDynamicBean.getTopicTitle(), fengCircleDynamicBean.getTopicID());
            } else {
                ParseEmojiMsgUtil.getExpressionString(this, fengCircleDynamicBean.getID(), txt_details_title, fengCircleDynamicBean.getTitle());
            }
//            ParseEmojiMsgUtil.getExpressionString(this, fengCircleDynamicBean.getID(), txt_details_title, fengCircleDynamicBean.getTitle());
//            txt_details_title.setText(fengCircleDynamicBean.getTitle());
            String readCount = String.format(getResources().getString(R.string.text_no_read_count), fengCircleDynamicBean.getHits() + "");
            txt_read_count.setText(readCount);
            if (fengCircleDynamicBean.getCreateTime() != null && fengCircleDynamicBean.getCreateTime().length() > 0) {
                String[] str = fengCircleDynamicBean.getCreateTime().split("T");
                //2016-04-22 16:32:50
                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_e = dfs.format(new Date());
                String date_b = str[0] + " " + str[1];
                try {
                    Date begin = dfs.parse(date_b);
                    Date end = dfs.parse(date_e);
                    long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                    long month1 = between / (30 * 24 * 3600);
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600;
                    long minute1 = between / 60;
                    if (minute1 == 0) {
                        txt_createTime.setText("刚刚");
                    } else if (minute1 < 60) {
                        txt_createTime.setText(minute1 + "分钟前");
                    } else if (hour1 < 24) {
                        txt_createTime.setText(hour1 + "小时前");
                    } else if (day1 < 30) {
                        txt_createTime.setText(day1 + "天前");
                    } else if (month1 < 12) {
                        txt_createTime.setText(month1 + "月前");
                    } else {
                        txt_createTime.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void recommenttalkcontent_response(String resonpse) {
        try {
//            dynamicDetailsPresenter.talkcommentlist(talkId, prePageLastDataObjectId, currPage, talkcommentlist_sign);
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (jsonArray != null && jsonArray.length() > 0) {
                    web_view = new WebView(this);
                    web_view.loadDataWithBaseURL(null, jsonArray.get(0).toString(), "text/html", "utf-8", null);
                    web_view.setBackgroundColor(getResources().getColor(R.color.bg_white));
                    ll_webview.addView(web_view);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void talkcollectionResponse(String type, String resonpse) {
        int txt_collect_numercount = fengCircleDynamicBean.getCollectCount();
        switch (type) {
            case "collect":
                img_collect.setImageResource(R.mipmap.collect_icon);
                fengCircleDynamicBean.setCollectioned(1);
                fengCircleDynamicBean.setCollectCount(txt_collect_numercount + 1);
                txt_collect_numer.setVisibility(View.VISIBLE);
                txt_collect_numer.setText(txt_collect_numercount + 1 + "");
                txt_collect_numer.setTextColor(Color.parseColor("#d5aa5f"));
                break;
            case "uncollect":
                img_collect.setImageResource(R.mipmap.uncollect_icon);
                fengCircleDynamicBean.setCollectioned(0);
                fengCircleDynamicBean.setCollectCount(txt_collect_numercount - 1);
                if (txt_collect_numercount - 1 <= 0) {
                    txt_collect_numer.setVisibility(View.GONE);
                } else {
                    txt_collect_numer.setText(txt_collect_numercount - 1 + "");
                    txt_collect_numer.setTextColor(Color.parseColor("#a3a3a3"));
                }
                break;
        }
    }

    @Override
    public void excuteFailedCallBack(String type, String resonpse) {
        closeLoadingProgressDialog();
        switch (type) {
            case "commenttalk":
                Log.e("jsadhfjhaf", "------1------");
                showHintDialog(resonpse);
                break;
            case "zan":
                Log.e("jsadhfjhaf", "------2------");
                showHintDialog(resonpse);
                break;
            case "Detial":
                Log.e("jsadhfjhaf", "------3------");
                showStop(resonpse);
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {
    }

    @Override
    public void excuteFailedCallBack(String s) {
        closeLoadingProgressDialog();
        showStop(s);
    }

    public String getPath(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        return path;
    }

    private void hidekeyword() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
    }

    private void showEditCommentDialog() {
//        layout_edit.setVisibility(View.VISIBLE);
//        edit_comment.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(edit_comment, InputMethodManager.SHOW_FORCED);
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


    // 弹出拍照对话框
    private void openDialogCommentShare() {
        performDialogAnimation(rl_dialog_comment_root,
                ll_dialog_comment_cotent, true);
    }

    // 关闭拍照对话框
    private void closeDialogCommentShare() {
        performDialogAnimation(rl_dialog_comment_root,
                ll_dialog_comment_cotent, false);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.top.state");
        intent.putExtra("position", position);
        if (itemData == null) {
            itemData = new CircleRecommentEntity();
        }
        itemData.setID(talkId);
        if (fengCircleDynamicBean != null) {
            itemData.setZaned(String.valueOf(fengCircleDynamicBean.getZaned()));
            itemData.setZanCount(String.valueOf(fengCircleDynamicBean.getZanCount()));
            itemData.setCommentCount(String.valueOf(fengCircleDynamicBean.getCommentCount()));
        }
        intent.putExtra("itemData", itemData);
        sendBroadcast(intent);
        finish();
    }

    //退出登录
    public void showQuitDialog(final String friendId, final String sign, final int position) {
        dialog_quit = new Dialog(NewRecomFengWenDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(NewRecomFengWenDetailsActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDetailsPresenter.CircleUnFollow(distributorid, friendId, sign, position);
                dialog_quit.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

    //错误提示框
    public void showErrorDialog(String str) {
        dialog_quit = new Dialog(NewRecomFengWenDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(NewRecomFengWenDetailsActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(str);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
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
        if (dynamicDetailsPresenter != null) {
            dynamicDetailsPresenter.dettach();
        }
    }

    public void showStop(String str) {
        dialog_quit = new Dialog(NewRecomFengWenDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(NewRecomFengWenDetailsActivity.this, R.layout.dialog_show_check_stop, null);
        TextView sure = (TextView) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(str);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                finish();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

    @Override
    public String getDistributorId() {
        return distributorid;
    }

    @Override
    public String getTalkId() {
        return talkId;
    }

    @Override
    public void UpdateCommentNum() {
        int comment_num = menuAdapter.getMenuList().get(0).getNumer();
        comment_num--;
        menuAdapter.getMenuList().get(0).setNumer(comment_num);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeData(int layer, CircleCommentBean circleCommentBean, int comment_position) {
        this.layer = layer;
        this.circleCommentBean = circleCommentBean;
        this.comment_position = comment_position;
    }

    @Override
    public void showCommentChildView() {
        showActivityCommentChildView();
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
                Intent intent1 = new Intent(NewRecomFengWenDetailsActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(NewRecomFengWenDetailsActivity.this, DistributorHomeActivity.class);
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

    @Override
    public void OnTalkisnormalSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jso = new JSONObject(respanse);
            JSONArray jsa = jso.getJSONArray("result");
            if (jsa.get(0).toString().equals("true")) {
                Intent intent_recom = new Intent(this, NewDynamicDetailsActivity.class);
                intent_recom.putExtra("talkId", fengCircleDynamicBean.getSourceFengwenID());
                startActivity(intent_recom);
            } else {
                MyToast.makeText(this, "动态已删除", Toast.LENGTH_SHORT).show();
//                showOneTextDialog("动态已删除");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showOneTextDialog(String str) {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text, null);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(str);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void OnTalkisnormalFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("askhdfkjs", "**************" + respanse);
    }

    @Override
    public void closeTalkisnormalProgress() {

    }


    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            String sign = TGmd5.getMD5(distributorid);
            getTuanBi(distributorid, sign);
//            MyToast.makeText(BaseActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyToast.makeText(NewRecomFengWenDetailsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToast.makeText(NewRecomFengWenDetailsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享成功，获取团币
     *
     * @param distributorid
     * @param sign
     */
    public void getTuanBi(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getShareFengTuanBi(NewRecomFengWenDetailsActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                MyToast.show(NewRecomFengWenDetailsActivity.this, jsonObject1.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}