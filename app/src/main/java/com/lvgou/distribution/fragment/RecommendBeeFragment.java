package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.activity.FamousTeacherDetialActivity;
import com.lvgou.distribution.activity.NoticeDetialActivity;
import com.lvgou.distribution.activity.PushSpeedDetialActivity;
import com.lvgou.distribution.activity.TuanbiMangerActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.activity.WebViewActivity;
import com.lvgou.distribution.adapter.CollegeAdViewPage;
import com.lvgou.distribution.bean.BannerBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CollegeDriectEntity;
import com.lvgou.distribution.entity.HotTeacherEntity;
import com.lvgou.distribution.entity.RecommentClassesEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnImageItemListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.RecommentBeePresenter;
import com.lvgou.distribution.utils.AdViewpagerUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ImageCycleView;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.view.MyListView;
import com.lvgou.distribution.view.RecommentBeeView;
import com.lvgou.distribution.viewholder.ColegeDriectViewHolder;
import com.lvgou.distribution.viewholder.HotTeacherViewHolder;
import com.lvgou.distribution.viewholder.RecommentClassViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2016/9/19.
 * 学院首页
 */
public class RecommendBeeFragment extends Fragment implements RecommentBeeView, View.OnClickListener, OnListItemClickListener<RecommentClassesEntity>, OnClassifyPostionClickListener<CollegeDriectEntity>, OnImageItemListener<HotTeacherEntity> {


    private ImageCycleView ad_banner;
    private MyListView myListView;
    private RelativeLayout rl_more_teacher;
    private RelativeLayout rl_more_classes;
    private MyGridView my_gridview;
    private GridView grid_hot;
    private ScrollView scroll_view;
    private TextView tv_none_show;
    private LinearLayout ll_ad_top;
    private CollegeAdViewPage collegeAdViewPage;


    private ListViewDataAdapter<RecommentClassesEntity> recommentClassesEntityListViewDataAdapter;
    private ListViewDataAdapter<CollegeDriectEntity> collegeDriectEntityListViewDataAdapter;
    private ListViewDataAdapter<HotTeacherEntity> hotTeacherEntityListViewDataAdapter;


    private String distributorid = "";
    private RecommentBeePresenter recommentBeePresenter;


