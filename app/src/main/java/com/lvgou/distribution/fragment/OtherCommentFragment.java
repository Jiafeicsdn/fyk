package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.DynamicDetailsActivity;
import com.lvgou.distribution.activity.MyCommentListActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleCommentZanViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/12.
 */
public class OtherCommentFragment extends Fragment implements GroupView , OnClassifyPostionClickListener<CircleCommZanEntity> {
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    public PersonalCircleListPresenter personalCircleListPresenter;
    private Context mContext;
    private String prePageLastDataObjectId = "";
    private int pageindex=1;
    private String distributorid;
    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;
    private int total_page;
    private String fenwenId = "";
    private String talkeId = "";
    private String commentId = "";
    private ListViewDataAdapter<CircleCommZanEntity> circleCommZanEntityListViewDataAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_praised_comment, container, false);
        pullToRefreshListView=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
        personalCircleListPresenter = new PersonalCircleListPresenter(this);
        ll_visibilty=(LinearLayout)view.findViewById(R.id.ll_visibilty);
        rl_none=(RelativeLayout)view.findViewById(R.id.rl_none);
        mContext=getActivity();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();
//        ((MyZanListActivity)getActivity()).showLoadingProgressDialog(getActivity(), "");
        initViewHolder();
        initCreateView();
        String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
        personalCircleListPresenter.getPersonalComment(distributorid, prePageLastDataObjectId, pageindex + "", sign);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void initViewHolder() {
        circleCommZanEntityListViewDataAdapter = new ListViewDataAdapter<CircleCommZanEntity>();
        circleCommZanEntityListViewDataAdapter.setViewHolderClass(this, CircleCommentZanViewHolder.class);
        CircleCommentZanViewHolder.setCircleCommZanEntityOnClassifyPostionClickListener(this);
        listView.setAdapter(circleCommZanEntityListViewDataAdapter);
    }
    /**
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageindex = 1;
                prePageLastDataObjectId = "";
                String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                personalCircleListPresenter.getPersonalComment(distributorid, prePageLastDataObjectId, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
                    personalCircleListPresenter.getPersonalComment(distributorid, prePageLastDataObjectId, pageindex + "", sign);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.onRefreshComplete();
                        }
                    }, 500);
                    MyToast.show(mContext, "没有更多数据");
                }
            }
        });
    }
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        closeProgress();
        switch (state) {
            case "1":
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");


                    String sing = TGmd5.getMD5(distributorid + "1");
                    personalCircleListPresenter.seekReadUnreadMessageList(distributorid, "1", sing);

                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);

                        if (pageindex == 1) {
                            total_page = jsonObject_data.getInt("DataPageCount");
                        }
                        String data_one = jsonObject_data.getString("Data");
                        if (pageindex==1) {
                            circleCommZanEntityListViewDataAdapter.removeAll();
                        }
                        JSONArray array_data = new JSONArray(data_one);
                        if (array_data != null && array_data.length() > 0) {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject jsonObject_item = array_data.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                prePageLastDataObjectId = jsonObject_item.getString("ID");
                                String fengwenID = jsonObject_item.getString("FengwenID");
                                String distributorID_one = jsonObject_item.getString("DistributorID");
                                String name = jsonObject_item.getString("DistributorName");
                                String userType = jsonObject_item.getString("UserType");
                                String replyId = jsonObject_item.getString("ReplyDistributorID");
                                String replayName = jsonObject_item.getString("ReplyDistributorName");
                                String IsRZ = jsonObject_item.getString("IsRZ");
                                String title = jsonObject_item.getString("Content");
                                String content = jsonObject_item.getString("FengwenTitle");
                                String time = jsonObject_item.getString("CreateTime");
                                CircleCommZanEntity circleCommZanEntity = new CircleCommZanEntity(id, fengwenID, distributorID_one, name, userType, IsRZ, time, "1", "1", title, distributorid, content, replayName, "", replyId);
                                circleCommZanEntityListViewDataAdapter.append(circleCommZanEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "3":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        ((MyCommentListActivity)mContext).closeDialog();
                        MyToast.show(mContext, "回复成功");
                        ((MyCommentListActivity)mContext).getEditView().setText("");
                        pageindex = 1;
                        prePageLastDataObjectId = "";
//                        String sign = TGmd5.getMD5(distributorid + prePageLastDataObjectId + pageindex);
//                        personalCircleListPresenter.getMycommentlist1(distributorid, prePageLastDataObjectId, pageindex, sign);
                        listView.setSelection(0);
                    } else {
                        MyToast.show(mContext, jsonObject.getString("message"));
                        ((MyCommentListActivity)mContext).closeDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "5":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        PreferenceHelper.write(mContext, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, "0");
                        EventFactory.updateCornerIndex("0");

                        /**
                         * 更新蜂圈的角标
                         */
                        EventFactory.updateHomeCircle("0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "6":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String one_data = array.get(0).toString();
                        JSONObject jsonObject_ = new JSONObject(one_data);

                        String id = jsonObject_.getString("ID");
                        String user_type = jsonObject_.getString("UserType");
                        String content = jsonObject_.getString("Title");
                        String img_path = jsonObject_.getString("PicUrl");
                        String userType = jsonObject_.getString("UserType");
                        String distributorID = jsonObject_.getString("DistributorID");
                        String name = jsonObject_.getString("SourceDistributorName");
                        String ZanCount = jsonObject_.getString("ZanCount");
                        String CommentCount = jsonObject_.getString("CommentCount");
                        String SourceDistributorID = jsonObject_.getString("SourceDistributorID");
                        String Zaned = jsonObject_.getString("Zaned");
                        if (user_type.equals("1")) {
                            Intent intent = new Intent(mContext, NewRecomFengWenDetailsActivity.class);
                            intent.putExtra("position", "0");
                            intent.putExtra("talkId", id);
                            startActivityForResult(intent, 0);
                        } else {
                            Intent intent = new Intent(mContext, NewDynamicDetailsActivity.class);
                            intent.putExtra("talkId", id);
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        closeProgress();
        switch (state) {
            case "1":
                MyToast.show(mContext, "请求失败");
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
            case "6"://{"status":0,"message":"动态已删除","result":null}
                ((BaseActivity)mContext).showHintDialog(respanse);
                break;
        }
    }
    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }
    @Override
    public void closeProgress() {
        ((MyCommentListActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {
        ((MyCommentListActivity) getActivity()).showLoadingProgressDialog(getActivity(),"");
    }

    /***
     * item 点击事件处理
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(CircleCommZanEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                showProgress();
                fenwenId = itemData.getFenwenId();
                String sign_ = TGmd5.getMD5(distributorid + itemData.getFenwenId());
                personalCircleListPresenter.getUserType(distributorid, itemData.getFenwenId(), sign_);
                break;
            case 2:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getUserId()));
                ((MyCommentListActivity)mContext).openActivity(UserPersonalCenterActivity.class, bundle);
                break;
            case 4:
                ((MyCommentListActivity)mContext).openDialog();
                talkeId = itemData.getFenwenId();
                commentId = itemData.getId();
                ((MyCommentListActivity)mContext).transmitData(talkeId, commentId, itemData.getUserName());
                break;
            case 5:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getReplyId()));
                ((MyCommentListActivity)mContext).openActivity(UserPersonalCenterActivity.class, bundle);
                break;
        }
    }
}
