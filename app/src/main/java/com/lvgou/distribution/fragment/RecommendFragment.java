package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.activity.ApplyCourseActivity;
import com.lvgou.distribution.activity.ApplyTeacherActivity;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.LoginActivity;
import com.lvgou.distribution.activity.MyTuanBiActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.NoticeDetialActivity;
import com.lvgou.distribution.activity.PushSpeedDetialActivity;
import com.lvgou.distribution.activity.SeriesClassActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.activity.TeacherListActivity;
import com.lvgou.distribution.activity.TopicDetailsActivity;
import com.lvgou.distribution.activity.WebViewActivity;
import com.lvgou.distribution.adapter.RecommendFragmentAdapter;
import com.lvgou.distribution.adapter.TeacgerViewAdapter;
import com.lvgou.distribution.adapter.XiaoBianGridAdapter;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.presenter.RecommendDatasPresenter;
import com.lvgou.distribution.presenter.TalkisnormalPresenter;
import com.lvgou.distribution.utils.AdViewpagerUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.WorksGridView;
import com.lvgou.distribution.view.AutoVerticalScrollTextView;
import com.lvgou.distribution.view.RecommendDatasView;
import com.lvgou.distribution.view.TalkisnormalView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/6.
 * 推荐
 */

public class RecommendFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener, RecommendDatasView, AdViewpagerUtil.OnAdItemClickListener, TalkisnormalView {

    private View view;
    private RecommendDatasPresenter recommendDatasPresenter;
    private XiaoBianGridAdapter xiaoBianGridAdapter;
    private String isover;
    private String userstate;
    private String usertype;
    private WindowManager wm;
    private int width;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        recommendDatasPresenter = new RecommendDatasPresenter(this);
        talkisnormalPresenter = new TalkisnormalPresenter(this);
        initView();
        onRefresh();
//        initDatas();
        initClick();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(199);
            }
        }, 3000, 3000);
        return view;
    }


    private View mLayout;
    private View mSearchlayout;
    private RelativeLayout rl_scroll_button;
    private XListView mListView;
    private RecommendFragmentAdapter recommendFragmentAdapter;
    private View liveHeader;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ShowCardFragment showCardFragment;
    private FragmentPagerAdapter pagerada;
    private TextView tv_total_number;
    private TextView tv_now_number;
    private View xiaobianHeader;
    private GridView xiaobian_gridview;
    private View teacherHeader;
    private RelativeLayout rl_change;//换一换
    private TextView tv_applycourse;//申请开课
    private RelativeLayout rl_teacher_title;//蜂优讲师更多
    private RelativeLayout rl_becometeacher;
    private TextView tv_baoming;//成为讲师
    private TalkisnormalPresenter talkisnormalPresenter;

    private void initView() {
        mLayout = getActivity().findViewById(R.id.rl_title);
        mSearchlayout = getActivity().findViewById(R.id.rl_title);
        rl_scroll_button = (RelativeLayout) getActivity().findViewById(R.id.rl_scroll_button);
        mListView = (XListView) view.findViewById(R.id.list_view);
        recommendFragmentAdapter = new RecommendFragmentAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CollegeManagerActivity) getActivity()).getTime());
        mListView.setDivider(null);
        recommendFragmentAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(recommendFragmentAdapter);

        //广告的header添加
        bannerHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_banner_header, null);
        viewpager = (ViewPager) bannerHeader.findViewById(R.id.viewpager);
        RelativeLayout rl_adroot = (RelativeLayout) bannerHeader.findViewById(R.id.rl_adroot);
        lydots = (LinearLayout) bannerHeader.findViewById(R.id.ly_dots);
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams para = rl_adroot.getLayoutParams();
        ViewGroup.LayoutParams paravp = viewpager.getLayoutParams();
        int iheight = (int) (width * 34 / 75);
        para.height = iheight;// 控件的高强制设成
        paravp.height = iheight;
        rl_adroot.setLayoutParams(para);
        viewpager.setLayoutParams(paravp);

        textview_auto_roll = (AutoVerticalScrollTextView) bannerHeader.findViewById(R.id.textview_auto_roll);
        mListView.addHeaderView(bannerHeader);
