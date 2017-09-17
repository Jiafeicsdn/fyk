package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.PersonalCenterActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.AllTopicActivity;
import com.lvgou.distribution.activity.CirclrFengActivity;
import com.lvgou.distribution.activity.CirclrIndexActivity;
import com.lvgou.distribution.activity.MessageListActivity;
import com.lvgou.distribution.activity.MoreFriendListActivity;
import com.lvgou.distribution.activity.OfficialHomePageActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.adapter.HomePageAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.GroupSendBean;
import com.lvgou.distribution.bean.MayknowpersonBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.FengCircleView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 * 蜂圈关注
 */
public class FollowCircleFragment extends Fragment implements FengCircleView, View.OnClickListener {
    private PullToRefreshListView pull_refresh_list;
    public ListView listView;
    OnArticleSelectedListener mListener;
    HomePagePresenter imFmPersenter;
    private HomePageAdapter homePageAdapter;
    private String mayknowperson_sign;
    private String keyword = "";
    private String tagId = "";
    private int dataPageCount = 0;
    private String followcircle_sign;
    private String unreadcount_sign;
    private int follow_currPage = 1;

    private LinearLayout ly_unread_msg;
    private TextView txt_unread_title;
    //    private ImageView img_unread_head;
    private FengCircleDynamicBean adapterDynamicBean;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;
    private int getNewestDistributorId = 1;
    private String prePageLastDataObjectId = "";
    private boolean isflag = false;

    private Dialog dialog_progress;
    private int unread_count;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_unread_msg:
                unread_count = 0;
                ((CirclrFengActivity) getActivity()).changeTopView(unread_count);
//                MyToast.makeText(getActivity(),getActivity().getParent().getClass().toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MessageListActivity.class);
                startActivity(intent);
                ly_unread_msg.setVisibility(View.GONE);
                EventBus.getDefault().post("unreradmessagefaxian");
                break;
        }
    }

    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("unreradmessagefollow".equals(action)) {
            ly_unread_msg.setVisibility(View.GONE);
        }
    }

    public void showProgressDialog() {
        dialog_progress = createLoadingDialog(getActivity(), "");
        dialog_progress.show();
    }

    public void closeProgressDialog() {
        if (dialog_progress != null && dialog_progress.isShowing()) {
            dialog_progress.dismiss();
        }
    }


    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if ("com.distribution.tugou.state".equals(intent.getAction())) {
                    FengCircleDynamicBean circleDynamicBean = (FengCircleDynamicBean) intent.getSerializableExtra("itemData");
                    int position = intent.getIntExtra("position", 0);
                    try {
                        for (int i = 0; i < homePageAdapter.getCount(); i++) {
                            if (circleDynamicBean.getDistributorID() == homePageAdapter.getItem(i).getDistributorID()) {
                                homePageAdapter.getItem(i).setFollowed(circleDynamicBean.getFollowed());
                            }
                            if (circleDynamicBean.getID().equals(homePageAdapter.getItem(i).getID())) {
                                homePageAdapter.getItem(i).setCommentCount(circleDynamicBean.getCommentCount());
                                homePageAdapter.getItem(i).setZaned(circleDynamicBean.getZaned());
                                homePageAdapter.getItem(i).setZanCount(circleDynamicBean.getZanCount());
                            }
                        }
                    } catch (Exception e) {
                    }
                } else if ("com.distribution.tugou.del".equals(intent.getAction())) {
                    String fengwenId = intent.getStringExtra("fengwenId");
                    for (int i = 0; i < homePageAdapter.getCount(); i++) {
                        if (fengwenId.equals(homePageAdapter.getItem(i).getID())) {
                            homePageAdapter.getFengcircleData().remove(i);
                        }
                    }
                }
                homePageAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        unreadcount_sign = TGmd5.getMD5(mListener.onArticleSelected() + getNewestDistributorId);
        imFmPersenter.unreadcount(mListener.onArticleSelected(), getNewestDistributorId, unreadcount_sign);
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        startTime = System.currentTimeMillis();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (stateReceiver != null) {
            getActivity().unregisterReceiver(stateReceiver);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    View fragment_mayknowperson;
    private ImageView img_dismiss_view;
    private View empty_view;
    private LinearLayout lr_listview;
    private long startTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        listView = pull_refresh_list.getRefreshableView();
        startTime = System.currentTimeMillis();
        lr_listview = (LinearLayout) view.findViewById(R.id.lr_listview);
        empty_view = LayoutInflater.from(getActivity()).inflate(R.layout.none_data_layout, null);
        mListener = (OnArticleSelectedListener) getActivity();
        imFmPersenter = new HomePagePresenter(FollowCircleFragment.this);
        ly_unread_msg = (LinearLayout) view.findViewById(R.id.ly_unread_msg);
        ly_unread_msg.setOnClickListener(this);
        txt_unread_title = (TextView) view.findViewById(R.id.txt_unread_title);
//        img_unread_head = (ImageView) view.findViewById(R.id.img_unread_head);
        EventBus.getDefault().register(this);
        init();
        fragment_mayknowperson = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mayknowperson, null);
        img_dismiss_view = (ImageView) fragment_mayknowperson.findViewById(R.id.img_dismiss_view);
        img_dismiss_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.removeHeaderView(fragment_mayknowperson);
            }
        });
        listView.addHeaderView(fragment_mayknowperson);

        showProgressDialog();
        mayknowperson_sign = TGmd5.getMD5(mListener.onArticleSelected());
        imFmPersenter.mayknowperson(mListener.onArticleSelected(), mayknowperson_sign);
        followcircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + prePageLastDataObjectId + follow_currPage);
        imFmPersenter.followcircle(mListener.onArticleSelected(), prePageLastDataObjectId, follow_currPage, followcircle_sign);
