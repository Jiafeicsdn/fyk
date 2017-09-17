package com.lvgou.distribution.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.DetailsMenuAdapter;
import com.lvgou.distribution.adapter.FengCircleCommentAdapter;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.MenuBean;
import com.lvgou.distribution.bean.ZanListBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.fragment.CommentFragment;
import com.lvgou.distribution.fragment.ZanFragment;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.DynamicDetailsPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.DynamicDetailsView;
import com.lvgou.distribution.view.HorizontalListView;
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
 * 蜂文详情
 */
public class NewDynamicDetailsActivity extends BaseCircleActivity implements DynamicDetailsView, ZanFragment.OnArticleSelectedListener, CommentFragment.OnArticleSelectedListener, DistributorHomeView {
    DynamicDetailsPresenter dynamicDetailsPresenter;
    private DistributorHomePresenter distributorHomePresenter;
    private String distributorid, distributorName;
    private int currPage = 1;
    private String talkisnormal_sign = "";
    private String talkcommentlist_sign = "";
    private String talkId = "";

    @ViewInject(R.id.txt_comment)
    private TextView txt_comment;
    @ViewInject(R.id.txt_zan_numer)
    private TextView txt_zan_numer;
    @ViewInject(R.id.img_zan)
    private ImageView img_zan;
    @ViewInject(R.id.layout_edit)
    private LinearLayout layout_edit;
    //    @ViewInject(R.id.edit_comment)
//    private EditText edit_comment;
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
    @ViewInject(R.id.tv_sned)
    private TextView tv_sned;
    @ViewInject(R.id.et_evaluate)
    private EditText et_evaluate;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_horizontalmenu)
    private HorizontalListView menu;
    @ViewInject(R.id.img_dismiss)
    private ImageView img_dismiss;
    @ViewInject(R.id.img_send)
    private ImageView img_send;
    @ViewInject(R.id.edit_comment)
    private EditText edit_comment;
    @ViewInject(R.id.img_reward)
    private TextView img_reward;
    //    @ViewInject(R.id.txt_geted_tuanbi)
//    private TextView txt_geted_tuanbi;
    @ViewInject(R.id.txt_reward_people_number)
    private TextView txt_reward_people_number;
    @ViewInject(R.id.img_collect)
    private ImageView img_collect;
    /*   @ViewInject(R.id.img_sex)
       private ImageView img_sex;*/
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.ll_zan)
    private LinearLayout ll_zan;
    TextView txt_praise;
    @ViewInject(R.id.img_concern)
    TextView img_concern;
    @ViewInject(R.id.txt_collect_numer)
    TextView txt_collect_numer;//收藏数量
    @ViewInject(R.id.ll_collect)
    LinearLayout ll_collect;//收藏
    @ViewInject(R.id.rl_fabu)
    private RelativeLayout rl_fabu;
    private int position;
    private FengCircleCommentAdapter fengCircleCommentAdapter;
    private FengCircleDynamicBean fengCircleDynamicBean;
    public CircleCommentBean circleCommentBean;
    public int layer;//层级
    public int comment_position;//点击的评论item
    private String comment_content;
    private int dataPageCount = 0;
    CommentFragment commentFragment;
    ZanFragment zanFragment;
    private String share_content = "";

    private String share_title = "蜂圈-小伙伴刚刚分享了自己的带团日常，快来围观吧";
    private String share_url = "http://agent.quygt.com/circle/detail?talkId=";
    UMImage share_image = null;


    private String sign_detial = "";
    private String sex;

    private String prePageLastDataObjectId = "";
    private int tuanbi = 0;//打赏的团币
    private int acquired_tuanbi = 0; //获得的赞赏团币
    String free_tuanbi;//剩余的团币
    private int reward_people_number;//赞赏人数
    List<MenuBean> menulist;
    DetailsMenuAdapter menuAdapter;
    private List<Fragment> fragmentList;
    private LinearLayout ll_comment_view;
    private RelativeLayout rl_top_title;
    private int zanListSize = 0;//赞的数量
    private String state;
    private String userType;
    private JSONObject jsonObject_data;
    GoodView goodView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_dynamic_details);
        ViewUtils.inject(this);
        showLoadingProgressDialog(this, "");
        distributorHomePresenter = new DistributorHomePresenter(this);
        rl_top_title = (RelativeLayout) findViewById(R.id.rl_top_title);
        ll_comment_view = (LinearLayout) findViewById(R.id.ll_comment_view);
        ll_comment_view.setVisibility(View.GONE);
        goodView = new GoodView(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        talkId = intent.getStringExtra("talkId");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        distributorName = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        sex = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        state = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        userType = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE);
        talkisnormal_sign = TGmd5.getMD5(talkId);

        talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);

        sign_detial = TGmd5.getMD5(distributorid + talkId);


        dynamicDetailsPresenter = new DynamicDetailsPresenter(this);
        dynamicDetailsPresenter.attach(this);