//        initListener();

        //直播课程的添加
        liveHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_live_header, null);
        fragmentManager = getActivity().getSupportFragmentManager();
        tv_total_number = (TextView) liveHeader.findViewById(R.id.tv_total_number);
        tv_now_number = (TextView) liveHeader.findViewById(R.id.tv_now_number);
        tv_applycourse = (TextView) liveHeader.findViewById(R.id.tv_applycourse);
        viewPager = (ViewPager) liveHeader.findViewById(R.id.vp_show_industry_list);
        ViewGroup.LayoutParams paralive = viewPager.getLayoutParams();
        int iheightlive = (int) ((width - ScreenUtils.dpToPx(getActivity(), 40)) * 34 / 67);
        paralive.height = iheightlive;// 控件的高强制设成
        viewPager.setLayoutParams(paralive);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_now_number.setText("" + (position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragments = new ArrayList<Fragment>();
        mListView.addHeaderView(liveHeader);

        //小编推荐
        xiaobianHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_xiaobian_header, null);
        rl_change = (RelativeLayout) xiaobianHeader.findViewById(R.id.rl_change);//换一换
        xiaobian_gridview = (GridView) xiaobianHeader.findViewById(R.id.xiaobian_gridview);
        ViewGroup.LayoutParams paraxb = xiaobian_gridview.getLayoutParams();
        int iheightxb = width * 182 / 375;
        paraxb.height = iheightxb;// 控件的高强制设成
        xiaobian_gridview.setLayoutParams(paraxb);
        xiaoBianGridAdapter = new XiaoBianGridAdapter(getActivity());
        xiaobian_gridview.setAdapter(xiaoBianGridAdapter);
        mListView.addHeaderView(xiaobianHeader);

        //蜂优讲师
        teacherHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_teacher_header, null);
        rl_teacher_title = (RelativeLayout) teacherHeader.findViewById(R.id.rl_teacher_title);
        rl_becometeacher = (RelativeLayout) teacherHeader.findViewById(R.id.rl_becometeacher);
        tv_baoming = (TextView) teacherHeader.findViewById(R.id.tv_baoming);
        mListView.addHeaderView(teacherHeader);

    }

    private View bannerHeader;
    private ViewPager viewpager;
    private LinearLayout lydots;
    private ArrayList<String> bannerUrls = new ArrayList<>();
    private ArrayList<String> bannerLinkUrls = new ArrayList<>();
    private AdViewpagerUtil adViewpagerUtil;
    private AutoVerticalScrollTextView textview_auto_roll;
    private ArrayList<String> touTiaoTextList = new ArrayList<>();
    private ArrayList<String> touTiaoLinkList = new ArrayList<>();

    private int number = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 199) {
                textview_auto_roll.next();
                number++;
                if (touTiaoTextList.size() > 0) {
                    textview_auto_roll.setText(touTiaoTextList.get(number % touTiaoTextList.size()));
                }
            }

        }
    };

    private void initClick() {
        textview_auto_roll.setOnClickListener(this);
        rl_change.setOnClickListener(this);
        tv_applycourse.setOnClickListener(this);
        rl_teacher_title.setOnClickListener(this);
        tv_baoming.setOnClickListener(this);
        xiaobian_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //小编推荐点击listDatas
                String isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (!isRZDialog(userstate, isover)) {
                    ((CollegeManagerActivity) getActivity()).showRZDialog(userstate, isover);
                    return;
                } else {
                    String linkUrl = listDatas.get(position).get("LinkUrl").toString();
               /* String linkUrl = listDatas.get(position).get("LinkUrl").toString();
                Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                intent1.putExtra("id", linkUrl);
                startActivity(intent1);*/
                    turnView(linkUrl);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        usertype = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, "0");
//        ((CollegeManagerActivity) getActivity()).showView(isRZDialog(userstate, isover));
        if (usertype.equals("3")) {
            //如果是讲师
            rl_becometeacher.setVisibility(View.GONE);
        } else {
            //普通导游
            rl_becometeacher.setVisibility(View.VISIBLE);
        }
    }

    private boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }

    //------------
  /*  private CollegeManagerActivity.MyOnTouchListener myOnTouchListener;

    private void initListener() {
        myOnTouchListener = new CollegeManagerActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((CollegeManagerActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }*/

