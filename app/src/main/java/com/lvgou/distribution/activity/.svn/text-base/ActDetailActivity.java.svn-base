package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.BaseFragment;
import com.lvgou.distribution.fragment.DiscussFragment;
import com.lvgou.distribution.fragment.IntroductionFragment;
import com.lvgou.distribution.presenter.ActivityApplyPresenter;
import com.lvgou.distribution.presenter.ActivityDetailPresenter;
import com.lvgou.distribution.presenter.CancelApplyActPresenter;
import com.lvgou.distribution.presenter.CommentSubmitPresenter;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ActivityApplyView;
import com.lvgou.distribution.view.ActivityDetailView;
import com.lvgou.distribution.view.CancelApplyActView;
import com.lvgou.distribution.view.CommentSubmitView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 * 活动详情
 */

public class ActDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, ActivityDetailView, View.OnClickListener, ActivityApplyView, CommentSubmitView, CancelApplyActView {

    private IntroductionFragment introductionFragment;
    private DiscussFragment discussFragment;
    private ActivityDetailPresenter activityDetailPresenter;
    private String activityid;
    private ActivityApplyPresenter activityApplyPresenter;
    private CommentSubmitPresenter commentSubmitPresenter;
    private String distributorid;
    private MyPagerAdapter myPagerAdapter;
    private SlidingTabLayout tabLayout_4;
    private CancelApplyActPresenter cancelApplyActPresenter;
    private String state1;
    private String startTime;
    // android:windowSoftInputMode="adjustResize|stateHidden"

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);
        activityDetailPresenter = new ActivityDetailPresenter(this);
        activityApplyPresenter = new ActivityApplyPresenter(this);
        commentSubmitPresenter = new CommentSubmitPresenter(this);
        cancelApplyActPresenter = new CancelApplyActPresenter(this);
        activityid = getIntent().getStringExtra("activityid");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initView();
        initDatas();
        initClick();
    }

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + activityid);
        showLoadingProgressDialog(this, "");
        activityDetailPresenter.activityDetailDatas(distributorid, activityid, sign);
    }

    private ViewPager viewPager;
    private List<BaseFragment> fragmentList;
    private ScrollableLayout sl_root;
    private TextView tv_spit;
    private RelativeLayout rl_scroll_button;
    private RelativeLayout rl_title;
    private RelativeLayout rl_introduction;
    private LinearLayout ll_bottom;
    private ImageView iv_title;
    private RelativeLayout rl_back;
    private RelativeLayout rl_share;
    private TextView tv_apply;
    private TextView tv_sned;
    private EditText et_evaluate;//编辑评论
    private float hearderMaxHeight;
    private RelativeLayout rl_dialog_share_root;
    private LinearLayout ll_dialog_share_cotent;
    private RelativeLayout rl_qq;
    private RelativeLayout rl_qzone;
    private RelativeLayout rl_weixin;
    private RelativeLayout rl_pengyou;
    private RelativeLayout rl_dismiss;

    private void initView() {
        rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
        rl_qzone = (RelativeLayout) findViewById(R.id.rl_qzone);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        rl_pengyou = (RelativeLayout) findViewById(R.id.rl_pengyou);
        rl_dismiss = (RelativeLayout) findViewById(R.id.rl_dismiss);
        ll_dialog_share_cotent = (LinearLayout) findViewById(R.id.ll_dialog_share_cotent);
        rl_dialog_share_root = (RelativeLayout) findViewById(R.id.rl_dialog_share_root);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tv_spit = (TextView) findViewById(R.id.tv_spit);
        rl_scroll_button = (RelativeLayout) findViewById(R.id.rl_scroll_button);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.getBackground().setAlpha(0);
        rl_introduction = (RelativeLayout) findViewById(R.id.rl_introduction);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        tv_apply = (TextView) findViewById(R.id.tv_apply);
        tv_sned = (TextView) findViewById(R.id.tv_sned);
        et_evaluate = (EditText) findViewById(R.id.et_evaluate);
        fragmentList = new ArrayList<>();
        introductionFragment = new IntroductionFragment();
        fragmentList.add(introductionFragment);
        discussFragment = new DiscussFragment();
        Bundle bundle = new Bundle();
        bundle.putString("activityid", activityid);
        discussFragment.setArguments(bundle);
        fragmentList.add(discussFragment);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(this);
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
            }
        });

    }

    private float avatarTop;
    private String[] TITLE = new String[]{"简介", "讨论区"};

    public void notifyTitle(String totalitems) {
        TITLE[1] = "讨论区(" + totalitems + ")";
        Log.e("khsadkfjh", "***********" + TITLE[1]);
        tabLayout_4.notifyDataSetChanged();
    }


    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        tv_apply.setOnClickListener(this);
        tv_sned.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
        rl_qzone.setOnClickListener(this);
        rl_weixin.setOnClickListener(this);
        rl_pengyou.setOnClickListener(this);
        rl_dismiss.setOnClickListener(this);
    }

