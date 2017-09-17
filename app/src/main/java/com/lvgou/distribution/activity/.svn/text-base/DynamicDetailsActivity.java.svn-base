package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.FengCircleCommentAdapter;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.DynamicDetailsPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshScrollView;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DynamicDetailsView;
import com.lvgou.distribution.view.ListViewForScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Snow on 2016/8/5
 * 蜂文动态
 */
public class DynamicDetailsActivity extends BaseCircleActivity implements DynamicDetailsView {
    DynamicDetailsPresenter dynamicDetailsPresenter;
    private String distributorid, distributorName;
    private int currPage = 1;
    private String talkisnormal_sign = "";
    private String talkcommentlist_sign = "";
    private String talkId = "";

    @ViewInject(R.id.txt_comment)
    private TextView txt_comment;
    @ViewInject(R.id.pull_refresh_scroller)
    private PullToRefreshScrollView pull_refresh_scroller;
    @ViewInject(R.id.layout_edit)
    private LinearLayout layout_edit;
    @ViewInject(R.id.edit_comment)
    private EditText edit_comment;
    @ViewInject(R.id.rl_send_dialog)
    private RelativeLayout rl_send_dialog;
    @ViewInject(R.id.rl_dismiss_dialog)
    private RelativeLayout rl_dismiss_dialog;
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


    private ScrollView scrollView;
    TextView txt_praise;
    ImageView img_concern;
    private int position;
    private FengCircleCommentAdapter fengCircleCommentAdapter;
    private FengCircleDynamicBean fengCircleDynamicBean;
    private CircleCommentBean circleCommentBean;
    int layer;//层级
    int comment_position;//点击的评论item
    private String comment_content;
    private int dataPageCount = 0;
    private ListViewForScrollView listView;
    private LinearLayout empty_view;

    private String share_content = "";

    private String share_title = "蜂圈-小伙伴刚刚分享了自己的带团日常，快来围观吧";
    private String share_url = "http://agent.quygt.com/circle/detail?talkId=";
    UMImage share_image = null;

    private Dialog dialog_quit;

    private String sign_detial = "";
    private String sex;

    private String prePageLastDataObjectId = "";
private int tuanbi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        ViewUtils.inject(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        talkId = intent.getStringExtra("talkId");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        distributorName = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        sex = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        scrollView = pull_refresh_scroller.getRefreshableView();
        layout_commentlist_emptyView = LayoutInflater.from(this).inflate(R.layout.layout_dynic_comment_empty, null);
        TextView textView = (TextView) layout_commentlist_emptyView.findViewById(R.id.txt_navigation_title);
        textView.setText("最新评论");
        empty_view = (LinearLayout) layout_commentlist_emptyView.findViewById(R.id.empty_view);
        listView = (ListViewForScrollView) layout_commentlist_emptyView.findViewById(R.id.listview);
        scrollView.addView(layout_commentlist_emptyView);


        talkisnormal_sign = TGmd5.getMD5(talkId);

        talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);

        sign_detial = TGmd5.getMD5(distributorid + talkId);


        dynamicDetailsPresenter = new DynamicDetailsPresenter(this);
        dynamicDetailsPresenter.attach(this);
        dynamicDetailsPresenter.talkisnormal(talkId, talkisnormal_sign);

        dynamicDetailsPresenter.getTalkDetial(distributorid, talkId, sign_detial);

//        dynamicDetailsPresenter.talkcommentlist(talkId, prePageLastDataObjectId, currPage, talkcommentlist_sign);
//
//        fengCircleCommentAdapter = new FengCircleCommentAdapter(this, dynamicDetailsPresenter);
//        fengCircleCommentAdapter.setListener(circleCommentCallBack);
//        listView.setAdapter(fengCircleCommentAdapter);

    }

    /***
     * 回复操作
     */