/*    private boolean isDown = false;
    private boolean isUp = false;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;*/

   /* private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -mSearchlayout.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    if (y > mSearchlayout.getHeight()) {
                        setMarginTop(mLayout.getHeight() - mSearchlayout.getHeight());
                    } else {
                        setMarginTop((int) (mLayout.getHeight() - y));
                    }

                } else if (isDown) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = -mSearchlayout.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);

                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    @Override
    public void onAnimationCancel(Animator arg0) {

    }*/


  /*  @Override
    public void onAnimationEnd(Animator arg0) {
        if (isDown) {
            setMarginTop(mLayout.getHeight());
        }
        mIsAnim = false;
    }


    @Override
    public void onAnimationRepeat(Animator arg0) {

    }*/


  /*  @Override
    public void onAnimationStart(Animator arg0) {

    }

    public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        rl_scroll_button.setLayoutParams(layoutParam);
        rl_scroll_button.invalidate();
    }*/


    @Override
    public void onRefresh() {
        initDatas();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        String isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        String userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        if (!isRZDialog(userstate, isover)) {
            ((CollegeManagerActivity) getActivity()).showRZDialog(userstate, isover);
            return;
        }
        switch (v.getId()) {
            case R.id.textview_auto_roll://头条轮播点击
                String sign1 = TGmd5.getMD5(touTiaoLinkList.get(number % touTiaoTextList.size()));
                ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                talkisnormalPresenter.talkisnormal(touTiaoLinkList.get(number % touTiaoTextList.size()), sign1);

                /*Intent intent3 = new Intent(getActivity(), NewRecomFengWenDetailsActivity.class);
                intent3.putExtra("position", "0");
                intent3.putExtra("talkId", touTiaoLinkList.get(number % touTiaoTextList.size()));
                startActivity(intent3);*/
//                MyToast.makeText(getActivity(), touTiaoTextList.get(number % touTiaoTextList.size()), Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_change://换一换
                toChange();
                break;
            case R.id.tv_applycourse://申请开课
                Intent intent = new Intent(getActivity(), ApplyCourseActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_teacher_title://蜂优讲师更多
                //进入讲师列表
                Intent intent1 = new Intent(getActivity(), TeacherListActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.tv_baoming:
                Intent intent2 = new Intent(getActivity(), ApplyTeacherActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private int position = 1;
    private ArrayList<HashMap<String, Object>> listDatas = new ArrayList<>();
    private boolean isFirst = true;

    private void toChange() {
        if (!isFirst) {
            position = (position + 4) % (recommendFraList3.size());
        }
        isFirst = false;
        listDatas.clear();
        for (int i = position - 1, j = 1; i < recommendFraList3.size(); i++, j++) {
            listDatas.add(recommendFraList3.get(i));
            if (j == 4) {
                break;
            }
        }
        if (listDatas.size() < 4) {
            for (int i = 0; i < 4 - listDatas.size(); i++) {
                listDatas.add(recommendFraList3.get(i));
            }
        }
        xiaoBianGridAdapter.setDatas(listDatas);
    }


    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        recommendDatasPresenter.recommendDatas(distributorid, sign);
    }

    private ArrayList<HashMap<String, Object>> recommendFraList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recommendFraList1 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recommendFraList2 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recommendFraList3 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recommendFraList5 = new ArrayList<>();

    @Override
    public void OnRecommendDatasSuccCallBack(String state, String respanse) {
        ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            //添加banner
            if (jsa != null && !jsa.equals("")) {
                recommendFraList.clear();
                JSONArray jsonArray = jsa.getJSONArray(0);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsoo = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    recommendFraList.add(map1);
                }
                JSONArray jsonToutiao = jsa.getJSONArray(1);
                recommendFraList1.clear();
                for (int i = 0; i < jsonToutiao.length(); i++) {
                    JSONObject jsoo = jsonToutiao.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    recommendFraList1.add(map1);
                }
                //直播课程
                JSONArray jsonLive = jsa.getJSONArray(2);
                recommendFraList2.clear();
                for (int i = 0; i < jsonLive.length(); i++) {
                    JSONObject jsoo = jsonLive.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    recommendFraList2.add(map1);
                }
                //小编推荐
                JSONArray jsonXiaob = jsa.getJSONArray(3);
                recommendFraList3.clear();
                for (int i = 0; i < jsonXiaob.length(); i++) {
                    JSONObject jsoo = jsonXiaob.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    recommendFraList3.add(map1);
                }
                //蜂优讲师
                teacherLists.clear();
                JSONArray jsonTeacher = jsa.getJSONArray(4);
                for (int i = 0; i < jsonTeacher.length(); i++) {
                    JSONObject jsoo = jsonTeacher.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    teacherLists.add(map1);
                }
                //带团技巧
                recommendFraList5.clear();
                JSONArray jsonSkills = jsa.getJSONArray(5);
                for (int i = 0; i < jsonSkills.length(); i++) {
                    JSONObject jsoo = jsonSkills.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    recommendFraList5.add(map1);
                }
            }
            bannerUrls.clear();
            bannerLinkUrls.clear();
            for (HashMap<String, Object> stringObjectHashMap : recommendFraList) {
                bannerUrls.add(Url.ROOT + stringObjectHashMap.get("PicUrl").toString());
                bannerLinkUrls.add(stringObjectHashMap.get("LinkUrl").toString());
            }
            if (adViewpagerUtil == null) {
                adViewpagerUtil = new AdViewpagerUtil(getActivity(), viewpager, lydots, 8);
//                adViewpagerUtil.initVps();
            }
            adViewpagerUtil.setDatas(bannerUrls.size(), bannerUrls);

            adViewpagerUtil.setOnAdItemClickListener(this);
            //头条
            touTiaoTextList.clear();
            touTiaoLinkList.clear();
            for (HashMap<String, Object> stringObjectHashMap1 : recommendFraList1) {
                touTiaoTextList.add(stringObjectHashMap1.get("Title").toString());
                touTiaoLinkList.add(stringObjectHashMap1.get("LinkUrl").toString());
            }
            if (touTiaoTextList.size() > 0) {
                textview_auto_roll.setText(touTiaoTextList.get(0));
            }


            //直播课程
            fragments.clear();
            for (HashMap<String, Object> stringObjectHashMap : recommendFraList2) {
                showCardFragment = new ShowCardFragment();
                fragments.add(showCardFragment);
                showCardFragment.setDatas(stringObjectHashMap);
            }
            tv_total_number.setText("/" + recommendFraList2.size());
            viewPager.setOffscreenPageLimit(fragments.size());//卡片数量
            viewPager.setPageMargin((int) ScreenUtils.dpToPx(getActivity(), 10));//两个卡片之间的距离，单位dp
            if (viewPager != null) {
                viewPager.removeAllViews();
            }
            MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
            viewPager.setAdapter(myFragmentPagerAdapter);
            //小编推荐
            toChange();
//            xiaoBianGridAdapter.setDatas(recommendFraList3);
            //蜂优讲师
            initTeacherGtideView();
            //课程分类
            recommendFragmentAdapter.setData(recommendFraList5);
//            skillsAdapter.setData(recommendFraList5);
//            skillsAdapter.notifyDataSetChanged();
            JSONObject jsonObject = jsa.getJSONObject(6);
            if (jsonObject.get("State").toString().equals("7")) {
                loginOut();
                ((CollegeManagerActivity) getActivity()).openActivity(LoginActivity.class);
                ((CollegeManagerActivity) getActivity()).finish();
            }
            shop_name = jsonObject.get("CompanyName").toString();
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, jsonObject.get("ID").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, jsonObject.get("TuanBi").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, jsonObject.get("State").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, jsonObject.get("ParentID").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, jsonObject.get("Ratio").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, jsonObject.get("UserType").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MOBILE, jsonObject.get("Mobile").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, jsonObject.get("PicUrl").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, jsonObject.get("LoginName").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, jsonObject.get("PassWord").toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX, jsa.get(7).toString());
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME, jsonObject.get("RealName").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String shop_name = "";

    @Override
    public void OnRecommendDatasFialCallBack(String state, String respanse) {
        ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
        if (respanse.equals("请登录")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void closeProgress() {

    }

    /**
     * 退出登录
     */
    public void loginOut() {
        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_DISTRIBUTORID, "");
        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "");
        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, "");
        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
    }

    //广告banner的点击
    @Override
    public void onItemClick(View v, int flag) {
        String isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        String userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        if (!isRZDialog(userstate, isover)) {
            ((CollegeManagerActivity) getActivity()).showRZDialog(userstate, isover);
            return;
        } else {
            turnView(bannerLinkUrls.get(flag));
        }
        // MyToast.makeText(getActivity(), "flag:" + flag, Toast.LENGTH_SHORT).show();
    }


    /**
     * 蜂优客4.0推荐的轮播Banner/4.0的精品对应页面链接地址所填的值
     * 注：除以下固定外的页面，请填写正确的地址（#表示不进行跳转
     * 1、公告详情：http://agent.quygt.com/user/messagedetail/77，77为公告文章编号
     * 2、商品详情页：http://m.quygt.com/product/details/5660，5660为商品ID，跳转时APP中会带上导游ID
     * 3、品牌商品页：http://m.quygt.com/product/sellerproduct?sellerid=813，813为品牌商家ID，跳转时会带上导游ID
     * 4、随时赚：http://agent.quygt.com/supply/selectsupply
     * 5、我的任务：http://agent.quygt.com/tuanbi/mytasklist
     * 6、名师讲堂详情页：http://agent.quygt.com/study/teacherdetail?id=36，36为当前名师讲堂ID
     * 7、专题列表：Series+专题的编号 例：Series1
     * 8、活动详情：Activity+活动编号 例：Activity1
     * 9、申请开课：ApplyForStudy
     * 10、申请讲师：ApplyForTeacher
     *
     * @param url
     */
    public void turnView(String url) {
        Bundle bundle = new Bundle();
        if (url != null && url.length() > 0) {
            try {
                if (url.equals("#")) {

                } else if (url.contains("user/messagedetail")) {//公告详情
                    String[] str_urls = url.split("/");
                    bundle.putString("id", str_urls[5]);
                    bundle.putString("index", "0");
                    ((CollegeManagerActivity) getActivity()).openActivity(NoticeDetialActivity.class, bundle);//公告详情 webview 打开
                } else if (url.contains("product/details")) {//商品详情页
                    String[] str_urls = url.split("/");
                    String id_ = str_urls[5];
                    bundle.putString("type_share", "1");
                    bundle.putString("goods_id", id_);
                    bundle.putString("shop_name", shop_name);
                    ((CollegeManagerActivity) getActivity()).openActivity(PushSpeedDetialActivity.class, bundle);//  商品详情页  webview打开
                } else if (url.contains("product/sellerproduct")) {//品牌商品页
                    /*String[] str_urls = url.split("=");
                    String id_ = str_urls[1];*/
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    ((CollegeManagerActivity) getActivity()).openActivity(WebViewActivity.class, bundle);//  商品详情页  webview打开

                } else if (url.contains("supply/selectsupply")) {//随时赚  原生态页面打开

                    bundle.putString("index", "1");
                    ((CollegeManagerActivity) getActivity()).openActivity(ApplicationActivity.class, bundle);
                } else if (url.contains("tuanbi/mytasklist")) {//我的任务
                    ((CollegeManagerActivity) getActivity()).openActivity(MyTuanBiActivity.class);//  我的任务 原生态打开打开
                } else if (url.contains("study/teacherdetail")) {
                    String[] ids_ = url.split("=");
//                    bundle.putString("index", "1");
//                    bundle.putString("id", ids_[1].substring(3, ids_[1].length()));
//                    ((CollegeManagerActivity) getActivity()).openActivity(FamousTeacherDetialActivity.class, bundle);//  名师讲堂 详情页 原生态打开打开

                    Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                    intent1.putExtra("id", ids_[1]);
                    startActivity(intent1);
                } else if (url.contains("Series")) {//专题列表
                    String linkUrl = url.replace("Series", "");
                    Intent intent = new Intent(getActivity(), SeriesClassActivity.class);
                    intent.putExtra("linkUrl", linkUrl);
                    startActivity(intent);
                } else if (url.contains("Activity")) {//活动详情
                    String activityId = url.replace("Activity", "");
                    Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                    intent.putExtra("activityid", activityId);
                    getActivity().startActivity(intent);

                } else if (url.contains("ApplyForStudy")) {//申请开课
                    Intent intent = new Intent(getActivity(), ApplyCourseActivity.class);
                    getActivity().startActivity(intent);
                } else if (url.contains("ApplyForTeacher")) {//申请讲师
                    Intent intent2 = new Intent(getActivity(), ApplyTeacherActivity.class);
                    startActivity(intent2);
                } else if (url.contains("UserFengWen")) {//普通蜂文
                    Bundle pBundle = new Bundle();
                    String fengId = url.replace("UserFengWen", "");
                    Intent intent_one = new Intent(getActivity(), NewDynamicDetailsActivity.class);
                    intent_one.putExtras(pBundle);
                    intent_one.putExtra("position", "0");
                    intent_one.putExtra("talkId", fengId);
                    startActivity(intent_one);
                } else if (url.contains("FengWen")) {//（官方蜂文）
                    Bundle pBundle = new Bundle();
                    String fengId = url.replace("FengWen", "");
                    CircleRecommentEntity circleRecommentEntity = new CircleRecommentEntity();
                    circleRecommentEntity.setID(fengId);
                    Intent intent_one = new Intent(getActivity(), NewRecomFengWenDetailsActivity.class);
                    intent_one.putExtras(pBundle);
                    intent_one.putExtra("talkId", fengId);
                    startActivity(intent_one);
                } else if (url.contains("Topic")) {//（话题）
                    String topicId = url.replace("Topic", "");
                    Intent intent_one = new Intent(getActivity(), TopicDetailsActivity.class);
                    intent_one.putExtra("topicId", topicId);
                    startActivity(intent_one);
                } else {
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    ((CollegeManagerActivity) getActivity()).openActivity(WebViewActivity.class, bundle);// 其余均是 url 文网页打开
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> listFragments;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> al) {
            super(fm);
            listFragments = al;
        }

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

    private WorksGridView gridTeacherGridView;
    private ArrayList<HashMap<String, Object>> teacherLists = new ArrayList<>();

    private void initTeacherGtideView() {
        gridTeacherGridView = (WorksGridView) teacherHeader.findViewById(R.id.grid_teacher);
        setTeacherGridView();
        gridTeacherGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridTeacherGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*HashMap<String, Object> dic2 = starLists.get(position);
                Intent intent = new Intent();
                intent.setClass(GuangChangActivity.this, UserInfoActivity_No2.class);
                intent.putExtra("sso_id", dic2.get("id").toString());
                startActivity(intent);*/
                String isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (!isRZDialog(userstate, isover)) {
                    ((CollegeManagerActivity) getActivity()).showRZDialog(userstate, isover);
                    return;
                } else {
                    //如果是讲师
                    Intent intent1 = new Intent(getActivity(), TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", teacherLists.get(position).get("LinkUrl").toString());
                    startActivity(intent1);
                }


            }
        });
    }

    private void setTeacherGridView() {
        int size = teacherLists.size();
        int length = 76;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridTeacherGridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridTeacherGridView.setColumnWidth(itemWidth); // 设置列表项宽
//        gridsetRankPotentialActorGridViewView.setHorizontalSpacing(20); // 设置列表项水平间距
        gridTeacherGridView.setStretchMode(GridView.NO_STRETCH);
        gridTeacherGridView.setNumColumns(size); // 设置列数量=列表集合数
        TeacgerViewAdapter adapter = new TeacgerViewAdapter(getActivity(), teacherLists);
        gridTeacherGridView.setAdapter(adapter);
    }


    @Override
    public void OnTalkisnormalSuccCallBack(String state, String respanse) {
        ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        try {
            JSONObject jso = new JSONObject(respanse);
            JSONArray jsa = jso.getJSONArray("result");
            if (jsa.get(0).toString().equals("true")) {
                Intent intent3 = new Intent(getActivity(), NewRecomFengWenDetailsActivity.class);
                intent3.putExtra("position", "0");
                intent3.putExtra("talkId", touTiaoLinkList.get(number % touTiaoTextList.size()));
                startActivity(intent3);
            } else {
                MyToast.makeText(getActivity(), "动态已删除", Toast.LENGTH_SHORT).show();
//                showOneTextDialog("动态已删除");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTalkisnormalFialCallBack(String state, String respanse) {
        ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeTalkisnormalProgress() {

    }

}