    private Dialog dialog_progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomment_bee, container, false);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        recommentBeePresenter = new RecommentBeePresenter(this);

        initView(view);

        initViewHolder();
        scroll_view.smoothScrollTo(0, 0);
        showProgressDialog();
        String sign = TGmd5.getMD5(distributorid);
        recommentBeePresenter.getRecommentBeeData(distributorid, sign);

        rl_more_teacher.setOnClickListener(this);
        rl_more_classes.setOnClickListener(this);
        return view;
    }


    public void initView(View view) {
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        ad_banner = (ImageCycleView) view.findViewById(R.id.ad_view);
        myListView = (MyListView) view.findViewById(R.id.lv_direct_clasess);
        rl_more_teacher = (RelativeLayout) view.findViewById(R.id.rl_more_teacher);
        rl_more_classes = (RelativeLayout) view.findViewById(R.id.rl_more_classess);
        my_gridview = (MyGridView) view.findViewById(R.id.my_gridview);
        grid_hot = (GridView) view.findViewById(R.id.grid_hot);
        tv_none_show = (TextView) view.findViewById(R.id.tv_none_show);
        ll_ad_top = (LinearLayout) view.findViewById(R.id.ll_ad_top);
    }


    public void initViewHolder() {
        recommentClassesEntityListViewDataAdapter = new ListViewDataAdapter<RecommentClassesEntity>();
        recommentClassesEntityListViewDataAdapter.setViewHolderClass(this, RecommentClassViewHolder.class);
        my_gridview.setAdapter(recommentClassesEntityListViewDataAdapter);
        RecommentClassViewHolder.setOnClassifyPostionClickListener(this);


        collegeDriectEntityListViewDataAdapter = new ListViewDataAdapter<CollegeDriectEntity>();
        collegeDriectEntityListViewDataAdapter.setViewHolderClass(this, ColegeDriectViewHolder.class);
        myListView.setAdapter(collegeDriectEntityListViewDataAdapter);
        ColegeDriectViewHolder.setOnClassifyPostionClickListener(this);

        hotTeacherEntityListViewDataAdapter = new ListViewDataAdapter<HotTeacherEntity>();
        hotTeacherEntityListViewDataAdapter.setViewHolderClass(this, HotTeacherViewHolder.class);
        HotTeacherViewHolder.setOnListItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more_teacher:
                EventFactory.turnToTeacher(0);
                break;
            case R.id.rl_more_classess:
                EventFactory.turnToClass(0);
                break;
        }
    }

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {

    }

    /**
     * 取消进度条
     */
    @Override
    public void closeProgress() {
        closeProgressDialog();
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
    private ArrayList<String> urls;
    private ArrayList<String >pathUrl;
    private View header;
    private ViewPager viewpager;
    private LinearLayout lydots;
    private AdViewpagerUtil adViewpagerUtil;
    /**
     * 成功回调
     *
     * @param s
     */
    @Override
    public void excuteSuccessCallBack(String s) {
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            String statu = jsonObject1.getString("status");
            if (statu.equals("1")) {

                String result = jsonObject1.getString("result");
                JSONArray jsonArray = new JSONArray(result);

                final List<BannerBean> beanList = new ArrayList<BannerBean>();
                ll_ad_top.removeAllViews();
                /**********顶部轮播图数据***********/
                urls = new ArrayList<>();
                pathUrl=new ArrayList<>();
                String data_one = jsonArray.get(0).toString();
                JSONArray array_banner = new JSONArray(data_one);
                if (array_banner != null && array_banner.length() > 0) {
                    for (int i = 0; i < array_banner.length(); i++) {

                        JSONObject json_banner = array_banner.getJSONObject(i);
                        String link_url = json_banner.getString("LinkUrl");
                        String img_url = json_banner.getString("PicUrl");
                        urls.add(Url.ROOT + img_url);
                        pathUrl.add(link_url);
                       /* BannerBean bannerBean = new BannerBean();
                        bannerBean.setID(i);
                        bannerBean.setPicUrl(Url.ROOT + img_url);
                        bannerBean.setLinkUrl(link_url);
                        beanList.add(bannerBean);*/
                    }
                    header = LayoutInflater.from(getActivity()).inflate(R.layout.activity_banner_header, null);
                    viewpager = (ViewPager) header.findViewById(R.id.viewpager);
                    lydots = (LinearLayout) header.findViewById(R.id.ly_dots);
                    RelativeLayout rl_toutiao = (RelativeLayout) header.findViewById(R.id.rl_toutiao);
                    View view_line_botton =header.findViewById(R.id.view_line_botton);
                    rl_toutiao.setVisibility(View.GONE);
                    view_line_botton.setVisibility(View.GONE);
                    if (collegeAdViewPage == null) {
                        /*collegeAdViewPage = new CollegeAdViewPage(getActivity());
                        // 第一次设置集合
                        collegeAdViewPage.setViewData(beanList);
                        collegeAdViewPage.startCarousel();
                        collegeAdViewPage.setBannerItemListener(itemCallback);
                        ll_ad_top.addView(collegeAdViewPage);*/
                        adViewpagerUtil = new AdViewpagerUtil(getActivity(), viewpager, lydots, 8, 4, urls);
                        adViewpagerUtil.initVps();
                        ll_ad_top.addView(header);
                    } /*else {
                        collegeAdViewPage.setViewData(beanList);
                        collegeAdViewPage.startCarousel();
                    }*/
                    adViewpagerUtil.setOnAdItemClickListener(new AdViewpagerUtil.OnAdItemClickListener() {
                        @Override
                        public void onItemClick(View v, int flag) {
                            turnView(pathUrl.get(flag));
                            Log.e("askdfhka", "-----------"+pathUrl.get(flag) );
                        }
                    });
                }


                /********直播课程数据*********/
                String data_two = jsonArray.get(1).toString();
                JSONArray array_direct = new JSONArray(data_two);
                collegeDriectEntityListViewDataAdapter.removeAll();
                if (array_direct != null && array_direct.length() > 0) {
                    myListView.setVisibility(View.VISIBLE);
                    tv_none_show.setVisibility(View.GONE);
                    for (int j = 0; j < array_direct.length(); j++) {
                        JSONObject json_direct = array_direct.getJSONObject(j);
                        String id = json_direct.getString("ID");
                        String img_url = json_direct.getString("Banner2");
                        String name = json_direct.getString("TeacherName");
                        String title = json_direct.getString("Theme");
                        int State = json_direct.getInt("State");
                        String signnu = json_direct.getString("People_Apply");
                        String time = json_direct.getString("ZBTime");
                        CollegeDriectEntity collegeDriectEntity = new CollegeDriectEntity(id, name, img_url, title, signnu, time);
                        collegeDriectEntity.setState(State);
                        collegeDriectEntityListViewDataAdapter.append(collegeDriectEntity);
                    }
                } else {
                    myListView.setVisibility(View.GONE);
                    tv_none_show.setVisibility(View.VISIBLE);
                }


                /********热门讲师数据******/
                String data_three = jsonArray.get(2).toString();
                JSONArray array_hot = new JSONArray(data_three);
                hotTeacherEntityListViewDataAdapter.removeAll();
                if (array_hot != null && array_hot.length() > 0) {
                    if (array_hot.length() <= 5) {
                        for (int k = 0; k < array_hot.length(); k++) {
                            JSONObject json_hot = array_hot.getJSONObject(k);
                            String id = json_hot.getString("LinkUrl");
                            String img_path = json_hot.getString("PicUrl");
                            String name = json_hot.getString("Title");
                            String state = json_hot.getString("Other");
                            HotTeacherEntity hotTeacherEntity = new HotTeacherEntity(id, img_path, name, state);
                            hotTeacherEntityListViewDataAdapter.append(hotTeacherEntity);
                        }
                    } else {
                        for (int k = 0; k < 5; k++) {
                            JSONObject json_hot = array_hot.getJSONObject(k);
                            String id = json_hot.getString("LinkUrl");
                            String img_path = json_hot.getString("PicUrl");
                            String name = json_hot.getString("Title");
                            String state = json_hot.getString("Other");
                            HotTeacherEntity hotTeacherEntity = new HotTeacherEntity(id, img_path, name, state);
                            hotTeacherEntityListViewDataAdapter.append(hotTeacherEntity);
                        }
                    }
                }

                int size = hotTeacherEntityListViewDataAdapter.getCount();
                int length = 85;
                int height = 130;
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int gridviewWidth = (int) (size * (length + 4) * density);
                int itemWidth = (int) (length * density);
                int itemHight = (int) (height * density);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, itemHight);
                grid_hot.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                grid_hot.setColumnWidth(itemWidth); // 设置列表项宽
                grid_hot.setHorizontalSpacing(5); // 设置列表项水平间距
                grid_hot.setStretchMode(GridView.NO_STRETCH);
                grid_hot.setNumColumns(size); // 设置列数量=列表集合数

                grid_hot.setAdapter(hotTeacherEntityListViewDataAdapter);

                /********推荐课程数据*****/
                recommentClassesEntityListViewDataAdapter.removeAll();
                String data_four = jsonArray.get(3).toString();
                JSONArray array_recomment = new JSONArray(data_four);
                if (array_recomment != null && array_recomment.length() > 0) {
                    for (int m = 0; m < array_recomment.length(); m++) {
                        JSONObject json_recomment = array_recomment.getJSONObject(m);
                        String id = json_recomment.getString("LinkUrl");
                        String img_path = json_recomment.getString("PicUrl");
                        String title = json_recomment.getString("Title");
                        String sing_num = json_recomment.getString("Price");
                        RecommentClassesEntity recommentClassesEntity = new RecommentClassesEntity(id, img_path, title, sing_num);
                        recommentClassesEntityListViewDataAdapter.append(recommentClassesEntity);
                    }
                }
            }


//           scroll_view.scrollTo(10, 10);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 失败回调
     *
     * @param s
     */
    @Override
    public void excuteFailedCallBack(String s) {
        closeProgressDialog();
        MyToast.show(getActivity(), "请求失败");
    }


    /**
     * 推荐课程 点击回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(RecommentClassesEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        Intent intent = new Intent(getActivity(), FamousTeacherDetialActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }


    /**
     * 直播课程 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(CollegeDriectEntity itemData, int postion) {
        switch (postion) {
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("id", itemData.getId());
                bundle.putString("index", "0");
                Intent intent = new Intent(getActivity(), FamousTeacherDetialActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
        }
    }

    /**
     * 热门讲师点击回调
     *
     * @param itemData
     */
    @Override
    public void onImageItemClick(HotTeacherEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putInt("distributorid", Integer.parseInt(itemData.getId()));
        Intent intent = new Intent(getActivity(), UserPersonalCenterActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();

        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();

        recommentBeePresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        recommentBeePresenter.dettach();
    }


    /**
     * 根据url 判断公告跳转页面
     * "http://agent.quygt.com/supply/selectsupply" 随时赚
     * "http://agent.quygt.com/user/messagedetail/66" 公告详细
     * http://agent.quygt.com/product/details/3036?distributorId=1&source=5 商品详细
     * http://agent.quygt.com/tuanbi/mytasklist 我的任务
     * http://m.quygt.com/product/productsshare 爆品速推
     * http://agent.quygt.com/study/teacherdetail?id=20  名师讲堂
     * http://m.quygt.com/product/sellerproduct?sellerid=813&distributorId=22
     * 导游APP：首页弹出公告活动--》点击跳转时判断，判断如果地址为/product/sellerproduct时，在原来的地址后面加上&distributorId=22
     *
     * @param url
     */
    public void turnView(String url) {
        Bundle bundle = new Bundle();
        if (url != null && url.length() > 0) {
            try {if(url.equals("#")){

            }else
                if (url.contains("user/messagedetail")) {
                    String[] str_urls = url.split("/");
                    bundle.putString("id", str_urls[5]);
                    bundle.putString("index", "0");
                    Intent intent = new Intent(getActivity(), NoticeDetialActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);//公告详情 webview 打开
                } else if (url.contains("supply/selectsupply")) {//随时赚  原生态页面打开
                    bundle.putString("index", "1");
                    Intent intent = new Intent(getActivity(), ApplicationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (url.contains("product/details")) {
                    String[] str_urls = url.split("/");
                    String id_ = str_urls[5];
                    bundle.putString("type_share", "1");
                    bundle.putString("goods_id", id_);
                    bundle.putString("shop_name", "");
                    Intent intent = new Intent(getActivity(), PushSpeedDetialActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);//  商品详情页  webview打开
                } else if (url.contains("product/productsshare")) {
                  /*  bundle.putString("index", "1");
                    bundle.putString("goods_id", "0");
                    bundle.putString("shop_name", "");
                    Intent intent = new Intent(getActivity(), PushSpeedActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);//  爆品速推  原生态打开打开*/
                } else if (url.contains("tuanbi")) {
                    Intent intent = new Intent(getActivity(), TuanbiMangerActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);//  我的任务 原生态打开打开
                } else if (url.contains("study/teacherdetail")) {
                    String[] ids_ = url.split("[?]");
                    bundle.putString("index", "1");
                    bundle.putString("id", ids_[1].substring(3, ids_[1].length()));
                    Intent intent = new Intent(getActivity(), FamousTeacherDetialActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);//  名师讲堂 详情页 原生态打开打开
                } else {
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);// 其余均是 url 文网页打开
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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


    /**
     * binner 点击事件
     */
    public CollegeAdViewPage.BannerItemCallback itemCallback = new CollegeAdViewPage.BannerItemCallback() {
        @Override
        public void ItemClick(String url) {
            LogUtils.e("------------------------" + url);
            turnView(url);
        }
    };
}