//        unreadcount_sign = TGmd5.getMD5(mListener.onArticleSelected() + getNewestDistributorId);
//        imFmPersenter.unreadcount(mListener.onArticleSelected(), getNewestDistributorId, unreadcount_sign);
        //注册广播
        stateReceiver = new StateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.state");
        intentFilter.addAction("com.distribution.tugou.del");
        getActivity().registerReceiver(stateReceiver, intentFilter);

        return view;
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                follow_currPage = 1;
                prePageLastDataObjectId = "";
                imFmPersenter.mayknowperson(mListener.onArticleSelected(), mayknowperson_sign);
                unreadcount_sign = TGmd5.getMD5(mListener.onArticleSelected() + getNewestDistributorId);
                imFmPersenter.unreadcount(mListener.onArticleSelected(), getNewestDistributorId, unreadcount_sign);
                followcircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + prePageLastDataObjectId + follow_currPage);
                imFmPersenter.followcircle(mListener.onArticleSelected(), prePageLastDataObjectId, follow_currPage, followcircle_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                follow_currPage++;
                if (follow_currPage <= dataPageCount) {
                    followcircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + prePageLastDataObjectId + follow_currPage);
                    imFmPersenter.followcircle(mListener.onArticleSelected(), prePageLastDataObjectId, follow_currPage, followcircle_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        homePageAdapter = new HomePageAdapter(getActivity(), imFmPersenter);
        homePageAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        homePageAdapter.setmAdapterListener(adapterCallBack);
        listView.setAdapter(homePageAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        Log.e("kjgasjdf", "************" + s);
        closeProgressDialog();
        MyToast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        pull_refresh_list.onRefreshComplete();
    }

    public interface OnArticleSelectedListener {
        public String onArticleSelected();
    }

    @Override
    public void findcircleResponse(String s) {
    }

    @Override
    public void zanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                list.get(position).setZaned(1);
                list.get(position).setZanCount(list.get(position).getZanCount() + 1);
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unzanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                list.get(position).setZaned(0);
                list.get(position).setZanCount(list.get(position).getZanCount() - 1);
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void followResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDistributorID() == list.get(position).getDistributorID()) {
                        list.get(i).setFollowed(String.valueOf(1));
                    }
                }
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unfollowResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDistributorID() == list.get(position).getDistributorID()) {
                        list.get(i).setFollowed(String.valueOf(0));
                    }
                }
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findtagandtopicResponse(String resonpse) {

    }

    @Override
    public void unreadcountResponse(String resonpse) {
        //未读消息
        Log.e("kjgasjdf", "--------" + resonpse);
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int state = jsonObject.getInt("status");
            if (state == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                int uncomment = (int) jsonArray.get(0);

                int unlike = (int) jsonArray.get(1);
                int classNum = (int) jsonArray.get(3);
                int systemNum = (int) jsonArray.get(4);
                int fengyouNum = (int) jsonArray.get(5);
                PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, uncomment + "");
                PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, unlike + "");
                PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));
                /**
                 * 更新我的角标
                 */
                EventFactory.updateHomeCenter("0");

                // 公告角标
                int gonggao_num = uncomment + unlike;
                if (gonggao_num > 0) {
                    ((CirclrFengActivity) getActivity()).changeTopView(gonggao_num);
                    ly_unread_msg.setVisibility(View.VISIBLE);
                    txt_unread_title.setText("您有" + gonggao_num + "条新消息");
                } else {
                    ly_unread_msg.setVisibility(View.GONE);
                }
