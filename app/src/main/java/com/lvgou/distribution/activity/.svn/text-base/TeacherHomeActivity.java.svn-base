package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.BaseFragment;
import com.lvgou.distribution.fragment.TeacherCourseFragment;
import com.lvgou.distribution.fragment.TeacherDataFragment;
import com.lvgou.distribution.fragment.TeacherDynaminFragment;
import com.lvgou.distribution.presenter.CircleUnFollowPresenter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.UseFollowPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleUnFollowView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.UseFollowView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 * 讲师主页
 */

public class TeacherHomeActivity extends BaseActivity implements View.OnClickListener, DistributorHomeView, UseFollowView, CircleUnFollowView {
    private String[] TITLE = new String[]{"课程", "动态", "讲师资料"};
    private DistributorHomePresenter distributorHomePresenter;
    private float avatarTop;
    private TeacherCourseFragment teacherCourseFragment;
    private TeacherDynaminFragment teacherDynaminFragment;
    private TeacherDataFragment teacherDataFragment;
    private String seeDistributorId;
    private String distributorid;
    private boolean isMe = false;
    private String isfocus;
    private UseFollowPresenter useFollowPresenter;
    private CircleUnFollowPresenter circleUnFollowPresenter;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        distributorHomePresenter = new DistributorHomePresenter(this);
        useFollowPresenter = new UseFollowPresenter(this);
        circleUnFollowPresenter = new CircleUnFollowPresenter(this);
        seeDistributorId = getIntent().getStringExtra("seeDistributorId");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (seeDistributorId.equals(distributorid)) {
            isMe = true;
        } else {
            isMe = false;
        }
        initView();
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
        showLoadingProgressDialog(this, "");
        distributorHomePresenter.distributorHome(distributorid, seeDistributorId, sign);
    }

    private ViewPager viewPager;
    private List<BaseFragment> fragmentList;
    private ScrollableLayout sl_root;
    private TextView tv_spit;//标题
    private MyPagerAdapter myPagerAdapter;
    private SlidingTabLayout tabLayout_4;
    private float hearderMaxHeight;
    private RelativeLayout rl_scroll_button;
    private RelativeLayout rl_title;
    private RelativeLayout rl_back;
    //    private ImageView tv_title_focus;//头部的关注
    private ImageView im_picture;//用户头像
    private ImageView iv_title;//title背景
    private TextView tv_focus_num;//关注数
    private RelativeLayout rl_focus;
    private TextView tv_fans_num;//粉丝数
    private RelativeLayout rl_fans;
    private ImageView im_certified;//认证讲师
    private TextView tv_name;//用户名
    private ImageView im_sex;//性别
    private ImageView im_level;//等级
    private TextView tv_teach_num;//教授人数
    private TextView tv_sign;//签名
    private RelativeLayout rl_sign;
    private ImageView im_focus;//关注
    private ImageView im_sign;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tv_spit = (TextView) findViewById(R.id.tv_spit);
        rl_scroll_button = (RelativeLayout) findViewById(R.id.rl_scroll_button);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.getBackground().setAlpha(0);

        im_picture = (ImageView) findViewById(R.id.im_picture);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_focus_num = (TextView) findViewById(R.id.tv_focus_num);
        rl_focus = (RelativeLayout) findViewById(R.id.rl_focus);
        tv_fans_num = (TextView) findViewById(R.id.tv_fans_num);
        rl_fans = (RelativeLayout) findViewById(R.id.rl_fans);
        im_certified = (ImageView) findViewById(R.id.im_certified);
        tv_name = (TextView) findViewById(R.id.tv_name);
        im_sex = (ImageView) findViewById(R.id.im_sex);
        im_level = (ImageView) findViewById(R.id.im_level);
        tv_teach_num = (TextView) findViewById(R.id.tv_teach_num);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        im_sign = (ImageView) findViewById(R.id.im_sign);
        rl_sign = (RelativeLayout) findViewById(R.id.rl_sign);
        im_focus = (ImageView) findViewById(R.id.im_focus);
        im_focus.getBackground().setAlpha(255);
        if (isMe) {
            im_sign.setVisibility(View.VISIBLE);
            rl_sign.setClickable(true);
            rl_sign.setEnabled(true);
            im_focus.setVisibility(View.GONE);
        } else {
            im_sign.setVisibility(View.GONE);
            rl_sign.setClickable(false);
            rl_sign.setEnabled(false);
            im_focus.setVisibility(View.VISIBLE);
        }


        fragmentList = new ArrayList<>();
        teacherCourseFragment = new TeacherCourseFragment();

        teacherDynaminFragment = new TeacherDynaminFragment();
        Bundle bundle = new Bundle();
        bundle.putString("seeDistributorId", seeDistributorId);
        teacherDynaminFragment.setArguments(bundle);
        teacherCourseFragment.setArguments(bundle);
        fragmentList.add(teacherCourseFragment);
        fragmentList.add(teacherDynaminFragment);
        teacherDataFragment = new TeacherDataFragment();
        fragmentList.add(teacherDataFragment);


        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