//    FengCircleCommentAdapter.CircleCommentCallBack circleCommentCallBack = new FengCircleCommentAdapter.CircleCommentCallBack() {
//        @Override
//        public void showComment(CircleCommentBean circleCommentBean, int layer, int position) {
//            DynamicDetailsActivity.this.layer = layer;
//            DynamicDetailsActivity.this.comment_position = position;
//            edit_comment.setText("");
//            if (layer == 1) {
//                edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
//            } else {
//                edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(position).getDistributorName());
//            }
//            openDialogCommentShare();
//            DynamicDetailsActivity.this.circleCommentBean = circleCommentBean;
//        }
//    };

    private void init(View convertView, final FengCircleDynamicBean fengCircleDynamicBean) {
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_scroller.setMode(PullToRefreshBase.Mode.BOTH);
        pull_refresh_scroller.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currPage = 1;
                prePageLastDataObjectId = "";
                talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);
//                dynamicDetailsPresenter.talkcommentlist(talkId, prePageLastDataObjectId, currPage, talkcommentlist_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);
//                    dynamicDetailsPresenter.talkcommentlist(talkId, prePageLastDataObjectId, currPage, talkcommentlist_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_scroller.onRefreshComplete();
                        }
                    }, 100);
                }
            }
        });
        CircleImageView img_head_pic = (CircleImageView) convertView.findViewById(R.id.img_head_pic);
        ImageView img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
        TextView txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);
        TextView txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
        img_concern = (ImageView) convertView.findViewById(R.id.img_concern);
        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        txt_title.setMaxLines(500);
        TextView txt_desc = (TextView) convertView.findViewById(R.id.txt_desc);
        txt_desc.setMaxLines(500);
        LinearLayout layout_desc = (LinearLayout) convertView.findViewById(R.id.layout_desc);
        txt_praise = (TextView) convertView.findViewById(R.id.txt_praise);
        txt_praise.setVisibility(View.GONE);
        TextView txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
        txt_comment.setVisibility(View.GONE);
        NineGridView nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
        LinearLayout categoryNames_layout = (LinearLayout) convertView.findViewById(R.id.categoryNames_layout);
        if (fengCircleDynamicBean != null) {
            if (String.valueOf(fengCircleDynamicBean.getDistributorID()).equals(distributorid)) {
                img_concern.setVisibility(View.GONE);
            } else {
                img_concern.setVisibility(View.VISIBLE);
            }
            if (fengCircleDynamicBean.getFollowed().equals("1")) {
                img_concern.setImageResource(R.mipmap.yiguanzhu);
            } else {
                img_concern.setImageResource(R.mipmap.circle_add_follow);
            }
            if (fengCircleDynamicBean.getZaned() == 1) {
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_zaned);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                txt_like.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_zan);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                txt_like.setCompoundDrawables(drawable, null, null, null);
            }
            if (fengCircleDynamicBean.getZanCount() > 0) {
//                txt_like.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
            } else {
//                txt_like.setText("");
            }
            txt_user_name.setText(fengCircleDynamicBean.getDistributorName());
            if (fengCircleDynamicBean.getSourceDistributorID() > 0) {
                  txt_title.setVisibility(View.GONE);
                int soureceLength = fengCircleDynamicBean.getSourceDistributorName().length();
                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("@");
                stringBuffer.append(fengCircleDynamicBean.getSourceTitle());
                stringBuffer.append(",");
                stringBuffer.append(fengCircleDynamicBean.getContent());
//                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
////                style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_daoliu_yellow)), 1, soureceLength + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                style.setSpan(new ClickableSpan() {
//                    @Override
//                    public void onClick(View widget) {
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
//                        // 去掉下划线
//                        ds.setUnderlineText(false);
//                    }
//                }, 0, soureceLength + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txt_desc.setText(stringBuffer);
                txt_desc.setMovementMethod(LinkMovementMethod.getInstance());
                layout_desc.setBackgroundColor(Color.parseColor("#f2f2f2"));
                txt_desc.setVisibility(View.VISIBLE);
            } else {
                layout_desc.setBackgroundColor(Color.parseColor("#00000000"));
                txt_desc.setVisibility(View.GONE);
                txt_title.setText(fengCircleDynamicBean.getContent());
            }
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

                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600;
                    long minute1 = between / 60;
                    if (minute1 == 0) {
                        txt_issue_time.setText("刚刚");
                    } else if (minute1 < 60) {
                        txt_issue_time.setText(minute1 + "分钟前");
                    } else if (hour1 < 24) {
                        txt_issue_time.setText(hour1 + "小时前");
                    } else if (day1 < 7) {
                        txt_issue_time.setText(day1 + "天前");
                    } else {
                        txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            List<FengCircleImageBean> images = fengCircleDynamicBean.getmImgUrlList();
            nineGrid.setAdapter(mAdapter);
            if (images == null || images.size() < 1) {
                nineGrid.setVisibility(View.GONE);
                share_image = new UMImage(DynamicDetailsActivity.this, R.mipmap.teacher_default_head);
            } else {
                share_image = new UMImage(DynamicDetailsActivity.this, images.get(0).getPicUrl());
                nineGrid.setVisibility(View.VISIBLE);
            }
            if (nineGrid.getVisibility() == View.GONE && txt_desc.getVisibility() == View.GONE) {
                layout_desc.setVisibility(View.GONE);
            } else {
                layout_desc.setVisibility(View.VISIBLE);
            }
            if (images != null && images.size() == 1) {
                nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
                nineGrid.setSingleImageSize(images.get(0).getWidth());
            }
            nineGrid.setImagesData(images);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片

            String path = ImageUtils.getInstance().getPath(String.valueOf(fengCircleDynamicBean.getDistributorID()));
            ImageLoader.getInstance().displayImage(path, img_head_pic, options);

            if (fengCircleDynamicBean.getUserType() == 3) {
                img_teacher_label.setVisibility(View.VISIBLE);
            } else {
                img_teacher_label.setVisibility(View.GONE);
            }
            categoryNames_layout.removeAllViews();
            if (fengCircleDynamicBean.getCategoryNames().size() > 0) {
                for (int i = 0; i < fengCircleDynamicBean.getCategoryNames().size(); i++) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 20, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setPadding(14, 6, 14, 6);
                    textView.setTextSize(10);
                    textView.setTextColor(getResources().getColor(R.color.bg_gray_three));
                    textView.setBackgroundResource(R.drawable.bg_tag_shape);
                    textView.setText(fengCircleDynamicBean.getCategoryNames().get(i));
                    categoryNames_layout.addView(textView);
                }
            }
        }
        img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        //关注
        img_concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int friendId = fengCircleDynamicBean.getDistributorID();
                String sign = TGmd5.getMD5(distributorid + friendId);
                if (fengCircleDynamicBean.getFollowed().equals("1")) {
                    dynamicDetailsPresenter.CircleUnFollow(distributorid, String.valueOf(friendId), sign, position);
                } else {
                    dynamicDetailsPresenter.CircleFollow(distributorid, String.valueOf(friendId), sign, position);
                }
            }
        });
    }

    @OnClick({ R.id.txt_comment,R.id.rl_back, R.id.rl_send_dialog, R.id.rl_dismiss_dialog, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_fengquan, R.id.rl_fabu, R.id.rl_dimiss_one, R.id.rl_dialog_comment_root, R.id.rl_dialog_zhuangfa_root})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Intent intent = new Intent();
                intent.setAction("com.distribution.tugou.state");
                intent.putExtra("position", position);
                intent.putExtra("itemData", fengCircleDynamicBean);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.rl_dismiss_dialog:
                closeDialogCommentShare();
                break;
            case R.id.txt_comment:
                //发布评论
                circleCommentBean = null;
                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
                edit_comment.setText("");
                openDialogCommentShare();
                break;
            case R.id.rl_send_dialog:
                comment_content = edit_comment.getText().toString();
                if (TextUtils.isEmpty(comment_content)) {
                    MyToast.show(DynamicDetailsActivity.this, "评论内容不能为空");
                    return;
                }
                String commentId = "";
                if (circleCommentBean != null) {
                    commentId = circleCommentBean.getID();
                }
                String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content+tuanbi);
                dynamicDetailsPresenter.commenttalk(distributorid, talkId, commentId, comment_content,tuanbi, sign);
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + talkId);
                web.setTitle(share_content);//标题
                web.setThumb(share_image);  //缩略图
                web.setDescription(share_title);//描述
                new ShareAction(DynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url + talkId);
                web1.setTitle(share_content);//标题
                web1.setThumb(share_image);  //缩略图
                web1.setDescription(share_title);//描述
                new ShareAction(DynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url + talkId);
                web2.setTitle(share_content);//标题
                web2.setThumb(share_image);  //缩略图
                web2.setDescription(share_title);//描述
                new ShareAction(DynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url + talkId);
                web3.setTitle(share_content);//标题
                web3.setThumb(share_image);  //缩略图
                web3.setDescription(share_title);//描述
                new ShareAction(DynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(share_image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.rl_fengquan:
                closeDialogShare();
                openDialogZhuanFaShare();
                break;
            case R.id.rl_dimiss_one:
                closeDialogZhuanFaShare();
                break;
            case R.id.rl_fabu:
                String share_content_one = et_zhuanfa.getText().toString();
                if (StringUtils.isEmpty(share_content_one)) {
                    MyToast.show(DynamicDetailsActivity.this, "请输入内容");
                    return;
                } else {
                    String sign_one = TGmd5.getMD5(distributorid + talkId + share_content_one);
                    dynamicDetailsPresenter.zhuanFa(distributorid, talkId + "", share_content_one, sign_one);
                }
                break;
            case R.id.rl_dialog_comment_root:
                closeDialogCommentShare();
                break;
            case R.id.rl_dialog_zhuangfa_root:
                closeDialogZhuanFaShare();
                break;
        }
    }

    private NineGridViewAdapter<FengCircleImageBean> mAdapter = new NineGridViewAdapter<FengCircleImageBean>() {
        @Override
        public void onDisplayImage(Context context, ImageView imageView,int size, FengCircleImageBean imageInfo) {
           if(size>1){
               if(null!=imageInfo.getSmallPicUrl()&&!"".equals(imageInfo.getSmallPicUrl())){
                   Glide.with(context).load(imageInfo.getSmallPicUrl())
                           .placeholder(R.mipmap.home_loading)//
                           .error(R.mipmap.home_loading)//
                           .into(imageView);
               }else{
                   Glide.with(context).load(imageInfo.getPicUrl())
                           .placeholder(R.mipmap.home_loading)//
                           .error(R.mipmap.home_loading)//
                           .into(imageView);
               }
           }else{
               Glide.with(context).load(imageInfo.getPicUrl())//
                       .placeholder(R.mipmap.home_loading)//
                       .error(R.mipmap.home_loading)//
                       .into(imageView);
           }

        }

        @Override
        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<FengCircleImageBean> imageInfo) {

            Intent intent = new Intent(context, ImagePagerActivity.class);
            Bundle bundle = new Bundle();
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < imageInfo.size(); i++) {
                list.add(imageInfo.get(i).getPicUrl());
            }

            bundle.putStringArrayList("image_urls", list);
            bundle.putInt("image_index", index);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }
    };

    @Override
    public void talkisnormalResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 0) {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    View layout_navigation_title = null;
    View layout_commentlist_emptyView = null;

    @Override
    public void talkcommentlistResponse(String resonpse) {
        pull_refresh_scroller.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                List<CircleCommentBean> commentBeanList = new ArrayList<>();
                if (currPage == 1) {
                    dataPageCount = jsonObject1.getInt("DataPageCount");
                }
                JSONArray jsonArraycomment = jsonObject1.getJSONArray("Data");
                if (null != jsonArraycomment && jsonArraycomment.length() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    for (int i = 0; i < jsonArraycomment.length(); i++) {
                        CircleCommentBean circleCommentBean = new CircleCommentBean();
                        JSONArray AppendComments = ((JSONObject) jsonArraycomment.get(i)).getJSONArray("AppendComment");
                        if (null != AppendComments && AppendComments.length() > 0) {
                            List<CircleCommentBean> item_commentBeanList = new ArrayList<>();
                            for (int j = 0; j < AppendComments.length(); j++) {
                                CircleCommentBean item_circleCommentBean = new CircleCommentBean();
                                item_circleCommentBean.setID(((JSONObject) AppendComments.get(j)).getString("ID"));
                                prePageLastDataObjectId = ((JSONObject) AppendComments.get(j)).getString("ID");
                                item_circleCommentBean.setFengwenID(((JSONObject) AppendComments.get(j)).getString("FengwenID"));
                                item_circleCommentBean.setFengwenDistributorID(((JSONObject) AppendComments.get(j)).getInt("FengwenDistributorID"));
                                item_circleCommentBean.setFengwenDistributorName(((JSONObject) AppendComments.get(j)).getString("FengwenDistributorName"));
                                item_circleCommentBean.setFengwenTitle(((JSONObject) AppendComments.get(j)).getString("FengwenTitle"));
                                item_circleCommentBean.setDistributorID(((JSONObject) AppendComments.get(j)).getInt("DistributorID"));
                                item_circleCommentBean.setDistributorName(((JSONObject) AppendComments.get(j)).getString("DistributorName"));
                                item_circleCommentBean.setUserType(((JSONObject) AppendComments.get(j)).getInt("UserType"));
                                item_circleCommentBean.setIsRZ(((JSONObject) AppendComments.get(j)).getInt("IsRZ"));
                                item_circleCommentBean.setParentID(((JSONObject) AppendComments.get(j)).getString("ParentID"));
                                item_circleCommentBean.setReplyDistributorID(((JSONObject) AppendComments.get(j)).getInt("ReplyDistributorID"));
                                item_circleCommentBean.setReplyDistributorName(((JSONObject) AppendComments.get(j)).getString("ReplyDistributorName"));
                                item_circleCommentBean.setContent(((JSONObject) AppendComments.get(j)).getString("Content"));
                                item_circleCommentBean.setIsRead(((JSONObject) AppendComments.get(j)).getInt("IsRead"));
                                item_circleCommentBean.setState(((JSONObject) AppendComments.get(j)).getInt("State"));
                                item_circleCommentBean.setCreateTime(((JSONObject) AppendComments.get(j)).getString("CreateTime"));
                                item_commentBeanList.add(item_circleCommentBean);
                            }
                            circleCommentBean.setItem_circleCommentBeans(item_commentBeanList);
                        }
                        circleCommentBean.setID(((JSONObject) jsonArraycomment.get(i)).getString("ID"));
                        circleCommentBean.setFengwenID(((JSONObject) jsonArraycomment.get(i)).getString("FengwenID"));
                        circleCommentBean.setFengwenDistributorID(((JSONObject) jsonArraycomment.get(i)).getInt("FengwenDistributorID"));
                        circleCommentBean.setFengwenDistributorName(((JSONObject) jsonArraycomment.get(i)).getString("FengwenDistributorName"));
                        circleCommentBean.setFengwenTitle(((JSONObject) jsonArraycomment.get(i)).getString("FengwenTitle"));
                        circleCommentBean.setDistributorID(((JSONObject) jsonArraycomment.get(i)).getInt("DistributorID"));
                        circleCommentBean.setDistributorName(((JSONObject) jsonArraycomment.get(i)).getString("DistributorName"));
                        circleCommentBean.setUserType(((JSONObject) jsonArraycomment.get(i)).getInt("UserType"));
                        circleCommentBean.setIsRZ(((JSONObject) jsonArraycomment.get(i)).getInt("IsRZ"));
                        circleCommentBean.setParentID(((JSONObject) jsonArraycomment.get(i)).getString("ParentID"));
                        circleCommentBean.setReplyDistributorID(((JSONObject) jsonArraycomment.get(i)).getInt("ReplyDistributorID"));
                        circleCommentBean.setReplyDistributorName(((JSONObject) jsonArraycomment.get(i)).getString("ReplyDistributorName"));
                        circleCommentBean.setContent(((JSONObject) jsonArraycomment.get(i)).getString("Content"));
                        circleCommentBean.setIsRead(((JSONObject) jsonArraycomment.get(i)).getInt("IsRead"));
                        circleCommentBean.setState(((JSONObject) jsonArraycomment.get(i)).getInt("State"));
                        circleCommentBean.setSex(((JSONObject) jsonArraycomment.get(i)).getString("Sex"));
                        circleCommentBean.setCreateTime(((JSONObject) jsonArraycomment.get(i)).getString("CreateTime"));
                        commentBeanList.add(circleCommentBean);
                    }
                    if (currPage == 1) {
                        fengCircleCommentAdapter.getcircleCommentData().clear();
                    }
                    fengCircleCommentAdapter.setcircleCommentData(commentBeanList);
                    fengCircleCommentAdapter.notifyDataSetChanged();
                } else {
                    empty_view.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commenttalkResponse(String resonpse) {
        closeDialogCommentShare();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                String comment_id = "";
                JSONArray result = jsonObject.getJSONArray("result");
                if (result != null && result.length() > 0) {
                    comment_id = (String) result.get(0);
                }
                listView.setVisibility(View.VISIBLE);
                empty_view.setVisibility(View.GONE);
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
                    itemcircleCommentBean.setID(fengCircleDynamicBean.getID());
                    itemcircleCommentBean.setSex(sex);
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
                    circleCommentBean.setDistributorID(Integer.valueOf(distributorid));
                    circleCommentBean.setDistributorName(distributorName);
                    fengCircleCommentAdapter.addcircleCommentData(circleCommentBean);
                }
                fengCircleCommentAdapter.notifyDataSetChanged();
                edit_comment.setText("");
                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
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
//                    txt_like.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
                } else {
//                    txt_like.setText("赞");
                }
                if (fengCircleDynamicBean.getZaned() == 1) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.dianzan_ready_icon);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    txt_like.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.img_like);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    txt_like.setCompoundDrawables(drawable, null, null, null);
                }
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
//                    txt_like.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
                } else {
//                    txt_like.setText("赞");
                }
                if (fengCircleDynamicBean.getZaned() == 1) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.dianzan_ready_icon);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    txt_like.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.img_like);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    txt_like.setCompoundDrawables(drawable, null, null, null);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                MyToast.show(DynamicDetailsActivity.this, "转发成功");
                et_zhuanfa.setText("");
                closeDialogZhuanFaShare();
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
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String data = array.get(0).toString();
                JSONObject jsonObject_data = new JSONObject(data);

                fengCircleDynamicBean = new FengCircleDynamicBean();
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
                            if(((String) piclists.get(j)).indexOf("smallPicUrl") != -1){
                                circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                            }
                            if(((String) piclists.get(j)).indexOf("picUrl") != -1){
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
                fengCircleDynamicBean.setZaned(jsonObject_data.getInt("Zaned"));
                //页面数据赋值
                init(layout_commentlist_emptyView, fengCircleDynamicBean);

                share_content = fengCircleDynamicBean.getContent();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recommenttalkcontent_response(String resonpse) {

    }

    @Override
    public void talkcollectionResponse(String type, String resonpse) {

    }

    @Override
    public void excuteFailedCallBack(String type, String resonpse) {

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
        pull_refresh_scroller.onRefreshComplete();
        showStop(s);
    }

    private void hidekeyword() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
    }

    private void showEditCommentDialog() {
        layout_edit.setVisibility(View.VISIBLE);
        edit_comment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit_comment, InputMethodManager.SHOW_FORCED);
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

    //弹出转发对话框
    private void openDialogZhuanFaShare() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, true);
    }

    // 关闭转发对话框
    private void closeDialogZhuanFaShare() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, false);

    }


    // 弹出评论对话框
    private void openDialogCommentShare() {
        performDialogAnimation(rl_dialog_comment_root,
                ll_dialog_comment_cotent, true);
    }

    // 关闭评论对话框
    private void closeDialogCommentShare() {
        performDialogAnimation(rl_dialog_comment_root,
                ll_dialog_comment_cotent, false);

    }

    public void showStop(String str) {
        dialog_quit = new Dialog(DynamicDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(DynamicDetailsActivity.this, R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
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
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.state");
        intent.putExtra("position", position);
        intent.putExtra("itemData", fengCircleDynamicBean);
        sendBroadcast(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dynamicDetailsPresenter != null) {
            dynamicDetailsPresenter.dettach();
        }
    }
}