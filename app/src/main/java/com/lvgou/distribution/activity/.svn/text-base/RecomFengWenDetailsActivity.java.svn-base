package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
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
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.DynamicDetailsPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshScrollView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DynamicDetailsView;
import com.lvgou.distribution.view.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 官方蜂文详情
 */
public class RecomFengWenDetailsActivity extends BaseCircleActivity implements DynamicDetailsView {
    DynamicDetailsPresenter dynamicDetailsPresenter;
    private String distributorid, distributorName;
    private int currPage = 1;
    private String talkisnormal_sign = "";
    private String talkcommentlist_sign = "";
    private String talkId = "";
    private int zaned;
    private int zancount;
    private int commentcount;
    @ViewInject(R.id.txt_comment)
    private TextView txt_comment;
    @ViewInject(R.id.pull_refresh_scroller)
    private PullToRefreshScrollView pull_refresh_scroller;
    @ViewInject(R.id.layout_edit)
    private LinearLayout layout_edit;
    @ViewInject(R.id.edit_comment)
    private EditText edit_comment;
    @ViewInject(R.id.img_send)
    private ImageView img_send;
    @ViewInject(R.id.img_dismiss)
    private ImageView img_dismiss;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
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
    private ScrollView scrollView;
    TextView txt_praise;
    ImageView img_concern;
    private int position;
    private FengCircleCommentAdapter fengCircleCommentAdapter;
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
    UMImage image = null;

    private Dialog dialog_quit;

    private String recommenttalkcontent_sign = "";

    private CircleRecommentEntity itemData;
    private String sex;

    private String prePageLastDataObjectId = "";

    private String push_talkId = "";
    private FengCircleDynamicBean fengCircleDynamicBean;
    private int tuanbi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        ViewUtils.inject(this);
        sex = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        tv_title.setText("动态详情");
        showLoadingProgressDialog(this, "");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        distributorName = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        itemData = (CircleRecommentEntity) intent.getSerializableExtra("itemData");
        dynamicDetailsPresenter = new DynamicDetailsPresenter(this);
        dynamicDetailsPresenter.attach(this);
        if (null != itemData) {
            talkId = itemData.getID();
            String sign_one = TGmd5.getMD5(distributorid + talkId);
            dynamicDetailsPresenter.getTalkDetial(distributorid, talkId, sign_one);
        }


        scrollView = pull_refresh_scroller.getRefreshableView();