//        viewPager.addOnPageChangeListener(this);
        tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (avatarTop == 0) {
                    avatarTop = tv_spit.getTop();
                }
                if (0 > avatarTop + translationY) {
                    tv_spit.setVisibility(View.VISIBLE);
                } else {
                    tv_spit.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = rl_scroll_button.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_title.getBackground().setAlpha(alp);
                tv_spit.setTextColor(Color.argb(alp, 255, 255, 255));
                if (alp >= 225) {
                    alp = 255;
                }
                im_focus.getBackground().setAlpha(255 - alp);
            }
        });
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        im_focus.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
        rl_focus.setOnClickListener(this);
        rl_fans.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.im_focus:
                //去关注
                if (isfocus.equals("false")) {
                    //未关注
                    String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
                    showLoadingProgressDialog(this, "");
                    useFollowPresenter.useFollow(distributorid, seeDistributorId, sign);
                } else {
                    //已经关注
                    showialog();
                }
                break;
            case R.id.rl_sign:
                openActivity(PersonalSignActivity.class);
                break;
            case R.id.rl_focus:
                Intent intent = new Intent(TeacherHomeActivity.this, MyFocusActivity.class);
                intent.putExtra("seeDistributorId", seeDistributorId);
                intent.putExtra("realName", realName);
                startActivity(intent);
                break;
            case R.id.rl_fans:
                Intent intent2 = new Intent(TeacherHomeActivity.this, MyFansActivity.class);
                intent2.putExtra("seeDistributorId", seeDistributorId);
                intent2.putExtra("realName", realName);
                startActivity(intent2);
                break;
        }
    }

    private void showialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定抛弃TA吗？");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("再想想");

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
                showLoadingProgressDialog(TeacherHomeActivity.this, "");
                circleUnFollowPresenter.circleUnFollow(distributorid, seeDistributorId, sign);
                mAlertDialog.dismiss();
            }
        });
    }


    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("Attr").toString().equals("1")) {
                //男
                im_sex.setBackgroundResource(R.mipmap.icon_man_home);
            } else if (distributorModeljsa.get("Attr").toString().equals("2")) {
                //女
                im_sex.setBackgroundResource(R.mipmap.icon_woman_home);
            }
            tv_teach_num.setText("学员" + distributorModeljsa.get("AttrLine").toString() + "人");
            realName = distributorModeljsa.get("RealName").toString();
            tv_name.setText(distributorModeljsa.get("RealName").toString());
            tv_spit.setText(distributorModeljsa.get("RealName").toString());
            tv_focus_num.setText(distributorModeljsa.get("FollowCount").toString());
            tv_fans_num.setText(distributorModeljsa.get("FansCount").toString());
            String star = distributorModeljsa.get("Star").toString();
            if (star.equals("1")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_one_icon);
            } else if (star.equals("2")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_two_icon);
            } else if (star.equals("3")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_three_icon);
            } else if (star.equals("4")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_four_icon);
            } else if (star.equals("5")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_five_icon);
            } else if (star.equals("6")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_six_icon);
            } else if (star.equals("7")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_seven_icon);
            } else if (star.equals("8")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_eight_icon);
            } else if (star.equals("9")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_nine_icon);
            } else if (star.equals("10")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_ten_icon);
            } else {
                im_level.setVisibility(View.GONE);
            }
            // Glide.with(TeacherHomeActivity.this).load(Url.ROOT + distributorModeljsa.get("PicUrl").toString()).into(iv_title);
            Glide.with(TeacherHomeActivity.this).load(ImageUtils.getInstance().getPath(seeDistributorId)).error(R.mipmap.teacher_default_head).into(im_picture);
            isfocus = jsa.get(1).toString();
            if (isfocus.equals("false")) {
                //未关注
                im_focus.setBackgroundResource(R.mipmap.focus_icon);

            } else {
                Log.e("kjhsakdf", "---------------");
                //已经关注
                im_focus.setBackgroundResource(R.mipmap.focusedss_icon);

            }
            String signStr = jsa.get(2).toString();
            tv_sign.setText(signStr);
            teacherDataFragment.setDatas(jsa.get(3).toString());
            TITLE[0] = "课程 " + distributorModeljsa.get("TuanBi").toString();
            TITLE[1] = "动态 " + distributorModeljsa.get("FengwenCount").toString();
            tabLayout_4.notifyDataSetChanged();

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


    /**
     * ViewPager 适配器
     */

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        //----------------------------
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }


    @Override
    public void OnUseFollowSuccCallBack(String state, String respanse) {
        //关注好友成功
        closeLoadingProgressDialog();
        isfocus = "true";
        im_focus.setBackgroundResource(R.mipmap.focusedss_icon);
    }

    @Override
    public void OnUseFollowFialCallBack(String state, String respanse) {
        //关注好友失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeUseFollowProgress() {

    }

    @Override
    public void OnCircleUnFollowSuccCallBack(String state, String respanse) {
        //取消关注好友失败
        closeLoadingProgressDialog();
        isfocus = "false";
        im_focus.setBackgroundResource(R.mipmap.focus_icon);
    }

    @Override
    public void OnCircleUnFollowFialCallBack(String state, String respanse) {
        //取消关注好友失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCircleUnFollowProgress() {

    }
}
