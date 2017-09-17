package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.activity.ApplyCourseActivity;
import com.lvgou.distribution.activity.ApplyTeacherActivity;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.MyTuanBiActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.NoticeDetialActivity;
import com.lvgou.distribution.activity.PushSpeedDetialActivity;
import com.lvgou.distribution.activity.SeriesClassActivity;
import com.lvgou.distribution.activity.TopicDetailsActivity;
import com.lvgou.distribution.activity.WebViewActivity;
import com.lvgou.distribution.adapter.BoutiqueFragmentAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.presenter.CompetitiveStudyPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CompetitiveStudyView;
import com.lvgou.distribution.widget.XListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 * 精品课程
 */

public class BoutiqueFragment extends Fragment implements XListView.IXListViewListener, CompetitiveStudyView {

    private CompetitiveStudyPresenter competitiveStudyPresenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_boutique, container, false);
        competitiveStudyPresenter = new CompetitiveStudyPresenter(this);
        initView();
        onRefresh();
        initClick();
//        initDatas();
        return view;
    }

    private View mLayout;
    private View mSearchlayout;
    private RelativeLayout rl_scroll_button;
    private XListView mListView;
    private BoutiqueFragmentAdapter boutiqueFragmentAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mLayout = getActivity().findViewById(R.id.rl_title);
        mSearchlayout = getActivity().findViewById(R.id.rl_title);
        rl_scroll_button = (RelativeLayout) getActivity().findViewById(R.id.rl_scroll_button);
        mListView = (XListView) view.findViewById(R.id.list_view);
        boutiqueFragmentAdapter = new BoutiqueFragmentAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CollegeManagerActivity) getActivity()).getTime());
        mListView.setDivider(null);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        boutiqueFragmentAdapter.setData(new ArrayList<HashMap<String, Object>>(), width);
        mListView.setAdapter(boutiqueFragmentAdapter);
//        initListener();
    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(getActivity(), SeriesClassActivity.class);
                intent.putExtra("linkUrl", boutiqueFraList.get(position - 1).get("LinkUrl").toString());
                startActivity(intent);*/
                turnView(boutiqueFraList.get(position - 1).get("LinkUrl").toString());
            }
        });
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
                    bundle.putString("shop_name", "");
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

  /*  //------------
    private CollegeManagerActivity.MyOnTouchListener myOnTouchListener;

    private void initListener() {
        myOnTouchListener = new CollegeManagerActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((CollegeManagerActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }

    private boolean isDown = false;
    private boolean isUp = false;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;

    private boolean dispathTouchEvent(MotionEvent event) {
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

    }


    @Override
    public void onAnimationEnd(Animator arg0) {
        if (isDown) {
            setMarginTop(mLayout.getHeight());
        }
        mIsAnim = false;
    }


    @Override
    public void onAnimationRepeat(Animator arg0) {

    }


    @Override
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
        //下拉刷新
        initDatas();
    }

    @Override
    public void onLoadMore() {
        //上拉加载
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        competitiveStudyPresenter.competitiveStudy(distributorid, sign);
    }

    private ArrayList<HashMap<String, Object>> boutiqueFraList = new ArrayList<>();
    private boolean isRefresh = false;

    @Override
    public void OnCompetitiveStudySuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            if (jsa != null && !jsa.equals("")) {
                boutiqueFraList.clear();
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
                    boutiqueFraList.add(map1);
                }
            }
            if (boutiqueFraList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            boutiqueFragmentAdapter.setData(boutiqueFraList, width);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnCompetitiveStudyFialCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeProgress() {

    }

}