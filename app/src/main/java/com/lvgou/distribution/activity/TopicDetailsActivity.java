package com.lvgou.distribution.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.TopicCommentAdapter;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.TopicDetailsPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.refresh.PullToRefreshScrollView;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ListViewForScrollView;
import com.lvgou.distribution.view.TopicDetailsView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.common.utils.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 话题详情
 */
public class TopicDetailsActivity extends BaseActivity implements TopicDetailsView {
    @ViewInject(R.id.pull_refresh_scroller)
    private PullToRefreshScrollView pull_refresh_scroller;
    @ViewInject(R.id.layout_edit)
    private LinearLayout layout_edit;
    @ViewInject(R.id.edit_comment)
    private EditText edit_comment;
    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.img_comment)
    private ImageView img_comment;
    @ViewInject(R.id.rl_dismiss_dialog)
    private RelativeLayout rl_dismiss_dialog;
    @ViewInject(R.id.rl_send_dialog)
    private RelativeLayout rl_send_dialog;
    @ViewInject(R.id.rl_dialog_comment_root)
    private RelativeLayout rl_dialog_zhuangfa_root;
    @ViewInject(R.id.ll_dialog_comment_cotent)
    private LinearLayout ll_dialog_zhuangfa_cotent;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_title)
    private RelativeLayout rl_title;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private int currPage = 1;
    private ScrollView scrollView;
    private CircleCommentBean circleCommentBean;
    int layer;//层级
    int comment_position;//点击的评论item
    private String comment_content;
    private int dataPageCount = 0;
    private String topicdetail_sign = "";
    private String commenttopic_sign = "";
    private String topiccommentlist_sign = "";
    private String topicId = "";
    private TopicCommentAdapter topicCommentAdapter;
    private String distributorid, distributorName;
    private String topicID = "";
    private TopicDetailsPresenter topicDetailsPresenter;
    private String commentId = "";
    private ListViewForScrollView listView;
    private LinearLayout empty_view;
    private String sex;

    private String prePageLastDataObjectId = "";

    private int del_layer_index;
    private int del_child_level;

    private String state;
    private String userType;
    private WebView webView;
    private TextView txt_navigation_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        ViewUtils.inject(this);
        scrollView = pull_refresh_scroller.getRefreshableView();
        topicId = getIntent().getStringExtra("topicId");
        initView();
        sex = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        distributorName = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        state = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        userType = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE);
        showLoadingProgressDialog(TopicDetailsActivity.this, "");
        topicdetail_sign = TGmd5.getMD5(topicId);
        topiccommentlist_sign = TGmd5.getMD5(topicId + prePageLastDataObjectId + currPage);
        topicDetailsPresenter = new TopicDetailsPresenter(this);
        topicDetailsPresenter.topicdetail(topicId, topicdetail_sign);

    }

    private void initView() {
        rl_title.getBackground().setAlpha(0);
        tv_title.setTextColor(Color.argb(0, 0, 0, 0));
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_scroller.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_scroller.setMode(PullToRefreshBase.Mode.BOTH);
        pull_refresh_scroller.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currPage = 1;
                prePageLastDataObjectId = "";
                topiccommentlist_sign = TGmd5.getMD5(topicId + prePageLastDataObjectId + currPage);
                topicDetailsPresenter.topiccommentlist(topicId, prePageLastDataObjectId, currPage, topiccommentlist_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    topiccommentlist_sign = TGmd5.getMD5(topicId + prePageLastDataObjectId + currPage);
                    topicDetailsPresenter.topiccommentlist(topicId, prePageLastDataObjectId, currPage, topiccommentlist_sign);
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
        pull_refresh_scroller.setOnScrollChanged(new PullToRefreshScrollView.OnScrollChanged() {
            @Override
            public void onScroll(int scrollX, int scrollY) {
                if (mscrollY < scrollY - 10) {
                    img_comment.setBackgroundResource(R.mipmap.feng_fabu_normal_icon);
                } else if (mscrollY > scrollY + 10) {
                    img_comment.setBackgroundResource(R.mipmap.feng_fabu_icon);
                }
                mscrollY = scrollY;
                int translationY = scrollY;
                if (translationY > ScreenUtils.dpToPx(TopicDetailsActivity.this, 135)) {
                    translationY = (int) ScreenUtils.dpToPx(TopicDetailsActivity.this, 135);
                }
                if (translationY < 0) {
                    translationY = 0;
                }
                int alp = (int) (255 * translationY / ScreenUtils.dpToPx(TopicDetailsActivity.this, 135));
                rl_title.getBackground().setAlpha(alp);
                tv_title.setTextColor(Color.argb(alp, 0, 0, 0));
                if (alp >= 255) {
                    img_back.setBackgroundResource(R.mipmap.rl_back);
                }else {
                    img_back.setBackgroundResource(R.mipmap.mq_ic_back_white);
                }
               /*
                if (avatarTop == 0) {
                    avatarTop = webView.getTop();
                }
                int translationY= -(int) ScreenUtils.dpToPx(TopicDetailsActivity.this, 180);
                Log.e("skahdfkjas", "*******"+translationY );
                Log.e("skahdfkjas", "======"+avatarTop );
                if (0 > avatarTop + translationY) {
                    tv_title.setVisibility(View.VISIBLE);
                } else {
                    tv_title.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = txt_navigation_title.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_title.getBackground().setAlpha(alp);
                tv_title.setTextColor(Color.argb(alp, 255, 255, 255));*/

            }
        });
    }

    private float hearderMaxHeight;
    private float avatarTop;
    private int mscrollY = 0;

    @OnClick({R.id.img_comment, R.id.rl_dismiss_dialog, R.id.rl_back, R.id.rl_send_dialog, R.id.rl_dialog_comment_root})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_dismiss_dialog:
                closeDialogCommentShare();
                break;
            case R.id.img_comment:
                //发布评论
                circleCommentBean = null;
                edit_comment.setHint(getResources().getString(R.string.text_pinglun_hint));
                edit_comment.setText("");
                edit_comment.requestFocus();
                openDialogCommentShare();

                break;
            case R.id.rl_send_dialog:
                closeDialogCommentShare();
                comment_content = edit_comment.getText().toString();
                if (TextUtils.isEmpty(comment_content)) {
                    MyToast.show(TopicDetailsActivity.this, "评论内容不能为空");
                    return;
                }
                String commentId = "";
                if (circleCommentBean != null) {
                    commentId = circleCommentBean.getID();
                }
                commenttopic_sign = TGmd5.getMD5(distributorid + topicId + commentId + comment_content);
                topicDetailsPresenter.commenttopic(distributorid, topicId, commentId, comment_content, commenttopic_sign);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_dialog_comment_root:
                closeDialogCommentShare();
                break;
        }
    }

    private PopupWindow popupWindow;

    public void showpopUpWindow(final String talkcommentId) {
        View contentview = LayoutInflater.from(this)
                .inflate(R.layout.delete_comment_view, null);
        LinearLayout ll_del_view = (LinearLayout) contentview.findViewById(R.id.ll_del_view);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView txt_confirm_del = (TextView) contentview.findViewById(R.id.txt_confirm_del);
        TextView txt_cancel_del = (TextView) contentview.findViewById(R.id.txt_cancel_del);
        txt_confirm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新界面
                String del_comment_sign = TGmd5.getMD5(distributorid + talkcommentId);
                topicDetailsPresenter.topiccommentdel(distributorid, String.valueOf(talkcommentId), del_comment_sign);
                popupWindow.dismiss();
            }
        });
        txt_cancel_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_del_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(img_back, Gravity.BOTTOM, 0, 0);
    }

    // 弹出拍照对话框
    private void openDialogCommentShare() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    // 关闭拍照对话框
    private void closeDialogCommentShare() {
        performDialogAnimation(rl_dialog_zhuangfa_root,
                ll_dialog_zhuangfa_cotent, false);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);

    }


    TopicCommentAdapter.CircleCommentCallBack circleCommentCallBack = new TopicCommentAdapter.CircleCommentCallBack() {
        @Override
        public void showComment(CircleCommentBean circleCommentBean, int layer, int position) {
            TopicDetailsActivity.this.layer = layer;
            TopicDetailsActivity.this.comment_position = position;
            edit_comment.setText("");
            if (layer == 1) {
                edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
            } else {
                edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(position).getDistributorName());
            }
            openDialogCommentShare();
            TopicDetailsActivity.this.circleCommentBean = circleCommentBean;
        }

        @Override
        public void showDelPopWindow(String talkcommentId, int child_level, int del_layer) {
            del_layer_index = del_layer;
            del_child_level = child_level;
            showpopUpWindow(talkcommentId);
        }
    };

    @Override
    public void topicdetailResponse(String resonpse) {
        topicDetailsPresenter.topiccommentlist(topicId, prePageLastDataObjectId, currPage, topiccommentlist_sign);
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            closeLoadingProgressDialog();
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (null != jsonArray && jsonArray.length() > 0) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    topicID = jsonObject1.getString("ID");
                    jsonObject1.getInt("UserID");
                    jsonObject1.getString("UserName");
                    jsonObject1.getString("Title");
                    jsonObject1.getString("PicUrl");
                    jsonObject1.getInt("Hits");
                    jsonObject1.getInt("Hits");
                    jsonObject1.getString("Content");
                    jsonObject1.getInt("State");
                    jsonObject1.getInt("OrderIndex");
                    jsonObject1.getString("CreateTime");
                    View view = LayoutInflater.from(this).inflate(R.layout.topic_layout_title, null);
                   /* ScrollableLayout sl_root = (ScrollableLayout) view.findViewById(R.id.sl_root);
                    sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
                        @Override
                        public void onScroll(int translationY, int maxY) {
                            translationY = -translationY;
                        }
                    });*/
                    txt_navigation_title = (TextView) view.findViewById(R.id.txt_navigation_title);
                    txt_navigation_title.setText("最新评论");
                    empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
                    listView = (ListViewForScrollView) view.findViewById(R.id.listview);
                    topicCommentAdapter = new TopicCommentAdapter(this, topicDetailsPresenter);
                    topicCommentAdapter.setListener(circleCommentCallBack);
                    listView.setAdapter(topicCommentAdapter);


                    webView = (WebView) view.findViewById(R.id.webView);
                    //加载、并显示HTML代码
                    webView.loadDataWithBaseURL(null, jsonObject1.getString("Content"), "text/html", "utf-8", null);
                    TextView txt_topic_hint = (TextView) view.findViewById(R.id.txt_topic_hint);
                    TextView txt_topic_title = (TextView) view.findViewById(R.id.txt_topic_title);
                    ImageView img_topic_pic = (ImageView) view.findViewById(R.id.img_topic_pic);
                    txt_topic_hint.setText(String.format(getResources().getString(R.string.topic_read_discuss), jsonObject1.getInt("Hits") + "", jsonObject1.getInt("CommentCount") + ""));
                    Glide.with(this).load(Url.ROOT + jsonObject1.getString("PicUrl"))//
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(img_topic_pic);
                    txt_topic_title.setText("#" + jsonObject1.getString("Title") + "#");
                    tv_title.setText(jsonObject1.getString("Title"));
                    scrollView.addView(view);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commenttopicResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                String comment_id = "";
                JSONArray result = jsonObject.getJSONArray("result");
                if (result != null && result.length() > 0) {
                    comment_id = (String) result.get(0);
                }
                empty_view.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
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
                    topicCommentAdapter.addcircleCommentData(circleCommentBean);
                }
                topicCommentAdapter.notifyDataSetChanged();
                edit_comment.setText("");
                edit_comment.setHint("想说点什么,就写在这里吧");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void hidekeyword() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
    }

    View layout_navigation_title = null;

    @Override
    public void topiccommentlistResponse(String resonpse) {
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
                    empty_view.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < jsonArraycomment.length(); i++) {
                        CircleCommentBean circleCommentBean = new CircleCommentBean();
                        JSONArray AppendComments = ((JSONObject) jsonArraycomment.get(i)).getJSONArray("AppendComment");
                        if (null != AppendComments && AppendComments.length() > 0) {
                            List<CircleCommentBean> item_commentBeanList = new ArrayList<>();
                            for (int j = 0; j < AppendComments.length(); j++) {
                                CircleCommentBean item_circleCommentBean = new CircleCommentBean();
                                item_circleCommentBean.setID(((JSONObject) AppendComments.get(j)).getString("ID"));
                                prePageLastDataObjectId = ((JSONObject) AppendComments.get(j)).getString("ID");
                                item_circleCommentBean.setDistributorID(((JSONObject) AppendComments.get(j)).getInt("DistributorID"));
                                item_circleCommentBean.setDistributorName(((JSONObject) AppendComments.get(j)).getString("DistributorName"));
                                item_circleCommentBean.setUserType(((JSONObject) AppendComments.get(j)).getInt("UserType"));
                                item_circleCommentBean.setIsRZ(((JSONObject) AppendComments.get(j)).getInt("IsRZ"));
                                item_circleCommentBean.setParentID(((JSONObject) AppendComments.get(j)).getString("ParentID"));
                                item_circleCommentBean.setReplyDistributorID(((JSONObject) AppendComments.get(j)).getInt("ReplyDistributorID"));
                                item_circleCommentBean.setReplyDistributorName(((JSONObject) AppendComments.get(j)).getString("ReplyDistributorName"));
                                item_circleCommentBean.setContent(((JSONObject) AppendComments.get(j)).getString("Content"));
                                item_circleCommentBean.setState(((JSONObject) AppendComments.get(j)).getInt("State"));
                                item_circleCommentBean.setCreateTime(((JSONObject) AppendComments.get(j)).getString("CreateTime"));
                                item_commentBeanList.add(item_circleCommentBean);
                            }
                            circleCommentBean.setItem_circleCommentBeans(item_commentBeanList);
                        }
                        circleCommentBean.setID(((JSONObject) jsonArraycomment.get(i)).getString("ID"));
                        circleCommentBean.setDistributorID(((JSONObject) jsonArraycomment.get(i)).getInt("DistributorID"));
                        circleCommentBean.setDistributorName(((JSONObject) jsonArraycomment.get(i)).getString("DistributorName"));
                        circleCommentBean.setUserType(((JSONObject) jsonArraycomment.get(i)).getInt("UserType"));
                        circleCommentBean.setIsRZ(((JSONObject) jsonArraycomment.get(i)).getInt("IsRZ"));
                        circleCommentBean.setParentID(((JSONObject) jsonArraycomment.get(i)).getString("ParentID"));
                        circleCommentBean.setReplyDistributorID(((JSONObject) jsonArraycomment.get(i)).getInt("ReplyDistributorID"));
                        circleCommentBean.setReplyDistributorName(((JSONObject) jsonArraycomment.get(i)).getString("ReplyDistributorName"));
                        circleCommentBean.setContent(((JSONObject) jsonArraycomment.get(i)).getString("Content"));
                        circleCommentBean.setState(((JSONObject) jsonArraycomment.get(i)).getInt("State"));
                        circleCommentBean.setCreateTime(((JSONObject) jsonArraycomment.get(i)).getString("CreateTime"));
                        circleCommentBean.setSex(((JSONObject) jsonArraycomment.get(i)).getString("Sex"));
                        commentBeanList.add(circleCommentBean);
                    }
                    if (currPage == 1) {
                        topicCommentAdapter.getcircleCommentData().clear();
                    }
                    topicCommentAdapter.setcircleCommentData(commentBeanList);
                    topicCommentAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String type, String resonpse) {
        switch (type) {
            case "detail":
                showStop(resonpse);
                break;
            default:
                showHintDialog(resonpse);
        }
    }

    @Override
    public void excuteSuccessCallBack(String response) {
        //刷新数据
        if (del_child_level >= 0) {
            topicCommentAdapter.getcircleCommentData().get(del_layer_index).getItem_circleCommentBeans().remove(del_child_level);
        } else {
            topicCommentAdapter.getcircleCommentData().remove(del_layer_index);
        }
        topicCommentAdapter.notifyDataSetChanged();
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