//    private ArrayList<HashMap<String, Object>> actDetailList = new ArrayList<>();

    boolean isshowBM = false;//下面报名是否显示 false 显示 true不显示

    @Override
    public void OnActivityDetailSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject jsonObject = jsa.getJSONObject(0);
            tv_spit.setText(jsonObject.get("Title").toString());
            share_title = jsonObject.get("Title").toString();
            state1 = jsonObject.get("State").toString();
            startTime = jsonObject.get("StartTime").toString().replace("T", " ");
            share_content = jsonObject.get("ActivityIntro").toString();
            share_img = Url.ROOT + jsonObject.get("PicUrl").toString();
            if (state1.equals("2")) {
                //已结束
                rl_introduction.setVisibility(View.GONE);
            } else if (state1.equals("1")) {
                //进行中
                try {
                    Date nowData = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startData = sdf.parse(startTime);
                    boolean boo = compareDate(nowData, startData);
                    if (boo || distributorid.equals(jsonObject.get("DistributorID").toString())) {
                        //当前时间大
                        isshowBM = true;
                        rl_introduction.setVisibility(View.GONE);
                    } else {
                        //当前时间小
                        isshowBM = false;
                        rl_introduction.setVisibility(View.VISIBLE);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Glide.with(this).load(Url.ROOT + jsonObject.get("PicUrl").toString()).into(iv_title);
            if (jsa.get(1).toString().equals("0")) {
                //没报名
                tv_apply.setText("立即报名");
            } else {
                //已经报名
                tv_apply.setText("取消报名");
            }
            introductionFragment.setIntroduct(jsa);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnActivityDetailFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeActivityDetailProgress() {

    }

    private String share_url = Url.XIANSHANG_ROOT + "/activity/activitydetail/";
    private String share_title = "";
    private String share_img = "";
    private String share_content = "";

    @Override
    public void onClick(View v) {
        UMImage image = new UMImage(ActDetailActivity.this, share_img);
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_share://分享
                openDialogShare();
                break;
            case R.id.tv_apply://报名
                if (tv_apply.getText().toString().equals("立即报名")) {
                    //得到指定模范的时间
                    String sign = TGmd5.getMD5(distributorid + activityid);
                    showLoadingProgressDialog(this, "");
                    activityApplyPresenter.activityApply(distributorid, activityid, sign);
                } else if (tv_apply.getText().toString().equals("取消报名")) {
                    showCancleDialog();
                }

                break;
            case R.id.tv_sned://发布评论
                String trim = et_evaluate.getText().toString().trim();
                if (trim == null || trim.equals("")) {
                    MyToast.makeText(this, "请填写评论内容！", Toast.LENGTH_SHORT).show();
                    break;
                }

                String sign1 = TGmd5.getMD5(distributorid + activityid + trim);
                showLoadingProgressDialog(this, "");
                commentSubmitPresenter.commentSubmit(distributorid, activityid, trim, sign1);
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + activityid);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(ActDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url + activityid);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(ActDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();

                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url + activityid);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(ActDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url + activityid);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(ActDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();

                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
        }
    }


    // 弹出分享对话框
    private void openDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭分享对话框
    private void closeDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);

    }

    public void showCancleDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定取消报名此活动吗?");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        yes.setText("确定");
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("不取消");

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
                String sign = TGmd5.getMD5(distributorid + activityid);
                showLoadingProgressDialog(ActDetailActivity.this, "");
                cancelApplyActPresenter.cancelApply(distributorid, activityid, sign);
                mAlertDialog.dismiss();
            }
        });
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    //报名的
    @Override
    public void OnActivityApplySuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "报名成功！", Toast.LENGTH_SHORT).show();
        tv_apply.setText("取消报名");
        initDatas();
    }

    @Override
    public void OnActivityApplyFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "报名失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivityApplyProgress() {

    }

    //发表评论
    @Override
    public void OnCommentSubmitSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "发表评论成功！", Toast.LENGTH_SHORT).show();
        et_evaluate.setText("");
        discussFragment.onRefresh();
    }

    @Override
    public void OnCommentSubmitFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "发表评论失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCommentSubmitProgress() {

    }

    @Override
    public void OnCancelApplyActSuccCallBack(String state, String respanse) {
        //活动取消报名成功
        closeLoadingProgressDialog();
        Log.e("kjhsksss", "--------" + respanse);
        MyToast.makeText(this, "取消报名成功！", Toast.LENGTH_SHORT).show();
        tv_apply.setText("立即报名");
        initDatas();

    }

    @Override
    public void OnCancelApplyActFialCallBack(String state, String respanse) {
        //活动取消报名失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCancelApplyActProgress() {

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

    /**
     * viewpager的
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            if (state1.equals("2") || isshowBM) {
                //已结束
                rl_introduction.setVisibility(View.GONE);
            } else {
                rl_introduction.setVisibility(View.VISIBLE);
            }
            ll_bottom.setVisibility(View.GONE);
        } else if (position == 1) {
            rl_introduction.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.VISIBLE);
        }
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
