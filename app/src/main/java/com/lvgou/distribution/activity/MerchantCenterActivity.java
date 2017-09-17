package com.lvgou.distribution.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MerchantMenuAdapter;
import com.lvgou.distribution.bean.MerchantBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.BaseFragment;
import com.lvgou.distribution.fragment.MerchantFragment;
import com.lvgou.distribution.presenter.MerchantPresenter;
import com.lvgou.distribution.utils.DistanceUtil;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.HorizontalListView;
import com.lvgou.distribution.view.MerchantViewLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 * 商家个人主页
 */
public class MerchantCenterActivity extends BaseActivity implements BaseView{

    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_horizontalmenu)
    private HorizontalListView menu;
    @ViewInject(R.id.img_user_head)
    private CircleImageView img_user_head;
    @ViewInject(R.id.img_teacher_label)
    private ImageView img_teacher_label;
    @ViewInject(R.id.im_bg_top)
    private ImageView im_bg_top;

    @ViewInject(R.id.txt_user_name)
    private TextView txt_user_name;
    @ViewInject(R.id.top_cirlce_follow)
    private ImageView top_cirlce_follow;
    @ViewInject(R.id.rl_top_title)
    private RelativeLayout rl_top_title;
    @ViewInject(R.id.txt_merchant_address)
    private TextView txt_merchant_address;
    @ViewInject(R.id.txt_pathlength)
    private TextView txt_pathlength;
    @ViewInject(R.id.top_user_name)
    private TextView top_user_name;
   /* @ViewInject(R.id.scroll_layout)
    private MerchantViewLayout scroll_layout;*/
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String distributorid;
    List<String> menulist;
    String reportid ="";
    private String merchant_sign = "";
    MerchantMenuAdapter menuAdapter;
    MerchantPresenter merchantPresenter;

    private ScrollView scrollView;
    private View layout_commentlist_emptyView;
     MerchantBean merchantBean=null;
    /*@Override
    public void showTopView(int y, int height) {
        int progress = (int) (new Float(y) * (255 / new Float(height)));//255
        rl_top_title.getBackground().setAlpha(progress);
        rl_top_title.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTopView() {
        rl_top_title.setVisibility(View.GONE);
    }*/
    private ScrollableLayout sl_root;
    private float avatarTop;
    private float hearderMaxHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);
        ViewUtils.inject(this);
        top_cirlce_follow.setVisibility(View.GONE);
//        scroll_layout.setListener(this);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        rl_top_title.getBackground().setAlpha(0);

        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        Intent intent = getIntent();
        reportid = intent.getStringExtra("reportid");
        merchantPresenter = new MerchantPresenter(this);
        showLoadingProgressDialog(MerchantCenterActivity.this, "");
        merchant_sign = TGmd5.getMD5(distributorid + reportid);
        merchantPresenter.reportsellerindex(distributorid, reportid, merchant_sign);
        menulist = new ArrayList();

        menuAdapter = new MerchantMenuAdapter(this);
        menuAdapter.setMenuList(menulist);
        menu.setAdapter(menuAdapter);
    }

    private void initFragments(MerchantBean bean) {
        MerchantFragment fragment_info= new MerchantFragment();
        Bundle bundle =new Bundle();
        bundle.putString("Intro_Content",bean.getIntro());
        fragment_info.setArguments(bundle);
        fragmentList.add(fragment_info);
        MerchantFragment fragment_policy = new MerchantFragment();
        Bundle bundle1 =new Bundle();
        bundle1.putString("Intro_Content",bean.getContent());
        fragment_policy.setArguments(bundle1);
        fragmentList.add(fragment_policy);

        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (avatarTop == 0) {
                    avatarTop = top_user_name.getTop();
                }
                if (0 > avatarTop + translationY) {
                    top_user_name.setVisibility(View.VISIBLE);
                } else {
                    top_user_name.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = menu.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_top_title.getBackground().setAlpha(alp);
                top_user_name.setTextColor(Color.argb(alp, 255, 255, 255));
            }
        });
    }

    @OnClick({R.id.ll_path_guide, R.id.img_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_path_guide:
                if (merchantBean!=null&&!merchantBean.getLatitude().equals("") && !merchantBean.getLongitude().equals("")) {
                    try {
                        Bundle bundle=new Bundle();
                        bundle.putString("index", "1");
                        bundle.putString("name", merchantBean.getShopName());
                        bundle.putString("lat", merchantBean.getLatitude());
                        bundle.putString("lon", merchantBean.getLongitude());
                        bundle.putString("address", merchantBean.getAdderss());
                        openActivity(Report_Shop_Location_Activity.class, bundle);
                    } catch (Exception e) {
                    }
                } else {
                    MyToast.show(this, "经纬度获取获取失败");
                }
                break;
            case R.id.img_back:
                finish();
                break;
            /*case R.id.bg_img_back:
                finish();
                break;*/
        }
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String resonpse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            closeLoadingProgressDialog();
            if (status == 1) {
                menulist.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (null != jsonArray && jsonArray.length() > 0) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    Gson gson = new Gson();
                    merchantBean = gson.fromJson(jsonArray.get(0).toString(), MerchantBean.class);
                    if (merchantBean!=null) {
                        txt_user_name.setText(merchantBean.getShopName());
                        top_user_name.setText(merchantBean.getShopName());
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.icon_merchant_head_defult) //设置图片在下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.icon_merchant_head_defult)//设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.icon_merchant_head_defult).build(); //设置图片加载/解码过程中错误时候显示的图片
                        ImageLoader.getInstance().displayImage(Url.ROOT + merchantBean.getLogo(), img_user_head, options);
                        Glide.with(MerchantCenterActivity.this).load(Url.ROOT + merchantBean.getLogo()).error(R.mipmap.merchant_bg).into(im_bg_top);
                        txt_merchant_address.setText(merchantBean.getAdderss());
                        DecimalFormat df = new DecimalFormat(".##");
                        if (!merchantBean.getLatitude().equals("") && !merchantBean.getLongitude().equals("") && !Constants.Latitude.equals("") && !Constants.Longitude.equals("")) {
                            double juli = DistanceUtil.getDistance(Double.parseDouble(merchantBean.getLongitude()), Double.parseDouble(merchantBean.getLatitude()), Double.parseDouble(Constants.Longitude), Double.parseDouble(Constants.Latitude));
                            if (juli < 1000) {
                                txt_pathlength.setText(juli + "m");
                            } else {
                                double a = juli / 1000.00;
                                if (!(a + "").equals("")) {
                                    txt_pathlength.setText(df.format(a) + "km");
                                }
                            }
                        } else {
                            txt_pathlength.setText("");
                        }
                        img_teacher_label.setVisibility(View.VISIBLE);
                        menulist.add("餐厅介绍");
                        menulist.add("联系方式");
                        menuAdapter.setMenuList(menulist);
                        menuAdapter.setAdaptercallback(new MerchantMenuAdapter.Adaptercallback() {
                            @Override
                            public void Itemclick(int position) {
                                viewPager.setCurrentItem(position);
                            }
                        });
                        menuAdapter.notifyDataSetChanged();
                    }

                    initFragments(merchantBean);
                    viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                        @Override
                        public int getCount() {
                            return fragmentList.size();
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
                            ((MerchantMenuAdapter) menu.getAdapter()).setCurOrderItem(position);
                        }

                    });
                    viewPager.setOffscreenPageLimit(4);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String resonpse) {
        closeLoadingProgressDialog();
        showStop(resonpse);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