//                Glide.with(getActivity()).load(ImageUtils.getInstance().getPath(String.valueOf(jsonArray.get(1))))
//                        .placeholder(R.mipmap.ic_launcher)//
//                        .error(R.mipmap.ic_launcher)//
//                        .into(img_unread_head);

           /*     DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                        .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
                String path = ImageUtils.getInstance().getPath(String.valueOf(jsonArray.get(2)));
                ImageLoader.getInstance().displayImage(path, img_unread_head, options);*/
            }
        } catch (Exception e) {

        }
    }

    View navigation_title;

    @Override
    public void followcircleResponse(String resonpse) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            closeProgressDialog();
            if (status == 1) {
                //if (navigation_title == null) {
                //navigation_title = LayoutInflater.from(getActivity()).inflate(R.layout.layout_navigation_title_one, null);
                //TextView textView = (TextView) navigation_title.findViewById(R.id.txt_navigation_title);
                //textView.setText("蜂圈动态");
                //listView.addHeaderView(navigation_title);
                //}
                if (follow_currPage == 1) {
                    homePageAdapter.getFengcircleData().clear();
                }
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                if (follow_currPage == 1) {
                    dataPageCount = jsonObject1.getInt("DataPageCount");
                }
                if (isflag) {
                    listView.removeHeaderView(empty_view);
                }
                listView.addHeaderView(empty_view);
                isflag = true;
                List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
                if (jsonArray1 != null && jsonArray1.length() > 0) {
                    isflag = false;
                    listView.removeHeaderView(empty_view);
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        FengCircleDynamicBean fengCircleDynamicBean = new FengCircleDynamicBean();
                        fengCircleDynamicBean.setID(((JSONObject) jsonArray1.get(i)).getString("ID"));
                        prePageLastDataObjectId = ((JSONObject) jsonArray1.get(i)).getString("ID");
                        fengCircleDynamicBean.setDistributorID(((JSONObject) jsonArray1.get(i)).getInt("DistributorID"));
                        fengCircleDynamicBean.setDistributorName(((JSONObject) jsonArray1.get(i)).getString("DistributorName"));
                        fengCircleDynamicBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
                        fengCircleDynamicBean.setIsRZ(((JSONObject) jsonArray1.get(i)).getInt("IsRZ"));
                        fengCircleDynamicBean.setTopicID(((JSONObject) jsonArray1.get(i)).getString("TopicID"));
                        fengCircleDynamicBean.setTopicTitle(((JSONObject) jsonArray1.get(i)).getString("TopicTitle"));
                        fengCircleDynamicBean.setCategoryIDs(((JSONObject) jsonArray1.get(i)).getString("CategoryIDs"));
                        JSONArray jsonCategory = ((JSONObject) jsonArray1.get(i)).getJSONArray("CategoryNames");

                        List<String> categoryNames = new ArrayList<>();
                        for (int j = 0; j < jsonCategory.length(); j++) {
                            categoryNames.add((String) jsonCategory.get(j));
                        }
                        fengCircleDynamicBean.setCategoryNames(categoryNames);
                        fengCircleDynamicBean.setTitle(((JSONObject) jsonArray1.get(i)).getString("Title"));
                        fengCircleDynamicBean.setContent(((JSONObject) jsonArray1.get(i)).getString("Content"));
                        fengCircleDynamicBean.setPicUrl(((JSONObject) jsonArray1.get(i)).getString("PicUrl"));
                        JSONArray piclists = ((JSONObject) jsonArray1.get(i)).getJSONArray("PicJson");
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
                        fengCircleDynamicBean.setZanCount(((JSONObject) jsonArray1.get(i)).getInt("ZanCount"));
                        fengCircleDynamicBean.setCommentCount(((JSONObject) jsonArray1.get(i)).getInt("CommentCount"));
                        fengCircleDynamicBean.setSourceDistributorID(((JSONObject) jsonArray1.get(i)).getInt("SourceDistributorID"));
                        fengCircleDynamicBean.setSourceDistributorName(((JSONObject) jsonArray1.get(i)).getString("SourceDistributorName"));
                        fengCircleDynamicBean.setSourceTitle(((JSONObject) jsonArray1.get(i)).getString("SourceTitle"));
                        fengCircleDynamicBean.setCreateTime(((JSONObject) jsonArray1.get(i)).getString("CreateTime"));
                        fengCircleDynamicBean.setFollowed(((JSONObject) jsonArray1.get(i)).getString("Followed"));
                        fengCircleDynamicBean.setZaned(((JSONObject) jsonArray1.get(i)).getInt("Zaned"));
                        fengCircleDynamicBean.setSex(((JSONObject) jsonArray1.get(i)).getString("Sex"));
                        fengCircleDynamicBean.setCurrentLocation(((JSONObject) jsonArray1.get(i)).getString("CurrentLocation"));
                        circleDynamicBeans.add(fengCircleDynamicBean);
                    }
                    homePageAdapter.setFengcircleData(circleDynamicBeans);
                }
                homePageAdapter.notifyDataSetChanged();
                if (follow_currPage == 1) {
                    listView.setSelection(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    LinearLayout hsview_mayknowperson;

    @Override
    public void mayknowpersonResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                if (fragment_mayknowperson.getVisibility() == View.GONE) {
                    return;
                }
                if (hsview_mayknowperson == null) {
                    hsview_mayknowperson = (LinearLayout) fragment_mayknowperson.findViewById(R.id.hsview_mayknowperson);
                } else {
                    hsview_mayknowperson.removeAllViews();
                }

                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (null != jsonArray && jsonArray.length() > 0) {
                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);
                    if (null != jsonArray1 && jsonArray1.length() > 0) {
                        List<MayknowpersonBean> mayknowpersonBeanList = new ArrayList<>();
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            MayknowpersonBean mayknowpersonBean = new MayknowpersonBean();
                            mayknowpersonBean.setID(((JSONObject) jsonArray1.get(i)).getInt("ID"));
                            mayknowpersonBean.setLoginName(((JSONObject) jsonArray1.get(i)).getString("LoginName"));
                            mayknowpersonBean.setPassWord(((JSONObject) jsonArray1.get(i)).getString("PassWord"));
                            mayknowpersonBean.setState(((JSONObject) jsonArray1.get(i)).getInt("State"));
                            mayknowpersonBean.setRealName(((JSONObject) jsonArray1.get(i)).getString("RealName"));
                            mayknowpersonBean.setServiceRealName(((JSONObject) jsonArray1.get(i)).getString("ServiceRealName"));
                            mayknowpersonBean.setCompanyName(((JSONObject) jsonArray1.get(i)).getString("CompanyName"));
                            mayknowpersonBean.setMobile(((JSONObject) jsonArray1.get(i)).getString("Mobile"));
                            mayknowpersonBean.setParentID(((JSONObject) jsonArray1.get(i)).getInt("ParentID"));
                            mayknowpersonBean.setTuanBi(((JSONObject) jsonArray1.get(i)).getInt("TuanBi"));
                            mayknowpersonBean.setRatio(((JSONObject) jsonArray1.get(i)).getInt("Ratio"));
                            mayknowpersonBean.setStar(((JSONObject) jsonArray1.get(i)).getInt("Star"));
                            mayknowpersonBean.setCountryPath(((JSONObject) jsonArray1.get(i)).getString("CountryPath"));
                            mayknowpersonBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
                            mayknowpersonBean.setLoginCount(((JSONObject) jsonArray1.get(i)).getInt("LoginCount"));
                            mayknowpersonBean.setAttr(((JSONObject) jsonArray1.get(i)).getInt("Attr"));
                            mayknowpersonBean.setFengwenCount(((JSONObject) jsonArray1.get(i)).getInt("FengwenCount"));
                            mayknowpersonBean.setFollowCount(((JSONObject) jsonArray1.get(i)).getInt("FollowCount"));
                            mayknowpersonBean.setFansCount(((JSONObject) jsonArray1.get(i)).getInt("FansCount"));
                            mayknowpersonBean.setPicUrl(((JSONObject) jsonArray1.get(i)).getString("PicUrl"));
                            mayknowpersonBean.setCreateTime(((JSONObject) jsonArray1.get(i)).getString("CreateTime"));
                            mayknowpersonBean.setLastLoginTime(((JSONObject) jsonArray1.get(i)).getString("LastLoginTime"));
                            mayknowpersonBeanList.add(mayknowpersonBean);
                            View layout_mayknowperson = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mayknowperson, null);
                            CircleImageView circleImageView = (CircleImageView) layout_mayknowperson.findViewById(R.id.circleImageview);
                            TextView txt_recom_course_title = (TextView) layout_mayknowperson.findViewById(R.id.txt_user_name);
                            txt_recom_course_title.setText(((JSONObject) jsonArray1.get(i)).getString("RealName"));
                            layout_mayknowperson.setTag(mayknowpersonBean);
//                            Glide.with(this).load(ImageUtils.getInstance().getPath(String.valueOf(mayknowpersonBean.getID())))
//                                    .placeholder(R.mipmap.teacher_default_head)
//                                    .error(R.mipmap.teacher_default_head)
//                                    .into(circleImageView);
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                                    .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                                    .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
                            String path = ImageUtils.getInstance().getPath(String.valueOf(mayknowpersonBean.getID()));
                            ImageLoader.getInstance().displayImage(path, circleImageView, options);
                            layout_mayknowperson.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MayknowpersonBean mayknowpersonBean = (MayknowpersonBean) v.getTag();
                                    if (mayknowpersonBean.getUserType() == 3) {
                                        Intent intent1 = new Intent(getActivity(), TeacherHomeActivity.class);
                                        intent1.putExtra("seeDistributorId",  mayknowpersonBean.getID()+"");
                                        startActivity(intent1);
                                        /*Intent intent = new Intent(getActivity(), OfficialHomePageActivity.class);
                                        intent.putExtra("seeDistributorId", mayknowpersonBean.getID());
                                        startActivity(intent);*/
                                    } else {
                                        Intent intent1 = new Intent(getActivity(), DistributorHomeActivity.class);
                                        intent1.putExtra("seeDistributorId", mayknowpersonBean.getID()+"");
                                        startActivity(intent1);
                                        /*Intent intent = new Intent(getActivity(), UserPersonalCenterActivity.class);
                                        intent.putExtra("distributorid", mayknowpersonBean.getID());
                                        startActivity(intent);*/
                                    }
                                }
                            });
                            hsview_mayknowperson.addView(layout_mayknowperson);
                        }
                        View layout_more = LayoutInflater.from(getActivity()).inflate(R.layout.layout_more, null);
                        ImageView img_more = (ImageView) layout_more.findViewById(R.id.img_topic_more);
                        img_more.setImageResource(R.mipmap.icon_friend_more);
                        img_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转更多认识的人
                                Intent intent = new Intent(getActivity(), MoreFriendListActivity.class);
                                startActivity(intent);
                            }
                        });
                        hsview_mayknowperson.addView(layout_more);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HomePageAdapter.AdapterCallBack adapterCallBack = new HomePageAdapter.AdapterCallBack() {
        @Override
        public void getItemData(FengCircleDynamicBean circleDynamicBean) {
            adapterDynamicBean = circleDynamicBean;
        }
    };

    private void sendBrodCastReciver() {
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.state");
        intent.putExtra("itemData", adapterDynamicBean);
        getActivity().sendBroadcast(intent);
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.UPDATE_FENGWEN) {
            follow_currPage = 1;
            prePageLastDataObjectId = "";
            followcircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + prePageLastDataObjectId + follow_currPage);
            imFmPersenter.followcircle(mListener.onArticleSelected(), prePageLastDataObjectId, follow_currPage, followcircle_sign);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == false) {
            startTime = System.currentTimeMillis();
        } else {
            long time = System.currentTimeMillis() - startTime;
            if (time >= 1000 * 60 * Constants.SECOND) {
                follow_currPage = 1;
                showProgressDialog();
                unreadcount_sign = TGmd5.getMD5(mListener.onArticleSelected() + getNewestDistributorId);
                imFmPersenter.unreadcount(mListener.onArticleSelected(), getNewestDistributorId, unreadcount_sign);
                followcircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + prePageLastDataObjectId + follow_currPage);
                imFmPersenter.followcircle(mListener.onArticleSelected(), prePageLastDataObjectId, follow_currPage, followcircle_sign);
            }
            startTime = System.currentTimeMillis();
        }
    }


    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loding, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }
}