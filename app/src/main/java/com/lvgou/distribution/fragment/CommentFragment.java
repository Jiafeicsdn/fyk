package com.lvgou.distribution.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.adapter.FengCircleCommentAdapter;
import com.lvgou.distribution.bean.CircleCommentBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.CommentListPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CmentFgView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class CommentFragment extends Fragment implements CmentFgView, View.OnClickListener {
    private PullToRefreshListView pull_refresh_list;
    private RelativeLayout rl_none;
    public static ListView listView;
    OnArticleSelectedListener mListener;
    private LinearLayout ll_visibility;
    private String distributorid;
    private String sex;
    private String distributorName;
    private int pageIndex = 1;
    public static FengCircleCommentAdapter fengCircleCommentAdapter;
    private Context context;
    private int dataPageCount;
    private CommentListPresenter commentListPresenter;
    private String talkcommentlist_sign;
    private String prePageLastDataObjectId = "";
    int layer;//层级
    int comment_position;//点击的评论item
    private String talkId;
    PopupWindow popupWindow;
    View view;
    private String comment_content;
    private int tuanbi = 0;
    private CircleCommentBean circleCommentBean;
    private int del_layer_index;
    private int del_child_level;

    //private LinearLayout ll_comment_view;
//    ImageView img_dismiss;
//    ImageView img_send;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_no_title_list, container, false);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        RelativeLayout rl_title= (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_title.setVisibility(View.GONE);
                //        ll_comment_view=(LinearLayout)view.findViewById(R.id.ll_comment_view);
//        ll_comment_view.setVisibility(View.GONE);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        TextView rl_none_title=(TextView)view.findViewById(R.id.nono_data_title);
        rl_none_title.setText("还没有评论，发表下想法吧！");
        listView = pull_refresh_list.getRefreshableView();
        edit_comment = (EditText) view.findViewById(R.id.edit_comment);
//            img_dismiss = (ImageView) view.findViewById(R.id.img_dismiss);
//            img_send = (ImageView) view.findViewById(R.id.img_send);
        mListener = (OnArticleSelectedListener) getActivity();
        distributorid = mListener.getDistributorId();
        distributorName = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        sex = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        talkId = mListener.getTalkId();
        commentListPresenter = new CommentListPresenter(this);
        commentListPresenter.attach(this);
        talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + pageIndex);
        commentListPresenter.talkcommentlist(talkId, prePageLastDataObjectId, pageIndex, talkcommentlist_sign);
        init();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (commentListPresenter != null) {
            commentListPresenter.dettach();
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
    public void excuteSuccessCallBack(String type, String s) {
        pull_refresh_list.onRefreshComplete();
        switch (type) {
            case "talkcoment":
                talkcommentResponse(s);
                break;
            case "talkcomentdel":
                 //刷新数据
                if (del_child_level >= 0) {
                    fengCircleCommentAdapter.getcircleCommentData().get(del_layer_index).getItem_circleCommentBeans().remove(del_child_level);
                } else {
                    fengCircleCommentAdapter.getcircleCommentData().remove(del_layer_index);
                }
                fengCircleCommentAdapter.notifyDataSetChanged();
                ChangeUiView(fengCircleCommentAdapter.getCount());
                mListener.UpdateCommentNum();
                break;
            case "commenttalk":
                commentTalkResponse(s);
                break;
        }

    }

    @Override
    public void excuteFailedCallBack(String s) {
        pull_refresh_list.onRefreshComplete();
    }

    private void commentTalkResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                String comment_id = "";
                JSONArray result = jsonObject.getJSONArray("result");
                if (result != null && result.length() > 0) {
                    comment_id = (String) result.get(0);
                }
                ll_visibility.setVisibility(View.VISIBLE);
                rl_none.setVisibility(View.GONE);
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
                    /* 修改*/
                    itemcircleCommentBean.setID(comment_id);
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
//                et_evaluate.setText("");
//                et_evaluate.setHint(getResources().getString(R.string.text_pinglun_hint));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void talkcommentResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                List<CircleCommentBean> commentBeanList = new ArrayList<>();
                if (pageIndex == 1) {
                    dataPageCount = jsonObject1.getInt("DataPageCount");
                }
                JSONArray jsonArraycomment = jsonObject1.getJSONArray("Data");
                if (null != jsonArraycomment && jsonArraycomment.length() > 0) {
                    ll_visibility.setVisibility(View.VISIBLE);
                    rl_none.setVisibility(View.GONE);
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
                        circleCommentBean.setTuanBi(((JSONObject) jsonArraycomment.get(i)).getInt("TuanBi"));
                        circleCommentBean.setCreateTime(((JSONObject) jsonArraycomment.get(i)).getString("CreateTime"));
                        commentBeanList.add(circleCommentBean);
                    }
                    if (pageIndex == 1) {
                        fengCircleCommentAdapter.getcircleCommentData().clear();
                    }
                    fengCircleCommentAdapter.setcircleCommentData(commentBeanList);
                    fengCircleCommentAdapter.notifyDataSetChanged();
                } else {
                    if (pageIndex == 1) {
                        rl_none.setVisibility(View.VISIBLE);
                        ll_visibility.setVisibility(View.GONE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发布评论
            case R.id.tv_sned:
//                comment_content = et_evaluate.getText().toString();
//                if (TextUtils.isEmpty(comment_content)) {
//                    MyToast.show(NewDynamicDetailsActivity.this, "评论内容不能为空");
//                    return;
//                }
                String commentId = "";
//                if (circleCommentBean != null) {
//                    commentId = circleCommentBean.getID();
//                }
                String sign = TGmd5.getMD5(distributorid + talkId + commentId + comment_content + tuanbi);
                commentListPresenter.commenttalk(distributorid, talkId, commentId, comment_content, tuanbi, sign);
                break;
        }
    }

    public interface OnArticleSelectedListener {
        public String getDistributorId();

        public String getTalkId();

        public void UpdateCommentNum();

        public void changeData(int layer, CircleCommentBean circleCommentBean, int comment_position);

        public void showCommentChildView();
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                prePageLastDataObjectId = "";
                talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + pageIndex);
                commentListPresenter.talkcommentlist(talkId, prePageLastDataObjectId, pageIndex, talkcommentlist_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                if (pageIndex <= dataPageCount) {
                    talkcommentlist_sign = TGmd5.getMD5(talkId + prePageLastDataObjectId + pageIndex);
                    commentListPresenter.talkcommentlist(talkId, prePageLastDataObjectId, pageIndex, talkcommentlist_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 100);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        fengCircleCommentAdapter = new FengCircleCommentAdapter(context, commentListPresenter);
        fengCircleCommentAdapter.setListener(circleCommentCallBack);
        listView.setAdapter(fengCircleCommentAdapter);
    }

    public void ChangeUiView(int size) {
        if (size > 0) {
            rl_none.setVisibility(View.GONE);
            ll_visibility.setVisibility(View.VISIBLE);
        } else {
            rl_none.setVisibility(View.VISIBLE);
            ll_visibility.setVisibility(View.GONE);
        }
    }

    /***
     * 回复操作
     */
    EditText edit_comment;
    FengCircleCommentAdapter.CircleCommentCallBack circleCommentCallBack = new FengCircleCommentAdapter.CircleCommentCallBack() {
        @Override
        public void showComment(CircleCommentBean circleCommentBean, int layer, int position) {
            comment_position = position;
//            edit_comment.setText("");
//            if (layer == 1) {
//                edit_comment.setHint("回复:" + circleCommentBean.getDistributorName());
//            } else {
//                edit_comment.setHint("回复:" + circleCommentBean.getItem_circleCommentBeans().get(position).getDistributorName());
//            }
//            openDialogCommentShare();
            mListener.changeData(layer, circleCommentBean, position);
//            ll_comment_view.setVisibility(View.VISIBLE);
            mListener.showCommentChildView();
//            edit_comment.setFocusable(true);
//            edit_comment.requestFocus();
//            View contentview = LayoutInflater.from(context)
//                    .inflate(R.layout.comment_popupwindow_view, null);
//            popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            edit_comment = (EditText) contentview.findViewById(R.id.edit_comment);
//            edit_comment.setFocusable(true);
//            ImageView img_dismiss = (ImageView) contentview.findViewById(R.id.img_dismiss);
//            ImageView img_send = (ImageView) contentview.findViewById(R.id.img_send);
//            img_dismiss.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    popupWindow.dismiss();
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
//                }
//            });
//            img_send.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    popupWindow.dismiss();
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
//                }
//            });
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

//            popupWindow.setFocusable(true);
//            popupWindow.setOutsideTouchable(true);
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

        @Override
        public void showDelPopWindow(String talkcommentId, int child_level, int layer_index) {
            del_layer_index = layer_index;
            del_child_level = child_level;
            showpopUpWindow(talkcommentId);
        }
    };

    public void showpopUpWindow(final String talkcommentId) {
        View contentview = LayoutInflater.from(context)
                .inflate(R.layout.delete_comment_view, null);
        LinearLayout ll_del_view=(LinearLayout)contentview.findViewById(R.id.ll_del_view);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView txt_confirm_del = (TextView) contentview.findViewById(R.id.txt_confirm_del);
        TextView txt_cancel_del = (TextView) contentview.findViewById(R.id.txt_cancel_del);
        txt_confirm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新界面
                String del_comment_sign = TGmd5.getMD5(distributorid + talkcommentId);
                commentListPresenter.talkcommentdel(distributorid, String.valueOf(talkcommentId), del_comment_sign);
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
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