        initView();
        talkisnormal_sign = TGmd5.getMD5(talkId);
        talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + currPage);
        recommenttalkcontent_sign = TGmd5.getMD5(talkId);


        dynamicDetailsPresenter.talkisnormal(talkId, talkisnormal_sign);
        dynamicDetailsPresenter.recommenttalkcontent(talkId, recommenttalkcontent_sign);

    }

    private void initView() {
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


    @OnClick({ R.id.txt_comment, R.id.rl_back, R.id.img_send, R.id.img_dismiss, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_fengquan, R.id.rl_dialog_comment_root})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Intent intent = new Intent();
                if (itemData == null) {
                    itemData = new CircleRecommentEntity();
                }
                itemData.setZaned(String.valueOf(zaned));
                itemData.setZanCount(String.valueOf(zancount));
                itemData.setCommentCount(String.valueOf(commentcount));
                intent.putExtra("itemData", itemData);
                setResult(1, intent);
                finish();
                break;
            case R.id.img_dismiss:
                closeDialogCommentShare();
                break;
            case R.id.txt_comment:
                circleCommentBean = null;
                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
                edit_comment.setText("");
                openDialogCommentShare();
                break;
            case R.id.img_send:
                //发布评论
                comment_content = edit_comment.getText().toString();
                if (TextUtils.isEmpty(comment_content)) {
                    MyToast.show(RecomFengWenDetailsActivity.this, "评论内容不能为空");
                    return;
                }
                String commentId = "";
                if (circleCommentBean != null) {
                    commentId = circleCommentBean.getID();
                }
                String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content+tuanbi);
                dynamicDetailsPresenter.commenttalk(distributorid, talkId, commentId, comment_content, tuanbi, sign);
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(share_url + talkId);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(RecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(share_url + talkId);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(RecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(share_url + talkId);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(RecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(share_url + talkId);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(RecomFengWenDetailsActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_content)
                        .withText(share_title)
                        .withTargetUrl(share_url + talkId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.rl_dialog_comment_root:
                closeDialogCommentShare();
                break;
        }
    }

    @Override
    public void talkisnormalResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 0) {
                showQuitDialog(resonpse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void talkcommentlistResponse(String resonpse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoadingProgressDialog();
            }
        }, 1000);
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
                        circleCommentBean.setCreateTime(((JSONObject) jsonArraycomment.get(i)).getString("CreateTime"));
                        circleCommentBean.setSex(((JSONObject) jsonArraycomment.get(i)).getString("Sex"));
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
                commentcount++;
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
                    itemcircleCommentBean.setID(talkId);
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
                edit_comment.setHint("想说点什么,就写在这里吧");
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
                zaned = 1;
                zancount++;
                if (zancount > 0) {
//                    txt_like.setText(String.valueOf(zancount));
                } else {
//                    txt_like.setText("赞");
                }
                if (zaned == 1) {
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
                zaned = 0;
                zancount--;
                if (zancount > 0) {
//                    txt_like.setText(String.valueOf(zancount));
                } else {
//                    txt_like.setText("赞");
                }
                if (zaned == 1) {
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
    }

    @Override
    public void unfollowResponse(String resonpse) {

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
                MyToast.show(RecomFengWenDetailsActivity.this, "转发成功");
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
                zaned = jsonObject_data.getInt("Zaned");
                zancount = jsonObject_data.getInt("ZanCount");
                commentcount = jsonObject_data.getInt("CommentCount");
                share_title = jsonObject_data.getString("Title");

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

                image = new UMImage(RecomFengWenDetailsActivity.this, Url.ROOT + fengCircleDynamicBean.getPicUrl());

                if (zancount > 0)

                {
//                    txt_like.setText(String.valueOf(zancount));
                } else

                {
//                    txt_like.setText("赞");
                }

                if (zaned == 1)

                {
                    Drawable drawable = getResources().getDrawable(R.mipmap.dianzan_ready_icon);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    txt_like.setCompoundDrawables(drawable, null, null, null);
                } else

                {
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
    public void recommenttalkcontent_response(String resonpse) {
        try {
//            dynamicDetailsPresenter.talkcommentlist(talkId, prePageLastDataObjectId, currPage, talkcommentlist_sign);
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (jsonArray != null && jsonArray.length() > 0) {
                    View view = LayoutInflater.from(this).inflate(R.layout.details_recom_fengwen_item, null);
                    TextView textView = (TextView) view.findViewById(R.id.txt_navigation_title);
                    textView.setText("最新评论");
                    empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
                    listView = (ListViewForScrollView) view.findViewById(R.id.listview);
//                    fengCircleCommentAdapter = new FengCircleCommentAdapter(this, dynamicDetailsPresenter);
//                    fengCircleCommentAdapter.setListener(circleCommentCallBack);
//                    listView.setAdapter(fengCircleCommentAdapter);

                    WebView webView = (WebView) view.findViewById(R.id.webView);
                    //加载、并显示HTML代码
                    webView.loadDataWithBaseURL(null, jsonArray.get(0).toString(), "text/html", "utf-8", null);
                    scrollView.addView(view);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        closeLoadingProgressDialog();
        closeLoadingProgressDialog();
        pull_refresh_scroller.onRefreshComplete();
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
        intent.putExtra("position", position);
        if (itemData == null) {
            itemData = new CircleRecommentEntity();
        }
        itemData.setZaned(String.valueOf(zaned));
        itemData.setZanCount(String.valueOf(zancount));
        itemData.setCommentCount(String.valueOf(commentcount));
        intent.putExtra("itemData", itemData);
        setResult(1, intent);
        finish();
    }

    //退出登录
    public void showQuitDialog(String str) {
        dialog_quit = new Dialog(RecomFengWenDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(RecomFengWenDetailsActivity.this,
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
        dialog_quit = new Dialog(RecomFengWenDetailsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(RecomFengWenDetailsActivity.this, R.layout.dialog_show_check_stop, null);
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
}