//        dynamicDetailsPresenter.talkisnormal(talkId, talkisnormal_sign);

        dynamicDetailsPresenter.getTalkDetial(distributorid, talkId, sign_detial);
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
                ((DetailsMenuAdapter) menu.getAdapter()).setCurOrderItem(position);
                if(position==1){
                    rl_fabu.setVisibility(View.GONE);
                }else{
                    rl_fabu.setVisibility(View.VISIBLE);
                }
            }
        });
//        fengCircleCommentAdapter = new FengCircleCommentAdapter(this, dynamicDetailsPresenter);
//        fengCircleCommentAdapter.setListener(circleCommentCallBack);
//        listView.setAdapter(fengCircleCommentAdapter);
        fengCircleCommentAdapter = commentFragment.fengCircleCommentAdapter;
//        img_concern
    }

    /***
     * 回复操作
     */
//    FengCircleCommentAdapter.CircleCommentCallBack circleCommentCallBack = new FengCircleCommentAdapter.CircleCommentCallBack() {
//        @Override
//        public void showComment(CircleCommentBean circleCommentBean, int layer, int position) {
//            NewDynamicDetailsActivity.this.layer = layer;
//            NewDynamicDetailsActivity.this.comment_position = position;
//            edit_comment.setText("");
//            if (layer == 1) {
//                edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
//            } else {
//                edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(position).getDistributorName());
//            }
//            openDialogCommentShare();
//            NewDynamicDetailsActivity.this.circleCommentBean = circleCommentBean;
//        }
//    };
    private void init(final FengCircleDynamicBean fengCircleDynamicBean) {
        CircleImageView img_head_pic = (CircleImageView) findViewById(R.id.img_head_pic);
        ImageView img_teacher_label = (ImageView) findViewById(R.id.img_teacher_label);
        TextView txt_user_name = (TextView) findViewById(R.id.txt_user_name);
        TextView txt_issue_time = (TextView) findViewById(R.id.txt_issue_time);
        TextView txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setMaxLines(10000);
        TextView txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_desc.setMaxLines(10000);
        LinearLayout layout_desc = (LinearLayout) findViewById(R.id.layout_desc);
        txt_praise = (TextView) findViewById(R.id.txt_praise);
        txt_praise.setVisibility(View.GONE);
        TextView txt_comment = (TextView) findViewById(R.id.txt_comment);
        txt_comment.setVisibility(View.GONE);
        NineGridView nineGrid = (NineGridView) findViewById(R.id.nineGrid);
        LinearLayout categoryNames_layout = (LinearLayout) findViewById(R.id.categoryNames_layout);
        if (fengCircleDynamicBean != null) {

            /*if (fengCircleDynamicBean.getSex().equals("1")) {
                img_sex.setImageResource(R.mipmap.icon_man);
            } else if (fengCircleDynamicBean.getSex().equals("2")) {
                img_sex.setImageResource(R.mipmap.icon_woman);
            }*/
            if (!"null".equals(fengCircleDynamicBean.getCurrentLocation()) && fengCircleDynamicBean.getCurrentLocation() != null && fengCircleDynamicBean.getCurrentLocation().length() > 0) {
                tv_address.setVisibility(View.VISIBLE);
            } else {
                tv_address.setVisibility(View.GONE);
            }
            tv_address.setText(fengCircleDynamicBean.getCurrentLocation());
            if (String.valueOf(fengCircleDynamicBean.getDistributorID()).equals(distributorid)) {
                img_concern.setVisibility(View.GONE);
            } else {
                img_concern.setVisibility(View.VISIBLE);
            }
            if (fengCircleDynamicBean.getFollowed().equals("1")) {
                img_concern.setText("已关注");
                img_concern.setVisibility(View.GONE);
//                img_concern.setImageResource(R.mipmap.yiguanzhu);
            } else {
                img_concern.setText("+ 关注");
//                img_concern.setImageResource(R.mipmap.circle_add_follow);
            }
            if (fengCircleDynamicBean.getZaned() == 1) {
                img_zan.setImageResource(R.mipmap.zan_icon);
                txt_zan_numer.setTextColor(Color.parseColor("#d5aa5f"));
            } else {
                img_zan.setImageResource(R.mipmap.unzan_icon);
                txt_zan_numer.setTextColor(Color.parseColor("#a3a3a3"));

            }
            if (fengCircleDynamicBean.getZanCount() > 0) {
                txt_zan_numer.setText(String.valueOf(fengCircleDynamicBean.getZanCount()));
            } else {
                txt_zan_numer.setText("");
            }
            if (fengCircleDynamicBean.getCollectioned() == 1) {
                txt_collect_numer.setText(fengCircleDynamicBean.getCollectCount() + "");
                txt_collect_numer.setTextColor(Color.parseColor("#d5aa5f"));
                img_collect.setImageResource(R.mipmap.collect_icon);
            } else {
                if(fengCircleDynamicBean.getCollectCount()>0){
                    txt_collect_numer.setText(fengCircleDynamicBean.getCollectCount() + "");
                    txt_collect_numer.setTextColor(Color.parseColor("#a3a3a3"));
                }else{
                    txt_collect_numer.setVisibility(View.GONE);
                }
                img_collect.setImageResource(R.mipmap.uncollect_icon);
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
//                layout_desc.setBackgroundColor(Color.parseColor("#f2f2f2"));
                txt_desc.setVisibility(View.VISIBLE);
            } else {
//                layout_desc.setBackgroundColor(Color.parseColor("#00000000"));
                txt_desc.setVisibility(View.GONE);
                try {
                    if (null != jsonObject_data.get("TopicTitle") && !jsonObject_data.get("TopicTitle").toString().equals("") && !jsonObject_data.get("TopicTitle").toString().equals("null")) {
                        FaXianParseEmojiMsgUtil.getExpressionString(this, fengCircleDynamicBean.getID(), txt_title, fengCircleDynamicBean.getContent(), jsonObject_data.get("TopicTitle").toString(), jsonObject_data.get("TopicID").toString());
                    } else {
                        ParseEmojiMsgUtil.getExpressionString(this, fengCircleDynamicBean.getID(), txt_title, fengCircleDynamicBean.getContent());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                txt_title.setText(fengCircleDynamicBean.getContent());
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
                    long month1 = between / (30 * 24 * 3600);
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600;
                    long minute1 = between / 60;
                    if (minute1 == 0) {
                        txt_issue_time.setText("刚刚");
                    } else if (minute1 < 60) {
                        txt_issue_time.setText(minute1 + "分钟前");
                    } else if (hour1 < 24) {
                        txt_issue_time.setText(hour1 + "小时前");
                    } else if (day1 < 30) {
                        txt_issue_time.setText(day1 + "天前");
                    } else if (month1 < 12) {
                        txt_issue_time.setText(month1 + "月前");
                    } else {
                        txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                    }
                } catch (ParseException e) {
                }
            }

            List<FengCircleImageBean> images = fengCircleDynamicBean.getmImgUrlList();
            nineGrid.setAdapter(mAdapter);
            if (images == null || images.size() < 1) {
                nineGrid.setVisibility(View.GONE);
                share_image = new UMImage(NewDynamicDetailsActivity.this, R.mipmap.ic_launcher);
            } else {
                share_image = new UMImage(NewDynamicDetailsActivity.this, images.get(0).getPicUrl());
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
                img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
                txt_user_name.setTextColor(getResources().getColor(R.color.text_color_ff9900));
            } else if (fengCircleDynamicBean.getUserType() == 2) {
                /*if (fengCircleDynamicBean.getIsRZ() == 1) {
                    img_teacher_label.setImageResource(R.mipmap.icon_certified);
                    txt_user_name.setTextColor(getResources().getColor(R.color.text_color_ff9900));
                } else {
                    img_teacher_label.setVisibility(View.GONE);
                    txt_user_name.setTextColor(getResources().getColor(R.color.text_color_333333));
                }*/
                img_teacher_label.setVisibility(View.GONE);
                    txt_user_name.setTextColor(Color.parseColor("#7b7b7b"));
            } else if (fengCircleDynamicBean.getUserType() == 1) {
                img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
                txt_user_name.setTextColor(getResources().getColor(R.color.text_color_ff9900));
            }
            categoryNames_layout.removeAllViews();
            if (fengCircleDynamicBean.getCategoryNames().size() > 0) {
                for (int i = 0; i < fengCircleDynamicBean.getCategoryNames().size(); i++) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 20, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setPadding(20, 10, 20, 10);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(10);
                    textView.setTextColor(getResources().getColor(R.color.bg_gray_three));
                    textView.setBackgroundResource(R.drawable.bg_text_normal);
                    textView.setText(fengCircleDynamicBean.getCategoryNames().get(i));
                    categoryNames_layout.addView(textView);
                }
            }
        }
        img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String sign2 = TGmd5.getMD5("" + distributorid + fengCircleDynamicBean.getDistributorID() + "");
                showLoadingProgressDialog(NewDynamicDetailsActivity.this, "");
                distributorHomePresenter.distributorHome(distributorid, fengCircleDynamicBean.getDistributorID() + "", sign2);

               /* if (fengCircleDynamicBean.getUserType() == 3) {
                    Intent intent1 = new Intent(NewDynamicDetailsActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", fengCircleDynamicBean.getDistributorID()+"");
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(NewDynamicDetailsActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", fengCircleDynamicBean.getDistributorID()+"");
                    startActivity(intent1);
                }*/
            }
        });
        txt_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign2 = TGmd5.getMD5("" + distributorid + fengCircleDynamicBean.getDistributorID() + "");
                showLoadingProgressDialog(NewDynamicDetailsActivity.this, "");
                distributorHomePresenter.distributorHome(distributorid, fengCircleDynamicBean.getDistributorID() + "", sign2);
              /*  if (fengCircleDynamicBean.getUserType() == 3) {
                    Intent intent1 = new Intent(NewDynamicDetailsActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", fengCircleDynamicBean.getDistributorID()+"");
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(NewDynamicDetailsActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", fengCircleDynamicBean.getDistributorID()+"");
                    startActivity(intent1);
                }*/
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
            MyToast.show(NewDynamicDetailsActivity.this, "评论内容不能为空");
            return;
        }
        String commentId = "";
        if (circleCommentBean != null) {
            commentId = circleCommentBean.getID();
        }
        String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content + tuanbi);
        dynamicDetailsPresenter.commenttalk(distributorid, talkId, commentId, comment_content, tuanbi, sign);
    }

    PopupWindow popupWindow;

    private void showRewardPopWindow() {
        View contentview = LayoutInflater.from(this)
                .inflate(R.layout.reward_popwindow_view, null);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView img_close = (ImageView) contentview.findViewById(R.id.img_close_view);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        free_tuanbi = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        TextView tv_tuanbi_num = (TextView) contentview.findViewById(R.id.tv_tuanbi_num);

        final EditText et_tuanbi = (EditText) contentview.findViewById(R.id.et_tuanbi);
        final EditText et_content = (EditText) contentview.findViewById(R.id.et_content);
        Button btn_zanshang = (Button) contentview.findViewById(R.id.btn_zanshang);
        btn_zanshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(et_tuanbi.getText().toString().trim())) {
                    tuanbi = Integer.valueOf(et_tuanbi.getText().toString().trim());
                    if (et_content.getText().toString().trim().length() > 0) {
                        comment_content = et_content.getText().toString();
                    } else {
                        comment_content = getResources().getString(R.string.text_reward_hint);
                    }
                    requestCommentTalk();
                    popupWindow.dismiss();
                }
            }
        });
        String surplus_tuanbi = String.format(getResources().getString(R.string.text_surplus_tuanbi), free_tuanbi);
        tv_tuanbi_num.setText(surplus_tuanbi);

        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 重写onKeyListener
        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.showAtLocation(rl_top_title, Gravity.CENTER, 0, -100);
    }

    @OnClick({R.id.ll_zan, R.id.img_reward, R.id.ll_collect, R.id.img_send, R.id.img_dismiss, R.id.tv_sned, R.id.img_share, R.id.rl_back, R.id.rl_send_dialog, R.id.rl_dismiss_dialog, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_fengquan, R.id.rl_dimiss_one, R.id.rl_dialog_comment_root, R.id.rl_dialog_zhuangfa_root})
    public void OnClick(View view) {
        tuanbi = 0;
        switch (view.getId()) {
            case R.id.ll_collect:
                if (fengCircleDynamicBean.getCollectioned() == 1) {
                    String talkcollect_sign = TGmd5.getMD5(distributorid + talkId);
                    showLoadingProgressDialog(this, "");
                    dynamicDetailsPresenter.talkuncollection(distributorid, talkId, talkcollect_sign);
                } else {
                    String talkcollect_sign = TGmd5.getMD5(distributorid + talkId);
                    showLoadingProgressDialog(this, "");
                    dynamicDetailsPresenter.talkcollection(distributorid, talkId, talkcollect_sign);
                }
                break;
            case R.id.img_reward:
                comment_content = "";
                showRewardPopWindow();
                break;
            case R.id.img_send:
                comment_content = edit_comment.getText().toString();
                //防止多次点击
                img_send.setClickable(false);
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
            case R.id.img_share:
                openDialogShare();
                break;
            case R.id.tv_sned:
                comment_content = et_evaluate.getText().toString();
                tv_sned.setClickable(false);
                circleCommentBean = null;
                requestCommentTalk();
                break;
//            case R.id.txt_comment:
//                //发布评论
//                circleCommentBean = null;
////                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
////                edit_comment.setText("");
//                openDialogCommentShare();
//                break;
            case R.id.rl_send_dialog:
//                comment_content = edit_comment.getText().toString();
//                if (TextUtils.isEmpty(comment_content)) {
//                    MyToast.show(NewDynamicDetailsActivity.this, "评论内容不能为空");
//                    return;
//                }
//                String commentId = "";
//                if (circleCommentBean != null) {
//                    commentId = circleCommentBean.getID();
//                }
//                String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content + tuanbi);
//                dynamicDetailsPresenter.commenttalk(distributorid, talkId, commentId, comment_content, tuanbi, sign);
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
                /*img_zan.startAnimation(AnimationUtils.loadAnimation(
                        this, R.anim.dianzan_anim));*/
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + talkId);
                web.setTitle(share_content);//标题
                web.setThumb(share_image);  //缩略图
                web.setDescription(share_title);//描述
                new ShareAction(NewDynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
               /* new ShareAction(this)
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
                new ShareAction(NewDynamicDetailsActivity.this)
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
                new ShareAction(NewDynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
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
                new ShareAction(NewDynamicDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
               /* new ShareAction(this)
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
//            case R.id.rl_fabu:
//                String share_content_one = et_zhuanfa.getText().toString();
//                if (StringUtils.isEmpty(share_content_one)) {
//                    MyToast.show(NewDynamicDetailsActivity.this, "请输入内容");
//                    return;
//                } else {
//                    String sign_one = TGmd5.getMD5(distributorid + talkId + share_content_one);
//                    dynamicDetailsPresenter.zhuanFa(distributorid, talkId + "", share_content_one, sign_one);
//                }
//                break;
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
        public void onDisplayImage(Context context, ImageView imageView, int size, FengCircleImageBean imageInfo) {

            if (size > 1) {
                if (null != imageInfo.getSmallPicUrl() && !"".equals(imageInfo.getSmallPicUrl())) {
                    Glide.with(context).load(imageInfo.getSmallPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                } else {
                    Glide.with(context).load(imageInfo.getPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                }
            } else {
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
            String message = jsonObject.getString("message");
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (jsonArray != null && jsonObject.length() > 0) {
                boolean isnormal = (boolean) jsonArray.get(0);
                if (!isnormal) {
                    showStop(message);
                }
            }
            int status = jsonObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    View layout_navigation_title = null;
    View layout_commentlist_emptyView = null;

    @Override
    public void talkcommentlistResponse(String resonpse) {
    }

    @Override
    public void commenttalkResponse(String resonpse) {
//        closeDialogCommentShare();
        //回复点击
        tv_sned.setClickable(true);
        img_send.setClickable(true);
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
//                    commentFragment.fengCircleCommentAdapter.setcircleCommentData(circleCommentBean);
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
                    circleCommentBean.setTuanBi(tuanbi);
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
                et_evaluate.setHint(getResources().getString(R.string.text_pinglun_hint));
                if (tuanbi > 0) {
                    acquired_tuanbi = tuanbi + acquired_tuanbi;
                    PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, String.valueOf(Integer.valueOf(free_tuanbi) - tuanbi));
//                    String tuanbi2 = String.format(getResources().getString(R.string.text_geted_tuanbi), acquired_tuanbi);
//                    txt_geted_tuanbi.setText(tuanbi2);
                    reward_people_number++;
                    txt_reward_people_number.setText(String.format(getResources().getString(R.string.text_zaned_numer), reward_people_number + ""));
                }
                //强制隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_evaluate.getWindowToken(), 0);
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
                    img_concern.setText("已关注");
                    img_concern.setVisibility(View.GONE);
//                    img_concern.setImageResource(R.mipmap.circle_follow_already);
                } else {
                    img_concern.setText("+ 关注");
//                    img_concern.setImageResource(R.mipmap.circle_add_follow);
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
                    img_concern.setText("已关注");
                    img_concern.setVisibility(View.GONE);
//                    img_concern.setImageResource(R.mipmap.circle_follow_already);
                } else {
                    img_concern.setText("+ 关注");
//                    img_concern.setImageResource(R.mipmap.circle_add_follow);
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
                MyToast.show(NewDynamicDetailsActivity.this, "转发成功");
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
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String data = array.get(0).toString();
                if (array != null && array.length() > 1) {
                    reward_people_number = (int) array.get(1);
                    String zendNum = String.format(getResources().getString(R.string.text_zaned_numer), reward_people_number + "");
                    txt_reward_people_number.setText(zendNum);
                }
               /* if (array != null && array.length() > 2) {
                    acquired_tuanbi=(int)array.get(2);
                    String tuanbi = String.format(getResources().getString(R.string.text_geted_tuanbi), array.get(2));
                    txt_geted_tuanbi.setText(tuanbi);
                }*/

                jsonObject_data = new JSONObject(data);


                fengCircleDynamicBean = new FengCircleDynamicBean();
                fengCircleDynamicBean.setID(jsonObject_data.getString("ID"));
                fengCircleDynamicBean.setDistributorID(jsonObject_data.getInt("DistributorID"));
                fengCircleDynamicBean.setDistributorName(jsonObject_data.getString("DistributorName"));
                fengCircleDynamicBean.setUserType(jsonObject_data.getInt("UserType"));
                fengCircleDynamicBean.setIsRZ(jsonObject_data.getInt("IsRZ"));
                fengCircleDynamicBean.setCategoryIDs(jsonObject_data.getString("CategoryIDs"));
                JSONArray jsonCategory = jsonObject_data.getJSONArray("CategoryNames");
//                Log.e("kashfkdsa", "---------" + array.get(3));
                fengCircleDynamicBean.setCollectCount(Integer.parseInt(array.get(3).toString()));
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
                fengCircleDynamicBean.setCollectioned(jsonObject_data.getInt("Collectioned"));
                fengCircleDynamicBean.setSex(jsonObject_data.getString("Sex"));
                fengCircleDynamicBean.setCurrentLocation(jsonObject_data.getString("CurrentLocation"));

                if (!distributorid.equals(jsonObject_data.getInt("DistributorID"))) {
                    fengCircleDynamicBean.setFollowed(jsonObject_data.getString("Followed"));

                    img_concern.setVisibility(View.VISIBLE);
                } else {
                    //如果是自己
                    img_concern.setVisibility(View.GONE);
                }
                fengCircleDynamicBean.setZaned(jsonObject_data.getInt("Zaned"));
                //页面数据赋值
                init(fengCircleDynamicBean);
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
        closeLoadingProgressDialog();
        int txt_collect_numercount=fengCircleDynamicBean.getCollectCount();
        switch (type) {
            case "collect":
                img_collect.setImageResource(R.mipmap.collect_icon);
                fengCircleDynamicBean.setCollectioned(1);
                fengCircleDynamicBean.setCollectCount(txt_collect_numercount + 1);
                txt_collect_numer.setVisibility(View.VISIBLE);
                txt_collect_numer.setText(txt_collect_numercount+1 + "");
                txt_collect_numer.setTextColor(Color.parseColor("#d5aa5f"));
                break;
            case "uncollect":
                img_collect.setImageResource(R.mipmap.uncollect_icon);
                fengCircleDynamicBean.setCollectioned(0);
                fengCircleDynamicBean.setCollectCount(txt_collect_numercount - 1);
                if(txt_collect_numercount - 1<=0){
                    txt_collect_numer.setVisibility(View.GONE);
                }else{
                    txt_collect_numer.setText(txt_collect_numercount - 1 + "");
                    txt_collect_numer.setTextColor(Color.parseColor("#a3a3a3"));
                }
                break;
        }

    }

    @Override
    public void excuteFailedCallBack(String type, String resonpse) {
        tv_sned.setClickable(true);
        img_send.setClickable(true);
        closeLoadingProgressDialog();
        switch (type) {
            case "commenttalk":
                showHintDialog(resonpse);
                break;
            case "zan":
                showHintDialog(resonpse);
                break;
            case "Detial":
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
    }

    private void hidekeyword() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
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
                Intent intent1 = new Intent(this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(this, DistributorHomeActivity.class);
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
            MyToast.makeText(NewDynamicDetailsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToast.makeText(NewDynamicDetailsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
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
        RequestTask.getInstance().getShareFengTuanBi(NewDynamicDetailsActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                MyToast.show(NewDynamicDetailsActivity.this, jsonObject1.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}